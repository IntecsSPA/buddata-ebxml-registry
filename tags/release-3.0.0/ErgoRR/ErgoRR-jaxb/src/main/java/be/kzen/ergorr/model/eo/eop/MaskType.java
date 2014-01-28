
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.AbstractFeatureType;


/**
 * <p>Java class for MaskType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaskType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}maskMembers"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaskType", propOrder = {
    "maskMembers"
})
public class MaskType
    extends AbstractFeatureType
{

    @XmlElement(required = true)
    protected MaskMembersPropertyType maskMembers;

    /**
     * Gets the value of the maskMembers property.
     * 
     * @return
     *     possible object is
     *     {@link MaskMembersPropertyType }
     *     
     */
    public MaskMembersPropertyType getMaskMembers() {
        return maskMembers;
    }

    /**
     * Sets the value of the maskMembers property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaskMembersPropertyType }
     *     
     */
    public void setMaskMembers(MaskMembersPropertyType value) {
        this.maskMembers = value;
    }

    public boolean isSetMaskMembers() {
        return (this.maskMembers!= null);
    }

}
