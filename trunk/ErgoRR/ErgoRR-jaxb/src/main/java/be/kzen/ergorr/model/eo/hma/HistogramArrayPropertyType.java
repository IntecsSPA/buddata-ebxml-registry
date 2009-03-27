
package be.kzen.ergorr.model.eo.hma;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HistogramArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HistogramArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://earth.esa.int/hma}Histogram" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistogramArrayPropertyType", propOrder = {
    "histogram"
})
public class HistogramArrayPropertyType {

    @XmlElement(name = "Histogram", required = true)
    protected List<HistogramType> histogram;

    /**
     * Gets the value of the histogram property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the histogram property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistogram().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HistogramType }
     * 
     * 
     */
    public List<HistogramType> getHistogram() {
        if (histogram == null) {
            histogram = new ArrayList<HistogramType>();
        }
        return this.histogram;
    }

    public boolean isSetHistogram() {
        return ((this.histogram!= null)&&(!this.histogram.isEmpty()));
    }

    public void unsetHistogram() {
        this.histogram = null;
    }

}
