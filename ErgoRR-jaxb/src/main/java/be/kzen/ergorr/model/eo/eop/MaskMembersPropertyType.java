
package be.kzen.ergorr.model.eo.eop;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaskMembersPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaskMembersPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}MaskFeature" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaskMembersPropertyType", propOrder = {
    "maskFeature"
})
public class MaskMembersPropertyType {

    @XmlElement(name = "MaskFeature")
    protected List<MaskFeatureType> maskFeature;

    /**
     * Gets the value of the maskFeature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maskFeature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaskFeature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaskFeatureType }
     * 
     * 
     */
    public List<MaskFeatureType> getMaskFeature() {
        if (maskFeature == null) {
            maskFeature = new ArrayList<MaskFeatureType>();
        }
        return this.maskFeature;
    }

    public boolean isSetMaskFeature() {
        return ((this.maskFeature!= null)&&(!this.maskFeature.isEmpty()));
    }

    public void unsetMaskFeature() {
        this.maskFeature = null;
    }

}
