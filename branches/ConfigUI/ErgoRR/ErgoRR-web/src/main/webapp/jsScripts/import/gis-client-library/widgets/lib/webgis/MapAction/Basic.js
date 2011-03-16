/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 *
 * Author: Björn Harrtell
 *
 * @fileoverview Basic map tools implemented as WebGIS.MapAction classes
 */

Ext.namespace('WebGIS', 'WebGIS.MapAction');

/**
 * @class Activates interactive zoom in box on map
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options
 */
WebGIS.MapAction.ZoomInBox = function(config) {
    // default config for this action, also used by button to make it toggle correctly
    config.iconCls = 'webgis-mapaction-zoominbox';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';

    // define an OpenLayers control for this MapAction (is handled by MapAction constructor)
    config.olcontrol = new OpenLayers.Control.ZoomBox();

    // call Ext.Action constructor
    WebGIS.MapAction.ZoomInBox.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.ZoomInBox, WebGIS.MapAction, {
});

/**
 * @class Activates interactive zoom out box on map
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options
 */
WebGIS.MapAction.ZoomOutBox = function(config) {
    config.iconCls = 'webgis-mapaction-zoomoutbox';
    config.olcontrol = new OpenLayers.Control.ZoomBox({out:true});

    WebGIS.MapAction.ZoomOutBox.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.ZoomOutBox, WebGIS.MapAction, { });

/**
 * @class Zooms in one zoomstep
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options
 */
WebGIS.MapAction.ZoomIn = function(config) {
    config.iconCls = 'webgis-mapaction-zoomin';
    config.handler = function() {
        for (i=0; i<WebGIS.MapAction.navigationActions.length; i++) WebGIS.MapAction.navigationActions[i].disable();
        this.map.zoomIn();
    }

    WebGIS.MapAction.navigationActions.push(this);

    WebGIS.MapAction.ZoomIn.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.ZoomIn, WebGIS.MapAction, { });

/**
 * @class Zooms out one zoomstep
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options
 */
WebGIS.MapAction.ZoomOut = function(config) {
    config.iconCls = 'webgis-mapaction-zoomout';
    config.handler = function() {
        for (i=0; i<WebGIS.MapAction.navigationActions.length; i++) WebGIS.MapAction.navigationActions[i].disable();
        this.map.zoomOut();
    }

    WebGIS.MapAction.navigationActions.push(this);

    WebGIS.MapAction.ZoomOut.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.ZoomOut, WebGIS.MapAction, { });

/**
 * @class Zooms map to full extent
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 */
WebGIS.MapAction.FullExtent = function(config) {
    config.iconCls = 'webgis-mapaction-fullextent';
    config.handler = function() {
        for (i=0; i<WebGIS.MapAction.navigationActions.length; i++) WebGIS.MapAction.navigationActions[i].disable();
        this.map.zoomToMaxExtent();
    }

    WebGIS.MapAction.navigationActions.push(this);

    WebGIS.MapAction.FullExtent.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.FullExtent, WebGIS.MapAction, { });

/**
 * @class Activates interactive drag pan on map
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 */
WebGIS.MapAction.DragPan = function(config) {
    config.iconCls = 'webgis-mapaction-dragpan';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';
    config.olcontrol = new OpenLayers.Control.DragPan();

    WebGIS.MapAction.DragPan.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.DragPan, WebGIS.MapAction, { });

/**
 * @class
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 */
WebGIS.MapAction.PreviousExtent = function(config) {
    config.iconCls = 'webgis-mapaction-previousextent';
    config.disabled = true;
    config.handler = function() {
        if (WebGIS.MapAction.currentHistoryPosition<(WebGIS.MapAction.navigationHistory.length-1)) {
            for (i=0; i<WebGIS.MapAction.navigationActions.length; i++) WebGIS.MapAction.navigationActions[i].disable();
            WebGIS.MapAction.currentHistoryPosition++;
            this.map.zoomToExtent(WebGIS.MapAction.navigationHistory[WebGIS.MapAction.currentHistoryPosition]);
        }
    }

    WebGIS.MapAction.navigationActions.push(this);
    WebGIS.MapAction.previousExtentActions.push(this);

    WebGIS.MapAction.PreviousExtent.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.PreviousExtent, WebGIS.MapAction, { });



/**
 * @class Select AOI
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options
 */
WebGIS.MapAction.SelectAOI = function(config) {
    config.iconCls = 'webgis-mapaction-selectAOI';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';
    var setBox=new OpenLayers.Control.SetBox();

    if(config.aoiName)
       setBox.setAOILayerName(config.aoiName);


    if(config.aoiStyle)
       setBox.setAOIStyle(config.aoiStyle);

    setBox.onChangeAOI=config.onChangeAOI;
    config.olcontrol =setBox ;


    WebGIS.MapAction.SelectAOI.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.SelectAOI, WebGIS.MapAction, { });


