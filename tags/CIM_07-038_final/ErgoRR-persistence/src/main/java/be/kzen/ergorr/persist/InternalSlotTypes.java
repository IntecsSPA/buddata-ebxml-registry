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
    private static final String[] slotTypes = new String[] {"string", "geometry", "datetime", "double", "int", "boolean"};


    public InternalSlotTypes() {
        slotMap = new ConcurrentHashMap<String, String>();
    }

    /**
     * Get Singleton instance of InternalSlotTypes.
     *
     * @return Singleton instance.
     */
    public static synchronized InternalSlotTypes getInstance() {
        if (instance == null) {
            instance = new InternalSlotTypes();
        }

        return instance;
    }

    /**
     * Check if the Singleton instance is initialized.
     *
     * @return True if initialized.
     */
    public static boolean isInitialized() {
        return (instance != null);
    }

    /**
     * Get the type of the slot with a specific name {@code slotName}.
     *
     * @param slotName Name of the slot.
     * @return Type of the slot.
     */
    public String getSlotType(String slotName) {
        return slotMap.get(slotName);
    }

    /**
     * Put a slot name, type pair.
     *
     * @param name Name of slot.
     * @param slotType Type of slot.
     * @throws Exception
     */
    public void putSlot(String name, String slotType) throws Exception {
        slotMap.put(name, slotType);
    }

    /**
     * Remove slot by slot name.
     *
     * @param name Name of slot.
     */
    public void removeSlot(String name) {
        slotMap.remove(name);
    }

    /**
     * Total size of slots.
     *
     * @return Size of slots.
     */
    public int getSlotTypeSize() {
        return slotMap.size();
    }

    /**
     * Load slots to {@code slotMap} from database.
     *
     * @param persist Persistence instance to use to fetch slots.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void loadSlots(SqlPersistence persist) throws ClassNotFoundException, SQLException {
        String sql = "select * from t_extrinsicobject where objecttype='" + RIMConstants.CN_OBJ_DEF + "'";
        List<JAXBElement<ExtrinsicObjectType>> eoEls =
                persist.query(sql, new ArrayList<Object>(), OFactory.getXmlClassByElementName("ExtrinsicObject"));

        for (JAXBElement<ExtrinsicObjectType> eoEl : eoEls) {
            for (SlotType slot : eoEl.getValue().getSlot()) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "slot in cache: " + slot.getName() + " > " + slot.getSlotType());
                }
                slotMap.put(slot.getName(), slot.getSlotType());
            }
        }
    }

    /**
     * Load slots to {@code slotMap} from database with a default {@code SQLPersistence}.
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void loadSlots() throws SQLException, ClassNotFoundException {
        loadSlots(new SqlPersistence());
    }

    /**
     * Check if slot type {@code slotType} is an internal slot type.
     *
     * @param slotType Slot type to check.
     * @return True if {@code slotType} is an internal slot type.
     */
    public static boolean isInternalSlotType(String slotType) {

        if (slotType != null) {
            for (String type : slotTypes) {
                if (slotType.equals(type)) {
                    return true;
                }
            }
        }

        return false;
    }
}
