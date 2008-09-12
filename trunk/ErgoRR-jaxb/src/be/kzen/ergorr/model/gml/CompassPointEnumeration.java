
package be.kzen.ergorr.model.gml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompassPointEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CompassPointEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="NNE"/>
 *     &lt;enumeration value="NE"/>
 *     &lt;enumeration value="ENE"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="ESE"/>
 *     &lt;enumeration value="SE"/>
 *     &lt;enumeration value="SSE"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="SSW"/>
 *     &lt;enumeration value="SW"/>
 *     &lt;enumeration value="WSW"/>
 *     &lt;enumeration value="W"/>
 *     &lt;enumeration value="WNW"/>
 *     &lt;enumeration value="NW"/>
 *     &lt;enumeration value="NNW"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CompassPointEnumeration")
@XmlEnum
public enum CompassPointEnumeration {

    N,
    NNE,
    NE,
    ENE,
    E,
    ESE,
    SE,
    SSE,
    S,
    SSW,
    SW,
    WSW,
    W,
    WNW,
    NW,
    NNW;

    public String value() {
        return name();
    }

    public static CompassPointEnumeration fromValue(String v) {
        return valueOf(v);
    }

}
