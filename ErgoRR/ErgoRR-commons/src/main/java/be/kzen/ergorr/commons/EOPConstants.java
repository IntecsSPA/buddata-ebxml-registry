/*
 * Project: Buddata ebXML RegRep
 * Class: EOPConstants.java
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

/**
 * RIM object types, association types, slot names, slot types and
 * output schemas used by the EOP.
 * 
 * @author Yaman Ustuntas
 */
public class EOPConstants {
    // ============================
    // ExtrinsicObject objectTypes
    // ============================
    public static final String E_PRODUCT = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct";
    public static final String E_ACQUISITION_PLATFORM = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOAcquisitionPlatform";
    public static final String E_PRODUCT_INFORMATION = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProductInformation";
    public static final String E_BROWSE_INFORMATION = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOBrowseInformation";
    public static final String E_MASK_INFORMATION = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOMaskInformation";
    public static final String E_ARCHIVING_INFORMATION = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOArchivingInformation";
    public static final String E_DATA_LAYER = "urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EODataLayer";    
    // ============================
    // Associations
    // ============================
    public static final String A_ACQUIRED_BY = "urn:x-ogc:specification:csw-ebrim:AssociationType:EO:AcquiredBy";
    public static final String A_HAS_PRODUCT_INFORMATION = "urn:x-ogc:specification:csw-ebrim:AssociationType:EO:HasProductInformation";
    public static final String A_HAS_MASK_INFORMATION = "urn:x-ogc:specification:csw-ebrim:AssociationType:EO:HasMaskInformation";
    public static final String A_HAS_BROWSE_INFORMATION = "urn:x-ogc:specification:csw-ebrim:AssociationType:EO:HasBrowseInformation";
    public static final String A_ARCHIVED_IN = "urn:x-ogc:specification:csw-ebrim:AssociationType:EO:ArchivedIn";
    public static final String A_HAS_DATA_LAYER = "urn:x-ogc:specification:csw-ebrim:AssociationType:EO:HasDataLayer";    
    // ============================
    // Slot names
    // ============================
    // Product slots
    public static final String S_DOI = "urn:ogc:def:ebRIM-Slot:OGC-06-131:doi";
    public static final String S_PARENT_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:parentIdentifier";
    public static final String S_PRODUCT_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:productType";
    public static final String S_STATUS = "urn:ogc:def:ebRIM-Slot:OGC-06-131:status";
    public static final String S_LOCAL_ATTRIBUTE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:vendorSpecificAttributes";
    public static final String S_LOCAL_VALUE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:vendorSpecificValues";
    public static final String S_MULTI_EXTENT_OF = "urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf";
    public static final String S_CENTER_OF = "urn:ogc:def:ebRIM-Slot:OGC-06-131:centerOf";
    public static final String S_ACQUISITION_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:acquisitionType";
    public static final String S_ACQUISITION_SUB_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:acquisitionSubType";
    public static final String S_BEGIN_POSITION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:beginPosition";
    public static final String S_END_POSITION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:endPosition";
    public static final String S_ACQUISITION_STATION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:acquisitionStation";
    public static final String S_ACQUISITION_DATE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:acquisitionDate";
    public static final String S_ORBIT_NUMBER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:orbitNumber";
    public static final String S_LAST_ORBIT_NUMBER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:lastOrbitNumber";
    public static final String S_ORBIT_DIRECTION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:orbitDirection";
    public static final String S_ACROSS_TRACK_POINTING_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:acrossTrackPointingAngle";
    public static final String S_ALONG_TRACK_POINTING_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:alongTrackPointingAngle";
    public static final String S_PITCH = "urn:ogc:def:ebRIM-Slot:OGC-06-131:pitch";
    public static final String S_YAW = "urn:ogc:def:ebRIM-Slot:OGC-06-131:yaw";
    public static final String S_ROLL = "urn:ogc:def:ebRIM-Slot:OGC-06-131:roll";
    public static final String S_ASCEDING_NODE_DATE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:ascendingNodeDate";
    public static final String S_START_TIME_FROM_ASCEDING_NODE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:startTimeFromAscendingNode";
    public static final String S_COMPLETION_TIME_FROM_ASCEDING_NODE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:completionTimeFromAscendingNode";
    public static final String S_ASCEDING_NODE_LONGITUDE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:ascendingNodeLongitude";
    public static final String S_ORBIT_DURATION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:orbitDuration";
    public static final String S_INCIDENCE_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:incidenceAngle";
    public static final String S_IMAGE_QUALITY_DEGRADATION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:imageQualityDegradation";
    public static final String S_IMAGE_QUALITY_DEGRADATION_QUOTATION_MODE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:imageQualityDegradationQuotationMode";
    public static final String S_COMPOSITE_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:compositeType";
    public static final String S_METHOD = "urn:ogc:def:ebRIM-Slot:OGC-06-131:method";
    public static final String S_METHOD_VERSION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:methodVersion";
    public static final String S_PROCESSOR_NAME = "urn:ogc:def:ebRIM-Slot:OGC-06-131:processorName";
    public static final String S_PROCESSOR_VERSION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:processorVersion";
    public static final String S_PROCESSING_LEVEL = "urn:ogc:def:ebRIM-Slot:OGC-06-131:processingLevel";
    public static final String S_NATIVE_PRODUCT_FORMAT = "urn:ogc:def:ebRIM-Slot:OGC-06-131:nativeProductFormat";    
    // Acquisition platform slots
    public static final String S_SERIAL_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:platformSerialIdentifier";
    public static final String S_ORBIT_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:platformOrbitType";
    public static final String S_INSTRUMENT_SHORT_NAME = "urn:ogc:def:ebRIM-Slot:OGC-06-131:instrumentShortName";
    public static final String S_SENSOR_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:sensorType";
    public static final String S_OPERATIONAL_MODE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:sensorOperationalMode";
    public static final String S_RESOLUTION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:sensorResolution";
    public static final String S_SWATH_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:swathIdentifier";    
    // Product information slots
    public static final String S_REFERENCE_SYSTEM_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:referenceSystemIdentifier";
    public static final String S_SIZE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:size";
    public static final String S_VERSION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:version";
    public static final String S_FILE_NAME = "urn:ogc:def:ebRIM-Slot:OGC-06-131:fileName";    
    // Browse information slots
    public static final String S_SUB_TYPE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:subType";
//    public static final String S_REFERENCE_SYSTEM_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:referenceSystemIdentifier";
//    public static final String S_FILE_NAME = "urn:ogc:def:ebRIM-Slot:OGC-06-131:fileName";
    // Mask information slots 
    public static final String S_FORMAT = "urn:ogc:def:ebRIM-Slot:OGC-06-131:format";
//    public static final String S_REFERENCE_SYSTEM_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:referenceSystemIdentifier";
//    public static final String S_FILE_NAME = "urn:ogc:def:ebRIM-Slot:OGC-06-131:fileName";
    // Archiving information slots
    public static final String S_ARCHIVING_IDENTIFIER = "urn:ogc:def:ebRIM-Slot:OGC-06-131:archivingIdentifier";
    public static final String S_ARCHIVING_DATE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:archivingDate";    
    // SAR specific slots
    public static final String S_POLARISATION_MODE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:polarisationMode";
    public static final String S_POLARISATION_CHANNEL = "urn:ogc:def:ebRIM-Slot:OGC-06-131:polarisationChannels";
    public static final String S_ANTENNA_LOOK_DIRECTION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:antennaLookDirection";
    public static final String S_MINIMUM_INCIDENCE_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:minimumIncidenceAngle";
    public static final String S_MAXIMUM_INCIDENCE_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:maximumIncidenceAngle";
    public static final String S_DOPPER_FREQUENCY = "urn:ogc:def:ebRIM-Slot:OGC-06-131:dopplerFrequency";
    public static final String S_INCIDENCE_ANGLE_VARIATION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:incidenceAngleVariation";    
    // OPT specific slots
    public static final String S_ILLUMINATION_AZIMUTH_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:illuminationAzimuthAngle";
    public static final String S_ILLUMINATION_ELEVATION_ANGLE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:illuminationElevationAngle";
    public static final String S_COULD_COVER_PERCENTAGE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:cloudCoverPercentage";
    public static final String S_SNOW_COVER_PERCENTAGE = "urn:ogc:def:ebRIM-Slot:OGC-06-131:snowCoverPercentage";    
    // ATM specific Data Layer slots
    public static final String S_UNIT = "urn:ogc:def:ebRIM-Slot:OGC-06-131:unit";
    public static final String S_HIGHEST_LOCATION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:highestLocation";
    public static final String S_LOWEST_LOCATION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:lowestLocation";
    public static final String S_ALGORITHM_NAME = "urn:ogc:def:ebRIM-Slot:OGC-06-131:algorithmName";
    public static final String S_ALGORITHm_VERSION = "urn:ogc:def:ebRIM-Slot:OGC-06-131:algorithmVersion";    
    // ============================
    // Slot types
    // ============================
    public static final String T_STRING = "string";
    public static final String T_GEOMETRY = "geometry";
    public static final String T_DATETIME = "dateTime";
    public static final String T_INT = "int";
    public static final String T_DOUBLE = "double";
}
