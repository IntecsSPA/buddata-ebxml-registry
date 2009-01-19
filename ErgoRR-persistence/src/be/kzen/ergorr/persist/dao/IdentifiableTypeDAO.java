package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.IdentifiableType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class IdentifiableTypeDAO<T extends IdentifiableType> extends GenericObjectDAO<T> {

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
            throw new SQLException("Could not load class " + daoClass.getName());
        }

    }

    protected T loadXmlObject(ResultSet result) throws SQLException {
        xmlObject.setId(result.getString("id"));

        if (!isBrief()) {
            xmlObject.setHome(result.getString("home"));
        }
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder();
        appendStringValue(xmlObject.getId(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getHome(), vals);
        return vals.toString();
    }

    protected String createUpdateValues() {
        StringBuilder vals = new StringBuilder();
        vals.append("id=");
        appendStringValue(xmlObject.getId(), vals);
        vals.append(",home=");
        appendStringValue(xmlObject.getHome(), vals);

        return vals.toString();
    }

    @Override
    protected void insertRelatedObjects(Statement batchStmt) throws SQLException {
        if (xmlObject.isSetSlot()) {
            SlotTypeDAO slotDAO = new SlotTypeDAO();
            slotDAO.insert(xmlObject, batchStmt);
        }
    }

    @Override
    protected void updateRelatedObjects(Statement batchStmt) throws SQLException {
    }

    @Override
    protected void deleteRelatedObjects(Statement batchStmt) throws SQLException {
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        SlotTypeDAO slotDAO = new SlotTypeDAO();
        slotDAO.setConnection(connection);
        slotDAO.addComposedObjects(xmlObject);
    }

    @Override
    public String getTableName() {
        return "identifiable";
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
}
