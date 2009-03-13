package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.RegistryObjectType;
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
    public void insert(Statement batchStmt) throws SQLException {

        if (parent.isSetName()) {
            InternationalStringType name = parent.getName();
            StringBuilder values = new StringBuilder();

            for (LocalizedStringType localString : name.getLocalizedString()) {
                appendStringValue(localString.getCharset(), values);
                values.append(",");
                appendStringValue(localString.getLang(), values);
                values.append(",");
                appendStringValue(localString.getValue(), values);
                values.append(",");
                appendStringValue(parent.getId(), values);
                currentValues = values.toString();
                batchStmt.addBatch(createInsertStatement());
            }
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
}
