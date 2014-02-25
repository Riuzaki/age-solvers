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
 * Created: 2011-12-16
 * $Id: InitializationActionStrategy.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.jssp.sp.model;

import org.jage.algorithms.jssp.sp.file.FileContentReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InputData {
    private static final Logger LOG = LoggerFactory.getLogger(InputData.class);
    private int jobsNo;
    private int machineNo;
    private int optimum;
    private int[][] operationsTime;
    private int[][] operationsMachines;

    public InputData(String filePath) throws IOException {
        String[] fileContent = new FileContentReader().readFileContent(filePath);

        jobsNo = Integer.parseInt(fileContent[0]);
        machineNo = Integer.parseInt(fileContent[1]);
        optimum = Integer.parseInt(fileContent[2]);

        LOG.info("Reading operations for: " + jobsNo + " jobs and " + machineNo + " machines...");

        operationsTime = new int[jobsNo][machineNo];
        operationsMachines = new int[jobsNo][machineNo];

        int index = 3;
        for (int i = 0; i < jobsNo; i++) {
            for (int j = 0; j < machineNo; j++) {
                operationsTime[i][j] = Integer.parseInt(fileContent[index++]);
            }
        }
        for (int i = 0; i < jobsNo; i++) {
            for (int j = 0; j < machineNo; j++) {
                operationsMachines[i][j] = Integer.parseInt(fileContent[index++]) - 1;
            }
        }
        for (int i = 0; i < jobsNo; i++) {
            StringBuilder sb = new StringBuilder("JOB " + i + ": ");
            for (int j = 0; j < machineNo; j++) {
                sb.append("M" + operationsMachines[i][j] + "(" + operationsTime[i][j] + ")");
                if (j != machineNo - 1) {
                    sb.append(", ");
                }
            }
            LOG.info(sb.toString());
        }
    }

    public int getJobsNo() {
        return jobsNo;
    }

    public void setJobsNo(int jobsNo) {
        this.jobsNo = jobsNo;
    }

    public int getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(int machineNo) {
        this.machineNo = machineNo;
    }

    public int getOptimum() {
        return optimum;
    }

    public void setOptimum(int optimum) {
        this.optimum = optimum;
    }

    public int[][] getOperationsTime() {
        return operationsTime;
    }

    public void setOperationsTime(int[][] operationsTimes) {
        this.operationsTime = operationsTimes;
    }

    public int[][] getOperationsMachines() {
        return operationsMachines;
    }

    public void setOperationsMachines(int[][] operationsMachines) {
        this.operationsMachines = operationsMachines;
    }
}
