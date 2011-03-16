/**
 * @requires OpenLayers/Filter.js
 */

/**
 * Class: OpenLayers.Filter.GeoSpatial
 * This class represents ogc:BBOX, ogc:Disjoint rules.
 * 
 * Inherits from
 * - <OpenLayers.Filter>
 */
OpenLayers.Filter.GeoSpatial  = OpenLayers.Class(OpenLayers.Filter, {

    /**
     * APIProperty: type
     * {String} type: type of the geospatial filter operator. This is one of
     * - OpenLayers.Filter.GeoSpatial.BBOX                 = "[ ]";
     * - OpenLayers.Filter.GeoSpatial.DISJOINT             = "[ [";
     */
    type: null,
    
     /**
     * APIProperty: property
     * {String}
     * name of the geometry property
     */
    property: null,
    
    
    /**
     * APIProperty: srs
     * {String}
     * codes to define the CRS or SRS (Spatial Reference Systems).
     * Default value is: 4326 (WGS 84)
     */
    srs: "4326",
    
    /**
     * APIProperty: lowerCorner
     * {Object}
     * lower corner for envelope definition (x:long, y:lat). 
     */
    lowerCorner: {
                  x: 0,
                  y: 0
    },
    
    /**
     * APIProperty: upperCorner
     * {Object}
     * upper corner for envelope definition (x:long, y:lat). 
     */
    upperCorner: {
                  x: 0,
                  y: 0
    },
    
    
    /** 
     * Constructor: OpenLayers.Filter.GeoSpatial
     * Creates a geospatial filter (BBOX, Disjoint).
     *
     * Parameters:
     * options - {Object} An optional object with properties to set on the
     *     filter.
     * 
     * Returns:
     * {<OpenLayers.Filter.GeoSpatial>}
     */
    initialize: function(options) {
        OpenLayers.Filter.prototype.initialize.apply(this, [options]);
    },
    

    /**
     * APIMethod: evaluate (Not yet Implemented)
     * Evaluates this filter in a specific context.  Should be implemented by
     *     subclasses.
     * 
     * Parameters:
     * context - {Object} Context to use in evaluating the filter.
     * 
     * Returns:
     * {Boolean} The filter applies.
     */
    evaluate: function(context) {
        alert("Evaluate Filter.GeoSpatial operator: Not yet Implemented");
    },
    
    CLASS_NAME: "OpenLayers.Filter.GeoSpatial"
});


OpenLayers.Filter.GeoSpatial.BBOX      = "[ ]";
OpenLayers.Filter.GeoSpatial.DISJOINT  = "[ [";

