

/*Ext.Client.Interface.*/BBOXField= function(field,numberColsField){
  var bboxformArray=new Array();
  var colSpan=0,size="15";
  if (field.size)
      size=field.size;
  if (field.colSpan)
      colSpan=(parseFloat(field.colSpan))*numberColsField;
  else
      colSpan=numberColsField;

  var u=0;

  var allowBlank=true;
  if(field.allowBlank == "false")
     allowBlank=false;
  var onchange="";
  if(!allowBlank)
     onchange+="_formObj_[_formObj_.length-1].onChangeFieldControlMandatory();";
  if(field.onChange)
    onchange+=field.onChange;

  var selectionToolbar = new Ext.Toolbar();
  var setAOI="function(){ var aoiArray=this.currentAOI.split(',');"+
              "document.getElementById('"+field.id+"WestBBOX').value=aoiArray[0];"+
              "document.getElementById('"+field.id+"SouthBBOX').value=aoiArray[1];"+
              "document.getElementById('"+field.id+"EastBBOX').value=aoiArray[2];"+
              "document.getElementById('"+field.id+"NorthBBOX').value=aoiArray[3];"+
             /* "document.getElementById('"+field.id+"WestBBOX').focus();"+
              "document.getElementById('"+field.id+"SouthBBOX').focus();"+
              "document.getElementById('"+field.id+"EastBBOX').focus();"+
              "document.getElementById('"+field.id+"NorthBBOX').focus();"+
              "document.getElementById('"+field.id+"SouthBBOX').focus();"+*/
               onchange+
              "}";
  var maptool;
  if(field.map)
     maptool=field.map;
  else
     maptool="map";

  
  /* aoiName: inputFormElements[i].getAttribute("aoiName"),
                    aoiColor: inputFormElements[i].getAttribute("aoiColor"),
                    aoiWidth: parseInt(inputFormElements[i].getAttribute("aoiWidth")),
     *this.aoiBorderColor=style.aoiBorderColor;
        this.aoiBorderWidth=style.aoiBorderWidth;
     */
  var aoiName="";
  var aoiStyle="";



  if(field.aoiName)
     aoiName=", aoiName: '"+field.aoiName+"'";

  if(field.aoiColor){
     aoiStyle="aoiBorderColor: '"+field.aoiColor+"'";

  }

  if(field.aoiWidth){
      if(field.aoiColor)
        aoiStyle+=", ";
     aoiStyle+="aoiBorderWidth: "+field.aoiWidth;
  }

  if(field.aoiColor || field.aoiWidth)
     aoiStyle=", aoiStyle: {"+aoiStyle+"}";

  var button="new WebGIS.MapAction.SelectAOI({map: "+maptool+", onChangeAOI:"+setAOI+aoiName+aoiStyle+"})";

  var buttons=new Array();
  buttons.push(button);

  var labels=new Array();
  if(!field.bboxLabels){
     labels.push("NORTH");
     labels.push("WEST");
     labels.push("EAST");
     labels.push("SOUTH");
  }else{
    var fieldlabelsSplit=field.bboxLabels.split(',');
    if(field.localization)
      for(var x=0; x<fieldlabelsSplit.length; x++){
         labels.push(field.localization.getLocalMessage(fieldlabelsSplit[x]));
      }
    else
      labels=fieldlabelsSplit;
  }


  var label;
  if(field.localization && field.label!="" && field.label){
    label=field.localization.getLocalMessage(field.label);
  }else
   label=field.label;

  if(!field.hideLabel){
      bboxformArray[u]={
                        colspan: colSpan,
                        layout: "form",
                        items: [new Ext.form.Field({
                                autoCreate: {tag: 'div', cn:{tag:'div'}},
                                id: 'labelAOIId',
                                hideLabel: true,
                                value: label+":",
                                setValue:function(val) {
                                      this.value = val;
                                      if(this.rendered){
                                          this.el.child('div').update(
                                          '<p>'+this.value+'</p>');
                                      }
                                }
                       })]
                     };
   u=u+1;
  }

   bboxformArray[u]={
      colspan: 1,
      layout: "form",
        html: "&nbsp;"
  };
  bboxformArray[u+1]={
                    colspan: 3,
                    layout: "form",
                    labelAlign: "top",
                    items: [new Ext.form.NumberField({
                                autoCreate : {
                                    tag: "input",
                                    id: field.id+'NorthBBOX',
                                    type: "text",
                                    onchange: onchange,
                                    size: size,
                                    autocomplete: "off"
                                },
                                fieldLabel: labels[0],
                                vtype: 'latitude',
                                msgTarget : 'qtip',
                                decimalSeparator: field.decimalSeparator,
                                decimalPrecision : field.decimalPrecision,
                                name: field.id+"North",
                                id: field.id+'NorthBBOX',
                                labelStyle: 'font-size:8px;',
                                hideLabel : false,
                                value: field.value,
                                allowBlank:allowBlank,
                                validationEvent : 'change'

                          })]
                  };

 bboxformArray[u+2]={
        colspan: colSpan-4,
        html: "&nbsp;"
    };


 bboxformArray[u+3]={
                   colspan: 1,
                   layout: "form",
                   labelAlign: "top",
                   items: [new Ext.form.NumberField({
                                name: field.id+"West",
                                autoCreate : {
                                    tag: "input",
                                    id: field.id+'WestBBOX',
                                    onchange: onchange,
                                    type: "text",
                                    size: size,
                                    autocomplete: "off"
                                },
                                vtype: 'longitude',
                                msgTarget : 'qtip',
                                decimalSeparator: field.decimalSeparator,
                                decimalPrecision : field.decimalPrecision,
                                fieldLabel: labels[1],
                                id: field.id+'WestBBOX',
                                labelStyle: 'font-size:8px;',
                                hideLabel : false,
                                value: field.value,
                                allowBlank:allowBlank,
                                validationEvent : 'change'

			})]
                 };
  var html="";

supportToolbars.push({toolbar: selectionToolbar, id:field.id+'BBOXbar', buttons: buttons});

  html="<div id='"+field.id+"BBOXbar'></div>";

 bboxformArray[u+4]={
        colspan: 3,
        layout: "form",
        html: html
    };


  bboxformArray[u+5]={
                   colspan: 1,
                   layout: "form",
                   labelAlign: "top",
                   items: [new Ext.form.NumberField({
                                name: field.id+"East",
                                id: field.id+'EastBBOX',
                                autoCreate : {
                                    tag: "input",
                                    id: field.id+'EastBBOX',
                                    type: "text",
                                    onchange: onchange,
                                    size: size,
                                    autocomplete: "off"
                                },
                                vtype: 'longitude',
                                msgTarget : 'qtip',
                                decimalSeparator: field.decimalSeparator,
                                decimalPrecision : field.decimalPrecision,
                                fieldLabel: labels[2],
                                labelStyle: 'font-size:8px;',
                                hideLabel : false,
                                value: null,
                                allowBlank:allowBlank,
                                validationEvent : 'change'
			})]
                  };

  bboxformArray[u+6]={
      colspan: colSpan-5,
      html: "&nbsp;"
  };

    bboxformArray[u+7]={
      colspan: 1,
      layout: "form",
        html: "&nbsp;"
  };

  bboxformArray[u+8]={
                   colspan: 3,
                   layout:'form',
                   labelAlign: "top",
                   items: [new Ext.form.NumberField({
                                name: field.id+"South",
                                autoCreate : {
                                    tag: "input",
                                    id: field.id+'SouthBBOX',
                                    type: "text",
                                    onchange: onchange,
                                    size: size,
                                    autocomplete: "off"
                                },
                                id: field.id+'SouthBBOX',
                                vtype: 'latitude',
                                msgTarget : 'qtip',
                                decimalSeparator: field.decimalSeparator,
                                decimalPrecision : field.decimalPrecision,
                                fieldLabel: labels[3],
                                hideLabel : false,
                                labelStyle: 'font-size:8px;',
                                value: field.value,
                                allowBlank:allowBlank,
                                validationEvent : 'change'
                         })]
                  };

 bboxformArray[u+9]={
      colspan: colSpan-4,
      html: "&nbsp;"
  };

  return(bboxformArray);
}

