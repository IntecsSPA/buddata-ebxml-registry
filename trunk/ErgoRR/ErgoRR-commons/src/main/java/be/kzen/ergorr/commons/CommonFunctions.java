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

import java.util.Collection;
import java.util.Iterator;

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

    /**
     * Get the XML element name from a XML complexType name.
     * E.g: If <code>typeName</code> is "ExtrinsicObjectType" then
     * this method will return "ExtrinsicObject".
     * This method assumes that all complexTypes end with "Type". 
     * 
     * @param typeName ComplexType name.
     * @return Element name.
     */
    public static String getElementName(String typeName) {
        if (typeName.length() > 4 && typeName.toLowerCase().endsWith("type")) {
            return typeName.substring(0, typeName.length() - 4);
        } else {
            return typeName;
        }
    }

    public static String listToString(Collection<String> col) {
        Iterator<String> items = col.iterator();
        StringBuilder sb = new StringBuilder();

        while (items.hasNext()) {
            sb.append(items.next().trim()).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static boolean stringHasData(String value) {
        return (value != null && value.trim().length() > 0);
    }

    public static String removeHttpPrefix(String url) {
        if (url.startsWith("http://")) {
            return url.substring(7);
        } else {
            return url;
        }
    }
}
