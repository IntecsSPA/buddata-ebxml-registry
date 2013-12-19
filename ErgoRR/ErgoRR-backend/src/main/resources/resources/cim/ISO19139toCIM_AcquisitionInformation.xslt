<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gmd="http://www.isotc211.org/2005/gmd" xmlns:gmi="http://www.isotc211.org/2005/gmi" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:gmx="http://www.isotc211.org/2005/gmx" xmlns:srv="http://www.isotc211.org/2005/srv" xmlns:gml32="http://www.opengis.net/gml/3.2" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink">
	<!-- <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/> -->
	<!-- Registration of Acquisition Information-->
	<xsl:template name="createAcquisitionInformation">
		<xsl:for-each select="/gmi:MI_Metadata/gmi:acquisitionInformation/gmi:MI_AcquisitionInformation">
			<xsl:variable name="acquisitionInformationId" select="concat($urnCimAcquisitionInformationExtrinsicObjectIDPrefix, generate-id())"/>
			<!-- Creation of the AcquisitionInformation object-->
			<rim:ExtrinsicObject id="{$acquisitionInformationId}" objectType="{$acquisitionInformationObjectType}">
			</rim:ExtrinsicObject>
			<!-- Creation of the AcquiredBy association-->
			<xsl:variable name="acquiredByAssociationId" select="concat( $urnCimAcquiredByAssociationIDPrefix, generate-id())"/>
			<rim:Association id="{$acquiredByAssociationId}" associationType="{$acquiredByAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$acquisitionInformationId}"/>
			<!-- 
				THE FOLLOWING SECTION HANDLES THE PLATFORM SUB-ROOT
			-->
			<!-- <xsl:for-each select="gmi:platform/gmi:MI_Platform | gmi:platform/gmi:MI_Platform/gmi:instrument/gmi:MI_Instrument/gmi:mountedOn/gmi:MI_Platform"> -->
			<xsl:for-each select="gmi:platform/gmi:MI_Platform">
				<!-- Creation of the Platform object -->
				<xsl:variable name="platformId" select="concat( $urnCimPlatformExtrinsicObjectIDPrefix, generate-id())"/>
				<xsl:call-template name="platform">
					<xsl:with-param name="platformNode" select="."/>
					<xsl:with-param name="platformId" select="$platformId"/>
				</xsl:call-template>
				<!-- Creation of the AcquisitionPlatform association-->
				<xsl:variable name="acquisitionPlatformAssociationId" select="concat( $urnCimAcquisitionPlatformAssociationIDPrefix, generate-id())"/>
				<rim:Association id="{$acquisitionPlatformAssociationId}" associationType="{$acquisitionPlatformAssociationType}" sourceObject="{$acquisitionInformationId}" targetObject="{$platformId}"/>
			</xsl:for-each>
			<!-- 
				THE FOLLOWING SECTION HANDLES THE INSTRUMENT SUB-ROOT with no mountedOn platforms
			-->
			<xsl:for-each select="gmi:instrument/gmi:MI_Instrument[empty(mountedOn)] | gmi:platform/gmi:MI_Platform/gmi:instrument/gmi:MI_Instrument[empty(mountedOn)]">
				<!-- Creation of the Instrument object -->
				<xsl:variable name="instrumentId" select="concat( $urnCimInstrumentExtrinsicObjectIDPrefix, generate-id())"/>
				<xsl:call-template name="instrument">
					<xsl:with-param name="instrumentNode" select="."/>
					<xsl:with-param name="instrumentId" select="$instrumentId"/>
				</xsl:call-template>
				<!-- Creation of the AcquisitionInstrument association-->
				<xsl:variable name="acquisitionInstrumentAssociationId" select="concat( $urnCimAcquisitionInstrumentAssociationIDPrefix, generate-id())"/>
				<rim:Association id="{$acquisitionInstrumentAssociationId}" associationType="{$acquisitionInstrumentAssociationType}" sourceObject="{$acquisitionInformationId}" targetObject="{$instrumentId}"/>
			</xsl:for-each>
			<!-- 
				THE FOLLOWING SECTION HANDLES THE INSTRUMENT SUB-ROOT with mountedOn platforms
			-->
			<xsl:for-each select="gmi:instrument/gmi:MI_Instrument[not(empty(mountedOn))] | gmi:platform/gmi:MI_Platform/gmi:instrument/gmi:MI_Instrument[not(empty(mountedOn))]">
				<!-- Creation of the Instrument object -->
				<xsl:variable name="instrumentId" select="concat( $urnCimInstrumentExtrinsicObjectIDPrefix, generate-id())"/>
				<xsl:call-template name="instrument">
					<xsl:with-param name="instrumentNode" select="."/>
					<xsl:with-param name="instrumentId" select="$instrumentId"/>
				</xsl:call-template>
				<!-- Creation of the AcquisitionInstrument association-->
				<xsl:variable name="acquisitionInstrumentAssociationId" select="concat( $urnCimAcquisitionInstrumentAssociationIDPrefix, generate-id())"/>
				<rim:Association id="{$acquisitionInstrumentAssociationId}" associationType="{$acquisitionInstrumentAssociationType}" sourceObject="{$acquisitionInformationId}" targetObject="{$instrumentId}"/>
				<!-- Creation of the Platform object -->
				<xsl:variable name="platformId" select="concat( $urnCimPlatformExtrinsicObjectIDPrefix, generate-id())"/>
				<xsl:call-template name="platform">
					<xsl:with-param name="platformNode" select="gmi:mountedOn/gmi:MI_Platform"/>
					<xsl:with-param name="platformId" select="$platformId"/>
				</xsl:call-template>
				<!-- Creation of the AcquisitionPlatform association-->
				<xsl:variable name="acquisitionPlatformAssociationId" select="concat( $urnCimAcquisitionPlatformAssociationIDPrefix, generate-id())"/>
				<rim:Association id="{$acquisitionPlatformAssociationId}" associationType="{$acquisitionPlatformAssociationType}" sourceObject="{$acquisitionInformationId}" targetObject="{$platformId}"/>
				<!-- Creation of the MountedOn association-->
				<xsl:variable name="mountedOnAssociationId" select="concat( $urnCimMountedOnAssociationIDPrefix, generate-id())"/>
				<rim:Association id="{$mountedOnAssociationId}" associationType="{$mountedOnAssociationType}" sourceObject="{$instrumentId}" targetObject="{$platformId}"/>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
	<!-- 
	The following is the template to call to create a Platform object, and the related objects (CitedItem, Organization) and associations (PlatformCitation, Sponsor)
	-->
	<xsl:template name="platform">
		<xsl:param name="platformNode"/>
		<xsl:param name="platformId"/>
		<rim:ExtrinsicObject id="{$platformId}" objectType="{$platformObjectType}">
			<rim:Slot name="{$identifierSlotName}" slotType="{$stringSlotType}">
				<rim:ValueList>
					<rim:Value>
						<xsl:value-of select="gmi:identifier/*/gmd:code/gco:CharacterString"/>
					</rim:Value>
				</rim:ValueList>
			</rim:Slot>
			<rim:Description>
				<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="{gmi:description/gco:CharacterString}"/>
			</rim:Description>
		</rim:ExtrinsicObject>
		<xsl:for-each select="gmd:citation/gmd:CI_Citation">
			<!-- Creation of the Citeditem object-->
			<xsl:variable name="citedItemId" select="concat( $urnCimCitedItemExtrinsicObjectIDPrefix, generate-id())"/>
			<xsl:call-template name="CitationInformation">
				<xsl:with-param name="citationInformationNode" select="."/>
				<xsl:with-param name="citedItemEOId" select="$citedItemId"/>
			</xsl:call-template>
			<!-- Creation of the PlatformCitation association-->
			<xsl:variable name="platformCitationAssociationId" select="concat( $urnCimPlatformCitationAssociationIDPrefix, generate-id())"/>
			<rim:Association id="{$platformCitationAssociationId}" associationType="{$platformCitationAssociationType}" sourceObject="{$platformId}" targetObject="{$citedItemId}"/>
		</xsl:for-each>
		<xsl:for-each select="gmi:sponsor">
			<!-- Creation of the Organization object-->
			<xsl:if test="not(empty(gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString))">
				<xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
				<xsl:call-template name="organization">
					<xsl:with-param name="responsiblePartyNode" select="."/>
					<xsl:with-param name="organizationId" select="$organizationId"/>
				</xsl:call-template>
				<!-- Creation of the Sponsor association-->
				<xsl:variable name="sponsorAssociationId" select="concat( $urnCimSponsorAssociationIDPrefix, generate-id())"/>
				<rim:Association id="{$sponsorAssociationId}" associationType="{$sponsorAssociationType}" sourceObject="{$platformId}" targetObject="{$organizationId}"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!-- 
	The following is the template to call to create an Instrument object, and the related object (CitedItem) and association (InstrumentCitation)
	-->
	<xsl:template name="instrument">
		<xsl:param name="instrumentNode"/>
		<xsl:param name="instrumentId"/>
		<rim:ExtrinsicObject id="{$instrumentId}" objectType="{$instrumentObjectType}">
			<rim:Slot name="{$identifierSlotName}" slotType="{$stringSlotType}">
				<rim:ValueList>
					<rim:Value>
					   <!-- This mapping should be the correct one according to the latest gmi schema
						<xsl:value-of select="gmi:identifier/*/gmd:code/gco:CharacterString"/>
                       -->
                       <xsl:value-of select="gmi:citation/gmd:CI_Citation/gmd:identifier/*/gmd:code/gco:CharacterString"/>
					</rim:Value>
				</rim:ValueList>
			</rim:Slot>
			<rim:Name>
				<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="{gmi:type/gmi:MI_SensorTypeCode/@codeListValue}"/>
			</rim:Name>
			<xsl:if test="gmi:description/gco:CharacterString">
				<rim:Description>
					<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="{gmi:description/gco:CharacterString}"/>
				</rim:Description>
			</xsl:if>
		</rim:ExtrinsicObject>
		<xsl:for-each select="gmd:citation/gmd:CI_Citation">
			<!-- Creation of the Citeditem object-->
			<xsl:variable name="citedItemId" select="concat( $urnCimCitedItemExtrinsicObjectIDPrefix, generate-id())"/>
			<xsl:call-template name="CitationInformation">
				<xsl:with-param name="citationInformationNode" select="."/>
				<xsl:with-param name="citedItemEOId" select="$citedItemId"/>
			</xsl:call-template>
			<!-- Creation of the InstrumentCitation association-->
			<xsl:variable name="instrumentCitationAssociationId" select="concat( $urnCimInstrumentCitationAssociationIDPrefix, generate-id())"/>
			<rim:Association id="{$instrumentCitationAssociationId}" associationType="{$instrumentCitationAssociationType}" sourceObject="{$instrumentId}" targetObject="{$citedItemId}"/>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
