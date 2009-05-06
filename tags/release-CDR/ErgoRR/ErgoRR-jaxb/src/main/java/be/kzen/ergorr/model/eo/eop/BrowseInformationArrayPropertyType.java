
package be.kzen.ergorr.model.eo.eop;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BrowseInformationArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BrowseInformationArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/eop}BrowseInformation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BrowseInformationArrayPropertyType", propOrder = {
    "browseInformation"
})
public class BrowseInformationArrayPropertyType {

    @XmlElement(name = "BrowseInformation", required = true)
    protected List<BrowseInformationType> browseInformation;

    /**
     * Gets the value of the browseInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the browseInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBrowseInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BrowseInformationType }
     * 
     * 
     */
    public List<BrowseInformationType> getBrowseInformation() {
        if (browseInformation == null) {
            browseInformation = new ArrayList<BrowseInformationType>();
        }
        return this.browseInformation;
    }

    public boolean isSetBrowseInformation() {
        return ((this.browseInformation!= null)&&(!this.browseInformation.isEmpty()));
    }

    public void unsetBrowseInformation() {
        this.browseInformation = null;
    }

}
