package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class SpecificationLinkTypeDAO extends RegistryObjectTypeDAO<SpecificationLinkType> {

    public SpecificationLinkTypeDAO() {
    }

    public SpecificationLinkTypeDAO(SpecificationLinkType serviceXml) {
        super(serviceXml);
    }

    @Override
    public SpecificationLinkType newXmlObject(ResultSet result) throws SQLException {
        return loadCompleteXmlObject(result);
    }

    @Override
    protected SpecificationLinkType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        xmlObject.setServiceBinding(result.getString("servicebinding"));
        xmlObject.setSpecificationObject(result.getString("specificationobject"));

        return xmlObject;
    }

    public void addSpecificationLink(ServiceBindingType parent) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getParamList()).append(" from ").append(getTableName()).append(" where servicebinding = '").append(parent.getId()).append("'");
        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            parent.getSpecificationLink().add(newXmlObject(result));
        }
    }

    @Override
    protected void insertRelatedObjects() throws SQLException {
        super.insertRelatedObjects();
        UsageParameterDAO paramDAO = new UsageParameterDAO(xmlObject);
        paramDAO.setContext(context);
        paramDAO.setConnection(connection);
        paramDAO.insert();
        UsageDescriptionDAO descDAO = new UsageDescriptionDAO(xmlObject);
        descDAO.setContext(context);
        descDAO.setConnection(connection);
        descDAO.insert();
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();
        
        if (returnNestedObjects()) {
            UsageParameterDAO paramDAO = new UsageParameterDAO(xmlObject);
            paramDAO.setContext(context);
            paramDAO.setConnection(connection);
            paramDAO.addComposedObjects();

            UsageDescriptionDAO descDAO = new UsageDescriptionDAO(xmlObject);
            descDAO.setContext(context);
            descDAO.setConnection(connection);
            descDAO.addComposedObjects();
        }
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getServiceBinding());
        stmt.setString(9, xmlObject.getSpecificationObject());
    }

    @Override
    public String getTableName() {
        return "t_specificationlink";
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",servicebinding,specificationlink";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".servicebinding,").append(alias).append(".specificationlink,").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?" : ",servicebinding=?,specificationlink=?");
    }

    @Override
    public JAXBElement<SpecificationLinkType> createJAXBElement() {
        return OFactory.rim.createSpecificationLink(xmlObject);
    }
}
