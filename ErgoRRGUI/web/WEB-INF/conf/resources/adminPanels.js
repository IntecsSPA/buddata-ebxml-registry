

 /*Load admin accordion Menu */
 document.getElementById("workspacePanel_div").innerHTML="";
 ergoRRUIManager.loginWindow.close();

 var loc= new localization(ergoRRUIManager.localizationPath+interfacesManager.lang+".xml");

 administrativeArea=new CatalogueConfiguration();
 var accordionPanel=Ext.getCmp('accordionMainMenuPanel');


           var munuAccordion= [
                {
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelConfiguration'),
                    border:false,
                    autoScroll:true,
                    items:[
                        new Ext.tree.TreePanel({
                             animate:true,
                             enableDD:false,
                             containerScroll: true,
                             rootVisible:false,
                             split:true,
                             autoScroll:true,
                             //tbar: tb,
                             loader: new Ext.tree.TreeLoader(),
                                root: new Ext.tree.AsyncTreeNode({
                                    expanded: true,
                                    children: [{
                                        text: ergoRRUIManager.loc.getLocalMessage('labelConfigurationMisc'),
                                        type: 'ConfigurationMisc',
                                        leaf: true
                                    }, {
                                        text: ergoRRUIManager.loc.getLocalMessage('labelConfigurationDatabase'),
                                        type: 'ConfigurationDatabase',
                                        leaf: true
                                    }/*,{
                                        text: ergoRRUIManager.loc.getLocalMessage('labelConfigurationSecurity'),
                                        type: 'ConfigurationSecurity',
                                        leaf: true
                                    }*/
                                   ]
                                }),
                                listeners: {
                                    click: function(n) {
                                       /* var myMask = new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
                                                     {
                                                       msg:"Please wait..."
                                                     }
                                        );
                                        myMask.show();*/
                                        ergoRRUIManager.workspacePanel.cleanPanel();
                                        switch (n.attributes.type){
                                            case 'ConfigurationMisc':
                                                 ergoRRUIManager.workspacePanel.add(administrativeArea.miscConfigurationPortal);
                                                 administrativeArea.reloadMisc();
                                                break;
                                            case 'ConfigurationDatabase':
                                                  ergoRRUIManager.workspacePanel.add(administrativeArea.databaseConfigurationPortal);
                                                  administrativeArea.reloadDatabase();
                                              break;
                                            case 'ConfigurationSecurity':
                                                 ergoRRUIManager.workspacePanel.add(administrativeArea.securityConfigurationPortal);
                                                 administrativeArea.reloadSecurity();
                                              break;

                                        }
                                       ergoRRUIManager.workspacePanel.doLayout();
                                      // myMask.hide();
                                    }
                                }
                        })

                    ],
                    iconCls:'settings'

                },{
                    title: /*ergoRRUIManager.loc.getLocalMessage('labelControlPanelLogging')*/"Internal Network Configuration",
                    autoScroll:true,
                    border:false,
                    html: "<div id='internalNetworkDiv'/>",
                    listeners: {
                        "collapse": function(){
                           
                            
                        },
                        "expand": function(){
                            
                            
                           if(!ergoRRUIManager.internalNetworkInterface){
                               
                              ergoRRUIManager.internalNetworkInterface=new InternalNetworkInterface();
                              
                           }
                           
                           ergoRRUIManager.internalNetworkInterface.render('internalNetworkDiv');

                        }
                },
                iconCls:'settings'
              },{
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelClient'),
                 
                    border:false,
                    autoScroll:true,
                    listeners: {
                        "expand": function(){
                        }
                    },
                    items:[
                        new Ext.tree.TreePanel({
                             animate:true,
                             enableDD:false,
                             containerScroll: true,
                             rootVisible:false,
                             split:true,
                             autoScroll:true,
                             loader: new Ext.tree.TreeLoader(),
                                root: new Ext.tree.AsyncTreeNode({
                                    expanded: true,
                                    children: [{
                                        text: ergoRRUIManager.loc.getLocalMessage('labelCatalogueCapabilities'),
                                        type: 'CatalogueCapabilities',
                                        leaf: true
                                    },{
                                        text: ergoRRUIManager.loc.getLocalMessage('labelCatalogueInpsireCapabilities'),
                                        type: 'CatalogueInspireCapabilities',
                                        leaf: true
                                    },{
                                        text: ergoRRUIManager.loc.getLocalMessage('labelEOClientUser'),
                                        type: 'EOClientUser',
                                        leaf: true
                                    }, {
                                        text: ergoRRUIManager.loc.getLocalMessage('labelCIMClientUser'),
                                        type: 'CIMClientUser',
                                        leaf: true
                                    }]
                                }),
                                listeners: {
                                    click: function(n) {
                                        
                                        ergoRRUIManager.myMask.show();
                                        switch (n.attributes.type){
                                            case 'CatalogueCapabilities':
                                                   ergoRRUIManager.workspacePanel.cleanPanel();
                                                   var capabilitiesPath="httpservice?service=CSW-ebRIM&request=GetCapabilities";
                                                   var capabilitiesPanel=new Ext.Panel({
                                                        title: ergoRRUIManager.loc.getLocalMessage('labelCatalogueCapabilities'),
                                                        id: "CatalogueCapabilities",
                                                        bodyStyle : {background: "#e4e7e7"},
                                                        html: "<iframe src='"+interfacesManager.properties.ergoRRURL+capabilitiesPath+"' name='"+n.attributes.type+"_frame' id='"+n.attributes.type+"_frame' scrolling='yes' width='100%' height='100%' marginwidth='0' marginheight='0'></iframe>"
                                                    });
                                                    ergoRRUIManager.workspacePanel.add(capabilitiesPanel);
                                                    ergoRRUIManager.workspacePanel.doLayout();
                                                break;
                                            case 'CatalogueInspireCapabilities':
                                                   ergoRRUIManager.workspacePanel.cleanPanel();
                                                   var capabilitiesInspirePath="inspire?service=CSW&request=GetCapabilities&version=2.0.2";
                                                   var capabilitiesInspirePanel=new Ext.Panel({
                                                        title: ergoRRUIManager.loc.getLocalMessage('labelCatalogueInpsireCapabilities'),
                                                        id: "CatalogueInspireCapabilities",
                                                        bodyStyle : {background: "#e4e7e7"},
                                                        html: "<iframe src='"+interfacesManager.properties.ergoRRURL+capabilitiesInspirePath+"' name='"+n.attributes.type+"_frame' id='"+n.attributes.type+"_frame' scrolling='yes' width='100%' height='100%' marginwidth='0' marginheight='0'></iframe>"
                                                    });
                                                    ergoRRUIManager.workspacePanel.add(capabilitiesInspirePanel);
                                                    ergoRRUIManager.workspacePanel.doLayout();
                                                break;    
                                            case 'EOClientUser':
                                                   ergoRRUIManager.workspacePanel.cleanPanel();
                                                   var eoClientPanel=new Ext.Panel({
                                                        title: ergoRRUIManager.loc.getLocalMessage('labelEOClientUser'),
                                                        id: "harvestFromURLPanel",
                                                        bodyStyle : {background: "#e4e7e7"},
                                                        html: "<iframe src='javascript:parent.ergoRRUIManager.getCatalogueClient(\"eop\");' name='"+n.attributes.type+"_frame' id='"+n.attributes.type+"_frame' scrolling='no' width='100%' height='100%' marginwidth='0' marginheight='0'></iframe>"
                                                    });
                                                    ergoRRUIManager.workspacePanel.add(eoClientPanel);
                                                    ergoRRUIManager.workspacePanel.doLayout();
                                                    
                                                 
                                                break;
                                            case 'CIMClientUser':
                                                  ergoRRUIManager.workspacePanel.cleanPanel();
                                                   var cimClientPanel=new Ext.Panel({
                                                        title: ergoRRUIManager.loc.getLocalMessage('labelCIMClientUser'),
                                                        id: "harvestFromURLPanel",
                                                        bodyStyle : {background: "#e4e7e7"},
                                                        html: "<iframe src='javascript:parent.ergoRRUIManager.getCatalogueClient(\"cim\");' name='"+n.attributes.type+"_frame' id='"+n.attributes.type+"_frame' scrolling='no' width='100%' height='100%' marginwidth='0' marginheight='0'></iframe>"

                                                    });
                                                    ergoRRUIManager.workspacePanel.add(cimClientPanel);
                                                    ergoRRUIManager.workspacePanel.doLayout();
                                              break;

                                        }
                                      setTimeout("ergoRRUIManager.myMask.hide();",1000);
                                    }
                                }
                        })

                    ],
                    iconCls:'settings'
                },
                  
                
                {
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelHarvest'),
                    border:false,
                    autoScroll:true,
                    listeners: {
                        "expand": function(){
                        }
                    },
                    items:[
                        new Ext.tree.TreePanel({
                             animate:true,
                             enableDD:false,
                             containerScroll: true,
                             rootVisible:false,
                             split:true,
                             autoScroll:true,
                             //tbar: tb,
                             loader: new Ext.tree.TreeLoader(),
                                root: new Ext.tree.AsyncTreeNode({
                                    expanded: true,
                                    children: [{
                                        text: ergoRRUIManager.loc.getLocalMessage('labelHarvestFromFile'),
                                        type: 'HarvestFromFile',
                                        leaf: true
                                    }, {
                                        text: ergoRRUIManager.loc.getLocalMessage('labelHarvestFromURL'),
                                        type: 'HarvestFormURL',
                                        leaf: true
                                    }]
                                }),
                                listeners: {
                                    click: function(n) {
                                        var myMask = new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
                                                                {
                                                                    msg:"Please wait..."/*,
                                                                    msgCls: "mask-workspace-loading"*/
                                                                }
                                                      );
                                        myMask.showfaceM
                                        var interfacePanel=null;
                                        switch (n.attributes.type){
                                            case 'HarvestFromFile':
                                                    harvestFromFile= new HarvestFromFileInterface();
                                                    ergoRRUIManager.workspacePanel.cleanPanel();
                                                    
                                                    interfacePanel=new Ext.Panel({
                                                        title: ergoRRUIManager.loc.getLocalMessage('labelHarvestFromFile'),
                                                        id: "harvestFromFilePanel",
                                                        bodyStyle : {background: "#e4e7e7"},
                                                        html: "<div id='HarvestFromFileSystemDiv'/>"
                                                    

                                                    });
                                                   ergoRRUIManager.workspacePanel.add(interfacePanel);
                                                   ergoRRUIManager.workspacePanel.doLayout();
                                                   harvestFromFile.render("HarvestFromFileSystemDiv");
                                                break;
                                            case 'HarvestFormURL':
                                                  harvestFromURL= new HarvestFromURLInterface();
                                                    ergoRRUIManager.workspacePanel.cleanPanel();
                                                    
                                                    interfacePanel=new Ext.Panel({
                                                        title: ergoRRUIManager.loc.getLocalMessage('labelHarvestFromURL'),
                                                        id: "harvestFromURLPanel",
                                                        bodyStyle : {background: "#e4e7e7"},
                                                        html: "<div id='HarvestFromURLDiv'/>"
                                                    

                                                    });
                                                   ergoRRUIManager.workspacePanel.add(interfacePanel);
                                                   ergoRRUIManager.workspacePanel.doLayout();
                                                   harvestFromURL.render("HarvestFromURLDiv");
                                              break;

                                        }
                                      myMask.hide();
                                    }
                                }
                        })

                    ],
                    iconCls:'settings'
                },
                
                {
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelLogging'),
                    autoScroll:true,
                    border:false,
                    html: "<div id='logManagerDiv'/>",
                    listeners: {
                        "collapse": function(){
                           
                            
                        },
                        "expand": function(){
                            
                            
                           if(!ergoRRUIManager.logManagerInterface){
                               
                              ergoRRUIManager.logManagerInterface=new LoggingInterface();
                              
                           }
                           
                           ergoRRUIManager.logManagerInterface.render('logManagerDiv');

                           ergoRRUIManager.logManagerInterface.updadeLogView();
                        }
                },
                iconCls:'nav'
              },{
                  title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelLogout'),
                  autoScroll:true,
                  border:false,
                  listeners: {
                        "expand": function(){

                           var myMask=new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
                                    {
                                        msg:"Please wait..."
                                    }
                          );
                           myMask.show();
                           ergoRRUIManager.workspacePanel.cleanPanel();
                           var logoutSection=this;
                           Ext.Msg.show({
                               
                               title:ergoRRUIManager.loc.getLocalMessage('labelControlPanelLogout'),
                               msg: ergoRRUIManager.loc.getLocalMessage('LogoutMessage'),
                               buttons: Ext.Msg.YESNO,
                               logoutSection: logoutSection,
                               fn: function(btn){
                                   if(btn == 'yes')
                                       window.location.reload();
                                   else{
                                      ergoRRUIManager.workspacePanel.cleanPanel(); 
                                      logoutSection.collapse(false);
                                   }   
                               },
                               animEl: 'elId',
                               icon: Ext.MessageBox.INFO
                          });

                           
                           myMask.hide();
                        }
                },
                iconCls:'nav'
              },{
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelAbout'),
                    autoScroll:true,
                    border:false,
                    html: aboutPage,
                    listeners: {
                        "expand": function(){

                          
                        }
                },
                iconCls:'nav'
              }
          ];

           /*Add west Panel with user options*/

          var westMainPanel=Ext.getCmp('westMainPanel');

          westMainPanel.show();

          westMainPanel.toggleCollapse(false);


          for(var i=0; i<munuAccordion.length; i++){
                accordionPanel.add(munuAccordion[i]);

            }
          accordionPanel.doLayout();



        var urlCheck=interfacesManager.properties.host;
        var portCheck=interfacesManager.properties.port;

           if(!( urlCheck && portCheck && urlCheck != '' && portCheck!='')){
               Ext.Msg.show({
                       title:'Network Configuration Warning',
                       buttons: Ext.Msg.OK,
                       msg: 'Please set your Internal Network Configuration.',
                       animEl: 'elId',
                       icon: Ext.MessageBox.WARNING
                      });
               
           }


