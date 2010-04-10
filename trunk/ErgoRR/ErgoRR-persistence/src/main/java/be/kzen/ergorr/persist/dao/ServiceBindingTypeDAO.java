package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 * ServiceBinding DAO.
 * 
 * @author yamanustuntas
 */
public class ServiceBindingTypeDAO extends RegistryObjectTypeDAO<ServiceBindingType> {

    public ServiceBindingTypeDAO() {
    }

    public ServiceBindingTypeDAO(ServiceBindingType serviceXml) {
        super(serviceXml);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceBindingType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new ServiceBindingType();
        return loadCompleteXmlObject(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ServiceBindingType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        xmlObject.setService(result.getString("service"));
        xmlObject.setAccessURI(result.getString("accessuri"));
        xmlObject.setTargetBinding(result.getString("targetbinding"));

        return xmlObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();
        
        if (returnNestedObjects()) {
            SpecificationLinkTypeDAO specLink = new SpecificationLinkTypeDAO();
            specLink.setContext(context);
            specLink.setConnection(connection);
            specLink.addSpecificationLink(xmlObject);
        }
    }

    protected void addServiceBinding(ServiceType parent) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getParamList()).append(" from ").append(getTableName()).append(" where service = '").append(parent.getId()).append("'");
        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            parent.getServiceBinding().add(newXmlObject(result));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getService());
        stmt.setString(9, xmlObject.getAccessURI());
        stmt.setString(10, xmlObject.getTargetBinding());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_servicebinding";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return super.getParamList() + ",service,accessuri,targetbinding";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".service,").append(alias).append(".accessuri,").append(alias).append(".targetbinding,").toString();
        } else {
            return getParamList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?" : ",service=?,accessuri=?,targetbinding=?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAXBElement<ServiceBindingType> createJAXBElement() {
        return OFactory.rim.createServiceBinding(xmlObject);
    }
}
