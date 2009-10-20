/*
 * Project: Buddata ebXML RegRep
 * Class: SqlPersistence.java
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
package be.kzen.ergorr.persist.service;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.persist.dao.GenericObjectDAO;
import be.kzen.ergorr.persist.dao.IdentifiableTypeDAO;
import be.kzen.ergorr.model.rim.IdentifiableType;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.bind.JAXBElement;

/**
 * SQL persistence class which interacts with the database.
 *
 * @author Yaman Ustuntas
 */
public class SqlPersistence {

    private static Logger logger = Logger.getLogger(SqlPersistence.class.getName());
    private static DataSource dataSource;
    private static AtomicBoolean useManualCreatedConn = new AtomicBoolean(false);
    private RequestContext requestContext;

    /**
     * Constructor.
     */
    public SqlPersistence() {
        requestContext = new RequestContext();
    }

    /**
     * Constructor.
     *
     * @param requestContext Request context.
     */
    public SqlPersistence(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    /**
     * Query the database.
     *
     * @param <T> Expected type to be returned.
     * @param query SQL query.
     * @param parameters SQL parameters or null.
     * @param clazz Expected type to be returned.
     * @return List of Identifiables as a result of the SQL query.
     * @throws java.sql.SQLException
     */
    public <T extends IdentifiableType> List<JAXBElement<? extends IdentifiableType>> query(String query, List<Object> parameters, Class<T> clazz) throws SQLException {

        Integer maxResults = requestContext.getParam(InternalConstants.MAX_RESULTS, Integer.class);
        Integer startPosition = requestContext.getParam(InternalConstants.START_POSITION, Integer.class);
        Integer allowedMaxResults = CommonProperties.getInstance().getInt("db.maxResponse");
        String orderBy = requestContext.getParam(InternalConstants.ORDER_BY, String.class);

        if (orderBy != null && orderBy.length() > 3) {
            query += " ORDER BY " + orderBy;
        }

        if (maxResults != null && maxResults != -1) {
            if (maxResults == 0 || maxResults > allowedMaxResults) {
                maxResults = allowedMaxResults;
            }
        } else {
            maxResults = allowedMaxResults;
        }

        query += " LIMIT " + maxResults;

        if (startPosition != null && startPosition > 0) {
            query += " OFFSET " + startPosition;
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Querying object: " + clazz.getSimpleName());
            logger.log(Level.FINE, "SQL: " + query);
        }

        List<JAXBElement<? extends IdentifiableType>> idents = new ArrayList<JAXBElement<? extends IdentifiableType>>();
        Connection conn = getConnection();
        ResultSet result = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("Query parameter " + i + ": " + parameters.get(i).toString());
                    }

                    stmt.setObject(i + 1, parameters.get(i));
                }
            }

            long startTime = System.currentTimeMillis();
            result = stmt.executeQuery();
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.INFO, "Query exec time: " + (System.currentTimeMillis() - startTime));
            }


            startTime = System.currentTimeMillis();
            int responseCount = 0;

            try {
                Class daoClass = Class.forName("be.kzen.ergorr.persist.dao." + clazz.getSimpleName() + "DAO");

                while (result.next()) {
                    responseCount++;
                    IdentifiableTypeDAO identDAO = (IdentifiableTypeDAO) daoClass.newInstance(); // TODO: move out of loop?
                    identDAO.setConnection(conn);
                    identDAO.setContext(requestContext);
                    identDAO.newXmlObject(result);
                    idents.add(identDAO.createJAXBElement());
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Could not load DAO object for query", ex);
                throw new SQLException("Could not load class " + clazz.getName() + ": " + ex.toString());
            }

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.INFO, "Loaded " + responseCount + " objects");
                logger.log(Level.INFO, "Load XML types time: " + (System.currentTimeMillis() - startTime));
            }

        } finally {
            closeConnection(conn);
        }

        return idents;
    }

    /**
     * Gets the count of queried objects.
     * Used for pagination.
     *
     * @param query Count query.
     * @param parameters Query parameters or null
     * @return Number of matched rows.
     * @throws java.sql.SQLException
     */
    public long getResultCount(String query, List<Object> parameters) throws SQLException {

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "SQL: " + query);
        }

        Connection conn = getConnection();

        try {
            long startTime = System.currentTimeMillis();
            PreparedStatement stmt = conn.prepareStatement(query);

            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    stmt.setObject(i + 1, parameters.get(i));
                }
            }

            ResultSet result = stmt.executeQuery();
            long count = 0;

            if (result.next()) {
                count = result.getLong(1);
            }
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.INFO, "Count query exec time: " + (System.currentTimeMillis() - startTime));
            }
            return count;
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Deletes the list of Identifiables.
     *
     * @param idents Identifiables to delete.
     * @throws java.sql.SQLException
     */
    public void delete(List<IdentifiableType> idents) throws SQLException {
        Connection conn = getConnection();

        try {
            conn.setAutoCommit(false);

            for (IdentifiableType ident : idents) {

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "Deleting " + ident.getClass().getSimpleName() + " with ID: " + ident.getId());
                }

                Class daoClass = Class.forName("be.kzen.ergorr.persist.dao." + ident.getClass().getSimpleName() + "DAO");
                IdentifiableTypeDAO identDAO = (IdentifiableTypeDAO) daoClass.newInstance();
                identDAO.setXmlObject(ident);
                identDAO.setConnection(conn);
                identDAO.setContext(requestContext);
                identDAO.delete();
            }

            conn.commit();
        } catch (Exception ex) {
            throw new SQLException(ex.toString());
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
            closeConnection(conn);
        }
    }

    /**
     * Get IDs of Identifiables. <code>sql</code> should
     * provide the ID return column.
     * 
     * @param sql SQL query string.
     * @return List of IDs matched from query.
     * @throws java.sql.SQLException
     */
    public List<String> getIds(String sql) throws SQLException {
        List<String> ids = new ArrayList<String>();

        ResultSet result = null;
        Connection conn = getConnection();

        try {
            result = conn.createStatement().executeQuery(sql);

            while (result.next()) {
                ids.add(result.getString(1));
            }
        } finally {
            closeConnection(conn);
        }

        return ids;
    }

    /**
     * Get the mimeType of an ExtrinsicObject.
     *
     * @param id ID of ExtrinsicObject.
     * @return ExtrinsicObject mimeType.
     * @throws java.sql.SQLException
     */
    public String getMimeType(String id) throws SQLException {
        String mimeType = "";
        String queryStr = "select mimetype from t_extrinsicobject where id ='" + id + "' limit 1";
        Connection conn = null;

        try {
            conn = getConnection();
            ResultSet result = conn.createStatement().executeQuery(queryStr);

            if (result.next()) {
                mimeType = result.getString(1);
            }
        } finally {
            closeConnection(conn);
        }

        return mimeType;
    }

    /**
     * Check if an Identifiable exists with <code>id</code>.
     *
     * @param id ID to check if it exists.
     * @return True if Identifiable exists.
     * @throws java.sql.SQLException
     */
    public boolean idExist(String id) throws SQLException {
        List<String> ids = new ArrayList();
        ids.add(id);
        List<String> res = idsExist(ids);
        return res.size() > 0;
    }

    /**
     * Checks if Identifiables with <code>ids</code> exist in the
     * database and returns the IDs which do exist.
     *
     * @param id ID to check if it exists.
     * @param clazz Identifiable subtype to check for ID.
     * @return True if ID exists.
     * @throws java.sql.SQLException
     */
    public boolean idExists(String id, Class<? extends IdentifiableType> clazz) throws SQLException {
        List<String> ids = new ArrayList();
        ids.add(id);
        List<String> res = idsExist(ids, clazz);
        return res.size() > 0;
    }

    /**
     * Checks if Identifiables with <code>ids</code> exist in the
     * database and returns the IDs which do exist.
     *
     * @param ids IDs to check if they exist.
     * @param clazz Identifiable subtype to check for IDs.
     * @return IDs which exist.
     * @throws java.sql.SQLException
     */
    public List<String> idsExist(List<String> ids, Class<? extends IdentifiableType> clazz) throws SQLException {
        String tableName = "";

        try {
            Class daoClass = Class.forName("be.kzen.ergorr.persist.dao." + clazz.getSimpleName() + "DAO");
            IdentifiableTypeDAO identDAO = (IdentifiableTypeDAO) daoClass.newInstance();
            tableName = identDAO.getTableName();
        } catch (Exception ex) {
            throw new SQLException(ex.toString());
        }

        return idsExist(ids, tableName);
    }

    /**
     * Checks if Identifiables with <code>ids</code> exist in the
     * database and returns the IDs which do exist.
     *
     * @param ids IDs to check if they exist.
     * @param tableName Table name of the Identifiable subtype to check.
     * @return IDs which exist.
     * @throws java.sql.SQLException
     */
    private List<String> idsExist(List<String> ids, String tableName) throws SQLException {
        List<String> dbIds = new ArrayList<String>();

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "requested object by id: " + ids.size());
        }

        if (!ids.isEmpty()) {
            StringBuilder values = new StringBuilder("(");

            for (String id : ids) {
                values.append("'").append(id).append("',");
            }
            values.replace(values.length() - 1, values.length(), ")");

            Connection conn = null;
            try {
                String sql = "select id from " + tableName + " where id in " + values.toString() + " limit " + ids.size();

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "SQL: " + sql);
                }

                conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery(sql);

                while (results.next()) {
                    dbIds.add(results.getString("id"));
                }
            } finally {
                closeConnection(conn);
            }
        }

        return dbIds;
    }

    /**
     * Checks if Identifiables with <code>ids</code> exist in the
     * database and returns the IDs which do exist.
     *
     * @param ids IDs to check if they exist.
     * @return IDs which exist.
     * @throws java.sql.SQLException
     */
    public List<String> idsExist(List<String> ids) throws SQLException {
        return idsExist(ids, "t_identifiable");
    }

    /**
     * Get any Identifiable object or its subtypes by ID.
     *
     * @param ids List of IDs of objects to get.
     * @return Identifiable types with the supplied IDs.
     * @throws java.sql.SQLException
     */
    public List<JAXBElement<? extends IdentifiableType>> getByIds(List<String> ids) throws SQLException {
        List<JAXBElement<? extends IdentifiableType>> idents = new ArrayList<JAXBElement<? extends IdentifiableType>>();

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "requested object by id: " + ids.size());
        }

        if (!ids.isEmpty()) {
            StringBuilder values = new StringBuilder("(");

            for (String id : ids) {
                values.append("'").append(id).append("',");
            }
            values.replace(values.length() - 1, values.length(), ")");

            Connection conn = null;
            try {
                String sql = "select * from t_identifiable where id in " + values.toString() + " limit " + ids.size();

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "SQL: " + sql);
                }

                conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery(sql);

                while (results.next()) {
                    IdentifiableTypeDAO identDAO = new IdentifiableTypeDAO();
                    identDAO.setConnection(conn);
                    identDAO.setContext(requestContext);
                    identDAO.newXmlObject(results);
                    idents.add(identDAO.createJAXBElement());
                }
            } finally {
                closeConnection(conn);
            }
        }

        return idents;
    }

    /**
     * Persists the list of Identifiables to the database.
     * Persist includes inserts and updates depending on the <code>isNewObject()</code>
     * boolean returned from the Identifiable object.
     *
     * @param idents Identifiables to persist.
     * @throws java.sql.SQLException
     */
    public void persist(List<IdentifiableType> idents) throws SQLException {
        Connection conn = getConnection();

        try {
            conn.setAutoCommit(false);

            for (IdentifiableType ident : idents) {

                Class daoClass = Class.forName("be.kzen.ergorr.persist.dao." + ident.getClass().getSimpleName() + "DAO");
                Constructor constructor = daoClass.getConstructor(ident.getClass());
                GenericObjectDAO dao = (GenericObjectDAO) constructor.newInstance(ident);
                dao.setConnection(conn);

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, (ident.isNewObject() ? "insert " : "update ") + ident.getClass().getSimpleName() + " " + ident.getId());
                }

                if (ident.isNewObject()) {
                    dao.insert();
                } else {
                    dao.update();
                }
            }

            conn.commit();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to insert objects", ex);

            if (ex.getNextException() != null) {
                logger.log(Level.SEVERE, "   NextException >>>>>", ex.getNextException());
            }
            throw ex;
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Failed to insert objects", t);
            throw new SQLException("Could not insert objects: " + t.toString());
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
            closeConnection(conn);
        }
    }

    /**
     * Close the connection peacefully, ignoring exceptions.
     *
     * @param conn Connection to close.
     */
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Could not close connection", ex);
            }
        }
    }

    /**
     * Get database connection.
     * First attempts to get connection from the application server DataSource.
     * If that fails then tries to create a connect manually.
     *
     * @return Database connection.
     * @throws java.sql.SQLException
     */
    public Connection getConnection() throws SQLException {

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "attempting to get a connection");
        }

        Connection conn = null;

        if (useManualCreatedConn.get()) {
            conn = createConnection();
        } else {
            if (dataSource != null) {
                conn = dataSource.getConnection();
            } else {
                if (loadDataSource()) {
                    conn = dataSource.getConnection();
                } else {
                    useManualCreatedConn.set(true);
                    loadDriver();
                    conn = createConnection();
                }
            }
        }

        return conn;
    }

    private Connection createConnection() throws SQLException {
        DbConnectionParams connParams = (DbConnectionParams) requestContext.getParam(InternalConstants.DB_CONNECTION_PARAMS);
        
        if (connParams == null) {
            if (logger.isLoggable(Level.FINE)) {
                logger.info("No custom DB connection params found! Using default.");
            }

            connParams = DbConnectionParams.getDefaultInstance();
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.info("Connecting to: " + connParams.toString());
        }

        return DriverManager.getConnection(connParams.createConnectionString(), connParams.getDbUser(), connParams.getDbPassword());
    }

    private boolean loadDataSource() throws SQLException {
        boolean loaded = false;

        try {
            String dataSourceName = CommonProperties.getInstance().get("db.datasource") +
                    CommonProperties.getInstance().get("deployName");
            dataSource = (DataSource) new InitialContext().lookup(dataSourceName);
            loaded = true;
        } catch (Exception ex) {
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Connection pool DateSource not provided");
            }
        }

        return loaded;
    }

    private void loadDriver() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception ex) {
            logger.severe("Postgres driver not found");
            throw new SQLException("Oops, Postgres Driver not found");
        }
    }
}
