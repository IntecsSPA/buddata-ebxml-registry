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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 * GenericObject DAO used any IdentifiableType and any of its subtypes.
 *
 * @author Yaman Ustuntas
 */
public abstract class GenericObjectDAO<T> extends GenericDAO<T> {

    protected String alias;

    /**
     * Get the SQL alias used for this type.
     *
     * @return SQL alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Set the SQL alias used for this type.
     * 
     * @param alias SQL alias.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Insert the object.
     * 
     * @throws SQLException
     */
    public void insert() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(createInsertStatement());
        setParameters(stmt);
        stmt.addBatch();
        stmt.executeBatch();
        insertRelatedObjects();
    }

    /**
     * Update the object.
     * 
     * @throws SQLException
     */
    public void update() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(createUpdateStatement());
        setParameters(stmt);
        stmt.addBatch();
        stmt.executeBatch();
        updateRelatedObjects();
    }

    /**
     * Delete the object.
     * 
     * @throws SQLException
     */
    public void delete() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.addBatch(createDeleteStatement());
        stmt.executeBatch();
        deleteRelatedObjects();
    }

    /**
     * Load the object represented by the DAO and all its related/nested objects.
     * 
     * @param result ResultSet to load the instance of {@code T} from.
     * @return Instance of {@code T} with related/nested objects.
     * @throws SQLException
     */
    public T loadCompleteXmlObject(ResultSet result) throws SQLException {
        T t = loadXmlObject(result);
        loadRelatedObjects();
        return t;
    }

    /**
     * Create a new instance of {@code T} and load its data.
     *
     * @param result ResultSet to load the instance of {@code T} from.
     * @return New instance of {@code T}.
     * @throws SQLException
     */
    public abstract T newXmlObject(ResultSet result) throws SQLException;

    /**
     * Loads data of an existing instance of {@code T}.
     *
     * @param result ResultSet to load the instance of {@code T} from.
     * @return Existing instance of {@code T} with loaded data.
     * @throws SQLException
     */
    protected abstract T loadXmlObject(ResultSet result) throws SQLException;

    /**
     * Insert the related/nested objects.
     * 
     * @throws SQLException
     */
    protected abstract void insertRelatedObjects() throws SQLException;

    /**
     * Update the related/nested objects.
     *
     * @throws SQLException
     */
    protected abstract void updateRelatedObjects() throws SQLException;

    /**
     * Delete the related/nested objects.
     * 
     * @throws SQLException
     */
    protected abstract void deleteRelatedObjects() throws SQLException;

    /**
     * Load the related/nested objects.
     * 
     * @throws SQLException
     */
    protected abstract void loadRelatedObjects() throws SQLException;

    /**
     * Create a JAXBElement of the current instance of {@code T}.
     * 
     * @return JAXBElement of instance of {@code T}.
     */
    public abstract JAXBElement<T> createJAXBElement();
}
