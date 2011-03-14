

 /*Load admin accordion Menu */
 document.getElementById("workspacePanel_div").innerHTML="";
 ergoRRUIManager.loginWindow.close();

 var loc= new localization(ergoRRUIManager.localizationPath+interfacesManager.lang+".xml");

 administrativeArea=new CatalogueConfiguration();
 var accordionPanel=Ext.getCmp('accordionMainMenuPanel');


           var munuAccordion= [
                
                {
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
                                                            msg:"Please wait..."
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
                  title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelAbout'),
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

                           document.getElementById(ergoRRUIManager.workspacePanelDIV).innerHTML=aboutPage;
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



         


