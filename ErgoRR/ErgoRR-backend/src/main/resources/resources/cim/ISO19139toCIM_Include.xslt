<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
                xmlns:saxon="http://saxon.sf.net/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:gmd="http://www.isotc211.org/2005/gmd"
                xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
                xmlns:wrs="http://www.opengis.net/cat/wrs/1.0"
                xmlns:gco="http://www.isotc211.org/2005/gco"
                xmlns:gmx="http://www.isotc211.org/2005/gmx"
                xmlns:srv="http://www.isotc211.org/2005/srv"
                xmlns:gml="http://www.opengis.net/gml"
                xmlns:xlink="http://www.w3.org/1999/xlink">
    <!-- <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/> -->

    <xsl:variable name="isoId" select="//*[local-name()='MI_Metadata' or local-name()='MD_Metadata']/gmd:fileIdentifier/gco:CharacterString"/>

    <xsl:variable name="cimIDPrefix">
        <xsl:choose>
            <xsl:when test="contains($isoId, 'urn:')">
                <xsl:value-of select="concat($isoId , ':')"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat( 'urn:i15:' , $isoId , ':')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
		
    <!--	BEGIN I15 Extrinsic Objects -->
    
    <xsl:variable name="cimObjectTypeUrnPrefix">
        <xsl:text>urn:ogc:def:ebRIM-ObjectType:OGC-I15::</xsl:text>			
    </xsl:variable>

    <xsl:variable name="applicationObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'Application' )"/>
    </xsl:variable>

    <xsl:variable name="datasetCollectionObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'DatasetCollection' )"/>
    </xsl:variable>

    <xsl:variable name="elementaryDatasetObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ElementaryDataset' )"/>
    </xsl:variable>

    <xsl:variable name="reMetadataObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ResourceMetadata' )"/>
    </xsl:variable>

    <xsl:variable name="dataMetadataObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'DataMetadata' )"/>
    </xsl:variable>

    <xsl:variable name="serviceMetadataObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ServiceMetadata' )"/>
    </xsl:variable>

    <!-- No longer applicable in i15 -->
    <!-- <xsl:variable name="responsiblePartyObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ResponsibleParty' )"/>
    </xsl:variable> -->

    <xsl:variable name="metadataInformationObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'MetadataInformation' )"/>
    </xsl:variable>
    
    <xsl:variable name="legalConstraintsObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'LegalConstraints' )"/>
    </xsl:variable>

    <xsl:variable name="securityConstraintsObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'SecurityConstraints' )"/>
    </xsl:variable>

    <xsl:variable name="identifiedItemObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'IdentifiedItem' )"/>
    </xsl:variable>

    <xsl:variable name="serviceOperationObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ServiceOperation' )"/>
    </xsl:variable>

    <xsl:variable name="citedItemObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'CitedItem' )"/>
    </xsl:variable>

    <xsl:variable name="referenceSpecificationObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ReferenceSpecification' )"/>
    </xsl:variable>   
    
    <!-- NEW in I15-->
    
    <xsl:variable name="dataQualityObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'DataQuality' )"/>
    </xsl:variable> 
    
      <xsl:variable name="lineageObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'Lineage' )"/>
    </xsl:variable>   
    
     <xsl:variable name="sourceObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'Source' )"/>
    </xsl:variable>   
    
    <xsl:variable name="processStepObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ProcessStep' )"/>
    </xsl:variable> 
    
    <xsl:variable name="acquisitionInformationObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'AcquisitionInformation' )"/>
    </xsl:variable>   
    
     <xsl:variable name="instrumentObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'Instrument' )"/>
    </xsl:variable>  
    
     <xsl:variable name="platformObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'Platform' )"/>
    </xsl:variable>   
    
    <!-- END I15 Extrinsic Object-->

	<!-- BEGIN RIM Object Types  -->
	
    <xsl:variable name="serviceObjectType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Service</xsl:text>
    </xsl:variable>

    <xsl:variable name="organizationObjectType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Organization</xsl:text>
    </xsl:variable>
    
     <xsl:variable name="personObjectType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person</xsl:text>
    </xsl:variable>
    
    <!-- END RIM Object Types  -->

	<!-- BEGIN CSW-ebRIM Basic Extension Package Object Types  -->
	
    <xsl:variable name="datasetObjectType">
        <xsl:text>urn:ogc:def:ebRIM-ObjectType:OGC:Dataset</xsl:text>
    </xsl:variable>

    <xsl:variable name="rightsObjectType">
        <xsl:text>urn:ogc:def:ebRIM-ObjectType:OGC:Rights</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="imageObjectType">
      <xsl:text>urn:ogc:def:ebRIM-ObjectType:OGC:Image</xsl:text>
    </xsl:variable>
    
    <!-- END CSW-ebRIM Basic Extension Package Object Types  -->


    <!--	ClassificationSchemes -->

    <xsl:variable name="cimClassificationSchemeUrnPrefix">
        <xsl:text>urn:ogc:def:ebRIM-ClassificationScheme:OGC-I15::</xsl:text>
    </xsl:variable>
	<!--	CSW-ebRIM Basic Extension Package	-->
    <!-- The urn should be inherited from the Basic Extension package -->
    <xsl:variable name="servicesClassificationScheme">
        <xsl:text>urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services</xsl:text>
    </xsl:variable>
    <xsl:variable name="servicesClassificationSchemePrefix">
        <xsl:text>urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:</xsl:text>
    </xsl:variable>

    <!--	I15 classification schemes -->
    <xsl:variable name="topicCategoryClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'TopicCategory')"/>
    </xsl:variable>
    <xsl:variable name="topicCategoryClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'TopicCategory:')"/>
    </xsl:variable>

    <xsl:variable name="spatialRepresentationClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'SpatialRepresentation')"/>
    </xsl:variable>
    <xsl:variable name="spatialRepresentationClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'SpatialRepresentation:')"/>
    </xsl:variable>

    <xsl:variable name="characterSetClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'CharacterSet')"/>
    </xsl:variable>
    <xsl:variable name="characterSetClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'CharacterSet:')"/>
    </xsl:variable>

	<!-- No longer applicable in I15
    <xsl:variable name="keywordTypeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordType')"/>
    </xsl:variable>
    <xsl:variable name="keywordTypeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordType:')"/>
    </xsl:variable>

    <xsl:variable name="keywordSchemeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordScheme')"/>
    </xsl:variable>
    <xsl:variable name="keywordSchemeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordScheme:')"/>
    </xsl:variable>
	-->

	<!-- NEW in I15 -->
	
 <xsl:variable name="KeywordSchemeDisciplineClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeDiscipline')"/>
    </xsl:variable>
    <xsl:variable name="KeywordSchemeDisciplineClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeDiscipline:')"/>
    </xsl:variable>

	<xsl:variable name="KeywordSchemePlaceClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemePlace')"/>
    </xsl:variable>
    <xsl:variable name="KeywordSchemePlaceClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemePlace:')"/>
    </xsl:variable>
    
    <xsl:variable name="KeywordSchemeStratumClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeStratum')"/>
    </xsl:variable>
    <xsl:variable name="KeywordSchemeStratumClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeStratum:')"/>
    </xsl:variable>
    
    <xsl:variable name="KeywordSchemeTemporalClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeTemporal')"/>
    </xsl:variable>
    <xsl:variable name="KeywordSchemeTemporalClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeTemporal:')"/>
    </xsl:variable>
    
    <xsl:variable name="KeywordSchemeThemeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeTheme')"/>
    </xsl:variable>
    <xsl:variable name="KeywordSchemeThemeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeTheme:')"/>
    </xsl:variable>

	<xsl:variable name="KeywordSchemeUntypedClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeUntyped')"/>
    </xsl:variable>
    <xsl:variable name="KeywordSchemeUntypedClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'KeywordSchemeUntyped:')"/>
    </xsl:variable>
   
