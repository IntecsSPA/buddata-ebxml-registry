/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kzen.ergorr.query;

import be.kzen.ergorr.interfaces.soap.RequestContext;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.persist.dao.RimDAO;
import be.kzen.ergorr.persist.dao.RimDAOLocalImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Yaman Ustuntas
 */
public class QueryManagerTest {

    public QueryManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testQuery() throws Exception {
        RimDAO rimDAO = new RimDAOLocalImpl();
        RequestContext rc = new RequestContext();
        rc.setRimDAO(rimDAO);

        QueryManager qm = new QueryManager(rc);
        GetRecordsResponseType response = qm.query();
    }

    @Test
    public void testQueryByIds() {
    }
}