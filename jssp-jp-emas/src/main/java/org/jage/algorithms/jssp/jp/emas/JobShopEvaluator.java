/**
 * Copyright (C) 2006 - 2012
 *   Pawel Kedzior
 *   Tomasz Kmiecik
 *   Kamil Pietak
 *   Krzysztof Sikora
 *   Adam Wos
 *   Lukasz Faber
 *   Daniel Krzywicki
 *   and other students of AGH University of Science and Technology.
 *
 * This file is part of AgE.
 *
 * AgE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AgE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AgE.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created: 2011-12-16
 * $Id: InitializationActionStrategy.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.jssp.jp.emas;

import com.google.common.util.concurrent.AtomicDouble;
import org.jage.algorithms.jssp.jp.emas.model.InputData;
import org.jage.algorithms.jssp.jp.emas.model.Operation;
import org.jage.solution.IVectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 07:15
 */
public class JobShopEvaluator extends org.jage.property.ClassPropertyContainer implements org.jage.evaluation.ISolutionEvaluator<IVectorSolution<Double>, Double> {
    private static final Logger LOG = LoggerFactory.getLogger(JobShopEvaluator.class);
    private InputData inputData;

    private static final AtomicDouble best = new AtomicDouble(Double.MAX_VALUE);
    private static Timer timer;

    private static long startTime;


    public JobShopEvaluator() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    public Double evaluate(IVectorSolution<Double> solution) {

        if (timer == null) {
            startTime = System.currentTimeMillis();

            timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //LOG.warn("{};{}", (System.currentTimeMillis() - startTime) / 1000., best.get());
                }
            }, 0, 500);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Solution vector: " + solution.getRepresentation());

        }

        List<List<Operation>> machinesTime = new ArrayList<List<Operation>>();

        int[] jobsTime = new int[inputData.getJobsNo()];
        int[] jobsIndexes = new int[inputData.getJobsNo()];

        for (int i = 0; i < inputData.getMachineNo(); i++) {
            machinesTime.add(new ArrayList<Operation>());
        }

        for (int i = 0; i < solution.getRepresentation().size(); i++) {
            int job = solution.getRepresentation().get(i).intValue();
            int jobTime = jobsTime[job];
            int jobIndex = jobsIndexes[job];

            int machine = inputData.getOperationsMachines()[job][jobIndex];
            int time = inputData.getOperationsTime()[job][machine];
            List<Operation> machineSchedule = machinesTime.get(machine);

            int lastOpFinish = 0;
            int index = machineSchedule.size();
            for (int k = 0; k < machineSchedule.size(); k++) {
                Operation currOp = machineSchedule.get(k);
                if (Math.max(jobTime, lastOpFinish) + time <= currOp.getOperationStart()) {
                    index = k;
                    break;
                }
                lastOpFinish = currOp.getOperationEnd();
            }
            machineSchedule.add(index, new Operation(Math.max(jobTime, lastOpFinish), Math.max(jobTime, lastOpFinish)
                    + time, machine + 1, job + 1, jobIndex));
            jobTime = Math.max(jobTime, lastOpFinish) + time;

            jobsTime[job] = jobTime;
            jobsIndexes[job]++;
        }

        int max = 0;
        for (int i = 0; i < machinesTime.size(); i++) {
            List<Operation> operations = machinesTime.get(i);
            int currMax = operations.get(operations.size() - 1).getOperationEnd();
            if (currMax > max) {
                max = currMax;
            }
        }

        if (max < inputData.getOptimum()) {
            throw new RuntimeException("Optimum beaten");
        }

        LOG.debug("Job Shop evaluator returns: " + max);

        if (max < best.get()) {
            best.set(max);
        }
        return (double) -max;
    }
}
