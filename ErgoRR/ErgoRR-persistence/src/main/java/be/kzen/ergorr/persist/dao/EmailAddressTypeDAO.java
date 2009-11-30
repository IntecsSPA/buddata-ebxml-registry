package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.EmailAddressType;
import be.kzen.ergorr.model.rim.OrganizationType;
import be.kzen.ergorr.model.rim.PersonType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * EmailAddress DAO
 *
 * @author yamanustuntas
 */
public class EmailAddressTypeDAO extends GenericComposedObjectDAO<EmailAddressType, RegistryObjectType> {

    public EmailAddressTypeDAO(RegistryObjectType parent) {
        super(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComposedObjects() throws SQLException {
        StringBuilder sql = new StringBuilder(200);
        sql.append("select ").append(getQueryParamList()).append(" from ").append(getTableName()).append(" where parent = '").append(parent.getId()).append("'");
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql.toString());

        List<EmailAddressType> emails = new ArrayList<EmailAddressType>();

        while (result.next()) {
            EmailAddressType email = new EmailAddressType();
            email.setAddress(result.getString(1));
            email.setType(result.getString(2));
            emails.add(email);
        }

        if (parent instanceof OrganizationType) {
            ((OrganizationType)parent).getEmailAddress().addAll(emails);
        } else if (parent instanceof PersonType) {
            ((PersonType)parent).getEmailAddress().addAll(emails);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert() throws SQLException {
        List<EmailAddressType> emails = null;

        if (parent instanceof OrganizationType) {
            emails = ((OrganizationType)parent).getEmailAddress();
        } else if (parent instanceof PersonType) {
            emails = ((PersonType)parent).getEmailAddress();
        }

        if (emails != null && emails.size() > 0) {
            PreparedStatement stmt = connection.prepareStatement(createInsertStatement());

            for (EmailAddressType email : emails) {
                stmt.setString(1, email.getAddress());
                stmt.setString(2, email.getType());
                stmt.setString(3, parent.getId());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_emailaddress";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return "address,type_,parent";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        return "address,type_";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return "?,?,?";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
