/**
 *
 */

function submitMisc()
{
    Ext.MessageBox.progress("Saving miscellaneous settings", "", "");
    Ext.MessageBox.updateProgress(10, "Saving miscellaneous settings", "");

    var json={'repository.root' : document.getElementById('repoDirField').value,
                'serviceProvider.name' :  document.getElementById('spNameField').value,
                'serviceProvider.contact.name' : document.getElementById('spContactNameField').value,
                'serviceProvider.contact.position' : document.getElementById('spContactPositionField').value,
                'serviceProvider.contact.phone' : document.getElementById('spContactPhoneField').value,
                'serviceProvider.contact.address.deliveryPoint' : document.getElementById('spDeliveryPointField').value,
                'serviceProvider.contact.address.city' : document.getElementById('spDeliveryCityField').value,
                'serviceProvider.contact.address.administrativeArea' : document.getElementById('spAdministrativeAreaField').value,
                'serviceProvider.contact.address.postalCode' : document.getElementById('spPostalCodeField').value,
                'serviceProvider.contact.address.country' : document.getElementById('spCountryField').value,
                'serviceProvider.contact.address.electronicMailAddress' : document.getElementById('spCpEmailField').value,
                'serviceProvider.contact.hoursOfService' : document.getElementById('spCpHoursOfServiceField').value,
                'serviceProvider.contact.contactInstructions' : document.getElementById('spContactInstructionsField').value,
                'serviceProvider.role' : document.getElementById('spContactRoleField').value,
                'encoding' : document.getElementById('encodingField').value,
                'lang' : document.getElementById('langField').value,
                'showExceptionsInSoap' : document.getElementById('soapExceptions').checked ? 'true':'false'};

    var request=new XMLHttpRequest();

    request.open("POST", '../../config');
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');

    request.send(JSON.stringify(json));

    Ext.MessageBox.updateProgress(100, "Saved", "");
    Ext.MessageBox.hide();
}

function reloadMisc()
{
    Ext.MessageBox.progress("Loading miscellaneous settings", "", "");

    var request=new XMLHttpRequest();

    request.open("GET", '../../config');
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');
    request.send();

    Ext.MessageBox.updateProgress(10, "Loading Miscellaneous settings", "");

    request.onreadystatechange= function() {
        if(request.readyState == 4) {
            if(request.status!=200){
                Ext.MessageBox.updateProgress(100, "Loaded", "");
                Ext.MessageBox.hide();

                Ext.Msg.show({
                    title:'Error',
                    msg: 'An error occurred while reloading settings',
                    buttons: Ext.Msg.OK,
                    animEl: 'elId',
                    icon: Ext.MessageBox.INFO
                });}
            else {
                var json = JSON.parse(request.responseText);

                document.getElementById('repoDirField').value=json['repository.root'];
                document.getElementById('spNameField').value=json['serviceProvider.name'];
                document.getElementById('spSiteField').value=json['serviceProvider.site'];
                document.getElementById('spContactNameField').value=json['serviceProvider.contact.name'];
                document.getElementById('spContactPositionField').value=json['serviceProvider.contact.position'];
                document.getElementById('spContactPhoneField').value=json['serviceProvider.contact.phone'];
                document.getElementById('spDeliveryPointField').value=json['serviceProvider.contact.address.deliveryPoint'];
                document.getElementById('spDeliveryCityField').value=json['serviceProvider.contact.address.city'];
                document.getElementById('spAdministrativeAreaField').value=json['serviceProvider.contact.address.administrativeArea'];
                document.getElementById('spPostalCodeField').value=json['serviceProvider.contact.address.postalCode'];
                document.getElementById('spCountryField').value=json['serviceProvider.contact.address.country'];
                document.getElementById('spCpEmailField').value=json['serviceProvider.contact.address.electronicMailAddress'];
                document.getElementById('spCpHoursOfServiceField').value=json['serviceProvider.contact.hoursOfService'];
                document.getElementById('spContactInstructionsField').value=json['serviceProvider.contact.contactInstructions'];
                document.getElementById('spContactRoleField').value=json['serviceProvider.role'];
                document.getElementById('encodingField').value=json['encoding'];
                document.getElementById('langField').value=json['lang'];
                document.getElementById('soapExceptions').checked=json['showExceptionsInSoap']=='true';

                Ext.MessageBox.updateProgress(100, "Loaded", "");
                Ext.MessageBox.hide();
            }
        }
    }
}

function submitDatabase()
{
    Ext.MessageBox.progress("Saving database settings", "", "");
    Ext.MessageBox.updateProgress(10, "Saving database settings", "");

    var json={'db.maxResponse' : document.getElementById('maxRecordsField').value,
                'db.defaultSrsId' :  document.getElementById('srsIdField').value,
                'db.defaultSrsName' : document.getElementById('srsNameField').value,
                'db.resultDepth' : document.getElementById('resultDepthField').value,
                'db.simplify.model' : document.getElementById('simplifyModelField').checked ? 'true':'false',
                'db.loadPackageMembers' : document.getElementById('loadPackageMembersField').checked ? 'true':'false',
                'db.loadNestedClassificationNodes' : document.getElementById('loadNestedClassificationNodesField').checked ? 'true':'false',
                'db.returnResultCount' : document.getElementById('returnResultCountField').checked ? 'true':'false'};

    var request=new XMLHttpRequest();

    request.open("POST", '../../config');
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');

    request.send(JSON.stringify(json));

    Ext.MessageBox.updateProgress(100, "Saved", "");
    Ext.MessageBox.hide();
}

