drop index if exists identifiable_id_idx;
drop index if exists registryobject_id_idx;
drop index if exists extrinsicobject_id_idx;
drop index if exists association_id_idx;
drop index if exists classification_id_idx;
drop index if exists classificationnode_id_idx;
drop index if exists classificationscheme_id_idx;
drop index if exists externalidentifier_id_idx;
drop index if exists registrypackage_id_idx;
drop index if exists slot_id_idx;
drop index if exists localizedstringname_id_idx;
drop index if exists localizedstringdesc_id_idx; 
drop index if exists slot_parent_idx;
drop index if exists association_source_idx;
drop index if exists association_target_idx;
drop index if exists classificationnode_parent_idx;
drop index if exists externalidentifier_registryobject_idx;
drop index if exists classification_classifiedobject_idx;
drop index if exists localizedstringname_parent_idx;
drop index if exists localizedstringdesc_parent_idx;
drop index if exists slot_geometry_idx;


create index identifiable_id_idx on identifiable (id);
create index registryobject_id_idx on registryobject (id);
create index extrinsicobject_id_idx on extrinsicobject (id);
create index association_id_idx on association (id);
create index classification_id_idx on classification (id);
create index classificationnode_id_idx on classificationnode (id);
create index classificationscheme_id_idx on classificationscheme (id);
create index externalidentifier_id_idx on externalidentifier (id);
create index registrypackage_id_idx on registrypackage (id);
/*
create index slot_id_idx on slot (id);
create index localizedstringname_id_idx on localizedstringname (id);
create index localizedstringdesc_id_idx on localizedstringdesc (id);
*/

create index slot_parent_idx on slot (parent);
create index association_source_idx on association (sourceobject);
create index association_target_idx on association (targetobject);
create index classificationnode_parent_idx on classificationnode (parent);
create index externalidentifier_registryobject_idx on externalidentifier (registryobject);
create index classification_classifiedobject_idx on classification (classifiedobject);
create index localizedstringname_parent_idx on localizedstringname (parent);
create index localizedstringdesc_parent_idx on localizedstringdesc (parent);
create index slot_geometry_idx on localizedstringdesc using gist (querygeometryvalue);

/*ALTER TABLE slot CLUSTER ON slot_geometry_idx;*/





