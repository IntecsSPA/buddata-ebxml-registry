package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.OrganizationType;
import be.kzen.ergorr.model.rim.PersonType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.TelephoneNumberType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yamanustuntas
 */
public class TelephoneNumberTypeDAO extends GenericComposedObjectDAO<TelephoneNumberType, RegistryObjectType>{

    public TelephoneNumberTypeDAO(RegistryObjectType parent) {
        super(parent);
    }

    @Override
    public void addComposedObjects() throws SQLException {
        StringBuilder sql = new StringBuilder(200);
        sql.append("select ").append(getQueryParamList()).append(" from ").append(getTableName()).append(" where parent = '").append(parent.getId()).append("'");
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql.toString());

        List<TelephoneNumberType> tels = new ArrayList<TelephoneNumberType>();

        while (result.next()) {
            TelephoneNumberType tel = new TelephoneNumberType();
            tel.setAreaCode(result.getString(1));
            tel.setCountryCode(result.getString(2));
            tel.setExtension(result.getString(3));
            tel.setNumber(result.getString(4));
            tel.setPhoneType(result.getString(5));
            tels.add(tel);
        }

        if (parent instanceof OrganizationType) {
            ((OrganizationType)parent).getTelephoneNumber().addAll(tels);
        } else if (parent instanceof PersonType) {
            ((PersonType)parent).getTelephoneNumber().addAll(tels);
        }
    }

    @Override
    public void insert() throws SQLException {
        List<TelephoneNumberType> tels = null;

        if (parent instanceof OrganizationType) {
            tels = ((OrganizationType)parent).getTelephoneNumber();
        } else if (parent instanceof PersonType) {
            tels = ((PersonType)parent).getTelephoneNumber();
        }

        if (tels != null && tels.size() > 0) {
            PreparedStatement stmt = connection.prepareStatement(createInsertStatement());

            for (TelephoneNumberType tel : tels) {
                stmt.setString(1, tel.getAreaCode());
                stmt.setString(2, tel.getCountryCode());
                stmt.setString(3, tel.getExtension());
                stmt.setString(4, tel.getNumber());
                stmt.setString(5, tel.getPhoneType());
                stmt.setString(6, parent.getId());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    @Override
    public String getTableName() {
        return "t_telephonenumber";
    }

    @Override
    protected String getParamList() {
        return "areacode,countrycode,extension,number_,phonetype,parent";
    }

    @Override
    protected String getQueryParamList() {
        return "areacode,countrycode,extension,number_,phonetype";
    }

    @Override
    protected String getPlaceHolders() {
        return "?,?,?,?,?,?";
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
