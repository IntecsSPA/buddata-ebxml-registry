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
import be.kzen.ergorr.interfaces.soap.csw.CswClient;
import be.kzen.ergorr.interfaces.soap.csw.ServiceExceptionReport;

/**
 * Client for the ebRIM CSW client Web Service interface.
 * 
 * @author Yaman Ustuntas
 */
public class CswSoapClient implements CswClient {
    private CswClientPortType service;

    /**
     * Constructor
     * Uses the property 'service.url' defined in <code>CommonProperties</code>.
     * 
     * @throws java.net.MalformedURLException
     */
    public CswSoapClient() throws MalformedURLException {
        this(new URL(CommonProperties.getInstance().get("service.url")));
    }
    
    /**
     * Constructor
     * 
     * @param serviceUrl URL of the CSW web service.
     */
    public CswSoapClient(URL serviceUrl) {
        QName serviceQName = new QName("http://www.kzen.be/ergorr/interfaces/soap", "webservice");
        service = new CswClientService(serviceUrl, serviceQName).getCswPort();
    }

    /**
     * {@inheritDoc}
     */
    public GetRecordsResponseType getRecords(GetRecordsType request) throws ServiceExceptionReport {
        return service.cswGetRecords(request);
    }
    
    /**
     * {@inheritDoc}
     */
    public GetRecordByIdResponseType getRecordById(GetRecordByIdType request) throws ServiceExceptionReport {
        return service.cswGetRecordById(request);
    }
    
    /**
     * {@inheritDoc}
     */
    public TransactionResponseType transact(TransactionType request) throws ServiceExceptionReport {
        return service.cswTransaction(request);
    }
    
    /**
     * {@inheritDoc}
     */
    public HarvestResponseType harvest(HarvestType request) throws ServiceExceptionReport {
        return service.cswHarvest(request);
    }
    
    /**
     * {@inheritDoc}
     */
    public DescribeRecordResponseType getRecordDescription(DescribeRecordType request) throws ServiceExceptionReport {
        return service.cswDescribeRecord(request);
    }
    
    /**
     * {@inheritDoc}
     */
    public GetDomainResponseType getDomain(GetDomainType request) throws ServiceExceptionReport {
        return service.cswGetDomain(request);
    }
    
    /**
     * {@inheritDoc}
     */
    public CapabilitiesType getCapabilities(GetCapabilitiesType request) throws ServiceExceptionReport {
        return service.cswGetCapabilities(request);
    }
}
