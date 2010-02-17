
package be.kzen.ergorr.model.csw;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ResultType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="results"/>
 *     &lt;enumeration value="hits"/>
 *     &lt;enumeration value="validate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ResultType")
@XmlEnum
public enum ResultType {


    /**
     * Include results in the response.
     * 
     */
    @XmlEnumValue("results")
    RESULTS("results"),

    /**
     * Provide a result set summary, but no results.
     * 
     */
    @XmlEnumValue("hits")
    HITS("hits"),

    /**
     * Validate the request and return an Acknowledgement message if it 
     * 	      is valid. Continue processing the request asynchronously.
     * 
     */
    @XmlEnumValue("validate")
    VALIDATE("validate");
    private final String value;

    ResultType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ResultType fromValue(String v) {
        for (ResultType c: ResultType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
