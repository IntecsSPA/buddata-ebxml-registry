
/*
 * Toolbox Harvest from URL Interface
 * author: Andrea Marongiu
 */


HarvestFromURLInterface=function(serviceName){

     this.xmlInterface="resources/xml/interfaces/harvestFromURLPanel.xml";

     this.serviceName= serviceName;

     this.formInterface=createPanelExjFormByXml(this.xmlInterface);

     this.render=function (elementID){
        this.formInterface.formsPanel.render(document.getElementById(elementID));
        this.formInterface.render();
     };


     this.onHarvest=function(){
           var formValuesImport=this.formInterface.getFormValues(false);
           if(formValuesImport.metadataURL !="" && formValuesImport.metadataURL !="http://"){

               var loading=new Object();
               loading.message="Please Wait ...";
               loading.title="Harvest Metadata";

               var importServiceFunc=function(response){
                    var evalStr="var respObj= new Object("+response+");";
                    eval(evalStr);
                    if(respObj.error){
                        Ext.Msg.show({
                              title:'Harvest Metadata: Error',
                              buttons: Ext.Msg.OK,
                              msg: respObj.error,
                              minWidth: screen.width/3,
                              animEl: 'elId',
                              fn: function(){
                               
                              },
                              icon: Ext.MessageBox.ERROR
                        });
                    }else{
                        Ext.Msg.show({
                              title:'Harvest Metadata: Completed',
                              buttons: Ext.Msg.OK,
                              msg: respObj.info,
                              minWidth: screen.width/3,
                              animEl: 'elId',
                              fn: function(){
                                
                              },
                              icon: Ext.MessageBox.INFO
                        });

                    };

               }


               var importServiceTimeOut=function(){
                   Ext.Msg.show({
                                    title:'Harvest Metadata: Error',
                                    buttons: Ext.Msg.OK,
                                    msg: 'Request TIME-OUT!',
                                    animEl: 'elId',
                                    icon: Ext.MessageBox.ERROR
                                });
               }
               
               var onSubmit=sendXmlHttpRequestTimeOut("GET",
                     "rest/harvest/fromURL?source="+formValuesImport.metadataURL,
                     true, null, 800000, importServiceFunc, importServiceTimeOut,null,
                     loading, null);
           }
    };


};

