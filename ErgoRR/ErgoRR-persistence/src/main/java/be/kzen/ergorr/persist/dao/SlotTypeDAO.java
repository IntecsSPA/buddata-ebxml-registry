package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.DateUtil;
import be.kzen.ergorr.geometry.GeometryTranslator;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.geometry.GmlWktParser;
import be.kzen.ergorr.geometry.GmlWktWriter;
import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.gml.EnvelopeType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.AnyValueType;
import be.kzen.ergorr.model.wrs.WrsValueListType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.postgis.Geometry;
import org.postgis.binary.BinaryWriter;

/**
 *
 * @author Yaman Ustuntas
 */
public class SlotTypeDAO extends GenericComposedObjectDAO<SlotType, IdentifiableType> {

    private static Logger logger = Logger.getLogger(SlotTypeDAO.class.getName());

    public SlotTypeDAO(IdentifiableType parent) {
        super(parent);
    }

    /**
     * This method covers the problem that Double, Int, DateTime can be
     * submitted as rim:Value although rim:Value is a string.
     * 
     * @param value String representation of slot value.
     * @param slotType Slot type.
     * @throws be.kzen.ergorr.exceptions.MappingException
     */
    private SlotValues getAnyValueByType(String value, String slotName, String slotType, String internalSlotType, String specType, int seq) throws SQLException {
        SlotValues slotValues = new SlotValues(parent.getId(), slotName, slotType);
        slotValues.seq = seq;
        slotValues.specType = specType;
        value = value.trim();
        slotValues.stringValue = value; // set the string value is any case

        if (!internalSlotType.equals(InternalConstants.TYPE_STRING)) {
            if (!value.equals("")) {
                if (internalSlotType.equals(InternalConstants.TYPE_INTEGER)) {
                    try {
                        // first to double in case int value passed as 1.0
                        // Integer.valueOf can't handle that.
                        double d = Double.valueOf(value);
                        slotValues.intValue = (int) d;
                    } catch (NumberFormatException ex) {
                        throw new SQLException("Could not cast Slot " + slotName + " integer value: " + value);
                    }
                } else if (internalSlotType.equals(InternalConstants.TYPE_DATETIME)) {
                    slotValues.dateTimeValue = DateUtil.getCalendar(value);
                } else if (internalSlotType.equals(InternalConstants.TYPE_DOUBLE)) {
                    try {
                        slotValues.doubleValue = Double.valueOf(value);
                    } catch (NumberFormatException ex) {
                        throw new SQLException("Could not cast Slot " + slotName + " double value: " + value);
                    }
                } else if (internalSlotType.equals(InternalConstants.TYPE_BOOLEAN)) {
                    String boolVal = value.toLowerCase();

                    if (boolVal.equals("true") || boolVal.equals("false")) {
                        slotValues.boolValue = Boolean.valueOf(value);
                    } else {
                        throw new SQLException("Could not cast Slot " + slotName + " bool value: " + value);
                    }
                }
            }
        }

        return slotValues;
    }

    public Object getWrsValue(ResultSet result) throws SQLException {
        String val = result.getString(4);
        String internalSlotType = InternalSlotTypes.getInstance().getSlotType(result.getString(1));

        if (internalSlotType.equals(InternalConstants.TYPE_GEOMETRY)) {

            if (val != null && !val.equals("")) {
                GmlWktParser parser = new GmlWktParser(val);
                return parser.parse();
            } else {
                return null;
            }
        } else {
            return val;
        }
    }

    @Override
    public String getTableName() {
        return "t_slot";
    }

    @Override
    public String getParamList() {
        return "seq,parent,name_,slottype,spectype,stringvalue,boolvalue,datetimevalue,doublevalue,intvalue,geometryvalue";
    }

    public String getQueryParamList() {
        return "name_,slottype,spectype,stringvalue";
    }

    @Override
    protected String getPlaceHolders() {
        return "?,?,?,?,?,?,?,?,?,?,transform(?," + CommonProperties.getInstance().get("db.defaultSrsId") + ")";
    }

