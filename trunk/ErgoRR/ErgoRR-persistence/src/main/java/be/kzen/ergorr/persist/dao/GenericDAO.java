/*
 * Project: Buddata ebXML RegRep
 * Class: GenericDAO.java
 * Copyright (C) 2008 Yaman Ustuntas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    protected String createInsertStatement() {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(getTableName()).append(" (").append(getParamList()).append(") values (");
        sql.append(getPlaceHolders()).append(");");
        return sql.toString();
    }

    protected String createUpdateStatement() {
        StringBuilder sql = new StringBuilder("update ");
        sql.append(getTableName()).append(" set ").append(getPlaceHolders());
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
            sb.append("''");
        }
    }

    public void appendBooleanValue(Boolean bool, StringBuilder sb) {
        if (bool != null) {
            sb.append("'").append(bool ? "t" : "f").append("'");
        } else {
            sb.append("null");
        }
    }

    public boolean returnSlots() {
        if (context == null) {
            System.out.println("NULL <<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("NULL <<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("NULL <<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("NULL <<<<<<<<<<<<<<<<<<<<<<<<<");
        }
        Boolean r = context.getParam(InternalConstants.RETURN_SLOTS, Boolean.class);
        return (r == null) ? true : r;
    }

    public boolean returnNameDesc() {
        Boolean r = context.getParam(InternalConstants.RETURN_NAME_DESC, Boolean.class);
        return (r == null) ? true : r;
    }

    public boolean returnNestedObjects() {
        Boolean r = context.getParam(InternalConstants.RETURN_NESTED_OBJECTS, Boolean.class);
        return (r == null) ? true : r;
    }

    public boolean returnAssociations() {
        Boolean r = context.getParam(InternalConstants.RETURN_ASSOCIATIONS, Boolean.class);
        return (r == null) ? false : r;
    }

    public abstract String getTableName();
    protected abstract String getParamList();
    protected abstract String getQueryParamList();
    protected abstract String getFetchCondition();
    protected abstract String getPlaceHolders();
    protected abstract void setParameters(PreparedStatement stmt) throws SQLException;
}
