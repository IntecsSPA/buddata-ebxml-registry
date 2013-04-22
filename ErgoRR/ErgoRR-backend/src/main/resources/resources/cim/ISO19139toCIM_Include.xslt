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

    <xsl:variable name="isoId" select="/gmd:MD_Metadata/gmd:fileIdentifier/gco:CharacterString"/>

    <xsl:variable name="cimIDPrefix">
        <xsl:choose>
            <xsl:when test="contains($isoId, 'urn:')">
                <xsl:value-of select="concat($isoId , ':')"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat( 'urn:CIM:' , $isoId , ':')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
		
    <!--	CIM Extrinsic Objects -->

    <xsl:variable name="cimObjectTypeUrnPrefix">
        <xsl:text>urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::</xsl:text>
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

    <xsl:variable name="resourceMetadataObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ResourceMetadata' )"/>
    </xsl:variable>

    <xsl:variable name="dataMetadataObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'DataMetadata' )"/>
    </xsl:variable>

    <xsl:variable name="serviceMetadataObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ServiceMetadata' )"/>
    </xsl:variable>

    <xsl:variable name="responsiblePartyObjectType">
        <xsl:value-of select="concat( $cimObjectTypeUrnPrefix, 'ResponsibleParty' )"/>
    </xsl:variable>

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
    <!-- END CIM Extrinsic Object-->


    <xsl:variable name="serviceObjectType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Service</xsl:text>
    </xsl:variable>

    <xsl:variable name="organizationObjectType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Organization</xsl:text>
    </xsl:variable>

    <xsl:variable name="datasetObjectType">
        <xsl:text>urn:ogc:def:ebRIM-ObjectType:OGC:Dataset</xsl:text>
    </xsl:variable>

    <xsl:variable name="rightsObjectType">
        <xsl:text>urn:ogc:def:ebRIM-ObjectType:OGC:Rights</xsl:text>
    </xsl:variable>


    <!--	ClassificationSchemes -->
        <!--	basic extension package	-->

    <xsl:variable name="cimClassificationSchemeUrnPrefix">
        <xsl:text>urn:ogc:def:classificationScheme:OGC-CSW-ebRIM-CIM::</xsl:text>
    </xsl:variable>

    <!-- The urn should be inherited from the Basic Extension package -->
    <xsl:variable name="servicesClassificationScheme">
        <xsl:text>urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services</xsl:text>
    </xsl:variable>
    <xsl:variable name="servicesClassificationSchemePrefix">
        <xsl:text>urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:</xsl:text>
    </xsl:variable>

    <!--	OGC 07-038 section D.4 CIM classification schemes -->
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

    <xsl:variable name="formatNameAndVersionClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'FormatNameAndVersion')"/>
    </xsl:variable>

    <xsl:variable name="dateTypesClassificationScheme">
        <xsl:value-of select="concat( $cimClassificationSchemeUrnPrefix, 'DateTypes')"/>
    </xsl:variable>

    <xsl:variable name="objectTypeClassificationScheme">urn:oasis:names:tc:ebxml-regrep:classificationScheme:ObjectType</xsl:variable>
    <xsl:variable name="objectTypeClassificationSchemePrefix">urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::</xsl:variable>
    <!-- ClassificationNodes -->

    <xsl:variable name="classificationeNodeIdPrefix">
        <xsl:text>urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::</xsl:text>
    </xsl:variable>




    <!-- Associations -->

    <xsl:variable name="cimAssociationTypeUrnPrefix">
        <xsl:text>urn:ogc:def:associationType:OGC-CSW-ebRIM-CIM::</xsl:text>
    </xsl:variable>


    <xsl:variable name="regisrtryPackageToMetadataInformationAssociationType">
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

