
/*
 * ErgoRR Logging Interface
 * author: Andrea Marongiu
 */


InternalNetworkInterface=function(){

     this.xmlInterface="resources/xml/interfaces/internalNetwork.xml";

     this.formInterface=new Object();
     
     this.user="";
     this.password="";

     this.init=function(){
       this.formInterface=createPanelExjFormByXml(this.xmlInterface, interfacesManager.lang);
     },

     this.render=function (elementID){
        this.formInterface.formsPanel.render(document.getElementById(elementID));
        this.formInterface.render();

            
           var getInternalNetworkInfo=function(response){
                var jsonResp=JSON.parse(response);
                if(jsonResp.port){
                     if(Ext.getCmp("internalNetworkPort"))
                     Ext.getCmp("internalNetworkPort").setValue(jsonResp.port);            
                }
                
                if(jsonResp.host){
                     if(Ext.getCmp("internalNetworkUrl"))
                     Ext.getCmp("internalNetworkUrl").setValue(jsonResp.host);            
                }
                
            };
            var getInternalNetworkTimeOut=function(){
                
            };
          
           sendAuthenticationXmlHttpRequestTimeOut("GET",
                     "rest/set",
                     false, null, interfacesManager.user, interfacesManager.password, 800000, getInternalNetworkInfo, getInternalNetworkTimeOut,null,
                     null, null);

            
     };
     
     

     this.updadeInternalNetworkConfiguration=function(){
        var url=Ext.getCmp("internalNetworkUrl").getValue();
        var port=Ext.getCmp("internalNetworkPort").getValue();

           if( url && port && url != '' && port!=''){
                var getUpdateFunc=function(response){
                  var jsonResp=JSON.parse(response);  
                  if(jsonResp.success){
                      var getPropertiesFunc=function(response){
                          interfacesManager.readXMLTextProperties(response);
                      };


                      var getPropertiesTimeOut=function(){
                          Ext.Msg.show({
                           title:'ErgoRR get Properties: Error',
                           buttons: Ext.Msg.OK,
                           msg: 'Request TIME-OUT!',
                           animEl: 'elId',
                           icon: Ext.MessageBox.ERROR
                          });

                      };

                      sendAuthenticationXmlHttpRequestTimeOut("GET",
                         "rest/resources/properties",
                         true, null, interfacesManager.user, interfacesManager.password, 800000, getPropertiesFunc, getPropertiesTimeOut,null,
                         null, null);
                      
                      Ext.Msg.show({
                       title:'Network Configuration',
                       buttons: Ext.Msg.OK,
                       msg: 'Internal Network Configuration saved',
                       animEl: 'elId',
                       icon: Ext.MessageBox.INFO
                      });
                         
                    }
                 };
               
                           
                var getUpdateTimeOut=function(){
                               
                };
                                              

                sendAuthenticationXmlHttpRequestTimeOut("GET",
                    "rest/set/"+url+"/"+port,
                    false, null,ergoRRUIManager.loginInterface.user,
                    ergoRRUIManager.loginInterface.password, 800000, 
                    getUpdateFunc, getUpdateTimeOut,null,
                    null, null); 
           }else{
               Ext.Msg.show({
                       title:'Network Configuration',
                       buttons: Ext.Msg.OK,
                       msg: 'Please inserta all mandatory parameters',
                       animEl: 'elId',
                       icon: Ext.MessageBox.WARNING
                      });
               
               
           }
       
 

              
           
    };

     this.init();
};


function onChangeNumberRows(){
    
    ergoRRUIManager.logManagerInterface.updadeLogView();
}