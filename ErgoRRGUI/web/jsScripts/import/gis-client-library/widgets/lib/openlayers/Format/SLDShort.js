

/**
 * @requires OpenLayers/Format/XML.js
 * @requires OpenLayers/Style.js
 * @requires OpenLayers/Filter/FeatureId.js
 * @requires OpenLayers/Filter/Logical.js
 * @requires OpenLayers/Filter/Comparison.js
 */

/**
 * Class: OpenLayers.Format.SLDShort
 * Read/Write SLD encoded. Create a new instance with the <OpenLayers.Format.SLDShort>
 *     constructor.
 * 
 * Inherits from:
 *  - <OpenLayers.Format.XML>
 */
OpenLayers.Format.SLDShort = OpenLayers.Class(OpenLayers.Format.XML, {
    
    /**
     * APIProperty: defaultVersion
     * {String} Version number to assume if none found.  Default is "1.0.0".
     */
    defaultVersion: "1.0.0",
    
    /**
     * APIProperty: version
     * {String} Specify a version string if one is known.
     */
    version: null,
    
    /**
     * Property: parser
     * {Object} Instance of the versioned parser.  Cached for multiple read and
     *     write calls of the same version.
     */
    parser: null,
    
    sldShortRappresetation:['StyledLayerDescriptor',
                            'NamedLayer',
                            'NamedStyle',
                            'Name',
                            'UserStyle',
                            'IsDefault',
                            'FeatureTypeStyle',
                            'Rule',
                            'Title',
                            'Abstract',
                            'ElseFilter',
                            'MinScaleDenominator',
                            'MaxScaleDenominator',
                            'LineSymbolizer',
                            'PolygonSymbolizer',
                            'PointSymbolizer',
                            'Stroke',
                            'Fill',
                            'CssParameter',
                            'Graphic',
                            'ExternalGraphic',
                            'Mark',
                            'WellKnownName',
                            'Opacity',
                            'Size',
                            'Rotation',
                            'OnlineResource',
                            'Format',
                            'Filter',
                            'FeatureId',
                            'And',
                            'Or',
                            'Not',
                            'PropertyIsEqualTo',
                            'PropertyIsNotEqualTo',
                            'PropertyIsLessThanOrEqualTo',
                            'PropertyIsLessThan',
                            'PropertyIsGreaterThanOrEqualTo',
                            'PropertyIsGreaterThan',
                            'PropertyIsBetween',
                            'PropertyIsLike',
                            'Literal',
                            'PropertyName',
                            'LowerBoundary',
                            'UpperBoundary',
                            'gml:Box',
                            'gml:lowerCorner',
                            'gml:upperCorner',
                            'gml:coordinates',
                            'gml:Envelope'],
 

    /**
     * Constructor: OpenLayers.Format.SLD
     * Create a new parser for SLD.
     *
     * Parameters:
     * options - {Object} An optional object whose properties will be set on
     *     this instance.
     */
    initialize: function(options) {
        OpenLayers.Format.XML.prototype.initialize.apply(this, [options]);
    },

    /**
     * APIMethod: write
     * Write a SLD document given a list of styles.
     *
     * Parameters:
     * sld - {Object} An object representing the SLD.
     * options - {Object} Optional configuration object.
     *
     * Returns:
     * {String} An SLD document string.
     */
    write: function(sld, options) {
        var version = (options && options.version) ||
                      this.version || this.defaultVersion;
        if(!this.parser || this.parser.VERSION != version) {
            var format = OpenLayers.Format.SLD[
                "v" + version.replace(/\./g, "_")
            ];
            if(!format) {
                throw "Can't find a SLD parser for version " +
                      version;
            }
            this.parser = new format(this.options);
        }
        var root = this.parser.write(sld);
        var sldString=OpenLayers.Format.XML.prototype.write.apply(this, [root]);
        
        return this.getShortSLD(sldString);
    },
    
    /**
     * APIMethod: read
     * Read and SLD doc and return an object representing the SLD.
     *
     * Parameters:
     * data - {String | DOMElement} Data to read.
     *
     * Returns:
     * {Object} An object representing the SLD.
     */
    read: function(data) {
        
        if(typeof data == "string") {
            data = OpenLayers.Format.XML.prototype.read.apply(this, [data]);
        }
        var root = data.documentElement;
        var version = this.version;
        if(!version) {
            version = root.getAttribute("version");
            if(!version) {
                version = this.defaultVersion;
            }
        }
        if(!this.parser || this.parser.VERSION != version) {
            var format = OpenLayers.Format.SLD[
                "v" + version.replace(/\./g, "_")
            ];
            if(!format) {
                throw "Can't find a SLD parser for version " +
                      version;
            }
            this.parser = new format(this.options);
        }
        var sld = this.parser.read(data);
        return sld;
    },
   
   getShortSLD: function(sld){
      var i;
      var shortSLD=sld, temp;
      shortSLD=replaceAll(shortSLD, 'ogc:', '');   
      shortSLD=replaceAll(shortSLD, ':ogc', ''); 
      shortSLD=replaceAll(shortSLD, '#', '%23');
      for(i=0; i<this.sldShortRappresetation.length;i++){
         temp=replaceAll(shortSLD,'<'+this.sldShortRappresetation[i], '<'+i);
         shortSLD=replaceAll(temp,'</'+this.sldShortRappresetation[i], '</'+i);
      }
       
     return shortSLD;  
   },
   
   
        /**
     * Method: getPointRappresentation
     */
    getPointRappresentation: function(point){
        return (point.x +" "+ point.y);
    },
    
     /**
     * Method: getCoordinateRappresentation
     */
    getCoordinateRappresentation: function(lowerPoint, upperPoint){
        return (lowerPoint.x +","+ lowerPoint.y+" "+upperPoint.x+","+upperPoint.y);
    },
    
    CLASS_NAME: "OpenLayers.Format.SLDShort" 
});
