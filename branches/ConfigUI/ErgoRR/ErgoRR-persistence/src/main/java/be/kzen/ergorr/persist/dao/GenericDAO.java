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
import be.kzen.ergorr.persist.SyntaxElements;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Generic DAO to be extended by all RIM model DAOs.
 * 
 * @author Yaman Ustuntas
 */
public abstract class GenericDAO<T> {

    protected Connection connection;
    protected RequestContext context;

    /**
     * Set the database connection to use.
     *
     * @param connection Database connection.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Set the RequestContext of the current request.
     *
     * @param context RequestContext.
     */
    public void setContext(RequestContext context) {
        this.context = context;
    }

    /**
     * Create SQL INSERT statement string.
     *
     * @return SQL INSERT statement.
     */
    protected String createInsertStatement() {
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(getTableName()).append(" (").append(getParamList()).append(") values (");
        sql.append(getPlaceHolders()).append(");");
        return sql.toString();
    }

    /**
     * Create SQL UPDATE statement string.
     *
     * @return SQL UPDATE statement.
     */
    protected String createUpdateStatement() {
        StringBuilder sql = new StringBuilder("update ");
        sql.append(getTableName()).append(" set ").append(getPlaceHolders());
        sql.append(" where ").append(getFetchCondition()).append(";");
        return sql.toString();
    }

    /**
     * Create SQL DELETE statement string.
     *
     * @return SQL DELETE statement.
     */
    protected String createDeleteStatement() {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(getTableName()).append(" where ").append(getFetchCondition()).append(";");
        return sql.toString();
    }

    /**
     * Append a String value {@code value} to the StringBuilder {@code sb}.
     * Wraps {@code value} with single quotes.
     *
     * @param value Value to append.
     * @param sb StringBuilder to append to.
     */
    public void appendStringValue(String value, StringBuilder sb) {
        if (value != null) {
            sb.append(SyntaxElements.SINGLE_QUOTE).append(value).append(SyntaxElements.SINGLE_QUOTE);
        } else {
            sb.append("''");
        }
    }

    /**
     * Append a boolean value {@code bool} to the StringBuilder {@code sb}.
     * Wraps {@code bool} with single quotes as 't' or 'f'.
     *
     * @param bool Value to append.
     * @param sb StringBuidler to append to.
     */
    public void appendBooleanValue(Boolean bool, StringBuilder sb) {
        if (bool != null) {
            sb.append(SyntaxElements.SINGLE_QUOTE).append(bool ? "t" : "f").append(SyntaxElements.SINGLE_QUOTE);
        } else {
            sb.append("null");
        }
    }

    public boolean returnSlots() {
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

    /**
     * Get the database table name of the DAO.
     *
     * @return Database table name.
     */
    public abstract String getTableName();

    /**
     * List of all the columns of the table of DAO.
     *
     * @return Parameter list.
     */
    protected abstract String getParamList();

    /**
     * List of columns to be return in a query.
     *
     * @return Parameter list.
     */
    protected abstract String getQueryParamList();

    /**
     * Get the unique fetch condition for an object.
     * E.g: by ID of object.
     *
     * @return SQL condition.
     */
    protected abstract String getFetchCondition();

    /**
     * Get the placeholders for an SQL statement (insert/update).
     * E.g:
     * ?,?,?
     * or
     * col1=?,col2=?
     *
     * @return Placeholders.
     */
    protected abstract String getPlaceHolders();

    /**
     * Set the parameters for an SQL insert/update statement.
     *
     * @param stmt PreparedStatement.
     * @throws SQLException
     */
    protected abstract void setParameters(PreparedStatement stmt) throws SQLException;
}
