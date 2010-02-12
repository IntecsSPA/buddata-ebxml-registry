package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * IdentifiableType DAO.
 * 
 * @author Yaman Ustuntas
 */
public class IdentifiableTypeDAO<T extends IdentifiableType> extends GenericObjectDAO<T> {
    private static Logger logger = Logger.getLogger(IdentifiableTypeDAO.class.getName());
    protected T xmlObject;
    private GenericObjectDAO genericObjDAO;

    public IdentifiableTypeDAO() {
    }

    public IdentifiableTypeDAO(String alias) {
        this.alias = alias;
    }

    public IdentifiableTypeDAO(T xmlObject) {
        this.xmlObject = xmlObject;
    }

    /**
     * Get XML object.
     *
     * @return XML object.
     */
    public T getXmlObject() {
        return xmlObject;
    }

    /**
     * Set the XML object.
     *
     * @param xmlObject XML object.
     */
    public void setXmlObject(T xmlObject) {
        this.xmlObject = xmlObject;
    }

    /**
     * Load a new XML object from the ResultSet {@code result}.
     * ResultSet must be from the t_identifiable VIEW as it contains the class name
     * of the object in the 'class_' column.
     * This helps to load the proper subtype of IdentifiableType using reflection.
     * 
     * @param result ResultSet to load a new instance of Identifiable.
     * @return New instance of {@code T}.
     * @throws SQLException
     */
    public T newXmlObject(ResultSet result) throws SQLException {
        String daoClassName = "be.kzen.ergorr.persist.dao." + result.getString("class_") + "TypeDAO";
        Class<? extends GenericObjectDAO> daoClass = null;
        try {
            daoClass = (Class<? extends GenericObjectDAO>) Class.forName(daoClassName);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Could not find DAO class: " + daoClassName);
        }

        try {
            genericObjDAO = daoClass.newInstance();
            genericObjDAO.setConnection(connection);
            genericObjDAO.setContext(context);
            return (T) genericObjDAO.newXmlObject(result);
        } catch (Exception ex) {
            String err = "Could not load class " + daoClass.getName();
            logger.log(Level.WARNING, err, ex);
            throw new SQLException(err);
        }

    }

    /**
     * {@inheritDoc}
     */
    protected T loadXmlObject(ResultSet result) throws SQLException {
        xmlObject.setId(result.getString("id"));
        xmlObject.setHome(result.getString("home"));
        return xmlObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void insertRelatedObjects() throws SQLException {
        if (xmlObject.isSetSlot()) {
            SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
            slotDAO.setContext(context);
            slotDAO.setConnection(connection);
            slotDAO.insert();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateRelatedObjects() throws SQLException {
        SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
        slotDAO.setContext(context);
        slotDAO.setConnection(connection);
        slotDAO.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deleteRelatedObjects() throws SQLException {
        SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
        slotDAO.setContext(context);
        slotDAO.setConnection(connection);
        slotDAO.delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadRelatedObjects() throws SQLException {
        if (returnSlots()) {
            SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
            slotDAO.setContext(context);
            slotDAO.setConnection(connection);
            slotDAO.addComposedObjects();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_identifiable";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return "id,home";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder().append(alias).append(".id,").append(alias).append(".home").toString();
        } else {
            return getParamList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAXBElement<T> createJAXBElement() {
        return (genericObjDAO != null) ? genericObjDAO.createJAXBElement() : null;
    }

    /**
     * {@inheritDoc}
     */
    public String getFetchCondition() {
        return new StringBuilder("id='").append(xmlObject.getId()).append("'").toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return (xmlObject.isNewObject() ? "?,?" : "id=?,home=?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, xmlObject.getId());
        stmt.setString(2, xmlObject.getHome());
    }
}
