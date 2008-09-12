/*
 * Project: Buddata ebXML RegRep
 * Class: CswServiceImpl.java
 * Copyright (C) 2008 Yaman Ustuntas
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
package be.kzen.ergorr.interfaces.soap;

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
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.dao.RimDAO;
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.HarvestService;
import be.kzen.ergorr.service.TransactionService;
import be.kzen.ergorr.commons.SlotTypes;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * SOAP service implementation for CSW.
 * 
 * @author Yaman Ustuntas
 */
@WebService(serviceName = "webservice", portName = "CswPort",
targetNamespace = "http://www.kzen.be/ergorr/interfaces/soap",
endpointInterface = "be.kzen.ergorr.interfaces.soap.CswPortType")
public class CswServiceImpl implements CswPortType {

    private static Logger log = Logger.getLogger(CswServiceImpl.class);
    
    // this singleton is initialized in init(). maybe not the best to
    // keep the instance here though. It should be kept somewhere so it doesn't get GC'ed.
    private SlotTypes slotTypes;
    
    @EJB
    RimDAO rimDao;

//    @Resource
//    private WebServiceContext wsContext;
    
    public CapabilitiesType cswGetCapabilities(GetCapabilitiesType getCapabilitiesReq) throws ServiceExceptionReport {
        try {
            JAXBElement capabilitiesEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(this.getClass().getResource("Capabilities.xml"));
            return (CapabilitiesType) capabilitiesEl.getValue();
        } catch (JAXBException ex) {
            throw new ServiceExceptionReport("Could not load capabilities document from file.", ex);
        }
    }

    public GetRecordsResponseType cswGetRecords(GetRecordsType getRecordsReq) throws ServiceExceptionReport {
        RequestContext rc = new RequestContext();
        rc.setRimDAO(rimDao);
        rc.setRequest(getRecordsReq);

        QueryManager qm = new QueryManager(rc);
        return qm.query();
    }

    public GetRecordByIdResponseType cswGetRecordById(GetRecordByIdType getRecordByIdReq) throws ServiceExceptionReport {
        RequestContext rc = new RequestContext();
        rc.setRimDAO(rimDao);
        rc.setRequest(getRecordByIdReq);
        
        GetRecordByIdResponseType response = new GetRecordByIdResponseType();
        List<JAXBElement<? extends IdentifiableType>> idents = new QueryManager(rc).getByIds(getRecordByIdReq.getId());
        response.getAny().addAll(idents);

        return response;
    }

    public GetDomainResponseType cswGetDomain(GetDomainType getDomainReq) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Not supported");
    }

    public HarvestResponseType cswHarvest(HarvestType body) throws ServiceExceptionReport {
        RequestContext rc = new RequestContext();
        rc.setRimDAO(rimDao);
        rc.setRequest(body);
        return new HarvestService(rc).process();
    }

    public TransactionResponseType cswTransaction(TransactionType transactionReq) throws ServiceExceptionReport {
        RequestContext rc = new RequestContext();
        rc.setRimDAO(rimDao);
        rc.setRequest(transactionReq);

        return new TransactionService(rc).process();
    }
    
    public DescribeRecordResponseType cswDescribeRecord(DescribeRecordType describeRecordReq) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Not supported");
    }

    /**
     * Put stuff here which should be initialized once.
     * 
     * The web container calls the PostConstruct twice.
     * Once when the application is deployed and once the service is invoked for the first time.
     * When it is called while deploying the dependency injection is not complete
     * so we leave the initialization work for the second call. In this case when
     * <code>rimDao</code> is not equal to <code>null</code>.
     */
    @PostConstruct
    protected void init() {
        if (rimDao != null) {
            log.info(">>> start pre construct");
            log.info("init slot types");
            slotTypes = SlotTypes.getInstance();
            slotTypes.setSlotMap(rimDao.getSlotTypes());
            log.info("  slots: " + slotTypes.getSlotTypeSize());
            log.info("init log4j");
            PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("log4j.properties"));
            log.info(">>> end pre construct");
        }
    }
}
