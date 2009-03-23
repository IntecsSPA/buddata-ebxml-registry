
package be.kzen.ergorr.model.gml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.eo.hma.EarthObservationEquipmentType;
import be.kzen.ergorr.model.eo.hma.FootprintType;
import be.kzen.ergorr.model.eo.hma.MaskFeatureType;
import be.kzen.ergorr.model.eo.hma.MaskType;


/**
 * Container for features - follow gml:ArrayAssociationType pattern.
 * 
 * <p>Java class for FeatureArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeatureArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}_Feature" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeatureArrayPropertyType", propOrder = {
    "feature"
})
public class FeatureArrayPropertyType {

    @XmlElementRef(name = "_Feature", namespace = "http://www.opengis.net/gml", type = JAXBElement.class)
    protected List<JAXBElement<? extends AbstractFeatureType>> feature;

    /**
     * Gets the value of the feature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the feature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link AbstractFeatureType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.hma.EarthObservationResultType }{@code >}
     * {@link JAXBElement }{@code <}{@link FootprintType }{@code >}
     * {@link JAXBElement }{@code <}{@link MaskType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.opt.EarthObservationResultType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.atm.EarthObservationType }{@code >}
     * {@link JAXBElement }{@code <}{@link MaskFeatureType }{@code >}
     * {@link JAXBElement }{@code <}{@link ObservationType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractFeatureCollectionType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.opt.EarthObservationType }{@code >}
     * {@link JAXBElement }{@code <}{@link EarthObservationEquipmentType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.atm.EarthObservationResultType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.hma.EarthObservationType }{@code >}
     * {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.sar.EarthObservationType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends AbstractFeatureType>> getFeature() {
        if (feature == null) {
            feature = new ArrayList<JAXBElement<? extends AbstractFeatureType>>();
        }
        return this.feature;
    }

    public boolean isSetFeature() {
        return ((this.feature!= null)&&(!this.feature.isEmpty()));
    }

    public void unsetFeature() {
        this.feature = null;
    }

}
