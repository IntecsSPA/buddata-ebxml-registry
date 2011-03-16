

/**
 * @requires OpenLayers/Format/XML.js
 * @requires OpenLayers.Filter.GeoSpatial (widget/SelectionTools/OpenLayers/lib/Filter/GeoSpatial.js)
 * @requires OpenLayers/Filter/FeatureId.js
 * @requires OpenLayers/Filter/Logical.js
 * @requires OpenLayers/Filter/Comparison.js
 */

/**
 * Class: OpenLayers.Format.FILTER
 * Read FILTER document. Create a new instance with the <OpenLayers.Format.FILTER>
 *     constructor.
 * 
 * Inherits from:
 *  - <OpenLayers.Format.XML>
 */
OpenLayers.Format.FILTER = OpenLayers.Class(OpenLayers.Format.XML, {
    
    /**
     * Property: namespaces
     * {Object} Mapping of namespace aliases to namespace URIs.
     */
    namespaces: {
        ogc: "http://www.opengis.net/ogc",
        gml: "http://www.opengis.net/gml"
    },
    
    /**
     * Property: defaultPrefix
     */
    defaultPrefix: "ogc",

    /**
     * Property: schemaLocation
     * {String} Schema location for a particular minor version.
     */
    schemaLocation: null,
    
    /**
     * Constructor: OpenLayers.Format.FILTER
     * Create a new parser for FILTER.
     *
     * Parameters:
     * options - {Object} An optional object whose properties will be set on
     *     this instance.
     */
    initialize: function(options) {
        OpenLayers.Format.XML.prototype.initialize.apply(this, [options]);
    },

    
   /**
     * Method: read
     *
     * Parameters:
     * data - {DOMElement} An OGC FILTER document element.
     *
     * Returns:
     * {Object} An object representing the OGC Filter Document.
     */
    read: function(data) {        
        var filterObj = {
                    fids: new Array(),
                    filters: new Array()
                  };
        this.readChildNodes(data, filterObj);
        return filterObj;
    }, 
    
    /**
     * Property: readers
     * Contains public functions, grouped by namespace prefix, that will
     *     be applied when a namespaced node is found matching the function
     *     name.  The function will be applied in the scope of this parser
     *     with two arguments: the node being read and a context object passed
     *     from the parent.
     */
    readers: {
       
        "ogc": {
            "Filter": function(node, filter) {
                var obj = {
                    fids: [],
                    filters: []
                };
                this.readChildNodes(node, obj);
                if(obj.fids.length > 0) {
                    filter.fids = new OpenLayers.Filter.FeatureId({
                        fids: obj.fids
                    });
                } else if(obj.filters.length > 0) {
		 			
                    filter.filters[0] = obj.filters[0];
                }
            },
            "FeatureId": function(node, obj) {
                var fid = node.getAttribute("fid");
                if(fid) {
                    obj.fids.push(fid);
                }
            },
            "And": function(node, obj) {
                var filter = new OpenLayers.Filter.Logical({
                    type: OpenLayers.Filter.Logical.AND
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "Or": function(node, obj) {
                var filter = new OpenLayers.Filter.Logical({
                    type: OpenLayers.Filter.Logical.OR
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "Not": function(node, obj) {
                var filter = new OpenLayers.Filter.Logical({
                    type: OpenLayers.Filter.Logical.NOT
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsEqualTo": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.EQUAL_TO
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsNotEqualTo": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.NOT_EQUAL_TO
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsLessThan": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.LESS_THAN
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsGreaterThan": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.GREATER_THAN
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsLessThanOrEqualTo": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.LESS_THAN_OR_EQUAL_TO
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsGreaterThanOrEqualTo": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
            "PropertyIsBetween": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.BETWEEN
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
	    "BBOX": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.GeoSpatial.BBOX
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },
	   "Disjoint": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.GeoSpatial.DISJOINT
                });
                this.readChildNodes(node, filter);
                obj.filters.push(filter);
            },		
            "PropertyIsLike": function(node, obj) {
                var filter = new OpenLayers.Filter.Comparison({
                    type: OpenLayers.Filter.Comparison.LIKE
                });
                this.readChildNodes(node, filter);
                var wildCard = node.getAttribute("wildCard");
                var singleChar = node.getAttribute("singleChar");
                var esc = node.getAttribute("escape");
                filter.value2regex(wildCard, singleChar, esc);
                obj.filters.push(filter);
            },
            "Literal": function(node, obj) {
                obj.value = this.getChildValue(node);
            },
            "PropertyName": function(node, filter) {
                filter.property = this.getChildValue(node);
            },
            "LowerBoundary": function(node, filter) {
                filter.lowerBoundary = this.readOgcExpression(node);
            },
            "UpperBoundary": function(node, filter) {
                filter.upperBoundary = this.readOgcExpression(node);
            }
        },
      "gml": {
          "Envelope": function(node, obj) {
                var srsName=node.getAttribute("srsName");
                if(srsName.indexOf('#') > -1){
                   var split=srsName.split('#');
                   obj.srs=split[1];
                }
                else{
                  obj.srs=srsName;  
                }
                    
                this.readChildNodes(node, obj);
                
            },
          "Box": function(node, obj) {
                var srsName=node.getAttribute("srsName");
                if(srsName.indexOf('#') > -1){
                   var split=srsName.split('#');
                   obj.srs=split[1];
                }
                else{
                  obj.srs=srsName;  
                }    
                this.readChildNodes(node, obj);
            },  
          "lowerCorner": function(node, obj) {
                obj.lowerCorner = this.setPointFromStringRappresentation
                                            (this.getChildValue(node));
            },
          "UpperCorner": function(node, obj) {
               obj.upperCorner = this.setPointFromStringRappresentation
                                            (this.getChildValue(node)); 
            },
          "coordinates": function(node, obj) {
               var corners=this.getChildValue(node).split(" ");
               obj.lowerCorner = this.setPointFromStringRappresentation
                                            (corners[0]);
               obj.upperCorner = this.setPointFromStringRappresentation
                                            (corners[1]);                              
            }  
            
        }
    },
    
    
    /**
     * Method: write
     *
     * Parameters:
     * filter - {Object} An object representing the OGC Filter.
     * type - {String} Output Type (Document,String) defualt String.
     * 
     * Returns:
     * {String} An OGC FILTER document string.
     */
    write: function(filter, type) {
        var filterDoc=this.writers.ogc.Filter.apply(this, [filter]);
        if(type){
           if(type=='Document')
             return filterDoc;
           else
             return OpenLayers.Format.XML.prototype.write.apply(this, [filterDoc]);  
        }
        else
          return OpenLayers.Format.XML.prototype.write.apply(this, [filterDoc]);
    },
    
    /**
     * Property: writers
     * As a compliment to the readers property, this structure contains public
     *     writing functions grouped by namespace alias and named like the
     *     node names they produce.
     */
    writers: {
        "ogc": {
            "Filter": function(filter) {
                var node = this.createElementNSPlus("ogc:Filter");
                var sub = filter.CLASS_NAME.split(".").pop();
                if(sub == "FeatureId") {
                    for(var i=0; i<filter.fids.length; ++i) {
                        this.writeNode(node, "FeatureId", filter.fids[i]);
                    }
                } else {
                    this.writeNode(node, this.getFilterType(filter), filter);
                }
                return node;
            },
            "FeatureId": function(fid) {
                return this.createElementNSPlus("ogc:FeatureId", {
                    attributes: {fid: fid}
                });
            },
            "And": function(filter) {
                var node = this.createElementNSPlus("ogc:And");
                var childFilter;
                for(var i=0; i<filter.filters.length; ++i) {
                    childFilter = filter.filters[i];
                    this.writeNode(
                        node, this.getFilterType(childFilter), childFilter
                    );
                }
                return node;
            },
            "Or": function(filter) {
                var node = this.createElementNSPlus("ogc:Or");
                var childFilter;
                for(var i=0; i<filter.filters.length; ++i) {
                    childFilter = filter.filters[i];
                    this.writeNode(
                        node, this.getFilterType(childFilter), childFilter
                    );
                }
                return node;
            },
            "Not": function(filter) {
                var node = this.createElementNSPlus("ogc:Not");
                var childFilter = filter.filters[0];
                this.writeNode(
                    node, this.getFilterType(childFilter), childFilter
                );
                return node;
            },
            "PropertyIsEqualTo": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsEqualTo");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "Literal", filter.value);
                return node;
            },
            "PropertyIsNotEqualTo": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsNotEqualTo");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "Literal", filter.value);
                return node;
            },
            "PropertyIsLessThan": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsLessThan");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "Literal", filter.value);                
                return node;
            },
            "PropertyIsGreaterThan": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsGreaterThan");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "Literal", filter.value);
                return node;
            },
            "PropertyIsLessThanOrEqualTo": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsLessThanOrEqualTo");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "Literal", filter.value);
                return node;
            },
            "PropertyIsGreaterThanOrEqualTo": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsGreaterThanOrEqualTo");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "Literal", filter.value);
                return node;
            },
            "PropertyIsBetween": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsBetween");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                this.writeNode(node, "LowerBoundary", filter);
                this.writeNode(node, "UpperBoundary", filter);
                return node;
            },
            "PropertyIsLike": function(filter) {
                var node = this.createElementNSPlus("ogc:PropertyIsLike", {
                    attributes: {
                        wildCard: "*", singleChar: ".", escape: "!"
                    }
                });
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                // convert regex string to ogc string
                this.writeNode(node, "Literal", filter.regex2value());
                return node;
            },
            "BBOX": function(filter) {
                var node = this.createElementNSPlus("ogc:BBOX");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                // convert regex string to ogc string
                this.writeNode(node, "gml:Box", filter);
                return node;
            },
            "Disjoint": function(filter) {
                var node = this.createElementNSPlus("ogc:Disjoint");
                // no ogc:expression handling for now
                this.writeNode(node, "PropertyName", filter);
                // convert regex string to ogc string
                this.writeNode(node, "gml:Envelope", filter);
                return node;
            },
            "PropertyName": function(filter) {
                // no ogc:expression handling for now
                return this.createElementNSPlus("ogc:PropertyName", {
                    value: filter.property
                });
            },
            "Literal": function(value) {
                // no ogc:expression handling for now
                return this.createElementNSPlus("ogc:Literal", {
                    value: value
                });
            },
            "LowerBoundary": function(filter) {
                // no ogc:expression handling for now
                var node = this.createElementNSPlus("ogc:LowerBoundary");
                this.writeNode(node, "Literal", filter.lowerBoundary);
                return node;
            },
            "UpperBoundary": function(filter) {
                // no ogc:expression handling for now
                var node = this.createElementNSPlus("ogc:UpperBoundary");
                this.writeNode(node, "Literal", filter.upperBoundary);
                return node;
            }
        },
        "gml": {
            "Envelope": function(filter) {
                var node = this.createElementNSPlus("gml:Envelope",
                    {attributes: {
                        "srsName": "http://www.opengis.net/gml/srs/epsg.xml#"+
                                    filter.srs  
                    }});
                this.writeNode(node, "lowerCorner", filter.lowerCorner);
                this.writeNode(node, "upperCorner", filter.upperCorner);
                return node;
            },
            "Box": function(filter) {
                var node = this.createElementNSPlus("gml:Box",
                    {attributes: {
                        "srsName": "http://www.opengis.net/gml/srs/epsg.xml#"+
                                    filter.srs  
                    }});
                this.writeNode(node, "coordinates", filter);
                return node;
            },
            "lowerCorner": function(lowerCorner) {
                var node = this.createElementNSPlus("gml:lowerCorner",{
                    value: this.getPointRappresentation(lowerCorner)
                });
                return node;
            },
            "upperCorner": function(upperCorner) {
                var node = this.createElementNSPlus("gml:upperCorner",{
                    value: this.getPointRappresentation(upperCorner)
                });
                return node;
            },
            "coordinates": function(filter) {
                var node = this.createElementNSPlus("gml:coordinates",{
                    attributes: {
                        "decimal": ".",
                        "cs":",",
                        "ts":" "
                    },
                    value: this.getCoordinateRappresentation(filter.lowerCorner,filter.upperCorner)
                });
                
                return node;
            }
            
        }     
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
    
    /**
     * Method: getFilterType
     */
    getFilterType: function(filter) {
        var filterType = this.filterMap[filter.type];
        if(!filterType) {
            throw "SLD writing not supported for rule type: " + filter.type;
        }
        return filterType;
    },
    
    /**
     * Property: filterMap
     * {Object} Contains a member for each filter type.  Values are node names
     *     for corresponding OGC Filter child elements.
     */
    filterMap: {
        "&&": "And",
        "||": "Or",
        "!": "Not",
        "==": "PropertyIsEqualTo",
        "!=": "PropertyIsNotEqualTo",
        "<": "PropertyIsLessThan",
        ">": "PropertyIsGreaterThan",
        "<=": "PropertyIsLessThanOrEqualTo",
        ">=": "PropertyIsGreaterThanOrEqualTo",
        "..": "PropertyIsBetween",
        "~": "PropertyIsLike",
        "[ ]": "BBOX",
        "[ [": "Disjoint"
    },
    
    
     /**
     * Method: setPointFromStringRappresentation
     */
    setPointFromStringRappresentation: function(pointString){
        var coor=pointString.split(" ");
        var pointObj= {
            x: coor[0],
            y: coor[1]
            
        };
        return (pointObj);
    },
    
    /**
     * Method: readOgcExpression
     * Limited support for OGC expressions.
     *
     * Parameters:
     * node - {DOMElement} A DOM element that contains an ogc:expression.
     *
     * Returns:
     * {String} A value to be used in a symbolizer.
     */
    readOgcExpression: function(node) {
        var obj = {};
        this.readChildNodes(node, obj);
        var value = obj.value;
        if(!value) {
            value = this.getChildValue(node);
        }
        return value;
    },

 /**
     * Methods below this point are of general use for versioned XML parsers.
     * These are candidates for an abstract class.
     */
    
    /**
     * Method: getNamespacePrefix
     * Get the namespace prefix for a given uri from the <namespaces> object.
     *
     * Returns:
     * {String} A namespace prefix or null if none found.
     */
    getNamespacePrefix: function(uri) {
        var prefix = null;
        if(uri == null) {
            prefix = this.namespaces[this.defaultPrefix];
        } else {
            var gotPrefix = false;
            for(prefix in this.namespaces) {
                if(this.namespaces[prefix] == uri) {
                    gotPrefix = true;
                    break;
                }
            }
            if(!gotPrefix) {
                prefix = null;
            }
        }
        return prefix;
    },


    /**
     * Method: readChildNodes
     */
    readChildNodes: function(node, obj) {
        var children = node.childNodes;
        var child, group, reader, prefix, local;
        for(var i=0; i<children.length; ++i) {
            child = children[i];
            if(child.nodeType == 1) {
                prefix = this.getNamespacePrefix(child.namespaceURI);
                local = child.nodeName.split(":").pop();
                group = this.readers[prefix];
                if(group) {
                    reader = group[local];
                    if(reader) {
                        reader.apply(this, [child, obj]);
                    }
                }
            }
        }
    },
    
     /**
     * Method: writeNode
     * Shorthand for applying one of the named writers and appending the
     *     results to a node.  If a qualified name is not provided for the
     *     second argument (and a local name is used instead), the namespace
     *     of the parent node will be assumed.
     *
     * Parameters:
     * parent - {DOMElement} Result will be appended to this node.
     * name - {String} The name of a node to generate.  If a qualified name
     *     (e.g. "pre:Name") is used, the namespace prefix is assumed to be
     *     in the <writers> group.  If a local name is used (e.g. "Name") then
     *     the namespace of the parent is assumed.
     * obj - {Object} Structure containing data for the writer.
     *
     * Returns:
     * {DOMElement} The child node.
     */
    writeNode: function(parent, name, obj) {
        var prefix, local;
        var split = name.indexOf(":");
        if(split > 0) {
            prefix = name.substring(0, split);
            local = name.substring(split + 1);
        } else {
            prefix = this.getNamespacePrefix(parent.namespaceURI);
            local = name;
        }

        var child = this.writers[prefix][local].apply(this, [obj]);
        parent.appendChild(child);
        return child;
    },
    
    /**
     * Method: createElementNSPlus
     * Shorthand for creating namespaced elements with optional attributes and
     *     child text nodes.
     *
     * Parameters:
     * name - {String} The qualified node name.
     * options - {Object} Optional object for node configuration.
     *
     * Returns:
     * {Element} An element node.
     */
    createElementNSPlus: function(name, options) {
        options = options || {};
        var loc = name.indexOf(":");
        // order of prefix preference
        // 1. in the uri option
        // 2. in the prefix option
        // 3. in the qualified name
        // 4. from the defaultPrefix
        var uri = options.uri || this.namespaces[options.prefix];
        if(!uri) {
            loc = name.indexOf(":");
            uri = this.namespaces[name.substring(0, loc)];
        }
        if(!uri) {
            uri = this.namespaces[this.defaultPrefix];
        }
        var node = this.createElementNS(uri, name);
        if(options.attributes) {
            this.setAttributes(node, options.attributes);
        }
        if(options.value) {
            node.appendChild(this.createTextNode(options.value));
        }
        return node;
    },
    
    /**
     * Method: setAttributes
     * Set multiple attributes given key value pairs from an object.
     *
     * Parameters:
     * node - {Element} An element node.
     * obj - {Object || Array} An object whose properties represent attribute
     *     names and values represent attribute values.  If an attribute name
     *     is a qualified name ("prefix:local"), the prefix will be looked up
     *     in the parsers {namespaces} object.  If the prefix is found,
     *     setAttributeNS will be used instead of setAttribute.
     */
    setAttributes: function(node, obj) {
        var value, loc, alias, uri;
        for(var name in obj) {
            value = obj[name].toString();
            // check for qualified attribute name ("prefix:local")
            uri = this.namespaces[name.substring(0, name.indexOf(":"))] || null;
            this.setAttributeNS(node, uri, name, value);
        }
    },


    CLASS_NAME: "OpenLayers.Format.FILTER" 
});
