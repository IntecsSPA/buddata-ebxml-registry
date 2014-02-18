<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gmd="http://www.isotc211.org/2005/gmd" xmlns:gmi="http://www.isotc211.org/2005/gmi" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:gmx="http://www.isotc211.org/2005/gmx" xmlns:srv="http://www.isotc211.org/2005/srv" xmlns:gml32="http://www.opengis.net/gml/3.2" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:param name="cswURL">http://hrt-11.pisa.intecs.it/ergorr2/httpservice</xsl:param>
	<xsl:param name="metadataInformationId">urn:CIM:metadataInformationId:1</xsl:param>
	<xsl:include href="ISO19139toCIM_Include.xslt"/>
	<xsl:include href="ISO19139toCIM_Common.xslt"/>
	<xsl:include href="ISO19139toCIM_MetadataInformation.xslt"/>
	<xsl:include href="ISO19139toCIM_DataQuality.xslt"/>
	<xsl:include href="ISO19139toCIM_AcquisitionInformation.xslt"/>
	<!-- 
		Definition of the resource metadata identifier
	-->
	<xsl:variable name="resourceMetadataId">
		<xsl:choose>
			<xsl:when test="contains( $isoId, 'urn:')">
				<xsl:value-of select="concat( $isoId, ':ResourceMetadata')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat( 'urn:CIM:', $isoId, ':ResourceMetadata')"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="metadataCharacterSet">
		<xsl:choose>
			<xsl:when test="/*/gmd:characterSet/gmd:MD_CharacterSetCode/@codeListValue">
				<xsl:value-of select="/*/gmd:characterSet/gmd:MD_CharacterSetCode/@codeListValue"/>
			</xsl:when>
			<xsl:otherwise>UTF-8</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="metadataLanguage">
		<xsl:choose>
			<xsl:when test="/*/gmd:language/gmd:LanguageCode/@codeListValue">
				<rim:Value>
					<xsl:value-of select="/*/gmd:language/gmd:LanguageCode/@codeListValue"/>
				</rim:Value>
			</xsl:when>
			<xsl:when test="/*/gmd:language/gco:CharacterString">
				<rim:Value>
					<xsl:value-of select="/*/gmd:language/gco:CharacterString"/>
				</rim:Value>
			</xsl:when>
			<xsl:otherwise>en</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:template match="gmd:MD_Metadata | gmi:MI_Metadata">
		<!--xsl:variable name="metadataInformationId" select="$urnCimMetadataInformationExtrinsicObjectID"/-->
		<xsl:variable name="registryPackageId" select="concat( $metadataInformationId , ':pkg')"/>
		<rim:RegistryObjectList>
			<xsl:attribute name="iso19139Id"><xsl:value-of select="./gmd:fileIdentifier/gco:CharacterString"/></xsl:attribute>
			<!-- 
				Begin keywords in standard classiifcation schemes
				-->
			<xsl:variable name="descriptiveKeywordsClassificationNodes">
				<xsl:for-each select="gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:descriptiveKeywords">
					<xsl:variable name="keywordType">
						<xsl:value-of select="gmd:MD_Keywords/gmd:type/gmd:MD_KeywordTypeCode/@codeListValue"/>
					</xsl:variable>
					<xsl:variable name="classificationScheme">
						<xsl:choose>
							<xsl:when test="$keywordType = 'discipline' ">
								<xsl:value-of select="$KeywordSchemeDisciplineClassificationScheme"/>
							</xsl:when>
							<xsl:when test="$keywordType = 'place' ">
								<xsl:value-of select="$KeywordSchemePlaceClassificationScheme"/>
							</xsl:when>
							<xsl:when test="$keywordType = 'stratum' ">
								<xsl:value-of select="$KeywordSchemeStratumClassificationScheme"/>
							</xsl:when>
							<xsl:when test="$keywordType = 'temporal' ">
								<xsl:value-of select="$KeywordSchemeTemporalClassificationScheme"/>
							</xsl:when>
							<xsl:when test="$keywordType = 'theme' ">
								<xsl:value-of select="$KeywordSchemeThemeClassificationScheme"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select=" '' "/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:if test="$classificationScheme!= '' ">
						<xsl:call-template name="createClassificationNodes">
							<xsl:with-param name="nodeDescriptiveKeywords" select="."/>
							<xsl:with-param name="parentClassificationScheme" select="$classificationScheme"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:variable name="isThesaurusName">
						<xsl:call-template name="getNodeContent">
							<xsl:with-param name="node" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:if test="string-length($isThesaurusName) = 0">
						<xsl:call-template name="createClassificationNodes">
							<xsl:with-param name="nodeDescriptiveKeywords" select="."/>
							<xsl:with-param name="parentClassificationScheme" select="$KeywordSchemeUntypedClassificationScheme"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:if test="$descriptiveKeywordsClassificationNodes">
				<xsl:copy-of select="$descriptiveKeywordsClassificationNodes"/>
			</xsl:if>
			<!-- 
				Begin keywords in new classiifcation schemes
				-->
			<xsl:variable name="descriptiveKeywordsClassificationSchemes">
				<xsl:for-each select="gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:descriptiveKeywords">
					<xsl:variable name="isThesaurusName">
						<xsl:call-template name="getNodeContent">
							<xsl:with-param name="node" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:if test="string-length($isThesaurusName) > 0">
						<xsl:variable name="thesaurusIdentifier">
							<xsl:call-template name="getIdByNodeContent">
								<xsl:with-param name="node" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title"/>
							</xsl:call-template>
						</xsl:variable>
						<xsl:variable name="thesaurusName">
							<xsl:call-template name="getNodeContent">
								<xsl:with-param name="node" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title"/>
							</xsl:call-template>
						</xsl:variable>
						<xsl:variable name="thesaurusDescription" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:alternateTitle/gco:CharacterString"/>
						<xsl:variable name="keywordThesaurusSchemeClassificationScheme">
							<xsl:value-of select="concat( $cimIDPrefix, 'KeywordThesaurusScheme:', $thesaurusIdentifier )"/>
						</xsl:variable>
						<xsl:variable name="keywordThesaurusSchemeClassificationSchemePrefix">
							<xsl:value-of select="concat( $cimIDPrefix, 'KeywordThesaurusScheme:', $thesaurusIdentifier , ':' )"/>
						</xsl:variable>
						<rim:ClassificationScheme xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="{$keywordThesaurusSchemeClassificationScheme}" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationScheme" isInternal="true" nodeType="urn:oasis:names:tc:ebxml-regrep:NodeType:UniqueCode">
							<rim:Name>
								<rim:LocalizedString xml:lang="en" value="{$thesaurusName}"/>
							</rim:Name>
							<xsl:if test="$thesaurusDescription">
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{$thesaurusDescription}"/>
								</rim:Description>
							</xsl:if>
							<xsl:call-template name="createClassificationNodes">
								<xsl:with-param name="nodeDescriptiveKeywords" select="."/>
								<xsl:with-param name="parentClassificationScheme" select="$keywordThesaurusSchemeClassificationScheme"/>
							</xsl:call-template>
						</rim:ClassificationScheme>
						<xsl:variable name="citedItemId" select="concat( $urnCimCitedItemExtrinsicObjectIDPrefix, generate-id(.))"/>
						<xsl:call-template name="CitationInformation">
							<xsl:with-param name="citationInformationNode" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation"/>
							<xsl:with-param name="citedItemEOId" select="$citedItemId"/>
						</xsl:call-template>
						<rim:Association id="{concat($urnCimThesaurusAssociationIDPrefix, ':', generate-id(.))}" associationType="{$thesaurusAssociationType}" sourceObject="{$keywordThesaurusSchemeClassificationScheme}" targetObject="{$citedItemId}"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<xsl:if test="$descriptiveKeywordsClassificationSchemes">
				<xsl:copy-of select="$descriptiveKeywordsClassificationSchemes"/>
			</xsl:if>
			<!-- -End Keywords-->
			<!-- 
				Classification Services Handling START 
			-->
			<!-- References to chapter 8.4 "OGC service types" of document OGC 07-144r4 on CSW-ebRIM Basic Extension package-->
			<xsl:variable name="servicesClassificationNodes">
				<xsl:variable name="serviceType" select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceType/gco:LocalName"/>
				<xsl:choose>
					<xsl:when test="$serviceType = 'WFS' ">
						<xsl:variable name="serviceTypeVersion">
							<versions>
								<xsl:choose>
									<xsl:when test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
										<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
											<value>
												<xsl:value-of select="."/>
											</value>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<value>
											<xsl:value-of select=" '1.1' "/>
										</value>
									</xsl:otherwise>
								</xsl:choose>
							</versions>
						</xsl:variable>
						<xsl:for-each select="$serviceTypeVersion//value">
							<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', .)"/>
							<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:FeatureAccess" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:serviceType:WebFeatureService', $serviceTypeVersionSuffix)}">
								<rim:Name>
									<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
								</rim:Name>
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{concat('OGC Web Feature Access Service version ', .)}"/>
								</rim:Description>
							</rim:ClassificationNode>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="$serviceType = 'WMS' ">
						<xsl:variable name="serviceTypeVersion">
							<versions>
								<xsl:choose>
									<xsl:when test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
										<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
											<value>
												<xsl:value-of select="."/>
											</value>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<value>
											<xsl:value-of select=" '1.3.0' "/>
										</value>
									</xsl:otherwise>
								</xsl:choose>
							</versions>
						</xsl:variable>
						<xsl:for-each select="$serviceTypeVersion//value">
							<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', .)"/>
							<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:MapAccess" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:serviceType:WebMapService', $serviceTypeVersionSuffix)}">
								<rim:Name>
									<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
								</rim:Name>
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{concat('OGC Web Map Access Service version ', .)}"/>
								</rim:Description>
							</rim:ClassificationNode>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="$serviceType = 'WCS' ">
						<xsl:variable name="serviceTypeVersion">
							<versions>
								<xsl:choose>
									<xsl:when test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
										<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
											<value>
												<xsl:value-of select="."/>
											</value>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<value>
											<xsl:value-of select=" '1.1.2' "/>
										</value>
									</xsl:otherwise>
								</xsl:choose>
							</versions>
						</xsl:variable>
						<xsl:for-each select="$serviceTypeVersion//value">
							<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', .)"/>
							<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:CoverageAccess" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:serviceType:WebCoverageService', $serviceTypeVersionSuffix)}">
								<rim:Name>
									<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
								</rim:Name>
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{concat('OGC Web Coverage Access Service version ', .)}"/>
								</rim:Description>
							</rim:ClassificationNode>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="$serviceType = 'CAT' ">
						<xsl:variable name="serviceTypeVersion">
							<versions>
								<xsl:choose>
									<xsl:when test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
										<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
											<value>
												<xsl:value-of select="."/>
											</value>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<value>
											<xsl:value-of select=" '2.0.2' "/>
										</value>
									</xsl:otherwise>
								</xsl:choose>
							</versions>
						</xsl:variable>
						<xsl:for-each select="$serviceTypeVersion//value">
							<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', .)"/>
							<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:Catalogue" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:serviceType:CatalogueService', $serviceTypeVersionSuffix)}">
								<rim:Name>
									<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
								</rim:Name>
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{concat('OGC Web Catalogue Service version ', .)}"/>
								</rim:Description>
							</rim:ClassificationNode>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="$serviceType = 'WRS' ">
						<xsl:variable name="serviceTypeVersion">
							<versions>
								<xsl:choose>
									<xsl:when test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
										<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
											<value>
												<xsl:value-of select="."/>
											</value>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<value>
											<xsl:value-of select=" '1.0.1' "/>
										</value>
									</xsl:otherwise>
								</xsl:choose>
							</versions>
						</xsl:variable>
						<xsl:for-each select="$serviceTypeVersion//value">
							<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', .)"/>
							<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="urn:ogc:serviceType:CatalogueService:2.0.2" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:serviceType:CatalogueService:2.0.2:HTTP:ebRIM', $serviceTypeVersionSuffix)}">
								<rim:Name>
									<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
								</rim:Name>
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{concat('OGC CSW-ebRIM Catalogue Service version ', .)}"/>
								</rim:Description>
							</rim:ClassificationNode>
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="$serviceType = 'WPS' ">
						<xsl:variable name="serviceTypeVersion">
							<versions>
								<xsl:choose>
									<xsl:when test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
										<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
											<value>
												<xsl:value-of select="."/>
											</value>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<value>
											<xsl:value-of select=" '1.0.0' "/>
										</value>
									</xsl:otherwise>
								</xsl:choose>
							</versions>
						</xsl:variable>
						<xsl:for-each select="$serviceTypeVersion//value">
							<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', .)"/>
							<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services:Processing" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:serviceType:WebProcessingService', $serviceTypeVersionSuffix)}">
								<rim:Name>
									<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
								</rim:Name>
								<rim:Description>
									<rim:LocalizedString xml:lang="en" value="{concat('OGC Web Processing Service version ', .)}"/>
								</rim:Description>
							</rim:ClassificationNode>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="count(gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion) > 0">
							<xsl:for-each select="gmd:identificationInfo/srv:SV_ServiceIdentification/srv:serviceTypeVersion/gco:CharacterString">
								<xsl:variable name="serviceTypeVersion" select="."/>
								<xsl:variable name="serviceTypeVersionSuffix" select="concat(':', $serviceTypeVersion)"/>
								<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="{concat('urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services', ':', $serviceType)}" code="{concat($serviceType, $serviceTypeVersionSuffix)}" id="{concat('urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services', ':', $serviceType, $serviceTypeVersionSuffix)}">
									<rim:Name>
										<rim:LocalizedString xml:lang="en" value="{concat($serviceType, $serviceTypeVersionSuffix)}"/>
									</rim:Name>
									<rim:Description>
										<rim:LocalizedString xml:lang="en" value="{concat($serviceType, ' version ' , $serviceTypeVersion)}"/>
									</rim:Description>
								</rim:ClassificationNode>
							</xsl:for-each>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:if test="$servicesClassificationNodes">
				<xsl:for-each select="$servicesClassificationNodes//rim:ClassificationNode">
					<xsl:if test="not(compare(@id, 'urn:ogc:serviceType:WebFeatureService:1.1') = 0 or compare(@id, 'urn:ogc:serviceType:WebMapService:1.3.0') = 0 or
                            compare(@id, 'urn:ogc:serviceType:WebCoverageService:1.1.2') = 0 or compare(@id, 'urn:ogc:serviceType:CatalogueService:2.0.2') = 0 or
                            compare(@id, 'urn:ogc:serviceType:CatalogueService:2.0.2:HTTP:ebRIM') = 0 )">
						<xsl:copy-of select="."/>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
			<!-- Classification Services Handling END -->
			<xsl:call-template name="createMainMetadataInformation">
				<xsl:with-param name="metadataInformationId" select="$metadataInformationId"/>
			</xsl:call-template>
			<!-- 
					Resource Metadata EO Call Template START 
			-->
			<xsl:for-each select="gmd:identificationInfo">
				<xsl:call-template name="resourceMetadata">
					<xsl:with-param name="metadadataEOIdentifier" select="$metadataInformationId"/>
					<xsl:with-param name="descriptiveKeywordsClassificationNodes" select="$descriptiveKeywordsClassificationNodes"/>
					<xsl:with-param name="descriptiveKeywordsClassificationSchemes" select="$descriptiveKeywordsClassificationSchemes"/>
					<xsl:with-param name="servicesClassificationNodes" select="$servicesClassificationNodes"/>
				</xsl:call-template>
			</xsl:for-each>
			<!-- Resource Metadata EO Call Template END -->
			<!-- 
					Parent Identifier (Metadata Information EO) START
			-->
			<xsl:variable name="parentIdentifier" select="gmd:parentIdentifier/gco:CharacterString"/>
			<xsl:if test="$parentIdentifier">
				<xsl:variable name="parentIdentifierMetadataId" select="concat($urnCimMetadataInformationExtrinsicObjectID, ':2')"/>
				<rim:ExtrinsicObject id="{$parentIdentifierMetadataId}" objectType="{$metadataInformationObjectType}">
					<rim:Slot name="{$identifierSlotName}" slotType="{$stringSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$parentIdentifier"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</rim:ExtrinsicObject>
				<xsl:variable name="parentMetadataInformationAssociationId" select="concat( $urnCimParentMetadataInformationAssociationIDPrefix, generate-id(.))"/>
				<rim:Association id="{$parentMetadataInformationAssociationId}" associationType="{$parentMetadataInformationAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$parentIdentifierMetadataId}"/>
			</xsl:if>
			<!-- Parent Idenfier (Metadata Information EO) END -->
		</rim:RegistryObjectList>
	</xsl:template>
	<!-- 
			Resource Metadata EO Template
	-->
	<xsl:template name="resourceMetadata">
		<xsl:param name="metadadataEOIdentifier"/>
		<xsl:param name="descriptiveKeywordsClassificationNodes"/>
		<xsl:param name="descriptiveKeywordsClassificationSchemes"/>
		<xsl:param name="servicesClassificationNodes"/>
		<!-- Classiifcation Node for format name and version START-->
		<xsl:for-each-group select="/*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributionFormat/gmd:MD_Format |
										   /*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributor/gmd:MD_Distributor/gmd:distributorFormat/gmd:MD_Format" group-by="concat(encode-for-uri(gmd:name/gco:CharacterString), encode-for-uri(gmd:version/gco:CharacterString))">
			<xsl:variable name="formatName">
				<xsl:value-of select="gmd:name/gco:CharacterString"/>
			</xsl:variable>
			<xsl:variable name="formatVersion" select="gmd:version/gco:CharacterString"/>
			<xsl:variable name="encodedCode" select="concat(encode-for-uri($formatName), ':', encode-for-uri($formatVersion))"/>
			<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="{$formatNameAndVersionClassificationScheme}" code="{concat($formatName, ':', $formatVersion)}" id="{concat($formatNameAndVersionClassificationSchemePrefix, $encodedCode)}">
				<rim:Name>
					<rim:LocalizedString xml:lang="en" value="{concat($formatName, ':', $formatVersion)}"/>
				</rim:Name>
				<rim:Description>
					<rim:LocalizedString xml:lang="en" value="{concat($formatName, ':', $formatVersion, ' version')}"/>
				</rim:Description>
			</rim:ClassificationNode>
		</xsl:for-each-group>
		<!-- Classiifcation Node for format name and version END-->
		<wrs:ExtrinsicObject id="{$resourceMetadataId}">
			<xsl:call-template name="specifyTypeOfResourceMetadata"/>
			<xsl:call-template name="slot-hierarchyLevelName">
				<xsl:with-param name="hierarchyLevelName" select="/*/gmd:hierarchyLevelName"/>
				<xsl:with-param name="charset" select="$metadataCharacterSet"/>
				<xsl:with-param name="language" select="$metadataLanguage"/>
			</xsl:call-template>
			<!-- Management of CITATION START -->
			<xsl:variable name="citation" select="/*/gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or 
																		local-name() = 'MD_DataIdentification' ]/gmd:citation/gmd:CI_Citation"/>
			<xsl:variable name="alternateTitle" select="$citation/gmd:alternateTitle"/>
			<xsl:if test="$alternateTitle">
				<!-- International string type NOT SUPPORTED
				 <rim:Slot name="{$titleSlotName}" slotType="{$internationalStringSlotType}"> 
					<wrs:ValueList>
					  <xsl:for-each select="$alternateTitle/gco:CharacterString | $alternateTitle/*/@codeListValue | $alternateTitle/@gco:nilReason">
						<wrs:AnyValue>
							<rim:InternationalString>
								<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="{.}"/>
							</rim:InternationalString>
						</wrs:AnyValue>
					  </xsl:for-each>
				    </wrs:ValueList>
				</rim:Slot>   -->
				<rim:Slot name="{$titleSlotName}" slotType="{$stringSlotType}">
					<rim:ValueList>
						<xsl:for-each select="$alternateTitle/gco:CharacterString | $alternateTitle/*/@codeListValue | $alternateTitle/@gco:nilReason">
							<rim:Value>
								<xsl:value-of select="."/>
							</rim:Value>
						</xsl:for-each>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
			<xsl:for-each select="$citation/gmd:date">
				<xsl:call-template name="slot-date-with-type">
					<xsl:with-param name="date" select="gmd:CI_Date/gmd:date"/>
					<xsl:with-param name="dateType" select="gmd:CI_Date/gmd:dateType"/>
				</xsl:call-template>
			</xsl:for-each>
			<!-- Management of CITATION END -->
			<xsl:variable name="language" select="/*/gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or
																			local-name() = 'MD_DataIdentification' ]/gmd:language"/>
			<rim:Slot name="{$languageSlotName}" slotType="{$languageSlotType}">
				<rim:ValueList>
					<xsl:for-each select="$language/gco:CharacterString | $language/*/@codeListValue | $language/@gco:nilReason">
						<rim:Value>
							<xsl:value-of select="."/>
						</rim:Value>
					</xsl:for-each>
				</rim:ValueList>
			</rim:Slot>
			<!-- Management of distribution format-->
			<xsl:if test="/*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributionFormat/gmd:MD_Format or
												/*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributor/gmd:MD_Distributor/gmd:distributorFormat/gmd:MD_Format">
				<rim:Slot name="{$formatSlotName}" slotType="{$stringSlotType}">
					<rim:ValueList>
						<xsl:for-each-group select="/*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributionFormat/gmd:MD_Format |
										   /*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributor/gmd:MD_Distributor/gmd:distributorFormat/gmd:MD_Format" group-by="concat(encode-for-uri(gmd:name/gco:CharacterString), encode-for-uri(gmd:version/gco:CharacterString))">
							<xsl:variable name="formatName" select="gmd:name/gco:CharacterString"/>
							<xsl:variable name="formatVersion" select="gmd:version/gco:CharacterString"/>
							<xsl:variable name="encodedCode" select="concat(encode-for-uri($formatName), ':', encode-for-uri($formatVersion))"/>
							<xsl:variable name="formatIdentifier" select="concat($formatNameAndVersionClassificationSchemePrefix, $encodedCode)"/>
							<rim:Value>
								<xsl:value-of select="$formatIdentifier"/>
							</rim:Value>
						</xsl:for-each-group>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
			<xsl:if test="/*/gmd:distributionInfo/gmd:MD_Distribution/gmd:transferOptions/gmd:MD_DigitalTransferOptions/gmd:onLine/gmd:CI_OnlineResource/gmd:linkage/gmd:URL or
           					   /*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributor/gmd:MD_Distributor/gmd:distributorTransferOptions/gmd:MD_DigitalTransferOptions/gmd:onLine/gmd:CI_OnlineResource/gmd:linkage/gmd:URL">
				<rim:Slot name="{$sourceSlotName}" slotType="{$uriSlotType}">
					<rim:ValueList>
						<xsl:for-each select="/*/gmd:distributionInfo/gmd:MD_Distribution/gmd:transferOptions/gmd:MD_DigitalTransferOptions/gmd:onLine/gmd:CI_OnlineResource/gmd:linkage/gmd:URL |
														   /*/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributor/gmd:MD_Distributor/gmd:distributorTransferOptions/gmd:MD_DigitalTransferOptions/gmd:onLine/gmd:CI_OnlineResource/gmd:linkage/gmd:URL">
							<rim:Value>
								<xsl:value-of select="."/>
							</rim:Value>
						</xsl:for-each>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
			<!-- Management of the Lineage information-->
			<xsl:if test="/*/gmd:dataQualityInfo[1]/*/gmd:lineage/*/gmd:statement/gco:CharacterString">
				<!--International string type NOT SUPPORTED -->
				<!-- <rim:Slot name="{$lineageSlotName}" slotType="{$internationalStringSlotType}">
					<wrs:ValueList>
					  <xsl:for-each select="/*/gmd:dataQualityInfo/*/gmd:lineage/*/gmd:statement/gco:CharacterString">
						<wrs:AnyValue>
							<rim:InternationalString>
								<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="{.}"/>
							</rim:InternationalString>
						</wrs:AnyValue>
					  </xsl:for-each>
				    </wrs:ValueList>
				</rim:Slot> -->
				<rim:Slot name="{$lineageSlotName}" slotType="{$stringSlotType}">
					<rim:ValueList>
						<xsl:for-each select="/*/gmd:dataQualityInfo/*/gmd:lineage/*/gmd:statement/gco:CharacterString">
							<rim:Value>
								<xsl:value-of select="."/>
							</rim:Value>
						</xsl:for-each>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
			<xsl:if test="gmd:MD_DataIdentification">
				<xsl:call-template name="datametadata">
					<xsl:with-param name="dataset-id" select="$resourceMetadataId"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="gmd:MD_ServiceIdentification">
				<xsl:call-template name="datametadata">
					<xsl:with-param name="dataset-id" select="$resourceMetadataId"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="srv:SV_ServiceIdentification">
				<xsl:call-template name="servicemetadata">
					<xsl:with-param name="servicemetadata-id" select="$resourceMetadataId"/>
					<xsl:with-param name="servicesClassificationNodes" select="$servicesClassificationNodes"/>
				</xsl:call-template>
			</xsl:if>
			<!--  ................................................................................................................... -->
			<xsl:call-template name="createDescriptiveKeywordsClassificationIdByNodes">
				<xsl:with-param name="classificationNodes" select="$descriptiveKeywordsClassificationNodes"/>
			</xsl:call-template>
			<!--  ................................................................................................................... -->
			<xsl:call-template name="createDescriptiveKeywordsClassificationIdBySchemes">
				<xsl:with-param name="classificationSchemes" select="$descriptiveKeywordsClassificationSchemes"/>
			</xsl:call-template>
			<!--  ................................................................................................................... -->
			<!-- External identifers handling-->
			<xsl:for-each select="gmd:MD_DataIdentification/gmd:citation/gmd:CI_Citation/gmd:identifier/gmd:RS_Identifier | 
											gmd:MD_DataIdentification/gmd:citation/gmd:CI_Citation/gmd:identifier/gmd:MD_Identifier">
				<!-- <xsl:variable name="codespace" select="gmd:codeSpace/gco:CharacterString"/> -->
				<xsl:variable name="codevalue" select="gmd:code/gco:CharacterString"/>
				<xsl:variable name="externalIdentifierId" select="concat( $urnCimExternalIdentifierIDPrefix, generate-id(.))"/>
				<xsl:variable name="identifierScheme" select="concat( $IdentifierClassificationSchemePrefix, 'resourceIdentifier')"/>
				<rim:ExternalIdentifier id="{$externalIdentifierId}" registryObject="{$resourceMetadataId}" identificationScheme="{$identifierScheme}" value="{$codevalue}"/>
			</xsl:for-each>
			<!-- Even if the following external identifier is redundant, since it duplicates the info already contained in the identifier slot of the MetadataInformation registry object,
				it is added to make available a relevant piece of info (the file identifier) also in the main registry oject (derived from Resource Metadata) -->
			<xsl:variable name="externalIdentifierId" select="concat( $urnCimExternalIdentifierIDPrefix, generate-id(.))"/>
			<xsl:variable name="identifierScheme" select="concat( $IdentifierClassificationSchemePrefix, 'fileIdentifier')"/>
			<rim:ExternalIdentifier id="{$externalIdentifierId}" registryObject="{$resourceMetadataId}" identificationScheme="{$identifierScheme}" value="{$isoId}"/>
			<wrs:repositoryItemRef xlink:href="{concat( $cswURL, '?request=GetRepositoryItem&amp;service=CSW-ebRIM&amp;Id=', $resourceMetadataId)}"/>
		</wrs:ExtrinsicObject>
		<!-- Registration of Graphic Overview Information-->
		<xsl:call-template name="createGraphicOverview"/>
		<!-- Registration of Quality Information-->
		<xsl:call-template name="createDataQuality"/>
		<!-- Registration of Quality Conformance Results-->
		<xsl:call-template name="createDataQualityConformanceResults"/>
		<!-- Registration of Acquisition Information -->
		<xsl:call-template name="createAcquisitionInformation"/>
		<xsl:if test="srv:SV_ServiceIdentification">
			<xsl:call-template name="serviceoperation">
				<xsl:with-param name="servicemetadata-id" select="$resourceMetadataId"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="srv:SV_ServiceIdentification">
			<xsl:call-template name="operatesOn">
				<xsl:with-param name="servicemetadata-id" select="$resourceMetadataId"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:call-template name="resourceConstraints">
			<xsl:with-param name="resourceConstraints-id" select="$resourceMetadataId"/>
		</xsl:call-template>
		<xsl:call-template name="citedResponsibleParty">
			<xsl:with-param name="citedResponsibleParty-id" select="$resourceMetadataId"/>
		</xsl:call-template>
		<xsl:call-template name="pointOfContact">
			<xsl:with-param name="pointOfContact-id" select="$resourceMetadataId"/>
		</xsl:call-template>
		<xsl:for-each select="/*/gmd:referenceSystemInfo">
			<xsl:call-template name="referenceSystemInfo">
				<xsl:with-param name="referenceSystemInfo_id" select="$resourceMetadataId"/>
			</xsl:call-template>
		</xsl:for-each>
		<xsl:variable name="resourceMetadataInformationAssociationId" select="concat( $urnCimResourceMetadataInformationAssociationIDPrefix, generate-id(.))"/>
		<!-- Controllare se ci va la resource che linkata dal metadata-->
		<rim:Association id="{$resourceMetadataInformationAssociationId}" associationType="{$resourceMetadataInformationAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$metadadataEOIdentifier}"/>
	</xsl:template>
	<!-- 
			Service Operation Template
	-->
	<xsl:template name="serviceoperation">
		<xsl:param name="servicemetadata-id"/>
		<xsl:for-each select="srv:SV_ServiceIdentification/srv:containsOperations">
			<xsl:variable name="serviceOperationId" select="concat( $urnCimServiceOperationExtrinsicObjectIDPrefix, ':', generate-id(.))"/>
			<rim:ExtrinsicObject id="{$serviceOperationId}" objectType="{$serviceOperationObjectType}">
				<xsl:for-each select="srv:SV_OperationMetadata/srv:connectPoint/gmd:CI_OnlineResource/gmd:linkage">
					<xsl:variable name="url" select="gmd:URL"/>
					<rim:Slot name="http://purl.org/dc/terms/references" slotType="{$uriSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$url"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:for-each>
				<rim:Name>
					<xsl:variable name="name" select="srv:SV_OperationMetadata/srv:operationName/gco:CharacterString"/>
					<rim:LocalizedString value="{$name}"/>
				</rim:Name>
				<xsl:for-each select="srv:SV_OperationMetadata/srv:DCP">
					<xsl:variable name="dcpListValue" select="srv:DCPList/@codeListValue"/>
					<xsl:variable name="dcpListClassificationId" select="concat( $urnCimDCPListClassificationIDPrefix, generate-id(.))"/>
					<xsl:if test="$dcpListValue='XML' or $dcpListValue='CORBA' or $dcpListValue='JAVA' or $dcpListValue='COM' or $dcpListValue='SQL' or $dcpListValue='WebServices' or $dcpListValue='SOAP' ">
						<rim:Classification id="{$dcpListClassificationId}" classifiedObject="{$serviceOperationId}" classificationNode="{concat( $DCPListClassificationSchemePrefix, $dcpListValue)}"/>
					</xsl:if>
				</xsl:for-each>
			</rim:ExtrinsicObject>
			<xsl:variable name="containsOperationId" select="concat( $urnCimContainsOperationAssociationIDPrefix, ':', generate-id(.))"/>
			<!-- Controllare se ci va la resource che linkata dal metadata-->
			<rim:Association id="{$containsOperationId}" associationType="{$containsOperationAssociationType}" sourceObject="{$servicemetadata-id}" targetObject="{$serviceOperationId}"/>
		</xsl:for-each>
	</xsl:template>
	<!-- 
			Operates On Template
	-->
	<xsl:template name="operatesOn">
		<xsl:param name="servicemetadata-id"/>
		<xsl:for-each select="srv:SV_ServiceIdentification/srv:operatesOn">
			<xsl:variable name="identifier" select="substring-after(@xlink:href, '#')"/>
			<xsl:if test="not(empty($identifier))">
				<xsl:variable name="identifierPrefix">
					<xsl:if test="not(starts-with($identifier,'urn:'))">
						<xsl:value-of select=" 'urn:CIM:' "/>
					</xsl:if>
				</xsl:variable>
				<xsl:variable name="resourceMetadataId" select="concat( $identifierPrefix, $identifier, ':ResourceMetadata' )"/>
				<wrs:ExtrinsicObject id="{$resourceMetadataId}" objectType="{$datasetCollectionObjectType}"/>
				<xsl:variable name="operatesOnId" select="concat( $urnCimOperatesOnAssociationIDPrefix, ':', generate-id(.))"/>
				<!-- Controllare se ci va la resource che linkata dal metadata-->
				<rim:Association id="{$operatesOnId}" associationType="{$operatesOnAssociationType}" sourceObject="{$servicemetadata-id}" targetObject="{$resourceMetadataId}"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!-- 
			Point of Contact Template
		-->
	<xsl:template name="pointOfContact">
		<xsl:param name="pointOfContact-id"/>
		<xsl:for-each select="*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:pointOfContact">
			<xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
			<xsl:call-template name="organization">
				<xsl:with-param name="responsiblePartyNode" select="."/>
				<xsl:with-param name="organizationId" select="$organizationId"/>
			</xsl:call-template>
			<xsl:variable name="citedResponsiblePartyAssociationId" select="concat( $urnCimCitedResponsiblePartyAssociationIDPrefix, generate-id(.) )"/>
			<rim:Association id="{$citedResponsiblePartyAssociationId}" associationType="{$citedResponsiblePartyAssociationType}" sourceObject="{$pointOfContact-id}" targetObject="{$organizationId}">
				<!-- <xsl:variable name="role" select="gmd:CI_ResponsibleParty/gmd:role/gmd:CI_RoleCode/@codeListValue"/> -->
				<xsl:variable name="role" select=" 'pointOfContact' "/>
				<xsl:variable name="citedResponsiblePartyAssociationClassificationId" select="concat( $urnCimCitedResponsiblePartyClassificationIDPrefix, generate-id(.) )"/>
				<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
			</rim:Association>
		</xsl:for-each>
	</xsl:template>
	<!-- 
			Cited Responsible Party Template
	-->
	<xsl:template name="citedResponsibleParty">
		<xsl:param name="citedResponsibleParty-id"/>
		<xsl:for-each select="*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:citation/gmd:CI_Citation/gmd:citedResponsibleParty">
			<xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.))"/>
			<xsl:call-template name="organization">
				<xsl:with-param name="responsiblePartyNode" select="."/>
				<xsl:with-param name="organizationId" select="$organizationId"/>
			</xsl:call-template>
			<xsl:variable name="citedResponsiblePartyAssociationId" select="concat( $urnCimCitedResponsiblePartyAssociationIDPrefix, generate-id(.) )"/>
			<rim:Association id="{$citedResponsiblePartyAssociationId}" associationType="{$citedResponsiblePartyAssociationType}" sourceObject="{$citedResponsibleParty-id}" targetObject="{$organizationId}">
				<xsl:variable name="role" select="gmd:CI_ResponsibleParty/gmd:role/gmd:CI_RoleCode/@codeListValue"/>
				<xsl:variable name="citedResponsiblePartyAssociationClassificationId" select="concat( $urnCimCitedResponsiblePartyClassificationIDPrefix, generate-id(.))"/>
				<xsl:if test="$role = 'pointOfContact'">
					<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role )}"/>
				</xsl:if>
				<xsl:if test="$role = 'author'">
					<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role )}"/>
				</xsl:if>
				<xsl:if test="$role = 'publisher'">
					<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role )}"/>
				</xsl:if>
				<xsl:if test="$role = 'originator'">
					<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role )}"/>
				</xsl:if>
			</rim:Association>
			<xsl:if test="not(empty(gmd:CI_ResponsibleParty/gmd:individualName/gco:CharacterString))">
				<xsl:variable name="personName" select="gmd:CI_ResponsibleParty/gmd:individualName/gco:CharacterString"/>
				<rim:Person id="{concat($urnCimPersonIDPrefix, ':', generate-id())}" objectType="{$personObjectType}">
					<rim:PersonName lastName="{$personName}"/>
				</rim:Person>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!-- 
			Resource Constraint Template
	-->
	<xsl:template name="resourceConstraints">
		<xsl:param name="resourceConstraints-id"/>
		<xsl:for-each select="*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:resourceConstraints">
			<xsl:if test="gmd:MD_Constraints">
				<xsl:if test="gmd:MD_Constraints/gmd:useLimitation[1]/gco:CharacterString">
					<xsl:variable name="rightsId" select="concat($urnCimRightsExtrinsicObjectIDPrefix, generate-id(gmd:MD_Constraints))"/>
					<rim:ExtrinsicObject id="{$rightsId}" objectType="{$rightsObjectType}">
						<rim:Description>
							<rim:LocalizedString xml:lang="{$metadataLanguage}" charset="{$metadataCharacterSet}" value="{gmd:MD_Constraints/gmd:useLimitation[1]/gco:CharacterString}"/>
						</rim:Description>
					</rim:ExtrinsicObject>
					<xsl:variable name="resourceConstraintsAssociationId" select="concat( $urnCimResourceConstraintsAssociationIDPrefix, generate-id(gmd:MD_Constraints))"/>
					<rim:Association id="{$resourceConstraintsAssociationId}" associationType="{$resourceConstraintsAssociationType}" sourceObject="{$resourceConstraints-id}" targetObject="{$rightsId}"/>
				</xsl:if>
			</xsl:if>
			<xsl:if test="gmd:MD_LegalConstraints/*">
				<xsl:variable name="legalConstraintsId" select="concat( $urnCimLegalConstraintsExtrinsicObjectIDPrefix, generate-id(gmd:MD_LegalConstraints))"/>
				<rim:ExtrinsicObject id="{$legalConstraintsId}" objectType="{$legalConstraintsObjectType}">
					<xsl:if test="gmd:MD_LegalConstraints/gmd:otherConstraints[1]/gco:CharacterString">
						<rim:Slot name="{$rightsSlotName}" slotType="{$stringSlotType}">
							<rim:ValueList>
								<rim:Value>
									<xsl:value-of select="gmd:MD_LegalConstraints/gmd:otherConstraints[1]/gco:CharacterString"/>
								</rim:Value>
							</rim:ValueList>
						</rim:Slot>
					</xsl:if>
					<xsl:if test="gmd:MD_LegalConstraints/gmd:useLimitation[1]/gco:CharacterString">
						<rim:Description>
							<rim:LocalizedString xml:lang="{$metadataLanguage}" charset="{$metadataCharacterSet}" value="{gmd:MD_LegalConstraints/gmd:useLimitation[1]/gco:CharacterString}"/>
						</rim:Description>
					</xsl:if>
					<xsl:if test="gmd:MD_LegalConstraints/gmd:accessConstraints">
						<xsl:variable name="position" select="gmd:MD_LegalConstraints/gmd:accessConstraints"/>
						<xsl:variable name="restrictionCodeClassificationId" select="concat( $urnCimRestrictionCodeClassificationIDPrefix, generate-id($position))"/>
						<xsl:variable name="restrictionCode" select="gmd:MD_LegalConstraints/gmd:accessConstraints[1]/gmd:MD_RestrictionCode/@codeListValue"/>
						<rim:Classification id="{$restrictionCodeClassificationId}" classifiedObject="{$legalConstraintsId}" classificationNode="{concat($restrictionCodeClassificationSchemePrefix, $restrictionCode)}">
							<xsl:variable name="restrictionTypeClassificationId" select="concat($urnCimRestrictionTypeClassificationIDPrefix, generate-id($position))"/>
							<rim:Classification id="{$restrictionTypeClassificationId}" classifiedObject="{$restrictionCodeClassificationId}" classificationNode="{concat( $restrictionTypeClassificationSchemePrefix, 'access')}"/>
						</rim:Classification>
					</xsl:if>
					<xsl:if test="gmd:MD_LegalConstraints/gmd:useConstraints">
						<xsl:variable name="position" select="gmd:MD_LegalConstraints/gmd:useConstraints"/>
						<xsl:variable name="restrictionCodeClassificationId" select="concat( $urnCimRestrictionCodeClassificationIDPrefix, generate-id($position))"/>
						<xsl:variable name="restrictionCode" select="gmd:MD_LegalConstraints/gmd:useConstraints[1]/gmd:MD_RestrictionCode/@codeListValue"/>
						<rim:Classification id="{$restrictionCodeClassificationId}" classifiedObject="{$legalConstraintsId}" classificationNode="{concat($restrictionCodeClassificationSchemePrefix, $restrictionCode)}">
							<xsl:variable name="restrictionTypeClassificationId" select="concat($urnCimRestrictionTypeClassificationIDPrefix, generate-id($position))"/>
							<rim:Classification id="{$restrictionTypeClassificationId}" classifiedObject="{$restrictionCodeClassificationId}" classificationNode="{concat( $restrictionTypeClassificationSchemePrefix, 'use')}"/>
						</rim:Classification>
					</xsl:if>
				</rim:ExtrinsicObject>
				<xsl:variable name="resourceConstraintsAssociationId" select="concat( $urnCimResourceConstraintsAssociationIDPrefix, generate-id(gmd:MD_LegalConstraints))"/>
				<rim:Association id="{$resourceConstraintsAssociationId}" associationType="{$resourceConstraintsAssociationType}" sourceObject="{$resourceConstraints-id}" targetObject="{$legalConstraintsId}"/>
			</xsl:if>
			<xsl:if test="gmd:MD_SecurityConstraints/*">
				<xsl:variable name="securityConstraintsId" select="concat( $urnCimSecurityConstraintsExtrinsicObjectIDPrefix, generate-id(gmd:MD_SecurityConstraints) )"/>
				<rim:ExtrinsicObject id="{$securityConstraintsId}" objectType="{$securityConstraintsObjectType}">
					<xsl:if test="gmd:MD_SecurityConstraints/gmd:useLimitation[1]/gco:CharacterString">
						<rim:Description>
							<rim:LocalizedString xml:lang="{$metadataLanguage}" charset="{$metadataCharacterSet}" value="{gmd:MD_SecurityConstraints/gmd:useLimitation[1]/gco:CharacterString}"/>
						</rim:Description>
					</xsl:if>
					<xsl:variable name="classificationCodeClassificationId" select="concat( $urnClassificationCodeClassificationIDPrefix, generate-id(gmd:MD_SecurityConstraints))"/>
					<xsl:variable name="classificationCode" select="gmd:MD_SecurityConstraints/gmd:classification/gmd:MD_ClassificationCode/@codeListValue"/>
					<rim:Classification id="{$classificationCodeClassificationId}" classifiedObject="{$securityConstraintsId}" classificationNode="{concat( $classificationCodeClassificationSchemePrefix, $classificationCode)}"/>
				</rim:ExtrinsicObject>
				<xsl:variable name="resourceConstraintsAssociationId" select="concat( $urnCimResourceConstraintsAssociationIDPrefix, generate-id(gmd:MD_SecurityConstraints))"/>
				<rim:Association id="{$resourceConstraintsAssociationId}" associationType="{$resourceConstraintsAssociationType}" sourceObject="{$resourceConstraints-id}" targetObject="{$securityConstraintsId}"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!--
			Getting Type of Resource Metadata Template
	 -->
	<xsl:template name="specifyTypeOfResourceMetadata">
		<xsl:variable name="mainObjectType">
			<xsl:choose>
				<xsl:when test="srv:SV_ServiceIdentification">
					<xsl:value-of select="$serviceMetadataObjectType"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$dataMetadataObjectType"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="hierarchyLevelCode" select="/*/gmd:hierarchyLevel/gmd:MD_ScopeCode/@codeListValue"/>
		<xsl:variable name="objectTypeValue">
			<xsl:choose>
				<!-- DataMetadata,  ElementaryDataset, DatasetCollection, ServiceMetadata and Application-->
				<xsl:when test="$hierarchyLevelCode = 'series' ">
					<xsl:value-of select="$datasetCollectionObjectType"/>
				</xsl:when>
				<xsl:when test="$hierarchyLevelCode = 'dataset'">
					<xsl:value-of select="$elementaryDatasetObjectType"/>
				</xsl:when>
				<xsl:when test="$hierarchyLevelCode = 'service' ">
					<xsl:value-of select="$serviceMetadataObjectType"/>
				</xsl:when>
				<xsl:when test="$hierarchyLevelCode = 'application' ">
					<xsl:value-of select="$applicationObjectType"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$mainObjectType"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:attribute name="objectType"><xsl:value-of select="$objectTypeValue"/></xsl:attribute>
	</xsl:template>
	<!-- 
			Service Metadata Template
	-->
	<xsl:template name="servicemetadata">
		<xsl:param name="servicemetadata-id"/>
		<xsl:param name="servicesClassificationNodes"/>
		<!-- the following call creates the Envelope,Coverage and TemporalBegin/End slots -->
		<xsl:call-template name="extent"/>
		<rim:Name>
			<xsl:variable name="name" select="srv:SV_ServiceIdentification/gmd:citation/gmd:CI_Citation/gmd:title/gco:CharacterString"/>
			<rim:LocalizedString value="{$name}"/>
		</rim:Name>
		<rim:Description>
			<xsl:variable name="abstract" select="srv:SV_ServiceIdentification/gmd:abstract/gco:CharacterString"/>
			<rim:LocalizedString value="{$abstract}"/>
		</rim:Description>
		<xsl:choose>
			<xsl:when test="$servicesClassificationNodes/*">
				<xsl:call-template name="createServicesClassificationId">
					<xsl:with-param name="classificationNodes" select="$servicesClassificationNodes"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="servicesClassificationId" select="concat( $urnCimServicesClassificationIDPrefix, generate-id(.))"/>
				<xsl:variable name="urnISOServices" select=" 'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services' "/>
				<xsl:variable name="serviceType" select="srv:SV_ServiceIdentification/srv:serviceType/gco:LocalName"/>
				<rim:Classification id="{$servicesClassificationId}" classifiedObject="{$resourceMetadataId}" classificationNode="{concat($urnISOServices, ':', $serviceType)}"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:variable name="couplingTypeClassificationId" select="concat( $urnCimCouplingTypeClassificationIDPrefix, generate-id(.))"/>
		<xsl:variable name="couplingType" select="srv:SV_ServiceIdentification/srv:couplingType/srv:SV_CouplingType/@codeListValue"/>
		<xsl:choose>
			<xsl:when test="$couplingType='loose'">
				<rim:Classification id="{$couplingTypeClassificationId}" classifiedObject="{$servicemetadata-id}" classificationNode="{concat( $couplingTypeClassificationSchemePrefix, $couplingType)}"/>
			</xsl:when>
			<xsl:when test="$couplingType='mixed'">
				<rim:Classification id="{$couplingTypeClassificationId}" classifiedObject="{$servicemetadata-id}" classificationNode="{concat( $couplingTypeClassificationSchemePrefix, $couplingType)}"/>
			</xsl:when>
			<xsl:when test="$couplingType='tight'">
				<rim:Classification id="{$couplingTypeClassificationId}" classifiedObject="{$servicemetadata-id}" classificationNode="{concat( $couplingTypeClassificationSchemePrefix, $couplingType)}"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- 
			Data Metadata Template
	-->
	<xsl:template name="datametadata">
		<xsl:param name="dataset-id"/>
		<xsl:variable name="resolution" select="gmd:MD_DataIdentification/gmd:spatialResolution/gmd:MD_Resolution"/>
		<xsl:if test="$resolution/gmd:equivalentScale">
			<rim:Slot name="{$scaleDenominatorSlotName}" slotType="{$integerSlotType}">
				<rim:ValueList>
					<xsl:for-each select="$resolution/gmd:equivalentScale/gmd:MD_RepresentativeFraction/gmd:denominator/gco:Integer">
						<rim:Value>
							<xsl:value-of select="."/>
						</rim:Value>
					</xsl:for-each>
				</rim:ValueList>
			</rim:Slot>
		</xsl:if>
		<xsl:if test="$resolution/gmd:distance">
			<!--   MeasureType NOT SUPPORTED
         <rim:Slot name="{$resolutionSlotName}" slotType="{$measureSlotType}">
            <wrs:ValueList>
				<xsl:for-each select="$resolution/gmd:distance">
				  <wrs:AnyValue>
				     <gml:distance uom="{gco:Distance/@uom}">
				        <xsl:value-of select="gco:Distance"/>
				     </gml:distance>
				  </wrs:AnyValue>
                </xsl:for-each>
            </wrs:ValueList> 
            </rim:Slot>
			-->
			<rim:Slot name="{$resolutionSlotName}" slotType="{$doubleSlotType}">
				<rim:ValueList>
					<xsl:for-each select="$resolution/gmd:distance">
						<rim:Value>
							<xsl:value-of select="gco:Distance"/>
						</rim:Value>
					</xsl:for-each>
				</rim:ValueList>
			</rim:Slot>
		</xsl:if>
		<xsl:if test="gmd:MD_DataIdentification/gmd:supplementalInformation">
			<xsl:variable name="supplementalInformation">
				<xsl:choose>
					<xsl:when test="gmd:MD_DataIdentification/gmd:supplementalInformation/*/@codeListValue">
						<xsl:value-of select="gmd:MD_DataIdentification/gmd:supplementalInformation/*/@codeListValue"/>
					</xsl:when>
					<xsl:when test="gmd:MD_DataIdentification/gmd:supplementalInformation/gco:CharacterString">
						<xsl:value-of select="gmd:MD_DataIdentification/gmd:supplementalInformation/gco:CharacterString"/>
					</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<!-- International string type NOT SUPPORTED
            <rim:Slot name="{$supplementalInformationSlotName}" slotType="{$internationalStringSlotType}">
              <wrs:ValueList>
				<wrs:AnyValue>
					<rim:InternationalString>
					  <rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="{$supplementalInformation}"/>
					</rim:InternationalString>
			  </wrs:AnyValue>
			</wrs:ValueList>
          </rim:Slot> -->
			<rim:Slot name="{$supplementalInformationSlotName}" slotType="{$stringSlotType}">
				<rim:ValueList>
					<rim:Value>
						<xsl:value-of select="$supplementalInformation"/>
					</rim:Value>
				</rim:ValueList>
			</rim:Slot>
		</xsl:if>
		<!-- the following call creates the Envelope,Coverage and TemporalBegin/End slots -->
		<xsl:call-template name="extent"/>
		<rim:Name>
			<xsl:variable name="name" select="gmd:MD_DataIdentification/gmd:citation/gmd:CI_Citation/gmd:title/gco:CharacterString"/>
			<rim:LocalizedString value="{$name}"/>
		</rim:Name>
		<rim:Description>
			<xsl:variable name="abstract" select="gmd:MD_DataIdentification/gmd:abstract/gco:CharacterString"/>
			<rim:LocalizedString value="{$abstract}"/>
		</rim:Description>
		<xsl:for-each select="gmd:MD_DataIdentification/gmd:spatialRepresentationType">
			<xsl:variable name="spatialRepresentationTypeClassificationId" select="concat( $urnSpatialRepresentationClassificationIDPrefix, generate-id(.))"/>
			<xsl:variable name="spatialRepresentationType" select="gmd:MD_SpatialRepresentationTypeCode/@codeListValue"/>
			<!-- TODO what is the ID of the spatialRepresentationType classificationNode ? -->
			<rim:Classification id="{$spatialRepresentationTypeClassificationId}" classifiedObject="{$dataset-id}" classificationNode="{concat( $spatialRepresentationClassificationSchemePrefix , $spatialRepresentationType )}"/>
		</xsl:for-each>
		<xsl:for-each select="gmd:MD_DataIdentification/gmd:characterSet">
			<xsl:variable name="charset" select="gmd:MD_CharacterSetCode/@codeListValue"/>
			<xsl:call-template name="classification-characterSet">
				<xsl:with-param name="characterSet" select="$charset"/>
				<xsl:with-param name="classifiedObjectId" select="$dataset-id"/>
			</xsl:call-template>
		</xsl:for-each>
		<xsl:for-each select="gmd:MD_DataIdentification/gmd:topicCategory">
			<xsl:variable name="topicCategoryClassificationId" select="concat( $urnTopicCategoryClassificationIDPrefix, generate-id(.) )"/>
			<xsl:variable name="topicCategory" select="gmd:MD_TopicCategoryCode"/>
			<!-- TODO what is the ID of the topicCategory classificationNode ?  {$topicCategory} -->
			<rim:Classification id="{$topicCategoryClassificationId}" classifiedObject="{$dataset-id}" classificationNode="{concat( $topicCategoryClassificationSchemePrefix, $topicCategory )}"/>
		</xsl:for-each>
	</xsl:template>
	<!-- 
			Reference System Info Template
	-->
	<xsl:template name="referenceSystemInfo">
		<xsl:param name="referenceSystemInfo_id"/>
		<xsl:variable name="identifiedItemId" select="concat( $urnCimIdentifiedItemExtrinsicObjectIDPrefix, generate-id(.) )"/>
		<xsl:variable name="identifiedItemCodeSpaceId" select="concat( $urnCimIdentifiedItemExtrinsicObjectIDPrefix, 'CodeSpace:', generate-id(.) )"/>
		<rim:ExtrinsicObject id="{$identifiedItemId}" objectType="{$identifiedItemObjectType}">
			<rim:Name>
				<xsl:variable name="name" select="gmd:MD_ReferenceSystem/gmd:referenceSystemIdentifier/gmd:RS_Identifier/gmd:code/gco:CharacterString"/>
				<rim:LocalizedString value="{$name}"/>
			</rim:Name>
		</rim:ExtrinsicObject>
		<xsl:variable name="codeSpace" select="gmd:MD_ReferenceSystem/gmd:referenceSystemIdentifier/gmd:RS_Identifier/gmd:codeSpace/gco:CharacterString"/>
		<rim:ExtrinsicObject id="{$identifiedItemCodeSpaceId}" objectType="{$identifiedItemObjectType}">
			<rim:Name>
				<rim:LocalizedString value="{$codeSpace}"/>
			</rim:Name>
		</rim:ExtrinsicObject>
		<xsl:variable name="codeSpaceAssociationId" select="concat( $urnCimCodeSpaceAssociationIDPrefix, generate-id(.) )"/>
		<rim:Association id="{$codeSpaceAssociationId}" associationType="{$codeSpaceAssociationType}" sourceObject="{$identifiedItemId}" targetObject="{$identifiedItemCodeSpaceId}"/>
		<xsl:variable name="resourceReferenceSystemAssociationId" select="concat( $urnCimResourceReferenceSystemAssociationIDPrefix, generate-id(.) )"/>
		<rim:Association id="{$resourceReferenceSystemAssociationId}" associationType="{$resourceReferenceSystemAssociationType}" sourceObject="{$referenceSystemInfo_id}" targetObject="{$identifiedItemId}"/>
		<xsl:if test="gmd:MD_ReferenceSystem/gmd:referenceSystemIdentifier/gmd:RS_Identifier/gmd:authority">
			<xsl:variable name="citedItemId" select="concat( $urnCimCitedItemExtrinsicObjectIDPrefix, generate-id(.))"/>
			<xsl:call-template name="CitationInformation">
				<xsl:with-param name="citationInformationNode" select="gmd:MD_ReferenceSystem/gmd:referenceSystemIdentifier/gmd:RS_Identifier/gmd:authority/gmd:CI_Citation"/>
				<xsl:with-param name="citedItemEOId" select="$citedItemId"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<!-- 
			Slot date with type selection Template
	-->
	<xsl:template name="slot-date-with-type">
		<xsl:param name="date"/>
		<xsl:param name="dateType"/>
		<xsl:if test="$date/gco:Date">
			<xsl:if test="$dateType/gmd:CI_DateTypeCode/@codeListValue = 'creation'">
				<xsl:call-template name="slot-typed-date">
					<xsl:with-param name="date" select="$date/gco:Date"/>
					<xsl:with-param name="slotname">
						<xsl:value-of select="$createdSlotName"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="$dateType/gmd:CI_DateTypeCode/@codeListValue = 'revision'">
				<xsl:call-template name="slot-typed-date">
					<xsl:with-param name="date" select="$date/gco:Date"/>
					<xsl:with-param name="slotname">
						<xsl:value-of select="$modifiedSlotName"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="$dateType/gmd:CI_DateTypeCode/@codeListValue = 'publication'">
				<xsl:call-template name="slot-typed-date">
					<xsl:with-param name="date" select="$date/gco:Date"/>
					<xsl:with-param name="slotname">
						<xsl:value-of select="$issuedSlotName"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
		<xsl:if test="$date/gco:DateTime">
			<xsl:if test="$dateType/gmd:CI_DateTypeCode/@codeListValue = 'creation'">
				<xsl:call-template name="slot-typed-date">
					<xsl:with-param name="date" select="$date/gco:DateTime"/>
					<xsl:with-param name="slotname">
						<xsl:value-of select="$createdSlotName"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="$dateType/gmd:CI_DateTypeCode/@codeListValue = 'revision'">
				<xsl:call-template name="slot-typed-date">
					<xsl:with-param name="date" select="$date/gco:DateTime"/>
					<xsl:with-param name="slotname">
						<xsl:value-of select="$modifiedSlotName"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="$dateType/gmd:CI_DateTypeCode/@codeListValue = 'publication'">
				<xsl:call-template name="slot-typed-date">
					<xsl:with-param name="date" select="$date/gco:DateTime"/>
					<xsl:with-param name="slotname">
						<xsl:value-of select="$issuedSlotName"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<!-- 
			Slot date with fixed type Template
	-->
	<xsl:template name="slot-typed-date">
		<xsl:param name="date"/>
		<xsl:param name="slotname"/>
		<rim:Slot name="{$slotname}" slotType="{$dateSlotType}">
			<rim:ValueList>
				<rim:Value>
					<xsl:value-of select="$date"/>
				</rim:Value>
			</rim:ValueList>
		</rim:Slot>
	</xsl:template>
	<!-- 
			Slot type Template
	-->
	<xsl:template name="slot-hierarchyLevelName">
		<xsl:param name="hierarchyLevelName"/>
		<xsl:param name="charset"/>
		<xsl:param name="language"/>
		<!-- International slot type NOT SUPPORTED
        <rim:Slot name="{$typeSlotName}" slotType="{$internationalStringSlotType}">
             <wrs:ValueList>
				<xsl:for-each select="$hierarchyLevelName/gco:CharacterString">
					<wrs:AnyValue>
						<rim:InternationalString>
						  <rim:LocalizedString charset="{$charset}" xml:lang="{$language}" value="{.}"/>
						</rim:InternationalString>
				  </wrs:AnyValue>
				</xsl:for-each>
			</wrs:ValueList>
        </rim:Slot> -->
		<rim:Slot name="{$typeSlotName}" slotType="{$stringSlotType}">
			<rim:ValueList>
				<xsl:for-each select="$hierarchyLevelName/gco:CharacterString">
					<rim:Value>
						<xsl:value-of select="."/>
					</rim:Value>
				</xsl:for-each>
			</rim:ValueList>
		</rim:Slot>
	</xsl:template>
	<!-- 
			Slot title Template
	-->
	<xsl:template name="slot-title">
		<xsl:param name="title"/>
		<xsl:param name="characterSet"/>
		<xsl:param name="language"/>
		<!-- International slot type NOT SUPPORTED
        <rim:Slot name="{$titleSlotName}" slotType="{$internationalStringSlotType}">
			<wrs:ValueList>
				<wrs:AnyValue>
					<rim:InternationalString>
						<rim:LocalizedString charset="{$characterSet}" xml:lang="{$language}" value="{$title}"/>
					</rim:InternationalString>
				</wrs:AnyValue>
			</wrs:ValueList>
		</rim:Slot> -->
		<rim:Slot name="{$typeSlotName}" slotType="{$stringSlotType}">
			<rim:ValueList>
				<rim:Value>
					<xsl:value-of select="$title"/>
				</rim:Value>
			</rim:ValueList>
		</rim:Slot>
	</xsl:template>
	<!-- 
			Citation Information Template
	-->
	<xsl:template name="CitationInformation">
		<xsl:param name="citationInformationNode"/>
		<xsl:param name="citedItemEOId"/>
		<xsl:variable name="citedItemId" select="$citedItemEOId"/>
		<rim:ExtrinsicObject id="{$citedItemId}" objectType="{$citedItemObjectType}">
			<xsl:if test="$citationInformationNode/gmd:alternateTitle">
				<xsl:call-template name="slot-title">
					<xsl:with-param name="title" select="$citationInformationNode/gmd:alternateTitle/gco:CharacterString"/>
					<xsl:with-param name="characterSet" select="$metadataCharacterSet"/>
					<xsl:with-param name="language" select="$metadataLanguage"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:for-each select="$citationInformationNode/gmd:date">
				<xsl:call-template name="slot-date-with-type">
					<xsl:with-param name="date" select="./gmd:CI_Date/gmd:date"/>
					<xsl:with-param name="dateType" select="./gmd:CI_Date/gmd:dateType"/>
				</xsl:call-template>
			</xsl:for-each>
			<xsl:for-each select="$citationInformationNode/gmd:identifier">
				<xsl:variable name="codespace">
					<xsl:choose>
						<xsl:when test="*/gmd:codeSpace/gco:CharacterString">
							<xsl:value-of select="*/gmd:codeSpace/gco:CharacterString"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="*/gmd:codeSpace/child::*/@codeListValue"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:variable name="codevalue">
					<xsl:choose>
						<xsl:when test="*/gmd:code/gco:CharacterString">
							<xsl:value-of select="*/gmd:code/gco:CharacterString"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="*/gmd:code/child::*/@codeListValue"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:if test="$codespace">
					<xsl:variable name="externalIdentifierId" select="concat( $urnCimExternalIdentifierIDPrefix, generate-id(.))"/>
					<rim:ExternalIdentifier id="{$externalIdentifierId}" registryObject="{$citedItemId}" identificationScheme="{$codespace}" value="{$codevalue}"/>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="$citationInformationNode/gmd:title/gmx:Anchor/@xlink:href">
				<rim:Slot name="{$sourceSlotName}" slotType="{$uriSlotType}">
					<rim:ValueList>
						<rim:Value>
							<xsl:value-of select="$citationInformationNode/gmd:title/gmx:Anchor/@xlink:href"/>
						</rim:Value>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
			<rim:Name>
				<xsl:variable name="nameK">
					<xsl:call-template name="getNodeContent">
						<xsl:with-param name="node" select="$citationInformationNode/gmd:title"/>
					</xsl:call-template>
				</xsl:variable>
				<rim:LocalizedString value="{$nameK}"/>
			</rim:Name>
		</rim:ExtrinsicObject>
		<xsl:for-each select="$citationInformationNode/gmd:citedResponsibleParty">
			<xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
			<xsl:call-template name="organization">
				<xsl:with-param name="responsiblePartyNode" select="."/>
				<xsl:with-param name="organizationId" select="$organizationId"/>
			</xsl:call-template>
			<xsl:variable name="citedResponsiblePartyAssociationId" select="concat( $urnCimCitedResponsiblePartyAssociationIDPrefix , generate-id(.) )"/>
			<rim:Association id="{$citedResponsiblePartyAssociationId}" associationType="{$citedResponsiblePartyAssociationType}" sourceObject="{$citedItemId}" targetObject="{$organizationId}"/>
			<xsl:variable name="role" select="./gmd:CI_ResponsibleParty/gmd:role/gmd:CI_RoleCode/@codeListValue"/>
			<xsl:variable name="citedResponsiblePartyAssociationClassificationId" select="concat( $urnCimCitedResponsiblePartyAssociationIDPrefix, generate-id(.))"/>
			<xsl:if test="$role = 'pointOfContact'">
				<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
			</xsl:if>
			<xsl:if test="$role = 'author'">
				<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
			</xsl:if>
			<xsl:if test="$role = 'publisher'">
				<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
			</xsl:if>
			<xsl:if test="$role = 'originator'">
				<rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
			</xsl:if>
			<xsl:if test="not(empty(gmd:CI_ResponsibleParty/gmd:individualName/gco:CharacterString))">
				<xsl:variable name="personName" select="gmd:CI_ResponsibleParty/gmd:individualName/gco:CharacterString"/>
				<rim:Person id="{concat($urnCimPersonIDPrefix, ':', generate-id())}" objectType="{$personObjectType}">
					<rim:PersonName lastName="{$personName}"/>
				</rim:Person>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!-- 
		The following template encode the value of the node (expressed either as cahracter string or anchor) so that it can be used in a URI
    -->
	<xsl:template name="getIdByNodeContent">
		<xsl:param name="node"/>
		<xsl:choose>
			<xsl:when test="$node/gco:CharacterString">
				<!-- <xsl:value-of select="translate(translate( $node/gco:CharacterString,' ','_' ), ',','_')"/> -->
				<!-- <xsl:value-of select="translate( $node/gco:CharacterString,' %/?#','' )"/> -->
				<xsl:value-of select="encode-for-uri($node/gco:CharacterString)"/>
			</xsl:when>
			<xsl:when test="$node/gmx:Anchor">
				<!-- <xsl:value-of select="translate(translate( $node/gmx:Anchor,' ',':' ), ',','_')"/> -->
				<!-- <xsl:value-of select="translate( $node/gmx:Anchor,' %/?#','' )"/> -->
				<xsl:value-of select="encode-for-uri($node/gmx:Anchor)"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- 
		The following template retrieve the value of the node expressed either as cahracter string or anchor 
	-->
	<xsl:template name="getNodeContent">
		<xsl:param name="node"/>
		<xsl:choose>
			<xsl:when test="$node/gco:CharacterString">
				<xsl:value-of select="$node/gco:CharacterString"/>
			</xsl:when>
			<xsl:when test="$node/gmx:Anchor">
				<xsl:value-of select="$node/gmx:Anchor"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- 
					Keywords classification nodes template
	-->
	<xsl:template name="createClassificationNodes">
		<xsl:param name="nodeDescriptiveKeywords"/>
		<xsl:param name="parentClassificationScheme"/>
		<xsl:for-each select="$nodeDescriptiveKeywords/gmd:MD_Keywords/gmd:keyword">
			<xsl:variable name="keywordId">
				<xsl:call-template name="getIdByNodeContent">
					<xsl:with-param name="node" select="."/>
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="keywordName">
				<xsl:call-template name="getNodeContent">
					<xsl:with-param name="node" select="."/>
				</xsl:call-template>
			</xsl:variable>
			<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="{$parentClassificationScheme}" code="{$keywordId}" id="{concat( $parentClassificationScheme, ':', $keywordId )}">
				<xsl:if test="gmx:Anchor">
					<rim:Slot name="{$sourceSlotName}" slotType="{$uriSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="gmx:Anchor/@xlink:href"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<rim:Name>
					<rim:LocalizedString xml:lang="en" value="{$keywordName}"/>
				</rim:Name>
			</rim:ClassificationNode>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="createDescriptiveKeywordsClassificationIdByNodes">
		<xsl:param name="classificationNodes"/>
		<xsl:for-each select="$classificationNodes//rim:ClassificationNode">
			<xsl:variable name="keywordSchemeClassificationId" select="concat(@parent, ':', generate-id(.) )"/>
			<rim:Classification id="{$keywordSchemeClassificationId}" classifiedObject="{$resourceMetadataId}" classificationNode="{@id}"/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="createDescriptiveKeywordsClassificationIdBySchemes">
		<xsl:param name="classificationSchemes"/>
		<xsl:for-each select="$classificationSchemes//rim:ClassificationNode">
			<xsl:variable name="keywordSchemeClassificationId" select="concat( @parent, ':' , generate-id(.) )"/>
			<rim:Classification id="{$keywordSchemeClassificationId}" classifiedObject="{$resourceMetadataId}" classificationNode="{./@id}"/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="createServicesClassificationId">
		<xsl:param name="classificationNodes"/>
		<xsl:for-each select="$classificationNodes//@id">
			<xsl:variable name="servicesClassificationId" select="concat( $urnCimServicesClassificationIDPrefix, generate-id(.))"/>
			<rim:Classification id="{$servicesClassificationId}" classifiedObject="{$resourceMetadataId}" classificationNode="{.}"/>
		</xsl:for-each>
	</xsl:template>
	<!-- 
		Handling of Graphic Overview Info
	-->
	<xsl:template name="createGraphicOverview">
		<xsl:for-each select="/*/gmd:identificationInfo/*/gmd:graphicOverview/gmd:MD_BrowseGraphic">
			<xsl:variable name="imageId" select="concat($urnCimImageExtrinsicObjectIDPrefix, generate-id(.))"/>
			<rim:ExtrinsicObject id="{$imageId}" objectType="{$imageObjectType}">
				<rim:Name>
					<xsl:variable name="name" select="gmd:fileName/gco:CharacterString"/>
					<rim:LocalizedString value="{$name}"/>
				</rim:Name>
			</rim:ExtrinsicObject>
			<xsl:variable name="graphicOverviewAssociationId" select="concat( $urnCimGraphicOverviewAssociationIDPrefix , generate-id(.) )"/>
			<rim:Association id="{$graphicOverviewAssociationId}" associationType="{$graphicOverviewAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$imageId}"/>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