<!-- END NEW in I15-->

    <xsl:variable name="restrictionCodeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'RestrictionCode')"/>
    </xsl:variable>
    <xsl:variable name="restrictionCodeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'RestrictionCode:')"/>
    </xsl:variable>

    <xsl:variable name="restrictionTypeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'RestrictionType')"/>
    </xsl:variable>
    <xsl:variable name="restrictionTypeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'RestrictionType:')"/>
    </xsl:variable>
	
    <xsl:variable name="classificationCodeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'ClassificationCode')"/>
    </xsl:variable>
    <xsl:variable name="classificationCodeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'ClassificationCode:')"/>
    </xsl:variable>

    <xsl:variable name="DCPListClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'DCPList')"/>
    </xsl:variable>
    <xsl:variable name="DCPListClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'DCPList:')"/>
    </xsl:variable>

    <xsl:variable name="citedResponsiblePartyClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'CitedResponsibleParty')"/>
    </xsl:variable>
    <xsl:variable name="citedResponsiblePartyClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'CitedResponsibleParty:')"/>
    </xsl:variable>

    <xsl:variable name="couplingTypeClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'CouplingType')"/>
    </xsl:variable>
    <xsl:variable name="couplingTypeClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'CouplingType:')"/>
    </xsl:variable>

    <xsl:variable name="metadataStandardNameAndVersionClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'MetadataStandardNameAndVersion')"/>
    </xsl:variable>
    <xsl:variable name="metadataStandardNameAndVersionClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'MetadataStandardNameAndVersion:')"/>
    </xsl:variable>

    <xsl:variable name="formatNameAndVersionClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'FormatNameAndVersion')"/>
    </xsl:variable>
     <xsl:variable name="formatNameAndVersionClassificationSchemePrefix">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'FormatNameAndVersion:')"/>
    </xsl:variable>

