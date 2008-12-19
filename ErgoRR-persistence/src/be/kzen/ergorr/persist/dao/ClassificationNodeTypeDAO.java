package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.rim.ClassificationNodeType;
import be.kzen.ergorr.model.util.OFactory;
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

        if (!isBrief()) {
            xmlObject.setCode(result.getString("code"));
            xmlObject.setParent(result.getString("parent"));
            xmlObject.setPath(result.getString("path_"));
        }
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder(super.createValues());
        vals.append(",");
        appendStringValue(xmlObject.getCode(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getParent(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getPath(), vals);
        return vals.toString();
    }

    @Override
    protected String createUpdateValues() {
        StringBuilder vals = new StringBuilder(super.createUpdateValues());
        vals.append(",code=");
        appendStringValue(xmlObject.getCode(), vals);
        vals.append(",parent=");
        appendStringValue(xmlObject.getParent(), vals);
        vals.append(",path_=");
        appendStringValue(xmlObject.getPath(), vals);
        return vals.toString();
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
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("loading cn related");
        }

        if (CommonProperties.getInstance().getBoolean("db.loadNestedClassificationNodes")) {
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
            cnDAO.setConnection(connection);
            cNodes.add(cnDAO.newXmlObject(result));
        }

        return cNodes;
    }

    @Override
    public String getTableName() {
        return "classificationnode";
    }

    @Override
    public JAXBElement<ClassificationNodeType> createJAXBElement() {
        return OFactory.rim.createClassificationNode(xmlObject);
    }
}
