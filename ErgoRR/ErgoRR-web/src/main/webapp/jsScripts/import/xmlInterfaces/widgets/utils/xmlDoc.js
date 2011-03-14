

XmlDoc = function (XmlURL, namespace){
    var xmlDoc;
 
    if(BrowserDetect.browser == "Chrome" || BrowserDetect.browser == "Opera"){
       if(!(XmlURL instanceof XMLDocument)){

         var xmlhttp = new window.XMLHttpRequest();
         xmlhttp.open("GET", XmlURL, false);
         xmlhttp.send(null);
         xmlDoc = xmlhttp.responseXML;
       }else
         xmlDoc=XmlURL;

   }else
    if(BrowserDetect.browser == "Firefox"){
          if(!(XmlURL instanceof XMLDocument)){
            xmlDoc = Sarissa.getDomDocument();
            xmlDoc.async=false;
           // xmlDoc._sarissa_useCustomResolver=true;
            xmlDoc.validateOnParse=false;
            xmlDoc.load(XmlURL);
            xmlDoc.setProperty("SelectionLanguage","XPath");
          }else
            xmlDoc=XmlURL;
        if(namespace)
          Sarissa.setXpathNamespaces(xmlDoc,namespace);
    }else{
     if(BrowserDetect.browser == "Explorer"){
        if(!(XmlURL instanceof ActiveXObject)){
            xmlDoc = Sarissa.getDomDocument();
            xmlDoc.async=false;
            xmlDoc.validateOnParse=false;
          //  xmlDoc._sarissa_useCustomResolver=true;
            xmlDoc.load(XmlURL);
            xmlDoc.setProperty("SelectionLanguage","XPath");
            if(namespace)
             Sarissa.setXpathNamespaces(xmlDoc,namespace);
        }else
            xmlDoc=XmlURL;  
     }
    }


  return{
    xmlDocument: xmlDoc,

    selectNodes: function (xpath){
        if(BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Explorer")
           return this.xmlDocument.selectNodes(xpath);
        else{
            var elementsName=xpath.split('/');
            var eleCurrList;
            var eleCurr=this.xmlDocument;
            for(var i=0; i<elementsName.length;i++){
                if(elementsName[i]!=''){
                    if(elementsName[i].indexOf(':')!= -1)
                       elementsName[i]=elementsName[i].split(':')[1];
                    eleCurrList=eleCurr.getElementsByTagName(elementsName[i]);
                    eleCurr=eleCurrList[0];
                }
            }
           return(eleCurrList);
        }
    }

  }
  

}


XmlElement = function (objectElement){
  return{
    xmlElement: objectElement,

    selectNodes: function (xpath){
        if(BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Explorer")
           if (this.xmlElement)
            return this.xmlElement.selectNodes(xpath);
           else
               return null;

        else{

            var elementsName=xpath.split('/');
            var eleCurrList;
            var eleCurr=this.xmlElement;
            for(var i=0; i<elementsName.length;i++){
                if(elementsName[i]!=''){
                    if(elementsName[i].indexOf(':')!= -1)
                       elementsName[i]=elementsName[i].split(':')[1];
                    eleCurrList=eleCurr.getElementsByTagName(elementsName[i]);
                    eleCurr=eleCurrList[0];
                }
            }
           return(eleCurrList);
        }
    }

  }

}


