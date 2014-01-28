package it.intecs.pisa.ergorr.service.translator.cim;

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
import java.util.ArrayList;
import java.util.Iterator;
import org.xml.sax.InputSource;

/**
 *
 * @author Andrea Marongiu
 */
public class ISOTranslator<T extends Object> implements Translator<T> {

    private static Logger logger = Logger.getLogger(ISOTranslator.class.getName());
    protected SAXSource isoSAXSource;
    protected String isoString;
    protected RegistryObjectListType regObjList;
    protected RegistryPackageType regPkg;
    private static final String XSLT_ISO_TO_CIM_PATH = "/resources/cim/ISO19139toCIM.xslt";
    private static final String ISO_GMD_NAMESPACE = "http://www.isotc211.org/2005/gmd";
    private static final String ISO_GCO_NAMESPACE = "http://www.isotc211.org/2005/gco";
    private static final String XSLT_ISO_TO_CIM_RESOURCE_PATH = "/resources/cim";
    private static final String CIM_ID_PREFIX = "urn:CIM:";
    private static final String CIM_METADATA_INFORMATION_ID_SUFIX = ":ExtrinsicObject:MetadataInformation";
    private static final String XSLT_URL_PARAM_NAME = "cswURL";
    private static final String XSLT_METADATA_INFORMATION_ID_PARAM_NAME = "metadataInformationId";
    private static final String GET_SERVLET = "/httpservice";

    public ISOTranslator() {
        regObjList = new RegistryObjectListType();
        regPkg = new RegistryPackageType();
        RegistryObjectListType regPkgObjList = new RegistryObjectListType();
        regPkg.setRegistryObjectList(regPkgObjList);
        regObjList.getIdentifiable().add(OFactory.rim.createRegistryPackage(regPkg));
        regPkg.setName(RIMUtil.createString("Cataloguing of ISO Metadata (CIM) package for CSW-ebRIM"));
        regPkg.setDescription(RIMUtil.createString("Provides Cataloguing of ISO Metadata extensions to the Basic package of the CSW-ebRIM catalogue profile."));
    }

    public void setObject(Object obj) {
        if (obj instanceof String) {
            this.isoString = (String) obj;
            this.isoSAXSource = new SAXSource(new InputSource(new StringReader(this.isoString)));
        }
    }

    protected void associateToPackage(String idExObjMI) {
        AssociationType asso = RIMUtil.createAssociation(idExObjMI + ":pkg-asso",
                RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember, regPkg.getId(), idExObjMI);

        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));
    }

    private String getISODataIndetifier() throws TranslationException {
        String identifier = null;
        try {
            SaxonDocument saxonDoc = new SaxonDocument(this.isoString);
            saxonDoc.declareXPathNamespace("gmd", ISO_GMD_NAMESPACE);
            saxonDoc.declareXPathNamespace("gco", ISO_GCO_NAMESPACE);
            //identifier = saxonDoc.evaluateXPath("//gmd:MD_Metadata/gmd:fileIdentifier/gco:CharacterString");
            identifier = saxonDoc.evaluateXPath("//*[local-name()='MI_Metadata' or local-name()='MD_Metadata']/gmd:fileIdentifier/gco:CharacterString");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not retrieve ISO Identifier.", ex);
            throw new TranslationException("Could not retrieve ISO Identifier.");
        }
        return identifier;
    }

    public RegistryObjectListType translate() throws TranslationException {

        PipedInputStream pipeInput = null;
        SaxonXSLTParameter[] parameters = new SaxonXSLTParameter[2];
        URL xsltURL = this.getClass().getResource(XSLT_ISO_TO_CIM_PATH);
        SAXSource xsltDoc = null;
        String isoIdentifier = this.getISODataIndetifier();
        String medataInformationID = isoIdentifier + CIM_METADATA_INFORMATION_ID_SUFIX;
        if (medataInformationID.contains("urn:") == false) {
            medataInformationID = CIM_ID_PREFIX + medataInformationID;
        }

        parameters[0] = new SaxonXSLTParameter(XSLT_URL_PARAM_NAME, "http://"
                + CommonProperties.getInstance().get("appserver.url") + "/"
                + CommonProperties.getInstance().get("deployName") + GET_SERVLET);
        parameters[1] = new SaxonXSLTParameter(XSLT_METADATA_INFORMATION_ID_PARAM_NAME,
                medataInformationID);
        SaxonURIResolver uriResolver = null;

        /* InternationalStringType packageDescription=new InternationalStringType();
        packageDescription.*/
        if (isoIdentifier.startsWith("urn:")) {
            regPkg.setId(isoIdentifier + ":pkg");
        } else {
            regPkg.setId(CIM_ID_PREFIX + isoIdentifier + ":pkg");
        }
        regPkg.setLid(regPkg.getId());


        byte[] dataBuf = this.isoString.getBytes();
        ByteArrayDataSource dataBufSrc = new ByteArrayDataSource(dataBuf, MimeTypeConstants.APPLICATION_XML);
        DataHandler dh = new DataHandler(dataBufSrc);

        /*JAXBElement<WrsExtrinsicObjectType> cimMetadataInformationEl;
        cimMetadataInformationEl.getValue().setRepositoryItem(dh);*/
        try {
            uriResolver = new SaxonURIResolver(this.getClass().getResource(XSLT_ISO_TO_CIM_RESOURCE_PATH));
        } catch (URISyntaxException ex) {
            logger.log(Level.WARNING, "ISO Transformation : URI Resolver Syntax Error  ", ex);
        }
        SaxonXSLT saxonUtil = new SaxonXSLT(uriResolver);
        try {
            xsltDoc = new SAXSource(new InputSource(xsltURL.openStream()));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not open a ISO to CIM XSLT File : " + xsltURL.getPath(), ex);
            throw new TranslationException("Could not open a ISO to CIM XSLT File : " + xsltURL.getPath());
        }
        try {
            pipeInput = saxonUtil.saxonXSLPipeTransform(this.isoSAXSource, xsltDoc, parameters);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not transform ISO Document.", ex);
            throw new TranslationException("Could not transform ISO Document.");
        }

        try {
            JAXBElement<RegistryObjectListType> regObjEl;
            regObjEl = (JAXBElement<RegistryObjectListType>) JAXBUtil.getInstance().unmarshall(pipeInput);
            regObjList.getIdentifiable().addAll(regObjEl.getValue().getIdentifiable());
            //associateToPackage(medataInformationID);
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
                    WrsExtrinsicObjectType eoCim = (WrsExtrinsicObjectType) currentType;
                    //  if(eoCim.getId().equalsIgnoreCase(medataInformationID)){
                    eoCim.setRepositoryItem(dh);
                    eoCim.setMimeType("text/xml");
                    //break;
                    //     }*/
                }
            }
            for (String s : a) {
                if (s.contains(isoIdentifier)) {
                    associateToPackage(s);
                }
            }
            // regPkg.setRegistryObjectList(regObjList);
        } catch (JAXBException ex) {
            logger.log(Level.SEVERE, "Could not create CIM Registry Object List ", ex);
            throw new TranslationException("Could not create CIM Registry Object List");
        }

        return regObjList;
    }
}
