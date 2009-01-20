
package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
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
import be.kzen.ergorr.persist.InternalSlotTypes;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import be.kzen.ergorr.persist.service.SqlPersistence;
import be.kzen.ergorr.query.QueryManager;
import be.kzen.ergorr.service.HarvestService;
import be.kzen.ergorr.service.TransactionService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Yaman Ustuntas
 */
public class CswBackendClient {
    private static Logger logger = Logger.getLogger(CswBackendClient.class.getName());
    private InternalSlotTypes slotTypes;
    private DbConnectionParams dbConnParams;
    
    public CswBackendClient(DbConnectionParams dbConnParams) {
        this.dbConnParams = dbConnParams;
        init();
    }

    public CapabilitiesType getCapabilities(GetCapabilitiesType getCapabilitiesReq) throws ServiceExceptionReport {
        try {
            JAXBElement capabilitiesEl = (JAXBElement) JAXBUtil.getInstance().unmarshall(this.getClass().getResource("Capabilities.xml"));
            return (CapabilitiesType) capabilitiesEl.getValue();
        } catch (JAXBException ex) {
            throw new ServiceExceptionReport("Could not load capabilities document from file.", ex);
        }
    }

    public GetRecordsResponseType getRecords(GetRecordsType getRecordsReq) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.putParam(InternalConstants.DB_CONNECTION_PARAMS, dbConnParams);
        requestContext.setRequest(getRecordsReq);

        QueryManager qm = new QueryManager(requestContext);
        return qm.query();
    }

    public GetRecordByIdResponseType getRecordById(GetRecordByIdType getRecordByIdReq) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.putParam(InternalConstants.DB_CONNECTION_PARAMS, dbConnParams);
        requestContext.setRequest(getRecordByIdReq);

        GetRecordByIdResponseType response = new GetRecordByIdResponseType();
        List<JAXBElement<? extends IdentifiableType>> idents = new QueryManager(requestContext).getByIds(getRecordByIdReq.getId());
        response.getAny().addAll(idents);

        return response;
    }

    public GetDomainResponseType getDomain(GetDomainType getDomainReq) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Not supported");
    }

    public HarvestResponseType harvest(HarvestType body) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.putParam(InternalConstants.DB_CONNECTION_PARAMS, dbConnParams);
        requestContext.setRequest(body);
        return new HarvestService(requestContext).process();
    }

    public TransactionResponseType transact(TransactionType transactionReq) throws ServiceExceptionReport {
        RequestContext requestContext = new RequestContext();
        requestContext.putParam(InternalConstants.DB_CONNECTION_PARAMS, dbConnParams);
        requestContext.setRequest(transactionReq);

        return new TransactionService(requestContext).process();
    }

    public DescribeRecordResponseType getRecordDescription(DescribeRecordType describeRecordReq) throws ServiceExceptionReport {
        throw new ServiceExceptionReport("Not supported");
    }

    private void init() {
        slotTypes = InternalSlotTypes.getInstance();

        if (slotTypes.getSlotTypeSize() == 0) {
            RequestContext requestContext = new RequestContext();
            requestContext.putParam(InternalConstants.DB_CONNECTION_PARAMS, dbConnParams);
            SqlPersistence service = new SqlPersistence(requestContext);

            try {
                slotTypes.loadSlots();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Could not load slot types", ex);
            }
        }
    }
}
