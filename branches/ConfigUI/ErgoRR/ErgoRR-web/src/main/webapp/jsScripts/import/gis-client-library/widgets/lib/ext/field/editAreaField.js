

function editAreaLoadDip(){
//    gcManager.loadScript("import/edit_area/edit_area_full.js");
    
}

/*Ext.Client.Interface.*/EditAreaField=function (field, numberColsField){
  var formField=new Array(), rows="4", cols="20";
  if (field.cols)
      cols=field.cols;
  if (field.rows)
      rows=field.rows;
  var colSpan=0;
  if (field.colSpan)
      colSpan=(parseFloat(field.colSpan)-1)*numberColsField;

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
  if(!allowBlank)
     onchange+="_formObj_[_formObj_.length-1].onChangeFieldControlMandatory();";
  if(field.onChange)
    onchange+=field.onChange;


  var label;
  if(field.localization && field.label!="" && field.label){
    label=field.localization.getLocalMessage(field.label);
  }else
   label=field.label;



  formField[u]={
             colspan: numberColsField+colSpan,
             layout: "form",
             items: [new Ext.form.Field({
				name: field.name,
                                autoCreate : {
                                    src: gcManager.gisClientLibPath+"/resources/jsp/getEditArea.jsp"+
                                         "?id="+field.id+
                                         "&syntax="+field.syntax+
                                         "&gcpath="+gcManager.gisClientLibPath+
                                         "&multifiles=false",
                                    tag: "iframe",
                                    id: field.id+"TextAreaIframe",
                                    name: field.id+"TextAreaIframe",
                                    width: '100%',
                                    height: '100%',
                                    frameborder: "0"
                                },
                                fieldType: 'editarea',
                                hideLabel: field.hideLabel,
                                disabled: field.disabled,
                                hidden: false,
                                heigthAuto: field.height,
                                id: field.id,
                                labelStyle: field.labelStyle,
                                labelSeparetor: field.labelSeparetor,
                                multipleFile: field.isMultiFile,
                                listeners:{
                                  'beforerender': function(){
                                     var fieldset=this.findParentByType("fieldset");
                                     var size=fieldset.getSize();
                                     this.autoCreate.width= size.width/1.05;
                                     this.autoCreate.height= eval(this.heigthAuto);
                                  }
                                  
                                },
                                getEditorValue: function(){

                                  var editArea = document.getElementById(this.autoCreate.id);
                                  var editAreaDoc = editArea.contentWindow.document;
                                  return editAreaDoc.getValue();
                                },
                                setEditorValue: function(value){
                                  var editArea = document.getElementById(this.autoCreate.id);
                                  var editAreaDoc = editArea.contentWindow.document;
                                  editAreaDoc.setValue(value);
                                  
                                  /*if(this.multipleFile){
                                     for(var i=0; i<objValue.length; i++){
                                        editAreaLoader.openFile(field.id, objValue[i]);
                                     }
                                  }else
                                     editAreaLoader.setValue(field.id,objValue);*/
                                },
                                allowBlank:allowBlank
			})]
  };

  if(field.adaptButtonLabel){
      formField[u+1]={
                 colspan: numberColsField+colSpan,
                 layout: "form",
                 items: [new Ext.Button({
                                    name: field.id+"adaptButton",
                                    id: field.id+"adaptButton",
                                    iframeId: field.id,
                                    heigthAuto: field.height,
                                    text: field.adaptButtonLabel,
                                    handler: function(){
                                        var iframe=this.findParentByType("form").getForm().findField(this.iframeId);
                                        iframe.setSize(10, 10);
                                        var fieldset=this.findParentByType("fieldset");
                                        var size=fieldset.getSize();
                                        iframe.setSize(size.width/1.05, eval(iframe.heigthAuto));

                                    }
                            })]
        };
  }

  return(formField);
}


