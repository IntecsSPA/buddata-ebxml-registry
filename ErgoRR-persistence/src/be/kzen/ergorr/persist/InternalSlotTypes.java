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
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * Stores slot type names which are not string values.
 * 
 * @author Yaman Ustuntas
 */
public class InternalSlotTypes {
    private static Logger logger = Logger.getLogger(InternalSlotTypes.class.getName());
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
        String type = slotMap.get(slotName);

        if (type == null) {
            type = InternalConstants.TYPE_STRING;
        }

        return type;
    }

    public void putTestSlot(String name, String type) {
        name = name.toLowerCase();
        String st = InternalConstants.TYPE_STRING;

        if (type != null) {
            type = type.toLowerCase();

            if (isNoneStringType(type)) {
                st = type;
            }
        }
        slotMap.put(name, st);
    }

    public void putSlot(String name, String type) throws Exception {
        name = name.toLowerCase();
        String st = InternalConstants.TYPE_STRING;

        if (type != null) {
            type = type.toLowerCase();

            if (isNoneStringType(type)) {
                st = type;
            }
        }

        String prevType = slotMap.get(name);

        if (prevType == null && !st.equals(InternalConstants.TYPE_STRING)) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "adding slot in cache: " + name + " > " + st);
            }

            slotMap.put(name, st);
        } else if (prevType != null && !prevType.equals(st)) {
            String err = "Slot type mismatch: Slot " + name + " was registered as " + prevType + " but the current request defines it as " + type;
            logger.log(Level.SEVERE, err);
            throw new Exception(err);
        }
    }

    public void remoteSlot(String name) {
        slotMap.remove(name);
    }

    public int getSlotTypeSize() {
        return slotMap.size();
    }

    public void clear() {
        slotMap.clear();
    }

    public void loadSlots(SqlPersistence persist) throws ClassNotFoundException, SQLException {
        String sql = "select * from t_extrinsicobject where objecttype='" + RIMConstants.CN_OBJ_DEF + "'";
        List<JAXBElement<ExtrinsicObjectType>> eoEls =
                persist.query(sql, new ArrayList<Object>(), OFactory.getXmlClassByElementName("ExtrinsicObject"));

        for (JAXBElement<ExtrinsicObjectType> eoEl : eoEls) {
            for (SlotType slot : eoEl.getValue().getSlot()) {
                if (isNoneStringType(slot.getSlotType())) {
                    if (logger.isLoggable(Level.FINE)) {
                        logger.log(Level.FINE, "slot in cache: " + slot.getName() + " > " + slot.getSlotType());
                    }
                    slotMap.put(slot.getName(), slot.getSlotType());
                }
            }
        }
    }

    public void loadSlots() throws SQLException, ClassNotFoundException {
        loadSlots(new SqlPersistence());
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
