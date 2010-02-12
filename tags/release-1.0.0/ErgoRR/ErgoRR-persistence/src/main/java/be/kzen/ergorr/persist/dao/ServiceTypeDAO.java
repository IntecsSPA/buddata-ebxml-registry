package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBElement;

/**
 * Service DAO.
 * 
 * @author yamanustuntas
 */
public class ServiceTypeDAO extends RegistryObjectTypeDAO<ServiceType> {

    public ServiceTypeDAO() {
    }

    public ServiceTypeDAO(ServiceType serviceXml) {
        super(serviceXml);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceType newXmlObject(ResultSet result) throws SQLException {
        return loadCompleteXmlObject(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ServiceType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        return xmlObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (returnNestedObjects()) {
            ServiceBindingTypeDAO bindingDAO = new ServiceBindingTypeDAO();
            bindingDAO.setContext(context);
            bindingDAO.setConnection(connection);
            bindingDAO.addServiceBinding(xmlObject);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_service";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAXBElement<ServiceType> createJAXBElement() {
        return OFactory.rim.createService(xmlObject);
    }
}
