/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.model.csw.CapabilitiesType;
import be.kzen.ergorr.model.csw.DescribeRecordResponseType;
import be.kzen.ergorr.model.csw.DescribeRecordType;
import be.kzen.ergorr.model.csw.GetCapabilitiesType;
import be.kzen.ergorr.model.csw.GetDomainResponseType;
import be.kzen.ergorr.model.csw.GetDomainType;
import be.kzen.ergorr.model.csw.GetRecordByIdResponseType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.HarvestResponseType;
import be.kzen.ergorr.model.csw.HarvestType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;
import be.kzen.ergorr.model.util.JAXBUtil;
import be.kzen.ergorr.persist.service.DbConnectionParams;
import be.kzen.ergorr.model.util.OFactory;
import javax.xml.bind.JAXBElement;
import junit.framework.TestCase;

/**
 *
 * @author yamanustuntas
 */
public class CswBackendClientTest extends TestCase {
    
    public CswBackendClientTest(String testName) {
        super(testName);
    }

    public void testGetCapabilities() throws Exception {
    }

    public void testGetRecords() throws Exception {
    }

    public void testGetRecordById() throws Exception {
    }

    public void testGetDomain() throws Exception {
    }

    public void testHarvest() throws Exception {
        DbConnectionParams dbConn = new DbConnectionParams();
        dbConn.setDbName(CommonProperties.getInstance().get("deployName"));
        dbConn.setDbPassword(CommonProperties.getInstance().get("db.password"));
        dbConn.setDbUrl(CommonProperties.getInstance().get("db.url"));
        dbConn.setDbUser(CommonProperties.getInstance().get("db.user"));
        CswBackendClient client = new CswBackendClient(dbConn);

        HarvestType req = new HarvestType();
        req.setSource("http://pisa007.pisa.intecs.it/EODATA/ESA-EECF-ENVISAT_ASA_GMI_1S-EN1-07010101263568-14914-XG0.xml");

        HarvestResponseType res = client.harvest(req);
        assertTrue(res.getTransactionResponse().getInsertResult().size() > 0);

//        JAXBElement<HarvestResponseType> resEl = OFactory.csw.createHarvestResponse(res);
//        System.out.println(JAXBUtil.getInstance().marshallToStr(resEl));
    }

    public void testTransact() throws Exception {
    }

    public void testGetRecordDescription() throws Exception {
    }

}
