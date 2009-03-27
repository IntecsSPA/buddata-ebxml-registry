
package be.kzen.ergorr.model.ows;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Unordered list of one or more groups of references to remote and/or local resources. 
 * 
 * <p>Java class for ManifestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/ows/1.1}BasicIdentificationType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/ows/1.1}ReferenceGroup" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestType", propOrder = {
    "referenceGroup"
})
public class ManifestType
    extends BasicIdentificationType
{

    @XmlElement(name = "ReferenceGroup", required = true)
    protected List<ReferenceGroupType> referenceGroup;

    /**
     * Gets the value of the referenceGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceGroupType }
     * 
     * 
     */
    public List<ReferenceGroupType> getReferenceGroup() {
        if (referenceGroup == null) {
            referenceGroup = new ArrayList<ReferenceGroupType>();
        }
        return this.referenceGroup;
    }

    public boolean isSetReferenceGroup() {
        return ((this.referenceGroup!= null)&&(!this.referenceGroup.isEmpty()));
    }

    public void unsetReferenceGroup() {
        this.referenceGroup = null;
    }

}
