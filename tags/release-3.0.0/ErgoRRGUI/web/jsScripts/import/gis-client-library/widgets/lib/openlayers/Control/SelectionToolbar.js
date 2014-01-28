/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


OpenLayers.Control.SelectionToolbar = OpenLayers.Class(OpenLayers.Control.Panel, {

    selectWMS: null,
    /**
     * Constructor: OpenLayers.Control.SelectionToolbar 
     * Add our two mousedefaults controls.
     *
     * Parameters:
     * selectbaleLayers - {Array Objcet} selectbaleLayers 
     *    selectable layer Object:
     *        {layer:OpenLayers.Layers.WMS, type:String, propertyGeo:String}
     * options - {Object} An optional object whose properties will be used
     *     to extend the control.
     */
    initialize: function(selectbaleLayers, options) {
        this.selectWMS=new OpenLayers.Control.SelectWMS();
        this.selectWMS.setSelectableLayers(selectbaleLayers);
        OpenLayers.Control.Panel.prototype.initialize.apply(this, [options]);
        this.addControls([
          new OpenLayers.Control.Navigation(),
          new OpenLayers.Control.ZoomBox(),
	  this.selectWMS
        ]);
    },

    /**
     * Method: draw 
     * calls the default draw, and then activates mouse defaults.
     */
    draw: function() {
        var div = OpenLayers.Control.Panel.prototype.draw.apply(this, arguments);
        this.activateControl(this.controls[0]);
        return div;
    },
    
    setNewSelectableLayer: function(selectbaleLayers){
        this.selectWMS.setSelectableLayers(selectbaleLayers);
    },

    getWMSSelectionFilterDocumentString: function (layerName){
     
       return this.selectWMS.getSelectionFilter(layerName);
    },
    

    CLASS_NAME: "OpenLayers.Control.SelectionToolbar"
});