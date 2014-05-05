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

package org.jage.algorithms.comma.op.cloning.input;

import java.io.IOException;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 07:48
 */
public class InputDataHolder
{
   //private static final String FILE_PATH = "input/example";
   //private static final String FILE_PATH = "input/jensen-example"; // optimum: 3260

    //private static final String FILE_PATH = "input/tailard/tai12a.dat"; // optimum: 224416
    //private static final String FILE_PATH = "input/tailard/tai12b.dat";
    //private static final String FILE_PATH = "input/tailard/tai15a.dat"; // 388214
    //  private static final String FILE_PATH = "input/tailard/tai15b.dat";
    private static final String FILE_PATH = "input/tailard/tai20a.dat"; // optimum: 703482
    //private static final String FILE_PATH = "input/tailard/tai20b.dat";

   private static InputDataHolder instance;

   private InputData inputData;

   private InputDataHolder () throws IOException
   {
      inputData = new InputData(FILE_PATH);
   }

   private InputDataHolder (String src) throws IOException
   {
      inputData = new InputData(src);
   }

   public static InputDataHolder getInstance () throws IOException
   {
      if (instance == null)
      {
         instance = new InputDataHolder();
      }
      return instance;
   }

   public static InputDataHolder getInstance (String src) throws IOException
   {
      if (instance == null)
      {
         instance = new InputDataHolder(src);
      }
      return instance;
   }

   public InputData getInputData ()
   {
      return inputData;
   }
}
