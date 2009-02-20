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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class SqlPersistence {

    private static Logger logger = Logger.getLogger(SqlPersistence.class.getName());
    private RequestContext requestContext;

    public SqlPersistence() {
        requestContext = new RequestContext();
    }

    public SqlPersistence(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public <T extends IdentifiableType> List<JAXBElement<? extends IdentifiableType>> query(String query, List<Object> parameters, Class<T> clazz) throws SQLException {

        Integer maxResults = requestContext.getParam(InternalConstants.MAX_RESULTS, Integer.class);
        Integer startPosition = requestContext.getParam(InternalConstants.START_POSITION, Integer.class);
        Integer allowedMaxResults = CommonProperties.getInstance().getInt("db.maxResponse");

        if (maxResults == null || maxResults == 0 || maxResults > allowedMaxResults) {
            maxResults = allowedMaxResults;
        }

        query += " limit " + maxResults;

        if (startPosition != null && startPosition > 0) {
            query += " offset " + startPosition;

        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Querying object: " + clazz.getSimpleName());
            logger.log(Level.FINE, "SQL: " + query);
        }

        List<JAXBElement<? extends IdentifiableType>> idents = new ArrayList<JAXBElement<? extends IdentifiableType>>();
        Connection conn = null;
        ResultSet result = null;

        try {
            conn = getConnection();
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
        } catch (SQLException ex) {
            closeConnection(conn);
            throw ex;
        }

        long startTime = System.currentTimeMillis();
        while (result.next()) {
            try {
                Class daoClass = Class.forName("be.kzen.ergorr.persist.dao." + clazz.getSimpleName() + "DAO");
                IdentifiableTypeDAO identDAO = (IdentifiableTypeDAO) daoClass.newInstance();
                identDAO.setConnection(conn);
                identDAO.setContext(requestContext);
                identDAO.newXmlObject(result);
                idents.add(identDAO.createJAXBElement());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Could not load DAO object for query", ex);
                closeConnection(conn);
                throw new SQLException("Could not load class " + clazz.getName() + ": " + ex.toString());
            }
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.INFO, "Load XML types time: " + (System.currentTimeMillis() - startTime));
        }
        closeConnection(conn);
        return idents;
    }

    public long getResultCount(String query, List<Object> parameters) throws SQLException {

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "SQL: " + query);
        }

        Connection conn = null;

        try {
            long startTime = System.currentTimeMillis();
            conn = getConnection();
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
            closeConnection(conn);
            return count;
        } catch (SQLException ex) {
            closeConnection(conn);
            throw ex;
        }
    }

    public boolean idExist(String id) throws SQLException {
        List<String> ids = new ArrayList();
        ids.add(id);
        List<String> res = idsExist(ids);
        return res.size() > 0;
    }

    public boolean idExists(String id, Class<? extends IdentifiableType> clazz) throws SQLException {
        List<String> ids = new ArrayList();
        ids.add(id);
        List<String> res = idsExist(ids, clazz);
        return res.size() > 0;
    }

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
            } catch (SQLException ex) {
                closeConnection(conn);
                throw ex;
            }

            closeConnection(conn);
        }

        return dbIds;
    }

    public List<String> idsExist(List<String> ids) throws SQLException {
        return idsExist(ids, "t_identifiable");
    }

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
            } catch (SQLException ex) {
                closeConnection(conn);
                throw ex;
            }

            closeConnection(conn);
        }

        return idents;
    }

    public void insert(List<IdentifiableType> idents) throws SQLException {
        persist(idents, true);
    }

    public void update(List<IdentifiableType> idents) throws SQLException {
        persist(idents, false);
    }

    private void persist(List<IdentifiableType> idents, boolean newObj) throws SQLException {
        Connection conn = getConnection();

        try {
            for (IdentifiableType ident : idents) {

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "Insert obj: " + ident.getClass().getSimpleName() + " " + ident.getId());
                }

                Class daoClass = Class.forName("be.kzen.ergorr.persist.dao." + ident.getClass().getSimpleName() + "DAO");
                Constructor constructor = daoClass.getConstructor(ident.getClass());
                GenericObjectDAO dao = (GenericObjectDAO) constructor.newInstance(ident);
                dao.setConnection(conn);

                if (newObj) {
                    dao.insert();
                } else {
                    dao.update();
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to insert objects", ex);

            if (ex.getNextException() != null) {
                logger.log(Level.SEVERE, "   NextException >>>>>", ex.getNextException());
            }
            closeConnection(conn);
            throw ex;
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Failed to insert objects", t);
            closeConnection(conn);
            throw new SQLException("Could not insert objects: " + t.toString());
        }

        closeConnection(conn);
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Could not close connection", ex);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        String dsName = CommonProperties.getInstance().get("db.datasource") + CommonProperties.getInstance().get("deployName");
        
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "attempting to get a connection");
            logger.log(Level.FINE, "datasource: " + dsName);
        }
        DataSource ds = null;
        try {
            ds = (DataSource) new InitialContext().lookup(dsName);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not get database DateSource", ex);
        }

        if (ds != null) {
            return ds.getConnection();
        } else {
            try {
                DbConnectionParams connParams = requestContext.getParam(InternalConstants.DB_CONNECTION_PARAMS, DbConnectionParams.class);

                if (connParams != null) {
                    System.out.println(connParams.toString());
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("Connection: " + connParams.toString());
                    }

                    Class.forName("org.postgresql.Driver");
                    return DriverManager.getConnection(connParams.createConnectionString(), connParams.getDbUser(), connParams.getDbPassword());
                } else {
                    throw new SQLException("No DataSource found and DbConnectionParams not set");
                }
            } catch (ClassNotFoundException ex) {
                throw new SQLException(ex.toString());
            }
        }
    }
}
