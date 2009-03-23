package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.SpecificationLinkType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yamanustuntas
 */
public class UsageParameterDAO extends GenericComposedObjectDAO<String, SpecificationLinkType> {

    public UsageParameterDAO(SpecificationLinkType parent) {
        super(parent);
    }

    @Override
    public void addComposedObjects() throws SQLException {
        StringBuilder sql = new StringBuilder();
        Statement stmt = connection.createStatement();

        sql.append("select ").append(getQueryParamList()).append(" from ").append(getTableName()).append(" where parent.id='").append(parent.getId());

        ResultSet result = stmt.executeQuery(sql.toString());

        while (result.next()) {
            parent.getUsageParameter().add(result.getString(1));
        }
    }

    @Override
    public void insert() throws SQLException {

        for (String param : parent.getUsageParameter()) {
            currentValues = param;
            batchStmt.addBatch(createInsertStatement());
        }
    }

    @Override
    public String getTableName() {
        return "t_usageparameter";
    }

    @Override
    protected String getParamList() {
        return "parent,value_";
    }

    @Override
    protected String getQueryParamList() {
        return "value_";
    }
}
