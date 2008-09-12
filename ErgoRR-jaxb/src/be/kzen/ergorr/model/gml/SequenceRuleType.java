
package be.kzen.ergorr.model.gml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * The gml:SequenceRuleType is derived from the gml:SequenceRuleEnumeration through the addition of an axisOrder attribute.  The gml:SequenceRuleEnumeration is an enumerated type. The rule names are defined in ISO 19123. If no rule name is specified the default is "Linear".
 * 
 * <p>Java class for SequenceRuleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SequenceRuleType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.opengis.net/gml/3.2>SequenceRuleEnumeration">
 *       &lt;attribute name="axisOrder" type="{http://www.opengis.net/gml/3.2}AxisDirectionList" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SequenceRuleType", propOrder = {
    "value"
})
public class SequenceRuleType {

    @XmlValue
    protected SequenceRuleEnumeration value;
    @XmlAttribute
    protected List<String> axisOrder;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link SequenceRuleEnumeration }
     *     
     */
    public SequenceRuleEnumeration getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceRuleEnumeration }
     *     
     */
    public void setValue(SequenceRuleEnumeration value) {
        this.value = value;
    }

    public boolean isSetValue() {
        return (this.value!= null);
    }

    /**
     * Gets the value of the axisOrder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the axisOrder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAxisOrder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAxisOrder() {
        if (axisOrder == null) {
            axisOrder = new ArrayList<String>();
        }
        return this.axisOrder;
    }

    public boolean isSetAxisOrder() {
        return ((this.axisOrder!= null)&&(!this.axisOrder.isEmpty()));
    }

    public void unsetAxisOrder() {
        this.axisOrder = null;
    }

}
