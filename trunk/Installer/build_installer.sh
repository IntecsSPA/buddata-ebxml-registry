export APPVERSION=`sed '/^\#/d' ergo_install.xml | sed 's/^[[:space:]]*//;s/[[:space:]]*$//' | grep appversion | tail -n 1 | cut -d ">" -f2- | cut -d "<" -f1 | cut -d " " -f2`

cd ../ErgoRR
cp conf/ergorr-template.properties conf/ergorr.properties 
export DEPLOY_NAME=`sed '/^\#/d' conf/ergorr.properties | grep 'deployName'  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
mvn clean install
cp ErgoRR-backend/target/ErgoRR-backend-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-backend-client/target/ErgoRR-backend-client-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-client/target/ErgoRR-client-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-commons/target/ErgoRR-commons-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-jaxb/target/ErgoRR-jaxb-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-persistence/target/ErgoRR-persistence-1.0-SNAPSHOT.jar ../Installer/distribution/
rm -rf ../Installer/distribution/unpacked-war/
cp -a ErgoRR-web/target/${DEPLOY_NAME}/ ../Installer/distribution/unpacked-war
rm ../Installer/distribution/unpacked-war/WEB-INF/classes/ergorr.properties
cd -

# ErgoRR GUI -- START
cd ../BuildScripts
ant -f GUIBuild.xml createUnpackedWarForInstaller
cp -Rf unpacked-war-gui/* ../Installer/distribution/unpacked-war-gui
cd -
# ErgoRR GUI -- END

cd CustomElements
mvn clean install
cp target/CustomIzPackElements-1.0-SNAPSHOT.jar ../distribution/
cd -

cd ../Installer
$IZPACK_HOME/bin/compile ergo_install.xml -b . -o ErgoRR-installeri_${APPVERSION}.jar -k standard
