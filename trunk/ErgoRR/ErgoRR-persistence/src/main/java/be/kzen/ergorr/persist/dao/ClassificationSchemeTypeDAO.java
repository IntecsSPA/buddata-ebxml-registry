package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.rim.ClassificationSchemeType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
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
        return loadCompleteXmlObject(result);
    }

    @Override
    protected ClassificationSchemeType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        xmlObject.setIsInternal(result.getBoolean("isinternal"));
        xmlObject.setNodeType(result.getString("nodetype"));
        return xmlObject;
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setBoolean(8, xmlObject.isIsInternal());
        stmt.setString(9, xmlObject.getNodeType());
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",isinternal,nodetype";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".isinternal,").append(alias).append(".nodetype,").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?" : ",isinternal=?,nodetype=?");
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
        return "t_classificationscheme";
    }

    @Override
    public JAXBElement<ClassificationSchemeType> createJAXBElement() {
        return OFactory.rim.createClassificationScheme(xmlObject);
    }
}
