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

package org.jage.algorithms.p.median.emas.rechenberg;

import org.jage.algorithms.p.median.emas.evaluate.Evaluator;
import org.jage.solution.VectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 13:51
 */
public class RechenbergSwapMutation extends RechenbergAbstractStochasticMutate<Double> {
    private static final Logger LOG = LoggerFactory.getLogger(RechenbergSwapMutation.class);
    private Random random = new Random();

    private void swap(List<Double> integers, int i1, int i2) {
        double tmp = integers.get(i1);
        integers.set(i1, integers.get(i2));
        integers.set(i2, tmp);
    }

    @Override
    protected void doMutate(List<Double> integers, int index) {
        double bestFitness = -Double.MAX_VALUE;
        int bestIndex = index;
        for (int i = 0; i < 5; i++) {
            int indexToSwap = Math.abs(random.nextInt() % integers.size());
            swap(integers, index, indexToSwap);
            double fitness = 0;
            try {
                fitness = new Evaluator().evaluate(new VectorSolution<Double>(integers));
            } catch (IOException e) {
                LOG.error("I DON'T NEED THIS SHIT");
            }
            if (fitness >= bestFitness) {
                bestFitness = fitness;
                bestIndex = indexToSwap;
            }
            swap(integers, index, indexToSwap);
        }
        // lamarkizm
        swap(integers, index, bestIndex);
    }
}
