
/* Localization Class*/



localization= function (xmlLocalizationURL){

        this.docLocUrl=xmlLocalizationURL;
        this.localizationXML=null;

        this.init=function(){
            /*var localizationXML = Sarissa.getDomDocument();
            localizationXML.async=false;
            localizationXML.validateOnParse=false;
            localizationXML.load(this.docLocUrl);
            localizationXML.setProperty("SelectionLanguage","XPath");
            Sarissa.setXpathNamespaces(localizationXML,
                    "xmlns:loc='http://gisClient.pisa.intecs.it/localization'");*/
            this.localizationXML = new XmlDoc(this.docLocUrl,"xmlns:loc='http://gisClient.pisa.intecs.it/localization'");
           /* this.rootLocalization=localizationXML.selectNodes(
                                                        "/loc:Localization")[0];*/
        };
       this.getLocalMessage= function (messageName){
            var newMessage=this.localizationXML.selectNodes("/loc:Localization/loc:"+messageName)[0].firstChild.nodeValue;
            if(!newMessage){
              Ext.Msg.show({
                title:'Localization Error',
                buttons: Ext.Msg.OK,
                msg: 'Message: '+messageName + "not defined.",
                animEl: 'elId',
                icon: Ext.MessageBox.ERROR
             });
              return("");
            }else
              return(newMessage);
        };

        this.init();
};

