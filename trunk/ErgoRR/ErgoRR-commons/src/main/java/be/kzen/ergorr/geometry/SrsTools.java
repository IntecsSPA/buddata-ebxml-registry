/*
 * Project: Buddata ebXML RegRep
 * Class: SrsTools.java
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
package be.kzen.ergorr.geometry;

import be.kzen.ergorr.commons.CommonProperties;
import be.kzen.ergorr.exceptions.TransformException;
import java.util.logging.Logger;

/**
 * SRS helper to read/parse SRS names/IDs.
 * 
 * @author Yaman Ustuntas
 */
public class SrsTools {

    /**
     * Get the SRS ID from a given <code>srsName</code>.
     * 
     * Input case examples:
     * "4326" or
     * "EPSG:4326" or
     * "urn:ogc:def:crs:EPSG:4326" or
     * "http://www.opengis.net/gml/srs/epsg.xml#4326"
     * 
     * @param srsName SRS name.
     * @return SRS ID extracted from the name.
     * @throws java.lang.NumberFormatException
     */
    public static int getSrsId(String srsName) throws TransformException {
        try {

            if (srsName != null) {

                // check for cases like "http://www.opengis.net/gml/srs/epsg.xml#4326"
                int idx = srsName.lastIndexOf("#");

                if (idx > 0) {
                    return Integer.valueOf(srsName.substring(idx + 1, srsName.length()));
                } else {
                    // check for cases like "EPSG:4326" or "urn:ogc:def:crs:EPSG:4326"
                    idx = srsName.lastIndexOf(":");

                    if (idx > 0) {
                        return Integer.valueOf(srsName.substring(idx + 1, srsName.length()));
                    } else {
                        return Integer.valueOf(srsName);
                    }
                }
            } else {
                return CommonProperties.getInstance().getInt("db.defaultSrsId");
            }
        } catch (Throwable t) {
            throw new TransformException("Could not get SRS ID from SRS Name: " + srsName, t);
        }
    }
}
