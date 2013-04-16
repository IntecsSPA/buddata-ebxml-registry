/*
 * Project: Buddata ebXML RegRep
 * Class: RIMConstants.java
 * Copyright (C) 2008 Yaman Ustuntas
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.commons;

import javax.xml.namespace.QName;

/**
 * RIM canonical object types, association types, slot names, slot types.
 * 
 * @author Yaman Ustuntas
 */
public class RIMConstants {

    private RIMConstants() {
    }

    public final static QName QNAME_EXTRINSIC_OBJECT = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ExtrinsicObject", "rim");
    public final static QName QNAME_SERVICE_BINDING = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ServiceBinding", "rim");
    public final static QName QNAME_SERVICE = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Service", "rim");
    public final static QName QNAME_EXTERNAL_IDENTIFIER = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ExternalIdentifier", "rim");
    public final static QName QNAME_REGISTRY = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Registry", "rim");
    public final static QName QNAME_CLASSIFICATION_SCHEME = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ClassificationScheme", "rim");
    public final static QName QNAME_ORGANIZATION = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Organization", "rim");
    public final static QName QNAME_REGISTRY_PACKAGE = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "RegistryPackage", "rim");
    public final static QName QNAME_ASSOCIATION = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Association", "rim");
    public final static QName QNAME_ADHOC_QUERY = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "AdhocQuery", "rim");
    public final static QName QNAME_EXTERNAL_LINK = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ExternalLink", "rim");
    public final static QName QNAME_CLASSIFICATION = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Classification", "rim");
    public final static QName QNAME_PERSON = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Person", "rim");
    public final static QName QNAME_SPECIFICATION_LINK = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "SpecificationLink", "rim");
    public final static QName QNAME_REGISTRY_OBJECT = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "RegistryObject", "rim");
    public final static QName QNAME_IDENTIFIABLE = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Identifiable", "rim");
    public final static QName QNAME_USER = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "User", "rim");
    public final static QName QNAME_CLASSIFICATION_NODE = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ClassificationNode", "rim");

    // ============================
    // ObjectType ClassificationScheme
    // ============================
    public static final String CS_ID_ObjectType = "urn:oasis:names:tc:ebxml-regrep:classificationScheme:ObjectType";
    // ObjectType ClassificationNode IDs
    public static final String CN_OBJECT_TYPE_ID_AdhocQuery = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:AdhocQuery";
    public static final String CN_OBJECT_TYPE_ID_Association = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association";
    public static final String CN_OBJECT_TYPE_ID_AuditableEvent = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:AuditableEvent";
    public static final String CN_OBJECT_TYPE_ID_Classification = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification";
    public static final String CN_OBJECT_TYPE_ID_ClassificationNode = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode";
    public static final String CN_OBJECT_TYPE_ID_ClassificationScheme = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationScheme";
    public static final String CN_OBJECT_TYPE_ID_ExternalIdentifier = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier";
    public static final String CN_OBJECT_TYPE_ID_ExternalLink = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalLink";
    public static final String CN_OBJECT_TYPE_ID_ExtrinsicObject = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject";
    public static final String CN_OBJECT_TYPE_ID_Federation = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Federation";
    public static final String CN_OBJECT_TYPE_ID_Notification = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Notification";
    public static final String CN_OBJECT_TYPE_ID_Organization = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Organization";
    public static final String CN_OBJECT_TYPE_ID_Person = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person";
    public static final String CN_OBJECT_TYPE_ID_Policy = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XACML:Policy";
    public static final String CN_OBJECT_TYPE_ID_PolicySet = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XACML:PolicySet";
    public static final String CN_OBJECT_TYPE_ID_Registry = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Registry";
    public static final String CN_OBJECT_TYPE_ID_RegistryObject = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject";
    public static final String CN_OBJECT_TYPE_ID_RegistryPackage = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage";
    public static final String CN_OBJECT_TYPE_ID_Service = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Service";
    public static final String CN_OBJECT_TYPE_ID_ServiceBinding = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ServiceBinding";
    public static final String CN_OBJECT_TYPE_ID_SpecificationLink = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:SpecificationLink";
    public static final String CN_OBJECT_TYPE_ID_Subscription = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Subscription";
    public static final String CN_OBJECT_TYPE_ID_User = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person:User";
    public static final String CN_OBJECT_TYPE_ID_XACML = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XACML";
    public static final String CN_OBJECT_TYPE_ID_XForm = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XHTML:XForm";
    public static final String CN_OBJECT_TYPE_ID_XHTML = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XHTML";
    public static final String CN_OBJECT_TYPE_ID_XML = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XML";
    public static final String CN_OBJECT_TYPE_ID_XMLSchema = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XMLSchema";
    public static final String CN_OBJECT_TYPE_ID_XSLT = "urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExtrinsicObject:XSLT";
    // ============================
    // AssociationType ClassificationScheme
    // ============================
    public static final String CS_ID_AssociationType = "urn:oasis:names:tc:ebxml-regrep:classificationScheme:AssociationType";
    // AssociationType ClassificationNode IDs
    public static final String CN_ASSOCIATION_TYPE_ID_AccessControlPolicyFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:AccessControlPolicyFor";
    public static final String CN_ASSOCIATION_TYPE_ID_AffiliatedWith = "urn:oasis:names:tc:ebxml-regrep:AssociationType:AffiliatedWith";
    public static final String CN_ASSOCIATION_TYPE_ID_CatalogingControlFileFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:InvocationControlFileFor:CatalogingControlFileFor";
    public static final String CN_ASSOCIATION_TYPE_ID_Contains = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Contains";
    public static final String CN_ASSOCIATION_TYPE_ID_ContentManagementServiceFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:ContentManagementServiceFor";
    public static final String CN_ASSOCIATION_TYPE_ID_EmployeeOf = "urn:oasis:names:tc:ebxml-regrep:AssociationType:AffiliatedWith:EmployeeOf";
    public static final String CN_ASSOCIATION_TYPE_ID_EquivalentTo = "urn:oasis:names:tc:ebxml-regrep:AssociationType:EquivalentTo";
    public static final String CN_ASSOCIATION_TYPE_ID_Extends = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Extends";
    public static final String CN_ASSOCIATION_TYPE_ID_ExternallyLinks = "urn:oasis:names:tc:ebxml-regrep:AssociationType:ExternallyLinks";
    public static final String CN_ASSOCIATION_TYPE_ID_FilteringControlFileFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:InvocationControlFileFor:FilteringControlFileFor";
    public static final String CN_ASSOCIATION_TYPE_ID_HasFederationMember = "urn:oasis:names:tc:ebxml-regrep:AssociationType:HasFederationMember";
    public static final String CN_ASSOCIATION_TYPE_ID_HasMember = "urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember";
    public static final String CN_ASSOCIATION_TYPE_ID_Implements = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Implements";
    public static final String CN_ASSOCIATION_TYPE_ID_Imports = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Imports";
    public static final String CN_ASSOCIATION_TYPE_ID_Includes = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Includes";
    public static final String CN_ASSOCIATION_TYPE_ID_InstanceOf = "urn:oasis:names:tc:ebxml-regrep:AssociationType:InstanceOf";
    public static final String CN_ASSOCIATION_TYPE_ID_InvocationControlFileFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:InvocationControlFileFor";
    public static final String CN_ASSOCIATION_TYPE_ID_MemberOf = "urn:oasis:names:tc:ebxml-regrep:AssociationType:AffiliatedWith:MemberOf";
    public static final String CN_ASSOCIATION_TYPE_ID_OffersService = "urn:oasis:names:tc:ebxml-regrep:AssociationType:OffersService";
    public static final String CN_ASSOCIATION_TYPE_ID_OwnerOf = "urn:oasis:names:tc:ebxml-regrep:AssociationType:OwnerOf";
    public static final String CN_ASSOCIATION_TYPE_ID_RelatedTo = "urn:oasis:names:tc:ebxml-regrep:AssociationType:RelatedTo";
    public static final String CN_ASSOCIATION_TYPE_ID_Replaces = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Replaces";
    public static final String CN_ASSOCIATION_TYPE_ID_ResponsibleFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:ResponsibleFor";
    public static final String CN_ASSOCIATION_TYPE_ID_SubmitterOf = "urn:oasis:names:tc:ebxml-regrep:AssociationType:SubmitterOf";
    public static final String CN_ASSOCIATION_TYPE_ID_Supersedes = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Supersedes";
    public static final String CN_ASSOCIATION_TYPE_ID_Uses = "urn:oasis:names:tc:ebxml-regrep:AssociationType:Uses";
    public static final String CN_ASSOCIATION_TYPE_ID_ValidationControlFileFor = "urn:oasis:names:tc:ebxml-regrep:AssociationType:InvocationControlFileFor:ValidationControlFileFor";
    // ============================
    // DataType ClassificationScheme
    // ============================
    public static final String CS_ID_DataType = "urn:oasis:names:tc:ebxml-regrep:classificationScheme:DataType";
    // DataType ClassificationNode IDs
    public static final String CN_DATA_TYPE_ID_Boolean = "urn:oasis:names:tc:ebxml-regrep:DataType:Boolean";
    public static final String CN_DATA_TYPE_ID_Date = "urn:oasis:names:tc:ebxml-regrep:DataType:Date";
    public static final String CN_DATA_TYPE_ID_DateTime = "urn:oasis:names:tc:ebxml-regrep:DataType:DateTime";
    public static final String CN_DATA_TYPE_ID_Double = "urn:oasis:names:tc:ebxml-regrep:DataType:Double";
    public static final String CN_DATA_TYPE_ID_Duration = "urn:oasis:names:tc:ebxml-regrep:DataType:Duration";
    public static final String CN_DATA_TYPE_ID_Float = "urn:oasis:names:tc:ebxml-regrep:DataType:Float";
    public static final String CN_DATA_TYPE_ID_Integer = "urn:oasis:names:tc:ebxml-regrep:DataType:Integer";
    public static final String CN_DATA_TYPE_ID_ObjectRef = "urn:oasis:names:tc:ebxml-regrep:DataType:ObjectRef";
    public static final String CN_DATA_TYPE_ID_String = "urn:oasis:names:tc:ebxml-regrep:DataType:String";
    public static final String CN_DATA_TYPE_ID_Time = "urn:oasis:names:tc:ebxml-regrep:DataType:Time";
    public static final String CN_DATA_TYPE_ID_URI = "urn:oasis:names:tc:ebxml-regrep:DataType:URI";
    // DataType ClassificationNode Codes
    public static final String CN_DATA_TYPE_CODE_Boolean = "Boolean";
    public static final String CN_DATA_TYPE_CODE_Date = "Date";
    public static final String CN_DATA_TYPE_CODE_DateTime = "DateTime";
    public static final String CN_DATA_TYPE_CODE_Double = "Double";
    public static final String CN_DATA_TYPE_CODE_Duration = "Duration";
    public static final String CN_DATA_TYPE_CODE_Float = "Float";
    public static final String CN_DATA_TYPE_CODE_Integer = "Integer";
    public static final String CN_DATA_TYPE_CODE_ObjectRef = "ObjectRef";
    public static final String CN_DATA_TYPE_CODE_String = "String";
    public static final String CN_DATA_TYPE_CODE_Time = "Time";
    public static final String CN_DATA_TYPE_CODE_URI = "URI";
    // ============================
    // StatusType ClassificationScheme
    // ============================
    public static final String CS_ID_StatusType = "urn:oasis:names:tc:ebxml-regrep:classificationScheme:StatusType";
    // StatusType ClassificationScheme IDs
    public static final String CN_STATUS_TYPE_ID_Approved = "urn:oasis:names:tc:ebxml-regrep:StatusType:Approved";
    public static final String CN_STATUS_TYPE_ID_Deprecated = "urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated";
    public static final String CN_STATUS_TYPE_ID_Submitted = "urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted";
    public static final String CN_STATUS_TYPE_ID_Withdrawn = "urn:oasis:names:tc:ebxml-regrep:StatusType:Withdrawn";
    // StatusType ClassificationNode Codes
    public static final String CN_STATUS_TYPE_CODE_Approved = "Approved";
    public static final String CN_STATUS_TYPE_CODE_Deprecated = "Deprecated";
    public static final String CN_STATUS_TYPE_CODE_Submitted = "Submitted";
    public static final String CN_STATUS_TYPE_CODE_Withdrawn = "Withdrawn";
    // ============================
    // ResponseStatusType
    // ============================
    public static final String RESPONSE_STATUS_ID_Failure = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure";
    public static final String RESPONSE_STATUS_ID_Success = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success";
    public static final String RESPONSE_STATUS_ID_Unavailable = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Unavailable";
    public static final String RESPONSE_STATUS_CODE_Failure = "Failure";
    public static final String RESPONSE_STATUS_CODE_Success = "Success";
    public static final String RESPONSE_STATUS_CODE_Unavailable = "Unavailable";
    // ============================
    // QueryLanguage ClassificationScheme
    // ============================
    public static final String CN_QUERY_LANG_SQL = "urn:oasis:names:tc:ebxml-regrep:QueryLanguage:SQL-92";
    public static final String CN_QUERY_LANG_GML_FILTER = "urn:ogc:def:ebRIM-QueryLanguage:OGC:CSW-Filter";
    public static final String CN_OBJ_DEF = "urn:kzen:ObjectType:SlotDescription";
}
