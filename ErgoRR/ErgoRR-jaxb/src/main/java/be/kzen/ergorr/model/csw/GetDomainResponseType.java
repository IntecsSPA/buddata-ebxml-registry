
package be.kzen.ergorr.model.csw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Returns the actual values for some property. In general this is a
 *          subset of the value domain (that is, set of permissible values),
 *          although in some cases these may be the same.
 * 
 * <p>Java class for GetDomainResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDomainResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DomainValues" type="{http://www.opengis.net/cat/csw/2.0.2}DomainValuesType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDomainResponseType", propOrder = {
    "domainValues"
})
public class GetDomainResponseType {

    @XmlElement(name = "DomainValues", required = true)
    protected List<DomainValuesType> domainValues;

    /**
     * Gets the value of the domainValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainValuesType }
     * 
     * 
     */
    public List<DomainValuesType> getDomainValues() {
        if (domainValues == null) {
            domainValues = new ArrayList<DomainValuesType>();
        }
        return this.domainValues;
    }

    public boolean isSetDomainValues() {
        return ((this.domainValues!= null)&&(!this.domainValues.isEmpty()));
    }

    public void unsetDomainValues() {
        this.domainValues = null;
    }

}
