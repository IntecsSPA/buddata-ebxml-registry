/*
 * Project: Buddata ebXML RegRep
 * Class: InternalConstants.java
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
 * Internal constants used in this registry/repository implementation.
 *
 * @author Yaman Ustuntas
 */
public class InternalConstants {
    public static final String TYPE_GEOMETRY = "geometry";
    public static final String TYPE_DOUBLE = "double";
    public static final String TYPE_INTEGER = "int";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_DATETIME = "datetime";
    public static final String TYPE_BOOLEAN = "bool";
    public static final String CONTENT_TYPE_XML = "text/xml";
    
    public static final String SPEC_TYPE_WRS = "wrs";
    public static final String SPEC_TYPE_RIM = "rim";
    
    public static final int MAX_RESULTS = 10;
    public static final int START_POSITION = 11;
    public static final int ORDER_BY = 12;
    public static final int DB_CONNECTION_PARAMS = 14;
    public static final int DEPLOY_NAME = 15;

    public static final int RETURN_SLOTS = 20;
    public static final int RETURN_NAME_DESC = 21;
    public static final int RETURN_NESTED_OBJECTS = 22;
    public static final int RETURN_ASSOCIATIONS = 23;
}
