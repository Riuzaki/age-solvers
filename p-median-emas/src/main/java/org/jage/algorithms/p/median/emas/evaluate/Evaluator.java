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
 * Created: 2011-11-03
 * $Id: IntegerSolutionFactory.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.p.median.emas.evaluate;

import com.google.common.util.concurrent.AtomicDouble;
import org.jage.algorithms.p.median.emas.input.InputData;
import org.jage.algorithms.p.median.emas.input.InputDataHolder;
import org.jage.solution.IVectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Norbert Tusiński
 * Date: 11/27/13
 * Time: 9:52 AM
 */
public class Evaluator extends org.jage.property.ClassPropertyContainer implements org.jage.evaluation.ISolutionEvaluator<org.jage.solution.IVectorSolution<Double>, Double> {
    private static final Logger LOG = LoggerFactory.getLogger(Evaluator.class);
    private static final int PENALTY = 100;
    private InputData inputData;

    private static final AtomicDouble best = new AtomicDouble(Double.MAX_VALUE);
    private static Timer timer;

    private static long startTime;

    public Evaluator() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    @Override
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

        int numberOfCustomers = solution.getRepresentation().size();
        int numberOfFacilityPlaces = solution.getRepresentation().size();

        int total = 0;
        for (int i = 0; i < numberOfCustomers; i++) {
            int min = PENALTY;
            for (int j = 0; j < numberOfFacilityPlaces; j++) {
                if (solution.getRepresentation().get(j) == 1 && inputData.getCost(j, i) != -1
                        && inputData.getCost(j, i) < min) {
                    min = inputData.getCost(j, i);
                }
            }
            total += min;
        }

        LOG.debug("Evaluator returns: " + (-total));

        if (total < best.get()) {
            best.set(total);
        }

        return (double) -total;
    }
}
