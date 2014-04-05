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

package org.jage.algorithms.comma.op.evaluate;

import com.google.common.util.concurrent.AtomicDouble;
import org.jage.algorithms.comma.op.input.InputData;
import org.jage.algorithms.comma.op.input.InputDataHolder;
import org.jage.solution.IVectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Norbert Tusiński
 * Date: 11/27/13
 * Time: 3:44 PM
 */
public class Evaluator extends org.jage.property.ClassPropertyContainer implements org.jage.evaluation.ISolutionEvaluator<org.jage.solution.IVectorSolution<Integer>, Double>
{
   private static final Logger LOG = LoggerFactory.getLogger(Evaluator.class);

   private static final AtomicDouble best = new AtomicDouble(Double.MAX_VALUE);

   private static List<Integer> bestList;

   private static Timer timer;

   private static long startTime;

   private static int maxEvalStep;

   static
   {
      try
      {
         maxEvalStep = 1000 * (int) Math.pow(InputDataHolder.getInstance().getInputData().getN(), 2);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private static Evaluator instance;

   private InputData inputData;

   private AtomicInteger currentEvalStep = new AtomicInteger(0);

   public Evaluator () throws IOException
   {
      inputData = InputDataHolder.getInstance().getInputData();
      instance = this;
   }

   public static Evaluator getInstance ()
   {
      return instance;
   }

   @Override
   public Double evaluate (IVectorSolution<Integer> solution)
   {
      if (timer == null)
      {
         startTime = System.currentTimeMillis();

         if (currentEvalStep.get() % 5000 == 0)
         {
            if (currentEvalStep.get() < maxEvalStep)
            {
               if (bestList != null)
                  LOG.warn("{} {} {}", (System.currentTimeMillis() - startTime) / 1000., best.get(), bestList);
            }
            else
            {
               LOG.warn("FINISHED EXECUTION");
               System.exit(0);
            }
         }
      }

      LOG.debug("Solution vector: " + solution.getRepresentation());

      int n = inputData.getN();

      int total = 0;
      for (int i = 0; i < n; i++)
      {
         for (int j = i + 1; j < n; j++)
         {
            int flow = inputData.getFlow(solution.getRepresentation().get(i),
              solution.getRepresentation().get(j)) + inputData.getFlow(solution.getRepresentation().get(j),
              solution.getRepresentation().get(i));

            total += inputData.getDistance(i, j) * flow;
         }
      }

      LOG.debug("Evaluator returns: {}", -total);

      if (total < best.get())
      {
         best.set(total);
         synchronized (best)
         {
            bestList = new ArrayList<Integer>(Collections.nCopies(solution.getRepresentation().size(), 0));
            Collections.copy(bestList, solution.getRepresentation());
         }
      }

      LOG.debug("Current: " + total + ", best: " + best.get());

      currentEvalStep.incrementAndGet();

      return (double) -total;
   }

   public double getRate ()
   {
      return ((double) currentEvalStep.get()) / ((double) maxEvalStep);
   }
}
