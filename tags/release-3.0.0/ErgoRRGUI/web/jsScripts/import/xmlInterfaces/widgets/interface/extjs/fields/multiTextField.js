

/*Ext.Client.Interface.*/MultiTextField=function (field, numberColsField){


  var formField=new Array(), size="20";
  var colSpan=0;
  if (field.colSpan)
      colSpan=(parseFloat(field.colSpan)-1)*numberColsField;
  if (field.size)
      size=field.size;

  var u=0;

 if(colSpan>0){
    formField[0]={
        colspan: colSpan,
        html: "&nbsp;"
    };
    u++;
  }
  var allowBlank=true;
  if(field.allowBlank == "false")
     allowBlank=false;

  var onchange="";
 
  if(field.onChange)
    onchange+=field.onChange;

  var label;
  if(field.localization && field.label!="" && field.label){
    label=field.localization.getLocalMessage(field.label);
  }else
   label=field.label;

  if(field.remoteControlURL)
      field.vtype="remotecontrol";



  var columns=1;
  if(field.columns)
      columns=parseInt(field.columns);
  
  formField[u]={
             colspan: numberColsField+colSpan,
             layout: "form",
             labelAlign: field.labelAlign/*"top"*/,
             items: [new Ext.Container({
                        autoEl: 'div',
                        size: size,
                        width:size*10*columns,
                        layout: 'table',
                        id: field.id,
                        remoteControlURL: field.remoteControlURL,
                        vtype: field.vtype,
                        layoutConfig: {
                          columns: columns
                        },
                        getValues: function(){
                            this.doLayout();
                            var textArray=this.find("xtype", "textfield");
                            var objValue=new Array();
                            for(var i=0; i<textArray.length;i++){
                                var obj={
                                    "id":textArray[i].id,
                                    "value": textArray[i].getValue()
                                };
                                
                                objValue.push(obj);
                            }
                           
                            return objValue;
                        },
                        getStringValues: function(){
                            var textArray=this.find("xtype", "textfield");
                            var stringValue="";
                            for(var i=0; i<textArray.length;i++){
                                stringValue+=textArray[i].getValue();
                                if(i<textArray.length-1)
                                    stringValue+=",";

                            }

                            return stringValue;
                        },
                        
                        addSpace: function(idSpace, spaceSize){
                           var spaces=""; 
                           for(var i=0; i<spaceSize; i++)
                               spaces+="&nbsp;";
                           this.add({
                                      colspan: 1,
                                      layout: "form",
                                      items: [
                                             new Ext.form.Field({
                                                autoCreate: {tag: 'div', cn:{tag:'div'}},
                                                id: idSpace,
                                                name: idSpace,
                                                hideLabel: true,
                                                value: spaces,
                                                 setValue:function(val) {
                                                    this.value = val;
                                                    if(this.rendered){
                                                        this.el.child('div').update(
                                                        '<p>'+this.value+'</p>');
                                                }
                                               } 
                                           })
                                      ]}  
                                 ); 
                            
                        },
                        
                        addTextField: function(textID, textLabel, textValue, textSize){
                                   this.add({
                                            colspan: 1,
                                            layout: "form",
                                            items: [
                                             new Ext.form.TextField({
                                                
                                                name: textID,
                                                autoCreate : {
                                                    tag: "input",
                                                    id: textID,
                                                    type: "text",
                                                    size: textSize,
                                                    autocomplete: "off"
                                                },
                                                xtype: "textfield",
                                                remoteControlURL: this.remoteControlURL,
                                                vtype: this.vtype,
                                                value: textValue,
                                                hideLabel: false,
                                                id: textID,
                                                size: textSize,
                                                fieldLabel: textLabel
                                               
                                            })
                                     ]}  
                                 );
                                },
                       addButtonField: function(buttonID, buttonLabel, buttonOnclickFunction, buttonIcon){
                                   this.add({
                                            colspan: 1,
                                            layout: "form",
                                            items: [
                                              
                                              new Ext.Button({
                                              id: buttonID,
                                              name: buttonID,
                                              text: buttonLabel,
                                              onclickFunction: buttonOnclickFunction,
                                              handler: function(){
                                                  eval((this.onclickFunction+";"));
                                              },
                                              icon: buttonIcon
                                          })]
                                        }                 
                                    );
                                },   
                                
                        addFileField: function(fileID, fileLabel, fileSize, fileAutoUploadURL, 
                                        fileOnSuccessMethod,
                                        fileIconWait,
                                        fileIconFailure,
                                        fileIconSuccess){
                                   
                                  var fileUpload=new Ext.ux.form.FileUploadField({
                                        id: fileID+"_file",
                                        width: eval(fileSize*7),
                                        emptyText: fileLabel,
                                        fieldLabel: fileLabel,
                                        autoUploadURL: fileAutoUploadURL,
                                        name: fileID+"_file",
                                        labelIcon: "Label"+fileID,
                                        onSuccessMethod: fileOnSuccessMethod,
                                        iconWait: fileIconWait,
                                        fileValue: "",
                                        buttonText: '',
                                        reset: function(){
                                            if(this.iconWait){
                                              var label=this.findParentByType("form").getForm().findField(this.labelIcon);
                                                      label.setValue("");
                                            }
                                            this.setValue("");
                                            this.fileInput.dom.value="";
                                        },
                                        listeners: {
                                            "fileselected": function (){
                                                if(this.autoUploadURL){
                                                   if(this.iconWait){
                                                      var label=this.findParentByType("form").getForm().findField(this.labelIcon);
                                                      label.setValue("<img src='"+this.iconWait+"'/>");
                                                   }

                                                   this.findParentByType("form").getForm().submit({
                                                        url: this.autoUploadURL,
                                                        method: 'POST',
                                                        iconSuccess: this.iconSuccess,
                                                        iconFailure: this.iconFailure,

                                                        success: function(form, action){
                                                            var jsonResponseObj=eval('new Object(' + action.response.responseText + ')');

                                                            if(form.iconSuccess){
                                                              if(jsonResponseObj.id){
                                                                var idField=form.findField(form.fieldID);
                                                                idField.setValue(jsonResponseObj.id);
                                                              }
                                                              var label=form.findField(form.labelIcon);
                                                              label.setValue("<img src='"+form.iconSuccess+"'/>");
                                                           }
                                                           if(form.editAreaID){
                                                                var editArea = document.getElementById(form.editAreaID+"TextAreaIframe");
                                                                var editAreaDoc = editArea.contentWindow.document;
                                                                var textResp=eval("'"+jsonResponseObj.content+"'");
                                                                editAreaDoc.setValue(textResp);
                                                           }

                                                          var file=form.findField(form.fileID);
                                                          file.fileInput.dom.value="";

                                                          eval(form.onSuccessMethod+"('"+jsonResponseObj.id+"');");
                                                        },
                                                        failure: function(form, action) {

                                                            if(form.iconFailure){
                                                              var label=this.findParentByType("form").getForm().findField(this.labelIcon);
                                                              label.setValue("<img src='"+form.iconFailure+"'/>");
                                                           }


                                                        }
                                                     });
                                                }
                                         }

                                        },
                                        buttonCfg: {
                                            iconCls: field.icon
                                        }
                                       });

                           var contentForm=fileUpload;
                           if(fileLabel)
                               contentForm=new Ext.form.FieldSet({
                                      title: fileLabel,
                                      layout: 'table',
                                      autoHeight: true,
                                      items: [fileUpload]
                                });

                           if(fileAutoUploadURL){
                               contentForm.items.add(
                                    new Ext.form.Field({
                                        autoCreate: {tag: 'div', cn:{tag:'div'}},
                                        id: "Label"+fileID,
                                        name: "Label"+fileID,
                                        hideLabel: true,
                                        setValue:function(val) {
                                            this.value = val;
                                            if(this.rendered){
                                                this.el.child('div').update(
                                                "&nbsp;&nbsp;&nbsp;&nbsp;"+this.value);
                                        }
                                      }
                                   }));
                               contentForm.items.add(
                                    new Ext.form.TextField({
                                                name: fileID+"UploadID",
                                                value: "",
                                                hideLabel: true,
                                                hidden: true,
                                                id: fileID+"UploadID"
                                        }));


                                }

                                   var fileUploadFormPanel=new Ext.FormPanel({
                                            colspan: 1,
                                            
                                            divName: "FileForm"+fileID,
                                            fileUpload: true,
                                            frame: true,
                                            width: fileSize*8,
                                            iconSuccess: fileIconSuccess,
                                            iconFailure: fileIconFailure,
                                           // onSuccessMethod: fileOnSuccessMethod,
                                            labelIcon: "Label"+fileID,
                                            fieldID: fileID+"UploadID",
                                            fileID: fileID+"_file",
                                            //editAreaID: fileID.editAreaID,
                                            autoShow : true,
                                            items: [contentForm]
                                        }); 
                                   
                                   
                                  
                                   this.add({
                                            colspan: 1,
                                            layout: "form",
                                            //fileUpload: true,
                                            //frame: true,
                                            id: fileID,
                                            iconSuccess: fileIconSuccess,
                                            iconFailure: fileIconFailure,
                                            onSuccessMethod: fileOnSuccessMethod,
                                             width: fileSize*8,
                                            items: [contentForm]
                                        }                 
                                    );
                                },        
                                      
                       addComboField: function(comboID, comboLabel, comboSize, 
                                             comboStoreFields, comboStoreData, comboDefaultValue){
                                                
                                var store;    
                                var mode;
                                if(comboStoreFields && comboStoreData){
                                    store = new Ext.data.SimpleStore({
                                        id: "store"+comboID,	
                                        fields: comboStoreFields,
                                        data : comboStoreData
                                      });
                                  mode="local";    
                                }  
                                 
                                  /*else
                                    if(field.getStoreMethod)
                                       store=eval(field.getStoreMethod);*/
                                   this.add({
                                            colspan: 1,
                                            layout: "form",
                                            items: [
                                            
                                                new Ext.form.ComboBox({
                                                        store: store,
                                                        mode: mode,
                                                        autoShow: true,
                                                        storeFields: comboStoreFields,
                                                        displayField: comboStoreFields[0],
                                                        id: comboID,
                                                        name: comboID,
                                                        value: comboDefaultValue,
                                                        msgTarget : 'qtip',
                                                        typeAhead: true,
                                                        autoCreate: {tag: "input", 
                                                                     type: "text", 
                                                                     id:comboID, 
                                                                     size: comboSize, 
                                                                     autocomplete: "on"},
                                                        fieldLabel: comboLabel,
                                                        triggerAction: 'all',
                                                        stateful: false,
                                                        //emptyText: label,
                                                        selectOnFocus:true,
                                                        arrayStore: comboStoreData,
                                                        allowBlank:allowBlank,
                                                        getValueInformation: function(infoValue){
                                                            var i;

                                                            for(i=0;i<this.store.getTotalCount();i++){

                                                                if(this.store.getAt(i).get(this.displayField) == this.value)
                                                                   return(this.store.getAt(i).get(infoValue)); 
                                                            }
                                                            return(null);
                                                        },
                                                        setStore: function(newStoreData,newstoreFields,newDisplayField){
                                                            var storeF;
                                                            if(newstoreFields){
                                                              storeF=newstoreFields;
                                                              this.storeFields=newDisplayField;
                                                            }
                                                            else
                                                              storeF=this.storeFields;
                                                            this.arrayStore=newStoreData;
                                                            this.storeFields=storeF;
                                                            var newStore = new Ext.data.SimpleStore({
                                                                    id: "store"+this.id,	
                                                                    fields: storeF,
                                                                    data : newStoreData
                                                            }); 
                                                            this.bindStore(newStore);
                                                        }


                                                })
                                            
                                            ]
                                        }                 
                                    );
                        }         

             })]};


  return(formField);



}

