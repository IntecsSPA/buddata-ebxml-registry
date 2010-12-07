
-- =================================================================================== TABLE
DROP TABLE IF EXISTS t_property CASCADE;
DROP TABLE IF EXISTS t_association CASCADE;
DROP TABLE IF EXISTS t_auditableevent CASCADE;
DROP TABLE IF EXISTS t_affectedobject CASCADE;
DROP TABLE IF EXISTS t_classification CASCADE;
DROP TABLE IF EXISTS t_classificationnode CASCADE;
DROP TABLE IF EXISTS t_classificationscheme CASCADE;
DROP TABLE IF EXISTS t_externalidentifier CASCADE;
DROP TABLE IF EXISTS t_externallink CASCADE;
DROP TABLE IF EXISTS t_extrinsicobject CASCADE;
DROP TABLE IF EXISTS t_federation CASCADE;
DROP TABLE IF EXISTS t_localizedname CASCADE;
DROP TABLE IF EXISTS t_localizeddesc CASCADE;
DROP TABLE IF EXISTS t_usagedescription CASCADE;
DROP TABLE IF EXISTS t_objectref CASCADE;
DROP TABLE IF EXISTS t_organization CASCADE;
DROP TABLE IF EXISTS t_registrypackage CASCADE;
DROP TABLE IF EXISTS t_postaladdress CASCADE;
DROP TABLE IF EXISTS t_emailaddress CASCADE;
DROP TABLE IF EXISTS t_registry CASCADE;
DROP TABLE IF EXISTS t_service CASCADE;
DROP TABLE IF EXISTS t_servicebinding CASCADE;
DROP TABLE IF EXISTS t_slot CASCADE;
DROP TABLE IF EXISTS t_specificationlink CASCADE;
DROP TABLE IF EXISTS t_usageparameter CASCADE;
DROP TABLE IF EXISTS t_subscription CASCADE;
DROP TABLE IF EXISTS t_notifyaction CASCADE;
DROP TABLE IF EXISTS t_notification CASCADE;
DROP TABLE IF EXISTS t_notificationobject CASCADE;
DROP TABLE IF EXISTS t_adhocquery CASCADE;
DROP TABLE IF EXISTS t_telephonenumber CASCADE;
DROP TABLE IF EXISTS t_user CASCADE;
DROP TABLE IF EXISTS t_person CASCADE;

CREATE TABLE t_property (
  key_                     VARCHAR(256) NOT NULL PRIMARY KEY,
  value_                   VARCHAR(4096),
  editable                 BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE t_association (
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association'),
  status		   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname		   VARCHAR(16),
  versioncomment           VARCHAR(256),
--Association attributes
  associationtype	   VARCHAR(256) NOT NULL,
  sourceobject		   VARCHAR(256) NOT NULL,
  targetobject  	   VARCHAR(256) NOT NULL
);

CREATE TABLE t_auditableevent (
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:AuditableEvent'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--AuditableEvent attributes
  requestid                VARCHAR(256) NOT NULL,
  eventtype                VARCHAR(256) NOT NULL,
  timestamp_               VARCHAR(30) NOT NULL,
  user_                    VARCHAR(256) NOT NULL
);

CREATE TABLE t_affectedobject (

--Each row is a relationship between a RegistryObject and an AuditableEvent
--Enables many-to-many relationship between effected RegistryObjects and AuditableEvents
  id                       VARCHAR(256) NOT NULL,
  home                     VARCHAR(256),
  eventid                  VARCHAR(256) NOT NULL,

  PRIMARY KEY (id, eventid)
);

CREATE TABLE t_classification (
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--Classfication attributes.
  classificationnode       VARCHAR(256),
  classificationscheme     VARCHAR(256),
  classifiedobject         VARCHAR(256) NOT NULL,
  noderepresentation       VARCHAR(256)
);

CREATE TABLE t_classificationnode (
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationNode'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--ClassficationNode attributes
  code                     VARCHAR(256),
  parent                   VARCHAR(256),
  path_                     VARCHAR(1024)
);

CREATE TABLE t_classificationscheme (
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ClassificationScheme'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--ClassificationScheme attributes
  isinternal               BOOLEAN NOT NULL,
  nodetype                 VARCHAR(256) NOT NULL
);

CREATE TABLE t_externalidentifier (
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--ExternalIdentifier attributes
  registryobject           VARCHAR(256) NOT NULL,
  identificationscheme     VARCHAR(256) NOT NULL,
  value_                    VARCHAR(256) NOT NULL
);

CREATE TABLE t_externallink (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--ExternalLink attributes
  externaluri              VARCHAR(256) NOT NULL
);

CREATE TABLE t_extrinsicobject (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--ExtrinsicObject attributes
  isopaque                 BOOLEAN NOT NULL,
  mimetype                 VARCHAR(256),
--contentVersionInfo flattened
  contentversionname       VARCHAR(16),
  contentversioncomment	   VARCHAR(256),
--WRS values
  spectype                 VARCHAR(3),
  wrsactuate               VARCHAR(256),
  wrsarcrole               VARCHAR(256),
  wrshref                  VARCHAR(256),
  wrsrole                  VARCHAR(256),
  wrsshow                  VARCHAR(256),
  wrstitle                 VARCHAR(256)
);

CREATE TABLE t_federation (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objectType = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Federation'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--Federation attributes: currently none defined
--xsd:duration stored in string form since no corresponding SQL type. Is 32 long enough?
  replicationsynclatency   VARCHAR(32)
);

CREATE TABLE t_name (
--LocalizedString attributes flattened for Name
  charset                  VARCHAR(32),
  lang                     VARCHAR(32) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL,
--The RegistryObject id for the parent RegistryObject for which this is a Name
  parent                   VARCHAR(256) NOT NULL,
  PRIMARY KEY (parent, lang)
);

CREATE TABLE t_description (
--LocalizedString attributes flattened for Description
  charset                  VARCHAR(32),
  lang                     VARCHAR(32) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL,
--The RegistryObject id for the parent RegistryObject for which this is a Name
  parent			VARCHAR(256) NOT NULL,
  PRIMARY KEY (parent, lang)
);

CREATE TABLE t_usagedescription (
--The RegistryObject id for the parent RegistryObject for which this is a Name
  parent                   VARCHAR(256) NOT NULL,
--LocalizedString attributes flattened for UsageDescription
  charset                  VARCHAR(32),
  lang                     VARCHAR(32) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL,
  PRIMARY KEY (parent, lang)
);

CREATE TABLE t_objectref (
--Stores remote ObjectRefs only
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256)
);

