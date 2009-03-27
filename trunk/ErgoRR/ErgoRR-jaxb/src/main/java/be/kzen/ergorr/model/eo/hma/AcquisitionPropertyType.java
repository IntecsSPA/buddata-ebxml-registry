
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AcquisitionPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcquisitionPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}Acquisition"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcquisitionPropertyType", propOrder = {
    "acquisition"
})
public class AcquisitionPropertyType {

    @XmlElementRef(name = "Acquisition", namespace = "http://earth.esa.int/hma", type = JAXBElement.class)
    protected JAXBElement<? extends be.kzen.ergorr.model.eo.hma.AcquisitionType> acquisition;

    /**
     * Gets the value of the acquisition property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.hma.AcquisitionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.sar.AcquisitionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.opt.AcquisitionType }{@code >}
     *     
     */
    public JAXBElement<? extends be.kzen.ergorr.model.eo.hma.AcquisitionType> getAcquisition() {
        return acquisition;
    }

    /**
     * Sets the value of the acquisition property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.hma.AcquisitionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.sar.AcquisitionType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.opt.AcquisitionType }{@code >}
     *     
     */
    public void setAcquisition(JAXBElement<? extends be.kzen.ergorr.model.eo.hma.AcquisitionType> value) {
        this.acquisition = ((JAXBElement<? extends be.kzen.ergorr.model.eo.hma.AcquisitionType> ) value);
    }

    public boolean isSetAcquisition() {
        return (this.acquisition!= null);
    }

}
