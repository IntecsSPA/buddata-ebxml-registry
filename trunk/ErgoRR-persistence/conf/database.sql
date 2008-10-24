
-- =================================================================================== TABLE
DROP TABLE IF EXISTS association CASCADE;
DROP TABLE IF EXISTS auditableevent CASCADE;
DROP TABLE IF EXISTS affectedobject CASCADE;
DROP TABLE IF EXISTS classification CASCADE;
DROP TABLE IF EXISTS classificationnode CASCADE;
DROP TABLE IF EXISTS classificationscheme CASCADE;
DROP TABLE IF EXISTS externalidentifier CASCADE;
DROP TABLE IF EXISTS externallink CASCADE;
DROP TABLE IF EXISTS extrinsicobject CASCADE;
DROP TABLE IF EXISTS federation CASCADE;
DROP TABLE IF EXISTS localizedname CASCADE;
DROP TABLE IF EXISTS localizeddesc CASCADE;
DROP TABLE IF EXISTS usagedescription CASCADE;
DROP TABLE IF EXISTS objectref CASCADE;
DROP TABLE IF EXISTS organization CASCADE;
DROP TABLE IF EXISTS registrypackage CASCADE;
DROP TABLE IF EXISTS postaladdress CASCADE;
DROP TABLE IF EXISTS emailaddress CASCADE;
DROP TABLE IF EXISTS registry CASCADE;
DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS servicebinding CASCADE;
DROP TABLE IF EXISTS slot CASCADE;
DROP TABLE IF EXISTS specificationlink CASCADE;
DROP TABLE IF EXISTS usageparameter CASCADE;
DROP TABLE IF EXISTS subscription CASCADE;
DROP TABLE IF EXISTS notifyaction CASCADE;
DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS notificationobject CASCADE;
DROP TABLE IF EXISTS adhocquery CASCADE;
DROP TABLE IF EXISTS telephonenumber CASCADE;
DROP TABLE IF EXISTS user_ CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS slottype CASCADE;


