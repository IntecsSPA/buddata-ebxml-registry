
/*
 * ErgoRR Login Interface
 * author: Andrea Marongiu
 */

var aboutPage="<div class=\"content\"><b>Developed by:</b><ul><li> - Yaman Ustuntas <br>(4C Technologies / kZen)</li><li> - Massimiliano Fanciulli <br>(Intecs Informatica e Tecnologia del Software)</li><li> - Andrea Marongiu <br>(Intecs Informatica e Tecnologia del Software)</li></ul><br/><br/><b>License:</b>General Public License version 3 (GPL3)</div>";

var userEOClient,userCIMClient;
var administrativeArea;
LoginInterface=function(){

     this.xmlInterface="resources/xml/interfaces/login.xml";

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

     this.onLogin=function(){


            var getUserPanelsFunc=function(response){
                  
                  var panelResponse=response;
                  var getPropertiesFunc=function(response){
           
                      interfacesManager.readXMLTextProperties(response);
                      
                      eval(panelResponse); 
                  };
                  
                  var getPropertiesError=function(){
                      
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
                  
                  
                  
                  
                  
            }

            var getUserPanelsError=function(){
                  Ext.Msg.show({
                       title:'User access: Error',
                       buttons: Ext.Msg.OK,
                       msg: 'Wrong user or password.',
                       animEl: 'elId',
                       icon: Ext.MessageBox.ERROR
                   });
            }

            var getUserPanelsTimeOut=function(){
                   Ext.Msg.show({
                       title:'User access: Error',
                       buttons: Ext.Msg.OK,
                       msg: 'Request TIME-OUT!',
                       animEl: 'elId',
                       icon: Ext.MessageBox.ERROR
                   });
            }

           var loginValues=this.formInterface.getFormValues();
        
           
            
           this.user= loginValues['user'];
           this.password=loginValues['password'];
           
           interfacesManager.user= loginValues['user'];
           interfacesManager.password=loginValues['password'];
           
 
           var onSubmit=sendAuthenticationXmlHttpRequestTimeOut("GET",
                     "rest/resources/panels",
                     true, null, loginValues['user'], loginValues['password'], 800000, getUserPanelsFunc, getUserPanelsTimeOut,null,
                     null, getUserPanelsError);


              
           
    };

     this.init();
};