<!-- No longer applicable in I15
    <xsl:variable name="dateTypesClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'DateTypes')"/>
    </xsl:variable>
-->

    <xsl:variable name="objectTypeClassificationScheme">urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationScheme</xsl:variable>
    <xsl:variable name="objectTypeClassificationSchemePrefix">urn:ogc:def:ebRIM-ClassificationScheme:OGC-I15::</xsl:variable>
 


    <!-- Associations -->
    
    <!-- Assocations in CSW-ebRIM Basic Package -->
    <xsl:variable name="graphicOverviewAssociationType">
        <xsl:text>urn:ogc:def:ebRIM-AssociationType:OGC:GraphicOverview</xsl:text>
    </xsl:variable> 
    
    <!-- Associations in CIM -->
    <xsl:variable name="cimAssociationTypeUrnPrefix">
        <xsl:text>urn:ogc:def:ebRIM-AssociationType:OGC-I15::</xsl:text>
    </xsl:variable>


    <xsl:variable name="registryPackageToMetadataInformationAssociationType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember</xsl:text>
    </xsl:variable>

    <xsl:variable name="isClientOfAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'IsClientOf' )"/>
    </xsl:variable>

    <xsl:variable name="accessesAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Accesses' )"/>
    </xsl:variable>

    <xsl:variable name="subsetAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Subset' )"/>
    </xsl:variable>


    <xsl:variable name="datasetDescriptionAssociationType">
        <xsl:value-of select="concat( $cimAssociationTypeUrnPrefix, 'DatasetDescription' )"/>
    </xsl:variable>

    <xsl:variable name="serviceDescriptionAssociationType">
        <xsl:value-of select="concat( $cimAssociationTypeUrnPrefix, 'ServiceDescription' )"/>
    </xsl:variable>
    
<!--  No longer applicable in I15
    <xsl:variable name="applicationDescriptionAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'ApplicationDescription' )"/>
    </xsl:variable>
