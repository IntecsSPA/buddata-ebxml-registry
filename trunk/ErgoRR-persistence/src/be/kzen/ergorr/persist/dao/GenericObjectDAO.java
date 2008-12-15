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
        Statement batchStmt = connection.createStatement();
        batchStmt.addBatch(createInsertStatement());
        insertRelatedObjects(batchStmt);
        batchStmt.executeBatch();
    }

    public void update() throws SQLException {
        Statement batchStmt = connection.createStatement();
        batchStmt.addBatch(createUpdateStatement());
        updateRelatedObjects(batchStmt);
        batchStmt.executeBatch();
    }

    public void delete() throws SQLException {
        Statement batchStmt = connection.createStatement();
        batchStmt.addBatch(createDeleteStatement());
        deleteRelatedObjects(batchStmt);
        batchStmt.executeBatch();
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