/**
 * @class Activates interactive Info Feature
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 */
WebGIS.MapAction.InfoFeature = function(config) {
    config.iconCls = 'webgis-mapaction-infofeature';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';
    this.selectControl= new Object();
    this.vectorLayers= new Object();
    this.vectorNames= new Array();
    this.selectControl[config.currentVectorLayer]=new OpenLayers.Control.SelectFeature(config.currentVectorLayer);

    this.vectorLayers[config.currentVectorLayer.name]=config.currentVectorLayer;
    this.vectorNames.push(config.currentVectorLayer.name);
    config.olcontrol = this.selectControl[config.currentVectorLayer];

    this.getContorl=function(name){

      return this.selectControl[name];
    };

    this.addVectorLayer=function(vectorLayer){
       this.vectorLayers[vectorLayer.name]=vectorLayer;
       this.vectorNames.push(vectorLayer.name);
    };

    this.setCurrentVectorLayer=function(name){
        config.currentVectorLayer=this.vectorLayers[name];
        if(!this.selectControl[name])
           this.selectControl[name]=
                new OpenLayers.Control.SelectFeature(this.vectorLayers[name]);

        config.olcontrol = this.selectControl[name];
        WebGIS.MapAction.InfoFeature.superclass.constructor.call(this, config);
        for(var i=0;i<this.vectorNames.length;i++){
            if(this.vectorNames[i]!= name && this.selectControl[this.vectorNames[i]])
                this.selectControl[this.vectorNames[i]].deactivate();
        }
        this.selectControl[name].activate();
    };

    WebGIS.MapAction.InfoFeature.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.InfoFeature, WebGIS.MapAction, { });




/**
 * @class
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 * @param {Array Object} selectableLayers
 */
var selectWMSControl=null; //Define Selection and Remove Selection
WebGIS.MapAction.SelectionWMS = function(config, selectableLayers, selectionStyle) {
    config.iconCls = 'webgis-mapaction-selection';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';

    // define an OpenLayers control for this MapAction (is handled by MapAction constructor)
    selectWMSControl=new OpenLayers.Control.SelectWMS();
    selectWMSControl.setSelectableLayers(selectableLayers);
    if(selectionStyle)
       selectWMSControl.setSimbolyzerSelection(selectionStyle);
    config.olcontrol = selectWMSControl;

    // call Ext.Action constructor
    WebGIS.MapAction.SelectionWMS.superclass.constructor.call(this, config);

    this.setSelectableLayers= function(selectableLayers){
      selectWMSControl.setSelectableLayers(selectableLayers);
    };

    this.setCurrentFilterSelectionLayer= function(selectableLayers, newsld){
      selectWMSControl.setCurrentFilter(selectableLayers, newsld);
    };

    this.getSelectionFilter=function(layername){

      return selectWMSControl.getSelectionFilter(layername);
    }

    this.setSelectionLayers= function (selectionLayers){
       selectWMSControl.setSelectionLayers(selectionLayers);
    }

    this.getSelectionFilterEncoded=function(layername){

      return selectWMSControl.getSelectionFilterEncoded(layername);
    }

    this.unSelect=function(){

      return selectWMSControl.removeSelectedLayer();
    }
}
Ext.extend(WebGIS.MapAction.SelectionWMS, WebGIS.MapAction, { });

/**
 * @class
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 */
WebGIS.MapAction.RemoveSelectionWMS = function(config) {
    config.iconCls = 'webgis-mapaction-remove-selection';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';

    if(!selectWMSControl){
        alert("In order to define the RemoveSelectionWMS button is mandatory\n\
              to define the SelectionWMS button");
    }
    else{
      // define an OpenLayers control for this MapAction (is handled by MapAction constructor)
      var removeSelectWMSControl=new OpenLayers.Control.RemoveSelectWMS();
      removeSelectWMSControl.setSelectWMSControl(selectWMSControl);
      config.olcontrol = removeSelectWMSControl;
   }

    // call Ext.Action constructor
    WebGIS.MapAction.RemoveSelectionWMS.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.RemoveSelectionWMS, WebGIS.MapAction, { });


