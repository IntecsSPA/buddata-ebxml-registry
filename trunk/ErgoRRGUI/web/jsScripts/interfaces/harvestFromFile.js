
/*
 * Toolbox Harvest From File Interface
 * @author: Andrea Marongiu
 */


HarvestFromFileInterface=function(serviceName){

     this.xmlInterface="resources/xml/interfaces/harvestFromFilePanel.xml";

     this.serviceName= serviceName;

     this.formInterface=createPanelExjFormByXml(this.xmlInterface);

     this.render=function (elementID){
        this.formInterface.formsPanel.render(document.getElementById(elementID));
        this.formInterface.render();
     };

     this.updateCount= function(idServicesGroup){
         /*var checkboxGroupServicesImport=Ext.getCmp('importGroupServices_cont');

         checkboxGroupServicesImport.updateRemoteUrl("manager?cmd=getServicesListFromServices&id="+idServicesGroup);*/
     }

     this.onCheckService=function(){
         var currentSelection=this.formInterface.formsArray[0].getForm().findField("importGroupServices").getSelected();
         var multiText=Ext.getCmp("DuplicateNameImport");
         multiText.removeAll(false);
         for(var i=0; i<currentSelection.length; i++)
           multiText.addTextField(currentSelection[i],"<b>\""+currentSelection[i]+"\"</b> New name",currentSelection[i]);
           multiText.doLayout();
    };

     this.onHarvest=function(){
          var formValuesImport=this.formInterface.getFormValues(false);
            
           if(formValuesImport.metadataFile.uploadID){

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
//                                window.location = 'tools.jsp?extVers=3&serviceName='+respObj.serviceName;
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
//                                window.location = 'tools.jsp?extVers=3&serviceName='+respObj.serviceName;
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
                     "rest/harvest/fromFile?id="+formValuesImport.metadataFile.uploadID,
                     true, null, 800000, importServiceFunc, importServiceTimeOut,null,
                     loading, null);
           }
    };

};

