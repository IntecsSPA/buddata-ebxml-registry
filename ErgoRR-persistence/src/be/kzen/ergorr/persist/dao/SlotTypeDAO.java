package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.geometry.GeometryTranslator;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.exceptions.TransformException;
import be.kzen.ergorr.model.gml.AbstractGeometryType;
import be.kzen.ergorr.model.rim.ExtrinsicObjectType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.AnyValueType;
import be.kzen.ergorr.model.wrs.WrsValueListType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import org.postgis.Geometry;
import org.postgis.binary.BinaryParser;
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
    private SlotValues getAnyValueByType(String value, String slotName, String slotType, String internalSlotType, String seq) throws SQLException {
        SlotValues slotValues = new SlotValues(parent.getId(), slotName, slotType);
        slotValues.seq = seq;

        value = value.trim();
        slotValues.stringValue = value; // set the string value is any case

        if (internalSlotType.equals(InternalConstants.TYPE_STRING)) {
            slotValues.specType = InternalConstants.SPEC_TYPE_RIM;
        } else {
            slotValues.specType = InternalConstants.SPEC_TYPE_WRS;
            if (internalSlotType.equals(InternalConstants.TYPE_INTEGER)) {
                slotValues.intValue = value;
            } else if (internalSlotType.equals(InternalConstants.TYPE_DATETIME)) {
                slotValues.dateTimeValue = value;
            } else if (internalSlotType.equals(InternalConstants.TYPE_DOUBLE)) {
                slotValues.doubleValue = value;
            } else if (internalSlotType.equals(InternalConstants.TYPE_BOOLEAN)) {
                slotValues.boolValue = value;
            }
        }

        return slotValues;
    }

    public Object getWrsValue(ResultSet result) throws SQLException {
        String val = result.getString(4);
        String internalSlotType = InternalSlotTypes.getInstance().getSlotType(result.getString(1));

        if (internalSlotType.equals(InternalConstants.TYPE_GEOMETRY)) {

            if (val != null && !val.equals("")) {
                BinaryParser parser = new BinaryParser();
                Geometry geometry = parser.parse(val);
                return GeometryTranslator.gmlGeometryFromGeometry(geometry);
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
//                        throw new SQLException(slot.getName() + " is not registered with an internal slotType");
                    }
                }
            }

            if (insertSlot) {
                if (slot.isSetValueList()) {
                    if (slot.getValueList().getValue() instanceof WrsValueListType) {
                        insertWrsValues((WrsValueListType) slot.getValueList().getValue(), internalSlotType, slot);
                    } else {
                        ValueListType valueList = slot.getValueList().getValue();
                        List<String> stringValues = valueList.getValue();

                        if (valueList.isSetValue() && stringValues.size() > 0) {
                            for (int j = 0; j < valueList.getValue().size(); j++) {
                                SlotValues slotValues = getAnyValueByType(stringValues.get(j), slot.getName(), slot.getSlotType(), internalSlotType, String.valueOf(j));
                                currentValues = slotValues.getValues();
                                batchStmt.addBatch(createInsertStatement());
                            }
                        } else {
                            // handle case when ValueList doesn't have Values
                            addSlotWithoutValues(slot);
                        }
                    }
                } else {
                    // handle case when slot doesn't have ValueList
                    addSlotWithoutValues(slot);
                }
            }
        }
    }

    private void addSlotWithoutValues(SlotType slot) throws SQLException {
        SlotValues slotValues = new SlotValues(parent.getId(), slot.getName(), slot.getSlotType());
        slotValues.specType = InternalConstants.SPEC_TYPE_RIM;
        slotValues.stringValue = "";
        slotValues.seq = "0";
        currentValues = slotValues.getValues();
        batchStmt.addBatch(createInsertStatement());
    }

    private void insertWrsValues(WrsValueListType wrsValueList, String internalSlotType, SlotType slot) throws SQLException {
        for (int i = 0; i < wrsValueList.getAnyValue().size(); i++) {
            AnyValueType anyVal = wrsValueList.getAnyValue().get(i);
            if (!anyVal.getContent().isEmpty()) {
                if (internalSlotType.equals(InternalConstants.TYPE_GEOMETRY)) {
                    if (anyVal.getContent().get(0) instanceof JAXBElement) {
                        JAXBElement anyValEl = (JAXBElement) anyVal.getContent().get(0);

                        if (anyValEl.getValue() instanceof AbstractGeometryType) {
                            AbstractGeometryType gmlGeometry = (AbstractGeometryType) anyValEl.getValue();
                            int defaultSrsId = CommonProperties.getInstance().getInt("db.defaultSrsId");
                            Geometry geometry = null;

                            try {
                                geometry = GeometryTranslator.geometryFromGmlGeometry(gmlGeometry);
                            } catch (TransformException ex) {
                                throw new SQLException(ex.toString());
                            }

                            BinaryWriter binaryWriter = new BinaryWriter();
                            SlotValues slotValues = new SlotValues(parent.getId(), slot.getName(), slot.getSlotType());
                            slotValues.seq = String.valueOf(i);
                            slotValues.specType = InternalConstants.SPEC_TYPE_WRS;
                            String geomHex = binaryWriter.writeHexed(geometry);

                            slotValues.stringValue = geomHex;
                            if (geometry.getSrid() == defaultSrsId) {
                                slotValues.geometryValue = "'" + slotValues.stringValue + "'";
                            } else {
                                slotValues.geometryValue = "transform('" + slotValues.stringValue + "'," + defaultSrsId + ")";
                            }

                            currentValues = slotValues.getValues();
                            batchStmt.addBatch(createInsertStatement());
                        } else {
                            throw new SQLException("Slot " + slot.getName() + " does not have a geometry value");
                        }
                    } else {
                        throw new SQLException("Slot " + slot.getName() + " does not have a valid XML value");
                    }
                } else {
                    String val = anyVal.getContent().get(0).toString().trim();
                    SlotValues slotValues = getAnyValueByType(val, slot.getName(), slot.getSlotType(), internalSlotType, String.valueOf(i));
                    currentValues = slotValues.getValues();
                    batchStmt.addBatch(createInsertStatement());
                }
            }
        }
    }

    /**
     * Helper class to store slot values
     */
    private class SlotValues {

        public String seq = "";
        public String parent = "";
        public String slotName = "";
        public String slotType = null;
        public String specType = InternalConstants.SPEC_TYPE_RIM;
        public String stringValue = null;
        public String boolValue = "null";
        public String dateTimeValue = "null";
        public String doubleValue = "null";
        public String intValue = "null";
        public String geometryValue = "null";

        public SlotValues(String parent, String slotName, String slotType) {
            this.parent = parent;
            this.slotName = slotName;
            this.slotType = (slotType != null) ? slotType : null;
        }

        public String getValues() {
            StringBuilder sql = new StringBuilder();
            sql.append(seq).append(",");
            appendStringValue(parent, sql);
            sql.append(",");
            appendStringValue(slotName, sql);
            sql.append(",");
            appendStringValue(slotType, sql);
            sql.append(",");
            appendStringValue(specType, sql);
            sql.append(",");
            appendStringValue(stringValue, sql);
            sql.append(",");
            // TODO: check if boolean is handled correctly
            sql.append(boolValue).append(",");
            sql.append((dateTimeValue.equals("null") ? dateTimeValue : "'" + dateTimeValue + "'")).append(",");
//            sql.append(dateTimeValue).append(",");
            sql.append(doubleValue).append(",");
            sql.append(intValue).append(",");
            sql.append(geometryValue);
            return sql.toString();
        }
    }
}
