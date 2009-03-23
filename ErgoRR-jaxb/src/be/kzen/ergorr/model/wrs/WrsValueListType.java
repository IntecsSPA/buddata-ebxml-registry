
package be.kzen.ergorr.model.wrs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.rim.ValueListType;


/**
 * Allows complex slot values.
 *             
 * 
 * <p>Java class for ValueListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueListType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}ValueListType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.opengis.net/cat/wrs/1.0}AnyValue"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueListType", propOrder = {
    "anyValue"
})
public class WrsValueListType
    extends ValueListType
{

    @XmlElement(name = "AnyValue")
    protected List<AnyValueType> anyValue;

    /**
     * Gets the value of the anyValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anyValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnyValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnyValueType }
     * 
     * 
     */
    public List<AnyValueType> getAnyValue() {
        if (anyValue == null) {
            anyValue = new ArrayList<AnyValueType>();
        }
        return this.anyValue;
    }

    public boolean isSetAnyValue() {
        return ((this.anyValue!= null)&&(!this.anyValue.isEmpty()));
    }

    public void unsetAnyValue() {
        this.anyValue = null;
    }

}
