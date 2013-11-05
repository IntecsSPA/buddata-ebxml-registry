#!/bin/bash

createdb template_postgis
createlang plpgsql template_postgis
psql -d template_postgis -f /usr/share/postgresql-8.3-postgis/lwpostgis.sql
psql -d template_postgis -f /usr/share/postgresql-8.3-postgis/spatial_ref_sys.sql
createdb -T template_postgis ergorr
psql -d ergorr -f ./ErgoRR-persistence/src/main/config/database.sql
psql -d ergorr -f ./ErgoRR-persistence/src/main/config/init-data.sql


