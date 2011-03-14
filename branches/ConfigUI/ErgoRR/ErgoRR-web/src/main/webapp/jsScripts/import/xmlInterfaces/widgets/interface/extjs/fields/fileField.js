


/*Ext.Client.Interface.*/FileField=function(field, title, numberColsField){

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

  var submitLabel;
  if(field.localization && field.submitLabel!="" && field.submitLabel){
    submitLabel=field.localization.getLocalMessage(field.submitLabel);
  }else
   submitLabel=field.submitLabel;

 
          var fileUpload=new Ext.ux.form.FileUploadField({
                        id: field.id+"_file",
                        width: eval(field.size),
                        emptyText: field.blankText,
                        fieldLabel: field.label,
                        autoUploadURL: field.autoUploadURL,
                        name: field.id+"_file",
                        labelIcon: "Label"+field.id,
                        onSuccessMethod: field.onSuccessMethod,
                        iconWait: field.iconWait,
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
           if(field.label)
               contentForm=new Ext.form.FieldSet({
                      title: field.label,
                      layout: 'table',
                      autoHeight: true,
                      items: [fileUpload]
                });

           if(field.autoUploadURL){
               contentForm.items.add(
                    new Ext.form.Field({
                        autoCreate: {tag: 'div', cn:{tag:'div'}},
                        id: "Label"+field.id,
                        name: "Label"+field.id,
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
				name: field.id+"UploadID",
                                value: "",
                                hideLabel: true,
                                hidden: true,
                                id: field.id+"UploadID"
			}));


           }

           var fileUploadFormPanel=new Ext.FormPanel({

                    divName: title+"FileForm"+field.id,
                    fileUpload: true,
                    frame: true,
                    width: '100%',
                    iconSuccess: field.iconSuccess,
                    iconFailure: field.iconFailure,
                    onSuccessMethod: field.onSuccessMethod,
                    labelIcon: "Label"+field.id,
                    fieldID: field.id+"UploadID",
                    fileID: field.id+"_file",
                    editAreaID: field.editAreaID,
                    autoShow : true,
                    items: [contentForm]
                });

  return(fileUploadFormPanel);

}

