


/*Import Form Interfaces -- START*/
interfacesManager.loadGlobalScript("jsScripts/interfaces/login.js");

interfacesManager.loadGlobalScript("jsScripts/interfaces/catalogueConfiguration.js");

interfacesManager.loadGlobalScript("jsScripts/interfaces/userEOCatalogueClient.js");

interfacesManager.loadGlobalScript("jsScripts/interfaces/userCIMCatalogueClient.js");




/*Import Form Interfaces -- END*/


ergoRRUIManager = {
    
    /*ergoRRUIManager Layout Panels*/
    northPanel: null,
    westPanel: null,
    workspacePanel: null,
    
    bodyInfo: "<div class=\"content\">"+
    "<b>Developed by:</b>"+
    "<ul>"+
    "<li>Yaman Ustuntas (4C Technologies / kZen)</li>"+
    "<li>Massimiliano Fanciulli (Intecs Informatica e Tecnologia del Software)</li>"+
    "<li>Andrea Marongiu (Intecs Informatica e Tecnologia del Software)</li>"+
    "</ul>"+
    "<br/><br/>"+
    "<b>License:</b>"+
    "General Public License version 3 (GPL3)"+
    "<br> "+
    "You can configure this ebRR instance through the Graphical interface. Click <a href=\"#\" onclick=\"javascript:ergoRRUIManager.showLogin()\">here</a> for accessing it."+
    "</div>",
    loginWindow: null,
    loginInterface: null,
    
    workspacePanelDIV: "workspacePanel_div",
    localizationPath: "resources/xml/localization/ergoRRUIManager/",
    loc: null,
    spot :null,
    bodyColor: '#eeeeee',
    init:function(){
        ergoRRUIManager.spot=new Ext.ux.Spotlight({
            easing: 'easeOut',
            duration: .3
        });
      
        ergoRRUIManager.loc= new localization(ergoRRUIManager.localizationPath+interfacesManager.lang+".xml");
      
        ergoRRUIManager.northPanel =new Ext.Panel({
            region:'north',
            id: 'northPanel',
            split:true,
            border: true,
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
            layout:'fit',
            border: true,
            autoScroll: true,
            id:"workspaceCataloguePanel",
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
                document.getElementById(ergoRRUIManager.workspacePanelDIV).innerHTML="";

            },
            loadingPanelWorkspaceEl: 'workspace'
        });


        var accordionMainMenuPanel= new Ext.Panel({
            split:true,
            id: "accordionMainMenuPanel",
            bodyStyle : {
                background: ergoRRUIManager.bodyColor
                },
            anchor:'100% 100%',
            margins:'5 0 5 5',
            layout:'accordion',
            items:[]
        });

        ergoRRUIManager.westPanel=new Ext.Panel({
            region:'west',
            id: 'westMainPanel',
            split:true,
            listeners: {
                "expand": function(){

                },
                "collapse": function(){
                }
            },
            bodyStyle : {
                background: ergoRRUIManager.bodyColor
                },
            width: BrowserDetect.getWidth(20),
            title: "Control Panel",
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
            items:[ergoRRUIManager.westPanel,ergoRRUIManager.northPanel,ergoRRUIManager.workspacePanel]
        });

        ergoRRUIManager.westPanel.toggleCollapse(false);

        ergoRRUIManager.westPanel.hide();

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

    },
    showLogin: function(){
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
            },
            border: false,
            animCollapse : true,
            autoScroll : true,
            resizable : true,
            collapsible: true,
            layout: 'fit',
            width: BrowserDetect.getWidth(33),
            height : BrowserDetect.getHeight(38),
            closeAction:'close',
            html: "<div id='loginDIV'/>"
        });
        ergoRRUIManager.loginWindow.show();
        ergoRRUIManager.spot.show('loginWin');
        ergoRRUIManager.loginInterface.render("loginDIV");
    }
  
};



interfacesManager.setXmlClientLibPath("jsScripts/import/xmlInterfaces");
interfacesManager.setGisClientLibPath("jsScripts/import/gis-client-library");
interfacesManager.setLanguage("eng");
interfacesManager.onReady(ergoRRUIManager.init);
