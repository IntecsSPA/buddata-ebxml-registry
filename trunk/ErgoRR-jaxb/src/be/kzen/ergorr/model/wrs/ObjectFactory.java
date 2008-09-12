
package be.kzen.ergorr.model.wrs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import be.kzen.ergorr.model.csw.CapabilitiesType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.kzen.ergorr.model.wrs package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExtrinsicObject_QNAME = new QName("http://www.opengis.net/cat/wrs/1.0", "ExtrinsicObject");
    private final static QName _RecordId_QNAME = new QName("http://www.opengis.net/cat/wrs/1.0", "RecordId");
    private final static QName _Capabilities_QNAME = new QName("http://www.opengis.net/cat/wrs/1.0", "Capabilities");
    private final static QName _AnyValue_QNAME = new QName("http://www.opengis.net/cat/wrs/1.0", "AnyValue");
    private final static QName _ValueList_QNAME = new QName("http://www.opengis.net/cat/wrs/1.0", "ValueList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.kzen.ergorr.model.wrs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SimpleLinkType }
     * 
     */
    public SimpleLinkType createSimpleLinkType() {
        return new SimpleLinkType();
    }

    /**
     * Create an instance of {@link AnyValueType }
     * 
     */
    public AnyValueType createAnyValueType() {
        return new AnyValueType();
    }

    /**
     * Create an instance of {@link ValueListType }
     * 
     */
    public WrsValueListType createValueListType() {
        return new WrsValueListType();
    }

    /**
     * Create an instance of {@link WrsExtrinsicObjectType }
     * 
     */
    public WrsExtrinsicObjectType createWrsExtrinsicObjectType() {
        return new WrsExtrinsicObjectType();
    }

    /**
     * Create an instance of {@link RecordIdType }
     * 
     */
    public RecordIdType createRecordIdType() {
        return new RecordIdType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WrsExtrinsicObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/cat/wrs/1.0", name = "ExtrinsicObject", substitutionHeadNamespace = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", substitutionHeadName = "ExtrinsicObject")
    public JAXBElement<WrsExtrinsicObjectType> createExtrinsicObject(WrsExtrinsicObjectType value) {
        return new JAXBElement<WrsExtrinsicObjectType>(_ExtrinsicObject_QNAME, WrsExtrinsicObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecordIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/cat/wrs/1.0", name = "RecordId", substitutionHeadNamespace = "http://www.opengis.net/ogc", substitutionHeadName = "_Id")
    public JAXBElement<RecordIdType> createRecordId(RecordIdType value) {
        return new JAXBElement<RecordIdType>(_RecordId_QNAME, RecordIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CapabilitiesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/cat/wrs/1.0", name = "Capabilities")
    public JAXBElement<CapabilitiesType> createCapabilities(CapabilitiesType value) {
        return new JAXBElement<CapabilitiesType>(_Capabilities_QNAME, CapabilitiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnyValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/cat/wrs/1.0", name = "AnyValue")
    public JAXBElement<AnyValueType> createAnyValue(AnyValueType value) {
        return new JAXBElement<AnyValueType>(_AnyValue_QNAME, AnyValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WrsValueListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/cat/wrs/1.0", name = "ValueList", substitutionHeadNamespace = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", substitutionHeadName = "ValueList")
    public JAXBElement<WrsValueListType> createValueList(WrsValueListType value) {
        return new JAXBElement<WrsValueListType>(_ValueList_QNAME, WrsValueListType.class, null, value);
    }

}
