
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EarthObservationResultPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationResultPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}EarthObservationResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationResultPropertyType", propOrder = {
    "earthObservationResult"
})
public class EarthObservationResultPropertyType {

    @XmlElementRef(name = "EarthObservationResult", namespace = "http://earth.esa.int/hma", type = JAXBElement.class)
    protected JAXBElement<? extends be.kzen.ergorr.model.eo.hma.EarthObservationResultType> earthObservationResult;

    /**
     * Gets the value of the earthObservationResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.opt.EarthObservationResultType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.atm.EarthObservationResultType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.hma.EarthObservationResultType }{@code >}
     *     
     */
    public JAXBElement<? extends be.kzen.ergorr.model.eo.hma.EarthObservationResultType> getEarthObservationResult() {
        return earthObservationResult;
    }

    /**
     * Sets the value of the earthObservationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.opt.EarthObservationResultType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.atm.EarthObservationResultType }{@code >}
     *     {@link JAXBElement }{@code <}{@link be.kzen.ergorr.model.eo.hma.EarthObservationResultType }{@code >}
     *     
     */
    public void setEarthObservationResult(JAXBElement<? extends be.kzen.ergorr.model.eo.hma.EarthObservationResultType> value) {
        this.earthObservationResult = ((JAXBElement<? extends be.kzen.ergorr.model.eo.hma.EarthObservationResultType> ) value);
    }

    public boolean isSetEarthObservationResult() {
        return (this.earthObservationResult!= null);
    }

}