CREATE TABLE association (
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

CREATE TABLE auditableevent (
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

CREATE TABLE affectedobject (

--Each row is a relationship between a RegistryObject and an AuditableEvent
--Enables many-to-many relationship between effected RegistryObjects and AuditableEvents
  id                       VARCHAR(256) NOT NULL,
  home                     VARCHAR(256),
  eventid                  VARCHAR(256) NOT NULL,

  PRIMARY KEY (id, eventid)
);

CREATE TABLE classification (
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

CREATE TABLE classificationnode (
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

CREATE TABLE classificationscheme (
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
  isinternal               VARCHAR(1) NOT NULL,
  nodetype                 VARCHAR(256) NOT NULL
);

CREATE TABLE externalidentifier (
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

CREATE TABLE externallink (
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

CREATE TABLE extrinsicobject (
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
  isopaque                 VARCHAR(1) NOT NULL,
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

CREATE TABLE federation (
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

CREATE TABLE localizedname (
--LocalizedString attributes flattened for Name
  charset                  VARCHAR(32),
  lang                     VARCHAR(32) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL,
--The RegistryObject id for the parent RegistryObject for which this is a Name
  parent                   VARCHAR(256) NOT NULL,
  PRIMARY KEY (parent, lang)
);

CREATE TABLE localizeddesc (
--LocalizedString attributes flattened for Description
  charset                  VARCHAR(32),
  lang                     VARCHAR(32) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL,
--The RegistryObject id for the parent RegistryObject for which this is a Name
  parent			VARCHAR(256) NOT NULL,
  PRIMARY KEY (parent, lang)
);

CREATE TABLE usagedescription (
--LocalizedString attributes flattened for UsageDescription
  charset                  VARCHAR(32),
  lang                     VARCHAR(32) NOT NULL,
  value_                   VARCHAR(1024) NOT NULL,
--The RegistryObject id for the parent RegistryObject for which this is a Name
  parent                   VARCHAR(256) NOT NULL,
  PRIMARY KEY (parent, lang)
);

CREATE TABLE objectref (
--Stores remote ObjectRefs only
--Identifiable Attributes
  id                       VARCHAR(256) NOT NULL PRIMARY KEY,
  home                     VARCHAR(256)
);

CREATE TABLE organization (
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

CREATE TABLE registrypackage (
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

CREATE TABLE postaladdress (
  city                     VARCHAR(64),
  country                  VARCHAR(64),
  postalcode               VARCHAR(64),
  state_                   VARCHAR(64),
  street                   VARCHAR(64),
  streetnumber             VARCHAR(32),
--The parent object that this is an address for
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE emailaddress (
  address                  VARCHAR(64) NOT NULL,
  type_                    VARCHAR(256),
--The parent object that this is an email address for
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE registry (
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

CREATE TABLE service (
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

CREATE TABLE servicebinding (
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

CREATE TABLE slot (
  seq                      BIGINT       NOT NULL,
  parent                   VARCHAR(256) NOT NULL,
  slotname                 VARCHAR(256) NOT NULL,
  slottype                 VARCHAR(256),
  spectype                 VARCHAR(3),
  stringvalue              VARCHAR(4096),
  boolvalue                VARCHAR(1),
  datetimevalue            TIMESTAMP WITH TIME ZONE,
  doublevalue              DOUBLE PRECISION,
  intvalue                 INTEGER,
  geometryvalue            geometry,
  PRIMARY KEY (seq, parent, slotName)
);

CREATE TABLE specificationlink (
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
  serviceBinding           VARCHAR(256) NOT NULL,
  specificationObject      VARCHAR(256) NOT NULL
);

CREATE TABLE usageparameter (
  value_                   VARCHAR(1024) NOT NULL,
--The parent SpecificationLink that this is a usage parameter for
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE subscription (
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

CREATE TABLE notification (
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

CREATE TABLE notificationobject (

--Each row is a relationship between a RegistryObject and a Notification
--Enables a Notification to have multiple RegistryObjects
  notificationid           VARCHAR(256) NOT NULL,
  registryobjectid         VARCHAR(256) NOT NULL,
  PRIMARY KEY (notificationid, registryobjectid)
);

CREATE TABLE adhocquery (
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

CREATE TABLE telephonenumber (
  areacode                 VARCHAR(8),
  countrycode              VARCHAR(8),
  extension                VARCHAR(8),
  -- we use "number_" instead of number, which is reserved in Oracle
  number_                  VARCHAR(16),
  phonetype                VARCHAR(256),
  parent                   VARCHAR(256) NOT NULL
);

CREATE TABLE user_ (
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
  personName_firstName     VARCHAR(64),
  personName_middleName    VARCHAR(64),
  personName_lastName      VARCHAR(64)
--telephoneNumbers is in TelephoneNumber table
);

CREATE TABLE person (
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

CREATE TABLE slottype (
    slotname              VARCHAR(256) NOT NULL,
    slottype              VARCHAR(256) NOT NULL,
    PRIMARY KEY (slotname)
);
-- =================================================================================== VIEW

DROP VIEW IF EXISTS identifiable;
DROP VIEW IF EXISTS registryobject;
DROP VIEW IF EXISTS registryobjectid;

CREATE VIEW identifiable (
class_,
/* Identifiable */
id,
home,
/* RegistryObject */
lid,
status,
objecttype,
versionname,
versioncomment,
/* Association */
associationtype,
sourceobject,
targetobject,
/* Classification */
classificationnode,
classificationscheme,
classifiedobject,
noderepresentation,
/* ClassificationNode */
code,
path_,
parent,
/* ClassificationScheme */
isinternal,
nodetype,
/* ExternalIdentifier */
registryobject,
identificationscheme,
value_,
/* ExternalLink */
externaluri,
/* ExtrinsicObject */
isopaque,
mimetype,
contentversionname,
contentversioncomment,
spectype,
wrsactuate,
wrsarcrole,
wrshref,
wrsrole,
wrsshow,
wrstitle
) AS
SELECT 
1 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
associationtype,
sourceobject,
targetobject,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM association UNION ALL SELECT
2 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
classificationnode,
classificationscheme,
classifiedobject,
noderepresentation,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classification UNION ALL SELECT
3 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
code,
path_,
parent,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classificationnode UNION ALL SELECT
4 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
isinternal,
nodetype,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classificationscheme UNION ALL SELECT
5 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
registryobject,
identificationscheme,
value_,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM externalidentifier UNION ALL SELECT
6 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
externaluri,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM externallink UNION ALL SELECT
7 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
isopaque,
mimetype,
contentversionname,
contentversioncomment,
spectype,
wrsactuate,
wrsarcrole,
wrshref,
wrsrole,
wrsshow,
wrstitle
FROM extrinsicobject UNION ALL SELECT
8 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM registrypackage;


CREATE VIEW registryobjectid (
class_,
/* Identifiable */
id,
home,
/* RegistryObject */
lid,
status,
objecttype,
versionname,
versioncomment,
/* Association */
associationtype,
sourceobject,
targetobject,
/* Classification */
classificationnode,
classificationscheme,
classifiedobject,
noderepresentation,
/* ClassificationNode */
code,
path_,
parent,
/* ClassificationScheme */
isinternal,
nodetype,
/* ExternalIdentifier */
registryobject,
identificationscheme,
value_,
/* ExternalLink */
externaluri,
/* ExtrinsicObject */
isopaque,
mimetype,
contentversionname,
contentversioncomment,
spectype,
wrsactuate,
wrsarcrole,
wrshref,
wrsrole,
wrsshow,
wrstitle
) AS
SELECT 
1 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
associationtype,
sourceobject,
targetobject,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM association UNION ALL SELECT
2 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
classificationnode,
classificationscheme,
classifiedobject,
noderepresentation,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classification UNION ALL SELECT
3 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
code,
path_,
parent,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classificationnode UNION ALL SELECT
4 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
isinternal,
nodetype,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classificationscheme UNION ALL SELECT
5 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
registryobject,
identificationscheme,
value_,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM externalidentifier UNION ALL SELECT
6 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
externaluri,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM externallink UNION ALL SELECT
7 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
isopaque,
mimetype,
contentversionname,
contentversioncomment,
spectype,
wrsactuate,
wrsarcrole,
wrshref,
wrsrole,
wrsshow,
wrstitle
FROM extrinsicobject UNION ALL SELECT
8 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM registrypackage;

CREATE VIEW registryobject (
class_,
/* Identifiable */
id,
home,
/* RegistryObject */
lid,
status,
objecttype,
versionname,
versioncomment,
/* Association */
associationtype,
sourceobject,
targetobject,
/* Classification */
classificationnode,
classificationscheme,
classifiedobject,
noderepresentation,
/* ClassificationNode */
code,
path_,
parent,
/* ClassificationScheme */
isinternal,
nodetype,
/* ExternalIdentifier */
registryobject,
identificationscheme,
value_,
/* ExternalLink */
externaluri,
/* ExtrinsicObject */
isopaque,
mimetype,
contentversionname,
contentversioncomment,
spectype,
wrsactuate,
wrsarcrole,
wrshref,
wrsrole,
wrsshow,
wrstitle
) AS
SELECT 
1 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
associationtype,
sourceobject,
targetobject,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM association UNION ALL SELECT
2 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
classificationnode,
classificationscheme,
classifiedobject,
noderepresentation,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classification UNION ALL SELECT
3 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
code,
path_,
parent,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classificationnode UNION ALL SELECT
4 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
isinternal,
nodetype,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM classificationscheme UNION ALL SELECT
5 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
registryobject,
identificationscheme,
value_,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM externalidentifier UNION ALL SELECT
6 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
externaluri,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM externallink UNION ALL SELECT
7 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
isopaque,
mimetype,
contentversionname,
contentversioncomment,
spectype,
wrsactuate,
wrsarcrole,
wrshref,
wrsrole,
wrsshow,
wrstitle
FROM extrinsicobject UNION ALL SELECT
8 AS clazz_,
id,
home,
lid,
status,
objecttype,
versionname,
versioncomment,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar,
null::varchar
FROM registrypackage;

-- =================================================================================== INDEX

DROP INDEX IF EXISTS extrinsicobject_id_idx;
DROP INDEX IF EXISTS association_id_idx;
DROP INDEX IF EXISTS classification_id_idx;
DROP INDEX IF EXISTS classificationnode_id_idx;
DROP INDEX IF EXISTS classificationscheme_id_idx;
DROP INDEX IF EXISTS externalidentifier_id_idx;
DROP INDEX IF EXISTS registrypackage_id_idx;
DROP INDEX IF EXISTS slot_parent_idx;
DROP INDEX IF EXISTS localizedname_id_idx;
DROP INDEX IF EXISTS localizeddesc_id_idx; 
DROP INDEX IF EXISTS association_source_idx;
DROP INDEX IF EXISTS association_target_idx;
DROP INDEX IF EXISTS classificationnode_parent_idx;
DROP INDEX IF EXISTS externalidentifier_registryobject_idx;
DROP INDEX IF EXISTS classification_classifiedobject_idx;
DROP INDEX IF EXISTS localizedname_parent_idx;
DROP INDEX IF EXISTS localizeddesc_parent_idx;
--drop index if exists slot_geometry_idx;


CREATE index extrinsicobject_id_idx ON extrinsicobject (id);
CREATE index association_id_idx ON association (id);
CREATE index classification_id_idx ON classification (id);
CREATE index classificationnode_id_idx ON classificationnode (id);
CREATE index classificationscheme_id_idx ON classificationscheme (id);
CREATE index externalidentifier_id_idx ON externalidentifier (id);
CREATE index registrypackage_id_idx ON registrypackage (id);
/*
CREATE index localizedname_id_idx ON localizedname (id);
CREATE index localizeddesc_id_idx ON localizeddesc (id);
*/

CREATE index slot_parent_idx ON slot (parent);
CREATE index association_source_idx ON association (sourceobject);
CREATE index association_target_idx ON association (targetobject);
CREATE index classificationnode_parent_idx ON classificationnode (parent);
CREATE index externalidentifier_registryobject_idx ON externalidentifier (registryobject);
CREATE index classification_classifiedobject_idx ON classification (classifiedobject);
CREATE index localizedname_parent_idx ON localizedname (parent);
CREATE index localizeddesc_parent_idx ON localizeddesc (parent);
--CREATE index slot_geometry_idx on slot using gist (querygeometryvalue);

/*ALTER TABLE slot CLUSTER ON slot_geometry_idx;*/





