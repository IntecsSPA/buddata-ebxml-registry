package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.OrganizationType;
import be.kzen.ergorr.model.rim.PersonType;
import be.kzen.ergorr.model.rim.PostalAddressType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * PostalAddress DAO.
 * 
 * @author yamanustuntas
 */
public class PostalAddressTypeDAO extends GenericComposedObjectDAO<PostalAddressType, RegistryObjectType> {
    private static Logger logger = Logger.getLogger(PostalAddressTypeDAO.class.getName());
    
    public PostalAddressTypeDAO(RegistryObjectType parent) {
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

        List<PostalAddressType> postalAddresses = new ArrayList<PostalAddressType>();

        while (result.next()) {
            PostalAddressType postalAddress = new PostalAddressType();
            postalAddress.setCity(result.getString(1));
            postalAddress.setCountry(result.getString(1));
            postalAddress.setPostalCode(result.getString(1));
            postalAddress.setStateOrProvince(result.getString(1));
            postalAddress.setStreet(result.getString(1));
            postalAddress.setStreetNumber(result.getString(1));
            postalAddresses.add(postalAddress);
        }

        if (parent instanceof OrganizationType) {
            ((OrganizationType)parent).getAddress().addAll(postalAddresses);
        } else if (parent instanceof PersonType) {
            ((PersonType)parent).getAddress().addAll(postalAddresses);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert() throws SQLException {
        List<PostalAddressType> postalAddress = null;
        logger.fine("INSERTING --------------------------------------");
        if (parent instanceof OrganizationType) {
            postalAddress = ((OrganizationType)parent).getAddress();
            logger.fine("ORG");
        } else if (parent instanceof PersonType) {
            postalAddress = ((PersonType)parent).getAddress();
            logger.fine("PERSON");
        }

        if (postalAddress != null && postalAddress.size() > 0) {
            logger.fine("HAS ADDRESS");
            PreparedStatement stmt = connection.prepareStatement(createInsertStatement());

            for (PostalAddressType pa : postalAddress) {
                stmt.setString(1, pa.getCity());
                stmt.setString(2, pa.getCountry());
                stmt.setString(3, pa.getPostalCode());
                stmt.setString(4, pa.getStateOrProvince());
                stmt.setString(5, pa.getStreet());
                stmt.setString(6, pa.getStreetNumber());
                stmt.setString(7, parent.getId());
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
        return "t_postaladdress";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return "city,country,postalcode,state_,street,streetnumber,parent";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        return "city,country,postalcode,state_,street,streetnumber";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return "?,?,?,?,?,?,?";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
