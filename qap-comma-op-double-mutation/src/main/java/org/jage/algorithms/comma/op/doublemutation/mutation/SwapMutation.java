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

package org.jage.algorithms.comma.op.doublemutation.mutation;

import org.jage.variation.mutation.AbstractStochasticMutate;

import java.util.List;
import java.util.Random;

public class SwapMutation extends AbstractStochasticMutate<Integer> {
    private final Random random = new Random();

    @Override
    protected void doMutate(List<Integer> integers, int index) {
        int indexToSwap = Math.abs(random.nextInt() % integers.size());
        int tmp = integers.get(index);
        integers.set(index, integers.get(indexToSwap));
        integers.set(indexToSwap, tmp);
    }
}
