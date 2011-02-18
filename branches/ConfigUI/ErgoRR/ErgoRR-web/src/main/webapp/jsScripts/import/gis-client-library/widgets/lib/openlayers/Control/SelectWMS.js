
/**
 * @requires OpenLayers/Control.js
 * @requires OpenLayers/Handler/Box.js
 */

/**
 * Class: OpenLayers.Control.SelectWMS
 *
 * Inherits from:
 *  - <OpenLayers.Control>
 */






OpenLayers.Control.SelectWMS = OpenLayers.Class(OpenLayers.Control, {
    /**
     * Property: type
     * {OpenLayers.Control.TYPE}
     */

    type: OpenLayers.Control.TYPE_TOOL,

    /**
     * Property: out
     * {Boolean} Should the control be used for zooming out?
     */
    out: false,


    /**
     * Property: aoi
     * {Object} Area of Interest
     */
    aoi: {
          lowerCorner:{x:null, y:null},
          upperCorner:{x:null, y:null}
         },

    /**
     * Property: srs
     * Spatial Reference System for the AOI
     */
    srs: "4326",

     /**
     * Property: selectableLayers
     *       Selectable Layer Object
     *          {layer:OpenLayers.Layers.WMS, type:String, propertyGeo:String,
     *          sld: OpenLayers.SLDWMS }
     * {Array Object}
     */
    selectableLayers: [],


    /**
     * Property: selectionLayers Array of current Selection Layer
     * {Array OpenLayers.Layers.WMS}
     */
    selectionLayers: [],


    /**
     * Property: currentFilter Hash
     * {Hash String}
     */
    currentFilter:[],

     /**
     * APIProperty: symbolizerSelection.
     * {Object} A symbolizer Selection defaults.
     */
    symbolizerSelection: {
        graphicName: "square",
        fillColor: "#ff0000",
        fillOpacity: 1,
        strokeColor: "#33FF66",
        strokeOpacity: 1,
        strokeWidth: 2,
        pointRadius: 8
    },

    defaultRuleSelectionName:"defaultSelection",

    defaultRuleSelectionTitle:"defaultSelection",

     /**
     * Constructor: OpenLayers.Control.SelectWMS
     *
     *
     * Parameters:
     * selectbaleLayers - {Array Objcet} selectableLayers
     *    layer selectable Object:
     *        {layer:OpenLayers.Layers.WMS, type:String, propertyGeo:String}
     * options - {Object} An optional object whose properties will be used
     *     to extend the control.
     */
    /*initialize: function(selectableLayers) {
      this.selectableLayers=selectableLayers;
    },*/



    /**
     * Method: draw
     */
    draw: function() {

        this.handler = new OpenLayers.Handler.Box( this,
                            {done: this.selectBox}, {keyMask: this.keyMask} );
    },



    /**
     * Method: selectBox
     *
     * Parameters:
     * position - {<OpenLayers.Bounds>} or {<OpenLayers.Pixel>}
     */
    selectBox: function (position) {

        if(this.selectionLayers.length > 0){
            this.removeSelectedLayer();
        }
           // this.map.removeLayer(oldBox,null);
        //this.map.removeLayer("boxes");
        if (position instanceof OpenLayers.Bounds) {
            if (!this.out) {
                var minXY = this.map.getLonLatFromPixel(
                            new OpenLayers.Pixel(position.left, position.bottom));
                var maxXY = this.map.getLonLatFromPixel(
                            new OpenLayers.Pixel(position.right, position.top));


                this.aoi.lowerCorner={x:minXY.lon, y:minXY.lat};
                this.aoi.upperCorner={x:maxXY.lon, y:maxXY.lat};

                this.selectInAOI();

            } else {
                var pixWidth = Math.abs(position.right-position.left);
                var pixHeight = Math.abs(position.top-position.bottom);
                var zoomFactor = Math.min((this.map.size.h / pixHeight),
                    (this.map.size.w / pixWidth));
                var extent = this.map.getExtent();
                var center = this.map.getLonLatFromPixel(
                    position.getCenterPixel());
                var xmin = center.lon - (extent.getWidth()/2)*zoomFactor;
                var xmax = center.lon + (extent.getWidth()/2)*zoomFactor;
                var ymin = center.lat - (extent.getHeight()/2)*zoomFactor;
                var ymax = center.lat + (extent.getHeight()/2)*zoomFactor;
                this.aoi.lowerCorner={x:xmin, y:ymin};
                this.aoi.upperCorner={x:xmax, y:ymax};

                this.selectInAOI();
            }


        } else { // it's a pixel
            if (!this.out) {
                this.map.setCenter(this.map.getLonLatFromPixel(position),
                               this.map.getZoom() + 1);
            } else {
                this.map.setCenter(this.map.getLonLatFromPixel(position),
                               this.map.getZoom() - 1);
            }
        }

    },

    selectInAOI: function (){

        var sldObj,filterSelection,sldString;
        var format;
            if (document.all){
                //alert("Explorer");
                format = new OpenLayers.Format.SLDShort();
            }else{
                //alert("No Explorer");
                format = new OpenLayers.Format.SLD();
            }

        for(var i=0; i<this.selectableLayers.length; i++){
            filterSelection=new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.GeoSpatial.BBOX,
                    property: this.selectableLayers[i].propertyGeo,
                    srs: this.srs,
                    lowerCorner: this.aoi.lowerCorner,
                    upperCorner: this.aoi.upperCorner
            });

            if(!this.selectableLayers[i].sld){

                this.selectableLayers[i].sld= new OpenLayers.SLDWMS(
                  this.selectableLayers[i].layer.name, this.selectableLayers[i].type);
                /*/this.selectableLayers[i].sld.removeAddedFilterToEachRule(
                OpenLayers.Filter.GeoSpatial.BBOX, OpenLayers.Filter.Logical.AND,
                this.selectableLayers[i].layer.name);  */
                this.selectableLayers[i].sld.addRule(
                  this.selectableLayers[i].layer.name,
                  this.defaultRuleSelectionName, this.defaultRuleSelectionTitle,
                  filterSelection, this.selectableLayers[i].type,
                    this.symbolizerSelection);
                this.selectableLayers[i].sld.removeElseFilterRule(this.selectableLayers[i].layer.name, 0);
                this.currentFilter[this.selectableLayers[i].layer.name]=
                    this.selectableLayers[i].sld.getOGCFilter(
                                           this.selectableLayers[i].layer.name);
                sldString=format.write(this.selectableLayers[i].sld);
                this.selectableLayers[i].sld=null;

            }
            else{

                var sldObjDef=this.selectableLayers[i].sld;
                sldObjDef.removeAddedFilterToEachRule(
                OpenLayers.Filter.GeoSpatial.BBOX, OpenLayers.Filter.Logical.AND,
                this.selectableLayers[i].layer.name);
                sldObjDef.addFilterToEachRule(this.selectableLayers[i].layer.name,
                       OpenLayers.Filter.Logical.AND,filterSelection,
                       this.symbolizerSelection, this.selectableLayers[i].type);
                this.currentFilter[this.selectableLayers[i].layer.name]=
                  sldObjDef.getOGCFilter(this.selectableLayers[i].layer.name);
               sldString=format.write(sldObjDef);

            }
            this.selectionLayers[i] = new OpenLayers.Layer.WMS(
                    this.selectableLayers[i].layer.name+"_Selection",
                    this.selectableLayers[i].layer.url,
                      {layers: this.selectableLayers[i].layer.name,
                      transparent: "true", format: "image/png", SLD_BODY:sldString });
            this.map.addLayer(this.selectionLayers[i]);
            //setTimeout("map.removeLayer(newWMSLayer2)",8250);
      }
    },

    getSelectionFilter: function(layerName){

    return this.currentFilter[layerName];

    },

    getSelectionFilterEncoded: function(layerName){

    var format = new OpenLayers.Format.FILTERShort();

    return format.getShortFILTER(this.currentFilter[layerName]);

    },


     setCurrentFilter: function(layerName, sld){

       var i=0;
       var filter="";

       while(i<this.selectableLayers.length){

          if(this.selectableLayers[i].layer.name == layerName) {
             if(this.selectableLayers[i].sld)
              filter=this.selectableLayers[i].sld.getOGCFilter(layerName);
             break;
          }
          i++;
       }
      return filter;
    },

    removeSelectedLayer: function (){
        for(var i=0; i<this.selectionLayers.length; i++)
            this.map.removeLayer(this.selectionLayers[i]);
        this.selectionLayers=[];
        this.currentFilter=[];
    },

   setSelectableLayers: function(selectbaleLayers){

      this.selectableLayers=selectbaleLayers;
   },

   setSelectionLayers: function(selectionLayers){

      this.selectionLayers=selectionLayers;
   },

   setSimbolyzerSelection: function(symbolyzerSelection){

      this.symbolizerSelection=symbolyzerSelection;
   },

    CLASS_NAME: "OpenLayers.Control.SelectWMS"
});
