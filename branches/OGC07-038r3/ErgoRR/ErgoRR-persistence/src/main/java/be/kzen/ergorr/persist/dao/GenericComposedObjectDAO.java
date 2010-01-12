
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * GenericComposedObject DAO.
 * Composed objects are objects which are NOT sub-types IdentifiableType but
 * have a composition relation to IdentifiableType or one of its sub-types.
 * Examples, rim:Name, rim:Description, rim:Slot
 * 
 * @author Yaman Ustuntas
 */
public abstract class GenericComposedObjectDAO<T, P extends IdentifiableType> extends GenericDAO<T> {
    protected P parent;

    public GenericComposedObjectDAO(P parent) {
        this.parent = parent;
    }

    /**
     * Update object.
     * Composed objects are first deleted and then reinserted.
     * 
     * @throws SQLException
     */
    public void update() throws SQLException {
        delete();
        insert();
    }

    /**
     * Delete object.
     * 
     * @throws SQLException
     */
    public void delete() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(createDeleteStatement());
    }

    /**
     * Condition to get compesed objects by parent.
     *
     * @return SQL condition.
     */
    @Override
    protected String getFetchCondition() {
        return new StringBuilder("parent='").append(parent.getId()).append("'").toString();
    }

    /**
     * Add composed object of {@code parent} to the {@code parent}.
     * 
     * @throws SQLException
     */
    public abstract void addComposedObjects() throws SQLException;

    /**
     * Insert object.
     * 
     * @throws SQLException
     */
    public abstract void insert() throws SQLException;
}
