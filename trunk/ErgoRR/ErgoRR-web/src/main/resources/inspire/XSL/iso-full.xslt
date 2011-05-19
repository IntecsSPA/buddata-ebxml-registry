<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
										xmlns:gml="http://www.opengis.net/gml"
										xmlns:csw="http://www.opengis.net/cat/csw/2.0.2"
										xmlns:dc ="http://purl.org/dc/elements/1.1/"
										xmlns:dct="http://purl.org/dc/terms/"
										xmlns:gmd="http://www.isotc211.org/2005/gmd"
										xmlns:gco="http://www.isotc211.org/2005/gco"
										xmlns:ows="http://www.opengis.net/ows"
										xmlns:geonet="http://www.fao.org/geonetwork">

	<xsl:param name="displayInfo"/>
	
	<!-- ============================================================================= -->
	<!-- Rename all ISO profil extension -->
	
	<xsl:template match="*[@gco:isoType]" priority="100">
		<xsl:element name="{@gco:isoType}">
			<xsl:attribute name="namespace">
				<!-- FIXME : Map element prefix to namespace,
					creating attribute with prefix does not set namespace.
					For now only gmd's element are extended by profile.
				-->
				<xsl:choose>
					<xsl:when test="contains(@gco:isoType, 'gmd')">http://www.isotc211.org/2005/gmd</xsl:when>
				</xsl:choose>
			</xsl:attribute>
			<xsl:apply-templates select="*"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="gml:TimePeriod">
		<xsl:variable name="id" select="generate-id(.)"/>
		<gml:TimePeriod gml:id="{$id}">
			<xsl:copy-of select="*" />			
		</gml:TimePeriod>
    </xsl:template>
	
	<!-- ============================================================================= -->

	<xsl:template match="@*|node()">
		<xsl:variable name="info" select="geonet:info"/>
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

	<!-- ============================================================================= -->
</xsl:stylesheet>