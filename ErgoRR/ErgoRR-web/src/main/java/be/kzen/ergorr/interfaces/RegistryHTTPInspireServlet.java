/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryHTTPServlet.java
 * Copyright (C) 2009
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.interfaces;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.IoUtil;
import be.kzen.ergorr.commons.MimeTypeConstants;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.commons.StopWatch;
import be.kzen.ergorr.exceptions.ErrorCodes;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.DescribeRecordResponseType;
import be.kzen.ergorr.model.csw.DescribeRecordType;
import be.kzen.ergorr.model.csw.ElementSetNameType;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetCapabilitiesType;
import be.kzen.ergorr.model.csw.GetDomainType;
import be.kzen.ergorr.model.csw.GetRecordByIdResponseType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.HarvestResponseType;
import be.kzen.ergorr.model.csw.HarvestType;
import be.kzen.ergorr.model.csw.SchemaComponentType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.ows.ExceptionReport;
import be.kzen.ergorr.model.ows.ExceptionType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.CapabilitiesCSWReader;
import be.kzen.ergorr.service.HarvestService;
import be.kzen.ergorr.service.RepositoryManager;
import be.kzen.ergorr.service.TransactionService;
import be.kzen.ergorr.service.translator.TranslationException;
import it.intecs.pisa.ergorr.saxon.SaxonDocument;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.trans.XPathException;
import org.w3c.dom.Document;
import it.intecs.pisa.ergorr.saxon.SaxonURIResolver;
import it.intecs.pisa.ergorr.saxon.SaxonXSLT;
import it.intecs.pisa.ergorr.saxon.SaxonXSLTParameter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.net.URL;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * CSW HTTP interface.
 * 
 * @author Massimiliano Fanciulli
 * @author Yaman Ustuntas
 */
