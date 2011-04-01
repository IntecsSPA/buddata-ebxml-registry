<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:gml="http://www.opengis.net/gml" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.0">
  <xsl:output method="xml" omit-xml-declaration="no" encoding="utf-8" indent="no"/>
  <xsl:template match="/SoapFault">
         <xsl:copy-of select="*"></xsl:copy-of>
  </xsl:template>
  <xsl:template match="//csw:GetRecordsResponse">
    <response>
        <results><xsl:value-of select="csw:SearchResults/@numberOfRecordsMatched"/></results>
        <retrievedData>
            <xsl:apply-templates select="csw:SearchResults/wrs:ExtrinsicObject[@objectType='urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']"/>
        </retrievedData>
    </response>
  </xsl:template>
  <xsl:template match="wrs:ExtrinsicObject">
    <xsl:variable name="Id" select='@id'/>
    <Metadata>
        <resourceMetadataId><xsl:value-of select="$Id"/></resourceMetadataId>
        <xsl:apply-templates select="rim:Slot"></xsl:apply-templates>
        <xsl:apply-templates select="rim:Name"></xsl:apply-templates>
        <xsl:apply-templates select="rim:Description"></xsl:apply-templates>
        <xsl:apply-templates select="wrs:repositoryItemRef"></xsl:apply-templates>
        <xsl:apply-templates select="../rim:Association[@sourceObject=$Id]"></xsl:apply-templates>
        <!--<xsl:if test="rim:ExternalIdentifier">
            <xsl:call-template name="extractIDInfo">
              <xsl:with-param name="idEtx" select="rim:ExternalIdentifier/@value"/>
              <xsl:with-param name="infoList"><xsl:text>dataType:null:variables:</xsl:text></xsl:with-param>
            </xsl:call-template>
        </xsl:if>-->
    </Metadata>
  </xsl:template>
    <xsl:template match="rim:Slot">
      <xsl:choose>
         <!-- <xsl:when test="@name='http://purl.org/dc/elements/1.1/identifier'">
				<itemId>urn:CIM:<xsl:value-of select="rim:ValueList/rim:Value"/>:ExtrinsicObject:MetadataInformation</itemId>
				<identifier><xsl:value-of select="rim:ValueList/rim:Value"/></identifier>
          </xsl:when> -->
          <xsl:when test="@name='http://purl.org/dc/terms/spatial'">
				<spatial><xsl:call-template name="polygonStringByBBOX">
                                            <xsl:with-param name="lCorner" select="//gml:lowerCorner"/>
                                            <xsl:with-param name="uCorner" select="//gml:upperCorner"/>
                                         </xsl:call-template><!--sl:value-of select="//gml:lowerCorner"/>,<xsl:value-of select="//gml:upperCorner"/--></spatial>
          </xsl:when>
         <!-- <xsl:when test="@name='http://purl.org/dc/elements/1.1/coverage'">
				<coverage><xsl:value-of select="rim:ValueList/rim:Value"/></coverage>
          </xsl:when>
          <xsl:when test="@name='http://purl.org/dc/terms/title'">
				<source><xsl:value-of select="rim:ValueList/rim:Value"/></source>
          </xsl:when>
          <xsl:when test="@name='http://purl.org/dc/terms/created'">
				<created><xsl:value-of select="rim:ValueList/rim:Value"/></created>
          </xsl:when>
          <xsl:when test="@name='http://purl.org/dc/elements/1.1/language'">
				<language><xsl:value-of select="rim:ValueList/rim:Value"/></language>
          </xsl:when>
          <xsl:when test="@name='http://purl.org/dc/terms/temporal'">
				<temporal><xsl:value-of select="rim:ValueList/rim:Value"/></temporal>
          </xsl:when>!-->

          <xsl:otherwise></xsl:otherwise>
      </xsl:choose>
    </xsl:template>
    <xsl:template match="rim:Name">
		<name><xsl:value-of select="rim:LocalizedString/@value"/></name>
    </xsl:template>
    <xsl:template match="rim:Description">
		<description><xsl:value-of select="rim:LocalizedString/@value"/></description>
    </xsl:template>
    
    <xsl:template match="wrs:repositoryItemRef">
        <metadataURL><xsl:value-of select="@xlink:href"/></metadataURL>
    </xsl:template>

   

   <xsl:template name="polygonStringByBBOX">
    <xsl:param name="lCorner"/>
    <xsl:param name="uCorner"/>

      <xsl:variable name="north" select="substring-before($uCorner,' ')"/>
      <xsl:variable name="east" select="substring-after($uCorner,' ')"/>
      <xsl:variable name="south" select="substring-before($lCorner,' ')"/>
      <xsl:variable name="west" select="substring-after($lCorner,' ')"/>
      <xsl:variable name="polygonString">
        <xsl:value-of select='$south'/>,<xsl:value-of select='$west'/><xsl:text> </xsl:text>
        <xsl:value-of select='$north'/>,<xsl:value-of select='$west'/><xsl:text> </xsl:text>
        <xsl:value-of select='$north'/>,<xsl:value-of select='$east'/><xsl:text> </xsl:text>
        <xsl:value-of select='$south'/>,<xsl:value-of select='$east'/></xsl:variable>
      <xsl:value-of select="$polygonString"/>
  </xsl:template>

  <xsl:template name="extractIDInfo">
      <xsl:param name="idEtx"/>
      <xsl:param name="infoList"/>

        <xsl:variable name="separator"><xsl:text>:</xsl:text></xsl:variable>

         <xsl:variable name="firstInfo" select="substring-before($idEtx,$separator)"/>
         <xsl:variable name="remainingInfo" select="substring-after($idEtx,$separator)"/>
         <xsl:variable name="firstInfoName" select="substring-before($infoList,$separator)"/>
         <xsl:variable name="remainingInfoNames" select="substring-after($infoList,$separator)"/>

      <xsl:if test="$firstInfoName != '' and $firstInfo!='' and $firstInfoName != 'null'">
        <xsl:element name="{$firstInfoName}">
            <xsl:value-of select="$firstInfo"/>
        </xsl:element>
      </xsl:if>
      <xsl:if test="$remainingInfo != '' and $remainingInfoNames != '' ">
        <xsl:call-template name="extractIDInfo">
          <xsl:with-param name="idEtx" select="$remainingInfo"/>
          <xsl:with-param name="infoList" select="$remainingInfoNames"/>
        </xsl:call-template>
      </xsl:if>
  </xsl:template>

</xsl:stylesheet>