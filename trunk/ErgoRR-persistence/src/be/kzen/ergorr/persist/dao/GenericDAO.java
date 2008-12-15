package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.model.csw.ElementSetType;
import java.sql.Connection;

/**
 *
 * @author Yaman Ustuntas
 */
public abstract class GenericDAO<T> {

    protected Connection connection;
    protected RequestContext context;
    protected ElementSetType elementSet;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setContext(RequestContext context) {
        this.context = context;
        elementSet = context.getParam(InternalConstants.ELEMENT_SET, ElementSetType.class);
    }

    protected String createInsertStatement() {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(getTableName()).append(" (").append(getParamList()).append(") values (");
        sql.append(createValues()).append(");");
        return sql.toString();
    }

    protected String createUpdateStatement() {
        StringBuilder sql = new StringBuilder("update ");
        sql.append(getTableName()).append(" set ").append(createUpdateValues());
        sql.append(" where ").append(getFetchCondition()).append(";");
        return sql.toString();
    }

    protected String createDeleteStatement() {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(getTableName()).append(" where ").append(getFetchCondition()).append(";");
        return sql.toString();
    }

    public void appendStringValue(String value, StringBuilder sb) {
        if (value != null) {
            sb.append("'").append(value).append("'");
        } else {
            sb.append("null");
        }
    }

    public void appendBooleanValue(Boolean bool, StringBuilder sb) {
        if (bool != null) {
            sb.append("'").append(bool ? "t" : "f").append("'");
        } else {
            sb.append("null");
        }
    }
    
    public boolean isBrief() {
        return elementSet != null && elementSet == ElementSetType.BRIEF;
    }
    
    public boolean isSummary() {
        return elementSet != null && elementSet == ElementSetType.SUMMARY;
    }
    
    public boolean isFull() {
        return elementSet != null && elementSet == ElementSetType.FULL;
    }
    
    public abstract String getTableName();
    protected abstract String getParamList();
    protected abstract String getQueryParamList();
    protected abstract String createValues();
    protected abstract String createUpdateValues();
    protected abstract String getFetchCondition();
}
