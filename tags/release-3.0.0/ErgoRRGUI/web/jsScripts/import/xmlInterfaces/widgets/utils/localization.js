
/* Localization Class*/



localization= function (xmlLocalizationURL){

        this.docLocUrl=xmlLocalizationURL;
        this.rootLocalization=null;

        this.init=function(){
            
            
            var localizationXML = new XmlDoc (this.docLocUrl, 
                "xmlns:loc='http://gisClient.pisa.intecs.it/localization'");
            this.rootLocalization=localizationXML.selectNodes(
                                                        "/loc:Localization")[0];
        };
       this.getLocalMessage= function (messageName){
            var newMessage=new XmlElement(this.rootLocalization).selectNodes("loc:"+messageName)[0].firstChild.nodeValue;
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

