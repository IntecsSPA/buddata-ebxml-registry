
package be.kzen.ergorr.interfaces.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import be.kzen.ergorr.model.csw.CapabilitiesType;
import be.kzen.ergorr.model.csw.DescribeRecordResponseType;
import be.kzen.ergorr.model.csw.DescribeRecordType;
import be.kzen.ergorr.model.csw.GetCapabilitiesType;
import be.kzen.ergorr.model.csw.GetDomainResponseType;
import be.kzen.ergorr.model.csw.GetDomainType;
import be.kzen.ergorr.model.csw.GetRecordByIdResponseType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.HarvestResponseType;
import be.kzen.ergorr.model.csw.HarvestType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.4-hudson-208-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "CswPortType", targetNamespace = "http://www.kzen.be/ergorr/interfaces/soap")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    be.kzen.ergorr.model.gml.ObjectFactory.class,
    be.kzen.ergorr.model.purl.terms.ObjectFactory.class,
    be.kzen.ergorr.model.wrs.ObjectFactory.class,
    be.kzen.ergorr.model.rim.ObjectFactory.class,
    be.kzen.ergorr.model.ows.ObjectFactory.class,
    be.kzen.ergorr.model.csw.ObjectFactory.class,
    be.kzen.ergorr.model.ogc.ObjectFactory.class,
    be.kzen.ergorr.model.purl.elements.ObjectFactory.class
})
public interface CswClientPortType {


    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.CapabilitiesType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.getCapabilities", action = "http://www.opengis.net/cat/csw/2.0.2/requests#GetCapabilities")
    @WebResult(name = "Capabilities", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public CapabilitiesType cswGetCapabilities(
        @WebParam(name = "GetCapabilities", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        GetCapabilitiesType body)
        throws ServiceExceptionReport
    ;

    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.DescribeRecordResponseType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.describeRecord", action = "http://www.opengis.net/cat/csw/2.0.2/requests#DescribeRecord")
    @WebResult(name = "DescribeRecordResponse", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public DescribeRecordResponseType cswDescribeRecord(
        @WebParam(name = "DescribeRecord", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        DescribeRecordType body)
        throws ServiceExceptionReport
    ;

    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.GetRecordsResponseType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.getRecords", action = "http://www.opengis.net/cat/csw/2.0.2/requests#GetRecords")
    @WebResult(name = "GetRecordsResponse", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public GetRecordsResponseType cswGetRecords(
        @WebParam(name = "GetRecords", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        GetRecordsType body)
        throws ServiceExceptionReport
    ;

    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.GetRecordByIdResponseType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.getRecordById", action = "http://www.opengis.net/cat/csw/2.0.2/requests#GetRecordsById")
    @WebResult(name = "GetRecordByIdResponse", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public GetRecordByIdResponseType cswGetRecordById(
        @WebParam(name = "GetRecordById", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        GetRecordByIdType body)
        throws ServiceExceptionReport
    ;

    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.GetDomainResponseType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.getDomain", action = "http://www.opengis.net/cat/csw/2.0.2/requests#GetDomain")
    @WebResult(name = "GetDomainResponse", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public GetDomainResponseType cswGetDomain(
        @WebParam(name = "GetDomain", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        GetDomainType body)
        throws ServiceExceptionReport
    ;

    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.HarvestResponseType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.harvest", action = "http://www.opengis.net/cat/csw/2.0.2/requests#Harvest")
    @WebResult(name = "HarvestResponse", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public HarvestResponseType cswHarvest(
        @WebParam(name = "Harvest", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        HarvestType body)
        throws ServiceExceptionReport
    ;

    /**
     * 
     * @param body
     * @return
     *     returns be.kzen.ergorr.model.csw.TransactionResponseType
     * @throws ServiceExceptionReport
     */
    @WebMethod(operationName = "csw.transaction", action = "http://www.opengis.net/cat/csw/2.0.2/requests#Transaction")
    @WebResult(name = "TransactionResponse", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
    public TransactionResponseType cswTransaction(
        @WebParam(name = "Transaction", targetNamespace = "http://www.opengis.net/cat/csw/2.0.2", partName = "Body")
        TransactionType body)
        throws ServiceExceptionReport
    ;

}
