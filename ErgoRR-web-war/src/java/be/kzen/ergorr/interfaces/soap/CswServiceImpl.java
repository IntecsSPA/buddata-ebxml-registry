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

import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.commons.InternalConstants;
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
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.HarvestService;
import be.kzen.ergorr.service.TransactionService;
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.logging.LoggerSetup;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.WebServiceContext;

/**
 * SOAP service implementation for CSW.
 * 
 * @author Yaman Ustuntas
 */
@WebService(serviceName = "webservice", portName = "CswPort",
targetNamespace = "http://www.kzen.be/ergorr/interfaces/soap",
endpointInterface = "be.kzen.ergorr.interfaces.soap.CswPortType")
public class CswServiceImpl implements CswPortType {

    private static Logger logger = Logger.getLogger(CswServiceImpl.class.getName());
    private InternalSlotTypes slotTypes;
    @Resource
    private WebServiceContext wsContext;
    
    public CapabilitiesType cswGetCapabilities(GetCapabilitiesType getCapabilitiesReq) throws ServiceExceptionReport {
        try {
            JAXBElement capabilitiesEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(this.getClass().getResource("Capabilities.xml"));
            return (CapabilitiesType) capabilitiesEl.getValue();
        } catch (JAXBException ex) {
            throw new ServiceExceptionReport("Could not load capabilities document from file.", ex);
        }
    }

    public GetRecordsResponseType cswGetRecords(GetRecordsType getRecordsReq) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecordsReq);

        QueryManager qm = new QueryManager(requestContext);
        return qm.query();
    }

    public GetRecordByIdResponseType cswGetRecordById(GetRecordByIdType getRecordByIdReq) throws ServiceExceptionReport {

//        Iterator<String> it = wsContext.getMessageContext().keySet().iterator();
//        logger.info("-----------------------------------");
//        while (it.hasNext()) {
//            String key = it.next();
//            Object o = wsContext.getMessageContext().get(key);
//            logger.info("key: " + key);
//            if (o != null) {
//                logger.info("type: " + o.getClass().getName());
//                logger.info("content: " + o.toString());
//            } else {
//                logger.info("null");
//            }
//            logger.info("-------");
//        }
//        logger.info("-----------------------------------");

        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(getRecordByIdReq);

        GetRecordByIdResponseType response = new GetRecordByIdResponseType();
        List<JAXBElement<? extends IdentifiableType>> idents = new QueryManager(requestContext).getByIds(getRecordByIdReq.getId());
        response.getAny().addAll(idents);

        return response;
    }

    public GetDomainResponseType cswGetDomain(GetDomainType getDomainReq) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Not supported");
    }

    public HarvestResponseType cswHarvest(HarvestType body) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(body);
        return new HarvestService(requestContext).process();
    }

    public TransactionResponseType cswTransaction(TransactionType transactionReq) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(transactionReq);

        return new TransactionService(requestContext).process();
    }

    public DescribeRecordResponseType cswDescribeRecord(DescribeRecordType describeRecordReq) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Not supported");
    }

    /**
     * Put stuff here which should be initialized once.
     * 
     * The web container calls the PostConstruct twice.
     * Once when the application is deployed and once the service is invoked for the first time.
     * This code will prevent the init from run twice.
     */
    @PostConstruct
    protected void init() {
        slotTypes = InternalSlotTypes.getInstance();

        if (slotTypes.getSlotTypeSize() > 0) {
            slotTypes.clear(); // now we can remove the dummy
            SqlPersistence service = new SqlPersistence();
//            LoggerSetup.setup();
            logger.info(">>> start init");
            logger.info("loading slot types cache from database");
            try {
                slotTypes.setSlotMap(service.querySlotTypes());
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Could not load slot types", ex);
            }
            logger.info("  non-string slots: " + slotTypes.getSlotTypeSize());
            logger.info(">>> end init");
        } else {
            try {
                slotTypes.putSlotType("dummyslot", "string");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Could not put dummy slot type", ex);
            }
        }
    }
}
