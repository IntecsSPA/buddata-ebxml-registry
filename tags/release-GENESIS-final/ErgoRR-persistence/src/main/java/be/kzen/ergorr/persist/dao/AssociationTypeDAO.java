package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 * Association DAO.
 * 
 * @author Yaman Ustuntas
 */
public class AssociationTypeDAO extends RegistryObjectTypeDAO<AssociationType> {

    public AssociationTypeDAO() {
    }

    public AssociationTypeDAO(AssociationType assoXml) {
        super(assoXml);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssociationType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new AssociationType();
        return loadCompleteXmlObject(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AssociationType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        xmlObject.setAssociationType(result.getString("associationtype"));
        xmlObject.setSourceObject(result.getString("sourceobject"));
        xmlObject.setTargetObject(result.getString("targetobject"));
        return xmlObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setParameters(PreparedStatement stmt) throws SQLException {
        super.setParameters(stmt);
        stmt.setString(8, xmlObject.getAssociationType());
        stmt.setString(9, xmlObject.getSourceObject());
        stmt.setString(10, xmlObject.getTargetObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParamList() {
        return super.getParamList() + ",associationtype,sourceobject,targetobject";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlaceHolders() {
        return super.getPlaceHolders() + (xmlObject.isNewObject() ? ",?,?,?" : ",associationtype=?,sourceobject=?,targetobject=?");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.equals("")) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".associationtype,").append(alias).append(".sourceobject,").append(alias).append(".targerobject").toString();
        } else {
            return getParamList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "t_association";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAXBElement<AssociationType> createJAXBElement() {
        return OFactory.rim.createAssociation(xmlObject);
    }
}
