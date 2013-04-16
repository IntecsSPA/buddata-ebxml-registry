
package be.kzen.ergorr.model.ogc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpatialOperatorsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpatialOperatorsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SpatialOperator" type="{http://www.opengis.net/ogc}SpatialOperatorType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpatialOperatorsType", propOrder = {
    "spatialOperator"
})
public class SpatialOperatorsType {

    @XmlElement(name = "SpatialOperator", required = true)
    protected List<SpatialOperatorType> spatialOperator;

    /**
     * Gets the value of the spatialOperator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spatialOperator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpatialOperator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpatialOperatorType }
     * 
     * 
     */
    public List<SpatialOperatorType> getSpatialOperator() {
        if (spatialOperator == null) {
            spatialOperator = new ArrayList<SpatialOperatorType>();
        }
        return this.spatialOperator;
    }

    public boolean isSetSpatialOperator() {
        return ((this.spatialOperator!= null)&&(!this.spatialOperator.isEmpty()));
    }

    public void unsetSpatialOperator() {
        this.spatialOperator = null;
    }

}
