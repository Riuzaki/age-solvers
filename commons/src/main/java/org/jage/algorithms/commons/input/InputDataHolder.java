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

import org.jage.algorithms.commons.properties.ProjectPropertiesHolder;

import java.io.IOException;

public class InputDataHolder {
    private static InputDataHolder instance;

    private InputData inputData;

    private InputDataHolder() throws IOException {
        inputData = new InputData(ProjectPropertiesHolder.getProperties().getProperty(ProjectPropertiesHolder.INPUT_PATH_PROPERTY_NAME));
    }

    private InputDataHolder(String src) throws IOException {
        inputData = new InputData(src);
    }

    public static InputDataHolder getInstance() throws IOException {
        if (instance == null) {
            instance = new InputDataHolder();
        }
        return instance;
    }

    public static InputDataHolder getInstance(String src) throws IOException {
        if (instance == null) {
            instance = new InputDataHolder(src);
        }
        return instance;
    }

    public InputData getInputData() {
        return inputData;
    }
}
