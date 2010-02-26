/*!
 * Ext JS Library 3.1.1
 * Copyright(c) 2006-2010 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */


Ext.onReady(function() {
    Ext.QuickTips.init();

    // create some portlet tools using built in Ext tool ids
    var tools = [{
        id:'refresh',
        handler: function(e, target, panel){
            panel.refresh();

        }
    }];

    var viewport = new Ext.Viewport({
        layout:'fit',
        items:[{
            xtype: 'grouptabpanel',
            tabWidth: 130,
            activeGroup: 0,
            items: [
            {
                expanded: true,
                items: [{
                    xtype: 'portal',
                    title: 'About',
                    tabTip: 'Information about this ebRR instance',
                    html: aboutPage
                }]
            },{
                expanded: true,
                items: [{
                    title: 'Configuration',
                    iconCls: 'x-icon-configuration',
                    tabTip: 'This panel contains all configuration settings',
                    style: 'padding: 10px;',
                    html: ""
                }, {
                    title: 'Misc',
                    iconCls: 'x-icon-templates',
                    tabTip: 'Edit configuration',
                    style: 'padding: 10px;',
                    xtype:'portal',
                    items:[{
                        columnWidth:.5,
                        items:[{
                            xtype: 'form',
                            id:'miscForm',
                            itemId:'miscForm',
                            title: 'Misc',
                            draggable:'true',
                            bodyStyle: 'padding: 10px;',
                            buttons:[ {
                                text: 'Reload',
                                id:'miscReloadButton',
                                handler: function(){
                                    reloadMisc();
                                }
                            },
                            {
                                text: 'Apply',
                                id:'miscApplyButton',
                                handler: function(){
                                    submitMisc();
                                }
                            }
                        ],
                        items: [{
                            xtype: 'fieldset',
                            title: 'Repository',
                            bodyStyle:'padding:5px; width:90%',
                            items: [
                            {
                                xtype: 'textfield',
                                id:'repoDirField',
                                fieldLabel:'Repository home folder'
                            }]
                        },{
                            xtype: 'fieldset',
                            title: 'Service Provider',
                            autoWidth:true,
                            bodyStyle:'padding:5px 5px 0',
                            items: [{
                                xtype: 'textfield',
                                id:'spNameField',
                                fieldLabel:'Name',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spSiteField',
                                fieldLabel:'Web site',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactNameField',
                                fieldLabel:'Contact name',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactPositionField',
                                fieldLabel:'Contact position',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactPhoneField',
                                fieldLabel:'Contact phone number',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spDeliveryPointField',
                                fieldLabel:'Delivery point address',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spDeliveryCityField',
                                fieldLabel:'Delivery point city',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spAdministrativeAreaField',
                                fieldLabel:'Delivery point administrative area',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spPostalCodeField',
                                fieldLabel:'Delivery point postal code',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spCountryField',
                                fieldLabel:'Delivery point Country',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spCpEmailField',
                                fieldLabel:'Contact point email address',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spCpHoursOfServiceField',
                                fieldLabel:'Contact person hours of service',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactInstructionsField',
                                fieldLabel:'Contact instructions',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'spContactRoleField',
                                fieldLabel:'Contact person role',
                                value:''
                            }]
                        },{
                            xtype: 'fieldset',
                            title: 'Encoding',
                            autoWidth:true,
                            bodyStyle:'padding:5px 5px 0',
                            items: [{
                                xtype: 'textfield',
                                id:'encodingField',
                                fieldLabel:'Encoding'
                            },{
                                xtype: 'textfield',
                                id:'langField',
                                fieldLabel:'Language'
                            }]
                        },{
                            xtype: 'fieldset',
                            title: 'SOAP',
                            bodyStyle:'padding:5px;',
                            items: [
                            {
                                xtype: 'checkbox',
                                id:'soapExceptions',
                                fieldLabel:'Return exceptions in SOAP messages',
                                width:.9
                            }]
                        }]

                    }]
                },{
                    columnWidth:.5,
		    style: 'padding: 0px 0px 0px 5px;',
                    items:[{
                        title: 'Help',
                        layout:'fit',
                        bodyStyle: 'padding: 10px;',
                        html: miscHelp
                    }]
                }]

            },
            {
                title: 'Database',
                iconCls: 'x-icon-templates',
                tabTip: 'Edit configuration',
                style: 'padding: 10px;',
                xtype:'portal',
                items:[{
                    columnWidth:.5,
                    items:[{
                        xtype: 'form',
                        title: 'Database',
                        bodyStyle:'padding:5px 5px 0',
                        buttons:[ {
                                text: 'Reload',
                                id: 'dbReloadButton',
                                handler: function(){
                                    reloadDatabase();
                                }
                            },
                            {
                                text: 'Apply',
                                id: 'dbSubmitButton',
                                handler: function(){
                                    submitDatabase();
                                }
                            }
                        ],
                        items: [{
                                    xtype: 'numberfield',
                                    id:'maxRecordsField',
                                    fieldLabel:'Max records to return',
                                    layout:'fit'
                                 },
                                {
                                    xtype: 'textfield',
                                    id:'srsIdField',
                                    fieldLabel:'default Srs Id'
                                 },
                                 {
                                    xtype: 'textfield',
                                    id:'srsNameField',
                                    fieldLabel:'default Srs Name'
                                 },{
                                    xtype: 'numberfield',
                                    id:'resultDepthField',
                                    fieldLabel:'Result depth'
                                 },{
                                xtype: 'checkbox',
                                id:'simplifyModelField',
                                fieldLabel:'Simplify model'
                                },{
                                xtype: 'checkbox',
                                id:'loadPackageMembersField',
                                fieldLabel:'Load package members'
                                },{
                                xtype: 'checkbox',
                                id:'loadNestedClassificationNodesField',
                                fieldLabel:'Load nested classification nodes'
                                },{
                                xtype: 'checkbox',
                                id:'returnResultCountField',
                                fieldLabel:'Return results count'
                                }
                             ]

                    }]
                },{
                    columnWidth:.5,
                    style: 'padding: 0px 0px 0px 5px;',
                    items:[{
                        title: 'Help',
                        layout:'fit',
                        bodyStyle: 'padding: 10px;',
                        html: databaseHelp
                    }]
                }]

            },
            {
                title: 'Security',
                iconCls: 'x-icon-templates',
                tabTip: 'Edit configuration',
                style: 'padding: 10px;',
                xtype:'portal',
                items:[{
                    columnWidth:.5,
                    items:[{
                        xtype: 'form',
			bodyStyle: 'padding: 10px;',
                        title: 'Security',
                        buttons:[ {
                                text: 'Reload',
                                handler: function(){
                                    reloadSecurity();
                                }
                            },
                            {
                                text: 'Apply',
                                handler: function(){
                                    submitSecurity();
                                }
                            }
                        ],
                        items: [{
                            xtype: 'fieldset',
                            title: 'Enable Security',
                            bodyStyle:'padding:5px;',
                            items: [
                            {
                                xtype: 'checkbox',
                                id:'enableSecurityCheckbox',
                                fieldLabel:'Enable security',
                                width:.9,
                                handler: function(){
                                    changeSecurityfieldStatus();
                                }
                            }]
                        },{
                            xtype: 'fieldset',
                            title: 'Keystore',
                            bodyStyle:'padding:5px;',
                            autoWidth:true,
                            items: [{
                                xtype: 'textfield',
                                id:'keyStorePathField',
                                fieldLabel:'Path'
                            },{
                                xtype: 'textfield',
                                id:'KeyStorePasswordField',
                                fieldLabel:'Password'
                            },{
                                xtype: 'textfield',
                                id:'keyStoreCertAliasField',
                                fieldLabel:'Certificate alias'
                            },{
                                xtype: 'textfield',
                                id:'keyStoreCertPasswordField',
                                fieldLabel:'Certificate password'
                            } ]
                        },{
                            xtype: 'fieldset',
                            title: 'Truststore',
                            bodyStyle:'padding:5px; width:90%',
                            autoWidth:true,
                            items: [{
                                xtype: 'textfield',
                                id:'trustStorePathField',
                                fieldLabel:'Path'
                            },{
                                xtype: 'textfield',
                                id:'trustStorePasswordField',
                                fieldLabel:'Password'
                            }]
                        }]

                    }]
                },{
                    columnWidth:.5,
                    style: 'padding: 0px 0px 0px 5px;',
                    items:[{
                        title: 'Help',
                        layout:'fit',
                        bodyStyle: 'padding: 10px;',
                        html: 'help'
                    }]
                }]

            }]
        }]
    }]
    });

});
