/*
 * Project: Buddata ebXML RegRep
 * Class: QueryBuilderImpl.java
 * Copyright (C) 2008 Yaman Ustuntas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.commons;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Date utility class.
 *
 * @author yamanustuntas
 */
public class DateUtil {
    private static final TimeZone GMT0_TZ = TimeZone.getTimeZone("Etc/UTC");
    private static final TimeZone LOCAL_TZ = TimeZone.getDefault();

    public static XMLGregorianCalendar getGMT0XMLCalendar(String xsdDateTimeStr) {
        DatatypeFactory df = getDatatypeFactory();
        XMLGregorianCalendar xmlDate = df.newXMLGregorianCalendar(xsdDateTimeStr);
        GregorianCalendar gmt0Cal = xmlDate.toGregorianCalendar(GMT0_TZ, null, null);
        gmt0Cal.add(Calendar.MINUTE, (xmlDate.getTimezone() * -1));
        return df.newXMLGregorianCalendar(gmt0Cal);
    }

    public static XMLGregorianCalendar getXMLCalendar(String xsdDateTimeStr) {
        return getDatatypeFactory().newXMLGregorianCalendar(xsdDateTimeStr);
    }

    public static String getGMT0XMLString(String xsdDateTimeStr) {
        return getGMT0XMLCalendar(xsdDateTimeStr).toXMLFormat();
    }

    public static long getGMT0Miliseconds(String xsdDateTimeStr) {
        XMLGregorianCalendar xmlDate = getDatatypeFactory().newXMLGregorianCalendar(xsdDateTimeStr);
        return xmlDate.toGregorianCalendar().getTime().getTime();
    }

    public static Calendar getGMT0Calendar(String xsdDateTimeStr) {
        Calendar c = getDatatypeFactory().newXMLGregorianCalendar(xsdDateTimeStr).toGregorianCalendar();
        Calendar c1 = new GregorianCalendar(GMT0_TZ);
        c1.setTimeInMillis(c.getTimeInMillis());
        return c1;
    }

    public static Calendar getCalendar(String xsdDateTimeStr) {
        return getDatatypeFactory().newXMLGregorianCalendar(xsdDateTimeStr).toGregorianCalendar();
    }

    private static DatatypeFactory getDatatypeFactory() {
        try {
            return DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            // should never happen as it is thrown if
            // implementation does not exist.
            ex.printStackTrace();
            return null;
        }
    }
}
