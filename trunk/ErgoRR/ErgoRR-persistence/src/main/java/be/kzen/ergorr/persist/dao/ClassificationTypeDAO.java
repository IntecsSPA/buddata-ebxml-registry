package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class ClassificationTypeDAO extends RegistryObjectTypeDAO<ClassificationType> {

    public ClassificationTypeDAO() {
    }

    public ClassificationTypeDAO(ClassificationType clXml) {
        super(clXml);
    }

    public void addClassifications(RegistryObjectType parent) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getParamList()).append(" from ").append(getTableName()).append(" where classifiedobject = '").append(parent.getId()).append("'");
        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            parent.getClassification().add(newXmlObject(result));
        }
    }

    public void deleteClassifications(RegistryObjectType parent) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(getTableName()).append(" where classifiedobject='").append(parent.getId()).append("';");
        stmt.execute(sql.toString());
    }

    @Override
    public ClassificationType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new ClassificationType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected ClassificationType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        if (!isBrief()) {
            xmlObject.setClassificationNode(result.getString("classificationnode"));
            xmlObject.setClassificationScheme(result.getString("classificationscheme"));
            xmlObject.setClassifiedObject(result.getString("classifiedobject"));
            xmlObject.setNodeRepresentation(result.getString("noderepresentation"));
        }
        return xmlObject;
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getClassificationNode());
        stmt.setString(9, xmlObject.getClassificationScheme());
        stmt.setString(10, xmlObject.getClassifiedObject());
        stmt.setString(11, xmlObject.getNodeRepresentation());
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",classificationnode,classificationscheme,classifiedobject,noderepresentation";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".classificationnode,").append(alias).append(".classificationscheme,").append(alias).append(".classifiedobject,").append(alias).append(".noderepresentation").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?,?" : ",classificationnode=?,classificationscheme=?,classifiedobject=?,noderepresentation=?");
    }

    @Override
    public String getTableName() {
        return "t_classification";
    }

    @Override
    public JAXBElement<ClassificationType> createJAXBElement() {
        return OFactory.rim.createClassification(xmlObject);
    }
}
