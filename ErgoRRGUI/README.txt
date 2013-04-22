
The gui-build.xml allows to build and deploy ErgoRRGUI using ant.

It assumes that an instance of the ErgoRR catalogue is already built and installed.  
NOTE: The gui-build.xml reads the catalogue properties in ../ErgoRR/conf/ergorr.properties

The commands are the following:

ant -f gui-build.xml build

ant -f gui-build.xml deploy (assumes CATALINA_HOME set)

ant -f guil-build.xml clean (removes "build" and "dist" directory)