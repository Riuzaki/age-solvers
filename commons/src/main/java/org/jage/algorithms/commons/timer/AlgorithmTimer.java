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

package org.jage.algorithms.commons.timer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlgorithmTimer {
    private static long ALGORITHM_DURATION_IN_SECONDS = 60;

    private long startTime = new Date().getTime();

    public boolean isAlgorithmEnd() {
        long currentTime = new Date().getTime();
        return TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime) > ALGORITHM_DURATION_IN_SECONDS;
    }

    public double calculateCompletionLevel() {
        long currentTime = new Date().getTime();
        double counter = (currentTime - startTime);
        double denominator = TimeUnit.SECONDS.toMillis(ALGORITHM_DURATION_IN_SECONDS);
        if (counter > denominator) denominator = counter;
        return counter / denominator;
    }
}
