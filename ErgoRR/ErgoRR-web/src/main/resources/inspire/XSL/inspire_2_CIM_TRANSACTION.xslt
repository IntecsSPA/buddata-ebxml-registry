<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" xmlns:ogc="http://www.opengis.net/ogc" xmlns:util="http://www.intecs.pisa/util" exclude-result-prefixes="xs fn xdt util">
	<xsl:param name="inspire_config">inspire_queryables_2_CIM.xml</xsl:param>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<xsl:variable name="inspire_queryables" select="document($inspire_config)"/>
	<xsl:variable name="isAService">
		<xsl:choose>
			<!-- 
			The possible values for the Type property name are the following:
				- dataset
				- datasetcollection
				- service
				- application
		 -->
			<xsl:when test="//ogc:PropertyIsEqualTo[substring-after(ogc:PropertyName, ':') = 'type' ]/ogc:Literal = 'service' ">
				<xsl:value-of select=" 'yes' "/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select=" 'no' "/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<!-- template matching the GetRecords root-->
	<xsl:template match="csw:Transaction">
		<xsl:copy>
			<xsl:call-template name="changeAttributeValue">
				<xsl:with-param name="listOfAttributes" select="./@*"/>
				<xsl:with-param name="attrName" select=" 'outputSchema' "/>
				<xsl:with-param name="attrValue" select=" 'urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' "/>
			</xsl:call-template>
			<xsl:namespace name="wrs">http://www.opengis.net/cat/wrs/1.0</xsl:namespace>
			<xsl:namespace name="rim">urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0</xsl:namespace>
			<xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<!-- template matching the Delete element -->
	<xsl:template match="csw:Delete">
		<xsl:variable name="CIM_Filter">
			<filter>
				<xsl:apply-templates select="//ogc:Filter"/>
			</filter>
		</xsl:variable>
		<xsl:copy>
			<!-- change the attribute typeNames of the Query element -->
			<xsl:variable name="tmpAliases" select="distinct-values($CIM_Filter//ogc:PropertyName/substring-after(substring-before(text(), '/'), '$'))"/>
			<xsl:variable name="aliasForResourceMetadata" select="$inspire_queryables//inspire[cim/@typeName = 'wrs:ExtrinsicObject'][1]/cim/@alias"/>
			<xsl:variable name="aliases" select="if (empty(index-of($tmpAliases, $aliasForResourceMetadata))) then insert-before($tmpAliases, 0, $aliasForResourceMetadata) else $tmpAliases"/>
			<xsl:variable name="typeNameList">
				<typeNames>
					<typeName>wrs:ExtrinsicObject</typeName>
					<typeName>rim:ExtrinsicObject</typeName>
					<typeName>rim:Organization</typeName>
					<typeName>rim:Association</typeName>
					<typeName>rim:Classification</typeName>
				</typeNames>
			</xsl:variable>
			<xsl:attribute name="typeNames">
				<xsl:for-each select="$typeNameList//typeName">
					<xsl:variable name="tmpTypeName" select="util:createTypeName($aliases, .)"/>
					<xsl:if test="not(empty($tmpTypeName))">
						<xsl:if test="position()!=1">
							<xsl:value-of select=" ' ' "/>
						</xsl:if>
						<xsl:value-of select="$tmpTypeName"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:attribute>
			<xsl:for-each select="csw:ElementSetName">
				<xsl:copy>
					<xsl:attribute name="typeNames">
						<xsl:value-of select="util:createTypeName($aliases, 'wrs:ExtrinsicObject')"/>
					</xsl:attribute>
					<xsl:value-of select=" 'brief' "/>
				</xsl:copy>
			</xsl:for-each>
			<xsl:variable name="CIM_Additions">
				<additions>
					<xsl:for-each select="$aliases">
						<xsl:variable name="alias">
							<xsl:value-of select="."/>
						</xsl:variable>
						<xsl:variable name="cim" select="$inspire_queryables//inspire[cim/@alias = $alias][1]/cim"/>
						<xsl:if test="$cim/@typeName = 'wrs:ExtrinsicObject' ">
							<!-- Building the object type of the mapped object-->
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$' , $cim/@alias, '/@objectType')"/>
								</ogc:PropertyName>
								<ogc:Literal>
									<xsl:choose>
										<xsl:when test=" $isAService = 'yes' ">
											<xsl:value-of select="concat($cim/@prefix, 'ServiceMetadata')"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="concat($cim/@prefix, 'DataMetadata')"/>
										</xsl:otherwise>
									</xsl:choose>
								</ogc:Literal>
							</ogc:PropertyIsEqualTo>
							<!-- The only main extrinsic objects of interest are those derived from an harvest and then they have
                                   the Name (queryable) child not null, the repositoryItemRef (not queryable) not null-->
							<ogc:PropertyIsLike escapeChar="\" singleChar="?" wildCard="*">
								<ogc:PropertyName>
									<xsl:value-of select="concat('$' , $cim/@alias, '/rim:Name/rim:LocalizedString/@value')"/>
								</ogc:PropertyName>
								<ogc:Literal>*</ogc:Literal>
							</ogc:PropertyIsLike>
						</xsl:if>
						<xsl:if test="$cim/@typeName = 'rim:ExtrinsicObject' or $cim/@typeName = 'rim:Organization' ">
							<!-- Building the object type of the mapped object-->
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$' , $cim/@alias, '/@objectType')"/>
								</ogc:PropertyName>
								<ogc:Literal>
									<xsl:value-of select="concat($cim/@prefix, $cim/@objectType)"/>
								</ogc:Literal>
							</ogc:PropertyIsEqualTo>
						</xsl:if>
						<xsl:if test="$cim/@typeName = 'rim:ExtrinsicObject' or $cim/@typeName = 'rim:Organization'">
							<!-- 
								If the mapping is to an extrinsic object other than the resource metadata, we need to create
								the association between this extrinsic object and the resource metadata
							-->
							<!-- Specify the type of the association -->
							<xsl:variable name="associationAlias" select="$cim/targetOf/@alias"/>
							<xsl:variable name="associationType" select="$cim/targetOf/@objectType"/>
							<xsl:variable name="associationPrefix" select="$cim/targetOf/@prefix"/>
							<xsl:variable name="sourceObject" select="$cim/targetOf/@source"/>
							<xsl:variable name="sourceObjectAlias" select="$inspire_queryables//inspire[cim/@objectType = $sourceObject][1]/cim/@alias"/>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$' , $associationAlias, '/@objectType')"/>
								</ogc:PropertyName>
								<ogc:Literal>
									<xsl:value-of select="concat($associationPrefix, $associationType)"/>
								</ogc:Literal>
							</ogc:PropertyIsEqualTo>
							<!-- Specify the source of the association -->
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$',  $associationAlias, '/', '@sourceObject')"/>
								</ogc:PropertyName>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$',  $sourceObjectAlias, '/', '@id')"/>
								</ogc:PropertyName>
							</ogc:PropertyIsEqualTo>
							<!-- Specify the target of the association -->
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$',  $associationAlias, '/', '@targetObject')"/>
								</ogc:PropertyName>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$',  $cim/@alias, '/', '@id')"/>
								</ogc:PropertyName>
							</ogc:PropertyIsEqualTo>
						</xsl:if>
						<xsl:if test="$cim/@typeName = 'rim:Classification' ">
							<!--
							If the mapping is a classification, we shall tie the classification to the classified object
							and shall specify the object type of the classification object (may be missing)
							 -->
							<!-- Building the association between the classification and the classified object -->
							<xsl:variable name="classifiedObject" select="$cim/attribute[@type = 'classifiedObject']"/>
							<xsl:variable name="classifiedObjectAlias" select="$inspire_queryables//inspire[cim/@objectType = $classifiedObject][1]/cim/@alias"/>
							<xsl:variable name="classifiedObjectType" select="$inspire_queryables//inspire[cim/@objectType = $classifiedObject][1]/cim/@objectType"/>
							<xsl:variable name="classifiedObjectPrefix" select="$inspire_queryables//inspire[cim/@objectType = $classifiedObject][1]/cim/@prefix"/>
							<ogc:PropertyIsEqualTo>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$',  $cim/@alias, '/', '@classifiedObject')"/>
								</ogc:PropertyName>
								<ogc:PropertyName>
									<xsl:value-of select="concat('$',  $classifiedObjectAlias, '/', '@id')"/>
								</ogc:PropertyName>
							</ogc:PropertyIsEqualTo>
							<!-- Building the object type of the classified object-->
							<xsl:if test="empty(index-of($aliases, $classifiedObjectAlias))">
								<ogc:PropertyIsEqualTo>
									<ogc:PropertyName>
										<xsl:value-of select="concat('$' , $classifiedObjectAlias, '/@objectType')"/>
									</ogc:PropertyName>
									<ogc:Literal>
										<xsl:value-of select="concat($classifiedObjectPrefix, $classifiedObjectType)"/>
									</ogc:Literal>
								</ogc:PropertyIsEqualTo>
							</xsl:if>
						</xsl:if>
					</xsl:for-each>
				</additions>
			</xsl:variable>
			<!-- change attribute typeNames of the ElementSetName -->
			<xsl:for-each select="csw:Constraint">
				<xsl:copy>
					<xsl:apply-templates select="@*"/>
					<xsl:call-template name="createFilter">
						<xsl:with-param name="CIM_Filter" select="$CIM_Filter"/>
						<xsl:with-param name="CIM_Additions" select="$CIM_Additions"/>
					</xsl:call-template>
				</xsl:copy>
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<!-- template matching the INSPIRE queryable name -->
	<xsl:template match="//*[ogc:PropertyName]">
		<xsl:variable name="propertyValue">
			<xsl:variable name="propertyName" select="ogc:PropertyName/text()"/>
			<xsl:value-of select="substring-after($propertyName, ':')"/>
		</xsl:variable>
		<xsl:variable name="cim" select="$inspire_queryables//inspire[apiso = $propertyValue]/cim"/>
		<xsl:if test="not(empty($cim))">
			<xsl:copy>
			   <xsl:copy-of select="@*"/>
				<xsl:for-each select="ogc:PropertyName">
					<!--  The possibile typeName of the mapped objects are the following:
							wrs:ExtrinsicObject, rim:ExtrinsicObject, rim:Organization
							rim:Classification, rim:Association
					-->
					<xsl:if test="$cim/@typeName = 'wrs:ExtrinsicObject' or $cim/@typeName = 'rim:ExtrinsicObject' or $cim/@typeName = 'rim:Organization'">
						<xsl:copy>
							<xsl:choose>
								<xsl:when test="$cim/child/@type = 'Slot' ">
									<xsl:choose>
										<xsl:when test="$cim/child/text() = 'http://purl.org/dc/terms/spatial' ">
											<xsl:value-of select="concat('$', $cim/@alias, '/', 'rim:Slot[@name=&quot;',  $cim/child, '&quot;]/wrs:ValueList/wrs:AnyValue[1]')"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="concat('$', $cim/@alias, '/', 'rim:Slot[@name=&quot;',  $cim/child, '&quot;]/rim:ValueList/rim:Value[1]')"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
								<xsl:when test="$cim/child/@type = 'Name' ">
									<xsl:value-of select="concat('$', $cim/@alias, '/', 'rim:Name/rim:LocalizedString/@value') "/>
								</xsl:when>
								<xsl:when test="$cim/child/@type = 'Description' ">
									<xsl:value-of select="concat('$',  $cim/@alias, '/', 'rim:Description/rim:LocalizedString/@value') "/>
								</xsl:when>
							</xsl:choose>
						</xsl:copy>
						<xsl:apply-templates select="./following-sibling::*"/>
					</xsl:if>
					<xsl:if test="$cim/@typeName = 'rim:Classification' ">
						<xsl:copy>
							<xsl:value-of select="concat('$',  $cim/@alias, '/', '@classificationNode')"/>
						</xsl:copy>
						<xsl:for-each select="./following-sibling::*">
							<xsl:copy>
								<xsl:value-of select="concat($cim/attribute[@type = 'classificationScheme'], ':', .)"/>
							</xsl:copy>
						</xsl:for-each>
					</xsl:if>
				</xsl:for-each>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<!-- template for copying an element together with its attributes, but changing the value of one them-->
	<xsl:template name="changeAttributeValue">
		<xsl:param name="listOfAttributes"/>
		<xsl:param name="attrName"/>
		<xsl:param name="attrValue"/>
		<xsl:for-each select="$listOfAttributes">
			<xsl:choose>
				<xsl:when test="name()= $attrName ">
					<xsl:attribute name="{name(.)}">
						<xsl:value-of select="$attrValue"/>
					</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<!-- template for copying the nodes and attributes -->
	<xsl:template match="node() | @* ">
		<xsl:copy>
			<xsl:apply-templates select="node() | @* "/>
		</xsl:copy>
	</xsl:template>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<xsl:function name="util:createTypeName">
		<xsl:param name="aliases"/>
		<xsl:param name="typeName"/>
		<!-- define a new variable holding the typeName with their used aliases -->
		<xsl:variable name="aliasesForTypeName">
			<xsl:for-each select="$aliases">
				<xsl:variable name="alias">
					<xsl:value-of select="."/>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="$typeName != 'rim:Association' ">
						<xsl:if test="$inspire_queryables//inspire[cim/@alias = $alias][1]/cim/@typeName = $typeName ">
							<xsl:text>_</xsl:text>
							<xsl:value-of select="."/>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="$inspire_queryables//inspire[cim/targetOf/@alias = $alias][1]/cim/targetOf/@typeName = $typeName ">
							<xsl:text>_</xsl:text>
							<xsl:value-of select="."/>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</xsl:variable>
		<xsl:if test="$aliasesForTypeName!= '' ">
			<xsl:value-of select="$typeName"/>
			<xsl:value-of select="$aliasesForTypeName"/>
		</xsl:if>
	</xsl:function>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<xsl:template name="createFilter">
		<xsl:param name="CIM_Filter"/>
		<xsl:param name="CIM_Additions"/>
		<xsl:for-each select="$CIM_Filter//ogc:Filter">
			<xsl:copy>
				<xsl:for-each select="ogc:And">
					<xsl:copy>
						<xsl:copy-of select="$CIM_Additions//ogc:PropertyIsEqualTo | $CIM_Additions//ogc:PropertyIsLike "/>
						<xsl:copy-of select="*[ name()!= 'ogc:Or' and name()!= 'ogc:Not' ]"/>
						<xsl:for-each select="ogc:Or | ogc:Not">
							<xsl:call-template name="normalizeLogicalOperators">
								<xsl:with-param name="logicOperator" select="."/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:copy>
				</xsl:for-each>
			</xsl:copy>
		</xsl:for-each>
	</xsl:template>
	<!-- ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''' -->
	<xsl:template name="normalizeLogicalOperators">
		<xsl:param name="logicOperator"/>
		<xsl:choose>
			<xsl:when test=" $logicOperator = ogc:And or $logicOperator = ogc:Or ">
				<xsl:choose>
					<xsl:when test="count($logicOperator/*) = 1">
						<xsl:copy-of select="$logicOperator/*"/>
					</xsl:when>
					<xsl:when test="count($logicOperator/*) > 1">
						<xsl:copy-of select="$logicOperator"/>
					</xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:when test=" $logicOperator = 'ogc:Not' ">
				<xsl:if test="count($logicOperator/*) = 1">
					<xsl:copy-of select="$logicOperator"/>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
