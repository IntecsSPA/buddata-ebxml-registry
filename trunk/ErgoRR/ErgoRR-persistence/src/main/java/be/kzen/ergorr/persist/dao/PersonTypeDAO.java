package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.model.rim.PersonNameType;
import be.kzen.ergorr.model.rim.PersonType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class PersonTypeDAO extends RegistryObjectTypeDAO<PersonType> {
    private static Logger logger = Logger.getLogger(PersonTypeDAO.class.getName());

    public PersonTypeDAO() {
    }

    public PersonTypeDAO(PersonType personXml) {
        super(personXml);
    }

    @Override
    public PersonType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new PersonType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected PersonType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (context.getParam(InternalConstants.RETURN_NESTED_OBJECTS, Boolean.class)) {
            PersonNameType personName = new PersonNameType();
            personName.setFirstName(result.getString(8));
            personName.setMiddleName(result.getString(9));
            personName.setLastName(result.getString(10));

            if (personName.isSetFirstName()) {
                xmlObject.setPersonName(personName);
            }
        }
        return xmlObject;
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (context.getParam(InternalConstants.RETURN_NESTED_OBJECTS, Boolean.class)) {
            PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
            addressDAO.setConnection(connection);
            addressDAO.addComposedObjects();

            EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
            emailDAO.setConnection(connection);
            emailDAO.addComposedObjects();

            TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
            telDAO.setConnection(connection);
            telDAO.addComposedObjects();
        }
    }

    @Override
    protected void insertRelatedObjects() throws SQLException {
        super.insertRelatedObjects();

        PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
        addressDAO.setConnection(connection);
        addressDAO.insert();

        EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
        emailDAO.setConnection(connection);
        emailDAO.insert();

        TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
        telDAO.setConnection(connection);
        telDAO.insert();
    }

    @Override
    protected void updateRelatedObjects() throws SQLException {
        super.updateRelatedObjects();

        PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
        addressDAO.setConnection(connection);
        addressDAO.update();

        EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
        emailDAO.setConnection(connection);
        emailDAO.update();

        TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
        telDAO.setConnection(connection);
        telDAO.update();
    }

    @Override
    protected void deleteRelatedObjects() throws SQLException {
        super.deleteRelatedObjects();

        PostalAddressTypeDAO addressDAO = new PostalAddressTypeDAO(xmlObject);
        addressDAO.setConnection(connection);
        addressDAO.delete();

        EmailAddressTypeDAO emailDAO = new EmailAddressTypeDAO(xmlObject);
        emailDAO.setConnection(connection);
        emailDAO.delete();

        TelephoneNumberTypeDAO telDAO = new TelephoneNumberTypeDAO(xmlObject);
        telDAO.setConnection(connection);
        telDAO.delete();
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);

        PersonNameType personName = xmlObject.isSetPersonName() ? xmlObject.getPersonName() : new PersonNameType();

        stmt.setString(8, personName.getFirstName());
        stmt.setString(9, personName.getMiddleName());
        stmt.setString(10, personName.getLastName());
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",firstname,middlename,lastname";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".firstname,").append(alias).append(".middlename,").append(alias).append(".lastname,").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?" : ",firstname=?,middlename=?,lastname=?");
    }

    @Override
    public String getTableName() {
        return "t_person";
    }

    @Override
    public JAXBElement<PersonType> createJAXBElement() {
        return OFactory.rim.createPerson(xmlObject);
    }
}
