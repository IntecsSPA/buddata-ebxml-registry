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

    <csw:GetRecords service="CSW" version="2.0.2" resultType="results" startPosition="{gc:cursor}" maxRecords="{gc:iteratorSize}" outputSchema="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:gml="http://www.opengis.net/gml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:dct="http://purl.org/dc/terms/" xmlns:ows="http://www.opengis.net/ows">
         <csw:Query typeNames="wrs:ExtrinsicObject rim:ExtrinsicObject_mi">
            <csw:ElementSetName typeNames="wrs:ExtrinsicObject"><xsl:value-of select="gc:presentation"/></csw:ElementSetName>
            <csw:Constraint version="1.1.0">
               <ogc:Filter>
                  <ogc:And>
                      <ogc:PropertyIsEqualTo>
                         <ogc:PropertyName>/wrs:ExtrinsicObject/@objectType</ogc:PropertyName>
                         <ogc:Literal>urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata</ogc:Literal>
                      </ogc:PropertyIsEqualTo>
                      <!-- <ogc:PropertyIsEqualTo>
                      <ogc:PropertyName>$mi/@objectType</ogc:PropertyName>
                        <ogc:Literal>urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::MetadataInformation</ogc:Literal>
                     </ogc:PropertyIsEqualTo>-->


                      <!-- BBOX FILTER  START -->
                      <xsl:if test="gc:bbox">
                        <xsl:variable name="W"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">W</xsl:with-param></xsl:call-template></xsl:variable>
                        <xsl:variable name="E"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">E</xsl:with-param></xsl:call-template></xsl:variable>
                        <xsl:variable name="N"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">N</xsl:with-param></xsl:call-template></xsl:variable>
                        <xsl:variable name="S"><xsl:call-template name="getCoordinate"><xsl:with-param name="bbox" select="gc:bbox"/><xsl:with-param name="format" select="$bboxFormat"/><xsl:with-param name="coordinate">S</xsl:with-param></xsl:call-template></xsl:variable>
                        <ogc:BBOX>
                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Slot[@name='http://purl.org/dc/terms/spatial']/wrs:ValueList/wrs:AnyValue[1]</ogc:PropertyName>
			    <gml:Envelope srsName="urn:ogc:def:crs:EPSG:6.3:4326">
				<gml:lowerCorner><xsl:value-of select="concat($S,' ',$W)"/></gml:lowerCorner>
				<gml:upperCorner><xsl:value-of select="concat($N,' ',$E)"/></gml:upperCorner>
			    </gml:Envelope>
			</ogc:BBOX>
                      </xsl:if>
                      <!-- BBOX FILTER  END -->

                      <!-- DATE FILTER START-->
                      <xsl:if test="gc:rangeDateStartDate"><!--/wrs:ExtrinsicObject/rim:Slot[@name='http://purl.org/dc/terms/temporal']/rim:ValueList/rim:Value[1]-->
			<ogc:PropertyIsGreaterThanOrEqualTo>
                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Slot[@name='http://purl.org/dc/terms/created']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
			    <ogc:Literal><xsl:value-of select="gc:rangeDateStartDate"/>T00:00:00Z</ogc:Literal>
			</ogc:PropertyIsGreaterThanOrEqualTo>
		      </xsl:if>

                      <xsl:if test="gc:rangeDateEndDate">
			<ogc:PropertyIsLessThanOrEqualTo>
                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Slot[@name='http://purl.org/dc/terms/created']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                            <ogc:Literal><xsl:value-of select="gc:rangeDateEndDate"/>T23:59:59Z</ogc:Literal>
			</ogc:PropertyIsLessThanOrEqualTo>
		      </xsl:if>
                      <!-- DATE FILTER END-->

                      <!-- FREE TEXT FILTER START-->
		      <xsl:if test="gc:freeText">
                        <ogc:Or>
                            <xsl:call-template name="freetextQueryBuilder">
				<xsl:with-param name="stringProcessing" select="gc:freeText"/>
				<xsl:with-param name="stringSeparator"><xsl:text> </xsl:text></xsl:with-param>
                            </xsl:call-template>
			</ogc:Or>
                      </xsl:if>
                      <!-- FREE TEXT FILTER END -->  					
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

  <xsl:template name="freetextQueryBuilder">
		<xsl:param name="stringProcessing"/>
		<xsl:param name="stringSeparator"/>
                <xsl:variable name="temp" select="normalize-space($stringProcessing)"></xsl:variable>
            <xsl:choose>
                <xsl:when test="contains($temp, $stringSeparator)">
                    <xsl:variable name="firstSubString" select="substring-before($temp,$stringSeparator)"/>
                    <xsl:variable name="stringRemaining" select="substring-after($temp,$stringSeparator)"/>
                            <xsl:if test="$firstSubString != '' ">
                                   <!-- <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/elements/1.1/coverage']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                    <!--<ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/elements/1.1/identifier']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                   <!-- <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/elements/1.1/language']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Name/rim:LocalizedString[@value]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>
                                    <!--<ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/terms/title']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>
                                    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/terms/references']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Description/rim:LocalizedString[@value]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>

                            </xsl:if>
                            <xsl:if test="$stringRemaining != '' ">
                                <xsl:call-template name="freetextQueryBuilder">
                                            <xsl:with-param name="stringProcessing" select="$stringRemaining"/>
                                            <xsl:with-param name="stringSeparator" select="$stringSeparator"/>
                                </xsl:call-template>
                            </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                                   <!-- <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/elements/1.1/coverage']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                    <!--<ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/elements/1.1/identifier']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                   <!-- <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/elements/1.1/language']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>-->
                                    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Name/rim:LocalizedString[@value]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>
                                <!--    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/terms/title']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>
                                    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>$mi/rim:Slot[@name='http://purl.org/dc/terms/references']/rim:ValueList/rim:Value[1]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike> -->
                                    <ogc:PropertyIsLike wildCard="%">
                                            <ogc:PropertyName>/wrs:ExtrinsicObject/rim:Description/rim:LocalizedString[@value]</ogc:PropertyName>
                                            <ogc:Literal>%<xsl:value-of select="$temp"/>%</ogc:Literal>
                                    </ogc:PropertyIsLike>
                                    
                </xsl:otherwise>
           </xsl:choose>
	</xsl:template>
</xsl:stylesheet>
