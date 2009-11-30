 /*
 * Project: Buddata ebXML RegRep
 * Class: PHRTranslator.java
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
package be.kzen.ergorr.service.translator.eo;

import be.kzen.ergorr.model.eo.sar.EarthObservationType;
import be.kzen.ergorr.model.util.OFactory;
import javax.xml.bind.JAXBElement;

/**
 * TO BE IMPLEMENTED
 * 
 * @author Yaman Ustuntas
 */
public class PHRTranslator extends HMATranslator<EarthObservationType> {
    private static final String CLASSIFICATION = "urn:x-ogc:specification:csw-ebrim:EO:EOProductTypes:SAR"; 

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClassification() {
        return CLASSIFICATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JAXBElement<EarthObservationType> getEarthObservationJaxbEl() {
        return OFactory.eo_sar.createEarthObservation(eo);
    }
}
