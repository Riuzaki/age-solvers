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

import org.jage.algorithms.commons.properties.ProjectPropertiesHolder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ResultPrinter implements Closeable {
    private static final long LOG_INTERVAL = 1000L;
    private static final String FOLDER_PATH = ProjectPropertiesHolder.getProperties().getProperty(ProjectPropertiesHolder.LOG_FOLDER_PATH_PROPERTY_NAME);
    private static final String FILE_PATH = FOLDER_PATH + File.separator;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    private long startTime = new Date().getTime();
    private long lastLogTime = startTime;

    private PrintWriter resultFileWriter;

    public ResultPrinter() {
        try {
            File folder = new File(FOLDER_PATH);
            if (!folder.exists()) folder.mkdir();
            resultFileWriter = new PrintWriter(FILE_PATH + DATE_FORMAT.format(startTime));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void printStep(int bestFitness) {
        long currentTime = new Date().getTime();

        if (currentTime - lastLogTime > LOG_INTERVAL) {
            resultFileWriter.println("" + TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime) + ";" + bestFitness);
            lastLogTime = currentTime;
        }
    }

    @Override
    public void close() throws IOException {
        resultFileWriter.close();
    }
}
