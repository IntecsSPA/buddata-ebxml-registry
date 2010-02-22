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
                    padding:10,
                    items:[{
                        columnWidth:.5,
                        style: 'padding: 10px;',
                        items:[{
                            xtype: 'form',
                            id:'miscForm',
                            itemId:'miscForm',
                            title: 'Misc',
                            draggable:'true',
                            bodyStyle: 'padding: 10px;',
                            buttons:[ {
                                text: 'Reload',
                                handler: function(){
                                    submitMisc();
                                }
                            },
                            {
                                text: 'Apply',
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
                                fieldLabel:'Encoding',
                                value:'UTF-8'
                            },{
                                xtype: 'textfield',
                                id:'langField',
                                fieldLabel:'Language',
                                value:'en-GB'
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
                    style: 'padding: 10px;',
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
                    style:'padding:10px 0px 10px 10px',
                    items:[{
                        xtype: 'form',
                        title: 'Database',
                        bodyStyle:'padding:5px 5px 0',
                        buttons:[ {
                                text: 'Reload',
                                handler: function(){
                                    submitMisc();
                                }
                            },
                            {
                                text: 'Apply',
                                handler: function(){
                                    submitMisc();
                                }
                            }
                        ],
                        items: [{
                                    xtype: 'numberfield',
                                    id:'maxRecordsField',
                                    fieldLabel:'Max records to return',
                                    layout:'fit',
                                    value:20
                                 },
                                {
                                    xtype: 'textfield',
                                    id:'srsIdField',
                                    fieldLabel:'default Srs Id',
                                    value:'4326'
                                 },
                                 {
                                    xtype: 'textfield',
                                    id:'srsNameField',
                                    fieldLabel:'default Srs Name',
                                    value:'EPSG:4326'
                                 },{
                                    xtype: 'numberfield',
                                    id:'resultDepthField',
                                    fieldLabel:'Result depth',
                                    value:4
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
                    style:'padding:10px 0 10px 10px',
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
                    padding:10,
                    items:[{
                        xtype: 'form',
                        title: 'Security',
                        buttons:[ {
                                text: 'Reload',
                                handler: function(){
                                    submitMisc();
                                }
                            },
                            {
                                text: 'Apply',
                                handler: function(){
                                    submitMisc();
                                }
                            }
                        ],
                        items: [{
                            xtype: 'fieldset',
                            title: 'Keystore',
                            bodyStyle:'padding:5px;',
                            autoWidth:true,
                            items: [{
                                xtype: 'textfield',
                                id:'keyStorePathField',
                                fieldLabel:'Path',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'KeyStorePasswordField',
                                fieldLabel:'Password',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'keyStoreCertAliasField',
                                fieldLabel:'Certificate alias',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'keyStoreCertPasswordField',
                                fieldLabel:'Certificate password',
                                value:''
                            } ]
                        },{
                            xtype: 'fieldset',
                            title: 'Truststore',
                            bodyStyle:'padding:5px; width:90%',
                            autoWidth:true,
                            items: [{
                                xtype: 'textfield',
                                id:'trustStorePathField',
                                fieldLabel:'Path',
                                value:''
                            },{
                                xtype: 'textfield',
                                id:'trustStorePasswordField',
                                fieldLabel:'Password',
                                value:''
                            }]
                        }]

                    }]
                },{
                    columnWidth:.5,
                    style:'padding:10px 0 10px 10px',
                    items:[{
                        title: 'Help',
                        layout:'fit',
                        html: 'help'
                    }]
                }]

            }]
        }]
    }]
    });
});
