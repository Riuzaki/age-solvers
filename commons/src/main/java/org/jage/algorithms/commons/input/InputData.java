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

package org.jage.algorithms.commons.input;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InputData {
    private static final Logger LOG = LoggerFactory.getLogger(InputData.class);
    private int n;
    private int[][] flow;
    private int[][] distances;

    public InputData(String filePath) throws IOException {
        String[] fileContent = new FileContentReader().readFileContent(filePath);

        n = Integer.parseInt(fileContent[0]);

        LOG.info("Reading operations for: " + n + " size problem");

        flow = new int[n][n];
        distances = new int[n][n];

        int index = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distances[j][i] = Integer.parseInt(fileContent[index++]);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                flow[j][i] = Integer.parseInt(fileContent[index++]);
            }
        }
    }

    public int getN() {
        return n;
    }

    public int getFlow(int f1, int f2) {
        return flow[f1][f2];
    }

    public int getDistance(int l1, int l2) {
        return distances[l1][l2];
    }
}
