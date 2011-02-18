<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:gc="http://gisclient.pisa.intecs.it"
xmlns:csw="http://www.opengis.net/cat/csw/2.0.2"
xmlns:ogc="http://www.opengis.net/ogc"
xmlns:gml="http://www.opengis.net/gml"
xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0">
  <xsl:output method="xml" omit-xml-declaration="no" encoding="utf-8" indent="no"/> 
  <xsl:param name="bboxFormat">W,S,E,N</xsl:param>
  <xsl:template match="gc:KeyValues">


<csw:GetRecords
outputSchema="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
service="CSW" version="2.0.2"
resultType="results"
startPosition="1"
maxRecords="20">
    <xsl:attribute name="startPosition"><xsl:value-of select="gc:cursor"/></xsl:attribute>
    <xsl:attribute name="maxRecords"><xsl:value-of select="gc:iteratorSize"/></xsl:attribute>
    <csw:Query typeNames="rim:ExtrinsicObject rim:ExtrinsicObject_ACQPLAT rim:Association">
        <csw:ElementSetName typeNames="rim:ExtrinsicObject"><xsl:value-of select="gc:presentation"/></csw:ElementSetName>
        <csw:Constraint version="1.1.0">
            <ogc:Filter>
                <ogc:And>
					<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>/wrs:ExtrinsicObject/@objectType</ogc:PropertyName>
							<ogc:Literal>urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct</ogc:Literal>
					</ogc:PropertyIsEqualTo>												
                    <xsl:if test="gc:bbox">
                    <xsl:variable name="W"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">W</xsl:with-param></xsl:call-template></xsl:variable>
                    <xsl:variable name="E"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">E</xsl:with-param></xsl:call-template></xsl:variable>
                    <xsl:variable name="N"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">N</xsl:with-param></xsl:call-template></xsl:variable>
                    <xsl:variable name="S"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">S</xsl:with-param></xsl:call-template></xsl:variable>
                    <ogc:BBOX>
                        <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf']/wrs:ValueList/wrs:AnyValue[1]</ogc:PropertyName>
                        <gml:Envelope  srsName="urn:ogc:def:crs:EPSG:6.3:4326">
                            <gml:lowerCorner><xsl:value-of select="concat($S,' ',$W)"/></gml:lowerCorner>
                            <gml:upperCorner><xsl:value-of select="concat($N,' ',$E)"/></gml:upperCorner>
                        </gml:Envelope>
                    </ogc:BBOX>
                    </xsl:if>
                    <xsl:if test="gc:rangeDateStartDate">
                        <ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:endPosition']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal><xsl:value-of select="gc:rangeDateStartDate"/>T00:00:00Z</ogc:Literal>
                        </ogc:PropertyIsGreaterThanOrEqualTo>
                    </xsl:if>
                    <xsl:if test="gc:rangeDateEndDate">
                        <ogc:PropertyIsLessThanOrEqualTo>
                            <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:beginPosition']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal><xsl:value-of select="gc:rangeDateEndDate"/>T23:59:59Z</ogc:Literal>
                        </ogc:PropertyIsLessThanOrEqualTo>
                    </xsl:if>

                    <xsl:if test="gc:collectionId">
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:parentIdentifier']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal>urn:ogc:def:EOP:<xsl:value-of select="gc:collectionId"/></ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </xsl:if>

<!-- Completare la filter con i nomi degli slots-->
                    <!--xsl:if test="gc:platfSNm">
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:parentIdentifier']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal><xsl:value-of select="gc:platfSNm"/></ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </xsl:if>
                    <xsl:if test="gc:platfSer">
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:parentIdentifier']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal><xsl:value-of select="gc:platfSer"/></ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </xsl:if>
                    <xsl:if test="gc:cloudCovePerc">
                        <ogc:PropertyIsLessThanOrEqualTo>
                            <ogc:PropertyName>/rim:ExtrinsicObject[@objectType='urn:x-ogc:specification:csw-ebrim:ObjectType:EO:EOProduct']/rim:Slot[@name='urn:ogc:def:ebRIM-Slot:OGC-06-131:endPosition']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal><xsl:value-of select="gc:cloudCovePerc"/></ogc:Literal>
                        </ogc:PropertyIsLessThanOrEqualTo>
                    </xsl:if-->
                </ogc:And>
            </ogc:Filter>
        </csw:Constraint>
    </csw:Query>
</csw:GetRecords>
  </xsl:template>
  <xsl:template name="getCoordinate">
    <xsl:param name="bbox"/>
    <xsl:param name="format"/>
    <xsl:param name="coordinate"/>
    <xsl:variable name="firstBbox" select="substring-before($bbox, ',')"/>
    <xsl:variable name="firstFormat" select="substring-before($format, ',')"/>
    <xsl:variable name="remainingBbox" select="substring-after($bbox, ',')"/>
    <xsl:variable name="remainingFormat" select="substring-after($format, ',')"/>
    <xsl:choose>
    <xsl:when test="$format = $coordinate">
      <xsl:value-of select="$bbox"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$firstFormat = $coordinate">
          <xsl:value-of select="$firstBbox"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="getCoordinate">
            <xsl:with-param name="bbox" select="$remainingBbox"/>
            <xsl:with-param name="format" select="$remainingFormat"/>
            <xsl:with-param name="coordinate" select="$coordinate"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>
