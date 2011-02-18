/* 
 * GISClient manager functionalities
 * author: Andrea Marongiu
 */


//create onDomReady Event
window.onDomReady = DomReady;

//Setup the event
function DomReady(fn)
{
   
	//W3C
	if(document.addEventListener)
	{
		document.addEventListener("DOMContentLoaded", fn, false);
	}
	//IE
	else
	{
          document.onreadystatechange = function(){readyState(fn)}
	}
}

//IE execute function
function readyState(fn)
{       
	//dom is ready for interaction
	if(document.readyState == "interactive")
	{
		fn();
	}
}



GisClientManager= function(lang, gisClientLibPath, proxyUrl, utilsUrl) {

 this.lang="eng";
 this.gisClientLibPath="";
 this.utilsUrl="Utils";
 this.proxyUrl="ProxyRedirect";
 this.loadScripts= new Array();
 this.loadCSS= new Array();
 this.initFun=null;


 this.init= function(){
     if(lang)
       this.lang=lang;
     if(gisClientLibPath)
       this.gisClientLibPath=gisClientLibPath;
     if(proxyUrl)
       this.proxyUrl=proxyUrl;
     if(utilsUrl)
       this.utilsUrl=utilsUrl;
   

   /*Browser util*/
    //this.loadScript("widgets/lib/utils/browserDetect.js");
   
 };

 this.setGisClientLibPath= function(gisClientLibPath){
     if(gisClientLibPath)
       this.gisClientLibPath=gisClientLibPath;

   
     /*Browser util*/
     this.loadScript("widgets/lib/utils/browserDetect.js");
 }


 this.setLanguage= function(lang){
     if(lang)
       this.lang=lang;
 }



 this.loadGlobalScript= function(url){
            if(!document.getElementById(url)){
                var script=document.createElement('script');
                script.defer=false;script.type="text/javascript";
                script.src=url;
                script.id=url;
                document.getElementsByTagName('head')[0].appendChild(script);
                this.loadScripts.push(script);
            }
};

 

 this.loadScript= function(url, onLoadFn){

            if(!document.getElementById(url)){
                var script;
               /* var agent = navigator.userAgent;
                var docWrite = (agent.match("MSIE") || agent.match("Safari"));
                if(docWrite){
                    alert(url);
                    script = "<script src='" + url + "' id='" + url + "'></script>";
                    document.write(script);
                    if(onLoadFn)
                      onLoadFn();
                }else{*/
                    script=document.createElement('script');
                    script.defer=false;script.type="text/javascript";
                    script.src=this.gisClientLibPath+"/"+url;
                    script.id=url;

                    if(onLoadFn){


                     script.onreadystatechange= function () {
                          
                        if (this.readyState == 'loaded') onLoadFn();
                       }
                     script.onload= onLoadFn;
                     }
                    document.getElementsByTagName('head')[0].appendChild(script);
                    this.loadScripts.push(script);
                    if(onLoadFn){
                        var agent = navigator.userAgent;
                            var docWrite = agent.match("MSIE");
                            if(docWrite){
                                onLoadFn();
                            }
                                
                    }
              // }
            }
 };
 this.insertScript= function(content, id){
   var script=document.createElement('script');
   script.defer=false;script.type="text/javascript";
   script.innerHTML=content;
   script.id=id;
   document.getElementsByTagName('head')[0].appendChild(script);
   //this.loadScripts.push(script);
};
 this.loadCSS= function(url){
            if(!document.getElementById(url)){
                var link=document.createElement('link');
                link.defer=false;
                link.rel="stylesheet";
                link.type="text/css";
                link.href=this.gisClientLibPath+"/"+url;;
                link.id=url;
                document.getElementsByTagName('head')[0].appendChild(link);
               // this.loadCSS.push(link);
            }
};


 this.mainImport= function(){

    
    
    /*Extjs Import*/
    this.extjsImport();

    /*GIS Import*/
   // this.gisImport();


    /*Sarissa Import*/
    this.sarissaImport();
    

    this.widgetsImport();
    

 };

 this.extjsImport= function(){

     /*EXTjs Import -- START*/
     this.loadCSS("import/ext/resources/css/ext-all.css");

     /*var agent = navigator.userAgent;
     if((agent.match("MSIE") || agent.match("Safari"))){
        this.loadScript("import/ext/adapter/ext/ext-base.js");
        this.loadScript("import/ext/ext-all.js");
     }else{ */
         //this.loadCSS("import/ext/resources/css/xtheme-gray.css");

         this.loadScript("import/ext/adapter/ext/ext-base.js",
                function(){
                   // alert("ext-all import");
                    gcManager.loadScript("import/ext/ext-all.js",
                       /*Util Import*/
                        function(){
                             gcManager.extExtensionImport();
                             gcManager.utilImport();
                             gcManager.gisImport();
                             gcManager.extInterfaceImport();
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
    // alert("gis imported");
    /*OpenLayers Import -- START*/
     this.loadScript("import/OpenLayers/lib/OpenLayers.js", function(){
         gcManager.loadScript("widgets/lib/openlayers/Control/ScaleBar.js");
        gcManager.loadScript("widgets/lib/openlayers/Control/SetBox.js");
        gcManager.loadScript("widgets/lib/openlayers/Format/XMLKeyValue.js");
        gcManager.loadScript("widgets/style/locale/en"/*+this.lang*/+".js");

     });
     
     this.loadCSS("widgets/style/css/scalebar-thin.css");
    /*OpenLayers Import -- END*/
    
    /*WebGis Import -- START*/
   //  this.loadScript("widgets/style/locale/en"/*+this.lang*/+".js");
     this.loadCSS("widgets/style/css/webgis.css");
     this.loadScript("widgets/lib/webgis/Control/Toc.js");
     this.loadScript("widgets/lib/webgis/Control/ScaleList.js");
     this.loadScript("widgets/lib/webgis/MapAction/MapAction.js");
     this.loadScript("widgets/lib/webgis/MapAction/Basic.js");
     this.loadScript("widgets/lib/webgis/MapAction/Editor.js");
     this.loadScript("widgets/lib/webgis/MapAction/Measure.js");
     this.loadScript("widgets/lib/webgis/MapAction/Identify.js");
    /*WebGis Import -- END*/

      
 };

 this.sarissaImport= function(){
   //  alert("sarissa imported");
     this.loadScript("import/sarissa/Sarissa.js", function(){gcManager.loadScript("import/sarissa/sarissa_ieemu_xpath.js");});
     //gcManager.loadScript("import/sarissa/sarissa_ieemu_xpath.js");

 };


 this.widgetsImport= function(){

    //alert("widget imported");

    /*EXTjs widgets Import -- START*/
    
     this.loadScript("widgets/lib/ext/ExtFormUtils.js");
     

     /*Import Ext Interface Field */
    // gcManager.extInterfaceImport();

    /*EXTjs widgets Import -- END*/

 };

 this.utilImport= function(){

  /*Spotlight util*/
  gcManager.loadScript("import/ext/ux/Spotlight.js");

  /*JSON util*/
  gcManager.loadScript("widgets/lib/utils/JSON.js");

  /*Encoders*/
  gcManager.loadScript("widgets/lib/utils/encoders/Base64.js");

  /*general util*/
  gcManager.loadScript("widgets/lib/utils/general.js",function(){
   //     alert("general");
            /*XML util*/
           gcManager.loadScript("widgets/lib/utils/XmlDoc.js",function(){
          //  alert("xmldoc");
            /*Localization util*/
            gcManager.loadScript("widgets/lib/utils/localization.js", function(){ 
                var agent = navigator.userAgent;
                if(agent.match("MSIE")){
                  setTimeout("gcManager.initFun();", 5000);
                }else
                  gcManager.initFun();
            }
            );})
        });

 };
 
 this.extInterfaceImport= function(){

    /*EXT INTERFACE FIELD -- Start Import*/


    /*Ext ux extension*/
    gcManager.loadScript("import/ext/ux/fileuploadfield/FileUploadField.js");

    /* BBOX field*/
    gcManager.loadScript("widgets/lib/ext/field/bboxField.js");

    /* FILE field*/
    gcManager.loadCSS("import/ext/ux/fileuploadfield/css/fileuploadfield.css");
    gcManager.loadScript("widgets/lib/ext/field/fileField.js");

    /* MULTITEXT field*/
    gcManager.loadScript("widgets/lib/ext/field/multiTextField.js");

    /* CHECKBOXGROUP field*/
    gcManager.loadScript("widgets/lib/ext/field/checkBoxGroupField.js");

    /* SPINNER field*/
    gcManager.loadScript("widgets/lib/ext/field/spinnerField.js");

    /* EDITAREA field*/
    gcManager.loadScript("widgets/lib/ext/field/editAreaField.js");

    gcManager.loadScript("widgets/lib/ext/RowExpander.js");

    /*EXT INTERFACE FIELD -- End Import*/
 };

 this.onReady= function(startFun){
    this.initFun=startFun;
    this.mainImport();
 };

this.onLoadControl= function(cssNumber, scriptNumber){
    

};

this.init();

}


var gcManager=null;
gcManager= new GisClientManager();
