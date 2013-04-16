<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
version="2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema" 
xmlns:fn="http://www.w3.org/2005/02/xpath-functions" 
xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" 
xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:util="http://www.intecs.pisa/util" exclude-result-prefixes="xs fn xdt util">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<csw:GetRecords service="CSW" version="2.0.2" resultType="results" startPosition="1" maxRecords="100" outputSchema="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:wrs="http://www.opengis.net/cat/wrs/1.0" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:gml="http://www.opengis.net/gml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:dct="http://purl.org/dc/terms/" xmlns:ows="http://www.opengis.net/ows">
			<csw:Query typeNames="wrs:ExtrinsicObject_rm rim:Association_rmi rim:ExtrinsicObject_md">
				<csw:ElementSetName typeNames="wrs:ExtrinsicObject_rm">full</csw:ElementSetName>
				<csw:Constraint version="1.1.0">
					<ogc:Filter>
						<ogc:And>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>$md/@objectType</ogc:PropertyName>
								<ogc:Literal>urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::MetadataInformation</ogc:Literal>
							</ogc:PropertyIsEqualTo>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>$md/rim:Slot[@name="http://purl.org/dc/elements/1.1/identifier"]/rim:ValueList/rim:Value[1]</ogc:PropertyName>
								<ogc:Literal><xsl:value-of select="//csw:Id"/></ogc:Literal>
							</ogc:PropertyIsEqualTo>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>$rmi/@associationType</ogc:PropertyName>
								<ogc:Literal>urn:ogc:def:associationType:OGC-CSW-ebRIM-CIM::ResourceMetadataInformation</ogc:Literal>
							</ogc:PropertyIsEqualTo>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>$rmi/@sourceObject</ogc:PropertyName>
								<ogc:PropertyName>$rm/@id</ogc:PropertyName>
							</ogc:PropertyIsEqualTo>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>$rmi/@targetObject</ogc:PropertyName>
								<ogc:PropertyName>$md/@id</ogc:PropertyName>
							</ogc:PropertyIsEqualTo>
						</ogc:And>
					</ogc:Filter>
				</csw:Constraint>
			</csw:Query>
		</csw:GetRecords>
	</xsl:template>
</xsl:stylesheet>
