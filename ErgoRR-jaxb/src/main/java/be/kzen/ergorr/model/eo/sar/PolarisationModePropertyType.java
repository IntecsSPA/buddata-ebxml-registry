
package be.kzen.ergorr.model.eo.sar;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PolarisationModePropertyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PolarisationModePropertyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="Q"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="UNDEFINED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PolarisationModePropertyType")
@XmlEnum
public enum PolarisationModePropertyType {

    D,
    Q,
    S,
    T,
    UNDEFINED;

    public String value() {
        return name();
    }

    public static PolarisationModePropertyType fromValue(String v) {
        return valueOf(v);
    }

}
