

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

                },
                {
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelClient'),
                  //  html: "Ext.example.shortBogusMarkup",
                    border:false,
                    autoScroll:true,
                    listeners: {
                        "expand": function(){

                           /*ergoRRUIManager.workspacePanel.insertLoadingPanel();
                           document.getElementById(ergoRRUIManager.workspacePanelDIV).innerHTML=aboutPage;
                           var wP=Ext.getCmp('ClientPortal');*/

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
                                        var myMask = new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
                                                                {
                                                                    msg:"Please wait..."/*,
                                                                    msgCls: "mask-workspace-loading"*/
                                                                }
                                                      );
                                        myMask.show();
                                        var portalPanel=null;
                                        switch (n.attributes.type){
                                            case 'EOClientUser':
                                                    userEOClient=new UserEOCatalogueClient();
                                                    ergoRRUIManager.workspacePanel.cleanPanel();
                                                    portalPanel=new Ext.ux.Portal({
                                                    id: "clientEOUserPortal",
                                                    margins:'35 5 5 0',

                                                     items:[ {
                                                               columnWidth:.33,
                                                               id: "catalogueEOUserInterface",
                                                               items:[userEOClient.westPanel],
                                                               bodyStyle : {background: "#FF0000"}

                                                             },
                                                             {
                                                               columnWidth:.67,
                                                               id: "catalogueEOUserMap",
                                                               items:[userEOClient.mapTool]
                                                             },
                                                             {
                                                               columnWidth:1,
                                                               id: "catalogueEOUserResults",
                                                               items:[userEOClient.southPanel]
                                                             }

                                                           ]

                                                });
                                                   ergoRRUIManager.workspacePanel.add(portalPanel);
                                                   ergoRRUIManager.workspacePanel.doLayout();
                                                   userEOClient.render();
                                                break;
                                            case 'CIMClientUser':
                                                  userCIMClient=new UserEOCatalogueClient();
                                                  ergoRRUIManager.workspacePanel.cleanPanel();
                                                    portalPanel=new Ext.ux.Portal({
                                                    id: "clientEOUserPortal",
                                                    margins:'35 5 5 0',

                                                     items:[ {
                                                               columnWidth:.33,
                                                               id: "catalogueEOUserInterface",
                                                               items:[userCIMClient.westPanel],
                                                               bodyStyle : {background: "#FF0000"}

                                                             },
                                                             {
                                                               columnWidth:.67,
                                                               id: "catalogueEOUserMap",
                                                               items:[userCIMClient.mapTool]
                                                             },
                                                             {
                                                               columnWidth:1,
                                                               id: "catalogueEOUserResults",
                                                               items:[userCIMClient.southPanel]
                                                             }

                                                           ]

                                                });
                                                   ergoRRUIManager.workspacePanel.add(portalPanel);
                                                   ergoRRUIManager.workspacePanel.doLayout();
                                                   userCIMClient.render();
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
                                        myMask.show();
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
                    // html: "Ext.example.shortBogusMarkup",
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelLogging'),
                    autoScroll:true,
                    border:false,
                    listeners: {
                        "expand": function(){

                          var myMask=new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
                                    {
                                        msg:"Please wait..."/*,
                                        msgCls: "mask-workspace-loading"*/
                                    }
                          );
                           myMask.show();
                           ergoRRUIManager.workspacePanel.cleanPanel();
                           var panelHeight=Ext.getCmp("workspaceCataloguePanel").getHeight()-2;
                           new Ext.Panel({
                              renderTo: ergoRRUIManager.workspacePanelDIV,
                              height: panelHeight,
                            
                             frameId: "catalogueLog_frame",
                             listeners: {
                                          "resize" : function(){
                                                             /*var panelHeight=Ext.getCmp("workspacePanel").getHeight()-2;
                                                             document.getElementById(frameId).setAttribute('height', panelHeight);
                                                             this.doLayout();*/
                                           }
                                         },
                                        html: "<div id='catalogueLog_frameID'></div><iframe src='logTest.html' name='catalogueLog_frame' id='catalogueLog_frame' scrolling='yes' width='100%' height='100%' marginwidth='0' marginheight='0'></iframe>"
                            }).show();
                          // document.getElementById(ergoRRUIManager.workspacePanelDIV).innerHTML=aboutPage;
                           myMask.hide();
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
                    // html: "Ext.example.shortBogusMarkup",
                    title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelAbout'),
                    autoScroll:true,
                    border:false,
                    html: aboutPage,
                    listeners: {
                        "expand": function(){

                           /*var myMask=new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
                                    {
                                        msg:"Please wait..."/*,
                                        msgCls: "mask-workspace-loading"
                                    }
                          );
                           myMask.show();
                           ergoRRUIManager.workspacePanel.cleanPanel();

                           document.getElementById(ergoRRUIManager.workspacePanelDIV).innerHTML=aboutPage;
                           myMask.hide();*/
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



         /* var cP=Ext.getCmp('ClientPortal');

          cP.add(userEOClient.westPanel);
          cP.add(userEOClient.mapTool);

          setTimeout('userEOClient.render();', 2000);*/

         // userEOClient.render("eoUserClient_div");

           /*if(formValuesImport.zipServices.uploadID){

               var loading=new Object();
               loading.message="Please Wait ...";
               loading.title="Import Services";*/


