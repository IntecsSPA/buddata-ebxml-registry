package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.SpecificationLinkType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * UsageParameter DAO.
 * 
 * @author yamanustuntas
 */
public class UsageParameterDAO extends GenericComposedObjectDAO<String, SpecificationLinkType> {

    public UsageParameterDAO(SpecificationLinkType parent) {
        super(parent);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(createInsertStatement());

        for (String param : parent.getUsageParameter()) {
            stmt.setString(1, parent.getId());
            stmt.setString(2, param);
            stmt.addBatch();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_usageparameter";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return "parent,value_";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        return "value_";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return "?,?";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
