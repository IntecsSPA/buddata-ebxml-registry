/*
 * Project: Buddata ebXML RegRep
 * Class: SARTranslator.java
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
import be.kzen.ergorr.model.eo.sar.AcquisitionType;
import be.kzen.ergorr.model.eo.sar.EarthObservationType;
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
public class SARTranslator extends HMATranslator<EarthObservationType> {
    private static final String CLASSIFICATION = "urn:x-ogc:specification:csw-ebrim:EO:EOProductTypes:SAR";
    
    @Override
    public String getClassification() {
        return CLASSIFICATION;
    }
    
    @Override
    protected WrsExtrinsicObjectType translateProduct() {
        WrsExtrinsicObjectType e = super.translateProduct();
        
        if (eo.isSetUsing()) {
            if (eo.getUsing().isSetFeature() && eo.getUsing().getFeature().getValue() instanceof EarthObservationEquipmentType) {
                EarthObservationEquipmentType eoEquip = (EarthObservationEquipmentType) eo.getUsing().getFeature().getValue();
                
                if (eoEquip.isSetAcquisitionParameters() && eoEquip.getAcquisitionParameters().isSetAcquisition() &&
                        eoEquip.getAcquisitionParameters().getAcquisition().getValue() instanceof AcquisitionType) {
                    
                    AcquisitionType acq = (AcquisitionType) eoEquip.getAcquisitionParameters().getAcquisition().getValue();
                    
                    if (acq.isSetPolarisationMode()) {
                        SlotType slotPolMode = RIMUtil.createSlot(EOPConstants.S_POLARISATION_MODE, EOPConstants.T_STRING, acq.getPolarisationMode().value());
                        e.getSlot().add(slotPolMode);
                    }
                    
                    if (acq.isSetPolarisationChannels()) {
                        SlotType slotPolChannel = RIMUtil.createSlot(EOPConstants.S_POLARISATION_CHANNEL, EOPConstants.T_STRING, acq.getPolarisationChannels().value());
                        e.getSlot().add(slotPolChannel);
                    }
                    
                    if (acq.isSetAntennaLookDirection()) {
                        SlotType slotAntDirection = RIMUtil.createSlot(EOPConstants.S_ANTENNA_LOOK_DIRECTION, EOPConstants.T_STRING, acq.getAntennaLookDirection());
                        e.getSlot().add(slotAntDirection);
                    }
                    
                    if (acq.isSetMinimumIncidenceAngle()) {
                        SlotType slotMinIncAngle = RIMUtil.createWrsSlot(EOPConstants.S_MINIMUM_INCIDENCE_ANGLE, EOPConstants.T_DOUBLE, String.valueOf(acq.getMinimumIncidenceAngle().getValue()));
                        e.getSlot().add(slotMinIncAngle);
                    }
                    
                    if (acq.isSetMaximumIncidenceAngle()) {
                        SlotType slotMaxIncAngle = RIMUtil.createWrsSlot(EOPConstants.S_MAXIMUM_INCIDENCE_ANGLE, EOPConstants.T_DOUBLE, String.valueOf(acq.getMaximumIncidenceAngle().getValue()));
                        e.getSlot().add(slotMaxIncAngle);
                    }
                    
                    if (acq.isSetDopplerFrequency()) {
                        SlotType slotDopperFreq = RIMUtil.createWrsSlot(EOPConstants.S_DOPPER_FREQUENCY, EOPConstants.T_DOUBLE, String.valueOf(acq.getDopplerFrequency().getValue()));
                        e.getSlot().add(slotDopperFreq);
                    }
                    
                    if (acq.isSetIncidenceAngleVariation()) {
                        SlotType slotIncAngleVar = RIMUtil.createWrsSlot(EOPConstants.S_INCIDENCE_ANGLE_VARIATION, EOPConstants.T_DOUBLE, String.valueOf(acq.getIncidenceAngleVariation().getValue()));
                        e.getSlot().add(slotIncAngleVar);
                    }
                }
            }
        }
        return e;
    }

    @Override
    protected JAXBElement<EarthObservationType> getExtrinsicObjectJaxbEl() {
        return OFactory.eo_sar.createEarthObservation(eo);
    }
}
