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

    public T getXmlObject() {
        return xmlObject;
    }

    public void setXmlObject(T xmlObject) {
        this.xmlObject = xmlObject;
    }

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
            logger.log(Level.SEVERE, "Could not load class", ex);
            throw new SQLException("Could not load class " + daoClass.getName());
        }

    }

    protected T loadXmlObject(ResultSet result) throws SQLException {
        xmlObject.setId(result.getString("id"));
        xmlObject.setHome(result.getString("home"));
        return xmlObject;
    }

    @Override
    protected void insertRelatedObjects() throws SQLException {
        if (xmlObject.isSetSlot()) {
            SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
            slotDAO.setConnection(connection);
            slotDAO.insert();
        }
    }

    @Override
    protected void updateRelatedObjects() throws SQLException {
        SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
        slotDAO.setConnection(connection);
        slotDAO.update();
    }

    @Override
    protected void deleteRelatedObjects() throws SQLException {
        SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
        slotDAO.setConnection(connection);
        slotDAO.delete();
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        if (returnSlots()) {
            SlotTypeDAO slotDAO = new SlotTypeDAO(xmlObject);
            slotDAO.setConnection(connection);
            slotDAO.addComposedObjects();
        }
    }

    @Override
    public String getTableName() {
        return "t_identifiable";
    }

    @Override
    protected String getParamList() {
        return "id,home";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder().append(alias).append(".id,").append(alias).append(".home").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<T> createJAXBElement() {
        return (genericObjDAO != null) ? genericObjDAO.createJAXBElement() : null;
    }

    public String getFetchCondition() {
        return "id='" + xmlObject.getId() + "'";
    }

    @Override
    protected String getPlaceHolders() {
        return (xmlObject.isNewObject() ? "?,?" : "id=?,home=?");
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, xmlObject.getId());
        stmt.setString(2, xmlObject.getHome());
    }
}
