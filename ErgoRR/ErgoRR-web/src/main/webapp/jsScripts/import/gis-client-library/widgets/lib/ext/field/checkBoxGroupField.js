/*Ext.Client.Interface.*/CheckBoxGroupField=function (field, numberColsField){

  var formField=new Array();
  var colSpan=0;
  if (field.colSpan)
    colSpan=(parseFloat(field.colSpan)-1)*numberColsField;
  else
    colSpan=1;
  var u=0;
  //var enableInputFunction,alternativeCheckFunction,checkEvent="";

  var label;
  if(field.localization && field.label!="" && field.label){
    label=field.localization.getLocalMessage(field.label);
  }else
   label=field.label;

 
   
   var valuesArray= null;

  
  
  var i=0;

  formField[u]={
             colspan: numberColsField+colSpan,
             layout: "form",
             labelAlign: field.labelAlign/*"top"*/,
             items: [ new Ext.Container({
                            id: field.id+"_cont",
                            idCheckGroup: field.id,//autoEl: 'div',
                            name: field.name,
                            colspan: 40,
                            layout: "form",
                            remoteValuesType: field.remoteValuesType,
                            remoteValuesDataElement: field.remoteValuesDataElement,
                            remoteValuesProperties: field.remoteValuesProperties,
                            remoteValuesURL: field.remoteValuesURL,
                            dataEmptyMessage: field.dataEmptyMessage,
                            buttonLabel: field.buttonLabel,
                            buttonHandler: field.buttonHandler,
                            button: field.button,
                            checked: field.value,
                            boxLabel: field.selDeslAllLabel,
                            selDeslAll: field.selDeslAll,
                            onChange: field.onChange,
                            valueList: field.valueList,
                            columns: field.columns,
                            listeners:{
                                "afterrender": function(){
                                    this.renderCheckBoxGroup();
                                    

                                }
                            },
                            renderCheckBoxGroup: function(){
                              
                                var itemsArray=new Array();
                                var checkInfo;

                                if(this.valueList){
                                    valuesArray=this.valueList.split(",");
                                    for(var ii=0; ii<valuesArray.length; ii++){
                                       checkInfo=valuesArray[ii].split(":");
                                       if(checkInfo.length == 2)
                                            itemsArray.push( {boxLabel: checkInfo[0], name: checkInfo[1]});
                                        else
                                            itemsArray.push({boxLabel: checkInfo[0], name: checkInfo[1], checked: eval(checkInfo[2])});
                                    }
                                } else
                                    if(this.remoteValuesURL &&
                                       this.remoteValuesDataElement &&
                                       this.remoteValuesProperties){

                                          var remoteStore=getStore(this.remoteValuesType, //storeType
                                                                 this.id+"_store", //storeID
                                                                 this.remoteValuesURL, //remoteDataURL
                                                                 this.remoteValuesProperties,
                                                                 this.remoteValuesDataElement,
                                                                 null, this.dataEmptyMessage);
                                          var rec;
                                          for(var zz=0; zz<remoteStore.length; zz++){
                                           rec=remoteStore[zz];
                                           itemsArray.push( {boxLabel: rec["boxLabel"], name: rec["name"]});
                                          }
                                  }

                                var buttonCG=Ext.getCmp(this.id+"_button");
                                
                                if(buttonCG)
                                    buttonCG.destroy();
                                
                                if(this.button =="true"){
                                    //alert("add button: "+ this.buttonLabel);
                                   this.add(
                                        new Ext.Button({
                                              id: this.id+"_button",
                                              name: this.name+"_button",
                                              text: this.buttonLabel,
                                              buttonHandler: this.buttonHandler,
                                              handler: function(){
                                                  if(this.buttonHandler)
                                                      eval(this.buttonHandler+"()");
                                              }
                                              //handlerParm: parameters,
                                              //disabled: field.disabled,
                                              //icon: field.icon
                                              })

                                );
                               
                               this.add(
                                  new Ext.form.Field({
                                    autoCreate: {tag: 'div', cn:{tag:'div'}},
                                    id: this.id+"label",
                                    name: this.id+"label",
                                    hideLabel: true,
                                    value: "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                   })
                               );
                             
                              }
                              
                              if(this.selDeslAll=="true"){
                                    this.add(
                                      new Ext.form.Checkbox({
                                        name: this.id+"_selDelAll",
                                        msgTarget : 'qtip',
                                        boxLabel: this.boxLabel,
                                        onChange: this.onChange,
                                        checked: this.checked,
                                        hideLabel: true,
                                        chekboxGroupId: this.idCheckGroup,
                                        checkBoxGroupDim: itemsArray.length,
                                        listeners: {
                                            check: function() {
                                                var group=this.findParentByType("form").getForm().findField(this.chekboxGroupId);
                                                for(var i=0; i<this.checkBoxGroupDim; i++){
                                                    group.items.items[i].setValue(this.checked);
                                                }
                                            }

                                        },
                                        id: field.id+"_selDelAll"
                                     })
                                   );
                                }

                              var columns=1;
                              if(this.columns){
                                  columns=new Array();
                                  for(var kz=0; kz<this.columns;kz++){
                                      columns.push(300);
                                  }

                              }

                              this.add(
                                new Ext.form.CheckboxGroup({
                                    id:this.idCheckGroup,
                                    colspan: 40,
                                    layout: "form",
                                    msgTarget : 'qtip',
                                    onChange: this.onChange,
                                    getSelected: function(){
                                        var checkgroupvalue= new Array();
                                           for(var kk=0; kk<this.items.length; kk++){
                                               if(this.items.items[kk].checked){
                                                   checkgroupvalue.push(this.items.items[kk].name);
                                               }
                                           }
                                       return checkgroupvalue;
                                    },
                                    listeners:{
                                        change: function(){
                                            if(this.onChange)
                                                eval(this.onChange+'()');
                                        }
                                    },
                                    columns: columns,
                                    items: itemsArray
                                  })
                              );
                              

                            },
                            updateValues: function(){
                                this.removeAll(false);
                                this.remove(this.id+"_button");
                                this.renderCheckBoxGroup();
                                this.doLayout();
                            }
      })]
  };




  return(formField);

}

