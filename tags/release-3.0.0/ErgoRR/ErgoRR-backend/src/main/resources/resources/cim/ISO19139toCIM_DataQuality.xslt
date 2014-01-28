<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gmd="http://www.isotc211.org/2005/gmd" xmlns:gmi="http://www.isotc211.org/2005/gmi" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:gmx="http://www.isotc211.org/2005/gmx" xmlns:srv="http://www.isotc211.org/2005/srv" xmlns:gml32="http://www.opengis.net/gml/3.2" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink">
	<!-- <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/> -->
	<!-- 
		Registration of Quality Information
	-->
	<xsl:template name="createDataQuality">
		<xsl:for-each select="/*/gmd:dataQualityInfo/gmd:DQ_DataQuality">
			<!-- Creation of the DataQuality extrinsic object -->
			<xsl:variable name="dataQualityID" select="concat ($urnCimDataQualityExtrinsicObjectIDPrefix, generate-id(.))"/>
			<rim:ExtrinsicObject id="{$dataQualityID}" objectType="{$dataQualityObjectType}">
				<rim:Slot name="{$scopeLevelSlotName}" slotType="{$stringSlotType}">
					<rim:ValueList>
						<rim:Value>
							<xsl:value-of select="gmd:scope/gmd:DQ_Scope/gmd:level/gmd:MD_ScopeCode/@codeListValue"/>
						</rim:Value>
					</rim:ValueList>
				</rim:Slot>
				<xsl:variable name="scopeDescription" select="gmd:scope/gmd:DQ_Scope/gmd:levelDescription/gmd:MD_ScopeDescription/gmd:dataset/gco:CharacterString"/>
				<xsl:if test="not(empty($scopeDescription))">
					<!--  International string type NOT SUPPORTED
					<rim:Slot name="{$scopeDescriptionSlotName}" slotType="{$internationalStringSlotType}">
						<wrs:ValueList>
							<wrs:AnyValue>
								<rim:InternationalString>
									<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" 
										value="{$scopeDescription}"/>
								</rim:InternationalString>
							</wrs:AnyValue>
						</wrs:ValueList>
					</rim:Slot> -->
					<rim:Slot name="{$scopeDescriptionSlotName}" slotType="{$stringSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$scopeDescription"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:if test="gmd:scope/gmd:DQ_Scope/gmd:extent/gmd:EX_Extent/gmd:geographicElement">
					<rim:Slot name="{$spatialSlotName}" slotType="{$envelopeSlotType}">
						<wrs:ValueList>
							<xsl:for-each select="gmd:scope/gmd:DQ_Scope/gmd:extent/gmd:EX_Extent/gmd:geographicElement">
								<wrs:AnyValue>
									<gml:Envelope srsName="urn:ogc:def:crs:EPSG:4326">
										<gml:lowerCorner>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:southBoundLatitude/gco:Decimal"/>
											<xsl:text> </xsl:text>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:westBoundLongitude/gco:Decimal"/>
										</gml:lowerCorner>
										<gml:upperCorner>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:northBoundLatitude/gco:Decimal"/>
											<xsl:text> </xsl:text>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:eastBoundLongitude/gco:Decimal"/>
										</gml:upperCorner>
									</gml:Envelope>
								</wrs:AnyValue>
							</xsl:for-each>
						</wrs:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:variable name="temporalBegin" select="gmd:scope/gmd:DQ_Scope/gmd:extent/gmd:EX_Extent/gmd:temporalElement[1]/gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='begin']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
				<xsl:if test="not(empty(temporalBegin))">
					<rim:Slot name="{$temporalBeginSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$temporalBegin"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:variable name="temporalEnd" select="gmd:scope/gmd:DQ_Scope/gmd:extent/gmd:EX_Extent/gmd:temporalElement[1]/gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='end']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
				<xsl:if test="not(empty(temporalEnd))">
					<rim:Slot name="{$temporalEndSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$temporalEnd"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
			</rim:ExtrinsicObject>
			<!-- Creation of the DataQualityInfo association -->
			<xsl:variable name="dataQualityInfoId" select="concat($urnCimDataQualityInfoAssociationIDPrefix, generate-id(.))"/>
			<rim:Association id="{$dataQualityInfoId}" associationType="{$dataQualityInfoAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$dataQualityID}"/>
			<!-- Creation of the Lineage object-->
			<xsl:variable name="lineageStatement" select="gmd:lineage/gmd:LI_Lineage/gmd:statement/gco:CharacterString"/>
			<xsl:if test="not(empty($lineageStatement))">
				<xsl:variable name="lineageId" select="concat($urnCimLineageExtrinsicObjectIDPrefix, generate-id(.))"/>
				<rim:ExtrinsicObject id="{$lineageId}" objectType="{$lineageObjectType}">
					<rim:Description>
						<rim:LocalizedString xml:lang="{$metadataLanguage}" charset="{$metadataCharacterSet}" value="{$lineageStatement}"/>
					</rim:Description>
				</rim:ExtrinsicObject>
				<!-- Creation of the DataQualityLineage association -->
				<xsl:variable name="dataQualityLineageId" select="concat($urnCimDataQualityLineageAssociationIDPrefix, generate-id(.))"/>
				<rim:Association id="{$dataQualityLineageId}" associationType="{$dataQualityLineageAssociationType}" sourceObject="{$dataQualityID}" targetObject="{$lineageId}"/>
				<!-- Creation of the ProcessStep objects -->
				<xsl:for-each select="gmd:lineage/gmd:LI_Lineage/gmd:processStep/gmd:LI_ProcessStep">
					<xsl:variable name="processStepDescription" select="gmd:description/gco:CharacterString"/>
					<xsl:if test="not(empty($processStepDescription))">
						<xsl:variable name="processStepId" select="concat($urnCimProcessStepExtrinsicObjectIDPrefix, generate-id(.))"/>
						<!-- Creation of the ProcessStep extrinsic object -->
						<rim:ExtrinsicObject id="{$processStepId}" objectType="{$processStepObjectType}">
							<xsl:if test="gmd:rationale/gco:CharacterString">
								<!-- International string type NOT SUPPORTED
								<rim:Slot name="{$rationaleSlotName}" slotType="{$internationalStringSlotType}">
									<wrs:ValueList>
										<wrs:AnyValue>
											<rim:InternationalString>
												<rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}" value="gmd:rationale/gco:CharacterString"/>
											</rim:InternationalString>
										</wrs:AnyValue>
									</wrs:ValueList>
								</rim:Slot> -->
								<rim:Slot name="{$rationaleSlotName}" slotType="{$stringSlotType}">
									<rim:ValueList>
										<rim:Value>
											<xsl:value-of select="gmd:rationale/gco:CharacterString"/>
										</rim:Value>
									</rim:ValueList>
								</rim:Slot>
							</xsl:if>
							<xsl:if test="gmd:dateTime/gco:DateTime">
								<rim:Slot name="{$dateSlotName}" slotType="{$dateTimeSlotType}">
									<rim:ValueList>
										<rim:Value>
											<xsl:value-of select="gmd:dateTime/gco:DateTime"/>
										</rim:Value>
									</rim:ValueList>
								</rim:Slot>
							</xsl:if>
							<rim:Description>
								<rim:LocalizedString xml:lang="{$metadataLanguage}" charset="{$metadataCharacterSet}" value="{$processStepDescription}"/>
							</rim:Description>
						</rim:ExtrinsicObject>
						<!-- Creation of the LineageProcessStep association -->
						<xsl:variable name="lineageProcessStepId" select="concat($urnCimLineageProcessStepAssociationIDPrefix, generate-id(.))"/>
						<rim:Association id="{$lineageProcessStepId}" associationType="{$lineageProcessStepAssociationType}" sourceObject="{$lineageId}" targetObject="{$processStepId}"/>
						<xsl:for-each select="gmd:processor">
							<!-- Creation of the Organization object-->
							<xsl:if test="not(empty(gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString))">
								<xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
								<xsl:call-template name="organization">
									<xsl:with-param name="responsiblePartyNode" select="."/>
									<xsl:with-param name="organizationId" select="$organizationId"/>
								</xsl:call-template>
								<!-- Creation of the Processor association-->
								<xsl:variable name="processorAssociationId" select="concat( $urnCimProcessorAssociationIDPrefix, generate-id())"/>
								<rim:Association id="{$processorAssociationId}" associationType="{$processorAssociationType}" sourceObject="{$processStepId}" targetObject="{$organizationId}"/>
							</xsl:if>
						</xsl:for-each>
						<xsl:for-each select="gmd:source">
							<xsl:variable name="sourceDescription" select="gmd:LI_Source/gmd:description/gco:CharacterString"/>
							<xsl:if test="not(empty($sourceDescription))">
								<xsl:variable name="sourceId" select="concat($urnCimSourceExtrinsicObjectIDPrefix, generate-id(.))"/>
								<xsl:call-template name="source">
									<xsl:with-param name="sourceNode" select="."/>
									<xsl:with-param name="sourceId" select="$sourceId"/>
								</xsl:call-template>
								<!-- Creation of the StepSource association-->
								<xsl:variable name="stepSourceAssociationId" select="concat( $urnCimStepSourceAssociationIDPrefix, generate-id())"/>
								<rim:Association id="{$stepSourceAssociationId}" associationType="{$stepSourceAssociationType}" sourceObject="{$processStepId}" targetObject="{$sourceId}"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:if>
				</xsl:for-each>
				<!-- Creation of the Source objects -->
				<xsl:for-each select="gmd:lineage/gmd:LI_Lineage/gmd:source">
					<xsl:variable name="sourceDescription" select="gmd:LI_Source/gmd:description/gco:CharacterString"/>
					<xsl:if test="not(empty($sourceDescription))">
						<xsl:variable name="sourceId" select="concat($urnCimSourceExtrinsicObjectIDPrefix, generate-id(.))"/>
						<xsl:call-template name="source">
							<xsl:with-param name="sourceNode" select="."/>
							<xsl:with-param name="sourceId" select="$sourceId"/>
						</xsl:call-template>
						<!-- Creation of the LineageSource association -->
						<xsl:variable name="lineageSourceId" select="concat($urnCimLineageSourceAssociationIDPrefix, generate-id(.))"/>
						<rim:Association id="{$lineageSourceId}" associationType="{$lineageSourceAssociationType}" sourceObject="{$lineageId}" targetObject="{$sourceId}"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<!-- 
		Template for the creation of the Source object
	-->
	<xsl:template name="source">
		<xsl:param name="sourceNode"/>
		<xsl:param name="sourceId"/>
		<rim:ExtrinsicObject id="{$sourceId}" objectType="{$sourceObjectType}">
			<xsl:if test="$sourceNode/gmd:LI_Source/gmd:scaleDenominator/gmd:MD_RepresentativeFraction/gmd:denominator/gco:Integer">
				<rim:Slot name="{$scaleDenominatorSlotName}" slotType="{$integerSlotType}">
					<rim:ValueList>
						<rim:Value>
							<xsl:value-of select="$sourceNode/gmd:LI_Source/gmd:scaleDenominator/gmd:MD_RepresentativeFraction/gmd:denominator/gco:Integer"/>
						</rim:Value>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
				<xsl:if test="$sourceNode/gmd:sourceExtent/gmd:EX_Extent/gmd:geographicElement">
					<rim:Slot name="{$spatialSlotName}" slotType="{$envelopeSlotType}">
						<wrs:ValueList>
							<xsl:for-each select="$sourceNode/gmd:sourceExtent/gmd:EX_Extent/gmd:geographicElement">
								<wrs:AnyValue>
									<gml:Envelope srsName="urn:ogc:def:crs:EPSG:4326">
										<gml:lowerCorner>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:southBoundLatitude/gco:Decimal"/>
											<xsl:text> </xsl:text>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:westBoundLongitude/gco:Decimal"/>
										</gml:lowerCorner>
										<gml:upperCorner>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:northBoundLatitude/gco:Decimal"/>
											<xsl:text> </xsl:text>
											<xsl:value-of select="gmd:EX_GeographicBoundingBox/gmd:eastBoundLongitude/gco:Decimal"/>
										</gml:upperCorner>
									</gml:Envelope>
								</wrs:AnyValue>
							</xsl:for-each>
						</wrs:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:variable name="temporalBegin" select="$sourceNode/gmd:sourceExtent/gmd:EX_Extent/gmd:temporalElement[1]/gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='begin']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
				<xsl:if test="not(empty(temporalBegin))">
					<rim:Slot name="{$temporalBeginSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$temporalBegin"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:variable name="temporalEnd" select="$sourceNode/gmd:sourceExtent/gmd:EX_Extent/gmd:temporalElement[1]/gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='end']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
				<xsl:if test="not(empty(temporalEnd))">
					<rim:Slot name="{$temporalEndSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$temporalEnd"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
			<rim:Description>
				<rim:LocalizedString xml:lang="{$metadataLanguage}" charset="{$metadataCharacterSet}" value="{$sourceNode/gmd:LI_Source/gmd:description/gco:CharacterString}"/>
			</rim:Description>
		</rim:ExtrinsicObject>
		<!-- TODO SourceReferenceSystem association-->
		<!-- TODO SourceCitation association-->
	</xsl:template>
	<!-- 
			Registration of Quality Conformance Results
	-->
	<xsl:template name="createDataQualityConformanceResults">
		<xsl:for-each select="/*/gmd:dataQualityInfo/gmd:DQ_DataQuality/gmd:report//gmd:result">
			<xsl:variable name="referenceSpecificationID" select="concat ( $urnCimReferenceSpecificationExtrinsicObjectIDPrefix, count(.) )"/>
			<rim:ExtrinsicObject id="{$referenceSpecificationID}" objectType="{$referenceSpecificationObjectType}">
				<xsl:for-each select="gmd:DQ_ConformanceResult/gmd:specification/gmd:CI_Citation/gmd:date">
					<xsl:call-template name="slot-date-with-type">
						<xsl:with-param name="date" select="gmd:CI_Date/gmd:date"/>
						<xsl:with-param name="dateType" select="gmd:CI_Date/gmd:dateType"/>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:if test="gmd:DQ_ConformanceResult/gmd:pass/gco:Boolean">
					<rim:Slot name="{$conformanceSlotName}" slotType="{$stringSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="gmd:DQ_ConformanceResult/gmd:pass/gco:Boolean"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<rim:Name>
					<xsl:variable name="nameK">
						<xsl:call-template name="getNodeContent">
							<xsl:with-param name="node" select="gmd:DQ_ConformanceResult/gmd:specification/gmd:CI_Citation/gmd:title"/>
						</xsl:call-template>
					</xsl:variable>
					<rim:LocalizedString value="{$nameK}"/>
				</rim:Name>
			</rim:ExtrinsicObject>
			<xsl:variable name="specificationAssociationTypeId" select="concat($urnCimSpecificationAssociationIDPrefix, generate-id(.))"/>
			<rim:Association id="{$specificationAssociationTypeId}" associationType="{$specificationAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$referenceSpecificationID}"/>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
