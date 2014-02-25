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

package org.jage.algorithms.p.median.emas.solution;

import org.jage.algorithms.p.median.emas.input.InputData;
import org.jage.algorithms.p.median.emas.input.InputDataHolder;
import org.jage.problem.IVectorProblem;

import java.io.IOException;

/**
 * User: Norbert Tusiński
 * Date: 11/27/13
 * Time: 10:05 AM
 */
public class Problem implements IVectorProblem<Double> {
    private InputData inputData;

    public Problem() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    @Override
    public final int getDimension() {
        return inputData.getLocationForFacilitiesNumber();
    }

    @Override
    public final Double lowerBound(int atDimension) {
        checkDimension(atDimension);
        return 0.;
    }

    @Override
    public final Double upperBound(int atDimension) {
        checkDimension(atDimension);
        return 1.;
    }

    private void checkDimension(int atDimension) {
        if (atDimension < 0 || atDimension >= inputData.getLocationForFacilitiesNumber()) {
            throw new IllegalArgumentException("Dimension out of range");
        }
    }
}
