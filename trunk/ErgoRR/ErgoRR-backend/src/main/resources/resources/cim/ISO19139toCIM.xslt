<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:saxon="http://saxon.sf.net/" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
        xmlns:gmd="http://www.isotc211.org/2005/gmd" 
        xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" 
        xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" 
        xmlns:gco="http://www.isotc211.org/2005/gco" 
        xmlns:gmx="http://www.isotc211.org/2005/gmx" 
        xmlns:srv="http://www.isotc211.org/2005/srv" 
        xmlns:gml32="http://www.opengis.net/gml/3.2" 
        xmlns:gml="http://www.opengis.net/gml" 
        xmlns:xlink="http://www.w3.org/1999/xlink">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
    <xsl:param name="cswURL">http://hrt-11.pisa.intecs.it/ergorr2/httpservice</xsl:param>
    <xsl:param name="metadataInformationId">urn:CIM:metadataInformationId:1</xsl:param>

    <xsl:include href="ISO19139toCIM_Include.xslt"/>
    
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

    <xsl:template match="gmd:MD_Metadata">
        <!--xsl:variable name="metadataInformationId" select="$urnCimMetadataInformationExtrinsicObjectID"/-->
        <xsl:variable name="registryPackageId" select="concat( $metadataInformationId , ':pkg')"/>
        <rim:RegistryObjectList>
            <xsl:attribute name="iso19139Id">
                <xsl:value-of select="./gmd:fileIdentifier/gco:CharacterString"/>
            </xsl:attribute>

            <!-- Keywords -->
            <xsl:variable name="descriptiveKeywordsClassificationNodes">
                <xsl:for-each select="gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:descriptiveKeywords">
                    <xsl:variable name="isThesaurusName">
                        <xsl:call-template name="getNodeContent">
                            <xsl:with-param name="node" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title"/>
                        </xsl:call-template>
                    </xsl:variable>
                    <xsl:if test="string-length($isThesaurusName) = 0">
                        <xsl:for-each select="gmd:MD_Keywords/gmd:keyword">
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
                            <rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="{$keywordSchemeClassificationScheme}" code="{$keywordId}" id="{concat( $keywordSchemeClassificationSchemePrefix, $keywordId )}">
                                <xsl:if test="gmx:Anchor">
                                    <rim:Slot name="{$urlSlotName}" slotType="{$uriSlotType}">
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
                    </xsl:if>
                </xsl:for-each>
            </xsl:variable>

            <xsl:if test="$descriptiveKeywordsClassificationNodes">
                <xsl:copy-of select="$descriptiveKeywordsClassificationNodes"/>
            </xsl:if>

            <xsl:variable name="descriptiveKeywordsClassificationSchemes">
                <xsl:for-each select="gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:descriptiveKeywords">
                    <xsl:variable name="isThesaurusName">
                        <xsl:call-template name="getNodeContent">
                            <xsl:with-param name="node" select="gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title"/>
                        </xsl:call-template>
                    </xsl:variable>
                    <xsl:if test="string-length($isThesaurusName) > 0">
                        <!-- gmd:MD_Keywords/gmd:thesaurusName/gmd:CI_Citation/gmd:title     thesaurusIdentifier-->
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
                            <xsl:variable name="urnCimKeywordThesaurusSchemeClassificationIDPrefix" select="concat( $cimIDPrefix , 'Classification:KeywordThesaurusScheme:')"/>
                            <xsl:for-each select="gmd:MD_Keywords/gmd:keyword">
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
                                <xsl:variable name="keywordThesaurusSchemeClassificationId" select="concat( $keywordThesaurusSchemeClassificationSchemePrefix, generate-id(.) )"/>
                                <rim:ClassificationNode objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode" parent="{$keywordThesaurusSchemeClassificationScheme}" code="{$keywordId}" id="{concat( $keywordThesaurusSchemeClassificationSchemePrefix, $keywordId)}">
                                    <xsl:if test="gmx:Anchor">
                                        <rim:Slot name="{$urlSlotName}" slotType="{$uriSlotType}">
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

            <!-- Classification Services Handling START -->
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
                                                <xsl:value-of select="."></xsl:value-of>
                                            </value>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <value>
                                            <xsl:value-of select=" '1.1' "></xsl:value-of>
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
                                                <xsl:value-of select="."></xsl:value-of>
                                            </value>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <value>
                                            <xsl:value-of select=" '1.3.0' "></xsl:value-of>
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
                                                <xsl:value-of select="."></xsl:value-of>
                                            </value>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <value>
                                            <xsl:value-of select=" '1.1.2' "></xsl:value-of>
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
                                                <xsl:value-of select="."></xsl:value-of>
                                            </value>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <value>
                                            <xsl:value-of select=" '2.0.2' "></xsl:value-of>
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
                                                <xsl:value-of select="."></xsl:value-of>
                                            </value>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <value>
                                            <xsl:value-of select=" '1.0.1' "></xsl:value-of>
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
                                                <xsl:value-of select="."></xsl:value-of>
                                            </value>
                                        </xsl:for-each>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <value>
                                            <xsl:value-of select=" '1.0.0' "></xsl:value-of>
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


            <!-- Metadata Information Extrinsic Object  START -->
            <rim:ExtrinsicObject id="{$metadataInformationId}" objectType="{$metadataInformationObjectType}">
                <!-- 	From fileIdentifier to <<slot>> identifier -->
                <xsl:call-template name="slot-identifier">
                    <xsl:with-param name="identifier" select="./gmd:fileIdentifier/gco:CharacterString"/>
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
                <xsl:if test="./gmd:metadataStandardName/gco:CharacterString and ./gmd:metadataStandardVersion/gco:CharacterString">
                    <xsl:call-template name="slot-conformsTo">
                        <xsl:with-param name="metadataStandardName" select="./gmd:metadataStandardName/gco:CharacterString"/>
                        <xsl:with-param name="metadataStandardVersion" select="./gmd:metadataStandardVersion/gco:CharacterString"/>
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
                <xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
                <rim:Organization id="{$organizationId}" objectType="{$organizationObjectType}">
                    <rim:Name>
                        <xsl:variable name="name" select="gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString"/>
                        <rim:LocalizedString value="{$name}"/>
                    </rim:Name>
                </rim:Organization>
                <xsl:variable name="metadataPointOfContactAssociationId" select="concat( $urnCimMetadataPointOfContactAssociationIDPrefix, generate-id(.) )"/>
                <rim:Association id="{$metadataPointOfContactAssociationId}" associationType="{$metadataPointOfContactAssociationType}" sourceObject="{$metadataInformationId}" targetObject="{$organizationId}">
                    <xsl:variable name="role" select="gmd:CI_ResponsibleParty/gmd:role/gmd:CI_RoleCode/@codeListValue"/>
                    <!-- heikki : the codelist for rolecode has *many* more options than just PointOfCOntact, Author, Originator, Publisher.
                                 If it is not one of those 4, I ignore it so no classification will be created. Does not make any sense. -->
                    <xsl:variable name="metadataPointOfContactAssociationClassificationId" select="concat( $citedResponsiblePartyClassificationSchemePrefix, generate-id(.) )"/>
                    <rim:Classification id="{$metadataPointOfContactAssociationClassificationId}" classifiedObject="{$metadataPointOfContactAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
                </rim:Association>
            </xsl:for-each>

            <!-- Resource Metadata EO Call Template START -->
            <xsl:for-each select="gmd:identificationInfo">
                <xsl:call-template name="resourceMetadata">
                    <xsl:with-param name="metadadataEOIdentifier" select="$metadataInformationId"/>
                    <xsl:with-param name="descriptiveKeywordsClassificationNodes" select="$descriptiveKeywordsClassificationNodes"/>
                    <xsl:with-param name="descriptiveKeywordsClassificationSchemes" select="$descriptiveKeywordsClassificationSchemes"/>
                    <xsl:with-param name="servicesClassificationNodes" select="$servicesClassificationNodes"/>
                </xsl:call-template>
            </xsl:for-each>
            <!-- Resource Metadata EO Call Tempalate END -->

            <!-- Parent Idenfier (Metadata Information EO) Call Template  START-->
            <xsl:variable name="parentIdentifier" select="gmd:parentIdentifier/gco:CharacterString"/>
            <xsl:if test="$parentIdentifier">
                <xsl:call-template name="parentIdentifierMetadataInformation">
                    <xsl:with-param name="childIdentifier" select="$metadataInformationId"/>
                    <xsl:with-param name="parentIdentifier" select="$parentIdentifier"/>
                </xsl:call-template>
            </xsl:if>
            <!-- Parent Idenfier (Metadata Information EO) Call Template END -->
        </rim:RegistryObjectList>
    </xsl:template>

    <!-- Parent Idenfier (Metadata Information EO) Template  START-->
    <xsl:template name="parentIdentifierMetadataInformation">
        <xsl:param name="childIdentifier"/>
        <xsl:param name="parentIdentifier"/>
        <xsl:variable name="parentMetadataInformationId" select="concat( 'urn:CIM:' , $parentIdentifier , ':' , 'ExtrinsicObject:MetadataInformation')"/>
        <rim:ExtrinsicObject id="{$parentMetadataInformationId}" objectType="{$metadataInformationObjectType}">
            <xsl:call-template name="slot-identifier">
                <xsl:with-param name="identifier" select="$parentIdentifier"/>
            </xsl:call-template>
        </rim:ExtrinsicObject>
        <xsl:variable name="parentMetadataInformationAssociationId" select="concat( $urnCimParentMetadataInformationAssociationIDPrefix, generate-id(.))"/>
        <rim:Association id="{$parentMetadataInformationAssociationId}" associationType="{$parentMetadataInformationAssociationType}" sourceObject="{$parentMetadataInformationAssociationId}" targetObject="{$childIdentifier}"/>
    </xsl:template>
    <!-- Parent Identifier (Metadata Information EO) Template  END-->

    <!-- Resource Metadata EO Template START-->
    <xsl:template name="resourceMetadata">
        <xsl:param name="metadadataEOIdentifier"/>
        <xsl:param name="descriptiveKeywordsClassificationNodes"/>
        <xsl:param name="descriptiveKeywordsClassificationSchemes"/>
        <xsl:param name="servicesClassificationNodes"/>

        <wrs:ExtrinsicObject id="{$resourceMetadataId}">
            <xsl:call-template name="specifyTypeOfResourceMetadata"/>

            <xsl:for-each select="/gmd:MD_Metadata/gmd:hierarchyLevelName">
                <xsl:call-template name="slot-hierarchyLevelName">
                    <xsl:with-param name="hierarchyLevelName" select="."/>
                </xsl:call-template>
            </xsl:for-each>
            <!-- Management of CITATION START -->
            <xsl:for-each select="/gmd:MD_Metadata/gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:citation/gmd:CI_Citation/gmd:alternateTitle">
                <xsl:variable name="alternateTitle" select="gco:CharacterString"/>
                <xsl:call-template name="slot-title">
                    <xsl:with-param name="title" select="$alternateTitle"/>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="/gmd:MD_Metadata/gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:citation/gmd:CI_Citation/gmd:date">
                <xsl:call-template name="slot-date-with-type">
                    <xsl:with-param name="date" select="gmd:CI_Date/gmd:date"/>
                    <xsl:with-param name="dateType" select="gmd:CI_Date/gmd:dateType"/>
                </xsl:call-template>
            </xsl:for-each>
            <!-- Management of CITATION END -->
            <xsl:for-each select="/gmd:MD_Metadata/gmd:distributionInfo/gmd:MD_Distribution/gmd:distributionFormat">
                <rim:Slot name="{$formatSlotName}" slotType="{$stringSlotType}">
                    <rim:ValueList>
                        <rim:Value>
                            <xsl:choose>
                                <xsl:when test="gmd:MD_Format/gmd:name/gco:CharacterString">
                                    <xsl:value-of select="gmd:MD_Format/gmd:name/gco:CharacterString"/>
                                </xsl:when>
                                <xsl:when test="gmd:MD_Format/gmd:name/@gco:nilReason">
                                    <xsl:value-of select="gmd:MD_Format/gmd:name/@gco:nilReason"/>
                                </xsl:when>
                            </xsl:choose>
                        </rim:Value>
                        <rim:Value>
                            <xsl:choose>
                                <xsl:when test="gmd:MD_Format/gmd:version/gco:CharacterString">
                                    <xsl:value-of select="gmd:MD_Format/gmd:version/gco:CharacterString"/>
                                </xsl:when>
                                <xsl:when test="gmd:MD_Format/gmd:version/@gco:nilReason">
                                    <xsl:value-of select="gmd:MD_Format/gmd:version/@gco:nilReason"/>
                                </xsl:when>
                            </xsl:choose>
                        </rim:Value>
                    </rim:ValueList>
                </rim:Slot>
            </xsl:for-each>

            <xsl:if test="/gmd:MD_Metadata/gmd:distributionInfo/gmd:MD_Distribution/gmd:transferOptions/gmd:MD_DigitalTransferOptions/gmd:onLine/gmd:CI_OnlineResource/gmd:linkage/gmd:URL">
                <xsl:variable name="url" select="/gmd:MD_Metadata/gmd:distributionInfo/gmd:MD_Distribution/gmd:transferOptions/gmd:MD_DigitalTransferOptions/gmd:onLine/gmd:CI_OnlineResource/gmd:linkage/gmd:URL"/>
                <rim:Slot name="http://purl.org/dc/terms/references" slotType="xsd:URI">
                    <rim:ValueList>
                        <rim:Value>
                            <xsl:value-of select="$url"/>
                        </rim:Value>
                    </rim:ValueList>
                </rim:Slot>
            </xsl:if>
            
            <!-- Management of the Lineage information--> 
            <xsl:if test="/gmd:MD_Metadata/gmd:dataQualityInfo[1]/*/gmd:lineage/*/gmd:statement/gco:CharacterString">
                <rim:Slot name="{$lineageSlotName}" slotType="xsd:string">
                    <rim:ValueList>
                        <rim:Value>
                            <xsl:value-of select="/gmd:MD_Metadata/gmd:dataQualityInfo[1]/*/gmd:lineage/*/gmd:statement/gco:CharacterString"/>
                        </rim:Value>   
                    </rim:ValueList>
                </rim:Slot>
            </xsl:if>
            <xsl:if test="/gmd:MD_Metadata/gmd:dataQualityInfo[1]/*/gmd:lineage/*/gmd:source/*/gmd:description/gco:CharacterString">
                <rim:Slot name="{$sourceSlotName}" slotType="xsd:string">
                    <rim:ValueList>
                        <rim:Value>
                            <xsl:value-of select="/gmd:MD_Metadata/gmd:dataQualityInfo[1]/*/gmd:lineage/*/gmd:source/*/gmd:description/gco:CharacterString"/>
                        </rim:Value>   
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
            <xsl:for-each select="/gmd:MD_Metadata/gmd:identificationInfo/*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:descriptiveKeywords">
                <xsl:if test="gmd:MD_Keywords/gmd:type">
                    <xsl:variable name="keywordType" select="gmd:MD_Keywords/gmd:type/gmd:MD_KeywordTypeCode/@codeListValue"/>
                    <xsl:variable name="keywordTypeClassificationId" select="concat( $urnCimKeywordTypeClassificationIDPrefix, generate-id(.) )"/>
                    <rim:Classification id="{$keywordTypeClassificationId}" classifiedObject="{$resourceMetadataId}" classificationNode="{concat( $keywordTypeClassificationSchemePrefix, $keywordType)}"/>
                </xsl:if>
            </xsl:for-each>
            <!--xsl:variable name="idRepo" select="translate( $metadataInformationId, ':', '_' )"/-->
            <wrs:repositoryItemRef xlink:href="{concat( $cswURL, '?request=GetRepositoryItem&amp;service=CSW-ebRIM&amp;Id=', $resourceMetadataId)}"/>
            <!--wrs:repositoryItemRef xlink:href="{concat( $cswURL, '?request=GetRepositoryItem&amp;service=CSW-ebRIM&amp;version=2.0.2&amp;id=', $metadataInformationId)}"/-->
        </wrs:ExtrinsicObject>

        <xsl:for-each select="/gmd:MD_Metadata/gmd:dataQualityInfo/gmd:DQ_DataQuality/gmd:report//gmd:result">
            <xsl:variable name="referenceSpecificationID" select="concat ( $urnCimReferenceSpecificationExtrinsicObjectIDPrefix, count(.) )"/>
            <rim:ExtrinsicObject id="{$referenceSpecificationID}" objectType="{$referenceSpecificationObjectType}">
                <rim:Name>
                    <xsl:variable name="nameK">
                        <xsl:call-template name="getNodeContent">
                            <xsl:with-param name="node" select="gmd:DQ_ConformanceResult/gmd:specification/gmd:CI_Citation/gmd:title"/>
                        </xsl:call-template>
                    </xsl:variable>
                    <rim:LocalizedString value="{$nameK}"/>
                </rim:Name>
                <xsl:for-each select="gmd:DQ_ConformanceResult/gmd:specification/gmd:CI_Citation/gmd:date">
                    <xsl:call-template name="slot-date-with-type">
                        <xsl:with-param name="date" select="gmd:CI_Date/gmd:date"/>
                        <xsl:with-param name="dateType" select="gmd:CI_Date/gmd:dateType"/>
                    </xsl:call-template>
                </xsl:for-each>
                <xsl:if test="gmd:DQ_ConformanceResult/gmd:pass/gco:Boolean">
                    <rim:Slot name="Conformance" slotType="xsd:string">
                        <rim:ValueList>
                            <rim:Value>
                                <xsl:value-of select="gmd:DQ_ConformanceResult/gmd:pass/gco:Boolean"/>
                            </rim:Value>
                        </rim:ValueList>
                    </rim:Slot>
                </xsl:if>
            </rim:ExtrinsicObject>
            <xsl:variable name="specificationAssociationTypeId" select="concat( $specificationAssociationType, ':' , generate-id(.))"/>
            <rim:Association id="{$specificationAssociationTypeId}" associationType="{$specificationAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$referenceSpecificationID}"/>
        </xsl:for-each>

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

        <xsl:for-each select="/gmd:MD_Metadata/gmd:referenceSystemInfo">
            <xsl:call-template name="referenceSystemInfo">
                <xsl:with-param name="referenceSystemInfo_id" select="$resourceMetadataId"/>
            </xsl:call-template>
        </xsl:for-each>

        <xsl:variable name="resourceMetadataInformationAssociationId" select="concat( $urnCimResourceMetadataInformationAssociationIDPrefix, generate-id(.))"/>
        <!-- Controllare se ci va la resource che linkata dal metadata-->
        <rim:Association id="{$resourceMetadataInformationAssociationId}" associationType="{$resourceMetadataInformationAssociationType}" sourceObject="{$resourceMetadataId}" targetObject="{$metadadataEOIdentifier}"/>
    </xsl:template>
    <!-- Resource Metadata EO Template END-->

    <xsl:template name="serviceoperation">
        <xsl:param name="servicemetadata-id"/>
        <xsl:for-each select="srv:SV_ServiceIdentification/srv:containsOperations">
            <xsl:variable name="serviceOperationId" select="concat( $urnCimServiceOperationExtrinsicObjectIDPrefix, ':', generate-id(.))"/>
            <rim:ExtrinsicObject id="{$serviceOperationId}" objectType="{$serviceOperationObjectType}">
                <xsl:for-each select="srv:SV_OperationMetadata/srv:connectPoint/gmd:CI_OnlineResource/gmd:linkage">
                    <xsl:variable name="url" select="gmd:URL"/>
                    <rim:Slot name="http://purl.org/dc/terms/references" slotType="xsd:URI">
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

    <xsl:template name="pointOfContact">
        <xsl:param name="pointOfContact-id"/>
        <xsl:for-each select="*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:pointOfContact">
            <xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.) )"/>
            <rim:Organization id="{$organizationId}" objectType="{$organizationObjectType}">
                <rim:Name>
                    <xsl:variable name="name" select="gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString"/>
                    <rim:LocalizedString value="{$name}"/>
                </rim:Name>
            </rim:Organization>
            <xsl:variable name="citedResponsiblePartyAssociationId" select="concat( $urnCimCitedResponsiblePartyAssociationIDPrefix, generate-id(.) )"/>
            <rim:Association id="{$citedResponsiblePartyAssociationId}" associationType="{$citedResponsiblePartyAssociationType}" sourceObject="{$pointOfContact-id}" targetObject="{$organizationId}">
                <xsl:variable name="role" select="gmd:CI_ResponsibleParty/gmd:role/gmd:CI_RoleCode/@codeListValue"/>
                <!-- heikki : the codelist for rolecode has *many* more options than just PointOfCOntact, Author, Originator, Publisher.
                                 If it is not one of those 4, I ignore it so no classification will be created. Does not make any sense. -->
                <xsl:variable name="citedResponsiblePartyAssociationClassificationId" select="concat( $urnCimCitedResponsiblePartyClassificationIDPrefix, generate-id(.) )"/>
                <rim:Classification id="{$citedResponsiblePartyAssociationClassificationId}" classifiedObject="{$citedResponsiblePartyAssociationId}" classificationNode="{concat( $citedResponsiblePartyClassificationSchemePrefix, $role)}"/>
            </rim:Association>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="citedResponsibleParty">
        <xsl:param name="citedResponsibleParty-id"/>
        <xsl:for-each select="*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:citation/gmd:CI_Citation/gmd:citedResponsibleParty">
            <xsl:variable name="organizationId" select="concat( $urnCimOrganizationIDPrefix, generate-id(.))"/>
            <rim:Organization id="{$organizationId}" objectType="{$organizationObjectType}">
                <rim:Name>
                    <xsl:variable name="name" select="gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString"/>
                    <rim:LocalizedString value="{$name}"/>
                </rim:Name>
            </rim:Organization>
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
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="resourceConstraints">
        <xsl:param name="resourceConstraints-id"/>
        <xsl:for-each select="*[local-name() = 'SV_ServiceIdentification' or local-name() = 'MD_DataIdentification' ]/gmd:resourceConstraints">

            <xsl:if test="gmd:MD_Constraints">
                <xsl:if test="gmd:MD_Constraints/gmd:useLimitation/gco:CharacterString">
                    <xsl:variable name="rightsId" select="concat($urnCimRightsExtrinsicObjectIDPrefix, generate-id(.))"/>
                    <rim:ExtrinsicObject id="{$rightsId}" objectType="{$rightsObjectType}">
                        <rim:Slot name="{$abstractSlotName}" slotType="xsd:string">
                            <rim:ValueList>
                                <rim:Value>
                                    <xsl:value-of select="gmd:MD_Constraints/gmd:useLimitation/gco:CharacterString"/>
                                </rim:Value>
                            </rim:ValueList>
                        </rim:Slot>
                    </rim:ExtrinsicObject>
                    <xsl:variable name="resourceConstraintsAssociationId" select="concat( $urnCimResourceConstraintsAssociationIDPrefix, generate-id(.))"/>
                    <rim:Association id="{$resourceConstraintsAssociationId}" associationType="{$resourceConstraintsAssociationType}" sourceObject="{$resourceConstraints-id}" targetObject="{$rightsId}"/>
                </xsl:if>
            </xsl:if>

            <xsl:if test="gmd:MD_LegalConstraints/*">
                <xsl:variable name="legalConstraintsId" select="concat( $urnCimLegalConstraintsExtrinsicObjectIDPrefix, generate-id(.))"/>
                <rim:ExtrinsicObject id="{$legalConstraintsId}" objectType="{$legalConstraintsObjectType}">
                    <xsl:if test="gmd:MD_LegalConstraints/gmd:useLimitation/gco:CharacterString">
                        <rim:Slot name="{$abstractSlotName}" slotType="xsd:string">
                            <rim:ValueList>
                                <rim:Value>
                                    <xsl:value-of select="gmd:MD_LegalConstraints/gmd:useLimitation/gco:CharacterString"/>
                                </rim:Value>
                            </rim:ValueList>
                        </rim:Slot>
                    </xsl:if>
                    <xsl:if test="gmd:MD_LegalConstraints/gmd:otherConstraints/gco:CharacterString">
                        <rim:Slot name="{$rightsSlotName}" slotType="xsd:string">
                            <rim:ValueList>
                                <rim:Value>
                                    <xsl:value-of select="gmd:MD_LegalConstraints/gmd:otherConstraints/gco:CharacterString"/>
                                </rim:Value>
                            </rim:ValueList>
                        </rim:Slot>
                    </xsl:if>
                    <xsl:if test="gmd:MD_LegalConstraints/gmd:useConstraints or gmd:MD_LegalConstraints/gmd:accessConstraints">
                        <xsl:variable name="restrictionTypeClassificationId" select="concat($urnCimRestrictionTypeClassificationIDPrefix, generate-id(.))"/>
                        <xsl:if test="gmd:MD_LegalConstraints/gmd:useConstraints">
                            <rim:Classification id="{$restrictionTypeClassificationId}" classifiedObject="{$legalConstraintsId}" classificationNode="{concat( $restrictionTypeClassificationSchemePrefix, 'use')}"/>
                        </xsl:if>
                        <xsl:if test="gmd:MD_LegalConstraints/gmd:accessConstraints">
                            <rim:Classification id="{$restrictionTypeClassificationId}" classifiedObject="{$legalConstraintsId}" classificationNode="{concat( $restrictionTypeClassificationSchemePrefix, 'access')}"/>
                        </xsl:if>
                        <xsl:variable name="restrictionCodeClassificationId" select="concat( $urnCimRestrictionCodeClassificationIDPrefix, generate-id(.))"/>
                        <xsl:variable name="restrictionCode" select="gmd:MD_LegalConstraints/*/gmd:MD_RestrictionCode/@codeListValue"/>
                        <rim:Classification id="{$restrictionCodeClassificationId}" classifiedObject="{$restrictionTypeClassificationId}" classificationNode="{concat($restrictionCodeClassificationSchemePrefix, $restrictionCode)}"/>
                    </xsl:if>
                </rim:ExtrinsicObject>
                <xsl:variable name="resourceConstraintsAssociationId" select="concat( $urnCimResourceConstraintsAssociationIDPrefix, generate-id(.))"/>
                <rim:Association id="{$resourceConstraintsAssociationId}" associationType="{$resourceConstraintsAssociationType}" sourceObject="{$resourceConstraints-id}" targetObject="{$legalConstraintsId}"/>
            </xsl:if>

            <xsl:if test="gmd:MD_SecurityConstraints/*">
                <xsl:variable name="securityConstraintsId" select="concat( $urnCimSecurityConstraintsExtrinsicObjectIDPrefix, generate-id(.) )"/>
                <rim:ExtrinsicObject id="{$securityConstraintsId}" objectType="{$securityConstraintsObjectType}">
                    <xsl:if test="gmd:MD_SecurityConstraints/gmd:useLimitation/gco:CharacterString">
                        <rim:Slot name="{$abstractSlotName}" slotType="xsd:string">
                            <rim:ValueList>
                                <rim:Value>
                                    <xsl:value-of select="gmd:MD_SecurityConstraints/gmd:useLimitation/gco:CharacterString"/>
                                </rim:Value>
                            </rim:ValueList>
                        </rim:Slot>
                    </xsl:if>
                    <xsl:variable name="classificationCodeClassificationId" select="concat( $urnClassificationCodeClassificationIDPrefix, generate-id(.))"/>
                    <xsl:variable name="classificationCode" select="gmd:MD_SecurityConstraints/gmd:classification/gmd:MD_ClassificationCode/@codeListValue"/>
                    <rim:Classification id="{$classificationCodeClassificationId}" classifiedObject="{$securityConstraintsId}" classificationNode="{concat( $classificationCodeClassificationSchemePrefix, $classificationCode)}"/>
                </rim:ExtrinsicObject>
                <xsl:variable name="resourceConstraintsAssociationId" select="concat( $urnCimResourceConstraintsAssociationIDPrefix, generate-id(.))"/>
                <rim:Association id="{$resourceConstraintsAssociationId}" associationType="{$resourceConstraintsAssociationType}" sourceObject="{$resourceConstraints-id}" targetObject="{$securityConstraintsId}"/>
            </xsl:if>

        </xsl:for-each>
    </xsl:template>

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
        <xsl:variable name="hierarchyLevelCode" select="/gmd:MD_Metadata/gmd:hierarchyLevel/gmd:MD_ScopeCode/@codeListValue"/>
        <xsl:variable name="objectTypeValue">
            <xsl:choose>
	                <!-- DataMetadata,  ElementaryDataset, DatasetCollection, ServiceMetadata and Application-->
                <xsl:when test="$hierarchyLevelCode = 'series' ">
                    <xsl:value-of select="$datasetCollectionObjectType"/>
                </xsl:when>
                <xsl:when test="$hierarchyLevelCode = 'dataset' and string-length(/gmd:MD_Metadata/gmd:parentIdentifier) = 0">
                    <xsl:value-of select="$dataMetadataObjectType"/>
                </xsl:when>
                <xsl:when test="$hierarchyLevelCode = 'dataset' and string-length(/gmd:MD_Metadata/gmd:parentIdentifier) > 0">
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
        <xsl:attribute name="objectType">
            <xsl:value-of select="$objectTypeValue"/>
        </xsl:attribute>
    </xsl:template>
    
    <xsl:template name="servicemetadata">
        <xsl:param name="servicemetadata-id"/>
        <xsl:param name="servicesClassificationNodes"/>

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
                <xsl:variable name="urnISOServices" select=" 'urn:ogc:def:ebRIM-ClassificationScheme:ISO-19119:2005:Services' "></xsl:variable>
                <xsl:variable name="serviceType" select="srv:SV_ServiceIdentification/srv:serviceType/gco:LocalName"></xsl:variable>
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

    <xsl:template name="datametadata">
        <xsl:param name="dataset-id"/>
        <xsl:for-each select="gmd:MD_DataIdentification/gmd:spatialResolution">
            <xsl:choose>
                <xsl:when test="gmd:MD_Resolution/gmd:equivalentScale">
                    <xsl:call-template name="slot-scaledenominator">
                        <xsl:with-param name="scaleDenominator" select="gmd:MD_Resolution/gmd:equivalentScale/gmd:MD_RepresentativeFraction/gmd:denominator/gco:Integer"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="slot-resolution">
                        <xsl:with-param name="uom" select="gmd:MD_Resolution/gmd:distance/gco:Distance/@uom"/>
                        <xsl:with-param name="distance" select="gmd:MD_Resolution/gmd:distance/gco:Distance"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        <xsl:for-each select="gmd:MD_DataIdentification/gmd:extent">
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
            <xsl:variable name="geographicDescription" select="gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicDescription"/>
            <xsl:if test="$geographicDescription">
                <rim:Slot name="{$coverageSlotName}" slotType="xsd:string">
                    <rim:ValueList>
                        <rim:Value>
                            <xsl:value-of select="$geographicDescription/gmd:geographicIdentifier/gmd:MD_Identifier/gmd:code/gco:CharacterString"/>
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
        <xsl:for-each select="gmd:MD_DataIdentification/gmd:citation/gmd:CI_Citation/gmd:identifier/gmd:MD_Identifier">
            <xsl:variable name="codespace" select="gmd:code/gmd:CI_DateTypeCode/@codeSpace"/>
            <xsl:variable name="codevalue" select="gmd:code/gmd:CI_DateTypeCode/@codeListValue"/>
            <xsl:if test="string-length($codespace) > 0">
                <xsl:variable name="externalIdentifierId" select="concat( $urnCimExternalIdentifierIDPrefix, generate-id(.))"/>
                <rim:ExternalIdentifier id="{$externalIdentifierId}" registryObject="{$dataset-id}" identificationScheme="{$codespace}" value="{$codevalue}"/>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

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

    <xsl:template name="slot-hierarchyLevelName">
        <xsl:param name="hierarchyLevelName"/>
        <rim:Slot name="{$typeSlotName}" slotType="xsd:string">
            <rim:ValueList>
                <rim:Value>
                    <xsl:value-of select="$hierarchyLevelName"/>
                </rim:Value>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-identifier">
        <xsl:param name="identifier"/>
        <rim:Slot name="{$identifierSlotName}" slotType="xsd:string">
            <rim:ValueList>
                <rim:Value>
                    <xsl:value-of select="$identifier"/>
                </rim:Value>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-title">
        <xsl:param name="title"/>
        <rim:Slot name="{$titleSlotName}" slotType="xsd:string">
            <rim:ValueList>
                <rim:Value>
                    <xsl:value-of select="$title"/>
                </rim:Value>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-language">
        <xsl:param name="languageNode"/>
        <rim:Slot name="{$languageSlotName}" slotType="{$languageSlotType}">
            <rim:ValueList>
                <xsl:if test="$languageNode/gmd:LanguageCode/@codeListValue">
                    <rim:Value>
                        <xsl:value-of select="$languageNode/gmd:LanguageCode/@codeListValue"/>
                    </rim:Value>
                </xsl:if>
                <xsl:if test="$languageNode/gco:CharacterString">
                    <rim:Value>
                        <xsl:value-of select="$languageNode/gco:CharacterString"/>
                    </rim:Value>
                </xsl:if>
                <xsl:if test="$languageNode/gmd:CI_OnLineFunctionCode/@codeListValue">
                    <rim:Value>
                        <xsl:value-of select="$languageNode/gmd:CI_OnLineFunctionCode/@codeListValue"/>
                    </rim:Value>
                </xsl:if>
                <xsl:if test="$languageNode/gmd:CI_DateTypeCode/@codeListValue">
                    <rim:Value>
                        <xsl:value-of select="$languageNode/gmd:CI_DateTypeCode/@codeListValue"/>
                    </rim:Value>
                </xsl:if>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-date">
        <xsl:param name="dateStamp"/>
        <rim:Slot name="{$dateSlotName}" slotType="{$dateSlotType}">
            <rim:ValueList>
                <xsl:if test="$dateStamp/gco:Date">
                    <rim:Value>
                        <xsl:value-of select="$dateStamp/gco:Date"/>
                    </rim:Value>
                </xsl:if>
                <xsl:if test="$dateStamp/gco:DateTime">
                    <rim:Value>
                        <xsl:value-of select="$dateStamp/gco:DateTime"/>
                    </rim:Value>
                </xsl:if>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-conformsTo">
        <xsl:param name="metadataStandardName"/>
        <xsl:param name="metadataStandardVersion"/>
        <rim:Slot name="{$conformsToSlotName}" slotType="xsd:string">
            <rim:ValueList>
                <rim:Value>
                    <xsl:value-of select="$metadataStandardName"/>
                </rim:Value>
                <rim:Value>
                    <xsl:value-of select="$metadataStandardVersion"/>
                </rim:Value>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-resolution">
        <xsl:param name="uom"/>
        <xsl:param name="distance"/>
        <rim:Slot name="{$resolutionSlotName}" slotType="xsd:string">
            <rim:ValueList>
                <rim:Value>
                    <xsl:value-of select="$uom"/>
                </rim:Value>
                <rim:Value>
                    <xsl:value-of select="$distance"/>
                </rim:Value>
            </rim:ValueList>
        </rim:Slot>
    </xsl:template>

    <xsl:template name="slot-scaledenominator">
        <xsl:param name="scaleDenominator"/>
        <rim:Slot name="{$scaleDenominatorSlotName}" slotType="xsd:string">
            <rim:ValueList>
                <rim:Value>
                    <xsl:value-of select="$scaleDenominator"/>
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

    <xsl:template name="CitationInformation">
        <xsl:param name="citationInformationNode"/>
        <xsl:param name="citedItemEOId"/>
        <xsl:variable name="citedItemId" select="$citedItemEOId"/>
        <rim:ExtrinsicObject id="{$citedItemId}" objectType="{$citedItemObjectType}">
            <xsl:if test="$citationInformationNode/gmd:alternateTitle">
                <xsl:call-template name="slot-title">
                    <xsl:with-param name="title" select="$citationInformationNode/gmd:alternateTitle"/>
                </xsl:call-template>
            </xsl:if>
            <xsl:for-each select="$citationInformationNode/gmd:date">
                <xsl:call-template name="slot-date-with-type">
                    <xsl:with-param name="date" select="./gmd:CI_Date/gmd:date"/>
                    <xsl:with-param name="dateType" select="./gmd:CI_Date/gmd:dateType"/>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="$citationInformationNode/gmd:identifier">
                <xsl:variable name="codespace" select="gmd:RS_Identifier/gmd:codeSpace/gco:CharacterString"/>
                <xsl:variable name="codevalue" select="gmd:RS_Identifier/gmd:code/child::*/@codeListValue"/>
                <xsl:if test="$codespace">
                    <xsl:variable name="externalIdentifierId" select="concat( $urnCimExternalIdentifierIDPrefix, generate-id(.))"/>
                    <rim:ExternalIdentifier id="{$externalIdentifierId}" registryObject="{$citedItemId}" identificationScheme="{$codespace}" value="{$codevalue}"/>
                </xsl:if>
            </xsl:for-each>
            <xsl:if test="$citationInformationNode/gmd:title/gmx:Anchor/@xlink:href">
                <rim:Slot name="{$urlSlotName}" slotType="{$uriSlotType}">
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
            <rim:Organization id="{$organizationId}" objectType="{$organizationObjectType}">
                <rim:Name>
                    <xsl:variable name="name" select="gmd:CI_ResponsibleParty/gmd:organisationName/gco:CharacterString"/>
                    <rim:LocalizedString value="{$name}"/>
                </rim:Name>
            </rim:Organization>
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
        </xsl:for-each>
    </xsl:template>

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

    <xsl:template name="createDescriptiveKeywordsClassificationIdByNodes">
        <xsl:param name="classificationNodes"/>
        <xsl:for-each select="$classificationNodes//@id">
            <xsl:variable name="keywordSchemeClassificationId" select="concat( $urnCimKeywordSchemeClassificationIDPrefix, generate-id(.) )"/>
            <rim:Classification id="{$keywordSchemeClassificationId}" classifiedObject="{$resourceMetadataId}" classificationNode="{.}"/>
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

</xsl:stylesheet>
