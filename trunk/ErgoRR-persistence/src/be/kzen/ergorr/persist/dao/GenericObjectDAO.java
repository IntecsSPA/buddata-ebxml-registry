/*
 * Project: Buddata ebXML RegRep
 * Class: GenericObjectDAO.java
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public abstract class GenericObjectDAO<T> extends GenericDAO<T> {

    protected String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void insert() throws SQLException {
        batchStmt.addBatch(createInsertStatement());
        insertRelatedObjects(batchStmt);
    }

    public void update() throws SQLException {
        batchStmt.addBatch(createUpdateStatement());
        updateRelatedObjects(batchStmt);
    }

    public void delete() throws SQLException {
        batchStmt.addBatch(createDeleteStatement());
        deleteRelatedObjects(batchStmt);
    }

    public T loadCompleteXmlObject(ResultSet result) throws SQLException {
        T t = loadXmlObject(result);
        if (!isBrief()) {
            loadRelatedObjects();
        }
        return t;
    }

    public abstract T newXmlObject(ResultSet result) throws SQLException;

    protected abstract T loadXmlObject(ResultSet result) throws SQLException;

    protected abstract void insertRelatedObjects(Statement batchStmt) throws SQLException;

    protected abstract void updateRelatedObjects(Statement batchStmt) throws SQLException;

    protected abstract void deleteRelatedObjects(Statement batchStmt) throws SQLException;

    protected abstract void loadRelatedObjects() throws SQLException;

    public abstract JAXBElement<T> createJAXBElement();
}
