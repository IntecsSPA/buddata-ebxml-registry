
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Yaman Ustuntas
 */
public abstract class GenericComposedObjectDAO<T, P extends IdentifiableType> extends GenericDAO<T> {
    protected String currentValues;
    
    public String createValues() {
        return currentValues;
    }
    
    public abstract void addComposedObjects(P parent) throws SQLException;
    public abstract void insert(P parent, Statement batchStmt) throws SQLException;
}
