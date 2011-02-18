
function spinnerLoadDip(){
    alert("spinner");
    gcManager.loadCSS("import/ext/ux/css/Spinner.css");
    gcManager.loadScript("import/ext/ux/Spinner.js");
    gcManager.loadScript("import/ext/ux/SpinnerField.js");
}

/*Ext.Client.Interface.*/SpinnerField= function(field,numberColsField){
  var formField=new Array(), size="20";
  if (field.size)
      size=field.size;
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
             items: [new Ext.ux.form.SpinnerField({
                                fieldLabel: label,
                                id: field.id,
                                hideLabel: field.hideLabel,
                                name: field.name,
                                disabled: field.disabled,
                                labelStyle: field.labelStyle,
                                labelSeparetor: field.labelSeparetor,
                                listeners: {
                                    "onFocus": function(){

                                    }
                                },
                                value: field.value,
                                vtype: field.vtype,
                                vtypeText: field.vtypeText,
                                hidden: field.hidden,
                                minValue: field.min,
                                maxValue: field.max,
                                allowDecimals: true,
                                decimalSeparator: field.decimalSeparator,
                                decimalPrecision : field.decimalPrecision,
                                incrementValue: eval(field.inc),
                                //alternateIncrementValue: 2.1,
                                accelerate: true
			})]
  };
  return(formField);
}

