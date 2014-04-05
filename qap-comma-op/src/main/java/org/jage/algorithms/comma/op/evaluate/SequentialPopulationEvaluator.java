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
 * Created: 2011-12-01
 * $Id: SequentialPopulationEvaluator.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.comma.op.evaluate;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.jage.evaluation.IPopulationEvaluator;
import org.jage.evaluation.ISolutionEvaluator;
import org.jage.population.IPopulation;
import org.jage.property.ClassPropertyContainer;
import org.jage.solution.ISolution;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple {@link IPopulationEvaluator} strategy, which evaluates solutions sequentially, one by one.
 *
 * @param <S> The type of {@link ISolution} to be evaluated
 * @param <E> the type of evaluation
 * @author AGH AgE Team
 */
public class SequentialPopulationEvaluator<S extends ISolution, E> extends ClassPropertyContainer implements
  IPopulationEvaluator<S, E>
{
   private static Object instance;

   @Inject
   private ISolutionEvaluator<S, E> evaluator;

   private SortedSetMultimap<Double, S> sortedPop = TreeMultimap.create(new Comparator<Double>()
                                                                        {
                                                                           @Override
                                                                           public int compare (Double o1, Double o2)
                                                                           {
                                                                              return o1.compareTo(o2);
                                                                           }

                                                                           @Override
                                                                           public boolean equals (Object obj)
                                                                           {
                                                                              return obj == this;
                                                                           }
                                                                        }, new Comparator<S>()
                                                                        {
                                                                           @Override
                                                                           public int compare (S o1, S o2)
                                                                           {
                                                                              return -1;
                                                                           }

                                                                           @Override
                                                                           public boolean equals (Object obj)
                                                                           {
                                                                              return false;
                                                                           }
                                                                        }
   );

   //= new SynchronizedSortedSetMultimap<E, S>();
   private AtomicInteger popSize = new AtomicInteger();

   private AtomicInteger epoch = new AtomicInteger(1);

   public static <S extends ISolution, E> SequentialPopulationEvaluator<S, E> getInstance ()
   {
      return (SequentialPopulationEvaluator<S, E>) instance;
   }

   public static <S extends ISolution> int getRank (final S solution)
   {
      //getInstance().sortedPop.put(getInstance().evaluator.evaluate(solution), solution);
      if (getInstance().sortedPop.size() > 100)
      {
         System.exit(0);
      }
      int rank = getInstance().sortedPop.size() - 1;
      for (ISolution s : getInstance().sortedPop.values())
      {
         if (solution == s)
         {
            return rank;
         }
         rank--;
      }
      throw new RuntimeException("getRank() error: parameter not in sortedpop");
   }

   public static int getPopSize ()
   {
      return getInstance().popSize.get();
   }

   public static int getEpoch ()
   {
      return getInstance().epoch.get();
   }

   @Override
   public void evaluatePopulation (final IPopulation<S, E> population)
   {
      init();
      popSize.set(population.size());
      sortedPop.clear();
      for (final S solution : population)
      {
         final E evaluation = evaluator.evaluate(solution);
         population.setEvaluation(solution, evaluation);
         int siz = sortedPop.size();
         sortedPop.put((Double) evaluation, solution);
         if (siz == sortedPop.size())
         {
            System.out.println("");
         }
      }
      epoch.incrementAndGet();
   }

   private void init ()
   {
      if (instance == null)
      {
         instance = this;
      }
   }
}
