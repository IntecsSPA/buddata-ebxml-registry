


/*Import Form Interfaces -- START*/
    gcManager.loadGlobalScript("jsScripts/interfaces/login.js");

    gcManager.loadGlobalScript("jsScripts/interfaces/catalogueConfiguration.js");

    gcManager.loadGlobalScript("jsScripts/interfaces/userEOCatalogueClient.js");

    gcManager.loadGlobalScript("jsScripts/interfaces/userCIMCatalogueClient.js");




/*Import Form Interfaces -- END*/


ergoRRUIManager = function()
{
  
  return {

          bodyInfo: "<div class=\"content\">"+
            "<b>Developed by:</b>"+
            "<ul>"+
                "<li>Yaman Ustuntas (4C Technologies / kZen)</li>"+
                "<li>Massimiliano Fanciulli (Intecs Informatica e Tecnologia del Software)</li>"+
            "</ul>"+
            "<br/><br/>"+
            "<b>License:</b>"+
            "General Public License version 3 (GPL3)"+
            "<br> "+
            "You can configure this ebRR instance through the Graphical interface. Click <a href=\"#\" onclick=\"javascript:ergoRRUIManager.showLogin()\">here</a> for accessing it."+
          "</div>",
          loginWindow: null,
          loginInterface: null,
          workspacePanel: null,
          workspacePanelDIV: "workspacePanel_div",
          localizationPath: "resources/xml/localization/ergoRRUIManager/",
          loc: null,
          spot :null,
          bodyColor: '#eeeeee',
          init:function(){
              //  alert("init");
              ergoRRUIManager.spot=new Ext.ux.Spotlight({
                easing: 'easeOut',
                duration: .3
              });
              ergoRRUIManager.loc= new localization(ergoRRUIManager.localizationPath+gcManager.lang+".xml");
              var northPanel =new Ext.Panel({
                                  region:'north',
                                  id: 'northPanel',
                                  split:true,
                                  border: true,
                                 /* height: screen.height/100*7,
                                  minSize: screen.height/100*7,
                                  maxSize: screen.height/100*7,*/
                                  autoHeight:true,
                                  bodyColor: '#79a3cb',
                                  collapsible: false,
                                  collapsed : false,
                                  autoScroll : false,
                                  margins:'0 0 0 0',
                                  contentEl: "header_div"
              });

              ergoRRUIManager.workspacePanel=new Ext.Panel({
                          region: 'center',
                          //layout:'anchor',
                          layout:'fit',
                          border: true,
                         // anchorSize: {width:1280, height:1024},
                         // contentEl: "content_div",
                          html: "<div id='"+ergoRRUIManager.workspacePanelDIV+"'/>",
                          loadingBarImg: "resources/images/loader.gif",
                          loadingMessage: "Loading... Please Wait...",
                          loadingMessageColor: "black",
                          loadingPanelColor: "#dfe8f6",
                          loadingMessagePadding: 0,
                          loadingBarImgPadding: 0,
                          loadingPanelDuration: 250,
                          cleanPanel: function(){
                              var i;

                               var panelArray=this.findByType('panel');
                              
                              for(i=0; i<panelArray.length; i++)
                                  this.remove(panelArray[i]);

                              var portalArray=this.findByType('portal');
                              
                              for(i=0; i<portalArray.length; i++){
                                  
                                  this.remove(portalArray[i]);
                              }

                             /* var panelArray=this.findByType('panel');
                              alert("panel: " +panelArray.length);
                              for(i=0; i<panelArray.length; i++)
                                  panelArray[i].destroy(true);*/

                              document.getElementById(ergoRRUIManager.workspacePanelDIV).innerHTML="";

                          },
                          loadingPanelWorkspaceEl: 'workspace'/*,
                          hideLoadingPanel: function(){
                              var hideMask = function () {
                                         Ext.get('loadingPanel-info').remove();
                                         Ext.fly('loadingPanel-mask').fadeOut({
                                            remove:true

                                        });
                              };
                              hideMask.defer(this.loadingPanelDuration);
                          },
                          insertLoadingPanel: function(){
                                  if(BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Chrome"){
                                        var workspace=document.getElementById(this.loadingPanelWorkspaceEl);
                                        var divPanelMask=document.createElement('div');
                                        divPanelMask.id="loadingPanel-mask";
                                        var divPanelInfo=document.createElement('div');
                                        divPanelInfo.id="loadingPanel-info";
                                        divPanelInfo.innerHTML=""+
                                            "<span id=\"loadingPanel-img\"><img src=\""+this.loadingBarImg+"\"/></span></br>"+
                                            "<span id=\"loadingPanel-msg\">"+this.loadingMessage+"</span>";
                                        var loadingPanelStyle=document.createElement('style');
                                        loadingPanelStyle.type="text/css";
                                       
                                        

                                        var left=this.x+1+((this.getWidth()-this.getInnerWidth())/2);
                                        var left_perc=left/(screen.availWidth/100);
                                      

                                        var top=this.y;
                                        var top_perc=top/ (screen.availHeight/100);

                                    

                                        
                                        var leftInfo=left+(this.getInnerWidth()/2.3);

                                        var topInfo=top+(this.getInnerHeight()/2.1);
                                        leftInfo= leftInfo/(screen.availWidth/100);
                                        topInfo= topInfo/(screen.availHeight/100);
                                        top= top/(screen.availHeight/100);

                                       
                                       
                                        var styleNode = document.createTextNode("#loadingPanel-mask{"+
                                            "position:absolute;"+
                                            "left:"+left_perc+"%;"+
                                            "top:"+top_perc+"%;"+
                                            "width:"+this.getInnerWidth()+"px;"+
                                            "height:"+this.getInnerHeight()+"px;"+
                                            "z-index:20000;"+
                                            "background-color:"+this.loadingPanelColor+";"+
                                        "}\n"+
                                        "#loadingPanel-info{"+
                                            "position:absolute;"+
                                            "left:"+leftInfo+"%;"+
                                            "top:"+topInfo+"%;"+
                                            "width:100px;"+
                                            "z-index:20000;"+
                                            "height:auto;"+
                                        "}\n"+
                                        "#loadingPanel-msg{"+
                                            "font: normal 10px arial,tahoma,sans-serif;"+
                                            "position:absolute;"+
                                            "left:45%;"+
                                            "top:190%;"+
                                            "width:150px;"+
                                            "color: "+this.loadingMessageColor+";"+
                                        "}\n"+
                                        "#loadingPanel-img{"+
                                            "position:absolute;"+
                                            "left:10%;"+
                                            "top:10%;"+
                                   
                                        "}\n");

                                        workspace.appendChild(loadingPanelStyle);
                                        var test=workspace.getElementsByTagName("style");
                                        test[0].appendChild(styleNode);
                                        workspace.appendChild(divPanelMask);
                                        workspace.appendChild(divPanelInfo);

                                       

                                    

                                    
                                    }

                            }*/
                      });


             var accordionMainMenuPanel= new Ext.Panel({
                                  split:true,
                                  id: "accordionMainMenuPanel",
                                  bodyStyle : {background: ergoRRUIManager.bodyColor},
                                  anchor:'100% 100%',
                                  margins:'5 0 5 5',
                                  layout:'accordion',
                                 /* layoutConfig: {
                                        
                                        titleCollapse: false,
                                        animate: false,
                                        fill : false
                                    },*/

                                  items:[]
                            });

             var westPanel=new Ext.Panel({
                                  region:'west',
                                  id: 'westMainPanel',
                                  split:true,
                                 // hidden: true,
    
                                  listeners: {
                                          "expand": function(){


                                           },
                                           "collapse": function(){


                                           }

                                    },
                                  bodyStyle : {background: ergoRRUIManager.bodyColor},
                                  width: screen.width/100*20,
                                  title: "Control Panel",
                                  //minSize: screen.width/100*25,
                                  collapsible: true,
                                  collapsed : false,
                                  autoScroll : true,
                                  bodyColor: '#79a3cb',
                                  margins:'5 0 5 5',
                                  layout:'anchor',
                                  items: [accordionMainMenuPanel]

           });

              var viewport = new Ext.Viewport({
                            layout:'border',
                            id: 'mainViewPort',
                            items:[westPanel,northPanel,ergoRRUIManager.workspacePanel]
              });

             westPanel.toggleCollapse(false);

             westPanel.hide();

              document.getElementById("workspacePanel_div").innerHTML=ergoRRUIManager.bodyInfo;
              var firebugWarning = function () {
                            var cp = new Ext.state.CookieProvider();

                            if(window.console && window.console.firebug && ! cp.get('hideFBWarning')){
                                Ext.Msg.show({
                                   title: ergoRRUIManager.loc.getLocalMessage('labelFirebugWarning'),
                                   msg: ergoRRUIManager.loc.getLocalMessage('labelFirebugMessage'),
                                   buttons: Ext.Msg.OK,
                                   icon: Ext.MessageBox.WARNING
                                });
                            }
                        }

                        var hideMask = function () {
                            Ext.get('loading').remove();
                            Ext.fly('loading-mask').fadeOut({
                                remove:true,
                                callback : firebugWarning
                            });
                        }

                        hideMask.defer(250);

                       // ergoRRUIManager.workspacePanel.insertLoadingPanel();
          },
          showLogin: function(){
              //alert("show");
               //toolbox.loc.getLocalMessage('applicationName'),
              // alert(ergoRRUIManager.workspacePanel);
               
               //ergoRRUIManager.workspacePanel.insertLoadingPanel();
               document.getElementById("workspacePanel_div").innerHTML="";
               ergoRRUIManager.loginInterface=new LoginInterface();
               ergoRRUIManager.loginWindow = new Ext.Window({
				title: "Login",
                                id: 'loginWin',
                                listeners:{
                                  hide: function(){
                                      ergoRRUIManager.spot.hide();
                                  },
                                  close: function(){
                                      ergoRRUIManager.spot.hide();
                                  }
                                 /* expand: function(){
                                      spot.show('importExportGroupServiceWin');
                                  }*/
                                },
				border: false,
                                animCollapse : true,
                                autoScroll : true,
                                resizable : true,
                                collapsible: true,
				layout: 'fit',
				width: screen.availWidth/3,
				height : 260,
                                closeAction:'close',
                                html: "<div id='loginDIV'/>"
			});
                ergoRRUIManager.loginWindow.show();
                ergoRRUIManager.spot.show('loginWin');
                ergoRRUIManager.loginInterface.render("loginDIV");
               // ergoRRUIManager.workspacePanel

          }
  }
}();

//var toolbox = ToolboxManager.Application;

// Run the application when browser is ready
//DomReady(ergoRRUIManager.init);
//alert("caricamento");
gcManager.setGisClientLibPath("jsScripts/import/gis-client-library");
gcManager.setLanguage("eng");
gcManager.onReady(ergoRRUIManager.init);



