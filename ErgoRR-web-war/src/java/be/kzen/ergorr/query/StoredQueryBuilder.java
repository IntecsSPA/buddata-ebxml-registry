package be.kzen.ergorr.query;

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
 *
 * @author Yaman Ustuntas
 */
public class StoredQueryBuilder {
    private static Logger logger = Logger.getLogger(StoredQueryBuilder.class.getName());
    private AdhocQueryType adhocParams;

    public StoredQueryBuilder(AdhocQueryType adhocParams) {
        this.adhocParams = adhocParams;
    }

    public JAXBElement<QueryType> build() throws SQLException, JAXBException {
        SqlPersistence service = new SqlPersistence();
        String sql = "select * from adhocquery where id ='" + adhocParams.getId() + "'";
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
            
            JAXBElement<QueryType> queryType = (JAXBElement<QueryType>) JAXBUtil.getInstance().unmarshall(gmlQueryStr);
            
            return queryType;
        } else {
            throw new SQLException("AdhocQuery with ID '" + adhocParams.getId() + "' not found");
        }
    }
}
