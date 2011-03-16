/* Copyright (c) 2006-2008 MetaCarta, Inc., published under the Clear BSD
 * license.  See http://svn.openlayers.org/trunk/openlayers/license.txt for the
 * full text of the license. */

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


// Link extjs with OpenLayers
var currentAOI="";
var changeAOIFunction= function (){
    refreshCurrentFormTab();
}
//-----------------------------------------



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
     * {OpenLayers.Marker.Box} Area of Interest
     */
    aoi: null,


     /**
     * Property: layer
     * {<OpenLayers.Layer.WMS>}
     */
    layer: null,

    
    /**
     * Method: draw
     */    
    draw: function() {
        this.handler = new OpenLayers.Handler.Box( this,
                            {done: this.zoomBox}, {keyMask: this.keyMask} );
    },
    

    /**
     * Method: zoomBox
     *
     * Parameters:
     * position - {<OpenLayers.Bounds>} or {<OpenLayers.Pixel>}
     */
    zoomBox: function (position) {
        if(this.aoi!=null){       
          this.boxes.removeMarker(this.aoi);
        }
           // this.map.removeLayer(oldBox,null);
        //this.map.removeLayer("boxes");
        if (position instanceof OpenLayers.Bounds) {
            if (!this.out) {
                var minXY = this.map.getLonLatFromPixel(
                            new OpenLayers.Pixel(position.left, position.bottom));
                var maxXY = this.map.getLonLatFromPixel(
                            new OpenLayers.Pixel(position.right, position.top));
                var bounds = new OpenLayers.Bounds(minXY.lon, minXY.lat,
                                               maxXY.lon, maxXY.lat);
                currentAOI= minXY.lon+','+minXY.lat+','+
                                 maxXY.lon+','+maxXY.lat;   
                
                var newWMSLayer = new OpenLayers.Layer.WMS( "Ocean Profiles",
                    "http://localhost:8021/geoserver/wms",
                    {layers: "nurc:T_OPROF_NEW_VIEW",
                     transparent: "true", format: "image/png", SLD:"http://localhost:8023/TOOLBOX/sldTest.xml" });

              //this.map.removeLayer(this.layer);
                map.addLayers([newWMSLayer]);
                
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
                var bounds = new OpenLayers.Bounds(xmin, ymin, xmax, ymax);
                currentAOI=xmin+','+ymin+','+xmax+','+ymax;
            }
            //this.map.zoomToExtent(bounds);
           if(this.boxes == null) {
              this.boxes = new OpenLayers.Layer.Boxes("Area of Interest");
              this.aoi = new OpenLayers.Marker.Box(bounds);
              this.boxes.addMarker(this.aoi);
              this.map.addLayer(this.boxes);
              if(this.onChangeAOI!= null)
                 this.onChangeAOI(); 
           }   
           else
           {    
            this.aoi = new OpenLayers.Marker.Box(bounds);
            this.boxes.addMarker(this.aoi);
            this.boxes.redraw();
            if(this.onChangeAOI!= null)
                 this.onChangeAOI();
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
           
     // alert(currentAOI);
    },

    CLASS_NAME: "OpenLayers.Control.SelectWMS"
});
