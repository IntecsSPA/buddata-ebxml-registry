
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SensorTypePropertyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SensorTypePropertyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALTIMETRIC"/>
 *     &lt;enumeration value="ATMOSPHERIC"/>
 *     &lt;enumeration value="OPTICAL"/>
 *     &lt;enumeration value="RADAR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SensorTypePropertyType")
@XmlEnum
public enum SensorTypePropertyType {

    ALTIMETRIC,
    ATMOSPHERIC,
    OPTICAL,
    RADAR;

    public String value() {
        return name();
    }

    public static SensorTypePropertyType fromValue(String v) {
        return valueOf(v);
    }

}
