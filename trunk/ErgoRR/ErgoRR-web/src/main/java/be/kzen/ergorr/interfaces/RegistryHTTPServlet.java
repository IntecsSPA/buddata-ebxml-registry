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
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.exceptions.ServiceException;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;
import be.kzen.ergorr.model.csw.CapabilitiesType;
import be.kzen.ergorr.model.csw.DescribeRecordResponseType;
import be.kzen.ergorr.model.csw.DescribeRecordType;
import be.kzen.ergorr.model.csw.ElementSetNameType;
import be.kzen.ergorr.model.csw.ElementSetType;
import be.kzen.ergorr.model.csw.GetCapabilitiesType;
import be.kzen.ergorr.model.csw.GetDomainResponseType;
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
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.HarvestService;
import be.kzen.ergorr.service.TransactionService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletInputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * CSW GetRepositoryItem HTTP interface.
 * 
 * @author Massimiliano Fanciulli
 */
public class RegistryHTTPServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(RegistryHTTPServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String serviceParameter,versionParameter;

       try
       {
           serviceParameter=request.getParameter("service");
           versionParameter=request.getParameter("version");

           if(serviceParameter.equals("CSW") &&
              versionParameter.equals("2.0.2"))
           {
               processGetRequest(request,response);
           }
           else response.sendError(response.SC_NOT_IMPLEMENTED);
       }
       catch(Exception e)
       {
           ExceptionReport exRep;
           
           try {
                exRep=createException(e.getMessage(),"500");
                response.setContentType("application/xml");

                JAXBUtil.getInstance().marshall(exRep, response.getOutputStream());
            } catch (JAXBException ex) {
                logger.log(Level.SEVERE, null, ex);
                response.sendError(response.SC_INTERNAL_SERVER_ERROR);
            }

       }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inStream;
        JAXBUtil jaxbUtil;
        Object unmarshalledObj;
        Object retValue;
        JAXBElement element;

        jaxbUtil=JAXBUtil.getInstance();
        inStream=request.getInputStream();
        try {
            unmarshalledObj=jaxbUtil.unmarshall(inStream);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            response.sendError(response.SC_BAD_REQUEST);
            return;
        }

        try
        {
            element=(JAXBElement) unmarshalledObj;
            retValue=process(element.getValue(),response);
            streamReturnValue(retValue,response);
            response.setStatus(response.SC_OK);
        }
        catch(Exception e)
        {
            ExceptionReport exRep;

           try {
                exRep=createException(e.getMessage(),"500");
                response.setContentType("application/xml");

                JAXBUtil.getInstance().marshall(exRep, response.getOutputStream());
            } catch (JAXBException ex) {
                logger.log(Level.SEVERE, null, ex);
                response.sendError(response.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private Object process(Object value,  HttpServletResponse response) throws Exception {
        Object retValue;
        String declaredType;

        declaredType=value.getClass().getCanonicalName();

        if(declaredType.equals("be.kzen.ergorr.model.csw.GetCapabilitiesType"))
            retValue=processGetCapabilities((GetCapabilitiesType) value);
        else if(declaredType.equals("be.kzen.ergorr.model.csw.GetDomainType"))
            retValue=processGetDomain((GetDomainType) value);
        else if(declaredType.equals("be.kzen.ergorr.model.csw.GetRecordsType"))
            retValue=processGetRecords((GetRecordsType) value);
        else if(declaredType.equals("be.kzen.ergorr.model.csw.GetRecordByIdType"))
            retValue=processGetRecordById((GetRecordByIdType) value);
        else if(declaredType.equals("be.kzen.ergorr.model.csw.HarvestType"))
            retValue=processHarvest((HarvestType) value);
        else if(declaredType.equals("be.kzen.ergorr.model.csw.TransactionType"))
            retValue=processTransaction((TransactionType) value);
        else if(declaredType.equals("be.kzen.ergorr.model.csw.DescribeRecordType"))
            retValue=processDescribeRecord((DescribeRecordType) value);
        else throw new Exception("Type not supported");

        return retValue;
    }

    private Object processGetRecords(GetRecordsType getRecordsReq) throws ServiceExceptionReport {
       long time = System.currentTimeMillis();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecordsReq);

        QueryManager qm = new QueryManager(requestContext);

        logger.log(Level.FINE, "Request processed in " + (System.currentTimeMillis() - time) + " milliseconds");

        try {
            return qm.query();
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    private Object processGetRecordById(GetRecordByIdType getRecordByIdReq) throws ServiceExceptionReport {
        long time = System.currentTimeMillis();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecordByIdReq);

        GetRecordByIdResponseType response = new GetRecordByIdResponseType();

        try {
            List<JAXBElement<? extends IdentifiableType>> idents = new QueryManager(requestContext).getByIds();
            response.getAny().addAll(idents);
            logger.log(Level.FINE, "Request processed in " + (System.currentTimeMillis() - time) + " milliseconds");
            return response;
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

     private static ServiceExceptionReport createExceptionReport(ServiceException ex) {
        ExceptionReport exRep = new ExceptionReport();
        exRep.setLanguage(CommonProperties.getInstance().get("lang"));
        ExceptionType exType = new ExceptionType();
        exType.setExceptionCode(ex.getCode());
        exRep.getException().add(exType);
        return new ServiceExceptionReport(ex.getMessage(), exRep, ex);
    }

    private Object processGetCapabilities(GetCapabilitiesType getCapabilitiesType) throws ServiceExceptionReport {
        long time = System.currentTimeMillis();
        try {
            JAXBElement capabilitiesEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(this.getClass().getResource("/resources/Capabilities.xml"));
            logger.log(Level.FINE, "Request processed in " + (System.currentTimeMillis() - time) + " milliseconds");
            return capabilitiesEl.getValue();
        } catch (JAXBException ex) {
            logger.log(Level.SEVERE, "Could not load Capabilities document", ex);
            throw new ServiceExceptionReport("Could not load capabilities document from file.", ex);
        }
    }

    private Object processGetDomain(GetDomainType getDomainType) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Object processHarvest(HarvestType harvest) throws ServiceExceptionReport {
        long time = System.currentTimeMillis();
        HarvestResponseType response;
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(harvest);

        try {
            response = new HarvestService(requestContext).process();
            logger.log(Level.FINE, "Request processed in " + (System.currentTimeMillis() - time) + " milliseconds");
            return response;
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    private Object processTransaction(TransactionType transactionReq) throws ServiceExceptionReport {
        long time = System.currentTimeMillis();
        TransactionResponseType response;
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(transactionReq);

        try {
            response = new TransactionService(requestContext).process();
            logger.log(Level.FINE, "Request processed in " + (System.currentTimeMillis() - time) + " milliseconds");
            return response;
        } catch (ServiceException ex) {
            throw createExceptionReport(ex);
        }
    }

    private Object processDescribeRecord(DescribeRecordType describeRecordType) throws ServiceExceptionReport {
        long time = System.currentTimeMillis();
        DescribeRecordResponseType response = new DescribeRecordResponseType();
        SchemaComponentType schemaComp = new SchemaComponentType();
        schemaComp.setTargetNamespace(NamespaceConstants.RIM);
        schemaComp.setSchemaLanguage(NamespaceConstants.SCHEMA);

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(this.getClass().getResourceAsStream("/resources/rim.xsd"));
            schemaComp.getContent().add(doc.getDocumentElement());
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Could not load RIM schema", ex);
            throw new ServiceExceptionReport("Could not load RIM schema", ex);
        }

        response.getSchemaComponent().add(schemaComp);
        logger.log(Level.FINE, "Request processed in " + (System.currentTimeMillis() - time) + " milliseconds");
        return response;
    }

      private void streamReturnValue(Object retValue, HttpServletResponse response) throws Exception {
        String canonicalName;
        JAXBElement element;

        canonicalName=retValue.getClass().getCanonicalName();

        if(canonicalName.equals("be.kzen.ergorr.model.csw.GetRecordsResponseType"))
            element=OFactory.csw.createGetRecordsResponse((GetRecordsResponseType) retValue);
        else if(canonicalName.equals("be.kzen.ergorr.model.csw.GetRecordByIdResponseType"))
           element=OFactory.csw.createGetRecordByIdResponse((GetRecordByIdResponseType) retValue);
         else if(canonicalName.equals("be.kzen.ergorr.model.csw.CapabilitiesType"))
           element=OFactory.csw.createCapabilities((CapabilitiesType) retValue);
         else if(canonicalName.equals("be.kzen.ergorr.model.csw.GetDomainResponseType"))
           element=OFactory.csw.createGetDomainResponse((GetDomainResponseType) retValue);
         else if(canonicalName.equals("be.kzen.ergorr.model.csw.HarvestType"))
           element=OFactory.csw.createHarvestResponse((HarvestResponseType) retValue);
         else if(canonicalName.equals("be.kzen.ergorr.model.csw.TransactionResponseType"))
           element=OFactory.csw.createTransactionResponse((TransactionResponseType) retValue);
         else if(canonicalName.equals("be.kzen.ergorr.model.csw.DescribeRecordResponseType"))
           element=OFactory.csw.createDescribeRecordResponse((DescribeRecordResponseType) retValue);
         else throw new Exception("Type not supported");

        response.setContentType("application/xml");
        JAXBUtil.getInstance().marshall(element,response.getOutputStream());
    }

    private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestParameter;
        Object methodObject;
        Object retValue;

        requestParameter=request.getParameter("REQUEST");
        if(requestParameter.equals("GetCapabilities"))
            methodObject=extractGetCapabilitiesTypeFromHttpGet(request);
        else if(requestParameter.equals("GetRecordById"))
            methodObject=extractGetRecordByIdTypeFromHttpGet(request);
        else throw new UnsupportedOperationException("Not yet implemented");

        retValue = process(methodObject, response);
        streamReturnValue(retValue,response);
        response.setStatus(response.SC_OK);
    }

    private Object extractGetCapabilitiesTypeFromHttpGet(HttpServletRequest request) {
        GetCapabilitiesType getCapabilities=null;
        
        getCapabilities=new GetCapabilitiesType();
        getCapabilities.setService("urn:x-ogc:specification:csw-ebrim:Service:OGC-CSW:ebRIM");

        return getCapabilities;
    }

    private Object extractGetRecordByIdTypeFromHttpGet(HttpServletRequest request) throws Exception {
        GetRecordByIdType getRecordById=null;
        String elementSetNameParameter;
        String outputFormatParameter;
        String outputSchemaParameter;
        String id;
        StringTokenizer tokenizer;
        List<String> idList;
        ElementSetNameType elementSetName;

        getRecordById=new GetRecordByIdType();

        elementSetNameParameter=request.getParameter("ElementSetName");
        if(elementSetNameParameter==null)
            elementSetNameParameter="summary";
        else if((elementSetNameParameter.equals("brief") ||
           elementSetNameParameter.equals("summary") ||
           elementSetNameParameter.equals("full"))==false)
            throw new Exception("Element Set Name contains a not allowed value");

        outputFormatParameter=request.getParameter("outputFormat");
        if(outputFormatParameter==null)
            outputFormatParameter="application/xml";
        else if((outputFormatParameter.equals("application/xml"))==false)
            throw new Exception("OutputFormat contains a not allowed or not supported value");

        outputSchemaParameter=request.getParameter("outputSchema");
        if(outputSchemaParameter==null)
            outputSchemaParameter="http://www.opengis.net/cat/csw/2.0.2";
        else if((outputSchemaParameter.equals("http://www.opengis.net/cat/csw/2.0.2"))==false)
            throw new Exception("Provided outputSchema is not supported");

        id=request.getParameter("id");
        if(id==null)
            throw new Exception("No id have been provided.");

        tokenizer=new StringTokenizer(id, ",");

        elementSetName=new ElementSetNameType();
        elementSetName.setValue(ElementSetType.fromValue(elementSetNameParameter));
        getRecordById.setElementSetName(elementSetName);

        getRecordById.setOutputFormat(outputFormatParameter);
        getRecordById.setOutputSchema(outputSchemaParameter);

        idList=getRecordById.getId();
        while(tokenizer.hasMoreTokens())
            idList.add(tokenizer.nextToken());
        
        return getRecordById;
    }

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
}