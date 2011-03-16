
/**
 * Class: XMLKeyValue
 * 
 *
 * Author: Andrea Marongiu
 *  
 */

XMLKeyValue =  function(){
    return({
    /**
     * Property: xmldom
     * {XMLDom} If this browser uses ActiveX, this will be set to a XMLDOM
     *     object.  It is not intended to be a browser sniffing property.
     *     Instead, the xmldom property is used instead of <code>document<end>
     *     where namespaced node creation methods are not supported. In all
     *     other browsers, this remains null.
     */
    xmldom: null,
    
    
    
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
     * APIMethod: createElementNS
     * Create a new element with namespace.  This node can be appended to
     *     another node with the standard node.appendChild method.  For
     *     cross-browser support, this method must be used instead of
     *     document.createElementNS.
     *
     * Parameters:
     * uri - {String} Namespace URI for the element.
     * name - {String} The qualified name of the element (prefix:localname).
     * 
     * Returns:
     * {Element} A DOM element with namespace.
     */
    createElementNS: function(uri, name) {
        var element;
        if(this.xmldom) {
            if(typeof uri == "string") {
                element = this.xmldom.createNode(1, name, uri);
            } else {
                element = this.xmldom.createNode(1, name, "");
            }
        } else {
            element = document.createElementNS(uri, name);
        }
        return element;
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
     * APIMethod: createTextNode
     * Create a text node.  This node can be appended to another node with
     *     the standard node.appendChild method.  For cross-browser support,
     *     this method must be used instead of document.createTextNode.
     * 
     * Parameters:
     * text - {String} The text of the node.
     * 
     * Returns: 
     * {DOMElement} A DOM text node.
     */
    createTextNode: function(text) {
        var node;
        if(this.xmldom) {
            node = this.xmldom.createTextNode(text);
        } else {
            node = document.createTextNode(text);
        }
        return node;
    },
    
    
    /**
     * APIMethod: getElementsByTagNameNS
     * Get a list of elements on a node given the namespace URI and local name.
     *     To return all nodes in a given namespace, use '*' for the name
     *     argument.  To return all nodes of a given (local) name, regardless
     *     of namespace, use '*' for the uri argument.
     * 
     * Parameters:
     * node - {Element} Node on which to search for other nodes.
     * uri - {String} Namespace URI.
     * name - {String} Local name of the tag (without the prefix).
     * 
     * Returns:
     * {NodeList} A node list or array of elements.
     */
    getElementsByTagNameNS: function(node, uri, name) {
        var elements = [];
        if(node.getElementsByTagNameNS) {
            elements = node.getElementsByTagNameNS(uri, name);
        } else {
            // brute force method
            var allNodes = node.getElementsByTagName("*");
            var potentialNode, fullName;
            for(var i=0; i<allNodes.length; ++i) {
                potentialNode = allNodes[i];
                fullName = (potentialNode.prefix) ?
                           (potentialNode.prefix + ":" + name) : name;
                if((name == "*") || (fullName == potentialNode.nodeName)) {
                    if((uri == "*") || (uri == potentialNode.namespaceURI)) {
                        elements.push(potentialNode);
                    }
                }
            }
        }
        return elements;
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
    
    /**
     * APIMethod: getAttributeNodeNS
     * Get an attribute node given the namespace URI and local name.
     * 
     * Parameters:
     * node - {Element} Node on which to search for attribute nodes.
     * uri - {String} Namespace URI.
     * name - {String} Local name of the attribute (without the prefix).
     * 
     * Returns:
     * {DOMElement} An attribute node or null if none found.
     */
    getAttributeNodeNS: function(node, uri, name) {
        var attributeNode = null;
        if(node.getAttributeNodeNS) {
            attributeNode = node.getAttributeNodeNS(uri, name);
        } else {
            var attributes = node.attributes;
            var potentialNode, fullName;
            for(var i=0; i<attributes.length; ++i) {
                potentialNode = attributes[i];
                if(potentialNode.namespaceURI == uri) {
                    fullName = (potentialNode.prefix) ?
                               (potentialNode.prefix + ":" + name) : name;
                    if(fullName == potentialNode.nodeName) {
                        attributeNode = potentialNode;
                        break;
                    }
                }
            }
        }
        return attributeNode;
    },
    
    
    /**
     * APIMethod: getAttributeNS
     * Get an attribute value given the namespace URI and local name.
     * 
     * Parameters:
     * node - {Element} Node on which to search for an attribute.
     * uri - {String} Namespace URI.
     * name - {String} Local name of the attribute (without the prefix).
     * 
     * Returns:
     * {String} An attribute value or and empty string if none found.
     */
    getAttributeNS: function(node, uri, name) {
        var attributeValue = "";
        if(node.getAttributeNS) {
            attributeValue = node.getAttributeNS(uri, name) || "";
        } else {
            var attributeNode = this.getAttributeNodeNS(node, uri, name);
            if(attributeNode) {
                attributeValue = attributeNode.nodeValue;
            }
        }
        return attributeValue;
    },
    
    /**
     * APIMethod: getChildValue
     * Get the value of the first child node if it exists, or return an
     *     optional default string.  Returns an empty string if no first child
     *     exists and no default value is supplied.
     *
     * Parameters:
     * node - {DOMElement} The element used to look for a first child value.
     * def - {String} Optional string to return in the event that no
     *     first child value exists.
     *
     * Returns:
     * {String} The value of the first child of the given node.
     */
    getChildValue: function(node, def) {
        var value;
        try {
            value = node.firstChild.nodeValue;
        } catch(e) {
            value = (def != undefined) ? def : "";
        }
        return value;
    },

    /**
     * APIMethod: concatChildValues
     * Concatenate the value of all child nodes if any exist, or return an
     *     optional default string.  Returns an empty string if no children
     *     exist and no default value is supplied.  Not optimized for large
     *     numbers of child nodes.
     *
     * Parameters:
     * node - {DOMElement} The element used to look for child values.
     * def - {String} Optional string to return in the event that no
     *     child exist.
     *
     * Returns:
     * {String} The concatenated value of all child nodes of the given node.
     */
    concatChildValues: function(node, def) {
        var value = "";
        var child = node.firstChild;
        var childValue;
        while(child) {
            childValue = child.nodeValue;
            if(childValue) {
                value += childValue;
            }
            child = child.nextSibling;
        }
        if(value == "" && def != undefined) {
            value = def;
        }
        return value;
    },

    /**
     * APIMethod: hasAttributeNS
     * Determine whether a node has a particular attribute matching the given
     *     name and namespace.
     * 
     * Parameters:
     * node - {Element} Node on which to search for an attribute.
     * uri - {String} Namespace URI.
     * name - {String} Local name of the attribute (without the prefix).
     * 
     * Returns:
     * {Boolean} The node has an attribute matching the name and namespace.
     */
    hasAttributeNS: function(node, uri, name) {
        var found = false;
        if(node.hasAttributeNS) {
            found = node.hasAttributeNS(uri, name);
        } else {
            found = !!this.getAttributeNodeNS(node, uri, name);
        }
        return found;
    },
    
    /**
     * APIMethod: setAttributeNS
     * Adds a new attribute or changes the value of an attribute with the given
     *     namespace and name.
     *
     * Parameters:
     * node - {Element} Element node on which to set the attribute.
     * uri - {String} Namespace URI for the attribute.
     * name - {String} Qualified name (prefix:localname) for the attribute.
     * value - {String} Attribute value.
     */
    setAttributeNS: function(node, uri, name, value) {
        if(node.setAttributeNS) {
            node.setAttributeNS(uri, name, value);
        } else {
            if(this.xmldom) {
                if(uri) {
                    var attribute = node.ownerDocument.createNode(
                        2, name, uri
                    );
                    attribute.nodeValue = value;
                    node.setAttributeNode(attribute);
                } else {
                    node.setAttribute(name, value);
                }
            } else {
                throw "setAttributeNS not implemented";
            }
        }
    },
    

    CLASS_NAME: "XMLKeyValue" 
    
    

});
}



