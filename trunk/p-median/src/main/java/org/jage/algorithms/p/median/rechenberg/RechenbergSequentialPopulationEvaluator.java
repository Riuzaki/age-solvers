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
 * Created: 2011-12-01
 * $Id: SequentialPopulationEvaluator.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.p.median.rechenberg;

import com.google.common.util.concurrent.AtomicDouble;
import org.jage.evaluation.IPopulationEvaluator;
import org.jage.evaluation.ISolutionEvaluator;
import org.jage.population.IPopulation;
import org.jage.property.ClassPropertyContainer;
import org.jage.solution.ISolution;
import org.jage.solution.IVectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RechenbergSequentialPopulationEvaluator<S extends ISolution, E> extends ClassPropertyContainer implements
        IPopulationEvaluator<S, E> {

    private static final Logger LOG = LoggerFactory.getLogger(RechenbergSequentialPopulationEvaluator.class);
    private int epochCount = 0;
    private double bestFitness = Double.MAX_VALUE;
    private boolean success = false;

    private AtomicDouble diversity = new AtomicDouble(0.0);
    private static Timer timer;
    private static long startTime;

    @Inject
    private ISolutionEvaluator<S, E> evaluator;

    private void calculateStandardDev(final IPopulation<S, E> population) {
        try {
            int size = ((IVectorSolution<Integer>) population.iterator().next()).getRepresentation().size();

            double[] sums = new double[size];
            double[] avgs = new double[size];
            double[] standardDevs = new double[size];

            for (final S solution : population) {
                IVectorSolution<Integer> s = (IVectorSolution<Integer>) solution;
                List<Integer> representation = s.getRepresentation();
                for (int i = 0; i < representation.size(); i++) {
                    sums[i] += representation.get(i);
                }
            }
            for (int i = 0; i < sums.length; i++) {
                avgs[i] = sums[i] / population.size();
            }

            for (final S solution : population) {
                IVectorSolution<Integer> s = (IVectorSolution<Integer>) solution;
                List<Integer> representation = s.getRepresentation();
                for (int i = 0; i < representation.size(); i++) {
                    standardDevs[i] += Math.pow(representation.get(i) - avgs[i], 2);
                }
            }
            double currentDiversity = 0;
            for (int i = 0; i < standardDevs.length; i++) {
                standardDevs[i] /= population.size();
                standardDevs[i] = Math.sqrt(standardDevs[i]);
                currentDiversity += standardDevs[i];
            }
            currentDiversity /= population.size();
            diversity.set(currentDiversity);

            if (timer == null && diversity.get() != 0) {
                startTime = System.currentTimeMillis();

                timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                    LOG.warn("{};{}", (System.currentTimeMillis() - startTime) / 1000., diversity.get());
                    }
                }, 0, 500);
            }
        } catch (RuntimeException e) {
            LOG.error("REx {}", e);
        }
    }

    @Override
    public void evaluatePopulation(final IPopulation<S, E> population) {
        if (population != null) {
            calculateStandardDev(population);
        }

        for (final S solution : population) {
            E evaluation = evaluator.evaluate(solution);
            if ((Double) evaluation < bestFitness) {
                success = true;
                bestFitness = (Double) evaluation;
            }
            population.setEvaluation(solution, evaluation);
        }
        epochCount++;
        if (epochCount >= 30) {
            if (success) {
                Settings.increase();
            } else {
                Settings.decrease();
            }
            success = false;
            epochCount = 0;
        }
    }
}
