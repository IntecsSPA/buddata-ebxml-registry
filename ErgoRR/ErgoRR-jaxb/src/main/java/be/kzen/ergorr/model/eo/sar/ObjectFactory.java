
package be.kzen.ergorr.model.eo.sar;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import be.kzen.ergorr.model.gml.AngleType;
import be.kzen.ergorr.model.gml.MeasureType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.kzen.ergorr.model.eo.sar package. 
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

    private final static QName _PolarisationMode_QNAME = new QName("http://earth.esa.int/sar", "polarisationMode");
    private final static QName _DopplerFrequency_QNAME = new QName("http://earth.esa.int/sar", "dopplerFrequency");
    private final static QName _EarthObservation_QNAME = new QName("http://earth.esa.int/sar", "EarthObservation");
    private final static QName _MaximumIncidenceAngle_QNAME = new QName("http://earth.esa.int/sar", "maximumIncidenceAngle");
    private final static QName _PolarisationChannels_QNAME = new QName("http://earth.esa.int/sar", "polarisationChannels");
    private final static QName _MinimumIncidenceAngle_QNAME = new QName("http://earth.esa.int/sar", "minimumIncidenceAngle");
    private final static QName _Acquisition_QNAME = new QName("http://earth.esa.int/sar", "Acquisition");
    private final static QName _AntennaLookDirection_QNAME = new QName("http://earth.esa.int/sar", "antennaLookDirection");
    private final static QName _IncidenceAngleVariation_QNAME = new QName("http://earth.esa.int/sar", "incidenceAngleVariation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.kzen.ergorr.model.eo.sar
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EarthObservationType }
     * 
     */
    public EarthObservationType createEarthObservationType() {
        return new EarthObservationType();
    }

    /**
     * Create an instance of {@link AcquisitionType }
     * 
     */
    public AcquisitionType createAcquisitionType() {
        return new AcquisitionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PolarisationModePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "polarisationMode")
    public JAXBElement<PolarisationModePropertyType> createPolarisationMode(PolarisationModePropertyType value) {
        return new JAXBElement<PolarisationModePropertyType>(_PolarisationMode_QNAME, PolarisationModePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "dopplerFrequency")
    public JAXBElement<MeasureType> createDopplerFrequency(MeasureType value) {
        return new JAXBElement<MeasureType>(_DopplerFrequency_QNAME, MeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EarthObservationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "EarthObservation", substitutionHeadNamespace = "http://earth.esa.int/eop", substitutionHeadName = "EarthObservation")
    public JAXBElement<EarthObservationType> createEarthObservation(EarthObservationType value) {
        return new JAXBElement<EarthObservationType>(_EarthObservation_QNAME, EarthObservationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AngleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "maximumIncidenceAngle")
    public JAXBElement<AngleType> createMaximumIncidenceAngle(AngleType value) {
        return new JAXBElement<AngleType>(_MaximumIncidenceAngle_QNAME, AngleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PolarisationChannelsPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "polarisationChannels")
    public JAXBElement<PolarisationChannelsPropertyType> createPolarisationChannels(PolarisationChannelsPropertyType value) {
        return new JAXBElement<PolarisationChannelsPropertyType>(_PolarisationChannels_QNAME, PolarisationChannelsPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AngleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "minimumIncidenceAngle")
    public JAXBElement<AngleType> createMinimumIncidenceAngle(AngleType value) {
        return new JAXBElement<AngleType>(_MinimumIncidenceAngle_QNAME, AngleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcquisitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "Acquisition", substitutionHeadNamespace = "http://earth.esa.int/eop", substitutionHeadName = "Acquisition")
    public JAXBElement<AcquisitionType> createAcquisition(AcquisitionType value) {
        return new JAXBElement<AcquisitionType>(_Acquisition_QNAME, AcquisitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "antennaLookDirection")
    public JAXBElement<String> createAntennaLookDirection(String value) {
        return new JAXBElement<String>(_AntennaLookDirection_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AngleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://earth.esa.int/sar", name = "incidenceAngleVariation")
    public JAXBElement<AngleType> createIncidenceAngleVariation(AngleType value) {
        return new JAXBElement<AngleType>(_IncidenceAngleVariation_QNAME, AngleType.class, null, value);
    }

}
