package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.util.OFactory;
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
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());

        vals.append(",");
        appendStringValue(xmlObject.getClassificationNode(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getClassificationScheme(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getClassifiedObject(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getNodeRepresentation(), vals);

        return vals.toString();
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
    public String getTableName() {
        return "t_classification";
    }

    @Override
    public JAXBElement<ClassificationType> createJAXBElement() {
        return OFactory.rim.createClassification(xmlObject);
    }
}
