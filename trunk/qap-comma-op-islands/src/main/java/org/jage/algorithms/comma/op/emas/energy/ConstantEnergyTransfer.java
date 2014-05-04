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
 * Created: 2012-01-30
 * $Id: ConstantEnergyTransfer.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.comma.op.emas.energy;

import org.jage.emas.agent.IndividualAgent;
import org.jage.emas.energy.EnergyTransfer;

/**
 * A constant energy transfer strategy. Always transfers the same fixed amount of energy. However, if the source have
 * less energy left, only this much will be transferred.
 *
 * @author AGH AgE Team
 */
public class ConstantEnergyTransfer implements EnergyTransfer<IndividualAgent>
{

   private double transferredEnergy;

   public void setTransferredEnergy (final double transferredEnergy)
   {
      this.transferredEnergy = transferredEnergy;
   }

   @Override
   public final double transferEnergy (final IndividualAgent source, final IndividualAgent target)
   {
      double f1 = source.getEffectiveFitness();
      double f2 = target.getEffectiveFitness();

      double e1 = source.getEnergy();
      double e2 = target.getEnergy();

      if (f1 * e1 > f2 * e2)
      {
         source.changeEnergyBy(transferredEnergy);
         target.changeEnergyBy(-transferredEnergy);
         return -transferredEnergy;
      }
      else
      {
         source.changeEnergyBy(-transferredEnergy);
         target.changeEnergyBy(transferredEnergy);
         return transferredEnergy;
      }
   }
}
