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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 13:51
 */
public class SwapMutationWithRange extends AbstractStochasticMutate<Integer>
{
   private static final Logger LOG = LoggerFactory.getLogger(SwapMutationWithRange.class);

   private static boolean fixedDistance = false;

   private Random random = new Random();

   public SwapMutationWithRange(int steps, int populationSize) {
      super(steps, populationSize);
   }

   int index1;
   int index2;

   @Override
   protected void doMutate (List<Integer> integers, int index, int range)
   {
      LOG.debug("range " + range);
      if (range > integers.size() / 2) {
         range = integers.size() / 2;
      }
      int realDistance = range == 0 ? 0 : fixedDistance ? range : (Math.abs(random.nextInt()) % range) + 1;
      int indexToSwap;
      if (index < integers.size() / 2)
      {
         indexToSwap = index + realDistance;
      }
      else
      {
         indexToSwap = index - realDistance;
      }
      index1 = index;
      index2 = indexToSwap;
      int tmp = integers.get(index);
      integers.set(index, integers.get(indexToSwap));
      integers.set(indexToSwap, tmp);
   }

   @Override
   protected void revert(List<Integer> integers) {
      int tmp = integers.get(index1);
      integers.set(index1, integers.get(index2));
      integers.set(index2, tmp);
   }
}
