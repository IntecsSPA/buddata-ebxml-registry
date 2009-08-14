package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.RegistryObjectType;
import be.kzen.ergorr.model.rim.VersionInfoType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class RegistryObjectTypeDAO<T extends RegistryObjectType> extends IdentifiableTypeDAO<T> {

    public RegistryObjectTypeDAO() {
    }

    public RegistryObjectTypeDAO(String alias) {
        super(alias);
    }

    public RegistryObjectTypeDAO(T roXml) {
        super(roXml);
    }

    @Override
    protected T loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);
        xmlObject.setLid(result.getString("lid"));
        xmlObject.setObjectType(result.getString("objecttype"));
        VersionInfoType versionInfo = new VersionInfoType();
        versionInfo.setVersionName(versionInfo.getVersionName()); // set the default
        xmlObject.setVersionInfo(versionInfo);
        return xmlObject;
    }

    @Override
    protected void insertRelatedObjects() throws SQLException {
        super.insertRelatedObjects();

        if (xmlObject.isSetName()) {
            NameDAO nameDAO = new NameDAO(xmlObject);
            nameDAO.setConnection(connection);
            nameDAO.insert();
        }

        if (xmlObject.isSetDescription()) {
            DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
            descDAO.setConnection(connection);
            descDAO.insert();
        }
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (returnNameDesc()) {
            NameDAO nameDAO = new NameDAO(xmlObject);
            nameDAO.setConnection(connection);
            nameDAO.addComposedObjects();

            DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
            descDAO.setConnection(connection);
            descDAO.addComposedObjects();
        }

        if (returnNestedObjects()) {
            ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
            clDAO.setConnection(connection);
            clDAO.addClassifications(xmlObject);

            ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
            eiDAO.setConnection(connection);
            eiDAO.addExternalIdentifiers(xmlObject);
        }
    }

    @Override
    protected void updateRelatedObjects() throws SQLException {
        super.updateRelatedObjects();

        NameDAO nameDAO = new NameDAO(xmlObject);
        nameDAO.setConnection(connection);
        nameDAO.update();
        DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
        descDAO.setConnection(connection);
        descDAO.update();
    }

    @Override
    protected void deleteRelatedObjects() throws SQLException {
        super.deleteRelatedObjects();
        NameDAO nameDAO = new NameDAO(xmlObject);
        nameDAO.setConnection(connection);
        nameDAO.delete();
        DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
        descDAO.setConnection(connection);
        descDAO.delete();
        ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
        clDAO.setConnection(connection);
        clDAO.deleteClassifications(xmlObject);
        ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
        eiDAO.setConnection(connection);
        eiDAO.deleteExternalIdentifiers(xmlObject);

//        String sqlAsso = "select * from t_association asso where asso.targetobject ='" +
//                xmlObject.getId() + "' or asso.sourceobject = '" + xmlObject.getId() +"';";
//
//        Statement assoStmt = connection.createStatement();
//        ResultSet assoResults = assoStmt.executeQuery(sqlAsso);
//
//        // TODO - validate, deleted association shouldn't be in a package.
//        while (assoResults.next()) {
//            AssociationTypeDAO assoDAO = new AssociationTypeDAO();
//            assoDAO.setConnection(connection);
//            assoDAO.setContext(context);
//            assoDAO.newXmlObject(assoResults);
//            assoDAO.delete();
//        }
//
//        assoStmt.close();
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",lid,objecttype,status,versionname,versioncomment";
    }

    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?,?,?" : ",lid=?,objecttype=?,status=?,versionname=?,versioncomment=?");
    }

    @Override
    protected String getQueryParamList() {

        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".lid,").append(alias).append(".objecttype,").append(alias).append(".status,").append(alias).append(".versionname,").append(alias).append(".versioncomment").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public JAXBElement<T> createJAXBElement() {
        throw new UnsupportedOperationException("Should not return Identifiable");
    }

    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(3, xmlObject.getLid());
        stmt.setString(4, xmlObject.getObjectType());
        stmt.setString(5, xmlObject.getStatus());

        if (xmlObject.isSetVersionInfo()) {
            stmt.setString(6, xmlObject.getVersionInfo().getVersionName());
            stmt.setString(7, xmlObject.getVersionInfo().getComment());
        } else {
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.VARCHAR);
        }
    }
}