public class RegistryHTTPInspireServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistryHTTPInspireServlet.class.getName());
    // The service parameter of the request shall be equal to:
    // - "CSW-ebRIM" for the GetCapabilities and GetRepositoryItem operations;
    // - "CSW" for the GetRecordById operation.
    private static final String CSW_SERVICE_NAME = "CSW";
    private static final String PARAM_SERVICE = "service";
    private static final String PARAM_VERSION = "version";
    // The version parameter of the request shall be:
    // - empty for the GetCapabilities and GetRepositoryItem operations;
    // - "2.0.2"  for the GetRecordById operation.
    private static final String VERSION_NAME = "2.0.2";
    private static final String PARAM_ELEMENT_SET_NAME = "ElementSetName";
    private static final String PARAM_OUTPUT_FORMAT = "outputFormat";
    private static final String PARAM_REQUEST = "request";
    private static final String PARAM_ID = "Id";
    
    private static final String INTERNAL_ERROR = "500";

    private static final String XSLT_INSPIRE_2_CIM_GET_RECORD_BY_ID = "/inspire/XSL/inspire_2_CIM_GRBI.xslt";
    private static final String XSLT_INSPIRE_2_CIM_GET_RECORDS = "/inspire/XSL/inspire_2_CIM.xslt";
    private static final String XSLT_INSPIRE_2_CIM_TRANSACTION = "/inspire/XSL/inspire_2_CIM_TRANSACTION.xslt";
    
    private static final String XSLT_INSPIRE_CONFIG_PARAMETER="inspire_config";
    
    private static final String XSLT_CIM_2_INSPIRE_GET_RECORD_BY_ID = "/inspire/XSL/CIM_2_inspire_GRBI.xslt";
    private static final String XSLT_CIM_2_INSPIRE_GET_RECORDS = "/inspire/XSL/CIM_2_inspire.xslt";
    private static final String XSLT_CIM_2_INSPIRE_HARVEST = "/inspire/XSL/CIM_2_inspire_HARVEST.xslt";
    private static final String XSLT_CIM_2_INSPIRE_TRANSACTION = "/inspire/XSL/CIM_2_inspire_TRANSACTION.xslt";
    
    private static final String XSLT_INSPIRE_RESOURCE_PATH = "/inspire/XSL";
    private static final String XML_INSPIRE_QUERYABLES_PATH = "/inspire/XSL/inspire_queryables_2_CIM.xml";
    
    private static final String CSW_NAMESPACE = "http://www.opengis.net/cat/csw/2.0.2";
    private static final String CSW_PREFIX = "csw";
    private static final String GET_RECORDS_RESULT_TYPE_XPATH = "//csw:GetRecords/csw:Query/csw:ElementSetName";
    private static final String GET_RECORD_BY_ID_RESULT_TYPE_XPATH = "//csw:GetRecordById/csw:ElementSetName";
    
    private static final String RESULT_TYPE_FULL = "full";
    private static final String RESULT_TYPE_SUMMARY = "summary";
    private static final String RESULT_TYPE_BRIEF = "brief";
    

    private static final String XSLT_TYPE_PREFIX = "/iso-";
    private static final String XSLT_TYPE_SUFFIX = ".xslt";
    
    
    private static final String OPERATION_GET_RECORDS="GetRecords";
    private static final String OPERATION_DESCRIBE_RECORD="DescribeRecord";
    private static final String OPERATION_GET_CAPABILITIES="GetCapabilities";
    private static final String OPERATION_GET_RECORD_BY_ID="GetRecordById";
    private static final String OPERATION_HARVEST="Harvest";
    private static final String OPERATION_TRANSACTION="Transaction";
    
    
    private static final String SOAP_1_1_NAMESPACE="http://schemas.xmlsoap.org/soap/envelope/";
    private static final String SOAP_1_2_NAMESPACE="http://schemas.xmlsoap.org/soap/envelope/";
    
    
    
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceParameter;
        String versionParameter = null;
        try {
            serviceParameter = request.getParameter(getRequestParameterIgnoringCase(request.getParameterNames(), PARAM_SERVICE));
            versionParameter = request.getParameter(getRequestParameterIgnoringCase(request.getParameterNames(), PARAM_VERSION));

           if (serviceParameter.equals(CSW_SERVICE_NAME) && versionParameter.equals(VERSION_NAME)) {
                processGetRequest(request, response);
            } else {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("Requested service name and version are not supported.");
                }
                response.sendError(response.SC_NOT_IMPLEMENTED);
            }
        } catch (Exception e) {
            ExceptionReport exRep;

            try {
                exRep = createException(e.getMessage(), INTERNAL_ERROR);
                response.setContentType(MimeTypeConstants.APPLICATION_XML);

                JAXBUtil.getInstance().marshall(exRep, response.getOutputStream());
            } catch (JAXBException ex) {
                logger.log(Level.WARNING, "Could not create error messsage", ex);
                response.sendError(response.SC_INTERNAL_SERVER_ERROR);
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object unmarshalledRequestObj;
        PipedInputStream pipedInput;
        String resultType=null;
        String rootNamespace=null;
        String rootOperation=null;
        URL xsltRequestURL=null, xsltResponseURL=null;
        SaxonDocument saxDocReq,saxRequest;
        SaxonXSLTParameter[] parameters = null;
        try {
            saxDocReq= new SaxonDocument(request.getInputStream());
            rootNamespace=saxDocReq.getRootNameSpace();
            if(rootNamespace.equalsIgnoreCase(SOAP_1_1_NAMESPACE) ||
               rootNamespace.equalsIgnoreCase(SOAP_1_2_NAMESPACE)){
                rootOperation=saxDocReq.evaluateXPath("//*[local-name()='Body']/*[1]/local-name()");
                saxRequest=new SaxonDocument(saxDocReq.evaluateXPathNode("//*[local-name()='"+rootOperation+"']"));
            }else{
                rootOperation=saxDocReq.evaluateXPath("/local-name(*)");
                saxRequest=saxDocReq;
            }
            
            if(rootOperation.equals(OPERATION_GET_CAPABILITIES)){
                
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(saxRequest.getXMLDocumentString());
            }else
            if(rootOperation.equals(OPERATION_GET_RECORD_BY_ID)){
               xsltRequestURL = this.getClass().getResource(XSLT_INSPIRE_2_CIM_GET_RECORD_BY_ID); 
               xsltResponseURL = this.getClass().getResource(XSLT_CIM_2_INSPIRE_GET_RECORD_BY_ID);
               saxRequest.declareXPathNamespace(CSW_PREFIX, CSW_NAMESPACE);
                saxRequest.declareXPathNamespace(CSW_PREFIX, CSW_NAMESPACE);
                resultType=saxRequest.evaluateXPath(GET_RECORD_BY_ID_RESULT_TYPE_XPATH);
               pipedInput = translate2CIM(saxRequest,xsltRequestURL, parameters);
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(pipedInput);
            }else
            if(rootOperation.equals(OPERATION_GET_RECORDS)){
               xsltRequestURL = this.getClass().getResource(XSLT_INSPIRE_2_CIM_GET_RECORDS); 
               xsltResponseURL = this.getClass().getResource(XSLT_CIM_2_INSPIRE_GET_RECORDS);
               parameters=new SaxonXSLTParameter[1];
               parameters[0]=new SaxonXSLTParameter(XSLT_INSPIRE_CONFIG_PARAMETER,
               this.getClass().getResource(XML_INSPIRE_QUERYABLES_PATH).toString());
               saxRequest.declareXPathNamespace(CSW_PREFIX, CSW_NAMESPACE);
               saxRequest.declareXPathNamespace(CSW_PREFIX, CSW_NAMESPACE);
               resultType=saxRequest.evaluateXPath(GET_RECORDS_RESULT_TYPE_XPATH);
               pipedInput = translate2CIM(saxRequest,xsltRequestURL, parameters);
               
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(pipedInput);
            }else
            if(rootOperation.equals(OPERATION_DESCRIBE_RECORD)){
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(saxRequest.getXMLDocumentString());
            }else
            if(rootOperation.equals(OPERATION_HARVEST)){
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(saxRequest.getXMLDocumentString());
               xsltResponseURL = this.getClass().getResource(XSLT_CIM_2_INSPIRE_HARVEST);
            }else
            if(rootOperation.equals(OPERATION_TRANSACTION)){
               xsltRequestURL = this.getClass().getResource(XSLT_INSPIRE_2_CIM_TRANSACTION); 
               xsltResponseURL = this.getClass().getResource(XSLT_CIM_2_INSPIRE_TRANSACTION);
               parameters=new SaxonXSLTParameter[1];
               parameters[0]=new SaxonXSLTParameter(XSLT_INSPIRE_CONFIG_PARAMETER,
               this.getClass().getResource(XML_INSPIRE_QUERYABLES_PATH).toString()); 
               pipedInput = translate2CIM(saxRequest,xsltRequestURL, parameters);
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(pipedInput);
               
               unmarshalledRequestObj = JAXBUtil.getInstance().unmarshall(saxRequest.getXMLDocumentString());
               
            }
            else{
                logger.log(Level.SEVERE, "Inspire Operation Error"); 
               response.sendError(response.SC_BAD_REQUEST);
               return;
            }   
            
                
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Could not unmarshall request", ex);
            response.sendError(response.SC_BAD_REQUEST);
            return;
        }

        try {
            JAXBElement requestElement = (JAXBElement) unmarshalledRequestObj;
            JAXBElement responseEl = process(requestElement.getValue(), request);
            response.setContentType(MimeTypeConstants.APPLICATION_XML);
            Node cimResponse=JAXBUtil.getInstance().marshall(responseEl);
            if(xsltResponseURL!=null){
                pipedInput=this.translate2inspire(cimResponse, xsltResponseURL, resultType);
                IoUtil.copy(pipedInput, response.getOutputStream());
            }else
               JAXBUtil.getInstance().marshall(responseEl, response.getOutputStream());
            response.setStatus(response.SC_OK);
        } catch (Exception e) {
            try {
                ExceptionReport exRep = createException(e.getMessage(), INTERNAL_ERROR);
                response.setContentType(MimeTypeConstants.APPLICATION_XML);
                JAXBUtil.getInstance().marshall(exRep, response.getOutputStream());
            } catch (JAXBException ex) {
                logger.log(Level.WARNING, "Could not create error message", ex);
                response.sendError(response.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Process the request object {@code requestObj}.
     * Depending on the type of {@code requestObj}, calls the appropriate handler method.
     *
     * @param requestObj Reqest object.
     * @param request Servlet request.
     * @return Response JAXBElement.
     * @throws Exception
     */
    private JAXBElement process(Object requestObj, HttpServletRequest request) throws Exception {
        JAXBElement retValue;

        if (requestObj instanceof GetCapabilitiesType) {
            String servletUrl = request.getRequestURL().substring(0, (request.getRequestURL().length() - request.getServletPath().length()));
            retValue = processGetCapabilities((GetCapabilitiesType) requestObj, servletUrl);
        } else if (requestObj instanceof GetDomainType) {
            retValue = processGetDomain((GetDomainType) requestObj);
        } else if (requestObj instanceof GetRecordsType) {
            retValue = processGetRecords((GetRecordsType) requestObj);
        } else if (requestObj instanceof GetRecordByIdType) {
            retValue = processGetRecordById((GetRecordByIdType) requestObj);
        } else if (requestObj instanceof HarvestType) {
            retValue = processHarvest((HarvestType) requestObj);
        } else if (requestObj instanceof TransactionType) {
            retValue = processTransaction((TransactionType) requestObj);
        } else if (requestObj instanceof DescribeRecordType) {
            retValue = processDescribeRecord((DescribeRecordType) requestObj);
        } else {
            throw new Exception("Type not supported");
        }

        return retValue;
    }

    /**
     * Process a GetRecords request.
     *
     * @param getRecordsReq GetRecords request.
     * @return GetRecordResponse.
     * @throws ServiceExceptionReport
     */
    private JAXBElement processGetRecords(GetRecordsType getRecordsReq) throws ServiceExceptionReport {
        StopWatch sw = new StopWatch();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecordsReq);

        QueryManager qm = new QueryManager(requestContext);

        try {
            GetRecordsResponseType response = qm.query();

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Request processed in " + sw.getDurationAsMillis() + " milliseconds");
            }
            return OFactory.csw.createGetRecordsResponse(response);
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    /**
     * Process a GetRecordById request.
     *
     * @param getRecordByIdReq GetRecordById request.
     * @return GetRecordByIdResponseType
     * @throws ServiceExceptionReport
     */
    private JAXBElement processGetRecordById(GetRecordByIdType getRecordByIdReq) throws ServiceExceptionReport {
        StopWatch sw = new StopWatch();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecordByIdReq);

        GetRecordByIdResponseType response = new GetRecordByIdResponseType();

        try {
            List<JAXBElement<? extends IdentifiableType>> idents = new QueryManager(requestContext).getByIds();
            response.getAny().addAll(idents);

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Request processed in " + sw.getDurationAsMillis() + " milliseconds");
            }
            return OFactory.csw.createGetRecordByIdResponse(response);
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    /**
     * Helper method to create a ServiceExceptionReport
     * with information from ServiceException {@code ex}.
     *
     * @param ex Exception.
     * @return ServiceExceptionReport
     */
    private static ServiceExceptionReport createExceptionReport(ServiceException ex) {
        ExceptionReport exRep = new ExceptionReport();
        exRep.setLanguage(CommonProperties.getInstance().get("lang"));
        ExceptionType exType = new ExceptionType();
        exType.setExceptionCode(ex.getCode());
        exRep.getException().add(exType);
        return new ServiceExceptionReport(ex.getMessage(), exRep, ex);
    }

    /**
     * Process GetCapabilities request.
     *
     * @param getCapabilitiesType GetCapabilities request.
     * @param requestUrl Request URL.
     * @return Capabilities.
     * @throws ServiceExceptionReport
     */
    private JAXBElement processGetCapabilities(GetCapabilitiesType getCapabilitiesType, String requestUrl) throws ServiceExceptionReport {
        StopWatch sw = new StopWatch();
        try {
            JAXBElement capabilitiesEl = new CapabilitiesCSWReader().getCapabilities(requestUrl);

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Request processed in " + sw.getDurationAsMillis() + " milliseconds");
            }
            return capabilitiesEl;
        } catch (JAXBException ex) {
            String err = "Could not load Capabilities document";
            logger.log(Level.WARNING, err, ex);
            throw new ServiceExceptionReport(err, ex);
        }
    }

    /**
     * not supported
     * 
     * @param getDomainType GetDomain request.
     * @return nothing
     */
    private JAXBElement processGetDomain(GetDomainType getDomainType) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Process Harvest request.
     * @param harvest Harvest request.
     * @return HarvestResponse.
     * @throws ServiceExceptionReport
     */
    private JAXBElement processHarvest(HarvestType harvest) throws ServiceExceptionReport {
        StopWatch sw = new StopWatch();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(harvest);

        try {
            HarvestResponseType response = new HarvestService(requestContext).process();

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Request processed in " + sw.getDurationAsMillis() + " milliseconds");
            }
            return OFactory.csw.createHarvestResponse(response);
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    /**
     * Process Transaction request.
     *
     * @param transactionReq Transaction request.
     * @return TransactionResponse.
     * @throws ServiceExceptionReport
     */
    private JAXBElement processTransaction(TransactionType transactionReq) throws ServiceExceptionReport, JAXBException {
        StopWatch sw = new StopWatch();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(transactionReq);

        try {
            TransactionResponseType response = new TransactionService(requestContext).process();

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Request processed in " + sw.getDurationAsMillis() + " milliseconds");
            }
            return OFactory.csw.createTransactionResponse(response);
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    /**
     * Process DescribeRecord request.
     *
     * @param describeRecordType DescribeRecord request.
     * @return DescribeRecordResponse.
     * @throws ServiceExceptionReport
     */
    private JAXBElement processDescribeRecord(DescribeRecordType describeRecordType) throws ServiceExceptionReport {
        StopWatch sw = new StopWatch();
        DescribeRecordResponseType response = new DescribeRecordResponseType();
        SchemaComponentType schemaComp = new SchemaComponentType();
        schemaComp.setTargetNamespace(NamespaceConstants.RIM);
        schemaComp.setSchemaLanguage(NamespaceConstants.SCHEMA);

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(this.getClass().getResourceAsStream("/resources/rim.xsd"));
            schemaComp.getContent().add(doc.getDocumentElement());
        } catch (Exception ex) {
            String err = "Could not load RIM schema";
            logger.log(Level.WARNING, err, ex);
            throw new ServiceExceptionReport(err, ex);
        }

        response.getSchemaComponent().add(schemaComp);

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Request processed in " + sw.getDurationAsMillis() + " milliseconds");
        }
        return OFactory.csw.createDescribeRecordResponse(response);
    }

    /**
     * Process HTTP Get request.
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @throws Exception
     */
    private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object methodObject;

        String requestParameter = request.getParameter(getRequestParameterIgnoringCase(request.getParameterNames(), PARAM_REQUEST));
        if (requestParameter.equals(OPERATION_GET_CAPABILITIES)) {
            methodObject = extractGetCapabilitiesTypeFromHttpGet(request);
        } else if (requestParameter.equals(OPERATION_GET_RECORD_BY_ID)) {
            methodObject = extractGetRecordByIdTypeFromHttpGet(request);
        } else {
            String err = "User requested unsuppored operation: " + requestParameter;
            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new UnsupportedOperationException(err);
        }

        JAXBElement retValue = process(methodObject, request);
        
        
        
        
        
        
        response.setContentType(MimeTypeConstants.APPLICATION_XML);
        JAXBUtil.getInstance().marshall(retValue, response.getOutputStream());
        response.setStatus(response.SC_OK);
    }

    /**
     * Streams repository item through servlet response {@code response}.
     * Looks up repository item ID from {@code request}.
     *
     * @param request Servlet request.
     * @param response Servlet response.
     * @throws Exception
     *   - if ID was not provided in request.
     *   - if repository item with given ID was not found.
     *   - if mimeType of object was not found.
     */
    protected void streamItemFromRepository(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MimeTypeConstants.TEXT_XML); // TODO - read from ExtrinsicObject contenttype
        ServletOutputStream out = response.getOutputStream();
        String id = request.getParameter(PARAM_ID);

        if (id != null && !id.trim().equals("")) {
            RepositoryManager repoMngr = new RepositoryManager();
            File file = repoMngr.getFile(id);

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Request for repo file: " + file.getAbsolutePath());
            }

            if (file.exists()) {
                SqlPersistence persistence = new SqlPersistence();

                try {
                    String mimeType = persistence.getMimeType(id);

                    if (mimeType != null) {
                        response.setContentType(mimeType);

                        int b = 0;
                        FileInputStream fis = new FileInputStream(file);

                        while ((b = fis.read()) != -1) {
                            out.write(b);
                        }
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        String err = "User requested not existing ExtrinsicObject ID: " + id;
                        if (logger.isLoggable(Level.INFO)) {
                            logger.info(err);
                        }
                        out.print(createExceptionAsString(err, ErrorCodes.NOT_FOUND));
                    }
                } catch (SQLException ex) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    logger.log(Level.WARNING, "Could not load mimeType for " + id, ex);
                    out.print(createExceptionAsString("Could not get mimeType: " + ex.getMessage(), "Error"));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                String err = "Repository item not found";
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(err);
                }
                out.print(createExceptionAsString(err, "NotFound"));
            }

        } else {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("User did not provide an ID");
            }
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(createExceptionAsString("ID not provided", ErrorCodes.INVALID_REQUEST));
        }
    }

    /**
     * not implemented
     * 
     * @param request Servlet request.
     * @return Empty capabilities document.
     */
    private Object extractGetCapabilitiesTypeFromHttpGet(HttpServletRequest request) {
        GetCapabilitiesType getCapabilities = null;

        getCapabilities = new GetCapabilitiesType();
        getCapabilities.setService("urn:x-ogc:specification:csw-ebrim:Service:OGC-CSW:ebRIM");

        return getCapabilities;
    }

    /**
     * Creates a GetRecordById request from the servlet {@code request} parameters.
     *
     * @param request Servlet request.
     * @return GetRecordByIdType.
     * @throws Exception
     */
    private Object extractGetRecordByIdTypeFromHttpGet(HttpServletRequest request) throws Exception {
        GetRecordByIdType getRecordById = new GetRecordByIdType();

        String elementSetNameParam = request.getParameter(PARAM_ELEMENT_SET_NAME);
        if (elementSetNameParam == null) {
            elementSetNameParam = ElementSetType.SUMMARY.value();
        } else if ((elementSetNameParam.equals(ElementSetType.BRIEF.value())
                || elementSetNameParam.equals(ElementSetType.SUMMARY.value())
                || elementSetNameParam.equals(ElementSetType.FULL.value())) == false) {

            String err = "User did not provide a valid ElementSetName: " + elementSetNameParam;

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }

            throw new Exception(err);
        }

        String outputFormatParam = request.getParameter(PARAM_OUTPUT_FORMAT);
        if (outputFormatParam == null) {
            outputFormatParam = MimeTypeConstants.APPLICATION_XML;
        } else if (!outputFormatParam.equals(MimeTypeConstants.APPLICATION_XML)) {
            String err = "User did not provide a valid OutputFormat: " + outputFormatParam;
            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new Exception(err);
        }

        String outputSchemaParam = request.getParameter("outputSchema");
        if (outputSchemaParam == null) {
            outputSchemaParam = NamespaceConstants.CSW;
        } else if (!outputSchemaParam.equals(NamespaceConstants.CSW)) {
            String err = "User did not provide a valid outputSchema: " + outputSchemaParam;

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new Exception(err);
        }

        String id = request.getParameter(PARAM_ID);
        if (id == null || id.trim().equals("")) {
            String err = "User did not provide an ID";

            if (logger.isLoggable(Level.INFO)) {
                logger.info(err);
            }
            throw new Exception(err);
        }

        StringTokenizer tokenizer = new StringTokenizer(id, ",");

        ElementSetNameType elementSetName = new ElementSetNameType();
        elementSetName.setValue(ElementSetType.fromValue(elementSetNameParam));
        getRecordById.setElementSetName(elementSetName);

        getRecordById.setOutputFormat(outputFormatParam);
        getRecordById.setOutputSchema(outputSchemaParam);

        List<String> idList = getRecordById.getId();
        while (tokenizer.hasMoreTokens()) {
            idList.add(tokenizer.nextToken());
        }

        return getRecordById;
    }

    /**
     * Creates a new ExceptionReport.
     *
     * @param error Exception detail.
     * @param code Exception code.
     * @return new ExceptionReport.
     */
    private ExceptionReport createException(String error, String code) {
        ExceptionReport exRep = new ExceptionReport();
        exRep.setLanguage(CommonProperties.getInstance().get("lang"));
        exRep.setVersion("1.0");
        ExceptionType ex = new ExceptionType();
        ex.setExceptionCode(code);
        ex.getExceptionText().add(error);
        exRep.getException().add(ex);

        return exRep;
    }

    /**
     * Create an ExceptionReport as String representation.
     *
     * @param error Exception detail.
     * @param code Exception code.
     * @return ExceptionReport as String.
     */
    private String createExceptionAsString(String error, String code) {
        ExceptionReport exRep = new ExceptionReport();
        exRep.setLanguage(CommonProperties.getInstance().get("lang"));
        exRep.setVersion("1.0");
        ExceptionType exType = new ExceptionType();
        exType.setExceptionCode(code);
        exType.getExceptionText().add(error);
        exRep.getException().add(exType);

        try {
            return JAXBUtil.getInstance().marshallToStr(exRep);
        } catch (JAXBException ex) {
            logger.log(Level.SEVERE, "Error marshalling exception report", ex);
            return "<error>Oops. Could not Marshall the error message XML. Error message:" + error + "</error>";
        }
    }

    /**
     * Gets a value from {@code params} with the key {@code keyToSearch}
     * with a case insensitive key comparison.
     *
     * @param params Parameters.
     * @param keyToSearch Key to get value of.
     * @return String value if key was found else empty String.
     */
    private String getRequestParameterIgnoringCase(Enumeration params, String keyToSearch) {
        String paramName = "";
        String keyLowerCase = keyToSearch.toLowerCase();

        while (params.hasMoreElements()) {
            String currentParam = (String) params.nextElement();
            if (currentParam.toLowerCase().equals(keyLowerCase)) {
                paramName = currentParam;
                break;
            }
        }

        return paramName;
    }

    PipedInputStream translate2CIM(SaxonDocument saxonReq, URL xsltURL, SaxonXSLTParameter[] param) throws IOException, TranslationException, 
            URISyntaxException, XPathException, XPathExpressionException, SaxonApiException, XPathFactoryConfigurationException {

        SAXSource source = saxonReq.getSAXSource();
        PipedInputStream pipeInput = null;

        SAXSource xsltDoc = null;

        SaxonURIResolver uriResolver = null;

        try {
            uriResolver = new SaxonURIResolver(this.getClass().getResource(XSLT_INSPIRE_RESOURCE_PATH));
        } catch (URISyntaxException ex) {
            logger.log(Level.WARNING, "INSPIRE Transformation : URI Resolver Syntax Error  ", ex);
        }
        SaxonXSLT saxonUtil = new SaxonXSLT(uriResolver);
        try {
            xsltDoc = new SAXSource(new InputSource(xsltURL.openStream()));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not open the SensorML XSLT File : " + xsltURL.getPath(), ex);
            throw new TranslationException("Could not open the SensorML XSLT File : " + xsltURL.getPath());
        }
        
        try {
            pipeInput = saxonUtil.saxonXSLPipeTransform(source, xsltDoc, param);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not transform SensorML Document.", ex);
            throw new TranslationException("Could not transform SensorML Document.");
        }

        return pipeInput;
    }

    PipedInputStream translate2inspire(Node responseNode, URL xsltURL, String resultType) throws IOException, 
                            TranslationException, URISyntaxException, Exception {

        SAXSource source = new SAXSource(new InputSource(getNodeAsInputStream(responseNode)));
        PipedInputStream pipeInput = null;

        SAXSource xsltDoc = null;

        SaxonURIResolver uriResolver = null;

        try {
            uriResolver = new SaxonURIResolver(this.getClass().getResource(XSLT_INSPIRE_RESOURCE_PATH));
        } catch (URISyntaxException ex) {
            logger.log(Level.WARNING, "INSPIRE Transformation : URI Resolver Syntax Error  ", ex);
        }
        SaxonXSLT saxonUtil = new SaxonXSLT(uriResolver);
        try {
            xsltDoc = new SAXSource(new InputSource(xsltURL.openStream()));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not open the SensorML XSLT File : " + xsltURL.getPath(), ex);
            throw new TranslationException("Could not open the SensorML XSLT File : " + xsltURL.getPath());
        }

        try {
            pipeInput = saxonUtil.saxonXSLPipeTransform(source, xsltDoc, null);
            if(resultType != null)
                if(! resultType.equalsIgnoreCase(RESULT_TYPE_FULL) ){
                    if(resultType.equalsIgnoreCase(RESULT_TYPE_SUMMARY) || 
                       resultType.equalsIgnoreCase(RESULT_TYPE_BRIEF)){

                       URL xslrespTypetURL=this.getClass().getResource(XSLT_INSPIRE_RESOURCE_PATH
                               +XSLT_TYPE_PREFIX+resultType.toLowerCase()
                               +XSLT_TYPE_SUFFIX);
                       xsltDoc = new SAXSource(new InputSource(xslrespTypetURL.openStream()));
                       source = new SAXSource(new InputSource(pipeInput));
                       pipeInput = saxonUtil.saxonXSLPipeTransform(source, xsltDoc, null);
                    }

                }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not transform SensorML Document.", ex);
            throw new TranslationException("Could not transform SensorML Document.");
        }

        return pipeInput;
    }
    
    private InputStream getNodeAsInputStream(Node xml)
            throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StreamResult res = new StreamResult(out);
        transformer.transform(new DOMSource(xml), res);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
