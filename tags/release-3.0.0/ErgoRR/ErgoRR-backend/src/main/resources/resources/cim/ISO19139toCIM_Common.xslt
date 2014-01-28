<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gmd="http://www.isotc211.org/2005/gmd" xmlns:gmi="http://www.isotc211.org/2005/gmi" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:gmx="http://www.isotc211.org/2005/gmx" xmlns:srv="http://www.isotc211.org/2005/srv" xmlns:gml32="http://www.opengis.net/gml/3.2" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink">
	<!-- <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/> -->
	<!-- 
	The following is the template to call to create an Organization and related Person
	-->
	<xsl:template name="organization">
		<xsl:param name="responsiblePartyNode"/>
		<xsl:param name="organizationId"/>
		<xsl:variable name="personName" select="$responsiblePartyNode/gmd:CI_ResponsibleParty/gmd:individualName/gco:CharacterString"/>
		<xsl:variable name="personNameId" select="concat($urnCimPersonIDPrefix, ':', generate-id())">
		</xsl:variable>
		<rim:Organization id="{$organizationId}" objectType="{$organizationObjectType}">
			<xsl:if test="not(empty($personName))">
				<xsl:attribute name="primaryContact" select="$personNameId"/>
			</xsl:if>
			<rim:Name>
				<xsl:variable name="name" select="$responsiblePartyNode/gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString"/>
				<rim:LocalizedString value="{$name}"/>
			</rim:Name>
			<xsl:variable name="eMailAddress" select="$responsiblePartyNode/gmd:CI_ResponsibleParty/gmd:contactInfo/gmd:CI_Contact/gmd:address/gmd:CI_Address/gmd:electronicMailAddress/gco:CharacterString"/>
			<xsl:if test="not(empty($eMailAddress))">
				<rim:EmailAddress address="{$eMailAddress}"/>
			</xsl:if>
		</rim:Organization>
		<xsl:if test="not(empty($personName))">
			<rim:Person id="{$personNameId}" objectType="{$personObjectType}">
				<rim:PersonName lastName="{$personName}"/>
			</rim:Person>
		</xsl:if>
	</xsl:template>
	<!-- 
	The following is the template to call to create an Envelope, a TemporalBegin/End andCoverage Slots
	-->
	<xsl:template name="extent">
		<xsl:for-each select="/*/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent | 
											/*/gmd:identificationInfo/gmd:MD_DataIdentification/gmd:extent">
			<xsl:variable name="boundingBox" select="gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicBoundingBox"/>
			<xsl:if test="$boundingBox">
				<rim:Slot name="{$spatialSlotName}" slotType="{$envelopeSlotType}">
					<wrs:ValueList>
						<wrs:AnyValue>
							<gml:Envelope srsName="urn:ogc:def:crs:EPSG:4326">
								<gml:lowerCorner>
									<xsl:value-of select="$boundingBox/gmd:southBoundLatitude/gco:Decimal"/>
									<xsl:text> </xsl:text>
									<xsl:value-of select="$boundingBox/gmd:westBoundLongitude/gco:Decimal"/>
								</gml:lowerCorner>
								<gml:upperCorner>
									<xsl:value-of select="$boundingBox/gmd:northBoundLatitude/gco:Decimal"/>
									<xsl:text> </xsl:text>
									<xsl:value-of select="$boundingBox/gmd:eastBoundLongitude/gco:Decimal"/>
								</gml:upperCorner>
							</gml:Envelope>
						</wrs:AnyValue>
					</wrs:ValueList>
				</rim:Slot>
			</xsl:if>
			<xsl:variable name="geographicDescription" select="gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicDescription/gmd:geographicIdentifier"/>
			<xsl:if test="$geographicDescription">
				<xsl:variable name="geographicDescriptionValue">
					<xsl:choose>
						<xsl:when test="$geographicDescription/*/gmd:code/gco:CharacterString">
							<xsl:value-of select="$geographicDescription/*/gmd:code/gco:CharacterString"/>
						</xsl:when>
						<xsl:when test="$geographicDescription/*/gmd:code/*/@codeListValue">
							<xsl:value-of select="$geographicDescription/*/gmd:code/*/@codeListValue"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$geographicDescription/@gco:nilReason"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<!-- International string type NOT SUPPORTED
                <rim:Slot name="{$coverageSlotName}" slotType="{$internationalStringSlotType}">
				   <wrs:ValueList>
						<wrs:AnyValue>
							<rim:InternationalString>
								  <rim:LocalizedString charset="{$metadataCharacterSet}" xml:lang="{$metadataLanguage}"
										   value="{$geographicDescription/gmd:MD_Identifier/gmd:code/gco:CharacterString}"/>
							</rim:InternationalString>
						  </wrs:AnyValue>
					</wrs:ValueList>   
                </rim:Slot> -->
				<rim:Slot name="{$coverageSlotName}" slotType="{$stringSlotType}">
					<rim:ValueList>
						<rim:Value>
							<xsl:value-of select="$geographicDescription/gmd:MD_Identifier/gmd:code/gco:CharacterString"/>
						</rim:Value>
					</rim:ValueList>
				</rim:Slot>
			</xsl:if>
			<xsl:for-each select="gmd:EX_Extent/gmd:temporalElement">
				<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/*[local-name() = 'TimeInstant']">
					<rim:Slot name="{$temporalBeginSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
					<rim:Slot name="{$temporalEndSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']">
					<rim:Slot name="{$temporalBeginSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='begin']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']">
									<xsl:value-of select="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='begin']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
								</xsl:if>
								<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='beginPosition']">
									<xsl:value-of select="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='beginPosition']"/>
								</xsl:if>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
					<rim:Slot name="{$temporalEndSlotName}" slotType="{$dateTimeSlotType}">
						<rim:ValueList>
							<rim:Value>
								<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='end']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']">
									<xsl:value-of select="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='end']/*[local-name() ='TimeInstant']/*[local-name() ='timePosition']"/>
								</xsl:if>
								<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='endPosition']">
									<xsl:value-of select="gmd:EX_TemporalExtent/gmd:extent/*[local-name() ='TimePeriod']/*[local-name() ='endPosition']"/>
								</xsl:if>
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
				</xsl:if>
				<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/gml:TimeEdge">
					<!-- Not yet Implemented-->
				</xsl:if>
				<xsl:if test="gmd:EX_TemporalExtent/gmd:extent/gml:TimeNode">
					<!-- Not yet Implemented-->
				</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
