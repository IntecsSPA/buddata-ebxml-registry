/**

 * 
 * Author: Andrea Marongiu
 *
 * @fileoverview WebGIS.Panel.MapPanel class
 */

Ext.namespace('WebGIS', 'WebGIS.Panel');


WebGIS.Panel.MapPanel = Ext.extend(Ext.Panel, {
    
  toolbarNorth: null,
  map: null,
  WMC: null,
  mapOptions: null,
  border: true,
  region: 'center',
	
 initComponent: function() { 
    this.toolbarNorth=new Ext.Toolbar();     
    this.tbar=this.toolbarNorth;
    WebGIS.Panel.MapPanel.superclass.initComponent.call(this);
  },
  
 draw: function(){
    var i; 
    this.map = new OpenLayers.Map(this.body.dom, this.mapOptions);
    if(this.WMC){
      var formatWMC = new OpenLayers.Format.WMC(this.mapOptions);  
      var contextMap = Sarissa.getDomDocument();
      contextMap.async=false;
      contextMap.validateOnParse=false;
      contextMap.load(this.WMC);
      this.map = formatWMC.read(contextMap, {map: this.map});
      this.map.zoomToMaxExtent(); 
    }
    
    // standard Open Layers
    var standardControlArray=this.standardOLControl.split(',');
    for (i=0; i<standardControlArray.length; i++){
        switch (standardControlArray[i]){
           case 'MousePosition':
              this.map.addControl(new OpenLayers.Control.MousePosition()); 
            break;
           case 'KeyboardDefaults':
              this.map.addControl(new OpenLayers.Control.KeyboardDefaults()); 
            break;
           case 'MouseDefaults':
              this.map.addControl(new OpenLayers.Control.MouseDefaults()); 
            break; 
           case 'LayerSwitcher':
              this.map.addControl(new OpenLayers.Control.LayerSwitcher()); 
            break; 
           case 'OverviewMap':
              var overview = new OpenLayers.Control.OverviewMap(); 
              this.map.addControl(overview);
            break; 
        }
            
    }
    
    // map action is an extended Ext.Action that can be used as a button or menu item
    var webGisControlArray=this.webGisControl.split(',');
    for (i=0; i<webGisControlArray.length; i++){
        switch (webGisControlArray[i]){
           case 'DragPan':
              this.toolbarNorth.add(new WebGIS.MapAction.DragPan({map: this.map})); 
            break;
           case 'ZoomInBox':
              this.toolbarNorth.add(new WebGIS.MapAction.ZoomInBox({map: this.map})); 
            break;
           case 'ZoomOutBox':
              this.toolbarNorth.add(new WebGIS.MapAction.ZoomOutBox({map: this.map})); 
            break; 
           case 'ZoomIn':
              this.toolbarNorth.add(new WebGIS.MapAction.ZoomIn({map: this.map})); 
            break; 
           case 'ZoomOut':
              this.toolbarNorth.add(new WebGIS.MapAction.ZoomOut({map: this.map}));
            break;
           case 'PreviousExtent':
              this.toolbarNorth.add(new WebGIS.MapAction.PreviousExtent({map: this.map})); 
            break;
           case 'NextExtent':
              this.toolbarNorth.add(new WebGIS.MapAction.NextExtent({map: this.map})); 
            break;
           case 'FullExtent':
              this.toolbarNorth.add(new WebGIS.MapAction.FullExtent({map: this.map})); 
            break; 
           case 'Scale':
              this.toolbarNorth.add(new WebGIS.MapAction.Scale({map: this.map})); 
            break; 
        }
            
    }
        
   this.map.enebaleMapEvent=true;        
 } 
});  