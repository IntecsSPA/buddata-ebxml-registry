package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.RequestContext;
import java.sql.Connection;

/**
 *
 * @author Yaman Ustuntas
 */
public abstract class GenericDAO<T> {

    protected Connection connection;
    protected RequestContext context;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setContext(RequestContext context) {
        this.context = context;
    }

    public String createInsertStatement() {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(getTableName()).append(" (").append(getParamList()).append(") values (");
        sql.append(createValues()).append(");");
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
    
    public abstract String getTableName();
    protected abstract String getParamList();
    protected abstract String getQueryParamList();
    protected abstract String createValues();
}
