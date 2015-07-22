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

package org.jage.algorithms.commons.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ResultPrinter {
    private static final Logger LOG = LoggerFactory.getLogger(ResultPrinter.class);
    private static final long LOG_INTERVAL = 1000L;

    private long startTime = new Date().getTime();
    private long lastLogTime = startTime;

    public void printStep(int bestFitness) {
        long currentTime = new Date().getTime();

        if (currentTime - lastLogTime > LOG_INTERVAL) {
            LOG.warn("{};{}", TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime), bestFitness);
            lastLogTime = currentTime;
        }
    }
}
