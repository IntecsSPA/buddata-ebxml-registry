<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gmd="http://www.isotc211.org/2005/gmd" xmlns:gmi="http://www.isotc211.org/2005/gmi" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:gmx="http://www.isotc211.org/2005/gmx" xmlns:srv="http://www.isotc211.org/2005/srv" xmlns:gml32="http://www.opengis.net/gml/3.2" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink">
	<!-- <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/> -->
	<xsl:template name="createMainMetadataInformation">
		<xsl:param name="metadataInformationId">urn:CIM:metadataInformationId:1</xsl:param>
		<!-- Classiifcation Node for metadata standard name and version START-->
		<xsl:if test="gmd:metadataStandardName/gco:CharacterString and gmd:metadataStandardVersion/gco:CharacterString">
			<xsl:variable name="metadataStandardName" select="gmd:metadataStandardName/gco:CharacterString"/>
			<xsl:variable name="metadataStandardVersion" select="gmd:metadataStandardVersion/gco:CharacterString"/>
			<xsl:variable name="encodedCode" select="concat(encode-for-uri($metadataStandardName), ':', encode-for-uri($metadataStandardVersion))"/>
			<rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="{$metadataStandardNameAndVersionClassificationScheme}" code="{concat($metadataStandardName, ':', $metadataStandardVersion)}" id="{concat($metadataStandardNameAndVersionClassificationSchemePrefix, $encodedCode)}">
				<rim:Name>
					<rim:LocalizedString xml:lang="en" value="{concat($metadataStandardName, ':', $metadataStandardVersion)}"/>
				</rim:Name>
				<rim:Description>
					<rim:LocalizedString xml:lang="en" value="{concat($metadataStandardName, ':', $metadataStandardVersion, ' version')}"/>
				</rim:Description>
			</rim:ClassificationNode>
		</xsl:if>
		<!-- Classiifcation Node for metadata standard name and version END-->
		<!-- Metadata Information Extrinsic Object  START -->
		<rim:ExtrinsicObject id="{$metadataInformationId}" objectType="{$metadataInformationObjectType}">
			<!-- 	From fileIdentifier to <<slot>> identifier -->
			<xsl:call-template name="slot-identifier">
				<xsl:with-param name="identifier" select="gmd:fileIdentifier/gco:CharacterString"/>
			</xsl:call-template>
			<!--	From language to <<slot>> language -->
			<xsl:if test="gmd:language">
				<xsl:call-template name="slot-language">
					<xsl:with-param name="languageNode" select="gmd:language"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:call-template name="slot-date">
				<xsl:with-param name="dateStamp" select="./gmd:dateStamp"/>
			</xsl:call-template>
			<xsl:if test="gmd:metadataStandardName/gco:CharacterString and gmd:metadataStandardVersion/gco:CharacterString">
				<xsl:call-template name="slot-conformsTo">
					<xsl:with-param name="metadataStandardName" select="gmd:metadataStandardName/gco:CharacterString"/>
					<xsl:with-param name="metadataStandardVersion" select="gmd:metadataStandardVersion/gco:CharacterString"/>
				</xsl:call-template>
			</xsl:if>
			<!--	For each character set : <<classification>> CharacterSet -->
			<xsl:for-each select="gmd:characterSet">
				<xsl:variable name="charset" select="gmd:MD_CharacterSetCode/@codeListValue"/>
				<xsl:call-template name="classification-characterSet">
					<xsl:with-param name="characterSet" select="$charset"/>
					<xsl:with-param name="classifiedObjectId" select="$metadataInformationId"/>
				</xsl:call-template>
			</xsl:for-each>
		</rim:ExtrinsicObject>
		<!-- Metadata Information Extrinsic Object  END -->
		<!-- Creation of the Organization Extrinsic Object and the MetadataPointOfcontat association for each instance of
                                        gmd:MD_Metatada/gmd.contact -->
		<xsl:for-each select="gmd:contact">
			<xsl:if test="not(empty(gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString))">
				<xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
				<xsl:call-template name="organization">
					<xsl:with-param name="responsiblePartyNode" select="."/>
					<xsl:with-param name="organizationId" select="$organizationId"/>
				</xsl:call-template>
				<xsl:variable name="metadataPointOfContactAssociationId" select="concat( $urnCimMetadataPointOfContactAssociationIDPrefix, generate-id(.) )"/>
				<rim:Association id="{$metadataPointOfContactAssociationId}" associationType="{$metadataPointOfContactAssociationType}" sourceObject="{$metadataInformationId}" targetObject="{$organizationId}">
					<xsl:variable name="role" select="gmd:CI_ResponsibleParty/gmd:role/gmd:CI_RoleCode/@codeListValue"/>
					<!-- heikki : the codelist for rolecode has *many* more options than just PointOfCOntact, Author, Originator, Publisher.
                                 If it is not one of those 4, I ignore it so no classification will be created. Does not make any sense. -->
					<xsl:variable name="metadataPointOfContactAssociationClassificationId" select="concat( $citedResponsiblePartyClassificationSchemePrefix, generate-id(.) )"/>
					<rim:Classification id="{$metadataPointOfContactAssociationClassificationId}" classifiedObject="{$metadataPointOfContactAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
				</rim:Association>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="slot-identifier">
		<xsl:param name="identifier"/>
		<rim:Slot name="{$identifierSlotName}" slotType="{$stringSlotType}">
			<rim:ValueList>
				<rim:Value>
					<xsl:value-of select="$identifier"/>
				</rim:Value>
			</rim:ValueList>
		</rim:Slot>
	</xsl:template>
	<xsl:template name="slot-language">
		<xsl:param name="languageNode"/>
		<rim:Slot name="{$languageSlotName}" slotType="{$languageSlotType}">
			<rim:ValueList>
				<xsl:choose>
					<xsl:when test="$languageNode/gmd:LanguageCode/@codeListValue">
						<rim:Value>
							<xsl:value-of select="$languageNode/gmd:LanguageCode/@codeListValue"/>
						</rim:Value>
					</xsl:when>
					<xsl:when test="$languageNode/gco:CharacterString">
						<rim:Value>
							<xsl:value-of select="$languageNode/gco:CharacterString"/>
						</rim:Value>
					</xsl:when>
					<!-- <xsl:when test="$languageNode/gmd:CI_OnLineFunctionCode/@codeListValue">
						<rim:Value>
							<xsl:value-of select="$languageNode/gmd:CI_OnLineFunctionCode/@codeListValue"/>
						</rim:Value>
					</xsl:when>
					<xsl:whe ntest="$languageNode/gmd:CI_DateTypeCode/@codeListValue">
						<rim:Value>
							<xsl:value-of select="$languageNode/gmd:CI_DateTypeCode/@codeListValue"/>
						</rim:Value>
					</xsl:when> -->
					<xsl:otherwise>en</xsl:otherwise>
				</xsl:choose>
			</rim:ValueList>
		</rim:Slot>
	</xsl:template>
	<xsl:template name="slot-date">
		<xsl:param name="dateStamp"/>
		<xsl:if test="$dateStamp/gco:Date">
			<rim:Slot name="{$dateSlotName}" slotType="{$dateSlotType}">
				<rim:ValueList>
					<rim:Value>
						<xsl:value-of select="$dateStamp/gco:Date"/>
					</rim:Value>
				</rim:ValueList>
			</rim:Slot>
		</xsl:if>
		<xsl:if test="$dateStamp/gco:DateTime">
			<rim:Slot name="{$dateSlotName}" slotType="{$dateTimeSlotType}">
				<rim:ValueList>
					<rim:Value>
						<xsl:value-of select="$dateStamp/gco:DateTime"/>
					</rim:Value>
				</rim:ValueList>
			</rim:Slot>
		</xsl:if>
	</xsl:template>
	<xsl:template name="slot-conformsTo">
		<xsl:param name="metadataStandardName"/>
		<xsl:param name="metadataStandardVersion"/>
		<xsl:variable name="encodedCode" select="concat(encode-for-uri($metadataStandardName), ':', encode-for-uri($metadataStandardVersion))"/>
		<rim:Slot name="{$conformsToSlotName}" slotType="{$stringSlotType}">
			<rim:ValueList>
				<rim:Value>
					<xsl:value-of select="concat($metadataStandardNameAndVersionClassificationSchemePrefix, $encodedCode)"/>
				</rim:Value>
			</rim:ValueList>
		</rim:Slot>
	</xsl:template>
	<xsl:template name="classification-characterSet">
		<xsl:param name="characterSet"/>
		<xsl:param name="classifiedObjectId"/>
		<xsl:variable name="classificationId" select="concat( $urnCharacterSetClassificationIDPrefix, generate-id(.))"/>
		<rim:Classification id="{$classificationId}" classifiedObject="{$classifiedObjectId}" classificationNode="{concat( $characterSetClassificationSchemePrefix, $characterSet )}"/>
	</xsl:template>
</xsl:stylesheet>
