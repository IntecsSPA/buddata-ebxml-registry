
package be.kzen.ergorr.model.eo.sar;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PolarisationChannelsPropertyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PolarisationChannelsPropertyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HH"/>
 *     &lt;enumeration value="HV"/>
 *     &lt;enumeration value="VH"/>
 *     &lt;enumeration value="VV"/>
 *     &lt;enumeration value="HH, VV"/>
 *     &lt;enumeration value="HH, VH"/>
 *     &lt;enumeration value="HH, HV"/>
 *     &lt;enumeration value="VH, VV"/>
 *     &lt;enumeration value="VH, HV"/>
 *     &lt;enumeration value="VV, HV"/>
 *     &lt;enumeration value="HH, VV, HV, VH"/>
 *     &lt;enumeration value="UNDEFINED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PolarisationChannelsPropertyType")
@XmlEnum
public enum PolarisationChannelsPropertyType {

    HH("HH"),
    HV("HV"),
    VH("VH"),
    VV("VV"),
    @XmlEnumValue("HH, VV")
    HH_VV("HH, VV"),
    @XmlEnumValue("HH, VH")
    HH_VH("HH, VH"),
    @XmlEnumValue("HH, HV")
    HH_HV("HH, HV"),
    @XmlEnumValue("VH, VV")
    VH_VV("VH, VV"),
    @XmlEnumValue("VH, HV")
    VH_HV("VH, HV"),
    @XmlEnumValue("VV, HV")
    VV_HV("VV, HV"),
    @XmlEnumValue("HH, VV, HV, VH")
    HH_VV_HV_VH("HH, VV, HV, VH"),
    UNDEFINED("UNDEFINED");
    private final String value;

    PolarisationChannelsPropertyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PolarisationChannelsPropertyType fromValue(String v) {
        for (PolarisationChannelsPropertyType c: PolarisationChannelsPropertyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
