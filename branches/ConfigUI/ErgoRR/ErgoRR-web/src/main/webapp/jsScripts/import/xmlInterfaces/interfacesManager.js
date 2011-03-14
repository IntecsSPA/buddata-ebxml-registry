/* 
 * XMLInterfaces Manager 
 * author: Andrea Marongiu
 */




InterfacesManager= function(lang, xmlClientLibPath, proxyUrl, utilsUrl) {

 this.lang="eng";
 this.xmlClientLibPath="";
 this.gisClientLibPath="";
 this.utilsUrl="Utils";
 this.proxyUrl="ProxyRedirect";
 this.loadScripts= new Array();
 this.loadCSS= new Array();
 this.initFun=null;


 this.init= function(){
     if(lang)
       this.lang=lang;
     if(xmlClientLibPath)
       this.xmlClientLibPath=xmlClientLibPath;
     if(proxyUrl)
       this.proxyUrl=proxyUrl;
     if(utilsUrl)
       this.utilsUrl=utilsUrl;
 
 };

 this.setXmlClientLibPath= function(xmlClientLibPath){
     if(xmlClientLibPath)
       this.xmlClientLibPath=xmlClientLibPath;
     /*Browser util*/
     this.loadScript("widgets/utils/browserDetect.js");
 }
 
 this.setGisClientLibPath= function(gisClientLibPath){
     if(gisClientLibPath)
       this.gisClientLibPath=gisClientLibPath;
 }


 this.setLanguage= function(lang){
     if(lang)
       this.lang=lang;
 }



 this.loadGlobalScript= function(url){ 
     $.getScript(url, null);        
};

 

 this.loadScript= function(url, onLoadFn){
    $.getScript(this.xmlClientLibPath+"/"+url, onLoadFn);  
 };
 
 this.loadGisScript= function(url, onLoadFn){
    $.getScript(this.gisClientLibPath+"/"+url, onLoadFn);  
 };
 
 this.insertScript= function(content, id){
   var script=document.createElement('script');
   script.defer=false;script.type="text/javascript";
   script.innerHTML=content;
   script.id=id;
   document.getElementsByTagName('head')[0].appendChild(script);
};

 this.loadCSS= function(url){
            if(!document.getElementById(url)){
                var link=document.createElement('link');
                link.defer=false;
                link.rel="stylesheet";
                link.type="text/css";
                link.href=this.xmlClientLibPath+"/"+url;;
                link.id=url;
                document.getElementsByTagName('head')[0].appendChild(link);
            }
};

this.loadGisCSS= function(url){
            if(!document.getElementById(url)){
                var link=document.createElement('link');
                link.defer=false;
                link.rel="stylesheet";
                link.type="text/css";
                link.href=this.gisClientLibPath+"/"+url;;
                link.id=url;
                document.getElementsByTagName('head')[0].appendChild(link);
            }
};


 this.mainImport= function(){
    
    /*Sarissa Import*/
    this.sarissaImport();
    
    /*Extjs Import*/
    this.extjsImport();

    this.widgetsImport();

 };
 
 

 this.extjsImport= function(){

     /*EXTjs Import -- START*/
     this.loadCSS("import/ext/resources/css/ext-all.css");


         this.loadScript("import/ext/adapter/ext/ext-base.js",
                function(){
                   // alert("ext-all import");
                    interfacesManager.loadScript("import/ext/ext-all.js",
                       /*Util Import*/
                        function(){
                             interfacesManager.extExtensionImport();
                             interfacesManager.utilImport();
                              
                                
                             interfacesManager.extInterfaceImport();
                          }
                    );
                 }
          );
    // }
     /*EXTjs Import -- END*/
 };

 this.extExtensionImport= function(){
        /* Extension CSS*/
             this.loadCSS("import/ext/ux/css/Portal.css");
             this.loadCSS("import/ext/ux/css/GroupTab.css");

         /*Extension Javascript*/
             this.loadScript("import/ext/ux/GroupTabPanel.js");
             this.loadScript("import/ext/ux/GroupTab.js");
             this.loadScript("import/ext/ux/Portal.js");
             this.loadScript("import/ext/ux/PortalColumn.js");
             this.loadScript("import/ext/ux/Portlet.js");
 };

 this.gisImport= function(){  
    /*OpenLayers Import -- START*/
    // this.loadGisScript("import/OpenLayers/lib/OpenLayers.js", function(){
      
        
        interfacesManager.loadGisScript("widgets/lib/openlayers/Control/ScaleBar.js");
        interfacesManager.loadGisScript("widgets/lib/openlayers/Control/SetBox.js");
        interfacesManager.loadGisScript("widgets/lib/openlayers/Format/XMLKeyValue.js");
        
        /*WebGis Import -- START*/
       interfacesManager.loadGisCSS("widgets/style/css/webgis.css");
       
        interfacesManager.loadGisScript("widgets/lib/webgis/MapAction/MapAction.js", function(){
          interfacesManager.loadGisScript("widgets/lib/webgis/MapAction/Basic.js"); 
          
          interfacesManager.loadGisScript("widgets/style/locale/en.js");
          interfacesManager.loadGisScript("widgets/lib/webgis/Control/Toc.js");
         interfacesManager.loadGisScript("widgets/lib/webgis/Control/ScaleList.js");
         
         interfacesManager.loadGisScript("widgets/lib/webgis/MapAction/Editor.js");
         interfacesManager.loadGisScript("widgets/lib/webgis/MapAction/Measure.js");
         interfacesManager.loadGisScript("widgets/lib/webgis/MapAction/Identify.js");
        });
        
  
     
    /*WebGis Import -- END*/
    // });
     this.loadGisCSS("widgets/style/css/scalebar-thin.css");
    /*OpenLayers Import -- END*/
    
    

 };

 this.sarissaImport= function(){

     this.loadScript("import/sarissa/sarissa-compressed.js", function(){interfacesManager.loadScript("import/sarissa/sarissa_ieemu_xpath-compressed.js");});

 };


 this.widgetsImport= function(){

    //alert("widget imported");

    /*EXTjs widgets Import -- START*/
    
     this.loadScript("widgets/interface/extjs/ExtInterface.js");
     

     /*Import Ext Interface Field */
    // interfacesManager.extInterfaceImport();

    /*EXTjs widgets Import -- END*/

 };

 this.utilImport= function(){

  /*Spotlight util*/
  interfacesManager.loadScript("import/ext/ux/Spotlight.js");

  /*JSON util*/
  interfacesManager.loadScript("widgets/format/json.js");

  /*Encoders*/
  interfacesManager.loadScript("widgets/utils/encoders/base64.js");

  /*general util*/
  interfacesManager.loadScript("widgets/utils/general.js",function(){
            /*XML util*/
           interfacesManager.loadScript("widgets/utils/xmlDoc.js",function(){
            /*Localization util*/
            interfacesManager.loadScript("widgets/utils/localization.js", function(){ 
         
                $(document).ready(interfacesManager.initFun());//  setTimeout("interfacesManager.initFun();", 5000);
                if(interfacesManager.gisClientLibPath!=""){
                    /*GIS Import*/
                  $(document).ready(interfacesManager.gisImport());
                }
                
            }
            );})
        });

 };
 
 this.extInterfaceImport= function(){

    /*EXT INTERFACE FIELD -- Start Import*/


    /*Ext ux extension*/
    interfacesManager.loadScript("import/ext/ux/fileuploadfield/FileUploadField.js");

    /* BBOX field*/
  //  interfacesManager.loadScript("widgets/lib/ext/field/bboxField.js");

    /* FILE field*/
    interfacesManager.loadCSS("import/ext/ux/fileuploadfield/css/fileuploadfield.css");
    interfacesManager.loadScript("widgets/interface/extjs/fields/fileField.js");

    /* MULTITEXT field*/
    interfacesManager.loadScript("widgets/interface/extjs/fields/multiTextField.js");

    /* CHECKBOXGROUP field*/
   // interfacesManager.loadScript("widgets/lib/ext/field/checkBoxGroupField.js");

    /* SPINNER field*/
   // interfacesManager.loadScript("widgets/lib/ext/field/spinnerField.js");

    /* EDITAREA field*/
  //  interfacesManager.loadScript("widgets/lib/ext/field/editAreaField.js");

  //  interfacesManager.loadScript("widgets/lib/ext/RowExpander.js");

    /*EXT INTERFACE FIELD -- End Import*/
 };

 this.onReady= function(startFun){
    this.initFun=startFun;
    this.mainImport();
 };  
 
 
 this.getOLpath= function(){
    return(this.gisClientLibPath+"/import/OpenLayers/"); 
 };


this.init();

}


var interfacesManager=null;
interfacesManager= new InterfacesManager();
