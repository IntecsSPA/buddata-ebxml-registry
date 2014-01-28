
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

            
            var getLogLevel=function(response){
                var jsonResp=JSON.parse(response);
                if(jsonResp.logLevel){
                     if(Ext.getCmp("logLevel"))
                     Ext.getCmp("logLevel").setValue(jsonResp.logLevel);            
                }/*else
                                {
                                  deleteAllServices=false;
                                  Ext.Msg.show({
                                            title:'Delete service: Error',
                                            buttons: Ext.Msg.OK,
                                            msg: 'Reason: ' + jsonResp.reason,
                                            animEl: 'elId',
                                            icon: Ext.MessageBox.ERROR
                                        });

                                }*/
                
            };
            var getLogLevelTimeOut=function(){
                
            };
          
           sendAuthenticationXmlHttpRequestTimeOut("GET",
                     "Redirect?url="+interfacesManager.properties.ergoRRURL+"config/log/level",
                     false, null, "", "", 800000, getLogLevel, getLogLevelTimeOut,null,
                     null, null);

            
     };
     
     this.clearLog=function(){
         var deleteLogFunc=function(response){
            var jsonResp=JSON.parse(response);
                if(jsonResp.success){
                  ergoRRUIManager.logManagerInterface.updadeLogView();  
                }else
                    Ext.Msg.show({
                          title:'Log cleaning: Error',
                          buttons: Ext.Msg.OK,
                          msg: 'Reason: ' + jsonResp.reason,
                          animEl: 'elId',
                          icon: Ext.MessageBox.ERROR
                   });      
        };
                           
        var deleteLogTimeOut=function(){
                               
        };
                                              
        sendAuthenticationXmlHttpRequestTimeOut("DELETE",
            "rest/resources/log",
            false, null,ergoRRUIManager.loginInterface.user,
            ergoRRUIManager.loginInterface.password, 800000, 
            deleteLogFunc, deleteLogTimeOut,null,
            null, null);
        
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
                                              
        var rowCmp=Ext.getCmp("logRows");
        var tmp="";
        if(rowCmp)
            tmp="/"+rowCmp.getValue();
        sendAuthenticationXmlHttpRequestTimeOut("GET",
            "rest/resources/log"+tmp,
            false, null,ergoRRUIManager.loginInterface.user,
            ergoRRUIManager.loginInterface.password, 800000, 
            getLogFunc, getLogTimeOut,null,
            null, null);
                                 
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
        
        
        
        
        
        if(Ext.getCmp("logLevel")){
            var logLevel=Ext.getCmp("logLevel").getValue();
            var setLogLevel=function(response){
                var jsonResp=JSON.parse(response);
           
                if(!jsonResp.success){
                    Ext.Msg.show({
                          title:'Log level setting: Error',
                          buttons: Ext.Msg.OK,
                          msg: 'Reason: ' + jsonResp.reason,
                          animEl: 'elId',
                          icon: Ext.MessageBox.ERROR
                   });      
                }
                
            };
            var setLogLevelTimeOut=function(){
                
            };
          
           sendAuthenticationXmlHttpRequestTimeOut("GET",
                     "Redirect?url="+interfacesManager.properties.ergoRRURL+"config/log/level/"+logLevel,
                     false, null, "", "", 800000, setLogLevel, setLogLevelTimeOut,null,
                     null, null);
            
            
        }    
        
        myMask.hide();

              
           
    };

     this.init();
};


function onChangeNumberRows(){
    
    ergoRRUIManager.logManagerInterface.updadeLogView();
}