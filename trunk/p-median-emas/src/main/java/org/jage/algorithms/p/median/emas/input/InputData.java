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

package org.jage.algorithms.p.median.emas.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Norbert Tusiński
 * Date: 11/27/13
 * Time: 9:32 AM
 */
public class InputData {
    private static final Logger LOG = LoggerFactory.getLogger(InputData.class);
    private int locationForFacilitiesNumber;
    private int facilitiesNumber;
    private int optimum;
    private Map<Integer, Map<Integer, Integer>> costs;

    public InputData(String filePath) throws IOException {
        String[] fileContent = new FileContentReader().readFileContent(filePath);

        locationForFacilitiesNumber = Integer.parseInt(fileContent[0]);
        facilitiesNumber = Integer.parseInt(fileContent[1]);
        optimum = Integer.parseInt(fileContent[2]);

        LOG.info("Reading operations for: " + locationForFacilitiesNumber + " locations for facilities and "
                + facilitiesNumber + " facilities...");

        costs = new HashMap<Integer, Map<Integer, Integer>>();

        int index = 3;

        for (int i = 3; i < fileContent.length; i += 3) {
            int facility = Integer.parseInt(fileContent[i]) - 1;
            int client = Integer.parseInt(fileContent[i + 1]) - 1;
            int cost = Integer.parseInt(fileContent[i + 2]);

            Map<Integer, Integer> facilityCosts = costs.get(facility);
            if (facilityCosts == null) {
                facilityCosts = new HashMap<Integer, Integer>();
                costs.put(facility, facilityCosts);
            }
            facilityCosts.put(client, cost);
        }
    }

    public int getLocationForFacilitiesNumber() {
        return locationForFacilitiesNumber;
    }

    public int getFacilitiesNumber() {
        return facilitiesNumber;
    }

    public int getOptimum() {
        return optimum;
    }

    public int getCost(int facility, int client) {
        if (costs.get(facility) == null || costs.get(facility).get(client) == null) {
            return -1;
        } else {
            return costs.get(facility).get(client);
        }
    }
}
