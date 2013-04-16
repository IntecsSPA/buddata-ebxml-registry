
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComparisonOperatorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ComparisonOperatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LessThan"/>
 *     &lt;enumeration value="GreaterThan"/>
 *     &lt;enumeration value="LessThanEqualTo"/>
 *     &lt;enumeration value="GreaterThanEqualTo"/>
 *     &lt;enumeration value="EqualTo"/>
 *     &lt;enumeration value="NotEqualTo"/>
 *     &lt;enumeration value="Like"/>
 *     &lt;enumeration value="Between"/>
 *     &lt;enumeration value="NullCheck"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ComparisonOperatorType")
@XmlEnum
public enum ComparisonOperatorType {

    @XmlEnumValue("LessThan")
    LESS_THAN("LessThan"),
    @XmlEnumValue("GreaterThan")
    GREATER_THAN("GreaterThan"),
    @XmlEnumValue("LessThanEqualTo")
    LESS_THAN_EQUAL_TO("LessThanEqualTo"),
    @XmlEnumValue("GreaterThanEqualTo")
    GREATER_THAN_EQUAL_TO("GreaterThanEqualTo"),
    @XmlEnumValue("EqualTo")
    EQUAL_TO("EqualTo"),
    @XmlEnumValue("NotEqualTo")
    NOT_EQUAL_TO("NotEqualTo"),
    @XmlEnumValue("Like")
    LIKE("Like"),
    @XmlEnumValue("Between")
    BETWEEN("Between"),
    @XmlEnumValue("NullCheck")
    NULL_CHECK("NullCheck");
    private final String value;

    ComparisonOperatorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComparisonOperatorType fromValue(String v) {
        for (ComparisonOperatorType c: ComparisonOperatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
