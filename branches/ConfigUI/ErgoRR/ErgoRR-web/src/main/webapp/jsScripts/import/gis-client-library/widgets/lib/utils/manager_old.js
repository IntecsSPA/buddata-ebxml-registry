/* 
 * GISClient manager functionalities
 * author: Andrea Marongiu
 */

GisClientManager= function(lang, proxyUrl, utilsUrl) {
 this.lang="eng";
 this.utilsUrl="Utils";
 this.proxyUrl="ProxyRedirect";
 this.loadScripts= new Array();
 this.loadCSS= new Array();

 this.init= function(){
     if(lang)
       this.lang=lang;

     if(proxyUrl)
       this.proxyUrl=proxyUrl;

     if(utilsUrl)
       this.utilsUrl=utilsUrl;


 };

 this.loadScript= function(url){
            if(!document.getElementById(url)){
                var script=document.createElement('script');
                script.defer=false;script.type="text/javascript";
                script.src=url;
                script.id=url;
                document.getElementsByTagName('head')[0].appendChild(script);
                this.loadScripts.push(script);
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
                link.href=url;
                link.id=url;
                document.getElementsByTagName('head')[0].appendChild(link);
               // this.loadCSS.push(link);
            }
};
this.onloadTest=function(){
 // Not yet Implemeted!! Carica le librerie sulla base di un xml delle funzionalità
};

this.onLoadControl= function(cssNumber, scriptNumber){
    

};

this.init();

}
