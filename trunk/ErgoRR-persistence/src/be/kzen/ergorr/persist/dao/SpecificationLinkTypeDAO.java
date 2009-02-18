package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ServiceBindingType;
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
public class SpecificationLinkTypeDAO extends RegistryObjectTypeDAO<SpecificationLinkType> {

    public SpecificationLinkTypeDAO() {
    }

    public SpecificationLinkTypeDAO(SpecificationLinkType serviceXml) {
        super(serviceXml);
    }

    @Override
    public SpecificationLinkType newXmlObject(ResultSet result) throws SQLException {
        return loadXmlObject(result);
    }

    @Override
    protected SpecificationLinkType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (!isBrief()) {
            xmlObject.setServiceBinding(result.getString("servicebinding"));
            xmlObject.setSpecificationObject(result.getString("specificationobject"));
        }

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
    protected void insertRelatedObjects(Statement batchStmt) throws SQLException {
        super.insertRelatedObjects(batchStmt);
        UsageParameterDAO paramDAO = new UsageParameterDAO();
        paramDAO.insert(xmlObject, batchStmt);
        UsageDescriptionDAO descDAO = new UsageDescriptionDAO();
        descDAO.insert(xmlObject, batchStmt);
    }

    protected void loadRelateObjects() throws SQLException {
        if (!isBrief()) {
            UsageParameterDAO paramDAO = new UsageParameterDAO();
            paramDAO.setConnection(connection);
            paramDAO.addComposedObjects(xmlObject);

            UsageDescriptionDAO descDAO = new UsageDescriptionDAO();
            descDAO.setConnection(connection);
            descDAO.addComposedObjects(xmlObject);
        }
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        vals.append(super.createValues());

        vals.append(",");
        appendStringValue(xmlObject.getServiceBinding(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getSpecificationObject(), vals);

        return vals.toString();
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
    public JAXBElement<SpecificationLinkType> createJAXBElement() {
        return OFactory.rim.createSpecificationLink(xmlObject);
    }
}
