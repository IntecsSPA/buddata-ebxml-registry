<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
version="2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema" 
xmlns:ns5="http://purl.org/dc/elements/1.1/" 
xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" 
xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:util="http://www.intecs.pisa/util"> <!-- exclude-result-prefixes="xs fn xdt util" -->
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
    <xsl:param name="transactionOperation">Insert</xsl:param>
    <xsl:template match="/">
            <csw:TransactionResponse xmlns:csw="http://www.opengis.net/cat/csw/2.0.2">
                <xsl:choose>
                    <xsl:when test="$transactionOperation = 'Insert'">
                        <xsl:choose>
                            <xsl:when test="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']/ns5:identifier">
                                <csw:TransactionSummary>
                                    <csw:totalInserted><xsl:value-of select="count(//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']/ns5:identifier)"/></csw:totalInserted>
                                    <csw:totalUpdated>0</csw:totalUpdated>
                                    <csw:totalDeleted>0</csw:totalDeleted>
                                </csw:TransactionSummary>
                                <csw:InsertResult>
                                    <xsl:for-each select="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::DataMetadata']/ns5:identifier">
                                        <csw:BriefRecord>
                                            <identifier><xsl:value-of select="."/></identifier>
                                        </csw:BriefRecord>
                                    </xsl:for-each>
                                </csw:InsertResult>
                           </xsl:when>  
                           <xsl:when test="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::ServiceMetadata']/ns5:identifier">
                                <csw:TransactionSummary>
                                    <csw:totalInserted><xsl:value-of select="count(//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::ServiceMetadata']/ns5:identifier)"/></csw:totalInserted>
                                    <csw:totalUpdated>0</csw:totalUpdated>
                                    <csw:totalDeleted>0</csw:totalDeleted>
                                </csw:TransactionSummary>
                                <csw:InsertResult>
                                    <xsl:for-each select="//csw:BriefRecord[ns5:type = 'urn:ogc:def:objectType:OGC-CSW-ebRIM-CIM::ServiceMetadata']/ns5:identifier">
                                        <csw:BriefRecord>
                                            <identifier><xsl:value-of select="."/></identifier>
                                        </csw:BriefRecord>
                                    </xsl:for-each>    
                                </csw:InsertResult>
                           </xsl:when> 
                        </xsl:choose> 
                    </xsl:when>
                </xsl:choose> 
            </csw:TransactionResponse> 
    </xsl:template>
    
    <xsl:template match="csw:Delete">
      <!--  "<ns:Transaction service=\"CSW\" version=\"2.0.2\" verboseResponse=\"true\">"+
                 "<ns:Delete typeName=\"rim:RegistryPackage\">"+
                    "<ns:Constraint version=\"1.1.0\">"+
                       "<ogc:Filter>"+
                          "<ogc:And>"+
                             "<ogc:PropertyIsEqualTo>"+
                                "<ogc:PropertyName>/rim:RegistryPackage/@id</ogc:PropertyName>"+
                                "<ogc:Literal>"+catId+"</ogc:Literal>"+
                             "</ogc:PropertyIsEqualTo>"+
                          "</ogc:And>"+
                       "</ogc:Filter>"+
                    "</ns:Constraint>"+
                 "</ns:Delete>"+
              "</ns:Transaction>" -->
   </xsl:template>           
</xsl:stylesheet>
