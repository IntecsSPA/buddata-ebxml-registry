
package be.kzen.ergorr.model.csw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The response contains a list of matching schema components
 *          in the requested schema language.
 * 
 * <p>Java class for DescribeRecordResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescribeRecordResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SchemaComponent" type="{http://www.opengis.net/cat/csw/2.0.2}SchemaComponentType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescribeRecordResponseType", propOrder = {
    "schemaComponent"
})
public class DescribeRecordResponseType {

    @XmlElement(name = "SchemaComponent")
    protected List<SchemaComponentType> schemaComponent;

    /**
     * Gets the value of the schemaComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schemaComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchemaComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SchemaComponentType }
     * 
     * 
     */
    public List<SchemaComponentType> getSchemaComponent() {
        if (schemaComponent == null) {
            schemaComponent = new ArrayList<SchemaComponentType>();
        }
        return this.schemaComponent;
    }

    public boolean isSetSchemaComponent() {
        return ((this.schemaComponent!= null)&&(!this.schemaComponent.isEmpty()));
    }

    public void unsetSchemaComponent() {
        this.schemaComponent = null;
    }

}
