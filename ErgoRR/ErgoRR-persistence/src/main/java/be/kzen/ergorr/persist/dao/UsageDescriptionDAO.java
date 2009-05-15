package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.LocalizedStringType;
import be.kzen.ergorr.model.rim.SpecificationLinkType;
import java.sql.PreparedStatement;
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
    public void insert() throws SQLException {
        if (parent.getDescription() != null) {
            InternationalStringType intString = parent.getDescription();

            if (!intString.getLocalizedString().isEmpty()) {
                    PreparedStatement stmt = connection.prepareCall(createInsertStatement());

                for (LocalizedStringType localString : intString.getLocalizedString()) {
                    stmt.setString(1, parent.getId());
                    stmt.setString(2, localString.getCharset());
                    stmt.setString(3, localString.getLang());
                    stmt.setString(4, localString.getValue());
                    stmt.addBatch();
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

    @Override
    protected String getPlaceHolders() {
        return "?,?,?,?";
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}