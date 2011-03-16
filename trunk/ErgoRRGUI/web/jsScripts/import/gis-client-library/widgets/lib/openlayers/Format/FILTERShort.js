/**
 * @requires OpenLayers/Format/XML.js
 * @requires OpenLayers/Style.js
 * @requires OpenLayers/Filter/FeatureId.js
 * @requires OpenLayers/Filter/Logical.js
 * @requires OpenLayers/Filter/Comparison.js
 */

/**
 * Class: OpenLayers.Format.FILTERShort
 * Read/Write SLD encoded. Create a new instance with the <OpenLayers.Format.SLDShort>
 *     constructor.
 * 
 * Inherits from:
 *  - <OpenLayers.Format.XML>
 */
OpenLayers.Format.FILTERShort = OpenLayers.Class(OpenLayers.Format.XML, {
    
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
    
   
    filterShortRappresetation:[
                            'Filter',
                            'FeatureId',
                            'And',
                            'Or',
                            'Not',
                            'BBOX',
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
     * Constructor: OpenLayers.Format.FILTERShort
     * Create a new parser for FILTERShort.
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
     * Write a FILTER document encoded.
     *
     * Parameters:
     * filter - {Object} An object representing the FILTER.
     * options - {Object} Optional configuration object.
     *
     * Returns:
     * {String} An FILTER document string encoded.
     */
    write: function(filter, options) {
        var version = (options && options.version) ||
                      this.version || this.defaultVersion;
        if(!this.parser || this.parser.VERSION != version) {
            var format = OpenLayers.Format.FILTER;
            if(!format) {
                throw "Can't find a FILTER parser for version " +
                      version;
            }
            this.parser = new format(this.options);
        }
        var root = this.parser.write(filter);
        var filterString=OpenLayers.Format.XML.prototype.write.apply(this, [root]);
        
        return this.getShortFILTER(filterString);
    },
    
    /**
     * APIMethod: read
     * Read and FILTER ecoded doc and return an object representing the SLD.
     *
     * Parameters:
     * data - {String | DOMElement} Data to read.
     *
     * Returns:
     * {Object} An object representing the SLD.
     */
  /*  read: function(data) {
        
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
    },*/
   
   getShortFILTER: function(filter){
      var shortFILTER=filter, temp; 
      if(filter){ 
        var i;   
        shortFILTER=replaceAll(shortFILTER, 'ogc:', '');   
        shortFILTER=replaceAll(shortFILTER, ':ogc', ''); 
        shortFILTER=replaceAll(shortFILTER, '#', '%23');
        for(i=0; i<this.filterShortRappresetation.length;i++){
          temp=replaceAll(shortFILTER,'<'+this.filterShortRappresetation[i], '<'+i);
          shortFILTER=replaceAll(temp,'</'+this.filterShortRappresetation[i], '</'+i);
        }
      }
    
     return shortFILTER;  
   },

    
    CLASS_NAME: "OpenLayers.Format.FILTERShort" 
});
