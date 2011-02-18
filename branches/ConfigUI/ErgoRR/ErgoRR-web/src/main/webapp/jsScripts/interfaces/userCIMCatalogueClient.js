
/*
 * ErgoRR User CIM Catalogue Client Interface
 * author: Andrea Marongiu
 */



UserCIMCatalogueClient=function(){


    this.init=function(){


       this.gisInterfaceObj={

           xmlInterface: "resources/xml/interfaces/CSW-ebRIM-CIM_Profile.xml",

           webMapContext: "resources/xml/wmc/WebMapContext.xml",

           locPath: "resources/xml/localization/userCIMCatalogueClient/",

           formInterface:null,

           gisInterfaceObj: null,

           bodyColor:'#eeeeee',


           map:null,

           mapOptions:{
                        maxResolution: 1.40625/2,
                        enebaleMapEvent: true
                      },

            westPanel: {
                       id:'CIMUserClientinterfacePanel',
                       title: 'interface',
                       bodyStyle : {background: this.bodyColor},
                       autoScroll : true,
                       margins:'5 0 5 5',
                       layout:'fit',
                       html: "<div id='userCIMCatalogueInterface'>"
           },

           searchTabResults:new Ext.TabPanel({
                        border: false,
                        resizeTabs:true,
                        minTabWidth: 115,
                        tabWidth:135,
                        enableTabScroll:true,
                        defaults: {autoScroll:true},
                        items: [{
                                locPath: "resources/xml/localization/userCIMCatalogueClient/",
                                title: 'aaaa',
                                id: "CatalogueResultFirtTeble",

                                html: "<b></b>",
                                closable:false
                               }]
                    }),

             southPanel: {
                           id: 'userCIMCatalogueResults',
                           locPath: "resources/xml/localization/userCIMCatalogueClient/",
                                  listeners: {
                                          "expand": function(){
                                            var worspacePortal=Ext.getCmp('clientCIMUserPortal');
                                                    //alert(worspacePortal.getInnerHeight()- this.getPosition()[1]);
                                                    this.setHeight(worspacePortal.getInnerHeight()- this.getPosition()[1]);
                                                    this.doLayout();
                                           },

                                           "resize": function(){

                                           },
                                           "collapse": function(){

                                           },

                                           "beforerender": function(){

                                                var loc=new localization(this.locPath+gcManager.lang+".xml");
                                              //   alert(loc.getLocalMessage('CatalogueSearchResultsTitle'));
                                                this.setTitle(loc.getLocalMessage('CatalogueSearchResultsTitle'));
                                                this.doLayout();


                                           }

                                  },
                          title: "aaa",
                          autoScroll : true,
                          margins:'0 0 0 0'
                         // html:
              },


              mapTool: {
                         id: 'clientCIMUserMapPanel',
                         title: 'map',
                         layout:'anchor'
                       },

           render: function (elementID){



                   var worspacePortal=Ext.getCmp('clientCIMUserPortal');


                      /*Load web Map Context -- End*/


                        var interfacePanel=Ext.getCmp('CIMUserClientinterfacePanel');

                         interfacePanel.setHeight((worspacePortal.getInnerHeight()/100)*75);



                        var mapPanel=Ext.getCmp('clientCIMUserMapPanel');

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

                        var resultsPanel=Ext.getCmp('userCIMCatalogueResults');
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
                        this.formInterface.formsPanel.render(document.getElementById("userCIMCatalogueInterface"));
                        this.formInterface.render();
     }
       }
     },



     this.onLogin=function(){
          /* var formValuesImport=this.formInterface.getFormValues(false);
           if(formValuesImport.zipServices.uploadID){

               var loading=new Object();
               loading.message="Please Wait ...";
               loading.title="Import Services";

               var importServiceFunc=function(response){

                    Ext.Msg.show({
                                  title: 'Import Services',
                                  buttons: Ext.Msg.OK,
                                  width: Math.floor((screen.width/100)*50),
                                  msg: "<pre>"+response+"</pre>",
                                  fn: function(){
                                    window.location = 'main.jsp';
                                  },
                                  icon: Ext.MessageBox.INFO
                     });
               }


               var importServiceTimeOut=function(){
                   Ext.Msg.show({
                                    title:'Import group services: Error',
                                    buttons: Ext.Msg.OK,
                                    msg: 'Request TIME-OUT!',
                                    animEl: 'elId',
                                    icon: Ext.MessageBox.ERROR
                                });
               }

                var onSubmit=sendXmlHttpRequestTimeOut("GET",
                     "manager?cmd=importGroupServices&id="+formValuesImport.zipServices.uploadID,
                     true, null, 800000, importServiceFunc, importServiceTimeOut,null,
                                            loading, null);
           }*/
    };

     this.init();

     return(this.gisInterfaceObj);
};


