
package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.csw.GetRecordByIdResponseType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import org.junit.Test;

/**
 *
 * @author Yaman Ustuntas
 */
public class CswBackendClientTest {

    public CswBackendClientTest() {
    }

    @Test
    public void testCswGetRecordById() throws Exception {
        GetRecordByIdType getRecordById = new GetRecordByIdType();
        getRecordById.getId().add("urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct:Definition");
        DbConnectionParams cp = new DbConnectionParams();
        cp.setDbUrl(CommonProperties.getInstance().get("db.url"));
        cp.setDbName(CommonProperties.getInstance().get("db.name"));
        cp.setDbUser(CommonProperties.getInstance().get("db.user"));
        cp.setDbPassword(CommonProperties.getInstance().get("db.password"));
        CswBackendClient service = new CswBackendClient(cp);
        GetRecordByIdResponseType response = service.getRecordById(getRecordById);
        
        System.out.println(JAXBUtil.getInstance().marshall(OFactory.csw.createGetRecordByIdResponse(response)));
    }
}