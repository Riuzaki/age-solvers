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
 * Created: 21-03-2012
 * $Id$
 */

package org.jage.algorithms.comma.op.cloning.recombination;

import org.jage.random.IIntRandomGenerator;
import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.recombination.IRecombine;

import javax.inject.Inject;

/**
 * Recombines the representations of two real-valued solutions at a random point.
 *
 * @author AGH AgE Team
 */
public class NopeRecombination extends AbstractStrategy implements IRecombine<IVectorSolution<Double>> {

	@Inject
	private IIntRandomGenerator rand;

    @Override
    public void recombine(final IVectorSolution<Double> solution1, final IVectorSolution<Double> solution2) {
    }
}
