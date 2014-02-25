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

package org.jage.algorithms.jssp.jp.emas;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import org.jage.algorithms.jssp.jp.emas.model.InputData;
import org.jage.problem.IVectorProblem;
import org.jage.property.ClassPropertyContainer;
import org.jage.random.IIntRandomGenerator;
import org.jage.solution.ISolutionFactory;
import org.jage.solution.IVectorSolution;
import org.jage.solution.VectorSolution;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 15:44
 */
public class JobShopSolutionFactory extends ClassPropertyContainer implements
        ISolutionFactory<IVectorSolution<Double>> {

    @Inject
    private IIntRandomGenerator rand;
    @Inject
    private IVectorProblem<Double> problem;

    private InputData inputData;

    public JobShopSolutionFactory() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    @Override
    public IVectorSolution<Double> createEmptySolution() {
        final double[] representation = new double[problem.getDimension()];
        return new VectorSolution<Double>(new FastDoubleArrayList(representation));
    }

    @Override
    public IVectorSolution<Double> createInitializedSolution() {
        final double[] representation = new double[problem.getDimension()];
        for (int i = 0; i < problem.getDimension(); i++) {
            representation[i] = i / inputData.getMachineNo();
        }
        Collections.shuffle(Arrays.asList(representation));

        return new VectorSolution<Double>(new FastDoubleArrayList(representation));
    }

    @Override
    public IVectorSolution<Double> copySolution(final IVectorSolution<Double> solution) {
        final DoubleArrayList representation = (DoubleArrayList) solution.getRepresentation();
        return new VectorSolution<Double>(new FastDoubleArrayList(representation));
    }

    /**
     * Helper class with faster equals and compareTo methods.
     *
     * @author AGH AgE Team
     */
    private static class FastDoubleArrayList extends DoubleArrayList {

        private static final long serialVersionUID = -2132234650006853053L;

        public FastDoubleArrayList(final double[] representation) {
            super(representation);
        }

        public FastDoubleArrayList(final DoubleArrayList representation) {
            super(representation);
        }

        @Override
        public boolean equals(final Object o) {
            if (o instanceof DoubleArrayList) {
                return super.equals(o);
            } else {
                return super.equals(o);
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public int compareTo(final List<? extends Double> l) {
            if (l instanceof DoubleArrayList) {
                return super.compareTo((DoubleArrayList) l);
            } else {
                return super.compareTo(l);
            }
        }
    }
}