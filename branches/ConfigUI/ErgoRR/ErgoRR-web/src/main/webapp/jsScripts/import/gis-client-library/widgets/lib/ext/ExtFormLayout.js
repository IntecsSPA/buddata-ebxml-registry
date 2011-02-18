/******************************************************
 *          EXTJS FORM LAYOUT                         *
 * ****************************************************/

function createExtInterfaceByXml(xmlDocument,lang){
  var interfaceXml;
  var layoutObj;
  var loc;
  if(!(xmlDocument instanceof XMLDocument)){
    interfaceXml = Sarissa.getDomDocument();
    interfaceXml.async=false;
    interfaceXml.validateOnParse=false;
    interfaceXml.load(xmlDocument);
    interfaceXml.setProperty("SelectionLanguage","XPath");
  }else
    interfaceXml=xmlDocument;
  Sarissa.setXpathNamespaces(interfaceXml,
                       "xmlns:gis='http://gisClient.pisa.intecs.it/gisClient'");
  var localizationControl=interfaceXml.selectNodes(
                        "/gis:inputInterface")[0].getAttribute("localization");
  var titleWindow="";
  var layoutTypeNodes=interfaceXml.selectNodes("/gis:inputInterface/gis:layout");
 if(localizationControl == 'yes'){
    var localizationPath=interfaceXml.selectNodes(
                     "/gis:inputInterface")[0].getAttribute("pathLocalization");
    loc=new localization(localizationPath+lang+".xml");
    titleWindow=loc.getLocalMessage(layoutTypeNodes[0].getAttribute("title"))
 }else
   titleWindow=layoutTypeNodes[0].getAttribute("title");
 
 if(layoutTypeNodes.length > 0){
     var type=layoutTypeNodes[0].getAttribute("type");
     switch (type) {
         case "window":
                var tmpPanel=createPanelExjFormByXml(xmlDocument, lang);
                var width=layoutTypeNodes[0].getAttribute("width");
                var height=layoutTypeNodes[0].getAttribute("height");
                var perc,closeAction,layout;
                if(width.indexOf('%')!= -1){
                   perc=width.split('%');
                   width=(screen.width/100)*parseInt(perc[0]);
                }
                if(height.indexOf('%')!= -1){
                   perc=height.split('%');
                   height=(screen.height/100)*parseInt(perc[0]);
                }
                if(layoutTypeNodes[0].getAttribute("closeAction"))
                   closeAction=layoutTypeNodes[0].getAttribute("closeAction");
                else
                   closeAction='hide';
                if(layoutTypeNodes[0].getAttribute("layout"))
                   layout=layoutTypeNodes[0].getAttribute("layout");
                else
                   layout='fit';

                layoutObj={
                            xmlFormPanel: tmpPanel,
                            container:new Ext.Window({
                                    title: titleWindow,
                                    border: eval(layoutTypeNodes[0].getAttribute("border")),
                                    animCollapse : eval(layoutTypeNodes[0].getAttribute("animCollapse")),
                                    autoScroll : eval(layoutTypeNodes[0].getAttribute("autoScroll")),
                                    resizable : eval(layoutTypeNodes[0].getAttribute("resizable")),
                                    maximizable: eval(layoutTypeNodes[0].getAttribute("maximizable")),
                                    collapsible: eval(layoutTypeNodes[0].getAttribute("collapsible")),
                                    layout: layout,
                                    width:  width,
                                    height: height,
                                    x: 10,
                                    y: 20,
                                    closeAction: closeAction,
                                    items:[tmpPanel.formsPanel]
                                    }),
                            render:function(){
                                    this.container.show();
                                    this.xmlFormPanel.render();
                                    alert("ok");
                                    this.container.setPosition(50,50);
                                  },
                            setFormPanelDDEvent:function(ddGroup,index,fields){
                                    this.xmlFormPanel.setFormPanelDDEvent(ddGroup,index,fields);
                                  },
                            setFieldSetDDEvent: function(index,fields, fieldValues){
                                    
                                    this.xmlFormPanel.setFieldSetDDEvent(index,fields, fieldValues);
                                  }
                };
             break;

     }
  return(layoutObj);
 }else{
   alert("Not Layout Node in the XML Interface Document");
   return(null);
 }
}


