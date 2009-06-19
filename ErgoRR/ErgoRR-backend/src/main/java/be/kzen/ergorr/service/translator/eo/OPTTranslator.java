  /*
 * Project: Buddata ebXML RegRep
 * Class: OPTTranslator.java
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

import be.kzen.ergorr.service.translator.*;
import be.kzen.ergorr.model.eo.eop.EarthObservationEquipmentType;
import be.kzen.ergorr.model.eo.opt.AcquisitionType;
import be.kzen.ergorr.model.eo.opt.EarthObservationResultType;
import be.kzen.ergorr.model.eo.opt.EarthObservationType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.commons.EOPConstants;
import be.kzen.ergorr.commons.RIMUtil;
import be.kzen.ergorr.model.util.OFactory;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Yaman Ustuntas
 */
public class OPTTranslator<T extends EarthObservationType> extends HMATranslator<EarthObservationType> {

    private static final String CLASSIFICATION = "urn:x-ogc:specification:csw-ebrim:EO:EOProductTypes:OPT"; 

    @Override
    public String getClassification() {
        return CLASSIFICATION;
    }

    @Override
    protected WrsExtrinsicObjectType translateProduct() throws TranslationException {
        WrsExtrinsicObjectType e = super.translateProduct();

        if (eo.isSetUsing()) {
            if (eo.getUsing().isSetFeature() && eo.getUsing().getFeature().getValue() instanceof EarthObservationEquipmentType) {
                EarthObservationEquipmentType eoEquip = (EarthObservationEquipmentType) eo.getUsing().getFeature().getValue();

                if (eoEquip.isSetAcquisitionParameters() && eoEquip.getAcquisitionParameters().isSetAcquisition() &&
                        eoEquip.getAcquisitionParameters().getAcquisition().getValue() instanceof AcquisitionType) {

                    AcquisitionType acq = (AcquisitionType) eoEquip.getAcquisitionParameters().getAcquisition().getValue();

                    if (acq.isSetIlluminationAzimuthAngle()) {
                        SlotType slotIlluAziAngle = RIMUtil.createSlot(EOPConstants.S_ILLUMINATION_AZIMUTH_ANGLE, EOPConstants.T_DOUBLE, String.valueOf(acq.getIlluminationAzimuthAngle().getValue()));
                        e.getSlot().add(slotIlluAziAngle);
                    }

                    if (acq.isSetIlluminationElevationAngle()) {
                        SlotType slotIlluElevAngle = RIMUtil.createSlot(EOPConstants.S_ILLUMINATION_ELEVATION_ANGLE, EOPConstants.T_DOUBLE, String.valueOf(acq.getIlluminationElevationAngle().getValue()));
                        e.getSlot().add(slotIlluElevAngle);
                    }
                }
            }
        }
        
        if (eo.isSetResultOf()) {
            if (eo.getResultOf().isSetObject()) {
                JAXBElement el = eo.getResultOf().getObject();
                
                if (el.getValue() instanceof EarthObservationResultType) {
                    EarthObservationResultType result = (EarthObservationResultType) el.getValue();
                    
                    if (result.isSetCloudCoverPercentage()) {
                        SlotType slotCouldPercent = RIMUtil.createSlot(EOPConstants.S_COULD_COVER_PERCENTAGE, EOPConstants.T_DOUBLE, String.valueOf(result.getCloudCoverPercentage().getValue()));
                        e.getSlot().add(slotCouldPercent);
                    }
                    
                    if (result.isSetSnowCoverPercentage()) {
                        SlotType slotSnowPercent = RIMUtil.createSlot(EOPConstants.S_SNOW_COVER_PERCENTAGE, EOPConstants.T_DOUBLE, String.valueOf(result.getSnowCoverPercentage().getValue()));
                        e.getSlot().add(slotSnowPercent);
                    }
                }
            }
        }
        return e;
    }

    @Override
    protected JAXBElement<EarthObservationType> getEarthObservationJaxbEl() {
        return OFactory.eo_opt.createEarthObservation(eo);
    }
}
