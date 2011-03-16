
/**
 * Class: OpenLayers.SLDWMS
 * This class represents Styled Layer Descriptor for a WMS layer
 */
OpenLayers.SLDWMS = OpenLayers.Class({

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
     * APIProperty: namedLayers
     * {Hash of <String>} rendering description for each NamedLayer included
     * in SLD.
     */
    namedLayers: null,

    /**
     * APIProperty: defaultSymbolizer.
     * {Object} A symbolizer with the SLD defaults.
     */
    defaultSymbolizer: {
        fillColor: "#808080",
        fillOpacity: 1,
        strokeColor: "#FF0000",
        strokeOpacity: 1,
        strokeWidth: 1,
        pointRadius: 6
    },
    
    defaultNameRule: "defaultRule",
    
    defaultTitleRule: "defaultRule",
    /** 
     * Constructor: OpenLayers.SLDWMS
     * Creates a UserStyle.
     *
     * Parameters:
     * defaultLayerName - {String} Layer Name for create a Defautl  NamedLayer.
     * defaultRules      - {Document} Filter Rules for the Default NamedLayer .
     * options           - {Object} An optional object with properties to set on the
     *                      userStyle
     * 
     * Return:
     * {<OpenLayers.StyledLayerDescriptor>}
     */
    initialize: function(defaultLayerName, symbolizerType, defaultRules, options) { 
        var defaultStyle;
        
        this.namedLayers= new Array();
        if(defaultRules){
            
             defaultStyle={
                name: defaultLayerName+"Style",
                layerName: defaultLayerName,
                isDefault: 1,
                rules: defaultRules
            };
            this.addNamedLayer(defaultLayerName, [defaultStyle]);
        }
        else{
         
            rule=new Array();
            defaultStyle={
                name: defaultLayerName+"Style",
                layerName: defaultLayerName,
                isDefault: 1,
                rules: rule
            };
            this.addNamedLayer(defaultLayerName, [defaultStyle]);
            this.addRuleElseFilter(defaultLayerName,symbolizerType,
                                    this.defaultSymbolizer);
        }
       
        
      OpenLayers.Util.extend(this, options);
    },

    
    
    addNamedLayer: function (name, userStyles) {
      
        var newNamedLayer={
                name: name,
                userStyles: userStyles
               
        };
        this.namedLayers[name]=newNamedLayer;
    },
    
    addRule: function (layerName, ruleName, ruleTitle, filter, typeSymbolizer, symbolizer) {
       var indexNewRule=this.namedLayers[layerName].userStyles[0].rules.length;
       if(indexNewRule == 0)
          this.namedLayers[layerName].userStyles[0].rules=new Array(); 
       var newRule=new OpenLayers.Rule();
       // Costruzione Title da implementare
       
       newRule.name=ruleName;
       newRule.title=ruleTitle;
       newRule.symbolizer[typeSymbolizer]= symbolizer;
       newRule.filter=filter;
       this.namedLayers[layerName].userStyles[0].rules[indexNewRule]=newRule;
    },
    
    addRuleElseFilter: function (layerName, typeSymbolizer, symbolizer) {
       var indexNewRule=this.namedLayers[layerName].userStyles[0].rules.length;
       var newRule=new OpenLayers.Rule();
       newRule.symbolizer[typeSymbolizer]= symbolizer;
       newRule.elseFilter=true;
       this.namedLayers[layerName].userStyles[0].rules[indexNewRule]=newRule;
    },
    
    addFilterToEachRule: function(layerName, logicalOperator, filter, symbolizer, typeSymbolizer){
       var newRules=this.namedLayers[layerName].userStyles[0].rules;
       if(newRules.length>0){
          for(var i=0;i<newRules.length; i++){
            newRules[i]=this.addFilterToRule(newRules[i], logicalOperator, filter, symbolizer, typeSymbolizer) 
          }
          this.namedLayers[layerName].userStyles[0].rules=newRules;
       }else
          this.addRule(layerName,this.defaultNameRule,this.defaultTitleRule,
                                              filter,typeSymbolizer,symbolizer); 
    },
    
    addFilterToRule: function(rule, logicalOperator, filter, symbolizer, typeSymbolizer){
        var newRule=rule;
          if(symbolizer)
             newRule.symbolizer[typeSymbolizer]=symbolizer; 
          if(newRule.filter.type == logicalOperator)
               newRule.filter.filters.push(filter);
           else{  
                var newFilters= new Array();   
                newFilters.push(filter);
                newFilters.push(rule.filter);
                var newLogicalFilter=new OpenLayers.Filter.Logical({
                  type: logicalOperator,
                  filters: newFilters
                  });  
              newRule.filter=newLogicalFilter;
            } 
       return(newRule);     
    },
    
    removeAddedFilterToEachRule: function(filterType, logicalOperator, layerName){
      var newRules=this.namedLayers[layerName].userStyles[0].rules;
      var newFilters;
      for(var i=0;i<newRules.length; i++){
          if(newRules[i].filter.type == logicalOperator){
             newFilters=this.removeFilter(newRules[i].filter.filters, filterType);
             newRules[i].filter.filters=newFilters;
          }   
          else
             if(newRules[i].filter.filter) 
              if(newRules[i].filter.filter.type == logicalOperator){
               newFilters=this.removeFilter(newRules[i].filter.filter.filters, filterType); 
               newRules[i].filter.filter.filters=newFilters;
              } 
       }
       this.namedLayers[layerName].userStyles[0].rules=newRules; 
    },
    
    removeFilter: function(arrayFilter, filterType){
        var newArrayFilter= new Array();
        for(var i=0;i<arrayFilter.length;i++)
            if(arrayFilter[i].type != filterType)
                newArrayFilter.push(arrayFilter[i]); 
        return(newArrayFilter);
    },
    
    removeElseFilterRule: function(layerName, indexUserStyle){
      var userStyleRules=this.namedLayers[layerName].userStyles[indexUserStyle].rules;
      var newRules;
       for(var i=0;i<userStyleRules.length; i++){
           if(userStyleRules[i].elseFilter)
              newRules=this.removeRule(userStyleRules, i);
       }
       this.namedLayers[layerName].userStyles[indexUserStyle].rules=newRules;  
    },
    
    
    removeRule: function(arrayRules, indexRule){
        var newArrayRules= new Array();
        for(var i=0;i<arrayRules.length;i++)
            if(i != indexRule)
                newArrayRules.push(arrayRules[i]); 
        return(newArrayRules);
    },



    getOGCFilter: function (layerName, indexUserStyle){
      var index;
      var ogcFilter=null;
      var format = new OpenLayers.Format.FILTER();
      if(indexUserStyle)
         index=indexUserStyle;
      else
        index=0;
      var userStyleRules=this.namedLayers[layerName].userStyles[index].rules;
      
      if(userStyleRules.length==1){
         if(userStyleRules[0].filter) 
            ogcFilter=userStyleRules[0].filter;
      }else{
         var newFilters=new Array(); 
         for(var i=0; i< userStyleRules.length; i++){
             if(userStyleRules[i].filter)
                newFilters.push(userStyleRules[i].filter);
         } 
         ogcFilter=new OpenLayers.Filter.Logical({
                  type: OpenLayers.Filter.Logical.AND,
                  filters: newFilters
                  });   
      }   
      return(format.write(ogcFilter));    
    },
    
    getOGCFilterEncoded: function (layerName, indexUserStyle){

      var format = new OpenLayers.Format.FILTERShort();
       
      return(format.getShortFILTER(this.getOGCFilter(layerName, indexUserStyle)));    
    },
    
    
    convertConditonToFilter: function (condition){
      /*Not yet Implemented*/
      
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


