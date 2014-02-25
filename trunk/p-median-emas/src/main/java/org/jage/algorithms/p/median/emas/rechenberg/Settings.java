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

package org.jage.algorithms.p.median.emas.rechenberg;

import com.google.common.util.concurrent.AtomicDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Settings {
    private static final Logger LOG = LoggerFactory.getLogger(Settings.class);

    private static AtomicDouble chanceToMutate = new AtomicDouble(0.5);
    private static double increase = 1.01;
    private static double decrease = 0.99;

    public static void increase() {
        LOG.debug("INCREAAAAAAAAAAAAAAAAASE");
        //chanceToMutate.set(chanceToMutate.get() * increase);
    }

    public static void decrease() {
        LOG.debug("DECREEEEEEEEEEEEEEEEASE");
        //chanceToMutate.set(chanceToMutate.get() * decrease);
    }

    public static void setChanceToMutate(double newValue) {
        chanceToMutate.set(newValue);
    }

    public static double getChanceToMutate() {
        return chanceToMutate.get();
    }
}
