package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.util.OFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class AssociationTypeDAO extends RegistryObjectTypeDAO<AssociationType> {

    public AssociationTypeDAO() {
    }

    public AssociationTypeDAO(AssociationType assoXml) {
        super(assoXml);
    }

    @Override
    public AssociationType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new AssociationType();
        return loadCompleteXmlObject(result);
    }

    @Override
    protected AssociationType loadXmlObject(ResultSet result) throws SQLException {
        super.loadXmlObject(result);

        if (!isBrief()) {
            xmlObject.setAssociationType(result.getString("associationtype"));
            xmlObject.setSourceObject(result.getString("sourceobject"));
            xmlObject.setTargetObject(result.getString("targetobject"));
        }
        return xmlObject;
    }

    @Override
    protected String createValues() {
        StringBuilder vals = new StringBuilder(super.createValues());
        vals.append(",");
        appendStringValue(xmlObject.getAssociationType(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getSourceObject(), vals);
        vals.append(",");
        appendStringValue(xmlObject.getTargetObject(), vals);

        return vals.toString();
    }

    @Override
    protected String createUpdateValues() {
        StringBuilder vals = new StringBuilder(super.createUpdateValues());
        vals.append(",associationtype=");
        appendStringValue(xmlObject.getAssociationType(), vals);
        vals.append(",sourceobject=");
        appendStringValue(xmlObject.getSourceObject(), vals);
        vals.append(",targetobject=");
        appendStringValue(xmlObject.getTargetObject(), vals);

        return vals.toString();
    }

    @Override
    protected String getParamList() {
        return super.getParamList() + ",associationtype,sourceobject,targetobject";
    }

    @Override
    protected String getQueryParamList() {
        if (alias != null && !alias.isEmpty()) {
            return new StringBuilder(super.getQueryParamList()).append(",").append(alias).append(".associationtype,").append(alias).append(".sourceobject,").append(alias).append(".targerobject").toString();
        } else {
            return getParamList();
        }
    }

    @Override
    public String getTableName() {
        return "association";
    }

    @Override
    public JAXBElement<AssociationType> createJAXBElement() {
        return OFactory.rim.createAssociation(xmlObject);
    }
}
