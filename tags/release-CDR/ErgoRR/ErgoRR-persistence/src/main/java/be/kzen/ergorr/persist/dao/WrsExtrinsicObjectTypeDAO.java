
package be.kzen.ergorr.persist.dao;

import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class WrsExtrinsicObjectTypeDAO extends ExtrinsicObjectTypeDAO<WrsExtrinsicObjectType> {

    public WrsExtrinsicObjectTypeDAO() {
    }

    public WrsExtrinsicObjectTypeDAO(WrsExtrinsicObjectType eoXml) {
        super(eoXml);
    }

     @Override
    public WrsExtrinsicObjectType newXmlObject(ResultSet result) throws SQLException {
        xmlObject = new WrsExtrinsicObjectType();
        return loadCompleteXmlObject(result);
    }

    @Override
    public JAXBElement<WrsExtrinsicObjectType> createJAXBElement() {
        return OFactory.wrs.createExtrinsicObject(xmlObject);
    }
}
