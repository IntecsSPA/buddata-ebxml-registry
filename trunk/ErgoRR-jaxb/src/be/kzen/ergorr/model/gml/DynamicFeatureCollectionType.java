
package be.kzen.ergorr.model.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DynamicFeatureCollectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DynamicFeatureCollectionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}DynamicFeatureType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}dynamicMembers"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamicFeatureCollectionType", propOrder = {
    "dynamicMembers"
})
public class DynamicFeatureCollectionType
    extends DynamicFeatureType
{

    @XmlElement(required = true)
    protected DynamicFeatureMemberType dynamicMembers;

    /**
     * Gets the value of the dynamicMembers property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicFeatureMemberType }
     *     
     */
    public DynamicFeatureMemberType getDynamicMembers() {
        return dynamicMembers;
    }

    /**
     * Sets the value of the dynamicMembers property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicFeatureMemberType }
     *     
     */
    public void setDynamicMembers(DynamicFeatureMemberType value) {
        this.dynamicMembers = value;
    }

    public boolean isSetDynamicMembers() {
        return (this.dynamicMembers!= null);
    }

}