    @Override
    public void addComposedObjects() throws SQLException {
        Map<String, SlotType> slotMap = new HashMap<String, SlotType>();
        Statement stmt = connection.createStatement();

        StringBuilder sql = new StringBuilder(200);
        sql.append("select ").append(getQueryParamList()).append(" from t_slot where parent='").append(parent.getId()).append("'");

        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            String name = result.getString(1);

            SlotType slot = slotMap.get(name);

            if (slot == null) {
                slot = new SlotType();
                slot.setName(name);
                slot.setSlotType(result.getString(2));
                String specType = result.getString(3);
                if (specType.equals(InternalConstants.SPEC_TYPE_RIM)) {
                    ValueListType valList = new ValueListType();
                    slot.setValueList(OFactory.rim.createValueList(valList));
                } else {
                    WrsValueListType wrsValList = new WrsValueListType();
                    slot.setValueList(OFactory.wrs.createValueList(wrsValList));
                }

                slotMap.put(name, slot);
            }

            if (slot.getValueList().getValue() instanceof WrsValueListType) {
                WrsValueListType wrsValList = (WrsValueListType) slot.getValueList().getValue();
                AnyValueType anyVal = new AnyValueType();
                Object val = getWrsValue(result);
                if (val != null) {
                    anyVal.getContent().add(val);
                }
                wrsValList.getAnyValue().add(anyVal);
            } else {
                String stringValue = result.getString(4);

                if (stringValue != null && !stringValue.equals("")) {
                    slot.getValueList().getValue().getValue().add(result.getString(4));
                }
            }
        }

