//sdfsdfsdfsdfsdfsdf



/**
 * Class: OpenLayers.Format.XMLKeyValue
 * 
 *
 * Inherits from:
 *  - <OpenLayers.Format.XML>
 */

OpenLayers.Format.XMLKeyValue = OpenLayers.Class(OpenLayers.Format.XML, {
    
    /**
     * Property: namespaces
     * {Object} Mapping of namespace aliases to namespace URIs.
     */
    namespaces: {
        gis: "http://gisclient.pisa.intecs.it"
    },
    
    /**
     * Property: defaultPrefix
     */
    defaultPrefix: "gis",

    /**
     * Property: schemaLocation
     * {String} Schema location for a particular minor version.
     */
    schemaLocation: null,


    /**
     * Constructor: OpenLayers.Format.XMLKeyValue
     * 
     *
     * Parameters:
     * options - {Object} An optional object whose properties will be set on
     *     this instance.
     */
    initialize: function(options) {
        OpenLayers.Format.XML.prototype.initialize.apply(this, [options]);
    },

    /**
     * Method: write
     *
     * Parameters:
     * keyValue - {array} An array representing the XMLKeyValue document.
     *
     * Returns:
     * {DOMElement} The root of an XMLKeyValue document.
     */
    write: function(keyValue,options) {
        var returnType = (options && options.returnType) ||
                      options.returnType || "DOMDocument";
        var namespace=options.namespace;        
        if(namespace == false){
           this.defaultPrefix="";
        }  
        var document=this.writers.KeyValues.apply(this, [keyValue]);    
         
        if(returnType == "String")    {
        //    var serializer = new XMLSerializer();
          return OpenLayers.Format.XML.prototype.write.apply(this, [document]);
          //  return serializer.serializeToString(document);  
         }   
        else
          return document;
    },
    
    /**
     * Property: writers
     * As a compliment to the readers property, this structure contains public
     *     writing functions grouped by namespace alias and named like the
     *     node names they produce.
     */
    writers: {

            "KeyValues": function(keyValue) {
                    var root = this.createElementNSPlus(
                            "KeyValues"
                           /*/ {attributes: {
                                "version": this.VERSION,
                                 "xsi:schemaLocation": this.schemaLocation
                            }}*/
                    );
                    var tempform;
                    for(var i=0;i<keyValue.confValues.length;i++){
                        tempform=keyValue.confValues[i];

                        for(var u=0;u<tempform.length;u++)
                            switch(tempform[u].type) {
                                case "text":
                                     // alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){  
                                       
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break; 
                                case "numeric":
                                     // alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){  
                                       
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break;  
                                case "textarea":
                                      if(keyValue.formValues[tempform[u].id]){  
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break;
                                case "editarea":
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }
                                      break;
                                case "checkbox":
                                      //alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){  
                                       
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break;
                                case "radiogroup":
                                  
                                      //alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){

                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }
                                      break;
                                case "combo":
                                      //alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break;
                                case "bbox":
                                      //alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break;    
                                case "date":
                                      if(keyValue.formValues[tempform[u].id]){  
                                        this.writeNode(root, tempform[u].id, keyValue.formValues[tempform[u].id]);
                                      }  
                                      break;
                                case "numericRange":
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id+"MinValue", keyValue.formValues[tempform[u].id].minValue);
                                        this.writeNode(root, tempform[u].id+"MaxValue", keyValue.formValues[tempform[u].id].maxValue);
                                      }
                                      break;
                                case "rangedate":
                                      //alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id+"StartDate", keyValue.formValues[tempform[u].id].startDate);
                                        this.writeNode(root, tempform[u].id+"EndDate", keyValue.formValues[tempform[u].id].endDate);
                                      }  
                                      break;
                                case "file":
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id+"FileName", keyValue.formValues[tempform[u].id].fileName);
                                        if(keyValue.formValues[tempform[u].id].uploadID)
                                            this.writeNode(root, tempform[u].id+"UploadID", keyValue.formValues[tempform[u].id].uploadID);
                                      }
                                      break;
                                case "rangetime":
                                      //alert(keyValue.formValues[tempform[u].id]);
                                      if(keyValue.formValues[tempform[u].id]){
                                        this.writeNode(root, tempform[u].id+"StartTime", keyValue.formValues[tempform[u].id].startTime);
                                        this.writeNode(root, tempform[u].id+"EndTime", keyValue.formValues[tempform[u].id].endTime);
                                      }  
                                      break;
                        } 
            }
            
                
           return root; 
        },    
        "node": function(keyValueObj){
           var node = this.createElementNSPlus(keyValueObj.key,{
                    value: keyValueObj.value
                });
           return node;
            
        }
     
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
        var prefix, local,child;
        var keyValueNode;
        var split = name.indexOf(":");
        if(split > 0) {
            prefix = name.substring(0, split);
            local = name.substring(split + 1);
             keyValueNode={
                  key: local,
                  value: obj
             };
            child = this.writers[prefix]["node"].apply(this, [keyValueNode])
        } else {
            keyValueNode={
                key: name,
                value: obj
             };
            child = this.writers["node"].apply(this, [keyValueNode]);

        }

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
        var node;
        if(!uri)
          node = this.createElementNS("", name);  
        else    
          node = this.createElementNS(uri, name);
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

    CLASS_NAME: "OpenLayers.Format.XMLKeyValue" 

});



