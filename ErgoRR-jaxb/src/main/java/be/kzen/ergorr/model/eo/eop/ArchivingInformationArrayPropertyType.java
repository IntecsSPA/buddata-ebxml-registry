
package be.kzen.ergorr.model.eo.eop;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArchivingInformationArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArchivingInformationArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}ArchivingInformation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArchivingInformationArrayPropertyType", propOrder = {
    "archivingInformation"
})
public class ArchivingInformationArrayPropertyType {

    @XmlElement(name = "ArchivingInformation", required = true)
    protected List<ArchivingInformationType> archivingInformation;

    /**
     * Gets the value of the archivingInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the archivingInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArchivingInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArchivingInformationType }
     * 
     * 
     */
    public List<ArchivingInformationType> getArchivingInformation() {
        if (archivingInformation == null) {
            archivingInformation = new ArrayList<ArchivingInformationType>();
        }
        return this.archivingInformation;
    }

    public boolean isSetArchivingInformation() {
        return ((this.archivingInformation!= null)&&(!this.archivingInformation.isEmpty()));
    }

    public void unsetArchivingInformation() {
        this.archivingInformation = null;
    }

}
