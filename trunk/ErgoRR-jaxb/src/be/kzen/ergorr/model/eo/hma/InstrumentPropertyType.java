
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InstrumentPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InstrumentPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}Instrument"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstrumentPropertyType", propOrder = {
    "instrument"
})
public class InstrumentPropertyType {

    @XmlElement(name = "Instrument", required = true)
    protected InstrumentType instrument;

    /**
     * Gets the value of the instrument property.
     * 
     * @return
     *     possible object is
     *     {@link InstrumentType }
     *     
     */
    public InstrumentType getInstrument() {
        return instrument;
    }

    /**
     * Sets the value of the instrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstrumentType }
     *     
     */
    public void setInstrument(InstrumentType value) {
        this.instrument = value;
    }

    public boolean isSetInstrument() {
        return (this.instrument!= null);
    }

}
