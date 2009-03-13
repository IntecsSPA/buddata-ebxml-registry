package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yamanustuntas
 */
public class UsageDescriptionDAO extends GenericComposedObjectDAO<InternationalStringType, SpecificationLinkType> {

    public UsageDescriptionDAO(SpecificationLinkType parent) {
        super(parent);
    }

    @Override
    public void addComposedObjects() throws SQLException {
        StringBuilder sql = new StringBuilder();
        Statement stmt = connection.createStatement();

        sql.append("select ").append(getQueryParamList()).append(" from ").append(getTableName()).append(" where parent.id='").append(parent.getId());

        ResultSet result = stmt.executeQuery(sql.toString());

        InternationalStringType intString = new InternationalStringType();

        while (result.next()) {
            LocalizedStringType localString = new LocalizedStringType();
            localString.setCharset(result.getString(1));
            localString.setLang(result.getString(2));
            localString.setValue(result.getString(3));
            intString.getLocalizedString().add(localString);
        }

        if (!intString.getLocalizedString().isEmpty()) {
            parent.setUsageDescription(intString);
        }
    }

    @Override
    public void insert(Statement batchStmt) throws SQLException {
        if (parent.getDescription() != null) {
            InternationalStringType intString = parent.getDescription();

            if (!intString.getLocalizedString().isEmpty()) {

                for (LocalizedStringType localString : intString.getLocalizedString()) {
                    StringBuilder values = new StringBuilder();
                    appendStringValue(parent.getId(), values);
                    values.append(",");
                    appendStringValue(localString.getCharset(), values);
                    values.append(",");
                    appendStringValue(localString.getLang(), values);
                    values.append(",");
                    appendStringValue(localString.getValue(), values);

                    currentValues = values.toString();
                    batchStmt.addBatch(createInsertStatement());
                }
            }
        }
    }

    @Override
    public String getTableName() {
        return "t_usagedescription";
    }

    @Override
    protected String getParamList() {
        return "parent,charset,lang,value_";
    }

    @Override
    protected String getQueryParamList() {
        return "charset,lang,value_";
    }
}