        parent.getSlot().addAll(slotMap.values());
    }

    @Override
    public void insert() throws SQLException {
        PreparedStatement stmt = connection.prepareCall(createInsertStatement());

        List<SlotType> slots = parent.getSlot();

        for (SlotType slot : slots) {
            String internalSlotType = InternalSlotTypes.getInstance().getSlotType(slot.getName());
            boolean insertSlot = true;

            if (internalSlotType == null) {
                // Check if it is object definition ExtrinsicObject
                if (parent instanceof ExtrinsicObjectType) {
                    ExtrinsicObjectType eo = (ExtrinsicObjectType) parent;
                    if (eo.getObjectType() == null || !eo.getObjectType().equals(RIMConstants.CN_OBJ_DEF) || !InternalSlotTypes.isInternalSlotType(slot.getSlotType())) {
                        insertSlot = false;
                        logger.log(Level.WARNING, "Skipping slot " + slot.getName() + " of object " + eo.getId() + " because slot is not registered");
                    }
                }
                internalSlotType = InternalConstants.TYPE_STRING;
            }

            if (insertSlot) {
                if (slot.isSetValueList()) {
                    if (slot.getValueList().getValue() instanceof WrsValueListType) {
                        insertWrsValues((WrsValueListType) slot.getValueList().getValue(), internalSlotType, slot, stmt);
                    } else {
                        ValueListType valueList = slot.getValueList().getValue();
                        List<String> stringValues = valueList.getValue();

                        if (valueList.isSetValue() && stringValues.size() > 0) {
                            for (int j = 0; j < valueList.getValue().size(); j++) {
                                SlotValues slotValues = getAnyValueByType(stringValues.get(j), slot.getName(), slot.getSlotType(), internalSlotType, InternalConstants.SPEC_TYPE_RIM, j);
                                slotValues.loadValues(stmt);
                                stmt.addBatch();
                            }
                        } else {
                            // handle case when ValueList doesn't have Values
                            addSlotWithoutValues(slot, stmt);
                        }
                    }
                } else {
                    // handle case when slot doesn't have ValueList
                    addSlotWithoutValues(slot, stmt);
                }
            }
        }
        
        stmt.executeBatch();
    }

    private void addSlotWithoutValues(SlotType slot, PreparedStatement stmt) throws SQLException {
        SlotValues slotValues = new SlotValues(parent.getId(), slot.getName(), slot.getSlotType());
        slotValues.specType = InternalConstants.SPEC_TYPE_RIM;
        slotValues.stringValue = "";
        slotValues.seq = 0;
        slotValues.loadValues(stmt);
        stmt.addBatch();
    }

    private void insertWrsValues(WrsValueListType wrsValueList, String internalSlotType, SlotType slot, PreparedStatement stmt) throws SQLException {
        for (int i = 0; i < wrsValueList.getAnyValue().size(); i++) {
            AnyValueType anyVal = wrsValueList.getAnyValue().get(i);

            if (!anyVal.getContent().isEmpty()) {
                if (internalSlotType.equals(InternalConstants.TYPE_GEOMETRY)) {
                    if (anyVal.getContent().get(0) instanceof JAXBElement) {
                        JAXBElement anyValEl = (JAXBElement) anyVal.getContent().get(0);
                        Object gmlGeometry = anyValEl.getValue();

                        if (gmlGeometry instanceof AbstractGeometryType || gmlGeometry instanceof EnvelopeType) {
                            Geometry geometry = null;

                            try {
                                geometry = GeometryTranslator.geometryFromGmlGeometry(gmlGeometry);
                            } catch (TransformException ex) {
                                throw new SQLException(ex.toString());
                            }

                            SlotValues slotValues = new SlotValues(parent.getId(), slot.getName(), slot.getSlotType());
                            slotValues.seq = i;
                            slotValues.specType = InternalConstants.SPEC_TYPE_WRS;

                            GmlWktWriter writer = new GmlWktWriter(gmlGeometry);

                            try {
                                slotValues.stringValue = writer.write();
                            } catch (TransformException ex) {
                                throw new SQLException("Could not format geometry: " + ex.getMessage());
                            }
                            slotValues.geometryValue = geometry;

                            slotValues.loadValues(stmt);
                            stmt.addBatch();
                        } else {
                            throw new SQLException("Slot " + slot.getName() + " does not have a geometry value");
                        }
                    } else {
                        throw new SQLException("Slot " + slot.getName() + " does not have a valid XML value");
                    }
                } else {
                    String val = anyVal.getContent().get(0).toString();
                    SlotValues slotValues = getAnyValueByType(val, slot.getName(), slot.getSlotType(), internalSlotType, InternalConstants.SPEC_TYPE_WRS, i);
                    slotValues.loadValues(stmt);
                    stmt.addBatch();
                }
            }
        }
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Helper class to store slot values
     */
    private class SlotValues {

        public int seq = 0;
        public String parent = "";
        public String slotName = "";
        public String slotType = null;
        public String specType = InternalConstants.SPEC_TYPE_RIM;
        public String stringValue = null;
        public boolean boolValue = false;
        public Calendar dateTimeValue = null;
        public Double doubleValue = null;
        public Integer intValue = null;
        public Geometry geometryValue = null;

        public SlotValues(String parent, String slotName, String slotType) {
            this.parent = parent;
            this.slotName = slotName;
            this.slotType = (slotType != null) ? slotType : null;
        }

        public void loadValues(PreparedStatement stmt) throws SQLException {
            stmt.setInt(1, seq);
            stmt.setString(2, parent);
            stmt.setString(3, slotName);
            stmt.setString(4, slotType);
            stmt.setString(5, specType);
            stmt.setString(6, stringValue);
            stmt.setBoolean(7, boolValue);

            if (dateTimeValue != null) {
                stmt.setTimestamp(8, new Timestamp(dateTimeValue.getTimeInMillis()), dateTimeValue);
            } else {
                stmt.setNull(8, Types.TIMESTAMP);
            }

            if (doubleValue != null) {
                stmt.setDouble(9, doubleValue);
            } else {
                stmt.setNull(9, Types.DOUBLE);
            }

            if (intValue != null) {
                stmt.setInt(10, intValue);
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            if (geometryValue != null) {
                stmt.setBytes(11, new BinaryWriter().writeBinary(geometryValue));
            } else {
                stmt.setBytes(11, null);
            }
        }
    }
}