<!--  Superseded in OGC CIM v. 0.1.12
    <xsl:variable name="datasetDescriptionAssociationType">
        <xsl:value-of select="concat( $cimAssociationTypeUrnPrefix, 'DatasetDescription' )"/>
    </xsl:variable>

    <xsl:variable name="serviceDescriptionAssociationType">
        <xsl:value-of select="concat( $cimAssociationTypeUrnPrefix, 'ServiceDescription' )"/>
    </xsl:variable>

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

    <!--	Slots -->


    <xsl:variable name="createdSlotName">
        <xsl:text>http://purl.org/dc/terms/created</xsl:text>
    </xsl:variable>

    <xsl:variable name="modifiedSlotName">
        <xsl:text>http://purl.org/dc/terms/modified</xsl:text>
    </xsl:variable>

    <xsl:variable name="issuedSlotName">
        <xsl:text>http://purl.org/dc/terms/issued</xsl:text>
    </xsl:variable>

    <xsl:variable name="resolutionSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::Resolution</xsl:text>
    </xsl:variable>

    <xsl:variable name="scaleDenominatorSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::ScaleDenominator</xsl:text>
    </xsl:variable>


    <xsl:variable name="contributorSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/contributor</xsl:text>
    </xsl:variable>

    <xsl:variable name="spatialSlotName">
        <xsl:text>http://purl.org/dc/terms/spatial</xsl:text>
    </xsl:variable>

    <!-- Changed starting from version 0.1.12: since this version, the mapping is to 2 temporal slots
    <xsl:variable name="temporalSlotName">
        <xsl:text>http://purl.org/dc/terms/temporal</xsl:text>
    </xsl:variable>
    -->

    <xsl:variable name="temporalBeginSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::TemporalBegin</xsl:text>
    </xsl:variable>

    <xsl:variable name="temporalEndSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::TemporalEnd</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="lineageSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::Lineage</xsl:text>
    </xsl:variable>
    
    <xsl:variable name="urlSlotName">
        <xsl:text>urn:ogc:def:slot:OGC-CSW-ebRIM-CIM::url</xsl:text>
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
    <xsl:variable name="conformsToSlotName">
        <xsl:text>http://purl.org/dc/terms/conformsTo</xsl:text>
    </xsl:variable>
    <xsl:variable name="dateSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:Date</xsl:text>
    </xsl:variable>
    <xsl:variable name="referencesSlotType">
        <xsl:text>xsd:URI</xsl:text>
    </xsl:variable>


    <xsl:variable name="languageSlotType">
        <xsl:text>urn:ogc:def:dataType:RFC-4646:Language</xsl:text>
    </xsl:variable>
    <xsl:variable name="titleSlotName">
        <xsl:text>http://purl.org/dc/terms/title</xsl:text>
    </xsl:variable>
    <xsl:variable name="identifierSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/identifier</xsl:text>
    </xsl:variable>
    <xsl:variable name="typeSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/type</xsl:text>
    </xsl:variable>
    <xsl:variable name="envelopeSlotType">
        <xsl:text>urn:ogc:def:dataType:ISO-19107:GM_Envelope</xsl:text>
    </xsl:variable>
    <xsl:variable name="dateTimeSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:DateTime</xsl:text>
    </xsl:variable>
    <xsl:variable name="abstractSlotName">
        <xsl:text>http://purl.org/dc/elements/1.1/abstract</xsl:text>
    </xsl:variable>
    <xsl:variable name="stringSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:String</xsl:text>
    </xsl:variable>
    <xsl:variable name="uriSlotType">
        <xsl:text>urn:oasis:names:tc:ebxml-regrep:DataType:URI</xsl:text>
    </xsl:variable>







    <!--ExtrinsicObject ID prefix -->

    <xsl:variable name="urnCimMetadataInformationExtrinsicObjectID" select="concat( $cimIDPrefix , 'ExtrinsicObject:MetadataInformation')"/>

    <xsl:variable name="urnCimParentIdentifierExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ParentIdentifier:')"/>

    <xsl:variable name="urnCimResourceMetadataExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ResourceMetadata:')"/>

    <xsl:variable name="urnCimIdentifiedItemExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:IdentifiedItem:')"/>

    <xsl:variable name="urnCimSecurityConstraintsExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:SecurityConstraints:')"/>

    <xsl:variable name="urnCimRightsExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:Rights:')"/>

    <xsl:variable name="urnCimLegalConstraintsExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:LegalConstraints:')"/>

    <xsl:variable name="urnCimCitedItemExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:CitedItem:')"/>

    <xsl:variable name="urnCimReferenceSpecificationExtrinsicObjectIDPrefix" select="concat( $cimIDPrefix , 'ExtrinsicObject:ReferenceSpecification:')"/>


    <!--Association ID prefix -->

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

    <xsl:variable name="urnCimServicesClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:Services:')"/>

    <xsl:variable name="urnCimKeywordTypeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordType:')"/>

    <xsl:variable name="urnCimKeywordSchemeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordScheme:')"/>

    <xsl:variable name="objectTypeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:ObjectType:')"/>

    <!-- Others-->

    <xsl:variable name="urnCimOrganizationIDPrefix" select="concat( $cimIDPrefix , 'Organization:')"/>

    <xsl:variable name="urnCimExternalIdentifierIDPrefix" select="concat( $cimIDPrefix , 'ExternalIdentifier:')"/>



</xsl:stylesheet>
