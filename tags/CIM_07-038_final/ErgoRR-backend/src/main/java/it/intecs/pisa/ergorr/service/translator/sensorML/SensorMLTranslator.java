/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.intecs.pisa.ergorr.service.translator.sensorML;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.MimeTypeConstants;
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.commons.RIMUtil;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import javax.activation.DataHandler;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.SimpleLinkType;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.service.translator.TranslationException;
import be.kzen.ergorr.service.translator.Translator;
import it.intecs.pisa.ergorr.saxon.SaxonURIResolver;
import it.intecs.pisa.ergorr.saxon.SaxonDocument;
import it.intecs.pisa.ergorr.saxon.SaxonXSLT;
import it.intecs.pisa.ergorr.saxon.SaxonXSLTParameter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.transform.sax.SAXSource;
import com.sun.xml.ws.util.ByteArrayDataSource;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;

/**
 *
 * @author Maria Rosaria Barone
 */
public class SensorMLTranslator<T extends Object> implements Translator<T> {

    private static Logger logger = Logger.getLogger(SensorMLTranslator.class.getName());
    protected SAXSource sensorMLSAXSource;
    protected String sensorMLString;
    protected RegistryObjectListType regObjList;
    protected RegistryPackageType regPkg;
    private static final String XSLT_PATH = "/resources/sensorML/SensorML-to-ebRIM.xsl";
    //private static final String SENSOR_ML_NAMESPACE = "http://www.opengis.net/sensorML/1.0.1";
    //private static final String SWE_NAMESPACE = "http://www.opengis.net/swe/1.0.1";
    private static final String XSLT_RESOURCE_PATH = "/resources/sensorML";
    private static final String SENSORML_PROFILE_SCHEMATRON_PATH = "/resources/sensorML/validation/SensorML_Profile_for_Discovery.sch";
    private static final String ISO_SVRL_FOR_XSLT_PATH = "/resources/sensorML/validation/iso_svrl_for_xslt2.xsl";
    private static final String GET_SERVLET = "/httpservice";

    public SensorMLTranslator() {
        regObjList = new RegistryObjectListType();
        regPkg = new RegistryPackageType();
        RegistryObjectListType regPkgObjList = new RegistryObjectListType();
        regPkg.setRegistryObjectList(regPkgObjList);
        regObjList.getIdentifiable().add(OFactory.rim.createRegistryPackage(regPkg));
        regPkg.setName(RIMUtil.createString("Cataloguing of SensorML Metadata for CSW-ebRIM"));
        regPkg.setDescription(RIMUtil.createString("Provides Cataloguing of SensorML Metadata extensions to the Basic package of the CSW-ebRIM catalogue profile."));
    }

    public void setObject(Object obj) {
        if (obj instanceof String) {
            this.sensorMLString = (String) obj;
            this.sensorMLSAXSource = new SAXSource(new InputSource(new StringReader(this.sensorMLString)));
        }
    }

