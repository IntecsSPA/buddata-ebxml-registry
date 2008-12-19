package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.model.rim.AdhocQueryType;
import be.kzen.ergorr.model.rim.QueryExpressionType;
import be.kzen.ergorr.model.util.OFactory;
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

        if (!isBrief()) {
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
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());
        vals.append(",");

        if (xmlObject.isSetQueryExpression()) {
            String queryLang = xmlObject.getQueryExpression().getQueryLanguage();
            Object content = xmlObject.getQueryExpression().getContent().get(0);
            String queryStr = "";

            appendStringValue(queryLang, vals);
            vals.append(",");

            if (queryLang.equals(RIMConstants.CN_QUERY_LANG_GML_FILTER)) {
                queryStr = (String) content;
            }
            appendStringValue(queryStr.trim(), vals);
        } else {
            vals.append("null,null");
        }

        return vals.toString();
    }

    @Override
    protected String createUpdateValues() {
        StringBuilder vals = new StringBuilder(super.createUpdateValues());
        vals.append(",querylanguage=");

        if (xmlObject.isSetQueryExpression()) {
            String queryLang = xmlObject.getQueryExpression().getQueryLanguage();
            Object content = xmlObject.getQueryExpression().getContent().get(0);
            String queryStr = "";

            appendStringValue(queryLang, vals);
            vals.append(",query=");

            if (queryLang.equals(RIMConstants.CN_QUERY_LANG_GML_FILTER)) {
                queryStr = (String) content;
            }
            appendStringValue(queryStr.trim(), vals);
        } else {
            vals.append("null,query=null");
        }

        return vals.toString();
    }

    @Override
    public String getTableName() {
        return "adhocquery";
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",querylanguage,query";
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