CREATE TABLE t_organization (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objectType = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Organization'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--Organization attributes
--Organization.address attribute is in PostalAddress table
  parent                   VARCHAR(256),
--primary contact for Organization, points to a User.
  primarycontact           VARCHAR(256)
--Organization.telephoneNumbers attribute is in TelephoneNumber table
);

CREATE TABLE t_registrypackage (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256)

--RegistryPackage attributes: currently none defined
);

CREATE TABLE t_postaladdress (
  city                     VARCHAR(64),
  country                  VARCHAR(64),
  postalcode               VARCHAR(64),
  state_                   VARCHAR(64),
  street                   VARCHAR(64),
  streetnumber             VARCHAR(32),
--The parent object that this is an address for
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE t_emailaddress (
  address                  VARCHAR(64) NOT NULL,
  type_                    VARCHAR(256),
--The parent object that this is an email address for
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE t_registry (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Registry'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--Registry attributes
--xsd:duration stored in string form since no corresponding SQL type. Is 32 long enough?
  catalogingsynclatency    VARCHAR(32) DEFAULT 'P1D',
  conformanceprofile       VARCHAR(16),
  operator                 VARCHAR(256) NOT NULL,
--xsd:duration stored in string form since no corresponding SQL type. Is 32 long enough?
  replicationsynclatency   VARCHAR(32) DEFAULT 'P1D',
  specificationversion     VARCHAR(8) NOT NULL
);

CREATE TABLE t_service (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Service'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256)

--Service attributes: currently none defined
);

CREATE TABLE t_servicebinding (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ServiceBinding'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--ServiceBinding attributes
  service                  VARCHAR(256) NOT NULL,
  accessuri                VARCHAR(256),
  targetbinding	           VARCHAR(256)
);

CREATE TABLE t_slot (
  seq                      SMALLINT     NOT NULL,
  parent                   VARCHAR(256) NOT NULL,
  name_                    VARCHAR(256) NOT NULL,
  slottype                 VARCHAR(256),
  spectype                 VARCHAR(3),
  stringvalue              VARCHAR(8192),
  boolvalue                BOOLEAN,
  datetimevalue            TIMESTAMP WITH TIME ZONE,
  doublevalue              DOUBLE PRECISION,
  intvalue                 INTEGER,
  geometryvalue            geometry,
  PRIMARY KEY (seq, parent, name_)
);

CREATE TABLE t_specificationlink (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:SpecificationLink'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--SpecificationLink attributes
  servicebinding           VARCHAR(256) NOT NULL,
  specificationobject      VARCHAR(256) NOT NULL
);

CREATE TABLE t_usageparameter (
--The parent SpecificationLink that this is a usage parameter for
  parent                   VARCHAR(256) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL
);

CREATE TABLE t_subscription (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Subscription'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--Subscription attributes
  selector                 VARCHAR(256) NOT NULL,
  endtime                  VARCHAR(30),
--xsd:duration stored in string form since no corresponding SQL type. Is 32 long enough?
  notificationinterval     VARCHAR(32) DEFAULT 'P1D',
  starttime                VARCHAR(30)
);

CREATE TABLE notifyaction (
  notificationoption       VARCHAR(256) NOT NULL,
--Either a ref to a Service, a String representing an email address in form: mailto:user@server,
--or a String representing an http URLin form: http://url
  endpoint                 VARCHAR(256) NOT NULL,
--Parent Subscription reference
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE t_notification (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Notification'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--Notification attributes
  subscription             VARCHAR(256) NOT NULL
);

CREATE TABLE t_notificationobject (

--Each row is a relationship between a RegistryObject and a Notification
--Enables a Notification to have multiple RegistryObjects
  notificationid           VARCHAR(256) NOT NULL,
  registryobjectid         VARCHAR(256) NOT NULL,
  PRIMARY KEY (notificationid, registryobjectid)
);

CREATE TABLE t_adhocquery (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:AdhocQuery'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--AdhocQuery attributes. Flattend QueryExpression attributes
  querylanguage            VARCHAR(256) NOT NULL,
  query                    VARCHAR(4096) NOT NULL
);

CREATE TABLE t_telephonenumber (
  areacode                 VARCHAR(8),
  countrycode              VARCHAR(8),
  extension                VARCHAR(8),
  -- we use "number_" instead of number, which is reserved in Oracle
  number_                  VARCHAR(16),
  phonetype                VARCHAR(256),
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE t_user (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person:User'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--User attributes
--address is in PostalAddress table
--email is in EMailAddress table
--personName flattened
  firstname                VARCHAR(64),
  middlename               VARCHAR(64),
  lastname                 VARCHAR(64)
--telephoneNumbers is in TelephoneNumber table
);

CREATE TABLE t_person (
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256),
--RegistryObject Attributes
  lid                      VARCHAR(256) NOT NULL,
  objecttype               VARCHAR(256) CHECK (objecttype = 'urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Person'),
  status                   VARCHAR(256) NOT NULL,
--VersionInfo flattened
  versionname              VARCHAR(16),
  versioncomment           VARCHAR(256),
--User attributes
--address is in PostalAddress table
--email is in EMailAddress table
--personName flattened
  firstname                VARCHAR(64),
  middlename               VARCHAR(64),
  lastname                 VARCHAR(64)
--telephoneNumbers is in TelephoneNumber table
);

--SELECT AddGeometryColumn('ergorr2', 'slot', 'geometryValue', -1, 'GEOMETRY', 2);
--SELECT AddGeometryColumn('ergorr2', 'slot', 'qGeometryValue', -1, 'GEOMETRY', 2);

/*CREATE TABLE slottype (
    slotname              VARCHAR(256) NOT NULL,
    slottype              VARCHAR(256) NOT NULL,
    PRIMARY KEY (slotname)
);*/
-- =================================================================================== VIEW

DROP VIEW IF EXISTS t_identifiable;
DROP VIEW IF EXISTS t_registryobject;
DROP VIEW IF EXISTS t_registryobjectid;

CREATE VIEW t_identifiable (
class_,
id, home,
lid, status, objecttype, versionname, versioncomment,
associationtype, sourceobject, targetobject,
requestid, eventtype, timestamp_, user_,
classificationnode, classificationscheme, classifiedobject, noderepresentation,
code, parent, path_,
isinternal, nodetype,
registryobject, identificationscheme, value_,
externaluri,
isopaque, mimetype, contentversionname, contentversioncomment, spectype, wrsactuate, wrsarcrole, wrshref, wrsrole, wrsshow, wrstitle,
replicationsynclatency,
primarycontact,
catalogingsynclatency, conformanceprofile, operator, specificationversion,
service, accessuri, targetbinding,
servicebinding, specificationobject,
selector, endtime, notificationinterval, starttime,
subscription,
querylanguage, query,
firstname, middlename, lastname
) AS
SELECT
'Association' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
associationtype,sourceobject,targetobject,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_association UNION ALL SELECT
'AuditableEvent' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
requestid, eventtype, timestamp_, user_,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_auditableevent UNION ALL SELECT
'Classification' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
classificationnode, classificationscheme, classifiedobject, noderepresentation,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_classification UNION ALL SELECT
'ClassificationNode' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
code, parent, path_,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_classificationnode UNION ALL SELECT
'ClassificationScheme' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
isinternal, nodetype,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_classificationscheme UNION ALL SELECT
'ExternalIdentifier' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
registryobject, identificationscheme, value_,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_externalidentifier UNION ALL SELECT
'ExternalLink' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
externaluri,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_externallink UNION ALL SELECT
'ExtrinsicObject' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
isopaque, mimetype, contentversionname, contentversioncomment, spectype, wrsactuate, wrsarcrole, wrshref, wrsrole, wrsshow, wrstitle,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_extrinsicobject UNION ALL SELECT
'Federation' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
replicationsynclatency,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_federation UNION ALL SELECT
'Organization' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, parent, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
primarycontact,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_organization UNION ALL SELECT
'RegistryPackage' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_registrypackage UNION ALL SELECT
'Registry' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
catalogingsynclatency, conformanceprofile, operator, specificationversion,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_registry UNION ALL SELECT
'Service' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_service UNION ALL SELECT
'ServiceBinding' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
service, accessuri, targetbinding,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_servicebinding UNION ALL SELECT
'SpecificationLink' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
servicebinding, specificationobject,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_specificationlink UNION ALL SELECT
'Subscription' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
selector, endtime, notificationinterval, starttime,
null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_subscription UNION ALL SELECT
'Notification' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
subscription,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar
FROM t_notification UNION ALL SELECT
'AdhocQuery' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
querylanguage, query,
null::varchar, null::varchar, null::varchar
FROM t_adhocquery UNION ALL SELECT
'User' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
firstname, middlename, lastname
FROM t_user UNION ALL SELECT
'Person' AS clazz_,
id, home,
lid, status, objecttype, versionname, versioncomment,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::boolean, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar,
null::boolean, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar,
null::varchar, null::varchar,
null::varchar, null::varchar, null::varchar, null::varchar,
null::varchar,
null::varchar, null::varchar,
firstname, middlename, lastname
FROM t_person;
-- =================================================================================== INDEX

DROP INDEX IF EXISTS extrinsicobject_id_idx;
DROP INDEX IF EXISTS extrinsicobject_objecttype_idx;
DROP INDEX IF EXISTS association_id_idx;
DROP INDEX IF EXISTS association_source_idx;
DROP INDEX IF EXISTS association_target_idx;
DROP INDEX IF EXISTS classification_id_idx;
DROP INDEX IF EXISTS classification_classifiedobject_idx;
DROP INDEX IF EXISTS classificationnode_id_idx;
DROP INDEX IF EXISTS classificationnode_parent_idx;
DROP INDEX IF EXISTS classificationscheme_id_idx;
DROP INDEX IF EXISTS externalidentifier_id_idx;
DROP INDEX IF EXISTS externalidentifier_registryobject_idx;
DROP INDEX IF EXISTS registrypackage_id_idx;
DROP INDEX IF EXISTS slot_parent_idx;
DROP INDEX IF EXISTS slot_name_idx;
DROP INDEX IF EXISTS slot_geometry_idx;
DROP INDEX IF EXISTS localizedname_parent_idx;
DROP INDEX IF EXISTS localizeddesc_parent_idx;


CREATE INDEX extrinsicobject_id_idx ON t_extrinsicobject (id);
CREATE INDEX extrinsicobject_objecttype_idx on t_extrinsicobject(objecttype);
CREATE INDEX association_id_idx ON t_association (id);
CREATE INDEX association_source_idx ON t_association (sourceobject);
CREATE INDEX association_target_idx ON t_association (targetobject);
CREATE INDEX classification_id_idx ON t_classification (id);
CREATE INDEX classification_classifiedobject_idx ON t_classification (classifiedobject);
CREATE INDEX classificationnode_id_idx ON t_classificationnode (id);
CREATE INDEX classificationnode_parent_idx ON t_classificationnode (parent);
CREATE INDEX classificationscheme_id_idx ON t_classificationscheme (id);
CREATE INDEX externalidentifier_id_idx ON t_externalidentifier (id);
CREATE INDEX externalidentifier_registryobject_idx ON t_externalidentifier (registryobject);
CREATE INDEX registrypackage_id_idx ON t_registrypackage (id);
CREATE INDEX slot_parent_idx ON t_slot (parent);
CREATE INDEX slot_name_idx ON t_slot (name_);
CREATE INDEX slot_geometry_idx on t_slot using gist (geometryvalue);
CREATE INDEX localizedname_parent_idx ON t_name (parent);
CREATE INDEX localizeddesc_parent_idx ON t_description (parent);

/*ALTER TABLE slot CLUSTER ON slot_geometry_idx;*/