    protected void associateToPackage(String idExObjMI) {
        AssociationType asso = RIMUtil.createAssociation(idExObjMI + ":pkg-asso",
                RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember, regPkg.getId(), idExObjMI);

        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));
    }

    public RegistryObjectListType translate() throws TranslationException {

        PipedInputStream pipeInput = null;

        URL xsltURL = this.getClass().getResource(XSLT_PATH);
        SAXSource xsltDoc = null;

        SaxonURIResolver uriResolver = null;

        byte[] dataBuf = this.sensorMLString.getBytes();
        ByteArrayDataSource dataBufSrc = new ByteArrayDataSource(dataBuf, MimeTypeConstants.APPLICATION_XML);
        DataHandler dh = new DataHandler(dataBufSrc);

        try {
            uriResolver = new SaxonURIResolver(this.getClass().getResource(XSLT_RESOURCE_PATH));
        } catch (URISyntaxException ex) {
            logger.log(Level.WARNING, "SensorML Transformation : URI Resolver Syntax Error  ", ex);
        }
        SaxonXSLT saxonUtil = new SaxonXSLT(uriResolver);
        try {
            xsltDoc = new SAXSource(new InputSource(xsltURL.openStream()));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not open the SensorML XSLT File : " + xsltURL.getPath(), ex);
            throw new TranslationException("Could not open the SensorML XSLT File : " + xsltURL.getPath());
        }

        try {
            pipeInput = saxonUtil.saxonXSLPipeTransform(this.sensorMLSAXSource, xsltDoc, null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not transform SensorML Document.", ex);
            throw new TranslationException("Could not transform SensorML Document.");
        }

        try {

            JAXBElement<RegistryPackageType> regPackageResult;
            regPackageResult = (JAXBElement<RegistryPackageType>) JAXBUtil.getInstance().unmarshall(pipeInput);

            regPkg.setId(regPackageResult.getValue().getId());
            regPkg.setLid(regPackageResult.getValue().getId());

            regObjList.getIdentifiable().addAll(regPackageResult.getValue().getRegistryObjectList().getIdentifiable());

            ArrayList<String> a = new ArrayList<String>();
            Iterator isoTransformObjects = regObjList.getIdentifiable().listIterator();
            while (isoTransformObjects.hasNext()) {
                JAXBElement current = (JAXBElement) isoTransformObjects.next();
                Object currentType = current.getValue();
                RegistryObjectType rO = (RegistryObjectType) currentType;
                if (!(currentType instanceof RegistryPackageType)) {
                    //System.out.println("Inserting identifier: " + rO.getId());
                    a.add(rO.getId());
                }
                if (currentType instanceof WrsExtrinsicObjectType) {
                    WrsExtrinsicObjectType eoSensor = (WrsExtrinsicObjectType) currentType;
                    if (eoSensor.getObjectType().equals("urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor::System")) {
                        eoSensor.setRepositoryItem(dh);
                        eoSensor.setMimeType("text/xml");

                        SimpleLinkType link = new SimpleLinkType();
                        String cswURL = new String("http://" + CommonProperties.getInstance().get("appserver.url") + "/" + CommonProperties.getInstance().get("deployName") + GET_SERVLET);
                        String href = new String(cswURL + "?request=GetRepositoryItem&service=CSW-ebRIM&Id=" + eoSensor.getId());
                        link.setHref(href);
                        eoSensor.setRepositoryItemRef(link);

                        //break;
                    }
                }
            }

            for (String s : a) {
                associateToPackage(s);
            }
            // regPkg.setRegistryObjectList(regObjList);
        } catch (JAXBException ex) {
            logger.log(Level.SEVERE, "Could not create CIM Registry Object List ", ex);
            throw new TranslationException("Could not create CIM Registry Object List");
        }

        return regObjList;
    }

    /**
     * Source: http://www.oxygenxml.com/forum/topic4883.html
     * Check if an XML is valid
     */
    /*
    public String checkValid() throws TransformerFactoryConfigurationError, IOException, TransformerException {

        URL schematronURL = this.getClass().getResource(SENSORML_PROFILE_SCHEMATRON_PATH);
        URL isoSVRLforXSLTUrl = this.getClass().getResource(ISO_SVRL_FOR_XSLT_PATH);

        //First apply the XSL over the schematron content
        InputSource xslSCHMessages = new InputSource();
        if (isoSVRLforXSLTUrl != null) {
            xslSCHMessages.setSystemId(isoSVRLforXSLTUrl.toString());
        }
        Transformer newTransformer = TransformerFactory.newInstance().newTransformer(new SAXSource(xslSCHMessages));
        //Apply over the schematron content
        File xslFromSchematron = File.createTempFile("sch", ".xsl");
        Result result = new StreamResult(xslFromSchematron);
        InputSource schematronIS = new InputSource();
        if (schematronURL != null) {
            schematronIS.setSystemId(schematronURL.toString());
        }

        newTransformer.transform(new SAXSource(schematronIS), result);

        //Now apply the XSL obtained from applying the Schematron XSL over the SCH file
        newTransformer = TransformerFactory.newInstance().newTransformer(new SAXSource(new InputSource(xslFromSchematron.toURL().toString())));
        StringWriter resultWriter = new StringWriter();
        result = new StreamResult(resultWriter);

        SAXSource sensorMLSource = new SAXSource(new InputSource(new StringReader(this.sensorMLString)));
        newTransformer.transform(sensorMLSource, result);

        return resultWriter.toString();
    }
     *
     */
}
