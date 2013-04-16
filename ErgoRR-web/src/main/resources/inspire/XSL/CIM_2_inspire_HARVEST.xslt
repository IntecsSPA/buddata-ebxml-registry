<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
version="2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema" 
xmlns:ns5="http://purl.org/dc/elements/1.1/" 
xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" 
xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:util="http://www.intecs.pisa/util"> <!-- exclude-result-prefixes="xs fn xdt util" -->
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <csw:HarvestResponse>
            <csw:TransactionResponse xmlns:csw="http://www.opengis.net/cat/csw/2.0.2">
                <xsl:choose>
                    <xsl:when test="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']/ns5:identifier">
                        <csw:TransactionSummary>
                            <csw:totalInserted>1</csw:totalInserted>
                            <csw:totalUpdated>0</csw:totalUpdated>
                            <csw:totalDeleted>0</csw:totalDeleted>
                        </csw:TransactionSummary>
                        <csw:InsertResult>
                            <csw:BriefRecord>
                                <identifier><xsl:value-of select="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']/ns5:identifier"/></identifier>
                            </csw:BriefRecord>
                        </csw:InsertResult>
                   </xsl:when>  
                   <xsl:when test="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::ServiceMetadata']/ns5:identifier">
                        <csw:TransactionSummary>
                            <csw:totalInserted>1</csw:totalInserted>
                            <csw:totalUpdated>0</csw:totalUpdated>
                            <csw:totalDeleted>0</csw:totalDeleted>
                        </csw:TransactionSummary>
                        <csw:InsertResult>
                            <csw:BriefRecord>
                                <identifier><xsl:value-of select="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']/ns5:identifier"/></identifier>
                            </csw:BriefRecord>
                        </csw:InsertResult>
                   </xsl:when> 
                </xsl:choose>    
            </csw:TransactionResponse> 
        </csw:HarvestResponse>
    </xsl:template>
</xsl:stylesheet>
