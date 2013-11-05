/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kzen.ergorr.commons;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yamanustuntas
 */
public class DateUtilTest {
    private DatatypeFactory df;

    public DateUtilTest() {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();;
        }
    }

    @Test
    public void testGetGMT0XMLCalendar() {
        String xsdDateTimeStr = "2009-01-01T15:00:00.000+02:00";
        String expXsdDateTimeStr = "2009-01-01T13:00:00.000+00:00";

        XMLGregorianCalendar expResult = df.newXMLGregorianCalendar(expXsdDateTimeStr);
        XMLGregorianCalendar result = DateUtil.getGMT0XMLCalendar(xsdDateTimeStr);
        assertEquals(expResult.toXMLFormat(), result.toXMLFormat());
    }

    @Test
    public void testGetGMT0XMLString() {
        String xsdDateTimeStr = "2009-01-01T15:00:00.000+02:00";
        String expResult = "2009-01-01T13:00:00.000Z";

        String result = DateUtil.getGMT0XMLString(xsdDateTimeStr);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetGMT0Miliseconds() {
        String xsdDateTimeStr = "2009-02-02T15:00:00.000+02:00";
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("Etc/UTC"));
        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long result = DateUtil.getGMT0Miliseconds(xsdDateTimeStr);
        assertEquals(cal.getTimeInMillis(), result);
    }

    @Test
    public void testGetGMT0Calendar() {
        String xsdDateTimeStr = "2009-02-02T15:00:00.000+02:00";
        Calendar c = DateUtil.getGMT0Calendar(xsdDateTimeStr);
        System.out.println(c.toString());
        System.out.println(c.getTimeInMillis());
        System.out.println(c.getTime().getTime());
        System.out.println(c.getTime());
    }
}