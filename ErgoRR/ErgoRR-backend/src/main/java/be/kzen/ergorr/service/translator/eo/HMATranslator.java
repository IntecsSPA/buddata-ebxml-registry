/*
 * Project: Buddata ebXML RegRep
 * Class: HMATranslator.java
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

import be.kzen.ergorr.commons.IDGenerator;
import be.kzen.ergorr.service.translator.*;
import be.kzen.ergorr.model.eo.hma.AcquisitionType;
import be.kzen.ergorr.model.eo.hma.ArchivingInformationType;
import be.kzen.ergorr.model.eo.hma.BrowseInformationType;
import be.kzen.ergorr.model.eo.hma.DownlinkInformationType;
import be.kzen.ergorr.model.eo.hma.EarthObservationEquipmentType;
import be.kzen.ergorr.model.eo.hma.EarthObservationMetaDataType;
import be.kzen.ergorr.model.eo.hma.EarthObservationResultType;
import be.kzen.ergorr.model.eo.hma.EarthObservationType;
import be.kzen.ergorr.model.eo.hma.FootprintType;
import be.kzen.ergorr.model.eo.hma.InstrumentType;
import be.kzen.ergorr.model.eo.hma.MaskInformationType;
import be.kzen.ergorr.model.eo.hma.PlatformType;
import be.kzen.ergorr.model.eo.hma.ProcessingInformationType;
import be.kzen.ergorr.model.eo.hma.ProductInformationType;
import be.kzen.ergorr.model.eo.hma.SensorType;
import be.kzen.ergorr.model.eo.hma.SpecificInformationType;
import be.kzen.ergorr.model.gml.AbstractSurfaceType;
import be.kzen.ergorr.model.gml.AbstractTimePrimitiveType;
import be.kzen.ergorr.model.gml.MetaDataPropertyType;
import be.kzen.ergorr.model.gml.PointType;
import be.kzen.ergorr.model.gml.SurfaceArrayPropertyType;
import be.kzen.ergorr.model.gml.SurfacePropertyType;
import be.kzen.ergorr.model.gml.TimePeriodType;
import be.kzen.ergorr.model.rim.AssociationType;
import be.kzen.ergorr.model.rim.ExternalIdentifierType;
import be.kzen.ergorr.model.rim.InternationalStringType;
import be.kzen.ergorr.model.rim.RegistryObjectListType;
import be.kzen.ergorr.model.rim.SlotType;
import be.kzen.ergorr.model.util.OFactory;
import be.kzen.ergorr.model.wrs.WrsExtrinsicObjectType;
import be.kzen.ergorr.commons.EOPConstants;
import be.kzen.ergorr.commons.InternalConstants;
import be.kzen.ergorr.commons.RIMUtil;
import be.kzen.ergorr.model.rim.ClassificationType;
import be.kzen.ergorr.model.util.JAXBUtil;
import com.sun.xml.ws.util.ByteArrayDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Yaman Ustuntas
 */
public class HMATranslator implements Translator {

    private static Logger logger = Logger.getLogger(HMATranslator.class.getName());
    private EarthObservationType eo;
    protected RegistryObjectListType regObjList;
    protected WrsExtrinsicObjectType eoProduct;
    private static final String CLASSIFICATION = "urn:x-ogc:specification:csw-ebrim:EO:EOProductTypes:EOP";
    public static final String CLASSIFICATION_SCHEME = "urn:x-ogc:specification:csw-ebrim:EO:EOProductTypes";

    public HMATranslator(EarthObservationType eo) {
        this.eo = eo;
        regObjList = new RegistryObjectListType();
    }

    public String getClassification() {
        return CLASSIFICATION;
    }

