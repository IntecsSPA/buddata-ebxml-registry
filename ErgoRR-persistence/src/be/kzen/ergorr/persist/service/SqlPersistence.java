package be.kzen.ergorr.persist.service;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RequestContext;
import be.kzen.ergorr.persist.InternalSlotTypes;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
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
            
            for (int i = 0; i < parameters.size(); i++) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Query parameter " + i + ": " + parameters.get(i).toString());
                }

                stmt.setObject(i + 1, parameters.get(i));
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
                IdentifiableTypeDAO identDao = (IdentifiableTypeDAO) daoClass.newInstance();
                identDao.setConnection(conn);
                identDao.setContext(requestContext);
                identDao.newXmlObject(result);
                idents.add(identDao.createJAXBElement());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Could not load DAO object for query", ex);
                closeConnection(conn);
                throw new SQLException("Could not load class " + clazz.getName(), ex);
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

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
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
                String sql = "select * from identifiable where id in " + values.toString();

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "SQL: " + sql);
                }

                conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery(sql);

                while (results.next()) {
                    IdentifiableTypeDAO identDAO = new IdentifiableTypeDAO();
                    identDAO.setConnection(conn);
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
                dao.insert();
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
            throw new SQLException("Could not insert objects", t);
        }

        closeConnection(conn);
    }

    public Map<String, String> getSlotTypes() throws SQLException {
        Map<String, String> slotNameTypes = new ConcurrentHashMap<String, String>();

        Connection conn = null;

        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            long startTime = System.currentTimeMillis();
            ResultSet result = stmt.executeQuery("select distinct(slotname), slottype from slot ");

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Queried slot types in ms:" + (System.currentTimeMillis() - startTime));
            }

            while (result.next()) {
                String slotName = result.getString(1);
                String slotType = "string";

                if (result.getString(2) != null) {
                    String val = result.getString(2);

                    if (InternalSlotTypes.isNoneStringType(val)) {
                        slotType = val;
                    }
                }

                slotNameTypes.put(slotName, slotType.toLowerCase());
            }
            closeConnection(conn);
        } catch (SQLException ex) {
            closeConnection(conn);
            throw ex;
        }
        return slotNameTypes;
    }

    public Map<String, String> querySlotTypes() throws SQLException {
        Map<String, String> slotTypes = new ConcurrentHashMap<String, String>();

        Connection conn = null;

        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select slotname, slottype from slottype");

            while (result.next()) {
                slotTypes.put(result.getString(1), result.getString(2));
            }
            stmt.close();
            closeConnection(conn);

            return slotTypes;
        } catch (SQLException ex) {
            closeConnection(conn);
            throw ex;
        }
    }

    public void insertSlotType(String slotName, String slotType) throws SQLException {
        Connection conn = null;

        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("insert into slottype (slotname, slottype) values ('" + slotName + "','" + slotType + "')");
            stmt.close();
            closeConnection(conn);
        } catch (SQLException ex) {
            closeConnection(conn);
            throw ex;
        }
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
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "attempting to get a connection");
        }
        DataSource ds = null;
        try {
            ds = (DataSource) new InitialContext().lookup(CommonProperties.getInstance().get("db.datasource"));
        } catch (Exception ex) {
            //logger.log(Level.SEVERE, "Could not get database DateSource", ex);
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
                throw new SQLException(ex);
            }
        }
    }
}
