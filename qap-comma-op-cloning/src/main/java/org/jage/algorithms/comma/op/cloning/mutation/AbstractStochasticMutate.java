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
 * $Id: AbstractStochasticMutate.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.comma.op.cloning.mutation;

import org.jage.algorithms.commons.evaluate.Evaluator;
import org.jage.random.IDoubleRandomGenerator;
import org.jage.random.IIntRandomGenerator;
import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

public abstract class AbstractStochasticMutate<R> extends AbstractStrategy implements IMutateSolution<IVectorSolution<R>>
{
   private static final double DEFAULT_CHANCE_TO_MUTATE = 1.0;

   private final int populationSize;

   private final int[] distances;

   @Inject
   private IDoubleRandomGenerator doubleRand;

   @Inject
   private IIntRandomGenerator intRand;

   private final Random random = new Random();

   protected AbstractStochasticMutate (int steps, int populationSize)
   {
      this.populationSize = populationSize;

      int size = populationSize / 2;
      distances = new int[size];
      for (int i = 0; i < size; i++)
      {
         distances[i] = i + 1;
      }
   }

   @Override
   public final void mutateSolution (final IVectorSolution<R> solution, int rank)
   {
      final List<R> representation = solution.getRepresentation();
      final int size = representation.size();

      double chanceToMutate = DEFAULT_CHANCE_TO_MUTATE;
      int mutatedBitsCount = (int) (chanceToMutate * size);
      final double chanceForExtraBit = chanceToMutate * size - mutatedBitsCount;
      final int extraBit = (doubleRand.nextDouble() < chanceForExtraBit) ? 1 : 0;
      mutatedBitsCount += extraBit;

      final boolean[] alreadyChecked = new boolean[size];
      for (int i = 0; i < 1; i++)
      {
         int k = intRand.nextInt(size);
         while (alreadyChecked[k])
         {
            k = intRand.nextInt(size);
         }
         alreadyChecked[k] = true;

         double fitness = 0.;
         double fitness2 = 0.;

         fitness = Evaluator.getInstance().evaluate((IVectorSolution<Integer>) solution);

         doMutate(representation, k, calculateRange(solution, rank));

         Evaluator.getInstance().decrStep();
         fitness2 = Evaluator.getInstance().evaluate((IVectorSolution<Integer>) solution);

         if (fitness2 < fitness)
         {
            revert(representation);
         }
      }
   }

   private int calculateRange (final IVectorSolution<R> solution, int r)
   {
      double rate = 1.0 - Evaluator.getInstance().getRate();
      //      System.out.println(rate);
      double rMinus = (((double) (r * distances.length)) / ((double) populationSize)) - rate * ((double) distances.length);
      int rMinusInt = (int) rMinus;
      if (rMinusInt < 0)
      {
         rMinusInt = 0;
      }
      double rPlus = (((double) (r * distances.length)) / ((double) populationSize)) + rate * ((double) distances.length);
      int rPlusInt = (int) rPlus;
      if (rPlusInt > distances.length - 1)
      {
         rPlusInt = distances.length - 1;
      }
      if (rMinusInt > rPlusInt)
      {
          if (rMinusInt < 0) {
              rMinusInt = 0;
          }
          rPlusInt = rMinusInt;
         //throw new RuntimeException("that's bad");
      }
      return (rMinusInt + random.nextInt((rPlusInt - rMinusInt + 1)));
   }

   /**
    * Mutate the representation at the given index. <br />
    * <br />
    * This method purpose is to allow efficient unboxing in case of representations of primitives. Subclasses can then
    * cast the given representation in the corresponding fastutil collection.
    *
    * @param representation the representation to be mutated
    * @param index          the index at which mutation should occur
    */
   protected abstract void doMutate (List<R> representation, int index, int range);

   protected abstract void revert (List<R> representation);
}
