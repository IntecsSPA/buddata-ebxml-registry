/*
 * Project: Buddata ebXML RegRep
 * Class: CommonFunctions.java
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

/**
 * Common functions used across the application.
 * 
 * @author Yaman Ustuntas
 */
public class CommonFunctions {

    /**
     * Remove the prefix from an element.
     * Example an input of "gml:Polygon" would return "Polygon"
     * 
     * @param obj String with prefix.
     * @return Input without the prefix.
     */
    public static String removePrefix(String obj) {
        int idx = obj.indexOf(":");
        return (idx > 0) ? obj.substring(idx + 1) : obj;
    }
}
