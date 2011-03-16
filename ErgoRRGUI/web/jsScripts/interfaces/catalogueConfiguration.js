/*
 * ErgoRR Catalogue Configuration Interface
 * author: Andrea Marongiu, Massimiliano Fanciulli
 */


CatalogueConfiguration=function(){


this.loc=new localization('resources/xml/localization/catalogueConfiguration/'+interfacesManager.lang+".xml");

this.configurationRestUrl="ProxyRedirect?url=http://localhost:8081/ergorrtestui/config&outFormat=application/json";

this.miscHTMLHelp=
    "<h2>"+this.loc.getLocalMessage('repositoryHomeLabel')+"</h2>"+
    this.loc.getLocalMessage('repositoryHomeDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('serviceProviderGroup')+
    this.loc.getLocalMessage('nameLabel')+"</h2>"+
    this.loc.getLocalMessage('serviceProviderNameDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('serviceProviderGroup')+
    this.loc.getLocalMessage('webSiteLabel')+"</h2>"+
    this.loc.getLocalMessage('serviceProviderwebSiteDescription')+
    "<h2>"+this.loc.getLocalMessage('concactNameLabel')+"</h2>"+
    this.loc.getLocalMessage('concatNameDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('concactPositionLabel')+"</h2>"+
    this.loc.getLocalMessage('concatPositionDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('concactPhoneNumberLabel')+"</h2>"+
    this.loc.getLocalMessage('concatPhoneNumberDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('deliveryPointAddressLabel')+"</h2>"+
    this.loc.getLocalMessage('deliveryPointAddressDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('deliveryPointCityLabel')+"</h2>"+
    this.loc.getLocalMessage('deliveryPointCityDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('deliveryPointAdminAreaLabel')+"</h2>"+
    this.loc.getLocalMessage('deliveryPointAADescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('deliveryPointPostalCodeLabel')+"</h2>"+
    this.loc.getLocalMessage('deliveryPointPPDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('deliveryPointCountryLabel')+"</h2>"+
    this.loc.getLocalMessage('deliveryPointCountryDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('concactPointEmailLabel')+"</h2>"+
    this.loc.getLocalMessage('concatPersonEmailDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('concactPersonHOFLabel')+"</h2>"+
    this.loc.getLocalMessage('concatPersonHSDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('concactInstructionsLabel')+"</h2>"+
    this.loc.getLocalMessage('concatInstructionsDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('concactPersonRoleLabel')+"</h2>"+
    this.loc.getLocalMessage('concatPRDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('encodingLabel')+"</h2>"+
    this.loc.getLocalMessage('encodingDescription')+"<br><br>"+
     "<h2>"+this.loc.getLocalMessage('languageLabel')+"</h2>"+
    this.loc.getLocalMessage('languageDescription')+"<br><br>"+
     "<h2>"+this.loc.getLocalMessage('exSoapMessagesLabel')+"</h2>"+
    this.loc.getLocalMessage('exSoapMessagesDescription')+"<br><br>";


  this.databaseHTMLHelp=
    "<h2>"+this.loc.getLocalMessage('maxRecordsLabel')+"</h2>"+
    this.loc.getLocalMessage('maxRecordsDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('defaultSrsIDLabel')+"</h2>"+
    this.loc.getLocalMessage('defaultSrsIDDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('defaultSrsNameLabel')+"</h2>"+
    this.loc.getLocalMessage('defaultSrsNameDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('resultDepthLabel')+"</h2>"+
    this.loc.getLocalMessage('resultDepthDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('simplifyModelLabel')+"</h2>"+
    this.loc.getLocalMessage('simplifyModelDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('loadPMLabel')+"</h2>"+
    this.loc.getLocalMessage('loadPMLabelDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('loadClassificationNodesLabel')+"</h2>"+
    this.loc.getLocalMessage('loadClassificationNodesDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('returnResultsCountLabel')+"</h2>"+
    this.loc.getLocalMessage('returnResultsCountDescription')+"<br><br>";


  this.securityHTMLHelp=
    "<h2>"+this.loc.getLocalMessage('enableSecurityLabel')+"</h2>"+
    this.loc.getLocalMessage('enableSecurityDescription')+"<br><br>"+

    "<h2>"+this.loc.getLocalMessage('keyStoreGroup')+
    this.loc.getLocalMessage('keyStorePathLabel')+"</h2>"+
    this.loc.getLocalMessage('keyStorePathDescription')+"<br><br>"+

    "<h2>"+this.loc.getLocalMessage('keyStoreGroup')+
    this.loc.getLocalMessage('keyStorePasswordLabel')+"</h2>"+
    this.loc.getLocalMessage('keyStorePasswordDescription')+"<br><br>"+

    "<h2>"+this.loc.getLocalMessage('certificateAliasLabel')+"</h2>"+
    this.loc.getLocalMessage('certificateAliasDescription')+"<br><br>"+
    "<h2>"+this.loc.getLocalMessage('certificatePasswordLabel')+"</h2>"+
    this.loc.getLocalMessage('certificatePasswordDescription')+"<br><br>"+

    "<h2>"+this.loc.getLocalMessage('truststoreGroup')+
    this.loc.getLocalMessage('trustStorePathLabel')+"</h2>"+
    this.loc.getLocalMessage('trustStorePasswordLabel')+"<br><br>"+

    "<h2>"+this.loc.getLocalMessage('truststoreGroup')+
    this.loc.getLocalMessage('trustStorePasswordLabel')+"</h2>"+
    this.loc.getLocalMessage('trustStorePasswordDescription')+"<br><br>";

    this.miscConfigurationPortal={
                    iconCls: 'x-icon-templates',
                    style: 'padding: 10px;',
                    id: 'CatalogueConfigurationMiscPortal',
                    border: false,
                    xtype:'portal',
                    items:[{
                        columnWidth:.5,
                        items:[{
                            xtype: 'form',
                            id:'miscForm',
                            itemId:'miscForm',
                            title: this.loc.getLocalMessage('miscTitlePanel'),
                            draggable:true,
                            split: true,
                            bodyStyle: 'padding: 10px;',
                            buttons:[ {
                                text: this.loc.getLocalMessage('reloadButton'),
                                id:'miscReloadButton',
                                handler: function(){
                                    administrativeArea.reloadMisc();
                                }
                            },
                            {
                                text: this.loc.getLocalMessage('applyButton'),
                                id:'miscApplyButton',
                                handler: function(){
                                    administrativeArea.submitMisc();
                                }
                            }
                        ],
                        items: [{
                            xtype: 'fieldset',
                            title: this.loc.getLocalMessage('repositoryGroup'),
                            bodyStyle:'padding:5px; width:90%',
                            items: [
                            {
                                xtype: 'textfield',
                                id:'repoDirField',
                                fieldLabel: this.loc.getLocalMessage('repositoryHomeLabel')
                            }]
                        },{
                            xtype: 'fieldset',
                            title: this.loc.getLocalMessage('serviceProviderGroup'),
                            autoWidth:true,
                            bodyStyle:'padding:5px 5px 0',
                            items: [{
                                xtype: 'textfield',
                                id:'spNameField',
                                fieldLabel:this.loc.getLocalMessage('nameLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spSiteField',
                                fieldLabel:this.loc.getLocalMessage('webSiteLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactNameField',
                                fieldLabel:this.loc.getLocalMessage('concactNameLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactPositionField',
                                fieldLabel:this.loc.getLocalMessage('concactPositionLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactPhoneField',
                                fieldLabel:this.loc.getLocalMessage('concactPhoneNumberLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spDeliveryPointField',
                                fieldLabel:this.loc.getLocalMessage('deliveryPointAddressLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spDeliveryCityField',
                                fieldLabel:this.loc.getLocalMessage('deliveryPointCityLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spAdministrativeAreaField',
                                fieldLabel:this.loc.getLocalMessage('deliveryPointAdminAreaLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spPostalCodeField',
                                fieldLabel:this.loc.getLocalMessage('deliveryPointPostalCodeLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spCountryField',
                                fieldLabel:this.loc.getLocalMessage('deliveryPointCountryLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spCpEmailField',
                                fieldLabel:this.loc.getLocalMessage('concactPointEmailLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spCpHoursOfServiceField',
                                fieldLabel:this.loc.getLocalMessage('concactPersonHOFLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactInstructionsField',
                                fieldLabel:this.loc.getLocalMessage('concactInstructionsLabel'),
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactRoleField',
                                fieldLabel: this.loc.getLocalMessage('concactPersonRoleLabel'),
                                value:''
                            }]
                        },{
                            xtype: 'fieldset',
                            title: this.loc.getLocalMessage('encodingGroup'),
                            autoWidth:true,
                            bodyStyle:'padding:5px 5px 0',
                            items: [{
                                xtype: 'textfield',
                                id:'encodingField',
                                fieldLabel:this.loc.getLocalMessage('encodingLabel')
                            },{
                                xtype: 'textfield',
                                id:'langField',
                                fieldLabel:this.loc.getLocalMessage('languageLabel')
                            }]
                        },{
                            xtype: 'fieldset',
                            title: this.loc.getLocalMessage('soapGroup'),
                            bodyStyle:'padding:5px;',
                            items: [
                            {
                                xtype: 'checkbox',
                                id:'soapExceptions',
                                fieldLabel:this.loc.getLocalMessage('exSoapMessagesLabel'),
                                width:.9
                            }]
                        }]

                    }]
                },{
                    columnWidth:.5,
                    id:"nada",
		    style: 'padding: 0px 0px 0px 5px;',
                    items:[{
                        title: this.loc.getLocalMessage('helpTitlePanel'),
                        layout:'fit',
                        bodyStyle: 'padding: 10px;',
                        html: this.miscHTMLHelp
                    }]
                }]

            };

this.databaseConfigurationPortal={

                iconCls: 'x-icon-templates',
                style: 'padding: 10px;',
                id: 'CatalogueConfigurationDatabasePortal',
                border: false,
                xtype:'portal',
                items:[{
                    columnWidth:.5,
                    items:[{
                        xtype: 'form',
                        title: this.loc.getLocalMessage('databaseTitlePanel'),
                        bodyStyle:'padding:5px 5px 0',
                        buttons:[ {
                                text: this.loc.getLocalMessage('reloadButton'),
                                id: 'dbReloadButton',
                                handler: function(){
                                    administrativeArea.reloadDatabase();
                                }
                            },
                            {
                                text: this.loc.getLocalMessage('applyButton'),
                                id: 'dbSubmitButton',
                                handler: function(){
                                    administrativeArea.submitDatabase();
                                }
                            }
                        ],
                        items: [{
                                    xtype: 'numberfield',
                                    id:'maxRecordsField',
                                    fieldLabel:this.loc.getLocalMessage('maxRecordsLabel'),
                                    layout:'fit'
                                 },
                                {
                                    xtype: 'textfield',
                                    id:'srsIdField',
                                    fieldLabel:this.loc.getLocalMessage('defaultSrsIDLabel')
                                 },
                                 {
                                    xtype: 'textfield',
                                    id:'srsNameField',
                                    fieldLabel:this.loc.getLocalMessage('defaultSrsNameLabel')
                                 },{
                                    xtype: 'numberfield',
                                    id:'resultDepthField',
                                    fieldLabel:this.loc.getLocalMessage('resultDepthLabel')
                                 },{
                                    xtype: 'checkbox',
                                    id:'simplifyModelField',
                                    fieldLabel:this.loc.getLocalMessage('simplifyModelLabel')
                                },{
                                    xtype: 'checkbox',
                                    id:'loadPackageMembersField',
                                    fieldLabel:this.loc.getLocalMessage('loadPMLabel')
                                },{
                                    xtype: 'checkbox',
                                    id:'loadNestedClassificationNodesField',
                                    fieldLabel:this.loc.getLocalMessage('loadClassificationNodesLabel')
                                },{
                                    xtype: 'checkbox',
                                    id:'returnResultCountField',
                                    fieldLabel:this.loc.getLocalMessage('returnResultsCountLabel')
                                }
                             ]

                    }]
                },{
                    columnWidth:.5,
                    style: 'padding: 0px 0px 0px 5px;',
                    items:[{
                        title: this.loc.getLocalMessage('helpTitlePanel'),
                        layout:'fit',
                        bodyStyle: 'padding: 10px;',
                        html: this.databaseHTMLHelp
                    }]
                }]

            };



  


   this.reloadMisc=function(){
   
    Ext.MessageBox.progress("Loading miscellaneous settings", "", "");

    var request=new XMLHttpRequest();

    request.open("GET", this.configurationRestUrl);
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');
    request.send(null);

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
};


   this.submitMisc=function(){
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

    request.open("POST", this.configurationRestUrl);
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');

    request.send(JSON.stringify(json));

    Ext.MessageBox.updateProgress(100, "Saved", "");
    Ext.MessageBox.hide();
}


   this.submitDatabase=function(){
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

    request.open("POST", this.configurationRestUrl);
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');

    request.send(JSON.stringify(json));

    Ext.MessageBox.updateProgress(100, "Saved", "");
    Ext.MessageBox.hide();
}

   this.reloadDatabase=function(){
        Ext.MessageBox.progress("Loading database settings", "", "");

        var request=new XMLHttpRequest();

        request.open("GET", this.configurationRestUrl);
        request.setRequestHeader('Accept','application/json');
        request.setRequestHeader('Cache-Control','no-cache');
        request.send(null);

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


   this.submitSecurity=function(){
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

    request.open("POST", this.configurationRestUrl);
    request.setRequestHeader('Accept','application/json');
    request.setRequestHeader('Cache-Control','no-cache');

    request.send(JSON.stringify(json));

    Ext.MessageBox.updateProgress(100, "Saved", "");
    Ext.MessageBox.hide();
}

   this.reloadSecurity=function(){
         Ext.MessageBox.progress("Loading security settings", "", "");
        var request=new XMLHttpRequest();

        request.open("GET", this.configurationRestUrl);
        request.setRequestHeader('Accept','application/json');
        request.setRequestHeader('Cache-Control','no-cache');
        request.send(null);

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

    this.changeSecurityfieldStatus=function(){
        var checked=!document.getElementById('enableSecurityCheckbox').checked;

        document.getElementById('keyStorePathField').disabled=checked;
        document.getElementById('KeyStorePasswordField').disabled=checked;
        document.getElementById('keyStoreCertAliasField').disabled=checked;
        document.getElementById('keyStoreCertPasswordField').disabled=checked;
        document.getElementById('trustStorePathField').disabled=checked
        document.getElementById('trustStorePasswordField').disabled=checked;
    };


};



