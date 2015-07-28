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

package org.jage.algorithms.commons.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectPropertiesHolder {
    private static final String PROPERTIES_FILE_NAME = "config.properties";

    public static final String LOG_FOLDER_PATH_PROPERTY_NAME = "log.folder.path";
    public static final String INPUT_PATH_PROPERTY_NAME = "input.path";
    public static final String ALGORITHM_DURATION_IN_SECONDS_PROPERTY_NAME = "algorithm.duration.in.seconds";

    private static final String LOG_FOLDER_PATH_PROPERTY_DEFAULT_VALUE = "C:\\age-solvers-logs";
    private static final String INPUT_PATH_PROPERTY_DEFAULT_VALUE = "input/tailard/tai12a.dat";
    private static final Long ALGORITHM_DURATION_IN_SECONDS_DEFAULT_VALUE = 60L;

    private static Properties properties;

    public static synchronized Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                if (new File(PROPERTIES_FILE_NAME).exists()) {
                    readPropertiesFromFile();
                } else {
                    createDefaultProperties();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return properties;
    }

    private static synchronized void readPropertiesFromFile() throws IOException {
        properties.load(new FileInputStream(PROPERTIES_FILE_NAME));
    }

    private static synchronized void createDefaultProperties() throws IOException {
        properties.setProperty(LOG_FOLDER_PATH_PROPERTY_NAME, LOG_FOLDER_PATH_PROPERTY_DEFAULT_VALUE);
        properties.setProperty(INPUT_PATH_PROPERTY_NAME, INPUT_PATH_PROPERTY_DEFAULT_VALUE);
        properties.setProperty(ALGORITHM_DURATION_IN_SECONDS_PROPERTY_NAME, ALGORITHM_DURATION_IN_SECONDS_DEFAULT_VALUE.toString());
        properties.store(new FileOutputStream(PROPERTIES_FILE_NAME), null);
    }
}
