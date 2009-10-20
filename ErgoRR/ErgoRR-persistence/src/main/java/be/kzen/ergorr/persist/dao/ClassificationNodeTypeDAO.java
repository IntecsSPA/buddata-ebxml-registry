package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class ClassificationNodeTypeDAO extends RegistryObjectTypeDAO<ClassificationNodeType> {

    private static Logger logger = Logger.getLogger(ClassificationNodeTypeDAO.class.getName());

    public ClassificationNodeTypeDAO() {
    }

    public ClassificationNodeTypeDAO(ClassificationNodeType cnXml) {
        super(cnXml);
    }

    @Override
    public ClassificationNodeType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new ClassificationNodeType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected ClassificationNodeType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        xmlObject.setCode(result.getString("code"));
        xmlObject.setParent(result.getString("parent"));
        xmlObject.setPath(result.getString("path_"));
        return xmlObject;
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getCode());
        stmt.setString(9, xmlObject.getParent());
        stmt.setString(10, xmlObject.getPath());
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",code,parent,path_";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".code,").append(alias).append(".parent,").append(alias).append(".path_").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?" : ",code=?,parent=?,path_=?");
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("loading cn related");
        }

        if (returnNestedObjects() && CommonProperties.getInstance().getBoolean("db.loadNestedClassificationNodes")) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("CN loadNested true, parent id: " + xmlObject.getId());
            }

            xmlObject.getClassificationNode().addAll(getAllByParent(xmlObject.getId()));
        }
    }

    public List<ClassificationNodeType> getAllByParent(String parentId) throws SQLException {
        List<ClassificationNodeType> cNodes = new ArrayList<ClassificationNodeType>();

        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getParamList()).append(" from ").append(getTableName()).append(" where parent ='").append(parentId).append("'");

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Select CN by parents SQL: " + sql.toString());
        }

        Statement stmt = connection.createStatement();

        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            ClassificationNodeTypeDAO cnDAO = new ClassificationNodeTypeDAO();
            cnDAO.setContext(context);
            cnDAO.setConnection(connection);
            cNodes.add(cnDAO.newXmlObject(result));
        }

        return cNodes;
    }

    @Override
    public String getTableName() {
        return "t_classificationnode";
    }

    @Override
    public JAXBElement<ClassificationNodeType> createJAXBElement() {
        return OFactory.rim.createClassificationNode(xmlObject);
    }
}