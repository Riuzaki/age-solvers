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

import org.jage.algorithms.comma.op.evaluate.Evaluator;
import org.jage.algorithms.comma.op.input.InputDataHolder;
import org.jage.solution.IVectorSolution;
import org.jage.solution.VectorSolution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: Norbert Tusiński
 * Date: 11/27/13
 * Time: 4:00 PM
 */
public class Test {
    private static final Logger LOGGER = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws IOException {
        //int[] tab = new int[]{7, 0, 5, 1, 10, 9, 2, 4, 8, 6, 11, 3};
        int[] tab = new int[]{1, 0, 3, 2};
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
        System.out.println(new File(".").getCanonicalPath());
        InputDataHolder.getInstance("qap-comma-op/input/example");
        IVectorSolution<Integer> sol = new VectorSolution<Integer>(list);
        LOGGER.info("Result is: " + new Evaluator().evaluate(sol));
    }
}
