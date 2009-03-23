package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.RegistryObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder(super.createValues());
        vals.append(xmlObject.isNewObject() ? "," : ",lid=");
        appendStringValue(xmlObject.getLid(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",objecttype=");
        appendStringValue(xmlObject.getObjectType(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",status=");
        appendStringValue(xmlObject.getStatus(), vals);
        vals.append(xmlObject.isNewObject() ? "," : ",versionname=");

        if (xmlObject.isSetVersionInfo()) {
            appendStringValue(xmlObject.getVersionInfo().getVersionName(), vals);
            vals.append(xmlObject.isNewObject() ? "," : ",versioncomment=");
            appendStringValue(xmlObject.getVersionInfo().getComment(), vals);
        } else {
            vals.append("''");
            vals.append(xmlObject.isNewObject() ? ",''" : ",versioncomment=''");
        }

        return vals.toString();
    }

    @Override
    protected void insertRelatedObjects(Statement batchStmt) throws SQLException {
        super.insertRelatedObjects(batchStmt);

        if (xmlObject.isSetName()) {
            NameDAO nameDAO = new NameDAO(xmlObject);
            nameDAO.setBatchStmt(batchStmt);
            nameDAO.insert();
        }

        if (xmlObject.isSetDescription()) {
            DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
            descDAO.setBatchStmt(batchStmt);
            descDAO.insert();
        }
    }

    @Override
    protected void loadRelatedObjects() throws SQLException {
        super.loadRelatedObjects();

        if (!isBrief()) {
            NameDAO nameDAO = new NameDAO(xmlObject);
            nameDAO.setConnection(connection);
            nameDAO.addComposedObjects();

            DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
            descDAO.setConnection(connection);
            descDAO.addComposedObjects();

            if (!isSummary()) {
                ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
                clDAO.setConnection(connection);
                clDAO.addClassifications(xmlObject);

                ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
                eiDAO.setConnection(connection);
                eiDAO.addExternalIdentifiers(xmlObject);
            }
        }
    }

    @Override
    protected void updateRelatedObjects(Statement batchStmt) throws SQLException {
        super.updateRelatedObjects(batchStmt);

        NameDAO nameDAO = new NameDAO(xmlObject);
        nameDAO.setBatchStmt(batchStmt);
        nameDAO.update();
        DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
        descDAO.setBatchStmt(batchStmt);
        descDAO.update();
    }

    @Override
    protected void deleteRelatedObjects(Statement batchStmt) throws SQLException {
        super.deleteRelatedObjects(batchStmt);
        NameDAO nameDAO = new NameDAO(xmlObject);
        nameDAO.setBatchStmt(batchStmt);
        nameDAO.delete();
        DescriptionDAO descDAO = new DescriptionDAO(xmlObject);
        descDAO.setBatchStmt(batchStmt);
        descDAO.delete();
        ClassificationTypeDAO clDAO = new ClassificationTypeDAO();
        clDAO.deleteClassifications(xmlObject, batchStmt);
        ExternalIdentifierTypeDAO eiDAO = new ExternalIdentifierTypeDAO();
        eiDAO.deleteExternalIdentifiers(xmlObject, batchStmt);
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",lid,objecttype,status,versionname,versioncomment";
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
}
