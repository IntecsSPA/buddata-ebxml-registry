
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class ClassificationSchemeTypeDAO extends RegistryObjectTypeDAO<ClassificationSchemeType> {
    private static Logger logger = Logger.getLogger(ClassificationSchemeTypeDAO.class.getName());
    public ClassificationSchemeTypeDAO() {
    }
    
    public ClassificationSchemeTypeDAO(ClassificationSchemeType csXml) {
        super(csXml);
    }

    @Override
    public ClassificationSchemeType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new ClassificationSchemeType();
        return loadXmlObject(result);
    }

    @Override
    protected ClassificationSchemeType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        xmlObject.setIsInternal(result.getBoolean("isinternal"));
        xmlObject.setNodeType(result.getString("nodetype"));
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());
        vals.append(",");
        appendBooleanValue(xmlObject.isIsInternal(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getNodeType(), vals);
        return vals.toString();
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",isinternal,nodetype";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.isEmpty()) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias)
                    .append(".isinternal,").append(alias).append(".nodetype,").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();
        
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("loading child classificationnodes");
        }

        if (CommonProperties.getInstance().getBoolean("db.loadNestedClassificationNodes")) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("CS loadNested true");
            }
            ClassificationNodeTypeDAO cnDAO = new ClassificationNodeTypeDAO();
            cnDAO.setConnection(connection);
            xmlObject.getClassificationNode().addAll(cnDAO.getAllByParent(xmlObject.getId()));
        }
    }

    @Override
    public String getTableName() {
        return "classificationscheme";
    }
    
    @Override
    public JAXBElement<ClassificationSchemeType> createJAXBElement() {
            return OFactory.rim.createClassificationScheme(xmlObject);
    }
}
