package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.commons.CommonProperties;
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
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * Client for the ebRIM CSW client Web Service interface.
 * 
 * @author Yaman Ustuntas
 */
public class CswClient {
    private CswPortType service;

    /**
     * Constructor
     * Uses the property 'service.url' defined in <code>CommonProperties</code>.
     * 
     * @throws java.net.MalformedURLException
     */
    public CswClient() throws MalformedURLException {
        this(new URL(CommonProperties.getInstance().get("service.url")));
    }
    
    /**
     * Constructor
     * 
     * @param serviceUrl URL of the CSW web service.
     */
    public CswClient(URL serviceUrl) {
        QName serviceQName = new QName("http://www.kzen.be/ergorr/interfaces/soap", "webservice");
        service = new CswService(serviceUrl, serviceQName).getCswPort();
    }

    /**
     * Invokes the CSW GetRecords service operation.
     * 
     * @param request 
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public GetRecordsResponseType getRecords(GetRecordsType request) throws ServiceExceptionReport {
        return service.cswGetRecords(request);
    }
    
    /**
     * Invokes the CSW GetRecordById service operation.
     * 
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public GetRecordByIdResponseType getRecordById(GetRecordByIdType request) throws ServiceExceptionReport {
        return service.cswGetRecordById(request);
    }
    
    /**
     * Invokes the CSW Transaction service operation.
     * 
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public TransactionResponseType transact(TransactionType request) throws ServiceExceptionReport {
        return service.cswTransaction(request);
    }
    
    /**
     * Invokes the CSW Harvest service operation.
     * 
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public HarvestResponseType harvest(HarvestType request) throws ServiceExceptionReport {
        return service.cswHarvest(request);
    }
    
    /**
     * Invokes the CSW RecordDescription service operation.
     * 
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public DescribeRecordResponseType getRecordDescription(DescribeRecordType request) throws ServiceExceptionReport {
        return service.cswDescribeRecord(request);
    }
    
    /**
     * Invokes the CSW Domain service operation.
     * 
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public GetDomainResponseType getDomain(GetDomainType request) throws ServiceExceptionReport {
        return service.cswGetDomain(request);
    }
    
    /**
     * Invokes the CSW Capabilities service operation.
     * 
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public CapabilitiesType getCapabilities(GetCapabilitiesType request) throws ServiceExceptionReport {
        return service.cswGetCapabilities(request);
    }
}
