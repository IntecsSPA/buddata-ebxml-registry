package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 *
 * @author Yaman Ustuntas
 */
public class RegistryPackageTypeDAO extends RegistryObjectTypeDAO<RegistryPackageType> {
    private static Logger logger = Logger.getLogger(RegistryPackageTypeDAO.class.getName());

    public RegistryPackageTypeDAO() {
    }

    public RegistryPackageTypeDAO(RegistryPackageType pkgXml) {
        super(pkgXml);
    }

    @Override
    public RegistryPackageType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new RegistryPackageType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected RegistryPackageType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        return xmlObject;
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (!isBrief()) {
            if (CommonProperties.getInstance().getBoolean("db.loadPackageMembers")) {
                RegistryObjectListType regObjList = new RegistryObjectListType();
                StringBuilder sql = new StringBuilder();

                sql.append("select * from t_identifiable where id in (select targetobject from t_association where sourceobject='").append(xmlObject.getId());
                sql.append("' and associationtype='").append(RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember).append("')");

                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("RegistryPackage members query: " + sql.toString());
                }

                Statement stmt = connection.createStatement();
                ResultSet result = stmt.executeQuery(sql.toString());

                while (result.next()) {
                    IdentifiableTypeDAO identDAO = new IdentifiableTypeDAO();
                    identDAO.setContext(context);
                    identDAO.setConnection(connection);
                    IdentifiableType ident = identDAO.newXmlObject(result);
                    QName qName = new QName(NamespaceConstants.RIM, CommonFunctions.getElementName(ident.getClass().getSimpleName()));
                    regObjList.getIdentifiable().add(new JAXBElement(qName, ident.getClass(), ident));
                }

                xmlObject.setRegistryObjectList(regObjList);
            }
        }
    }

    @Override
    public JAXBElement<RegistryPackageType> createJAXBElement() {
        return OFactory.rim.createRegistryPackage(xmlObject);
    }

    @Override
    public String getTableName() {
        return "t_registrypackage";
    }
}
