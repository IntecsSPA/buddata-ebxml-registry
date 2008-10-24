
package be.kzen.ergorr.model.eo.opt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.kzen.ergorr.model.eo.opt package. 
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

    private final static QName _EarthObservation_QNAME = new QName("http://earth.esa.int/opt", "EarthObservation");
    private final static QName _Acquisition_QNAME = new QName("http://earth.esa.int/opt", "Acquisition");
    private final static QName _EarthObservationResult_QNAME = new QName("http://earth.esa.int/opt", "EarthObservationResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.kzen.ergorr.model.eo.opt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AcquisitionType }
     * 
     */
    public AcquisitionType createAcquisitionType() {
        return new AcquisitionType();
    }

    /**
     * Create an instance of {@link EarthObservationResultType }
     * 
     */
    public EarthObservationResultType createEarthObservationResultType() {
        return new EarthObservationResultType();
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
    @XmlElementDecl(namespace = "http://earth.esa.int/opt", name = "EarthObservation", substitutionHeadNamespace = "http://earth.esa.int/hma", substitutionHeadName = "EarthObservation")
    public JAXBElement<EarthObservationType> createEarthObservation(EarthObservationType value) {
        return new JAXBElement<EarthObservationType>(_EarthObservation_QNAME, EarthObservationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcquisitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/opt", name = "Acquisition", substitutionHeadNamespace = "http://earth.esa.int/hma", substitutionHeadName = "Acquisition")
    public JAXBElement<AcquisitionType> createAcquisition(AcquisitionType value) {
        return new JAXBElement<AcquisitionType>(_Acquisition_QNAME, AcquisitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EarthObservationResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/opt", name = "EarthObservationResult", substitutionHeadNamespace = "http://earth.esa.int/hma", substitutionHeadName = "EarthObservationResult")
    public JAXBElement<EarthObservationResultType> createEarthObservationResult(EarthObservationResultType value) {
        return new JAXBElement<EarthObservationResultType>(_EarthObservationResult_QNAME, EarthObservationResultType.class, null, value);
    }

}