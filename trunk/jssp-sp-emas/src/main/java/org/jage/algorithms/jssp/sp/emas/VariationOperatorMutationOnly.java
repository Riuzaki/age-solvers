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

package org.jage.algorithms.jssp.sp.emas;

import org.jage.population.IPopulation;
import org.jage.solution.ISolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.utils.JageUtils;
import org.jage.variation.IVariationOperator;
import org.jage.variation.mutation.IMutatePopulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 14:00
 */
public class VariationOperatorMutationOnly<S extends ISolution> extends AbstractStrategy implements IVariationOperator<S> {
    private static final Logger LOG = LoggerFactory.getLogger(VariationOperatorMutationOnly.class);
    @Inject
    private IMutatePopulation<S> mutate;

    @Override
    public void transformPopulation(final IPopulation<S, ?> population) {
        mutate.mutatePopulation(population);

        if (LOG.isDebugEnabled()) {
            LOG.debug(JageUtils.getPopulationLog(population, "Transformed population"));
        }
    }
}
