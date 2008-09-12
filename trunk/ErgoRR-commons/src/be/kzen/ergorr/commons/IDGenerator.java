/*
 * Project: Buddata ebXML RegRep
 * Class: IDGenerator.java
 * Copyright (C) 2008 Yaman Ustuntas
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.commons;

import java.util.UUID;

/**
 * UUID generator.
 *
 * @author <a href="mailto:yaman@cryptosense.com">Yaman Ustuntas</a>
 * Created on 25 January 2007
 */
public class IDGenerator {

    private IDGenerator() {
    }

    /**
     * Generates a UUID.
     * Example urn:uuid:be202f67-8b3c-44fd-885e-b69773b679a2
     * @return New UUID as string.
     */
    public static String generateUuid() {
        return "urn:uuid:" + UUID.randomUUID();
    }

    /**
     * Generates a custom UUID.
     * Example:
     * If <code>str</code> is "myname", 
     * generated UUID will be urn:myname:be202f67-8b3c-44fd-885e-b69773b679a2
     *
     * @param namespace String to customize UUID.
     * @return Custum UUID
     */
    public static String generateUuid(String namespace) {
        return "urn:" + namespace + ":" + UUID.randomUUID();
    }

    /**
     * Generate a random double number
     * between 0 and <code>Double.MAX_VALUE</code>
     * 
     * @return Random double.
     */
    public static double generateDoule() {
        return Math.random() * Double.MAX_VALUE;
    }

    /**
     * Generate a random long number
     * between 0 and <code>Long.MAX_VALUE</code>
     * 
     * @return Random long.
     */
    public static long generateLong() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }

    /**
     * Generate a random integer number
     * between 0 and <code>Integer.MAX_VALUE</code>
     * 
     * @return Random integer.
     */
    public static int generateInt() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}
