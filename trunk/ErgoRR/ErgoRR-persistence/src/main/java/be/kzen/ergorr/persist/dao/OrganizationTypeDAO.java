package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.OrganizationType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class OrganizationTypeDAO extends RegistryObjectTypeDAO<OrganizationType> {

    public OrganizationTypeDAO() {
    }

    public OrganizationTypeDAO(OrganizationType oXml) {
        super(oXml);
    }

    @Override
    public OrganizationType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new OrganizationType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected OrganizationType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        xmlObject.setParent(result.getString("parent"));
        xmlObject.setPrimaryContact(result.getString("primarycontact"));
        return xmlObject;
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getParent());
        stmt.setString(9, xmlObject.getPrimaryContact());
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",parent,primarycontact";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".parent,").append(alias).append(".primarycontact").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?" : ",parent=?,primarycontact=?");
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (returnNestedObjects()) {
            PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
            addressDAO.setContext(context);
            addressDAO.setConnection(connection);
            addressDAO.addComposedObjects();

            EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
            emailDAO.setContext(context);
            emailDAO.setConnection(connection);
            emailDAO.addComposedObjects();

            TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
            telDAO.setContext(context);
            telDAO.setConnection(connection);
            telDAO.addComposedObjects();
        }
    }

    @Override
    protected void insertRelatedObjects() throws SQLException {
        super.insertRelatedObjects();

        PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
        addressDAO.setContext(context);
        addressDAO.setConnection(connection);
        addressDAO.insert();

        EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
        emailDAO.setContext(context);
        emailDAO.setConnection(connection);
        emailDAO.insert();

        TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
        telDAO.setContext(context);
        telDAO.setConnection(connection);
        telDAO.insert();
    }

    @Override
    protected void updateRelatedObjects() throws SQLException {
        super.updateRelatedObjects();

        PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
        addressDAO.setContext(context);
        addressDAO.setConnection(connection);
        addressDAO.update();

        EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
        emailDAO.setContext(context);
        emailDAO.setConnection(connection);
        emailDAO.update();

        TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
        telDAO.setContext(context);
        telDAO.setConnection(connection);
        telDAO.update();
    }

    @Override
    protected void deleteRelatedObjects() throws SQLException {
        super.deleteRelatedObjects();

        PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
        addressDAO.setContext(context);
        addressDAO.setConnection(connection);
        addressDAO.delete();

        EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
        emailDAO.setContext(context);
        emailDAO.setConnection(connection);
        emailDAO.delete();

        TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
        telDAO.setContext(context);
        telDAO.setConnection(connection);
        telDAO.delete();
    }

    @Override
    public String getTableName() {
        return "t_organization";
    }

    @Override
    public JAXBElement<OrganizationType> createJAXBElement() {
        return OFactory.rim.createOrganization(xmlObject);
    }
}
