<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes"
	xmlns:csw="http://www.opengis.net/cat/csw/2.0.2"
	xmlns:ogc="http://www.opengis.net/ogc"
	xmlns:util="http://www.intecs.pisa/util"
	xmlns:wrs="http://www.opengis.net/cat/wrs/1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:gmd="http://www.isotc211.org/2005/gmd"
	exclude-result-prefixes="xs fn xdt util">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />
	<!-- template matching the GetRecords root-->
	<xsl:variable name="inspire_queryables"
		select="document('inspire_queryables_2_CIM.xml')" />
	<xsl:template match="/">
		<GetRecordsResponse xmlns="http://www.opengis.net/cat/csw/2.0.2"
			xmlns:ogc="http://www.opengis.net/ogc"
			xmlns:ows="http://www.opengis.net/ows"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<RequestId><xsl:value-of select="generate-id()"/></RequestId>
			<SearchStatus timestamp="{fn:current-dateTime()}"/>
			<SearchResults 
				elementSet="full" 
				numberOfRecordsMatched="{//csw:SearchResults/@numberOfRecordsMatched}" 
				numberOfRecordsReturned="{//csw:SearchResults/@numberOfRecordsReturned}"
				nextRecord="{//csw:SearchResults/@nextRecord}">
				<xsl:for-each select="//wrs:repositoryItemRef">
					<xsl:variable name="url">
						<xsl:value-of select="@xlink:href" />
					</xsl:variable>
					<xsl:variable name="metadata" select="document($url)" />
					<gmd:MD_Metadata>
						<xsl:copy-of select="$metadata/gmd:MD_Metadata/*" />			
					</gmd:MD_Metadata>
				</xsl:for-each>
			</SearchResults>
		</GetRecordsResponse>
	</xsl:template>
</xsl:stylesheet>
