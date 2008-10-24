package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.persist.service.ServiceHelper;
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
        int classId = result.getInt("class_");
        Class<? extends GenericObjectDAO> clazz = ServiceHelper.getClass(classId);

        try {
            genericObjDAO = clazz.newInstance();
            genericObjDAO.setConnection(connection);
            return (T) genericObjDAO.newXmlObject(result);
        } catch (Exception ex) {
            throw new SQLException("Could not load class " + clazz.getName(), ex);
        }
        
    }

    protected T loadXmlObject(ResultSet result) throws SQLException {
        xmlObject.setId(result.getString("id"));
        xmlObject.setHome(result.getString("home"));
        loadRelatedObjects();
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

    @Override
    protected void insertRelatedObjects(Statement batchStmt) throws SQLException {
        if (xmlObject.isSetSlot()) {
            SlotTypeDAO slotDAO = new SlotTypeDAO();
            slotDAO.insert(xmlObject, batchStmt);
        }
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
        if (alias != null && !alias.isEmpty()) {
            return new StringBuilder().append(alias).append(".id,").append(alias).append(".home").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<T> createJAXBElement() {
        return (genericObjDAO != null) ? genericObjDAO.createJAXBElement() : null;
    }
}
