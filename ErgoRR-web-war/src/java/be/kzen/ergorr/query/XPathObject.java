/*
 * Project: Buddata ebXML RegRep
 * Class: XPathObject.java
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
package be.kzen.ergorr.query;

import be.kzen.ergorr.exceptions.QueryException;
import be.kzen.ergorr.commons.CommonFunctions;
import java.util.logging.Logger;

/**
 * Helper for the XPath within the OGC query.
 * Extracts the queried object and parameters from the XPath
 * to be used in the translated SQL query.
 * 
 * @author Yaman Ustuntas
 */
public class XPathObject {

    private static Logger log = Logger.getLogger(XPathObject.class.getName());
    private String xpath;
    private String objectType;
    private String objectAttribute;
    private boolean isSlotQuery;
    private String slotName;
    private String slotType;

    public XPathObject(String xpath) {
        this.xpath = xpath;
        isSlotQuery = false;
    }

    public void process() throws QueryException {

        if (xpath.startsWith("/")) {
            xpath = xpath.substring(1);
        }
        String[] paths = xpath.split("/");
        
        objectType = paths[0];


        if (objectType.startsWith("$")) {
            objectType = objectType.substring(1);
        } else {
            objectType = CommonFunctions.removePrefix(objectType);
        }

        if (paths.length <= 2) {
            if (paths[1].startsWith("@")) {
                objectAttribute = paths[1].substring(1);
            } else {
                throw new QueryException("Invalid xpath: " + xpath);
            }
        } else {
            isSlotQuery = true;
            String p2 = paths[1];

            int nameStartIdx = p2.indexOf("@") + 1;
            int nameEndIdx = p2.indexOf("=");

            objectAttribute = p2.substring(nameStartIdx, nameEndIdx);

            int valEndIdx = p2.lastIndexOf("\"");

            slotName = p2.substring(nameEndIdx + 2, valEndIdx);
        }
    }

    public boolean isSlotQuery() {
        return isSlotQuery;
    }

    public void setIsSlotQuery(boolean isSlotQuery) {
        this.isSlotQuery = isSlotQuery;
    }

    public String getObjectAttribute() {
        return objectAttribute;
    }

    public void setObjectAttribute(String objectAttribute) {
        this.objectAttribute = objectAttribute;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }
}