function reloadDatabase()
{
    Ext.MessageBox.progress("Loading database settings", "", "");

    var request=new XMLHttpRequest();

    request.open("GET", '../../config');
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');
    request.send();

    Ext.MessageBox.updateProgress(10, "Loading database settings", "");

    request.onreadystatechange= function() {
        if(request.readyState == 4) {
            if(request.status!=200)
                {
                    Ext.MessageBox.updateProgress(100, "Loaded", "");
                    Ext.MessageBox.hide();

                    Ext.Msg.show({
                        title:'Error',
                        msg: 'An error occurred while reloading settings',
                        buttons: Ext.Msg.OK,
                        animEl: 'elId',
                        icon: Ext.MessageBox.INFO
                    });
                }
            else {
                var json = JSON.parse(request.responseText);

                document.getElementById('maxRecordsField').value=json['db.maxResponse'];
                document.getElementById('srsIdField').value=json['db.defaultSrsId'];
                document.getElementById('srsNameField').value=json['db.defaultSrsName'];
                document.getElementById('resultDepthField').value=json['db.resultDepth'];
                document.getElementById('simplifyModelField').checked=json['db.simplify.model']=='true';
                document.getElementById('loadPackageMembersField').checked=json['db.loadPackageMembers']=='true';
                document.getElementById('loadNestedClassificationNodesField').checked=json['db.loadNestedClassificationNodes']=='true';
                document.getElementById('returnResultCountField').checked=json['db.returnResultCount']=='true';

                Ext.MessageBox.updateProgress(100, "Loaded", "");
                Ext.MessageBox.hide();
            }
        }
    }
}

function submitSecurity()
{
    Ext.MessageBox.progress("Saving security settings", "", "");
    Ext.MessageBox.updateProgress(10, "Saving security settings", "");

    var json={'security.enabled' : document.getElementById('enableSecurityCheckbox').checked ? 'true':'false',
                'security.keystore.path' : document.getElementById('keyStorePathField').value,
                'security.keystore.password' :  document.getElementById('KeyStorePasswordField').value,
                'security.keystore.cert.alias' : document.getElementById('keyStoreCertAliasField').value,
                'security.keystore.cert.password' : document.getElementById('keyStoreCertPasswordField').value,
                'security.truststore.path' : document.getElementById('trustStorePathField').value,
                'security.truststore.password' : document.getElementById('trustStorePasswordField').value};

    var request=new XMLHttpRequest();

    request.open("POST", '../../config');
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');

    request.send(JSON.stringify(json));

    Ext.MessageBox.updateProgress(100, "Saved", "");
    Ext.MessageBox.hide();
}

function reloadSecurity()
{
     Ext.MessageBox.progress("Loading security settings", "", "");
    var request=new XMLHttpRequest();

    request.open("GET", '../../config');
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');
    request.send();

    Ext.MessageBox.updateProgress(10, "Loading security settings", "");

    request.onreadystatechange= function() {
        if(request.readyState == 4) {
            if(request.status!=200){
                Ext.MessageBox.updateProgress(100, "Loaded", "");
                Ext.MessageBox.hide();

                Ext.Msg.show({
                    title:'Error',
                    msg: 'An error occurred while reloading settings',
                    buttons: Ext.Msg.OK,
                    animEl: 'elId',
                    icon: Ext.MessageBox.INFO
                });
            }
            else {
                var json = JSON.parse(request.responseText);

                document.getElementById('enableSecurityCheckbox').checked=json['security.enabled']=='true';
                document.getElementById('keyStorePathField').value=json['security.keystore.path'];
                document.getElementById('KeyStorePasswordField').value=json['security.keystore.password'];
                document.getElementById('keyStoreCertAliasField').value=json['security.keystore.cert.alias'];
                document.getElementById('keyStoreCertPasswordField').value=json['security.keystore.cert.password'];
                document.getElementById('trustStorePathField').value=json['security.truststore.path'];
                document.getElementById('trustStorePasswordField').value=json['security.truststore.password'];

                Ext.MessageBox.updateProgress(100, "Loaded", "");
                Ext.MessageBox.hide();
            }
        }
    }
}

function changeSecurityfieldStatus()
{
    var checked=!document.getElementById('enableSecurityCheckbox').checked;

    document.getElementById('keyStorePathField').disabled=checked;
    document.getElementById('KeyStorePasswordField').disabled=checked;
    document.getElementById('keyStoreCertAliasField').disabled=checked;
    document.getElementById('keyStoreCertPasswordField').disabled=checked;
    document.getElementById('trustStorePathField').disabled=checked
    document.getElementById('trustStorePasswordField').disabled=checked;
}