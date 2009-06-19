package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.commons.CommonFunctions;
import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.commons.NamespaceConstants;
import be.kzen.ergorr.commons.RIMConstants;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

        if (CommonProperties.getInstance().getBoolean("db.loadPackageMembers")) {
            RegistryObjectListType regObjList = new RegistryObjectListType();
            StringBuilder sql = new StringBuilder();

            sql.append("select ident.* from t_identifiable ident inner join t_association asso on ident.id = asso.targetobject where asso.sourceobject = '");
            sql.append(xmlObject.getId()).append("' and asso.associationtype='").append(RIMConstants.CN_ASSOCIATION_TYPE_ID_HasMember).append("';");

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("RegistryPackage members query: " + sql.toString());
            }

            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(sql.toString());

            while (result.next()) {
                IdentifiableTypeDAO identDAO = new IdentifiableTypeDAO();
                identDAO.setContext(context);
                identDAO.setConnection(connection);
                identDAO.newXmlObject(result);

                regObjList.getIdentifiable().add(identDAO.createJAXBElement());
            }

            xmlObject.setRegistryObjectList(regObjList);
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
