
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessingInformationPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessingInformationPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}ProcessingInformation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessingInformationPropertyType", propOrder = {
    "processingInformation"
})
public class ProcessingInformationPropertyType {

    @XmlElement(name = "ProcessingInformation", required = true)
    protected ProcessingInformationType processingInformation;

    /**
     * Gets the value of the processingInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessingInformationType }
     *     
     */
    public ProcessingInformationType getProcessingInformation() {
        return processingInformation;
    }

    /**
     * Sets the value of the processingInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessingInformationType }
     *     
     */
    public void setProcessingInformation(ProcessingInformationType value) {
        this.processingInformation = value;
    }

    public boolean isSetProcessingInformation() {
        return (this.processingInformation!= null);
    }

}
