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
package be.kzen.ergorr.commons;

import be.kzen.ergorr.exceptions.TransformException;
import com.vividsolutions.jts.geom.Geometry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import org.geotools.geometry.jts.GeometryCoordinateSequenceTransformer;
import org.geotools.referencing.CRS;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Yaman Ustuntas
 */
public class SrsTools {

    private static Logger log = Logger.getLogger(SrsTools.class);
    private static SrsTools instance;
    private Map<String, MathTransform> transformers;
    private GeometryCoordinateSequenceTransformer coordinateTransformer;

    private SrsTools() {
        coordinateTransformer = new GeometryCoordinateSequenceTransformer();
        transformers = new ConcurrentHashMap<String, MathTransform>();
    }

    public static synchronized SrsTools getInstance() {
        if (instance == null) {
            instance = new SrsTools();
        }

        return instance;
    }

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
        } catch (Throwable t) {
            throw new TransformException("Could not get SRS ID from SRS Name: " + srsName, t);
        }
    }

    private Geometry transformGeometry(Geometry geometry, String sourceSrs, String destSrs) throws Exception {
        MathTransform mt = transformers.get(sourceSrs);

        if (mt == null) {
            try {
                mt = CRS.findMathTransform(CRS.decode(sourceSrs), CRS.decode(destSrs), false);
            } catch (Exception ex) {
                log.warn("failed finding coordinate math transformer with lenient = false");
            }

            if (mt == null) {
                mt = CRS.findMathTransform(CRS.decode(sourceSrs), CRS.decode(destSrs), true);
            }

            transformers.put(sourceSrs, mt);
        }

        if (log.isDebugEnabled()) {
            log.debug("Transforming " + sourceSrs + " to " + destSrs);
        }
        coordinateTransformer.setMathTransform(mt);
        return coordinateTransformer.transform(geometry);
    }

    /**
     * Transform <code>geometry</code> coordinates from
     * <code>geometry.getSRID()</code> to the applications internally used srs ID.
     * 
     * @param geometry Geometry to transform.
     * @return Geometry with applications internally used srsName.
     * @throws be.kzen.ergorr.exceptions.QueryException
     */
    public Geometry transformGeometry(Geometry geometry) throws TransformException {
        int defaultSrsId = CommonProperties.getInstance().getInt("db.defaultSrsId");

        if (geometry.getSRID() != defaultSrsId) {
            try {
                geometry = SrsTools.getInstance().transformGeometry(geometry, "EPSG:" + geometry.getSRID(),
                        CommonProperties.getInstance().get("db.defaultSrsName"));
            } catch (Exception ex) {
                throw new TransformException("Could not translate geometry to " + CommonProperties.getInstance().get("db.defaultSrsName"), ex);
            }
        }

        geometry.setSRID(defaultSrsId);

        return geometry;
    }
}
