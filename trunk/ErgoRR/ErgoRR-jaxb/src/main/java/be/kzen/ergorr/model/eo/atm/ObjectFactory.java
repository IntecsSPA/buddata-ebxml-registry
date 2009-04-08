
package be.kzen.ergorr.model.eo.atm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.kzen.ergorr.model.eo.atm package. 
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

    private final static QName _EarthObservation_QNAME = new QName("http://earth.esa.int/atm", "EarthObservation");
    private final static QName _DataLayers_QNAME = new QName("http://earth.esa.int/atm", "dataLayers");
    private final static QName _EarthObservationResult_QNAME = new QName("http://earth.esa.int/atm", "EarthObservationResult");
    private final static QName _DataLayer_QNAME = new QName("http://earth.esa.int/atm", "DataLayer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.kzen.ergorr.model.eo.atm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EarthObservationResultType }
     * 
     */
    public EarthObservationResultType createEarthObservationResultType() {
        return new EarthObservationResultType();
    }

    /**
     * Create an instance of {@link DataLayerPropertyType }
     * 
     */
    public DataLayerPropertyType createDataLayerPropertyType() {
        return new DataLayerPropertyType();
    }

    /**
     * Create an instance of {@link DataLayerType }
     * 
     */
    public DataLayerType createDataLayerType() {
        return new DataLayerType();
    }

    /**
     * Create an instance of {@link EarthObservationType }
     * 
     */
    public EarthObservationType createEarthObservationType() {
        return new EarthObservationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EarthObservationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/atm", name = "EarthObservation", substitutionHeadNamespace = "http://earth.esa.int/eop", substitutionHeadName = "EarthObservation")
    public JAXBElement<EarthObservationType> createEarthObservation(EarthObservationType value) {
        return new JAXBElement<EarthObservationType>(_EarthObservation_QNAME, EarthObservationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataLayerPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/atm", name = "dataLayers")
    public JAXBElement<DataLayerPropertyType> createDataLayers(DataLayerPropertyType value) {
        return new JAXBElement<DataLayerPropertyType>(_DataLayers_QNAME, DataLayerPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EarthObservationResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/atm", name = "EarthObservationResult", substitutionHeadNamespace = "http://earth.esa.int/eop", substitutionHeadName = "EarthObservationResult")
    public JAXBElement<EarthObservationResultType> createEarthObservationResult(EarthObservationResultType value) {
        return new JAXBElement<EarthObservationResultType>(_EarthObservationResult_QNAME, EarthObservationResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataLayerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/atm", name = "DataLayer")
    public JAXBElement<DataLayerType> createDataLayer(DataLayerType value) {
        return new JAXBElement<DataLayerType>(_DataLayer_QNAME, DataLayerType.class, null, value);
    }

}
