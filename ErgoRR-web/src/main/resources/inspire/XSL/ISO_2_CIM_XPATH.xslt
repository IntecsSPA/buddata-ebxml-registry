<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:util="http://www.intecs.pisa/util" exclude-result-prefixes="xs fn xdt util">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
        <xsl:variable name="xpathIsoCim">
            <xpathList>
                <xpath>
                    <cim>rim:ExtrinsicObject/rim:Slot[@name="http://purl.org/dc/elements/1.1/identifier"]/rim:ValueList/rim:Value[1]</cim>
                    <iso>/gmd:MD_Metadata/gmd:fileIdentifier<iso>
                </xpath>
            
            </xpathList>                
        </xsl:variable>
        
        
        
        
        <xsl:template name="getCIMPath">
            <xsl:param name="xpathISO"/>
            
            <xsl:when test="$xpathIsoCim//xpathList/xpath[iso = $xpathISO]">
                <xsl:value-of select="$xpathIsoCim//xpathList/xpath[iso = $xpathISO]/cim"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$xpathISO"/>
            </xsl:otherwise>    
        </xsl:template>    
	
</xsl:stylesheet>
