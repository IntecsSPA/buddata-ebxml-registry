
package be.kzen.ergorr.model.eo.atm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EarthObservationResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://earth.esa.int/eop}EarthObservationResultType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/atm}dataLayers" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationResultType", propOrder = {
    "dataLayers"
})
public class EarthObservationResultType
    extends be.kzen.ergorr.model.eo.eop.EarthObservationResultType
{

    protected DataLayerPropertyType dataLayers;

    /**
     * Gets the value of the dataLayers property.
     * 
     * @return
     *     possible object is
     *     {@link DataLayerPropertyType }
     *     
     */
    public DataLayerPropertyType getDataLayers() {
        return dataLayers;
    }

    /**
     * Sets the value of the dataLayers property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataLayerPropertyType }
     *     
     */
    public void setDataLayers(DataLayerPropertyType value) {
        this.dataLayers = value;
    }

    public boolean isSetDataLayers() {
        return (this.dataLayers!= null);
    }

}
