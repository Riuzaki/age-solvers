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
 * Created: 2011-04-29
 * $Id: NopePreselectionActionStrategy.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.comma.op.emas.strategy;

import org.jage.action.AbstractPerformActionStrategy;
import org.jage.action.IActionContext;
import org.jage.agent.AgentException;
import org.jage.agent.IAgent;
import org.jage.genetic.preselection.IPreselection;
import org.jage.solution.ISolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * This action handler performs a preselection on an agent's population, using a given preselection algorithm. The
 * agent's population is replaced with the preselected one.
 *
 * @param <S>
 *            the type of solutions
 *
 * @author AGH AgE Team
 */
public final class NopePreselectionActionStrategy<S extends ISolution> extends AbstractPerformActionStrategy {

   private static final Logger LOG = LoggerFactory.getLogger(NopePreselectionActionStrategy.class);

   @Inject
   private IPreselection<S, Double> preselection;

   @Override
   public void perfom(final IAgent target, final IActionContext context) throws AgentException {
   }
}
