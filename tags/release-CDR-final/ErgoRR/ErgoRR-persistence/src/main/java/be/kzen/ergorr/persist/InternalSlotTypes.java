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
    private static String[] slotTypes;


    static {
        slotTypes = CommonProperties.getInstance().getStringArray("rim.internalSlotTypes");
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

    public String getSlotType(String slotName) {
        return slotMap.get(slotName);
    }

    public void putSlot(String name, String slotType) throws Exception {
        slotMap.put(name, slotType);
    }

    public void removeSlot(String name) {
        slotMap.remove(name);
    }

    public int getSlotTypeSize() {
        return slotMap.size();
    }

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

    public void loadSlots() throws SQLException, ClassNotFoundException {
        loadSlots(new SqlPersistence());
    }

    public static boolean isInternalSlotType(String slotType) {
        boolean isInternal = false;

        if (slotType != null) {
            for (String type : slotTypes) {
                if (slotType.equals(type)) {
                    isInternal = true;
                    break;
                }
            }
        }

        return isInternal;
    }
}
