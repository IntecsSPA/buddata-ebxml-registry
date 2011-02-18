/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * Class: OpenLayers.Style
 * This class represents Styled Layer Descriptor for a WMS layer
 */
OpenLayers.StyledLayerDescriptor = OpenLayers.Class({

    SLDNAMESPACE: "http://www.opengis.net/sld",
    SLDVERSION: "1.0.0",
    SLDSCHEMALOCATION: "http://www.opengis.net/sld",
    
    SLD_RULE: "Rule",
    SLD_RULE_TITLE: "Title",
    SLD_RULE_NAME: "Name",
    SLD_RULE_DESCRIPTION: "Description",
    
    OGC_FILTER:"Filter",

    OGC_COMP_EQUAL_TO:"PropertyIsEqualTo",
    OGC_COMP_NOT_EQUAL_TO:"PropertyIsNotEqualTo",
    OGC_COMP_LESS_THAN:"PropertyIsLessThan",
    OGC_COMP_GREATER_THAN:"PropertyIsGreaterThan",
    OGC_COMP_LESS_THAN_OR_EQUAL_TO:"PropertyIsLessThanOrEqualTo",
    OGC_COMP_GREATER_THAN_OR_EQUAL_TO:"PropertyIsGreatherThanOrEqualTo",
    OGC_COMP_BETWEEN:"PropertyIsBeetwen",
    OGC_COMP_LIKE:"PropertyIsLike",
    
    OGC_LOG_AND: "And",
    OGC_LOG_OR: "Or",
    OGC_LOG_NOT: "Not",
            
    SLD_PROPERTY_NAME:"PropertyName",    
    SLD_LITERAL:"Literal",
    /**
     * APIProperty: Styled Layer Descriptor Name
     * {String}
     */
    name: null,
    
    /**
     * Property: Styled Layer Descriptor Title
     * {String} Title of this style (set if included in SLD)
     */
    title: null,
    
    /**
     * Property: Styled Layer Descriptor Description
     * {String} Description of this style (set if abstract is included in SLD)
     */
    description: null,

    /**
     * APIProperty: layerName
     * {<String>} name of the layer that this SLD belongs to, usually
     * according to the NamedLayer attribute of an SLD document.
     */
    layerName: null,
    
   
// FORSE NON NECESSARIA
    /**
     * APIProperty: isDefault
     * {Boolean}
     */
    isDefault: false,
     
     
    /** 
     * Property: rules 
     * {Array(<OpenLayers.Rule>)}
     */
    rules: null,
    
    /**
     * Property: context
     * {Object} An optional object with properties that symbolizers' property
     * values should be evaluatad against. If no context is specified,
     * feature.attributes will be used
     */
    context: null,

    /**
     * Property: defaultStyle
     * {Document} The current Styled Layer Descriptor Document.
     */
    SLDdocument:null,
    
    /**
     * Property: propertyStyles
     * {Hash of Boolean} cache of style properties that need to be parsed for
     * propertyNames. Property names are keys, values won't be used.
     */
    propertyStyles: null,
    

    /** 
     * Constructor: OpenLayers.StyledLayerDescriptor
     * Creates a UserStyle.
     *
     * Parameters:
     * defaultFilter- {Document} Default FeatureTypeStyle.
     * options      - {Object} An optional object with properties to set on the
     *                userStyle
     * 
     * Return:
     * {<OpenLayers.StyledLayerDescriptor>}
     */
    initialize: function(defaultFeatureTypeStyle, options) { 
       
        this.createNewSLD();
        this.setDefaultRules(defaultFeatureTypeStyle);
        OpenLayers.Util.extend(this, options);
    },

    /** 
     * APIMethod: destroy
     * nullify references to prevent circular references and memory leaks
     */
    destroy: function() {
        for (var i=0; i<this.rules.length; i++) {
            this.rules[i].destroy();
            this.rules[i] = null;
        }
        this.rules = null;
        this.defaultStyle = null;
    },
    
    /**
     * Method: createSymbolizer
     * creates a style by applying all feature-dependent rules to the base
     * style.
     * 
     * Parameters:
     * feature - {<OpenLayers.Feature>} feature to evaluate rules for
     * 
     * Returns:
     * {Object} symbolizer hash
     */
    createSymbolizer: function(feature) {
        var style = this.createLiterals(
            OpenLayers.Util.extend({}, this.defaultStyle), feature);
        
        var rules = this.rules;

        var rule, context;
        var elseRules = [];
        var appliedRules = false;
        for(var i=0; i<rules.length; i++) {
            rule = rules[i];
            // does the rule apply?
            var applies = rule.evaluate(feature);
            
            if(applies) {
                if(rule instanceof OpenLayers.Rule && rule.elseFilter) {
                    elseRules.push(rule);
                } else {
                    appliedRules = true;
                    this.applySymbolizer(rule, style, feature);
                }
            }
        }
        
        // if no other rules apply, apply the rules with else filters
        if(appliedRules == false && elseRules.length > 0) {
            appliedRules = true;
            for(var i=0; i<elseRules.length; i++) {
                this.applySymbolizer(elseRules[i], style, feature);
            }
        }

        // don't display if there were rules but none applied
        if(rules.length > 0 && appliedRules == false) {
            style.display = "none";
        } else {
            style.display = "";
        }
        
        return style;
    },
    
    /**
     * Method: applySymbolizer
     *
     * Parameters:
     * rule - {OpenLayers.Rule}
     * style - {Object}
     * feature - {<OpenLayer.Feature.Vector>}
     *
     * Returns:
     * {Object} A style with new symbolizer applied.
     */
    applySymbolizer: function(rule, style, feature) {
        var symbolizerPrefix = feature.geometry ?
                this.getSymbolizerPrefix(feature.geometry) :
                OpenLayers.Style.SYMBOLIZER_PREFIXES[0];

        var symbolizer = rule.symbolizer[symbolizerPrefix] || rule.symbolizer;

        // merge the style with the current style
        return this.createLiterals(
                OpenLayers.Util.extend(style, symbolizer), feature);
    },
    
    /**
     * Method: createLiterals
     * creates literals for all style properties that have an entry in
     * <this.propertyStyles>.
     * 
     * Parameters:
     * style   - {Object} style to create literals for. Will be modified
     *           inline.
     * feature - {Object}
     * 
     * Returns:
     * {Object} the modified style
     */
    createLiterals: function(style, feature) {
        var context = this.context || feature.attributes || feature.data;
        
        for (var i in this.propertyStyles) {
            style[i] = OpenLayers.Style.createLiteral(style[i], context, feature);
        }
        return style;
    },
    
    /**
     * Method: findPropertyStyles
     * Looks into all rules for this style and the defaultStyle to collect
     * all the style hash property names containing ${...} strings that have
     * to be replaced using the createLiteral method before returning them.
     * 
     * Returns:
     * {Object} hash of property names that need createLiteral parsing. The
     * name of the property is the key, and the value is true;
     */
    findPropertyStyles: function() {
        var propertyStyles = {};

        // check the default style
        var style = this.defaultStyle;
        this.addPropertyStyles(propertyStyles, style);

        // walk through all rules to check for properties in their symbolizer
        var rules = this.rules;
        var symbolizer, value;
        for (var i=0; i<rules.length; i++) {
            var symbolizer = rules[i].symbolizer;
            for (var key in symbolizer) {
                value = symbolizer[key];
                if (typeof value == "object") {
                    // symbolizer key is "Point", "Line" or "Polygon"
                    this.addPropertyStyles(propertyStyles, value);
                } else {
                    // symbolizer is a hash of style properties
                    this.addPropertyStyles(propertyStyles, symbolizer);
                    break;
                }
            }
        }
        return propertyStyles;
    },
    
    /**
     * Method: addPropertyStyles
     * 
     * Parameters:
     * propertyStyles - {Object} hash to add new property styles to. Will be
     *                  modified inline
     * symbolizer     - {Object} search this symbolizer for property styles
     * 
     * Returns:
     * {Object} propertyStyles hash
     */
    addPropertyStyles: function(propertyStyles, symbolizer) {
        var property;
        for (var key in symbolizer) {
            property = symbolizer[key];
            if (typeof property == "string" &&
                    property.match(/\$\{\w+\}/)) {
                propertyStyles[key] = true;
            }
        }
        return propertyStyles;
    },
    
   
    addRules: function(rules) {
        this.rules = this.rules.concat(rules);
        this.propertyStyles = this.findPropertyStyles();
    },

    
    createNewStringSLD: function(){
      var stringSLD="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+
                "<StyledLayerDescriptor version=\""+this.SLDVERSION+"\""+
                "xsi:schemaLocation=\""+this.SLDSCHEMALOCATION+"\""+ 
                "xmlns=\""+this.SLDNAMESPACE+"\""+ 
                "xmlns:ogc=\"http://www.opengis.net/ogc\""+ 
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\""+ 
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
                "<NamedLayer>"+
                "<Name>Default Style</Name>"+
                 "<UserStyle>"+
                "<FeatureTypeStyle>"+
                "</FeatureTypeStyle>"+
                "</UserStyle>"+
                "</NamedLayer>"+
                "</StyledLayerDescriptor>";
        this.SLDDocument= Sarissa.getDomDocument(); 
        this.SLDDocument=(new DOMParser()).parseFromString(stringSLD, "text/xml");  

    },
    
    setDefaultElementRules: function(defaultFeatureTypeStyle){
       this.elementRules=defaultFeatureTypeStyle.selectNodes("/Rule"); 
    },

     /**
     * APIMethod: createNewElementRule
     * Add rule to this Styled Layer Descriptor Document.
     * 
     * Parameters:
     * rules - <OpenLayers.Rule>
     */  
    createNewElementRule: function(rule){
      /*var featureTypeStyleNodes=this.SLDDocument.selectNodes("/StyledLayerDescriptor/NamedLayer/UserStyle/FeatureTypeStyle");  */
   
      var newRuleElement = this.SLDDocument.createElement(this.SLD_RULE);
      var newNode=null;
      var newtext=null;
      
      if(rule.title){
        newNode=this.SLDDocument.createElement(this.SLD_RULE_TITLE);
        newtext=this.SLDDocument.createTextNode(rule.title);
        newNode.appendChild(newtext);
        newRuleElement.appendChild(newNode);
      }
      if(rule.name){
        newNode=this.SLDDocument.createElement(this.SLD_RULE_NAME);
        newtext=this.SLDDocument.createTextNode(rule.name);
        newNode.appendChild(newtext);
        newRuleElement.appendChild(newNode);
      }
      if(rule.description){
        newNode=this.SLDDocument.createElement(this.SLD_RULE_DESCRIPTION);
        newtext=this.SLDDocument.createTextNode(rule.description);
        newNode.appendChild(newtext);
        newRuleElement.appendChild(newNode);
      }
          
      var newFilterElement = this.SLDDocument.createElement(this.OGC_FILTER);
      newFilterElement.appendChild(getFilterContentElement(rule));    
  

         
    },
    
    
    
    
    
    getFilterContentElement: function (rule){
      var tagComparisonName=this.getComparisonTag(rule.type);
      if(tagComparisonName){
         return this.getComparisonElement(tagComparisonName,rule.property,rule.value);
      }
      else{
        var tagLogicalName=this.getLogicalTag(rule.type);  
        if(tagLogicalName){
           var logicalNode=this.SLDDocument.createElement(tagLogicalName);  
           for(var i=0; i<rule.filter.length;i++) 
               logicalNode.appendChild(getFilterContentElement(rule.filer[i]));
           return(logicalNode);
        }  
        else
          alert("ERROR: Rule Type Error");  
      } 
    },
    
    getComparisonElement: function (tagName, property, value){
      var compNode=this.SLDDocument.createElement(tagName); 
      var propNode=this.SLDDocument.createElement(this.SLD_PROPERTY_NAME); 
      var newtext=this.SLDDocument.createTextNode(property);
      propNode.appendChild(newtext);
      var literalNode=this.SLDDocument.createElement(this.SLD_LITERAL); 
      newtext=this.SLDDocument.createTextNode(value);
      literalNode.appendChild(newtext);
      compNode.appendChild(propNode);
      compNode.appendChild(literalNode);
      return compNode;
    },
    
    getComparisonTag: function(operator){
        switch (operator) {
            case OpenLayers.Filter.Comparison.EQUAL_TO:
                return this.OGC_COMP_EQUAL_TO;
            case OpenLayers.Filter.Comparison.NOT_EQUAL_TO:
                return this.OGC_COMP_NOT_EQUAL_TO;
            case OpenLayers.Filter.Comparison.LESS_THAN:
                return this.OGC_COMP_LESS_THAN;
            case OpenLayers.Filter.Comparison.GREATER_THAN:
                return this.OGC_COMP_GREATER_THAN;
            case OpenLayers.Filter.Comparison.LESS_THAN_OR_EQUAL_TO:
                return this.OGC_COMP_LESS_THAN_OR_EQUAL_TO;
            case OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO:
                return this.OGC_COMP_GREATER_THAN_OR_EQUAL_TO;
            case OpenLayers.Filter.Comparison.BETWEEN:
                return this.OGC_COMP_BETWEEN;
            case OpenLayers.Filter.Comparison.LIKE :
                return this.OGC_COMP_LIKE ;    
            default :
                return null;
        } 

    },
    
    getLogicalTag: function (operator){
       switch (operator) {
            case OpenLayers.Filter.Logical.AND:
                return this.OGC_LOG_AND;
            case OpenLayers.Filter.Logical.NOT:
                return this.OGC_LOG_NOT;
            case OpenLayers.Filter.Logical.OR:
                return this.OGC_LOG_OR;
            default:
                return null;    
       }          
    },
    

    /**
     * APIMethod:  addNewComparisonToElementRule
     * Add comparison to rule element.
     * 
     * Parameters:
     * indexRule - <Integer> Index of the rule to modify
     * comparison - <OpenLayers.Filter.Comparison>
     */ 
    
    addNewComparisonToElementRule: function(indexRule, comparison){
       var newComparisonElement=""; 
    },
    
    CLASS_NAME: "OpenLayers.StyledLayerDescriptor"
});


