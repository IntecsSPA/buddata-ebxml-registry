package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.ServiceType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author yamanustuntas
 */
public class ServiceTypeDAO extends RegistryObjectTypeDAO<ServiceType> {

    public ServiceTypeDAO() {
    }

    public ServiceTypeDAO(ServiceType serviceXml) {
        super(serviceXml);
    }

    @Override
    public ServiceType newXmlObject(ResultSet result) throws SQLException {
        return loadCompleteXmlObject(result);
    }

    @Override
    protected ServiceType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        return xmlObject;
    }

    protected void loadRelateObjects() throws SQLException {
        if (returnNestedObjects()) {
            ServiceBindingTypeDAO bindingDAO = new ServiceBindingTypeDAO();
            bindingDAO.setConnection(connection);
        }
    }

    @Override
    public String getTableName() {
        return "t_service";
    }

    @Override
    public JAXBElement<ServiceType> createJAXBElement() {
        return OFactory.rim.createService(xmlObject);
    }
}
