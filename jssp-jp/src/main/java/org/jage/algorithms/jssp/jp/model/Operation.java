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

package org.jage.algorithms.jssp.jp.model;

public class Operation implements Comparable<Operation> {
    private int operationStart;
    private int operationEnd;

    private int machineNumber = -1;
    private int jobNumber = -1;
    private int operationNumber = -1;

    public Operation(int operationStart, int operationEnd) {
        this.operationStart = operationStart;
        this.operationEnd = operationEnd;
    }

    public Operation(int operationStart, int operationEnd, int machineNumber, int jobNumber, int operationNumber) {
        this(operationStart, operationEnd);

        this.machineNumber = machineNumber;
        this.jobNumber = jobNumber;
        this.operationNumber = operationNumber;
    }

    public int getOperationStart() {
        return operationStart;
    }

    public void setOperationStart(int operationStart) {
        this.operationStart = operationStart;
    }

    public int getOperationEnd() {
        return operationEnd;
    }

    public void setOperationEnd(int operationEnd) {
        this.operationEnd = operationEnd;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    @Override
    public int compareTo(Operation o) {
        return Integer.valueOf(operationStart).compareTo(o.operationStart);
    }

    @Override
    public String toString() {
        return "m" + machineNumber + "j" + jobNumber + "o" + operationNumber + "(" + operationStart + ","
                + operationEnd + ")";
    }
}