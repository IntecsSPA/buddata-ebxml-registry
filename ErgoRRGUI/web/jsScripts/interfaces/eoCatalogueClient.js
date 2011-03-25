

Ext.namespace('ErgorrCatalogues');

var selectionLayer=null;
var proxyRedirect="ProxyRedirect";
var timeOutRequestCatalog=500;
var map;

function set_selectionLayer(newValue){ 
  selectionLayer=newValue;
}

ErgorrCatalogues.Application = function()
{
  var toolbar,toolbarSouth;
  var formsObject;
  var south=null;
  var mapOptions = {
        maxResolution: 1.40625/2,
        enebaleMapEvent: true
  };
  return { 
           map: null,
           formsCatInterfObject: null,
           eopXMLInterfaceUrl: "resources/xml/interfaces/CSW-ebRIM-EO_Profile-noloc.xml",
           westPanel: null,
	   init: function()
		{
		 toolbar = new Ext.Toolbar();
                 toolbarSouth = new Ext.Toolbar();
		 var panel = new Ext.Panel({
			border: false,
                        layout:'anchor',
                        anchor:'100% 94%',
                        tbar: toolbar
		 });
                        
                 var southToolbarPanel = new Ext.Panel({
				border: false,
                                anchor:'100% 6%',
                                html:'',
                                bodyStyle : {background: '#99bbe8'},
				tbar: toolbarSouth
                  });

                  south =new Ext.Panel({region:'south',
                                  split:true,
                                  height: (window.innerHeight || document.body.clientHeight)/3.5,
                                  collapsible: true,
                                  collapsed : true,
                                  title:'Response',
                                  autoScroll : true,
                                  margins:'0 0 0 0', 
                                  listeners: {
                                    expand: function(){

                                    },
                                    collapse: function(){
                                        eopCat.mapRefresh();
                                    },
                                    resize: function(){
                                        eopCat.mapRefresh();
                                    }
                                  },
                                  html: "<div id='responsePanelID'></div>" 
                  });
                 
                  
                  
                  eopCat.westPanel =new Ext.Panel({region:'west',
                                  split:true,
                                  width: (window.innerWidth || document.body.clientWidth)/2.6,
                                  collapsible: true,
                                  collapsed : false,
                                  title:'Catalogue Interfaces',
                                  bodyStyle : {background: '#99bbe8'},
                                  autoScroll : true,
                                  margins:'0 0 0 0',
                                  listeners: {
                                    expand: function(){

                                    },
                                    collapse: function(){
                                        eopCat.mapRefresh();
                                    },
                                    resize: function(){
                                       if(eopCat.formsCatInterfObject)
                                            eopCat.formsCatInterfObject.render();
                                        eopCat.mapRefresh();
                                       
                                    }
                                  },
                                  html: "<div id='formcataloguePanelID'></div>"
                                 
                  });
                  
		
                      var mapTool=new Ext.Panel({
                          region: 'center',
                          layout:'anchor',
                          anchorSize: {width:(window.innerWidth || document.body.clientWidth), 
                                       height:(window.innerHeight || document.body.clientHeight)},
                          items:[panel,southToolbarPanel]
                      });
                      
                      var viewport = new Ext.Viewport({
                            layout:'border',
                            items:[eopCat.westPanel,south,mapTool]
                      });
                       eopCat.map = new OpenLayers.Map(panel.body.dom, mapOptions); 
                       
                       
                       eopCat.formsCatInterfObject=createPanelExjFormByXml(eopCat.eopXMLInterfaceUrl);
                       eopCat.formsCatInterfObject.formsPanel.render(document.getElementById("formcataloguePanelID"));
                       eopCat.formsCatInterfObject.render();     
                       
                       var formatWMC = new OpenLayers.Format.WMC(mapOptions);
                       
                     
                       var contextMap = new XmlDoc("resources/xml/wmc/WebMapContext.xml").xmlText;
                      

                       eopCat.map = formatWMC.read(contextMap, {map: eopCat.map});       
                       eopCat.map.zoomToMaxExtent();
                     
                       
                       // standard Open Layers
                        eopCat.map.addControl(new OpenLayers.Control.MousePosition());
			eopCat.map.addControl(new OpenLayers.Control.MouseDefaults());
	
			eopCat.map.addControl(new OpenLayers.Control.LayerSwitcher());
      
                        
			toolbar.add(new WebGIS.MapAction.DragPan({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomInBox({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomOutBox({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomIn({map: eopCat.map}));                    
			toolbar.add(new WebGIS.MapAction.ZoomOut({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.PreviousExtent({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.NextExtent({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.FullExtent({map: eopCat.map}));
                        
                        toolbar.add(new WebGIS.MapAction.Scale({map: eopCat.map, pathGisClient:"jsScripts/import/gis-client-library/"}));
                      
                      eopCat.westPanel.collapse(false);
                     
                   
                     
                      setErgoRRURL();
                     var hideMask = function () {
                        Ext.get('loading').remove();
                        Ext.fly('loading-mask').fadeOut({
                            remove:true/*,
                            callback : west.expand*/
                        });
                    }

                    hideMask.defer(250);
                    setTimeout("eopCat.westPanel.expand(false)",(255));
		},
        
         SendCatalogueRequest: function(){
            south.expand(true);
            var serviceRequest=eopCat.formsCatInterfObject.sendXmlKeyValueRequest(
                                        "responsePanelID", 99999,"resources/images/loader.gif");
         },
          // Reset Alphanumeric Filter       
         Reset: function(){
             eopCat.formsCatInterfObject.resetFormValues();
         },
         mapRefresh: function(){
            if(eopCat.map){
                setTimeout("var oldCenter=eopCat.map.getCenter();var oldZoom=eopCat.map.getZoom();eopCat.map.setCenter(oldCenter, 0);eopCat.map.zoomTo(oldZoom);",1000);
            }
        }
	};
}();

var eopCat=ErgorrCatalogues.Application;
Ext.onReady(eopCat.init, ErgorrCatalogues.Application);
 



function setErgoRRURL(){
   
    Ext.getCmp("ServiceUrl").setValue(top.interfacesManager.properties.ergoRRURL+"webservice");
    
    
}