
/*
 * ErgoRR Logging Interface
 * author: Andrea Marongiu
 */


LoggingInterface=function(){

     this.xmlInterface="resources/xml/interfaces/logging.xml";

     this.formInterface=new Object();
     
     this.user="";
     this.password="";

     this.init=function(){
       this.formInterface=createPanelExjFormByXml(this.xmlInterface, interfacesManager.lang);
     },

     this.render=function (elementID){
        this.formInterface.formsPanel.render(document.getElementById(elementID));
        this.formInterface.render();
     };

     this.updadeLogView=function(){
         
          var myMask=new Ext.LoadMask(ergoRRUIManager.workspacePanel.body,
            {
                msg:"Please wait..."
            }
            );
        myMask.show();
        ergoRRUIManager.workspacePanel.cleanPanel();
        var panelHeight=Ext.getCmp("workspaceCataloguePanel").getHeight()-2;
                           
        var newHtml;
        var getLogFunc=function(response){
            newHtml=response;
        };
                           
        var getLogTimeOut=function(){
                               
        };
                           
        var getLogError=function(){
                               
        };
                           
        var rowCmp=Ext.getCmp("logRows");
        var tmp="";
        if(rowCmp)
            tmp="/"+rowCmp.getValue();
        sendAuthenticationXmlHttpRequestTimeOut("GET",
            "rest/resources/log"+tmp,
            false, null,ergoRRUIManager.loginInterface.user,
            ergoRRUIManager.loginInterface.password, 800000, 
            getLogFunc, getLogTimeOut,null,
            null, getLogError);
                                 
        new Ext.Panel({
            title: ergoRRUIManager.loc.getLocalMessage('labelControlPanelLogging'),
            renderTo: ergoRRUIManager.workspacePanelDIV,
            height: panelHeight,
            autoScroll: true,
            frameId: "catalogueLog_frame",
            listeners: {
                "resize" : function(){
                                            
                }
            },
            html: newHtml
        }).show();
        myMask.hide();

              
           
    };

     this.init();
};