-->

    <xsl:variable name="resourceMetadataInformationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'ResourceMetadataInformation' )"/>
    </xsl:variable>

    <xsl:variable name="parentMetadataInformationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'ParentMetadataInformation' )"/>
    </xsl:variable>

    <xsl:variable name="resourceReferenceSystemAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'ResourceReferenceSystem' )"/>
    </xsl:variable>

    <xsl:variable name="citedResponsiblePartyAssociationType">
        <xsl:value-of select="concat( $cimAssociationTypeUrnPrefix, 'CitedResponsibleParty' )"/>
    </xsl:variable>

    <xsl:variable name="containsOperationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'ContainsOperation' )"/>
    </xsl:variable>

    <xsl:variable name="resourceConstraintsAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'ResourceConstraints' )"/>
    </xsl:variable>

    <xsl:variable name="codeSpaceAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'CodeSpace' )"/>
    </xsl:variable>

    <xsl:variable name="authorityAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Authority' )"/>
    </xsl:variable>

    <xsl:variable name="operatesOnAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'OperatesOn' )"/>
    </xsl:variable>

    <xsl:variable name="specificationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Specification' )"/>
    </xsl:variable>

    <xsl:variable name="metadataPointOfContactAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'MetadataPointOfContact' )"/>
    </xsl:variable>

    <xsl:variable name="thesaurusAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Thesaurus' )"/>
    </xsl:variable>
    
     
   
   <!-- NEW in I15-->
   
	<xsl:variable name="sourceReferenceSystemAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'SourceReferenceSystem' )"/>
    </xsl:variable>   
    
    <xsl:variable name="dataQualityLineageAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'DataQualityLineage' )"/>
    </xsl:variable>  
      
    <xsl:variable name="lineageSourceAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'LineageSource' )"/>
    </xsl:variable>  
      
    <xsl:variable name="lineageProcessStepAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'LineageProcessStep' )"/>
    </xsl:variable>  
      
    <xsl:variable name="stepSourceAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'StepSource' )"/>
    </xsl:variable>  
      
    <xsl:variable name="sourceCitationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'SourceCitation' )"/>
    </xsl:variable>  
      
    <xsl:variable name="processorAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Processor' )"/>
    </xsl:variable>  
      
    <xsl:variable name="acquiredByAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'AcquiredBy' )"/>
    </xsl:variable>  
    
    <xsl:variable name="acquisitionInstrumentAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'AcquisitionInstrument' )"/>
    </xsl:variable>  
    
    <xsl:variable name="acquisitionPlatformAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'AcquisitionPlatform' )"/>
    </xsl:variable>  
    
    <xsl:variable name="mountedOnAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'MountedOn' )"/>
    </xsl:variable>  
    
    <xsl:variable name="instrumentCitationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'InstrumentCitation' )"/>
    </xsl:variable>  
    
     <xsl:variable name="platformCitationAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'PlatformCitation' )"/>
    </xsl:variable>  
    
     <xsl:variable name="sponsorAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'Sponsor' )"/>
    </xsl:variable>  
    
      <xsl:variable name="dataQualityInfoAssociationType">
        <xsl:value-of select="concat($cimAssociationTypeUrnPrefix, 'DataQualityInfo' )"/>
    </xsl:variable>  
   
    <!--	Slots -->

	<!--Dublin Core Metadata Slots-->
	
	 <!-- Explicetely cited IN I15 -->
    <xsl:variable name="createdSlotName">
        <xsl:text>http://purl.org/dc/terms/created</xsl:text>
    </xsl:variable>

    <xsl:variable name="modifiedSlotName">
        <xsl:text>http://purl.org/dc/terms/modified</xsl:text>
    </xsl:variable>

   <!-- Explicetely cited IN I15 -->
    <xsl:variable name="issuedSlotName">
        <xsl:text>http://purl.org/dc/terms/issued</xsl:text>
    </xsl:variable>

    <xsl:variable name="contributorSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/contributor</xsl:text>
    </xsl:variable>

    <xsl:variable name="spatialSlotName_BP">
        <xsl:text>http://purl.org/dc/terms/spatial</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="creatorSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/creator</xsl:text>
    </xsl:variable>

    <xsl:variable name="dateSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/date</xsl:text>
    </xsl:variable>

    <xsl:variable name="referencesSlotName">
        <xsl:text>http://purl.org/dc/terms/references</xsl:text>
    </xsl:variable>

    <xsl:variable name="languageSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/language</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="rightsSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/rights</xsl:text>
    </xsl:variable>
    
    <!-- Explicetely cited IN I15 -->
    <xsl:variable name="sourceSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/source</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="subjectSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/subject</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="formatSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/format</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="coverageSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/coverage</xsl:text>
    </xsl:variable>
    
     <!-- Explicetely cited IN I15 -->
    <xsl:variable name="conformsToSlotName">
        <xsl:text>http://purl.org/dc/terms/conformsTo</xsl:text>
    </xsl:variable>
    
    <!-- Explicetely cited IN I15 -->
    <xsl:variable name="titleSlotName">
        <xsl:text>http://purl.org/dc/terms/title</xsl:text>
    </xsl:variable>
    
     <!-- Explicetely cited IN I15 -->
    <xsl:variable name="identifierSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/identifier</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="typeBasicSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/type</xsl:text>
    </xsl:variable>
    
     <!-- Explicetely cited IN I15 -->
     <xsl:variable name="typeSlotName">
        <xsl:text>http://purl.org/dc/terms/type</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="abstractSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/abstract</xsl:text>
    </xsl:variable>
    
    <!-- Slot types-->
    
    <xsl:variable name="dateSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:Date</xsl:text>
    </xsl:variable>
     
    <xsl:variable name="dateTimeSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:DateTime</xsl:text>
    </xsl:variable>

    <xsl:variable name="languageSlotType">
        <xsl:text>urn:ogc:def:dataType:RFC-4646:LanguageTag</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="envelopeSlotType">
        <xsl:text>urn:ogc:def:dataType:ISO-19107:GM_Envelope</xsl:text>
    </xsl:variable>
   
    <xsl:variable name="stringSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:String</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="uriSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:URI</xsl:text>
    </xsl:variable>
    
     <xsl:variable name="internationalStringSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:InternationalString</xsl:text>
    </xsl:variable>

	 <xsl:variable name="measureSlotType">
        <xsl:text>urn:ogc:def:dataType:gml:MeasureType</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="integerSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:Integer</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="doubleSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:Double</xsl:text>
    </xsl:variable>


	<!--I15 Slots -->
	<xsl:variable name="cimSlotPrefix">
        <xsl:text>urn:ogc:def:ebRIM-slot:OGC-I15::</xsl:text>
    </xsl:variable>
	
	<xsl:variable name="resolutionSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Resolution' )"/>
    </xsl:variable>

    <xsl:variable name="scaleDenominatorSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'ScaleDenominator' )"/>
    </xsl:variable>

    <!-- Changed starting from version 0.1.12: since this version, the mapping is to 2 temporal slots
    <xsl:variable name="temporalSlotName">
        <xsl:text>http://purl.org/dc/terms/temporal</xsl:text>
    </xsl:variable>
    -->

    <xsl:variable name="temporalBeginSlotName">
       <xsl:value-of select="concat($cimSlotPrefix, 'TemporalBegin' )"/>
    </xsl:variable>

    <xsl:variable name="temporalEndSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'TemporalEnd' )"/>
    </xsl:variable>
    
    <xsl:variable name="lineageSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Lineage' )"/>
    </xsl:variable>
    
    <!-- No longer applicable in I15 
    <xsl:variable name="urlSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::url</xsl:text>
    </xsl:variable>
	-->
	
	<!-- NEW in I15-->
	
	<xsl:variable name="conformanceSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Conformance' )"/>
    </xsl:variable>
	
	<xsl:variable name="envelopeSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Envelope' )"/>
    </xsl:variable>
    
    <xsl:variable name="supplementalInformationSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'SupplementalInformation' )"/>
    </xsl:variable>
    
    <xsl:variable name="explanationSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Explanation' )"/>
    </xsl:variable>
    
    <xsl:variable name="scopeLevelSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'ScopeLevel' )"/>
    </xsl:variable>
    
    <xsl:variable name="scopeDescriptionSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'ScopeDescription' )"/>
    </xsl:variable>
    
    <xsl:variable name="rationaleSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Rationale' )"/>
    </xsl:variable>
    
    <xsl:variable name="spatialSlotName">
        <xsl:value-of select="concat($cimSlotPrefix, 'Envelope' )"/>
    </xsl:variable>
    
	
    <!--ExtrinsicObject ID prefix -->

    <xsl:variable name="urnCimMetadataInformationExtrinsicObjectID" select="concat( $cimIDPrefix , 'ExtrinsicObject:MetadataInformation')"/>

    <!-- No longer applicable in I15
    <xsl:variable name="urnCimParentIdentifierExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ParentIdentifier:')"/>
	-->
		
    <xsl:variable name="urnCimResourceMetadataExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ResourceMetadata:')"/>

    <xsl:variable name="urnCimIdentifiedItemExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:IdentifiedItem:')"/>

    <xsl:variable name="urnCimSecurityConstraintsExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:SecurityConstraints:')"/>
   
    <xsl:variable name="urnCimRightsExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Rights:')"/>
    
    <xsl:variable name="urnCimImageExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Image:')"/>

    <xsl:variable name="urnCimLegalConstraintsExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:LegalConstraints:')"/>
 
    <xsl:variable name="urnCimCitedItemExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:CitedItem:')"/>

    <xsl:variable name="urnCimReferenceSpecificationExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ReferenceSpecification:')"/>
    
     <xsl:variable name="urnCimServiceOperationExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ServiceOperation:')"/>
    
     <xsl:variable name="urnCimDataQualityExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:DataQuality:')"/>
     
    <xsl:variable name="urnCimLineageExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Lineage:')"/>
    
     <xsl:variable name="urnCimProcessStepExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ProcessStep:')"/>
     
      <xsl:variable name="urnCimSourceExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Source:')"/>
      
      <xsl:variable name="urnCimAcquisitionInformationExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:AcquisitionInformation:')"/>
      
       <xsl:variable name="urnCimPlatformExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Platform:')"/>
       
        <xsl:variable name="urnCimInstrumentExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Instrument:')"/>

    <!--Association ID prefix -->
    
   <xsl:variable name="urnCimGraphicOverviewAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:GraphicOverview:')"/>

    <xsl:variable name="urnRegisrtryPackageToMetadataInformationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:HasMember:')"/>

    <xsl:variable name="urnCimMetadataPointOfContactAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:MetadataPointOfContact:')"/>

    <xsl:variable name="urnCimCitedResponsiblePartyAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:CitedResponsibleParty:')"/>

    <xsl:variable name="urnCimCodeSpaceAssociationIDPrefix"  select="concat( $cimIDPrefix , 'Association:CodeSpace:')"/>

    <xsl:variable name="urnCimResourceReferenceSystemAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:ResourceReferenceSystem:')"/>

    <xsl:variable name="urnCimResourceMetadataInformationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:ResourceMetadataInformation:')"/>

    <xsl:variable name="urnCimParentMetadataInformationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:ParentMetadataInformation:')"/>

    <xsl:variable name="urnCimResourceConstraintsAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:ResourceConstraints:')"/>

    <xsl:variable name="urnCimAuthorityAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:Authority:')"/>

    <xsl:variable name="urnCimThesaurusAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:Thesaurus:')"/>
	 
    <xsl:variable name="urnCimContainsOperationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:ContainsOperation:')"/>
	  
    <xsl:variable name="urnCimOperatesOnAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:OperatesOn:')"/>
    
     <xsl:variable name="urnCimSpecificationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:Specification:')"/>
    
     <xsl:variable name="urnCimDataQualityInfoAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:DataQualityInfo:')"/>
	 
	 <xsl:variable name="urnCimDataQualityLineageAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:DataQualityLineage:')"/>
	 
	 <xsl:variable name="urnCimLineageProcessStepAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:LineageProcessStep:')"/>
	 
	 <xsl:variable name="urnCimLineageSourceAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:LineageSource:')"/>
	  
	<xsl:variable name="urnCimStepSourceAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:StepSource:')"/>
	
	<xsl:variable name="urnCimProcessorAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:Processor:')"/>
	   
     <xsl:variable name="urnCimAcquiredByAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:AcquiredBy:')"/>
	    
	<xsl:variable name="urnCimAcquisitionPlatformAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:AcquisitionPlatform:')"/>
	     
	<xsl:variable name="urnCimAcquisitionInstrumentAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:AcquisitionInstrument:')"/>
	
	<xsl:variable name="urnCimPlatformCitationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:PlatformCitation:')"/>
	
	<xsl:variable name="urnCimInstrumentCitationAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:InstrumentCitation:')"/>
	
	<xsl:variable name="urnCimMountedOnAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:MountedOn:')"/>
	
	<xsl:variable name="urnCimSponsorAssociationIDPrefix" select="concat( $cimIDPrefix , 'Association:Sponsor:')"/>
	   

    <!--Classification ID prefix -->

    <xsl:variable name="urnCharacterSetClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:CharacterSet:')"/>

    <xsl:variable name="urnSpatialRepresentationClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:SpatialRepresentation:')"/>

    <xsl:variable name="urnTopicCategoryClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:TopicCategory:')"/>

    <xsl:variable name="urnClassificationCodeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:ClassificationCode:')"/>

    <xsl:variable name="urnCimCitedResponsiblePartyClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:CitedResponsibleParty:')"/>

    <xsl:variable name="urnCimRestrictionTypeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:RestrictionType:')"/>

    <xsl:variable name="urnCimCouplingTypeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:CouplingType:')"/>

    <xsl:variable name="urnCimDCPListClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:DCPList:')"/>

    <xsl:variable name="urnCimRestrictionCodeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:RestrictionCode:')"/>
    
    <xsl:variable name="urnCimFormatNameAndVersionClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:FormatNameAndVersion:')"/>
    
    <xsl:variable name="urnCimMetadataStandardNameAndVersionClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:MetadataStandardNameAndVersion:')"/>

    <xsl:variable name="urnCimServicesClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:Services:')"/>

	<!--  No longer applicable in I15
    <xsl:variable name="urnCimKeywordTypeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordType:')"/>

    <xsl:variable name="urnCimKeywordSchemeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordScheme:')"/>
	-->
	
    <xsl:variable name="objectTypeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:ObjectType:')"/>
    
    <!-- NEW in I15-->
       <xsl:variable name="urnCimKeywordSchemeDisciplineClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordSchemeDiscipline:')"/>
       
       <xsl:variable name="urnCimKeywordSchemePlaceClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordSchemePlace:')"/>
       
       <xsl:variable name="urnCimKeywordSchemeStratumClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordSchemeStratum:')"/>
       
       <xsl:variable name="urnCimKeywordSchemeTemporalClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordSchemeTemporal:')"/>
        
       <xsl:variable name="urnCimKeywordSchemeThemeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordSchemeTheme:')"/>
       
       <xsl:variable name="urnCimKeywordSchemeUntypedClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordSchemeUntyped:')"/>
       
       

    <!-- Others-->

    <xsl:variable name="urnCimOrganizationIDPrefix" select="concat( $cimIDPrefix , 'Organization:')"/>
    
	 <xsl:variable name="urnCimPersonIDPrefix" select="concat( $cimIDPrefix , 'Person:')"/>
 
    <xsl:variable name="urnCimExternalIdentifierIDPrefix" select="concat( $cimIDPrefix , 'ExternalIdentifier:')"/>



</xsl:stylesheet>
