package be.kzen.ergorr.persist.dao;

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
public class LocalizedStringDescDAO extends GenericComposedObjectDAO<LocalizedStringType, RegistryObjectType> {

    @Override
    public String getTableName() {
        return "localizeddesc";
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
    public void insert(RegistryObjectType parent, Statement batchStmt) throws SQLException {

        if (parent.isSetName()) {
            InternationalStringType desc = parent.getDescription();
            StringBuilder values = new StringBuilder();

            for (LocalizedStringType localString : desc.getLocalizedString()) {
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
    public void addComposedObjects(RegistryObjectType parent) throws SQLException {
        StringBuilder sql = new StringBuilder(200);
        sql.append("select ").append(getQueryParamList()).append(" from ").append(getTableName()).append(" where parent = '").append(parent.getId()).append("'");
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql.toString());
        InternationalStringType desc = null;

        while (result.next()) {
            if (result.isFirst()) {
                desc = new InternationalStringType();
            }

            LocalizedStringType localString = new LocalizedStringType();
            localString.setCharset(result.getString(1));
            localString.setLang(result.getString(2));
            localString.setValue(result.getString(3));
            desc.getLocalizedString().add(localString);
        }

        if (desc != null) {
            parent.setDescription(desc);
        }
    }

    @Override
    protected String createUpdateValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String getFetchCondition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
