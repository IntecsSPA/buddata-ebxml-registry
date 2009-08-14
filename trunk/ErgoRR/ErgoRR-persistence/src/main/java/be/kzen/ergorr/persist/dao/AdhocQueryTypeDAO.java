package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.QueryExpressionType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class AdhocQueryTypeDAO extends RegistryObjectTypeDAO<AdhocQueryType> {

    private static Logger logger = Logger.getLogger(AdhocQueryTypeDAO.class.getName());

    public AdhocQueryTypeDAO() {
    }

    public AdhocQueryTypeDAO(AdhocQueryType aqXml) {
        super(aqXml);
    }

    @Override
    public AdhocQueryType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new AdhocQueryType();
        return loadCompleteXmlObject(result);
    }

    @Override
    public AdhocQueryType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (context.getParam(InternalConstants.RETURN_NESTED_OBJECTS, Boolean.class)) {
            QueryExpressionType queryEx = new QueryExpressionType();
            String queryLang = result.getString("querylanguage");
            String query = result.getString("query");
            queryEx.setQueryLanguage(queryLang);

//            try {
//                JAXBElement<QueryType> queryEl = (JAXBElement<QueryType>) JAXBUtil.getInstance().unmarshall(query);
//                queryEx.getContent().add(queryEl);
                queryEx.getContent().add(query);
//            } catch (JAXBException ex) {
//                throw new SQLException("Could not load GML Filter query from database", ex);
//            }

            xmlObject.setQueryExpression(queryEx);
        }

        return xmlObject;
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getQueryExpression().getQueryLanguage());
        Object content = xmlObject.getQueryExpression().getContent().get(0);
        stmt.setString(9,(String) content);
    }

    @Override
    public String getTableName() {
        return "t_adhocquery";
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",querylanguage,query";
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?" : ",querylanguage=?,query=?");
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".querylanguage,").append(alias).append(".query").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<AdhocQueryType> createJAXBElement() {
        return OFactory.rim.createAdhocQuery(xmlObject);
    }
}
