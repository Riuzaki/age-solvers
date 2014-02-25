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

package org.jage.algorithms.jssp.sp;

import org.jage.algorithms.jssp.sp.model.InputData;
import org.jage.algorithms.jssp.sp.model.Operation;
import org.jage.solution.IVectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 07:15
 */
public class JobShopEvaluator extends org.jage.property.ClassPropertyContainer implements org.jage.evaluation.ISolutionEvaluator<org.jage.solution.IVectorSolution<Integer>, Double> {
    private static final Logger LOG = LoggerFactory.getLogger(JobShopEvaluator.class);
    private InputData inputData;

    public JobShopEvaluator() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    public Double evaluate(IVectorSolution<Integer> solution) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Solution vector: " + solution.getRepresentation());
        }

        List<List<Operation>> machinesTime = new ArrayList<List<Operation>>();

        for (int i = 0; i < inputData.getMachineNo(); i++) {
            machinesTime.add(new ArrayList<Operation>());
        }

        for (int i = 0; i < solution.getRepresentation().size(); i++) {
            int job = solution.getRepresentation().get(i);
            int jobTime = 0;

            for (int j = 0; j < inputData.getMachineNo(); j++) {
                int machine = inputData.getOperationsMachines()[job][j];
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
                machineSchedule.add(index,
                        new Operation(Math.max(jobTime, lastOpFinish), Math.max(jobTime, lastOpFinish) + time));
                jobTime = Math.max(jobTime, lastOpFinish) + time;
            }
        }

        int max = 0;
        for (int i = 0; i < machinesTime.size(); i++) {
            List<Operation> operations = machinesTime.get(i);
            int currMax = operations.get(operations.size() - 1).getOperationEnd();
            if (currMax > max) {
                max = currMax;
            }
        }

        LOG.info("Job Shop evaluator returns: " + (-max));

        return (double) -max;
    }
}
