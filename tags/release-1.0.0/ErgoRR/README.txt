==========================================================
= Configuration
==========================================================

Copy 'conf/ergorr-template.properties' to 'conf/ergorr.properties'.
Do not edit 'conf/ergorr-template.properties'.

The project can be configured from 'conf/ergorr.properties'
- This config is added to the classpath of each module while testing.
- This config is bundled (in WEB-INF/classes) with the generated WAR from the ErgoRR-web project.
  This is also the properties used if you build the project with Ant.

ergorr.properties file is NOT bundled with other ErgoRR module.
E.g If you want to just make use of the ErgoRR-backend (without deploying the ErgoRR-web)
then you have to add ergorr.properties manually in your classpath.
This way, you don't have to re-package any ErgoRR module on every configuration change.

==========================================================
= Installation
==========================================================

Refer to: http://code.google.com/p/buddata-ebxml-registry/wiki/InstallationGuide