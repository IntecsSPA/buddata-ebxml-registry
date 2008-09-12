
package be.kzen.ergorr.model.csw;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             Base type for all request messages except GetCapabilities. The 
 *             attributes identify the relevant service type and version.
 *          
 * 
 * <p>Java class for RequestBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="service" use="required" type="{http://www.opengis.net/ows/1.1}ServiceType" fixed="CSW" />
 *       &lt;attribute name="version" use="required" type="{http://www.opengis.net/ows/1.1}VersionType" fixed="2.0.2" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestBaseType")
@XmlSeeAlso({
    GetDomainType.class,
    DescribeRecordType.class,
    GetRecordByIdType.class,
    TransactionType.class,
    HarvestType.class,
    GetRecordsType.class
})
public abstract class RequestBaseType {

    /**
     * 
     * 
     */
    @XmlAttribute(required = true)
    public final static String SERVICE = "CSW";
    /**
     * 
     * 
     */
    @XmlAttribute(required = true)
    public final static String VERSION = "2.0.2";

}
