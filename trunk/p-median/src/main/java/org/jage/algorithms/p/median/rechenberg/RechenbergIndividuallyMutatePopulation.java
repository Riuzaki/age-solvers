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
 * Created: 2011-10-20
 * $Id: IndividuallyMutatePopulation.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.p.median.rechenberg;

import org.jage.population.IPopulation;
import org.jage.random.INormalizedDoubleRandomGenerator;
import org.jage.solution.ISolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.mutation.IMutatePopulation;
import org.jage.variation.mutation.IMutateSolution;

import javax.inject.Inject;

/**
 * An {@link org.jage.variation.mutation.IMutatePopulation} implementation which mutates each solution individually, based on a chanceToMutate
 * property to decide whether mutation should occur, and a specified {@link org.jage.variation.mutation.IMutateSolution} strategy to perform it.
 *
 * @param <S>
 *            The type of the solutions held by the population to be mutated
 * @author AGH AgE Team
 */
public final class RechenbergIndividuallyMutatePopulation<S extends ISolution> extends AbstractStrategy implements
        IMutatePopulation<S> {

    @Inject
    private INormalizedDoubleRandomGenerator rand;

    @Inject
    private IMutateSolution<S> mutate;

    /**
     * Creates an IndividuallyMutatePopulation with a default chance to mutate.
     */
    public RechenbergIndividuallyMutatePopulation() {
    }

    /**
     * Creates an IndividuallyMutatePopulation with a given chance to mutate.
     *
     * @param chanceToMutate
     *            the chance to mutate
     */
    public RechenbergIndividuallyMutatePopulation(final double chanceToMutate) {
    }

    public void setChanceToMutate(final double chanceToMutate) {
    }

    @Override
    public void mutatePopulation(final IPopulation<S, ?> population) {
        for (final S solution : population) {
            if (rand.nextDouble() < Settings.getChanceToMutate()) {
                mutate.mutateSolution(solution);
            }
        }
    }
}
