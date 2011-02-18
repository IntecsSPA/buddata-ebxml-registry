// New Ext Vtypes

Ext.apply(Ext.form.VTypes, {
  daterange: function(val, field) {

    var date = field.parseDate(val);

    // We need to force the picker to update values to recaluate the disabled dates display
    var dispUpd = function(picker) {
      var ad = picker.activeDate;
      picker.activeDate = null;
      picker.update(ad);
    };

    if (field.startDateField) {
      var sd = Ext.getCmp(field.startDateField);
      sd.maxValue = date;
      if (sd.menu && sd.menu.picker) {
        sd.menu.picker.maxDate = date;
        dispUpd(sd.menu.picker);
      }
    } else if (field.endDateField) {
      var ed = Ext.getCmp(field.endDateField);
      ed.minValue = date;
      if (ed.menu && ed.menu.picker) {
        ed.menu.picker.minDate = date;
        dispUpd(ed.menu.picker);
      }
    }
    /* Always return true since we're only using this vtype
     * to set the min/max allowed values (these are tested
     * for after the vtype test)
     */
    return true;
  },
  timerangeComponents: new Array("hStart","mStart","sStart","msStart",
                                 "hEnd","mEnd","sEnd","msEnd"),
  timerangeSetControls: function(component,field){
    for(var i=0; i<this.timerangeComponents.length; i++){
       // if(this.timerangeComponents[i]!= component)
           if(Ext.getCmp(field[this.timerangeComponents[i]])){
            Ext.getCmp(field[this.timerangeComponents[i]]).isOnChange=false;
            Ext.getCmp(field[this.timerangeComponents[i]]).validate();
           }
    }
  },
  timerange: function(val, field) {
     var hStart,mStart,sStart,hEnd,mEnd,sEnd,startTime,endTime,msEnd="000",msStart="000";
    if(field.hStart)
       if(Ext.getCmp(field.hStart).getValue())
          hStart=Ext.getCmp(field.hStart).getValue();
       else
          return true;
    else{
       hStart=val;
       if(field.isOnChange){
          this.timerangeSetControls("hStart",field);
       }else
         field.isOnChange=true;
    }
  
    if(field.mStart)
       if(Ext.getCmp(field.mStart).getValue())
          mStart=Ext.getCmp(field.mStart).getValue();
       else
          return true;
    else{
       mStart=val;
       if(field.isOnChange){
          this.timerangeSetControls("mStart",field);
       }else
         field.isOnChange=true;
          
    }

    if(field.sStart)
       if(Ext.getCmp(field.sStart).getValue())
          sStart=Ext.getCmp(field.sStart).getValue();
       else
          return true;
    else{
       sStart=val;
       if(field.isOnChange){
          this.timerangeSetControls("sStart",field);
       }else
         field.isOnChange=true;
    }

    if(field.secondsDiv)
    if(field.msStart)
       if(Ext.getCmp(field.msStart).getValue())
          msStart=Ext.getCmp(field.msStart).getValue();
       else
          return true;
    else{
       if(val > 999){
          val=999;
          field.setValue(999);
       }else{
         msStart=val;
         if(field.isOnChange){
            this.timerangeSetControls("msStart",field);
         }else
            field.isOnChange=true;
       }
    }
 
    if(field.hEnd)
       if(Ext.getCmp(field.hEnd).getValue())
          hEnd=Ext.getCmp(field.hEnd).getValue();
       else
         return true;
    else{
       hEnd=val;
       if(field.isOnChange){
          this.timerangeSetControls("hEnd",field);
       }else
         field.isOnChange=true;
    }

    if(field.mEnd)
       if(Ext.getCmp(field.mEnd).getValue())
          mEnd=Ext.getCmp(field.mEnd).getValue();
       else
          return true;
    else{
       mEnd=val;
      if(field.isOnChange){
          this.timerangeSetControls("mEnd",field);
       }else
         field.isOnChange=true;
    }

    if(field.sEnd)
       if(Ext.getCmp(field.sEnd).getValue())
          sEnd=Ext.getCmp(field.sEnd).getValue();
       else
          return true;
    else{
       sEnd=val;
       if(field.isOnChange){
          this.timerangeSetControls("sEnd",field);
       }else
         field.isOnChange=true;
    }

  if(field.secondsDiv)
   if(field.msEnd)
       if(Ext.getCmp(field.msEnd).getValue())
          msEnd=Ext.getCmp(field.msEnd).getValue();
       else
          return true;
    else{
       if(val > 999){
          val=999;
          field.setValue(999);
       }else{
           msEnd=val;
           if(field.isOnChange){
              this.timerangeSetControls("msEnd",field);
           }else
             field.isOnChange=true;
       }
    }

   /* startTime=""+parseInt(hStart)+mStart+sStart;
    endTime=""+parseInt(hEnd)+mEnd+sEnd;

    return (parseInt(endTime)+1000+msEnd >= parseInt(startTime)+1000+msStart);*/

    startTime=""+hStart+mStart+sStart+msStart;
    endTime=""+hEnd+mEnd+sEnd+msEnd;

    return (endTime >= startTime);
  },
  timerangeText: 'The Start Time must be smaller of the End Time',


  numberrange: function (val, field){
    var minValue=0, maxValue=0;
    
    if(field.maxRangeValueID)
       if(Ext.getCmp(field.maxRangeValueID).getValue())
          maxValue=Ext.getCmp(field.maxRangeValueID).getValue();
       else
          return true;
    else
      maxValue=val;

    if(field.minRangeValueID)
       if(Ext.getCmp(field.minRangeValueID).getValue())
          minValue=Ext.getCmp(field.minRangeValueID).getValue();
       else
          return true;
    else
      minValue=val;

    return (maxValue >= minValue)
  },
  numberrangeText: 'The Min Value must be smaller of the Max Value',

  password: function(val, field) {
    if (field.initialPassField) {
      var pwd = Ext.getCmp(field.initialPassField);
      return (val == pwd.getValue());
    }
    return true;
  },
  passwordText: 'Passwords do not match',

  latitude: function(val) {
      return ((val >= -90)&&(val <= 90));
  },
  latitudeText: 'The Latitude Coordinate must be between -90 and 90.',

  longitude: function(val) {
      return ((val >= -180)&&(val <= 180));
  },
  longitudeText: 'The Longitude Coordinate must be between -180 and 180.',

  percentage: function(val) {
      return ((val >= 0)&&(val <= 100));
  },
  percentageText: 'The Percentage must be between 0 and 100.',

  seconds: function(val){
     return ((val >= 0)&&(val <= 59));
  },
  secondsText: 'The seconds value must be between 0 and 59.',

  minutes: function(val){
     return ((val >= 0)&&(val <= 59));
  },
  minutesText: 'The minutes value is between 0 and 59.',

  millseconds: function(val){
     return ((val >= 0)&&(val <= 999));
  },
  millsecondsText: 'The millseconds value is between 0 and 999.'

});

// ----end new vtype definition


