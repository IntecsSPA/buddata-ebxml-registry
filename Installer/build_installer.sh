cd ../ErgoRR
mvn clean install
cp ErgoRR-backend/target/ErgoRR-backend-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-backend-client/target/ErgoRR-backend-client-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-client/target/ErgoRR-client-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-commons/target/ErgoRR-commons-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-jaxb/target/ErgoRR-jaxb-1.0-SNAPSHOT.jar ../Installer/distribution/
cp ErgoRR-persistence/target/ErgoRR-persistence-1.0-SNAPSHOT.jar ../Installer/distribution/
cd -

cd CustomElements
mvn clean install
cp target/CustomIzPackElements-1.0-SNAPSHOT.jar ../distribution/
cd -

cd ../Installer
$IZPACK_HOME/bin/compile ergo_install.xml -b . -o ErgoRR-installer.jar -k standard
