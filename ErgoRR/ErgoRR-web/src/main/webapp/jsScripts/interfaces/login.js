
/*
 * ErgoRR Login Interface
 * author: Andrea Marongiu
 */

var aboutPage="<div class=\"content\"><b>Developed by:</b><ul><li>Yaman Ustuntas (4C Technologies / kZen)</li><li>Massimiliano Fanciulli (Intecs Informatica e Tecnologia del Software)</li></ul><br/><br/><b>License:</b>General Public License version 3 (GPL3)</div>";

var userEOClient,userCIMClient;
var administrativeArea;
LoginInterface=function(){

     this.xmlInterface="resources/xml/interfaces/login.xml";

     this.formInterface=new Object();

     this.init=function(){
       this.formInterface=createPanelExjFormByXml(this.xmlInterface, interfacesManager.lang);
     },

     this.render=function (elementID){
        this.formInterface.formsPanel.render(document.getElementById(elementID));
        this.formInterface.render();
     };

     this.onLogin=function(){


            var getUserPanelsFunc=function(response){
                  eval(response); 
            }

            var getUserPanelsError=function(){
                  //alert("Error");
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
        
           /*document.cookie = "JSESSIONID" + "=" +
            "; expires=Thu, 01-Jan-70 00:00:01 GMT";*/
 
           var onSubmit=sendAuthenticationXmlHttpRequestTimeOut("GET",
                     "rest/resources/panels",
                     true, null, loginValues['user'], loginValues['password'], 800000, getUserPanelsFunc, getUserPanelsTimeOut,null,
                     null, getUserPanelsError);


              
           
    };

     this.init();
};