    public RegistryObjectListType translate() {
        String id = "";
        AssociationType asso;

        JAXBElement<WrsExtrinsicObjectType> eProduct = OFactory.wrs.createExtrinsicObject(translateProduct());
        regObjList.getIdentifiable().add(eProduct);
        id = IDGenerator.generateUuid();
        eProduct.getValue().setId(id);
        eProduct.getValue().setLid(id);
        eoProduct = eProduct.getValue();

        try {
            byte[] dataBuf = JAXBUtil.getInstance().marshallToByteArr(eo);
            ByteArrayDataSource dataBufSrc = new ByteArrayDataSource(dataBuf, InternalConstants.CONTENT_TYPE_XML);
            DataHandler dh = new DataHandler(dataBufSrc);
            eoProduct.setRepositoryItem(dh);
        } catch (JAXBException ex) {
            logger.log(Level.SEVERE, "Could not marshall EarthObservation to byte[]", ex);
        }
        ClassificationType classification = new ClassificationType();
        id = IDGenerator.generateUuid();
        classification.setId(id);
        classification.setLid(id);
        classification.setClassificationNode(getClassification());
        classification.setClassificationScheme(CLASSIFICATION_SCHEME);
        classification.setClassifiedObject(eoProduct.getId());
        eoProduct.getClassification().add(classification);

        JAXBElement<WrsExtrinsicObjectType> eAcquisitionPlatform = OFactory.wrs.createExtrinsicObject(translateAcquisitionPlatform());
        regObjList.getIdentifiable().add(eAcquisitionPlatform);
        id = IDGenerator.generateUuid();
        eAcquisitionPlatform.getValue().setId(id);
        eAcquisitionPlatform.getValue().setLid(id);

        asso = RIMUtil.createAssociation(id + "asso", EOPConstants.A_ACQUIRED_BY, eProduct.getValue().getId(), eAcquisitionPlatform.getValue().getId());
        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));

        JAXBElement<WrsExtrinsicObjectType> eProductInfo = OFactory.wrs.createExtrinsicObject(translateProductInformation());
        regObjList.getIdentifiable().add(eProductInfo);
        id = IDGenerator.generateUuid();
        eProductInfo.getValue().setId(id);
        eProductInfo.getValue().setLid(id);

        asso = RIMUtil.createAssociation(id + "asso", EOPConstants.A_HAS_PRODUCT_INFORMATION, eProduct.getValue().getId(), eProductInfo.getValue().getId());
        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));

        JAXBElement<WrsExtrinsicObjectType> eBrowseInfo = OFactory.wrs.createExtrinsicObject(translateBrowseInformation());
        regObjList.getIdentifiable().add(eBrowseInfo);
        id = IDGenerator.generateUuid();
        eBrowseInfo.getValue().setId(id);
        eBrowseInfo.getValue().setLid(id);

        asso = RIMUtil.createAssociation(id + "asso", EOPConstants.A_HAS_BROWSE_INFORMATION, eProduct.getValue().getId(), eBrowseInfo.getValue().getId());
        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));

        JAXBElement<WrsExtrinsicObjectType> eMaskInfo = OFactory.wrs.createExtrinsicObject(translateMaskInformation());
        regObjList.getIdentifiable().add(eMaskInfo);
        id = IDGenerator.generateUuid();
        eMaskInfo.getValue().setId(id);
        eMaskInfo.getValue().setLid(id);

        asso = RIMUtil.createAssociation(id + "asso", EOPConstants.A_HAS_MASK_INFORMATION, eProduct.getValue().getId(), eMaskInfo.getValue().getId());
        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));

        JAXBElement<WrsExtrinsicObjectType> eArchInfo = OFactory.wrs.createExtrinsicObject(translateArchivingInformation());
        regObjList.getIdentifiable().add(eArchInfo);
        id = IDGenerator.generateUuid();
        eArchInfo.getValue().setId(id);
        eArchInfo.getValue().setLid(id);

        asso = RIMUtil.createAssociation(id + "asso", EOPConstants.A_ARCHIVED_IN, eProduct.getValue().getId(), eArchInfo.getValue().getId());
        regObjList.getIdentifiable().add(OFactory.rim.createAssociation(asso));

        return regObjList;
    }

    protected WrsExtrinsicObjectType translateProduct() {
        WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
        e.setObjectType(EOPConstants.E_PRODUCT);

        MetaDataPropertyType metadata = eo.getMetaDataProperty().get(0);

        JAXBElement el = (JAXBElement) metadata.getAny();
        EarthObservationMetaDataType eoMetadata = (EarthObservationMetaDataType) el.getValue();

        ExternalIdentifierType exIdent = RIMUtil.createExternalIdentifier(IDGenerator.generateUuid(), e.getId(), eoMetadata.getIdentifier(), "");
        e.getExternalIdentifier().add(exIdent);

        if (eoMetadata.isSetDoi()) {
            SlotType slotDoi = RIMUtil.createSlot(EOPConstants.S_DOI, EOPConstants.T_STRING, eoMetadata.getDoi());
            e.getSlot().add(slotDoi);
        }

        if (eoMetadata.isSetParentIdentifier()) {
            SlotType slotParentIdent = RIMUtil.createSlot(EOPConstants.S_PARENT_IDENTIFIER, EOPConstants.T_STRING, eoMetadata.getParentIdentifier());
            e.getSlot().add(slotParentIdent);
        }

        if (eoMetadata.isSetProductType()) {
            SlotType slotProdType = RIMUtil.createSlot(EOPConstants.S_PRODUCT_TYPE, EOPConstants.T_STRING, eoMetadata.getProductType());
            e.getSlot().add(slotProdType);
        }

        if (eoMetadata.isSetStatus()) {
            SlotType slotStatus = RIMUtil.createSlot(EOPConstants.S_STATUS, EOPConstants.T_STRING, eoMetadata.getStatus());
            e.getSlot().add(slotStatus);
        }

        if (eoMetadata.isSetVendorSpecific()) {
            List<SpecificInformationType> specificInfos = eoMetadata.getVendorSpecific().getSpecificInformation();

            List<String> localAttrList = new ArrayList<String>();
            List<String> localValList = new ArrayList<String>();
            for (SpecificInformationType specificInfo : specificInfos) {
                localAttrList.add(specificInfo.getLocalAttribute());
                localValList.add(specificInfo.getLocalValue());
            }

            if (!localAttrList.isEmpty()) {
                SlotType slotLocalAttr = RIMUtil.createSlot(EOPConstants.S_LOCAL_ATTRIBUTE, EOPConstants.T_STRING, localAttrList);
                e.getSlot().add(slotLocalAttr);
            }
            if (!localValList.isEmpty()) {
                SlotType slotLocalVal = RIMUtil.createSlot(EOPConstants.S_LOCAL_VALUE, EOPConstants.T_STRING, localValList);
                e.getSlot().add(slotLocalVal);
            }
        }

        if (eo.isSetTarget() && eo.getTarget().isSetFeature()) {
            FootprintType footprint = (FootprintType) eo.getTarget().getFeature().getValue();

            JAXBElement<? extends AbstractSurfaceType> absSur = null;
            if (footprint.isSetMultiExtentOf()) {
                if (footprint.getMultiExtentOf().isSetMultiSurface()) {
                    if (footprint.getMultiExtentOf().getMultiSurface().isSetSurfaceMember()) {
                        List<SurfacePropertyType> surProp = footprint.getMultiExtentOf().getMultiSurface().getSurfaceMember();
                        if (!surProp.isEmpty()) {
                            absSur = surProp.get(0).getSurface();

                            if (!absSur.getValue().isSetSrsName() && footprint.getMultiExtentOf().getMultiSurface().isSetSrsName()) {
                                absSur.getValue().setSrsName(footprint.getMultiExtentOf().getMultiSurface().getSrsName());
                            }
                        }
                    } else if (footprint.getMultiExtentOf().getMultiSurface().isSetSurfaceMembers()) {
                        SurfaceArrayPropertyType surArrayProp = footprint.getMultiExtentOf().getMultiSurface().getSurfaceMembers();
                        List<JAXBElement<? extends AbstractSurfaceType>> asTypes = surArrayProp.getSurface();
                        if (!asTypes.isEmpty()) {
                            absSur = asTypes.get(0);

                            if (!absSur.getValue().isSetSrsName() && footprint.getMultiExtentOf().getMultiSurface().isSetSrsName()) {
                                absSur.getValue().setSrsName(footprint.getMultiExtentOf().getMultiSurface().getSrsName());
                            }
                        }
                    }
                }
            }

            if (absSur != null) {
                absSur.getValue().setId("ID_" + IDGenerator.generateIntString());
                SlotType slotMultiExtent = RIMUtil.createWrsSlot(EOPConstants.S_MULTI_EXTENT_OF, EOPConstants.T_GEOMETRY, absSur);
                e.getSlot().add(slotMultiExtent);
            }

            if (footprint.isSetCenterOf() && footprint.getCenterOf().isSetPoint()) {
                JAXBElement<PointType> point = OFactory.gml.createPoint(footprint.getCenterOf().getPoint());

                SlotType slotCenterOf = RIMUtil.createWrsSlot(EOPConstants.S_CENTER_OF, EOPConstants.T_GEOMETRY, point);
                e.getSlot().add(slotCenterOf);
            }
        }

        if (eo.isSetValidTime() && eo.getValidTime().isSetTimePrimitive()) {
            AbstractTimePrimitiveType absTime = eo.getValidTime().getTimePrimitive().getValue();

            if (absTime instanceof TimePeriodType) {
                TimePeriodType timePeriod = (TimePeriodType) absTime;
                List<String> timeBeginPositionVals = timePeriod.getBeginPosition().getValue();

                if (!timeBeginPositionVals.isEmpty()) {
                    try {
                        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(timeBeginPositionVals.get(0));
                        SlotType slotBeginPos = RIMUtil.createWrsSlot(EOPConstants.S_BEGIN_POSITION, EOPConstants.T_DATETIME, cal);
                        e.getSlot().add(slotBeginPos);
                    } catch (DatatypeConfigurationException ex) {
                        logger.log(Level.WARNING, "Invalid date at element beginPosition", ex);
                    }
                }

                List<String> timeEndPositionVals = timePeriod.getEndPosition().getValue();

                if (!timeEndPositionVals.isEmpty()) {
                    try {
                        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(timeEndPositionVals.get(0));
                        SlotType slotEndPos = RIMUtil.createWrsSlot(EOPConstants.S_END_POSITION, EOPConstants.T_DATETIME, cal);
                        e.getSlot().add(slotEndPos);
                    } catch (DatatypeConfigurationException ex) {
                        logger.log(Level.WARNING, "Invalid date at element endPosition", ex);
                    }
                }
            }
        }

        if (eoMetadata.isSetDownlinkedTo()) {
            List<DownlinkInformationType> downInfos = eoMetadata.getDownlinkedTo().getDownlinkInformation();

            List<String> downInfoStationList = new ArrayList<String>();
            List<XMLGregorianCalendar> downInfoDateList = new ArrayList<XMLGregorianCalendar>();
            for (DownlinkInformationType downInfo : downInfos) {

                if (downInfo.isSetAcquisitionStation()) {
                    List<String> acquisitionStations = downInfo.getAcquisitionStation().getValue();
                    String stations = "";
                    for (String station : acquisitionStations) {
                        stations += station + " ";
                    }

                    stations = stations.substring(0, stations.length() - 1);
                    downInfoStationList.add(stations);
                }
                if (downInfo.isSetAcquisitionDate()) {
                    downInfoDateList.add(downInfo.getAcquisitionDate());
                }
            }

            SlotType slotDownInfoStations = RIMUtil.createSlot(EOPConstants.S_ACQUISITION_STATION, EOPConstants.T_STRING, downInfoStationList);
            e.getSlot().add(slotDownInfoStations);

            if (!downInfoDateList.isEmpty()) {
                SlotType slotDownInfoDate = RIMUtil.createDateSlots(EOPConstants.S_ACQUISITION_DATE, EOPConstants.T_DATETIME, downInfoDateList);
                e.getSlot().add(slotDownInfoDate);
            }
        }

        if (eo.isSetUsing()) {

            if (eo.getUsing().isSetFeature() && eo.getUsing().getFeature().getValue() instanceof EarthObservationEquipmentType) {
                EarthObservationEquipmentType eoEquip = (EarthObservationEquipmentType) eo.getUsing().getFeature().getValue();

                if (eoEquip.isSetAcquisitionParameters()) {
                    if (eoEquip.getAcquisitionParameters().isSetAcquisition()) {
                        AcquisitionType acq = eoEquip.getAcquisitionParameters().getAcquisition().getValue();
                        if (acq.isSetOrbitNumber()) {
                            SlotType slotOrbitNum = RIMUtil.createWrsSlot(EOPConstants.S_ORBIT_NUMBER, EOPConstants.T_INT, acq.getOrbitNumber());
                            e.getSlot().add(slotOrbitNum);
                        }
                        if (acq.isSetLastOrbitNumber()) {
                            SlotType slotLastOrbitNum = RIMUtil.createWrsSlot(EOPConstants.S_LAST_ORBIT_NUMBER, EOPConstants.T_INT, acq.getLastOrbitNumber());
                            e.getSlot().add(slotLastOrbitNum);
                        }
                        if (acq.isSetOrbitDirection()) {
                            SlotType slotOrbitDirection = RIMUtil.createSlot(EOPConstants.S_ORBIT_DIRECTION, EOPConstants.T_STRING, acq.getOrbitDirection());
                            e.getSlot().add(slotOrbitDirection);
                        }
                        if (acq.isSetAcrossTrackIncidenceAngle()) {
                            SlotType slotAcrossPointingAngle = RIMUtil.createWrsSlot(EOPConstants.S_ACROSS_TRACK_POINTING_ANGLE, EOPConstants.T_DOUBLE, acq.getAcrossTrackIncidenceAngle().getValue());
                            e.getSlot().add(slotAcrossPointingAngle);
                        }
                        if (acq.isSetAlongTrackIncidenceAngle()) {
                            SlotType slotAlongPointingAngle = RIMUtil.createWrsSlot(EOPConstants.S_ALONG_TRACK_POINTING_ANGLE, EOPConstants.T_DOUBLE, acq.getAlongTrackIncidenceAngle().getValue());
                            e.getSlot().add(slotAlongPointingAngle);
                        }
                        if (acq.isSetPitch()) {
                            SlotType slotPitch = RIMUtil.createWrsSlot(EOPConstants.S_PITCH, EOPConstants.T_DOUBLE, acq.getPitch().getValue());
                            e.getSlot().add(slotPitch);
                        }
                        if (acq.isSetYaw()) {
                            SlotType slotYaw = RIMUtil.createWrsSlot(EOPConstants.S_YAW, EOPConstants.T_DOUBLE, acq.getYaw().getValue());
                            e.getSlot().add(slotYaw);
                        }
                        if (acq.isSetRoll()) {
                            SlotType slotRoll = RIMUtil.createWrsSlot(EOPConstants.S_ROLL, EOPConstants.T_DOUBLE, acq.getRoll().getValue());
                            e.getSlot().add(slotRoll);
                        }
                        if (acq.isSetAscendingNodeDate()) {
                            SlotType slotAscNodeDate = RIMUtil.createWrsSlot(EOPConstants.S_ASCEDING_NODE_DATE, EOPConstants.T_DATETIME, acq.getAscendingNodeDate());
                            e.getSlot().add(slotAscNodeDate);
                        }
                        if (acq.isSetStartTimeFromAscendingNode()) {
                            SlotType slotStartTimeAscNodeDate = RIMUtil.createWrsSlot(EOPConstants.S_START_TIME_FROM_ASCEDING_NODE, EOPConstants.T_DOUBLE, acq.getStartTimeFromAscendingNode().getValue());
                            e.getSlot().add(slotStartTimeAscNodeDate);
                        }
                        if (acq.isSetCompletionTimeFromAscendingNode()) {
                            SlotType slotCompTimeAscNode = RIMUtil.createWrsSlot(EOPConstants.S_COMPLETION_TIME_FROM_ASCEDING_NODE, EOPConstants.T_DOUBLE, acq.getCompletionTimeFromAscendingNode().getValue());
                            e.getSlot().add(slotCompTimeAscNode);
                        }
                        if (acq.isSetAscendingNodeLongitude()) {
                            SlotType slotAscNodeLongitude = RIMUtil.createWrsSlot(EOPConstants.S_ASCEDING_NODE_LONGITUDE, EOPConstants.T_DOUBLE, acq.getAscendingNodeLongitude().getValue());
                            e.getSlot().add(slotAscNodeLongitude);
                        }
                        if (acq.isSetOrbitDuration()) {
                            SlotType slotOrbitDuration = RIMUtil.createWrsSlot(EOPConstants.S_ORBIT_DURATION, EOPConstants.T_DOUBLE, acq.getOrbitDuration().getValue());
                            e.getSlot().add(slotOrbitDuration);
                        }
                        if (acq.isSetIncidenceAngle()) {
                            SlotType slotIncidenceAngle = RIMUtil.createWrsSlot(EOPConstants.S_INCIDENCE_ANGLE, EOPConstants.T_DOUBLE, acq.getIncidenceAngle().getValue());
                            e.getSlot().add(slotIncidenceAngle);
                        }
                    }
                }
            }
        }

        if (eoMetadata.isSetImageQualityDegradation()) {
            SlotType slotImgQualityDeg = RIMUtil.createWrsSlot(EOPConstants.S_IMAGE_QUALITY_DEGRADATION, EOPConstants.T_DOUBLE, eoMetadata.getImageQualityDegradation().getValue());
            e.getSlot().add(slotImgQualityDeg);
        }
        if (eoMetadata.isSetImageQualityDegradationQuotationMode()) {
            SlotType slotImgQualityDegQMode = RIMUtil.createWrsSlot(EOPConstants.S_IMAGE_QUALITY_DEGRADATION_QUOTATION_MODE, EOPConstants.T_STRING, eoMetadata.getImageQualityDegradationQuotationMode());
            e.getSlot().add(slotImgQualityDegQMode);
        }

        if (eoMetadata.isSetProcessing()) {
            if (eoMetadata.getProcessing().isSetProcessingInformation()) {
                ProcessingInformationType processingInfoType = eoMetadata.getProcessing().getProcessingInformation();
                if (processingInfoType.isSetCompositeType()) {
                    SlotType slotCompsiteType = RIMUtil.createSlot(EOPConstants.S_COMPOSITE_TYPE, EOPConstants.T_STRING, processingInfoType.getCompositeType());
                    e.getSlot().add(slotCompsiteType);
                }
                if (processingInfoType.isSetMethod()) {
                    SlotType slotMethod = RIMUtil.createSlot(EOPConstants.S_METHOD, EOPConstants.T_STRING, processingInfoType.getMethod());
                    e.getSlot().add(slotMethod);
                }
                if (processingInfoType.isSetMethodVersion()) {
                    SlotType slotMethodVersion = RIMUtil.createSlot(EOPConstants.S_METHOD_VERSION, EOPConstants.T_STRING, processingInfoType.getMethodVersion());
                    e.getSlot().add(slotMethodVersion);
                }
                if (processingInfoType.isSetProcessorName()) {
                    SlotType slotProcessorName = RIMUtil.createSlot(EOPConstants.S_PROCESSOR_NAME, EOPConstants.T_STRING, processingInfoType.getProcessorName());
                    e.getSlot().add(slotProcessorName);
                }
                if (processingInfoType.isSetProcessorVersion()) {
                    SlotType slotProcessorVersion = RIMUtil.createSlot(EOPConstants.S_PROCESSOR_VERSION, EOPConstants.T_STRING, processingInfoType.getProcessorVersion());
                    e.getSlot().add(slotProcessorVersion);
                }
                if (processingInfoType.isSetProcessingLevel()) {
                    SlotType slotProcessingLevel = RIMUtil.createSlot(EOPConstants.S_PROCESSING_LEVEL, EOPConstants.T_STRING, processingInfoType.getProcessingLevel());
                    e.getSlot().add(slotProcessingLevel);
                }
                if (processingInfoType.isSetNativeProductFormat()) {
                    SlotType slotNativeFormat = RIMUtil.createSlot(EOPConstants.S_NATIVE_PRODUCT_FORMAT, EOPConstants.T_STRING, processingInfoType.getNativeProductFormat());
                    e.getSlot().add(slotNativeFormat);
                }
            }
        }

        return e;
    }

    protected WrsExtrinsicObjectType translateAcquisitionPlatform() {
        WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
        e.setObjectType(EOPConstants.E_ACQUISITION_PLATFORM);

        if (eo.isSetUsing() && eo.getUsing().isSetFeature() &&
                eo.getUsing().getFeature().getValue() instanceof EarthObservationEquipmentType) {

            EarthObservationEquipmentType equipment = (EarthObservationEquipmentType) eo.getUsing().getFeature().getValue();

            if (equipment.isSetPlatform() && equipment.getPlatform().isSetPlatform()) {
                PlatformType platform = equipment.getPlatform().getPlatform();

                if (platform.isSetShortName()) {
                    InternationalStringType name = RIMUtil.createString(platform.getShortName());
                    e.setName(name);
                }

                if (platform.isSetOrbitType()) {
                    SlotType slotOrbitType = RIMUtil.createSlot(EOPConstants.S_ORBIT_TYPE, EOPConstants.T_STRING, platform.getOrbitType());
                    e.getSlot().add(slotOrbitType);
                }
            }

            if (equipment.isSetInstrument() && equipment.getInstrument().isSetInstrument()) {
                InstrumentType instrument = equipment.getInstrument().getInstrument();

                if (instrument.isSetShortName()) {
                    SlotType slotInstrumentName = RIMUtil.createSlot(EOPConstants.S_INSTRUMENT_SHORT_NAME, EOPConstants.T_STRING, instrument.getShortName());
                    e.getSlot().add(slotInstrumentName);
                }
            }

            if (equipment.isSetSensor() && equipment.getSensor().isSetSensor()) {
                SensorType sensor = equipment.getSensor().getSensor();

                if (sensor.isSetSensorType()) {
                    SlotType slotSensorType = RIMUtil.createSlot(EOPConstants.S_SENSOR_TYPE, EOPConstants.T_STRING, sensor.getSensorType().value());
                    e.getSlot().add(slotSensorType);
                }

                if (sensor.isSetOperationalMode()) {
                    // TODO: OperationalMode/value is a string list, how to map it to a string?
                    SlotType slotOpMode = RIMUtil.createSlot(EOPConstants.S_OPERATIONAL_MODE, EOPConstants.T_STRING, sensor.getOperationalMode().getValue().get(0).toString());
                    e.getSlot().add(slotOpMode);
                }

                if (sensor.isSetResolution()) {
                    SlotType slotResolution = RIMUtil.createWrsSlot(EOPConstants.S_RESOLUTION, EOPConstants.T_DOUBLE, sensor.getResolution().getValue());
                    e.getSlot().add(slotResolution);
                }

                if (sensor.isSetSwathIdentifier()) {
                    // TODO: SwathIdentifier/value is a string list, how to map it to a string?
                    SlotType slotSwathIdentifier = RIMUtil.createSlot(EOPConstants.S_SWATH_IDENTIFIER, EOPConstants.T_STRING, sensor.getSwathIdentifier().getValue().get(0));
                    e.getSlot().add(slotSwathIdentifier);
                }
            }
        }
        return e;
    }

    protected WrsExtrinsicObjectType translateProductInformation() {
        WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
        e.setObjectType(EOPConstants.E_PRODUCT_INFORMATION);

        if (eo.isSetResultOf()) {
            if (eo.getResultOf().isSetObject()) {
                JAXBElement el = eo.getResultOf().getObject();

                if (el.getValue() instanceof EarthObservationResultType) {
                    EarthObservationResultType result = (EarthObservationResultType) el.getValue();

                    if (result.isSetProduct() && result.getProduct().isSetProductInformation()) {
                        List<ProductInformationType> infos = result.getProduct().getProductInformation();

                        if (!infos.isEmpty()) {
                            ProductInformationType info = infos.get(0);

                            if (info.isSetReferenceSystemIdentifier()) {
                                SlotType slotRefSustemIdentifier = RIMUtil.createSlot(EOPConstants.S_REFERENCE_SYSTEM_IDENTIFIER, EOPConstants.T_STRING, info.getReferenceSystemIdentifier().getValue());
                                e.getSlot().add(slotRefSustemIdentifier);
                            }

                            if (info.isSetSize() && info.getSize().isSetValue() && info.getSize().getValue().size() > 0) {

                                // TODO: how to map MeasureType to int?
                                SlotType slotSize = RIMUtil.createWrsSlot(EOPConstants.S_SIZE, EOPConstants.T_INT, info.getSize().getValue().get(0));
                                e.getSlot().add(slotSize);
                            }

                            if (info.isSetVersion()) {
                                SlotType slotVersion = RIMUtil.createSlot(EOPConstants.S_VERSION, EOPConstants.T_STRING, info.getVersion());
                                e.getSlot().add(slotVersion);
                            }

                            if (info.isSetFileName()) {
                                SlotType slotFileName = RIMUtil.createSlot(EOPConstants.S_FILE_NAME, EOPConstants.T_STRING, info.getFileName());
                                e.getSlot().add(slotFileName);
                            }
                        }
                    }
                }
            }
        }
        return e;
    }

    protected WrsExtrinsicObjectType translateBrowseInformation() {
        WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
        e.setObjectType(EOPConstants.E_BROWSE_INFORMATION);

        if (eo.isSetResultOf()) {
            if (eo.getResultOf().isSetObject()) {
                JAXBElement el = eo.getResultOf().getObject();

                if (el.getValue() instanceof EarthObservationResultType) {
                    EarthObservationResultType result = (EarthObservationResultType) el.getValue();

                    if (result.getBrowse().isSetBrowseInformation()) {
                        List<BrowseInformationType> browseInfos = result.getBrowse().getBrowseInformation();

                        if (!browseInfos.isEmpty()) {
                            BrowseInformationType browseInfo = browseInfos.get(0);

                            if (browseInfo.isSetType()) {
                                InternationalStringType name = RIMUtil.createString(browseInfo.getType());
                                e.setName(name);
                            }

                            if (browseInfo.isSetSubType()) {
                                // TODO: SubType/value is a string list, how to map it to a string?
                                SlotType slotSubType = RIMUtil.createSlot(EOPConstants.S_SUB_TYPE, EOPConstants.T_STRING, browseInfo.getSubType().getValue());
                                e.getSlot().add(slotSubType);
                            }

                            if (browseInfo.isSetReferenceSystemIdentifier()) {
                                SlotType slotRefSystemIdentifier = RIMUtil.createSlot(EOPConstants.S_REFERENCE_SYSTEM_IDENTIFIER, EOPConstants.T_STRING, browseInfo.getReferenceSystemIdentifier().getValue());
                                e.getSlot().add(slotRefSystemIdentifier);
                            }

                            if (browseInfo.isSetType()) {
                                SlotType slotFileName = RIMUtil.createSlot(EOPConstants.S_FILE_NAME, EOPConstants.T_STRING, browseInfo.getFileName());
                                e.getSlot().add(slotFileName);
                            }
                        }
                    }
                }

            }
        }

        InternationalStringType name = RIMUtil.createString("");

        return e;
    }

    protected WrsExtrinsicObjectType translateMaskInformation() {
        WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
        e.setObjectType(EOPConstants.E_MASK_INFORMATION);

        if (eo.isSetResultOf()) {
            if (eo.getResultOf().isSetObject()) {
                JAXBElement el = eo.getResultOf().getObject();

                if (el.getValue() instanceof EarthObservationResultType) {
                    EarthObservationResultType result = (EarthObservationResultType) el.getValue();

                    if (result.isSetMask() && result.getMask().isSetMaskInformation()) {
                        List<MaskInformationType> infos = result.getMask().getMaskInformation();

                        if (!infos.isEmpty()) {
                            MaskInformationType info = infos.get(0);

                            if (info.isSetType()) {
                                InternationalStringType name = RIMUtil.createString(info.getType());
                                e.setName(name);
                            }

                            if (info.isSetFormat()) {
                                SlotType slotFormat = RIMUtil.createSlot(EOPConstants.S_FORMAT, EOPConstants.T_STRING, info.getFormat());
                                e.getSlot().add(slotFormat);
                            }

                            if (info.isSetReferenceSystemIdentifier()) {
                                SlotType slotRefSystemIdentifier = RIMUtil.createSlot(EOPConstants.S_REFERENCE_SYSTEM_IDENTIFIER, EOPConstants.T_STRING, info.getReferenceSystemIdentifier().getValue());
                                e.getSlot().add(slotRefSystemIdentifier);
                            }

                            if (info.isSetType()) {
                                SlotType slotFileName = RIMUtil.createSlot(EOPConstants.S_FILE_NAME, EOPConstants.T_STRING, info.getFileName());
                                e.getSlot().add(slotFileName);
                            }
                        }
                    }
                }
            }
        }

        return e;
    }

    protected WrsExtrinsicObjectType translateArchivingInformation() {
        WrsExtrinsicObjectType e = new WrsExtrinsicObjectType();
        e.setObjectType(EOPConstants.E_ARCHIVING_INFORMATION);

        MetaDataPropertyType metadata = eo.getMetaDataProperty().get(0);

        if (metadata.getAny() instanceof JAXBElement) {
            JAXBElement el = (JAXBElement) metadata.getAny();

            if (el.getValue() instanceof EarthObservationMetaDataType) {
                EarthObservationMetaDataType eoMetadata = (EarthObservationMetaDataType) el.getValue();

                if (eoMetadata.isSetArchivedIn()) {
                    List<ArchivingInformationType> archInfos = eoMetadata.getArchivedIn().getArchivingInformation();

                    if (archInfos.size() > 0) {
                        ArchivingInformationType archInfo = archInfos.get(0);

                        if (archInfo.isSetArchivingCenter()) {
                            // TODO: ArchivingCenter/value is a string list, how can I map it to a string?
                            if (archInfo.getArchivingCenter().getValue().size() > 0) {
                                InternationalStringType name = RIMUtil.createString(archInfo.getArchivingCenter().getValue().get(0));
                                e.setName(name);
                            }
                        }

                        if (archInfo.isSetArchivingIdentifier()) {
                            SlotType slotArchIdent = RIMUtil.createSlot(EOPConstants.S_ARCHIVING_IDENTIFIER, EOPConstants.T_STRING, archInfo.getArchivingIdentifier().getValue());
                            e.getSlot().add(slotArchIdent);
                        }

                        if (archInfo.isSetArchivingDate()) {
                            SlotType slotDate = RIMUtil.createWrsSlot(EOPConstants.S_ARCHIVING_DATE, EOPConstants.T_DATETIME, archInfo.getArchivingDate());
                            e.getSlot().add(slotDate);
                        }
                    }
                }
            }
        }
        return e;
    }
}
