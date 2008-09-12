/*
 * Project: Buddata ebXML RegRep
 * Class: SlotTypes.java
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores slot type names which are not string values.
 * 
 * @author Yaman Ustuntas
 */
public class SlotTypes {

    private static SlotTypes instance;
    private Map<String, String> slotMap;
    private static String[] nonStringTypes;
    

    static {
        nonStringTypes = CommonProperties.getInstance().getStringArray("rim.nonStringSlotTypes");
    }

    public SlotTypes() {
        slotMap = new ConcurrentHashMap<String, String>();
    }

    public static synchronized SlotTypes getInstance() {
        if (instance == null) {
            instance = new SlotTypes();
        }

        return instance;
    }

    public void setSlotMap(Map<String, String> slotMap) {
        this.slotMap.clear();
        this.slotMap.putAll(slotMap);
    }

    public String getSlotType(String slotName) {
        return slotMap.get(slotName);
    }

    public void putSlotType(String slotName, String slotType) throws Exception {
        String st = "string";

        if (slotType != null) {
            slotType = slotType.toLowerCase();

            if (isNoneStringType(slotType)) {
                st = slotType;
            }
        }

        String prevType = slotMap.get(slotName);

        if (prevType == null) {
            slotMap.put(slotName, st);
        } else if (!prevType.equals(st)) {
            throw new Exception("Slot type mismatch: Slot " + slotName + " was registered as " + prevType + " but the current request defines it as " + slotType);
        }
    }

    public int getSlotTypeSize() {
        return slotMap.size();
    }

    public static String getInternalSlotType(String slotType) {

        if (slotType != null) {
            slotType = slotType.toLowerCase();

            if (slotType.equals("string") || slotType.equals("double") || slotType.equals("int") || slotType.equals("geometry")) {
                return slotType;
            }
        }
        
        return "string";
    }

    public static boolean isNoneStringType(String slotType) {
        for (String s : nonStringTypes) {
            if (slotType.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
