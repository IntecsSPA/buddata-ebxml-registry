package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Yaman Ustuntas
 */
public class NameDAO extends GenericComposedObjectDAO<LocalizedStringType, RegistryObjectType> {

    public NameDAO(RegistryObjectType parent) {
        super(parent);
    }

    @Override
    public String getTableName() {
        return "t_name";
    }

    @Override
    public String getParamList() {
        return "charset,lang,value_,parent";
    }

    @Override
    public String getQueryParamList() {
        return "charset,lang,value_";
    }

    @Override
    public void insert() throws SQLException {

        if (parent.isSetName()) {
            PreparedStatement stmt = connection.prepareStatement(createInsertStatement());
            InternationalStringType name = parent.getName();

            for (LocalizedStringType localString : name.getLocalizedString()) {
                stmt.setString(1, localString.getCharset());
                stmt.setString(2, localString.getLang());
                stmt.setString(3, localString.getValue());
                stmt.setString(4, parent.getId());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    @Override
    public void addComposedObjects() throws SQLException {
        StringBuilder sql = new StringBuilder(200);
        sql.append("select ").append(getQueryParamList()).append(" from ").append(getTableName()).append(" where parent = '").append(parent.getId()).append("'");
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql.toString());
        InternationalStringType name = null;

        while (result.next()) {
            if (result.isFirst()) {
                name = new InternationalStringType();
            }

            LocalizedStringType localString = new LocalizedStringType();
            localString.setCharset(result.getString(1));
            localString.setLang(result.getString(2));
            localString.setValue(result.getString(3));
            name.getLocalizedString().add(localString);
        }

        if (name != null) {
            parent.setName(name);
        }
    }

    @Override
    protected String getPlaceHolders() {
        return "?,?,?,?";
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
