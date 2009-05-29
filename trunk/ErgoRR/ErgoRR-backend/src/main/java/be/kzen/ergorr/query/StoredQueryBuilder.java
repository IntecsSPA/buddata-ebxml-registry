package be.kzen.ergorr.query;

import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.model.csw.QueryType;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.rim.ValueListType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.service.SqlPersistence;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 * Builds stored queries from AdhocQueries.
 *
 * @author Yaman Ustuntas
 */
public class StoredQueryBuilder {
    private static Logger logger = Logger.getLogger(StoredQueryBuilder.class.getName());
    private AdhocQueryType adhocParams;
    private RequestContext context;

    /**
     * Constructor
     *
     * @param adhocParams Adhoc query request.
     */
    public StoredQueryBuilder(AdhocQueryType adhocParams, RequestContext context) {
        this.adhocParams = adhocParams;
        this.context = context;
    }

    /**
     * Build and wraps an AdhocQuery in an OGC query.
     * Replaces attributes from the stored query with the values
     * from the <code>adhocParams</code> parameters.
     *
     * @return OGC Query.
     * @throws java.sql.SQLException
     * @throws javax.xml.bind.JAXBException
     */
    public JAXBElement<QueryType> build() throws SQLException, JAXBException {
        SqlPersistence service = new SqlPersistence(context);
        String sql = "select * from t_adhocquery where id ='" + adhocParams.getId() + "'";
        List<JAXBElement<? extends IdentifiableType>> adhocQueryEls = service.query(sql, null, AdhocQueryType.class);

        if (adhocQueryEls.size() > 0) {
            logger.info("found Adhoc query in database");
            AdhocQueryType adhocQuery = (AdhocQueryType) adhocQueryEls.get(0).getValue();

            String gmlQueryStr = (String) adhocQuery.getQueryExpression().getContent().get(0);
            
            List<SlotType> slots = adhocParams.getSlot();
            
            for (SlotType slot : slots) {
                if (slot.isSetValueList()) {
                    ValueListType valList = slot.getValueList().getValue();
                    
                    if (valList.isSetValue()) {
                        String replaceValue = "\\$" + slot.getName() + "\\$";
                        gmlQueryStr = gmlQueryStr.replaceAll(replaceValue, valList.getValue().get(0));
                    }
                }
            }
            
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, gmlQueryStr);
            }
            
            return (JAXBElement<QueryType>) JAXBUtil.getInstance().unmarshall(gmlQueryStr);
        } else {
            throw new SQLException("AdhocQuery with ID '" + adhocParams.getId() + "' not found");
        }
    }
}