/**
 * Function: createLiteral
 * converts a style value holding a combination of PropertyName and Literal
 * into a Literal, taking the property values from the passed features.
 * 
 * Parameters:
 * value - {String} value to parse. If this string contains a construct like
 *         "foo ${bar}", then "foo " will be taken as literal, and "${bar}"
 *         will be replaced by the value of the "bar" attribute of the passed
 *         feature.
 * context - {Object} context to take attribute values from
 * feature - {OpenLayers.Feature.Vector} The feature that will be passed
 *     to <OpenLayers.String.format> for evaluating functions in the context.
 * 
 * Returns:
 * {String} the parsed value. In the example of the value parameter above, the
 * result would be "foo valueOfBar", assuming that the passed feature has an
 * attribute named "bar" with the value "valueOfBar".
 */
OpenLayers.Style.createLiteral = function(value, context, feature) {
    if (typeof value == "string" && value.indexOf("${") != -1) {
        value = OpenLayers.String.format(value, context, [feature]);
        value = (isNaN(value) || !value) ? value : parseFloat(value);
    }
    return value;
};
    
/**
 * Constant: OpenLayers.Style.SYMBOLIZER_PREFIXES
 * {Array} prefixes of the sld symbolizers. These are the
 * same as the main geometry types
 */
OpenLayers.Style.SYMBOLIZER_PREFIXES = ['Point', 'Line', 'Polygon'];



