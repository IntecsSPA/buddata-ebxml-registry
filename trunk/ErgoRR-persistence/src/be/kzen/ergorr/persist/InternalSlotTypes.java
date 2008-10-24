/*
 * Project: Buddata ebXML RegRep
 * Class: InternalSlotTypes.java
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
package be.kzen.ergorr.persist;

import be.kzen.ergorr.commons.*;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores slot type names which are not string values.
 * 
 * @author Yaman Ustuntas
 */
public class InternalSlotTypes {

    private static InternalSlotTypes instance;
    private Map<String, String> slotMap;
    private static String[] nonStringTypes;
    

    static {
        nonStringTypes = CommonProperties.getInstance().getStringArray("rim.nonStringSlotTypes");
    }

    public InternalSlotTypes() {
        slotMap = new ConcurrentHashMap<String, String>();
    }

    public static synchronized InternalSlotTypes getInstance() {
        if (instance == null) {
            instance = new InternalSlotTypes();
        }

        return instance;
    }

    public void setSlotMap(Map<String, String> slotMap) {
        this.slotMap.clear();
        this.slotMap.putAll(slotMap);
    }

    public String getSlotType(String slotName) {
        slotName = slotName.toLowerCase();
        return slotMap.get(slotName);
    }

    public void putSlotType(String slotName, String slotType) throws Exception {
        slotName = slotName.toLowerCase();
        String st = InternalConstants.TYPE_STRING;

        if (slotType != null) {
            slotType = slotType.toLowerCase();

            if (isNoneStringType(slotType)) {
                st = slotType;
            }
        }

        String prevType = slotMap.get(slotName);

        if (prevType == null) {
            slotMap.put(slotName, st);

            if (!st.equals(InternalConstants.TYPE_STRING)) {
                SqlPersistence service = new SqlPersistence();
                service.insertSlotType(slotName, st);
            }
        } else if (!prevType.equals(st)) {
            throw new Exception("Slot type mismatch: Slot " + slotName + " was registered as " + prevType + " but the current request defines it as " + slotType);
        }
    }

    public int getSlotTypeSize() {
        return slotMap.size();
    }

    public void clear() {
        slotMap.clear();
    }

    /**
     * TODO: Read internal slot types from config file.
     * 
     * @param slotType
     * @return
     */
    public static String getInternalSlotType(String slotType) {

        if (slotType != null) {
            slotType = slotType.toLowerCase();

            if (slotType.equals("string") || slotType.equals("datetime") || slotType.equals("double") || slotType.equals("int") || slotType.equals("geometry") || slotType.equals("boolean")) {
                return slotType;
            }
        }

        return "string";
    }

    public static boolean isNoneStringType(String slotType) {
        slotType = slotType.toLowerCase();

        for (String s : nonStringTypes) {
            if (slotType.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
