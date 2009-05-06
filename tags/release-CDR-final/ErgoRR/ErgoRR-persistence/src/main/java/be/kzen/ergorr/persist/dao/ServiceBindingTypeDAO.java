package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ServiceBindingType;
import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class ServiceBindingTypeDAO extends RegistryObjectTypeDAO<ServiceBindingType> {

    public ServiceBindingTypeDAO() {
    }

    public ServiceBindingTypeDAO(ServiceBindingType serviceXml) {
        super(serviceXml);
    }

    @Override
    public ServiceBindingType newXmlObject(ResultSet result) throws SQLException {
        return loadCompleteXmlObject(result);
    }

    @Override
    protected ServiceBindingType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (!isBrief()) {
            xmlObject.setService(result.getString("service"));
            xmlObject.setAccessURI(result.getString("accessuri"));
            xmlObject.setTargetBinding(result.getString("targetbinding"));
        }

        return xmlObject;
    }

    protected void loadRelateObjects() throws SQLException {
        if (isFull()) {
            SpecificationLinkTypeDAO specLink = new SpecificationLinkTypeDAO();
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

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());

        vals.append(xmlObject.isNewObject() ? "," : ",service=");
        appendStringValue(xmlObject.getService(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",accessuri=");
        appendStringValue(xmlObject.getAccessURI(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",targetbinding=");
        appendStringValue(xmlObject.getTargetBinding(), vals);

        return vals.toString();
    }

    @Override
    public String getTableName() {
        return "t_servicebinding";
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",service,accessuri,targetbinging";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".service,").append(alias).append(".accessuri,").append(alias).append(".targetbinding,").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<ServiceBindingType> createJAXBElement() {
        return OFactory.rim.createServiceBinding(xmlObject);
    }
}
