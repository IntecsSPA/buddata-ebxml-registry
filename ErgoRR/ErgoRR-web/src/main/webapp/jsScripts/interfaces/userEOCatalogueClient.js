
/*
 * ErgoRR User Earth Observation Catalogue Client Interface
 * author: Andrea Marongiu
 */


UserEOCatalogueClient=function(){


    this.init=function(){
       

       this.gisInterfaceObj={

           xmlInterface: "resources/xml/interfaces/CSW-ebRIM-EO_Profile-noloc.xml",

           webMapContext: "resources/xml/wmc/WebMapContext.xml",

           locPath: "resources/xml/localization/userEOCatalogueClient/",
           
           formInterface:null,

           gisInterfaceObj: null,

           bodyColor:'#eeeeee',

           catalogRequestCount: 0,

           map:null,

           mapOptions:{
                        maxResolution: 1.40625/2,
                        enebaleMapEvent: true
                      },

            westPanel: {          
                       id:'EOUserClientinterfacePanel',
                       title: 'interface',
                       bodyStyle : {background: this.bodyColor},
                       autoScroll : true,
                       margins:'5 0 5 5',
                       layout:'fit',
                       html: "<div id='userEOCatalogueInterface'>"
           },

           searchTabResults:new Ext.TabPanel({
                        border: false,
                        resizeTabs:true, 
                        minTabWidth: 115,
                        tabWidth:135,
                        enableTabScroll:true,
                        defaults: {autoScroll:true},
                        items: [{
                                locPath: "resources/xml/localization/userEOCatalogueClient/",
                                title: 'aaaa',
                                id: "CatalogueResultFirtTeble",
                               
                                html: "<b></b>",
                                closable:false
                               }]
                    }),

             southPanel: {
                           id: 'userEOCatalogueResults',
                           locPath: "resources/xml/localization/userEOCatalogueClient/",
                                  listeners: {
                                          "expand": function(){
                                            var worspacePortal=Ext.getCmp('clientEOUserPortal');
                                               
                                                    this.setHeight(worspacePortal.getInnerHeight()- this.getPosition()[1]);
                                                    this.doLayout();
                                           },

                                           "resize": function(){
                                               
                                           },
                                           "collapse": function(){
                                              
                                           },

                                           "beforerender": function(){
                                                var loc=new localization(this.locPath+gcManager.lang+".xml");
                                                this.setTitle(loc.getLocalMessage('CatalogueSearchResultsTitle'));
                                                this.doLayout();


                                           }

                                  },
                          title: "aaa",
                          autoScroll : true,
                          html: "<div id='eoSearchResponseID'/>",
                          margins:'0 0 0 0'
                         // html:
              },


              mapTool: {
                         id: 'clientEOUserMapPanel',
                         title: 'map',
                         layout:'anchor'
                       },

             sendEORequest: function(){


                this.catalogRequestCount++;
                var fillColor=randomColor();
                var style={fillColor: fillColor, fillOpacity: 0.6, strokeColor: "#FFFFFF", strokeOpacity: 1, strokeWidth: 1};

                var serviceRequest=this.formInterface.sendXmlKeyValueRequest(
                "eoSearchResponseID","ProxyRedirect",
                false,500000000,null,null,false,"styles/img/loader.gif",null,
                "SearchResult_"+this.catalogRequestCount,style);
          
           },

           render: function (elementID){

                   

                   var worspacePortal=Ext.getCmp('clientEOUserPortal');

                    
                      /*Load web Map Context -- End*/


                        var interfacePanel=Ext.getCmp('EOUserClientinterfacePanel');

                         interfacePanel.setHeight((worspacePortal.getInnerHeight()/100)*75);

                      

                        var mapPanel=Ext.getCmp('clientEOUserMapPanel');
                        
                        mapPanel.setHeight((worspacePortal.getInnerHeight()/100)*75);

                        var toolbarNorthPanel=new Ext.Panel({
                            border: false,
                            id: "mapNorth",
                            anchor:'100% 94%',
                            tbar: new Ext.Toolbar()
                        });

                        mapPanel.add(toolbarNorthPanel);

                          /* southToolbarPanel: new Ext.Panel({
                                        border: false,
                                        anchor:'100% 6%',
                                        html:'',
                                        bodyStyle : {background: this.bodyColor}
                           }),*/
                        //mapPanel.add(this.southToolbarPanel);

                        mapPanel.doLayout();

                        var resultsPanel=Ext.getCmp('userEOCatalogueResults');
                        resultsPanel.setHeight((worspacePortal.getInnerHeight()/100)*20);

                        var loc=new localization(this.locPath+gcManager.lang+".xml");
                        this.searchTabResults.items.items[0].title=loc.getLocalMessage('CatalogueResultTab');

                        resultsPanel.add(this.searchTabResults);
                        resultsPanel.doLayout();
                        /*Ext.getCmp('aggiunta').add({
                            title: 'Another Panel Aggiunto',
                           // tools: tools,
                            html: 'Ext.example.shortBogusMarkup'
                        });
                        alert("aggiunto");

                        Ext.getCmp('aggiunta').doLayout();

                        Ext.getCmp('aggiunta').columnWidth=.0;
                        alert("cambio");
                        Ext.getCmp('aggiunta').doLayout();*/
                   
                          /*Load web Map Context -- Start*/
                          this.map = new OpenLayers.Map(Ext.getCmp('mapNorth').body.dom, this.mapOptions);
                          var formatWMC = new OpenLayers.Format.WMC(this.mapOptions);
                          var contextMap = Sarissa.getDomDocument();
                          contextMap.async=false;
                          contextMap.validateOnParse=false;
                          contextMap.load(this.webMapContext);
                          this.map = formatWMC.read(contextMap, {map: this.map});


                       /*Search Interfaces Rendering -- End*/



                        var toc = new WebGIS.Control.Toc({map: this.map, parseWMS: false, autoScroll: true});
                    
                        this.map.addControl(new OpenLayers.Control.MousePosition());
			this.map.addControl(new OpenLayers.Control.MouseDefaults());
			this.map.addControl(new OpenLayers.Control.KeyboardDefaults());
			this.map.addControl(new OpenLayers.Control.LayerSwitcher());
                        
                     
                       var scalebar = new OpenLayers.Control.ScaleBar();
                        this.map.addControl(scalebar);

                        var northToolbar=Ext.getCmp('mapNorth').getTopToolbar();



			 //map action is an extended Ext.Action that can be used as a button or menu item
                        
			northToolbar.add(new WebGIS.MapAction.DragPan({map: this.map}));

			northToolbar.add(new WebGIS.MapAction.ZoomInBox({map: this.map}));
			northToolbar.add(new WebGIS.MapAction.ZoomOutBox({map: this.map}));
			northToolbar.add(new WebGIS.MapAction.ZoomIn({map: this.map}));
			northToolbar.add(new WebGIS.MapAction.ZoomOut({map: this.map}));
			northToolbar.add(new WebGIS.MapAction.PreviousExtent({map: this.map}));
			northToolbar.add(new WebGIS.MapAction.NextExtent({map: this.map}));
			northToolbar.add(new WebGIS.MapAction.FullExtent({map: this.map}));
                        var barPosition= Ext.getCmp('mapNorth').getPosition();
                        northToolbar.add(new WebGIS.MapAction.Scale({map: this.map, backgroundColor: this.bodyColor, scalebar:scalebar, windowPosition: {x:barPosition[0], y:barPosition[1]+40}}));
                        northToolbar.doLayout();

                        this.map.setCenter(new OpenLayers.LonLat(0, 0), 0);
                        //setTimeout("cat.map.setCenter(new OpenLayers.LonLat(0, 0), 0);",2000);

                        
                        /*Search Interfaces Rendering -- Start*/
                         this.formInterface=createPanelExjFormByXml(
                         this.xmlInterface, gcManager.lang);
                        this.formInterface.formsPanel.render(document.getElementById("userEOCatalogueInterface"));
                        this.formInterface.render();
     }
       }
     };

    

     

     this.init();

     return(this.gisInterfaceObj);
};

