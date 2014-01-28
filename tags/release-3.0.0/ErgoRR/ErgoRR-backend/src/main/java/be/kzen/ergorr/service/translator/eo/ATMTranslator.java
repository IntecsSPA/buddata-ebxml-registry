/*
 * Project: Buddata ebXML RegRep
 * Class: ATMTranslator.java
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

import be.kzen.ergorr.model.eo.atm.DataLayerType;
import be.kzen.ergorr.model.eo.atm.EarthObservationResultType;
import be.kzen.ergorr.model.eo.atm.EarthObservationType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.commons.EOPConstants;
import be.kzen.ergorr.commons.RIMUtil;
import be.kzen.ergorr.service.translator.TranslationException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 * ATM translator
 * 
 * @author Yaman Ustuntas
 */
public class ATMTranslator extends HMATranslator<EarthObservationType> {

    private static final String CLASSIFICATION = "urn:x-ogc:specification:csw-ebrim:EO:EOProductTypes:ATM"; 

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
    public RegistryObjectListType translate() throws TranslationException {
        super.translate();

        List<WrsExtrinsicObjectType> eoDataLayers = translateDataLayer();

        for (int i = 0; i < eoDataLayers.size(); i++) {
            JAXBElement<WrsExtrinsicObjectType> eoDateLayerEl = OFactory.wrs.createExtrinsicObject(eoDataLayers.get(i));
            String id = eoProduct.getId() + ":DataLayer:" + i;
            eoDateLayerEl.getValue().setId(id);
            eoDateLayerEl.getValue().setLid(id);
            regObjList.getIdentifiable().add(eoDateLayerEl);
            associateToPackage(eoDateLayerEl.getValue());

            AssociationType asso = RIMUtil.createAssociation(id + ":asso", EOPConstants.A_HAS_PRODUCT_INFORMATION, eoProduct.getId(), id);
            JAXBElement<AssociationType> assoEl = OFactory.rim.createAssociation(asso);
            regObjList.getIdentifiable().add(assoEl);
            associateToPackage(asso);
        }

        return regObjList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JAXBElement<EarthObservationType> getEarthObservationJaxbEl() {
        return OFactory.eo_atm.createEarthObservation(eo);
    }

    /**
     * Translate the DataLayer ExtrinsicObjects.
     * @return DataLayer ExtrinsicObjects.
     */
    protected List<WrsExtrinsicObjectType> translateDataLayer() {
        List<WrsExtrinsicObjectType> es = new ArrayList<WrsExtrinsicObjectType>();

        if (eo.isSetResultOf()) {
            if (eo.getResultOf().isSetObject()) {
                JAXBElement el = eo.getResultOf().getObject();

                if (el.getValue() instanceof EarthObservationResultType) {
                    EarthObservationResultType result = (EarthObservationResultType) el.getValue();

                    if (result.isSetDataLayers() && result.getDataLayers().isSetDataLayer()) {
                        List<DataLayerType> layers = result.getDataLayers().getDataLayer();

                        for (DataLayerType layer : layers) {
                            WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
                            e.setObjectType(EOPConstants.E_DATA_LAYER);

                            if (layer.isSetSpecy()) {
                                InternationalStringType name = RIMUtil.createString(layer.getSpecy());
                                e.setName(name);
                            }

                            if (layer.isSetUnit()) {
                                SlotType slotUnit = RIMUtil.createSlot(EOPConstants.S_UNIT, EOPConstants.T_STRING, layer.getUnit());
                                e.getSlot().add(slotUnit);
                            }

                            if (layer.isSetHighestLocation()) {
                                SlotType slotHighLoc = RIMUtil.createSlot(EOPConstants.S_HIGHEST_LOCATION, EOPConstants.T_DOUBLE, String.valueOf(layer.getHighestLocation().getValue()));
                                e.getSlot().add(slotHighLoc);
                            }

                            if (layer.isSetLowestLocation()) {
                                SlotType slotLowLoc = RIMUtil.createSlot(EOPConstants.S_LOWEST_LOCATION, EOPConstants.T_DOUBLE, String.valueOf(layer.getLowestLocation().getValue()));
                                e.getSlot().add(slotLowLoc);
                            }

                            if (layer.isSetAlgorithmName()) {
                                SlotType slotAlgName = RIMUtil.createWrsSlot(EOPConstants.S_ALGORITHM_NAME, EOPConstants.T_STRING, layer.getAlgorithmName());
                                e.getSlot().add(slotAlgName);
                            }

                            if (layer.isSetAlgorithmVersion()) {
                                SlotType slotAlgVer = RIMUtil.createWrsSlot(EOPConstants.S_ALGORITHm_VERSION, EOPConstants.T_STRING, layer.getAlgorithmVersion());
                                e.getSlot().add(slotAlgVer);
                            }

                            es.add(e);
                        }
                    }
                }
            }
        }

        return es;
    }
}
