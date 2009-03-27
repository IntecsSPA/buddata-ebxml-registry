package be.kzen.ergorr.commons;

import javax.xml.datatype.DatatypeFactory;

/**
 *
 * @author yaman
 */
public enum SlotTypeEnum {
    STRING, BOOLEAN, DOUBLE, INT, DATETIME, GEOMETRY;

    public static SlotTypeEnum getByName(String name) {
        name = name.toUpperCase();
        return SlotTypeEnum.valueOf(name);
    }

    public static SlotTypeEnum findType(String val) {

        try {
            Double.valueOf(val);
            return SlotTypeEnum.DOUBLE;
        } catch (Exception ex) {}

        try {
            DatatypeFactory.newInstance().newXMLGregorianCalendar(val);
            return SlotTypeEnum.DATETIME;
        } catch (Exception ex) {}

//        try {
//            Boolean.valueOf(val);
//            return SlotTypeEnum.BOOLEAN;
//        } catch (Exception ex) {}

        return SlotTypeEnum.STRING;
    }
}