var scalewindow;
WebGIS.MapAction.SetScaleParameters= function () {
    scaleWindow.SetScaleParameters();

}
WebGIS.MapAction.Scale = function(config) {
    // default config for this action, also used by button to make it toggle correctly
    config.iconCls = 'webgis-mapaction-scale';
    if(config.backgroundColor)
       backColor=config.backgroundColor;
    var formsObject=createPanelExjFormByXml(config.pathGisClient+"widgets/lib/webgis/resources/xml/ScaleConfigurationPanel.xml");
    var setScalePanel= new Ext.Panel({ title: 'Set Scale', border: false, bodyStyle : {background: backColor}, items: {xtype: 'webgis-scalelist', map: config.map}});
    var backColor='#99bbe8';
    if(config.backgroundColor)
       backColor=config.backgroundColor;
    scaleWindow = new Ext.Window({
				title: 'Scales',
				border: false,
				layout: 'form',
                                collapsible: true,
				hideLabels: true,
				width: 294,
                                bodyStyle : {background: backColor},
                                closeAction:'hide',
				height: 320,
                                configuration: config,
                                formsObject: formsObject,
				items: [formsObject.formsPanel,setScalePanel],
                                SetScaleParameters: function(){
                                    var values=this.formsObject.getFormValues();
                                    if(values['displaySystem'] && values['displaySystem']!= '')
                                      this.configuration.scalebar.displaySystem = values['displaySystem'];
                                    if(values['majorDivisions'] && values['majorDivisions']!= ''){
                                        var divisions = parseInt(values['majorDivisions']);
                                        // divisions = Math.max(1, Math.min(isNaN(divisions) ? 0 : divisions, 4));
                                        this.configuration.scalebar.divisions = divisions;
                                    }
                                    if(values['majorDivisions'] && values['majorDivisions']!= ''){
                                        var subdivisions = parseInt(values['subdivisionsMajorDivisions']);
                                        //subdivisions = Math.max(1, Math.min(isNaN(subdivisions) ? 0 : subdivisions, 4));
                                        this.configuration.scalebar.subdivisions = subdivisions;
                                    }
                                    this.configuration.scalebar.showMinorMeasures = values['ShowSubdivisionMeasures'];
                                    this.configuration.scalebar.singleLine = values['SingleLineDisplay'];
                                    this.configuration.scalebar.abbreviateLabel = values['AbbreviatedLabel'];
                                    this.configuration.scalebar.update();
                                }
			})
			scaleWindow.show();
    scaleWindow.setPosition(0,150);
    formsObject.render();
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';
    config.olcontrol = new OpenLayers.Control({
                             activate: function() {
                                     if(!scaleWindow.isVisible()){
                                         scaleWindow.show();
                                         scaleWindow.setPosition(0,150);
                                     }

                                     else
                                        scaleWindow.hide();
                                 }
                            });
    scaleWindow.hide();
    // call Ext.Action constructor
    WebGIS.MapAction.Scale.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.Scale, WebGIS.MapAction, {
});

/**
 * @class
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options
 */
WebGIS.MapAction.NextExtent = function(config) {
    config.iconCls = 'webgis-mapaction-nextextent';
    config.disabled = true;
    config.handler = function() {
        if (WebGIS.MapAction.currentHistoryPosition>0) {
            for (i=0; i<WebGIS.MapAction.navigationActions.length; i++) WebGIS.MapAction.navigationActions[i].disable();
            WebGIS.MapAction.currentHistoryPosition--;
            this.map.zoomToExtent(WebGIS.MapAction.navigationHistory[WebGIS.MapAction.currentHistoryPosition]);
        }
    }

    WebGIS.MapAction.navigationActions.push(this);
    WebGIS.MapAction.nextExtentActions.push(this);

    WebGIS.MapAction.NextExtent.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.NextExtent, WebGIS.MapAction, { });

var legendWindow;
WebGIS.MapAction.SimpleLegend = function(config) {
    config.iconCls = 'webgis-mapaction-simplelegend';

   var othersParam="";
   if(config.SLD)
      othersParam+="&SLD="+config.SLD
   if(config.SLD_BODY)
      othersParam+="&SLD_BODY="+config.SLD_BODY
  var url=config.legendServiceURL+"?REQUEST=GetLegendGraphic&VERSION=1.0.0&"+
       "FORMAT="+config.format+"&WIDTH="+config.width+"&HEIGHT="+config.height+
        "&LAYER="+config.layer+othersParam


    legendWindow = new Ext.Window({
				title: config.legendTitle,
				border: false,
                                id: 'Legend',
				layout: 'fit',
                                collapsible: true,
                                closeAction:'hide',
				hideLabels: true,
                                resizable: false,
				width: config.widthWin,
				height: config.heightWin,
				html: "<img src='"+url+"'/>"
			})
			//legendWindow.show();
			//legendWindow.setPosition(0,450);

    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';
    config.olcontrol = new OpenLayers.Control({
                             activate: function() {
                                     if(!legendWindow.isVisible())
                                        legendWindow.show();
                                     else
                                        legendWindow.hide();
                                 }
                            });

    // call Ext.Action constructor
    WebGIS.MapAction.SimpleLegend.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.SimpleLegend, WebGIS.MapAction, {
});


