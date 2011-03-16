

Ext.namespace('ErgorrCatalogues');

var selectionLayer=null;
var proxyRedirect="ProxyRedirect";
var timeOutRequestCatalog=500;
var screenWidth=screen.availWidth;
var screenHeight=screen.availHeight;
var formsCatInterfObject;
var map;

function set_selectionLayer(newValue){ 
  selectionLayer=newValue;
}

ErgorrCatalogues.Application = function()
{
  var toolbar,toolbarSouth;
  var formsObject;
  var south=null, west=null;
  var mapOptions = {
        maxResolution: 1.40625/2,
        enebaleMapEvent: true
  };
  return { 
           map: null,
      
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
                                  height: 300,
                                  minSize: 200,
                                  maxSize: 700,
                                  collapsible: true,
                                  collapsed : true,
                                  title:'Response',
                                  autoScroll : true,
                                  margins:'0 0 0 0', 
                                  html: "<div id='responsePanelID'></div>" 
                  });
                 
                  var xmlDocumentUrl="resources/xml/interfaces/CSW-ebRIM-EO_Profile-noloc.xml";
                  formsCatInterfObject=createPanelExjFormByXml(xmlDocumentUrl);

                 

                  west =new Ext.Panel({region:'west',
                                  split:true,
                                  width: (screenWidth/100)*32,
                                  //minSize: (screenWidth/100)*30,
                                  maxSize: (screenWidth/100)*50,
                                  collapsible: true,
                                  collapsed : false,
                                  title:'Catalogue Interfaces',
                                  bodyStyle : {background: '#99bbe8'},
                                  autoScroll : true,
                                  margins:'0 0 0 0',
                                  items:[formsCatInterfObject.formsPanel]
                         
                  });
                  
		
                      var mapTool=new Ext.Panel({
                          region: 'center',
                          layout:'anchor',
                          anchorSize: {width:1280, height:1024},
                          items:[panel,southToolbarPanel]
                      });
                      
                      var viewport = new Ext.Viewport({
                            layout:'border',
                            items:[west,south,mapTool]
                      });
                       eopCat.map = new OpenLayers.Map(panel.body.dom, mapOptions); 
                        formsCatInterfObject.formsPanel.render(document.getElementById("cataloguePanel"));
                       formsCatInterfObject.render();     
                       
                       var formatWMC = new OpenLayers.Format.WMC(mapOptions);
                       var contextMap = Sarissa.getDomDocument();
                       contextMap.async=false;
                       contextMap.validateOnParse=false;
                       contextMap.load("resources/xml/wmc/WebMapContext.xml");
            
                       eopCat.map = formatWMC.read(contextMap, {map: eopCat.map});       
                       eopCat.map.zoomToMaxExtent();
                     
                       //var toc = new WebGIS.Control.Toc({map: eopCat.map, parseWMS: false, autoScroll: true});
                       
                       // standard Open Layers
                        eopCat.map.addControl(new OpenLayers.Control.MousePosition());
			eopCat.map.addControl(new OpenLayers.Control.MouseDefaults());
	
			eopCat.map.addControl(new OpenLayers.Control.LayerSwitcher());
      
			// map action is an extended Ext.Action that can be used as a button or menu item
                        
			toolbar.add(new WebGIS.MapAction.DragPan({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomInBox({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomOutBox({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomIn({map: eopCat.map}));                    
			toolbar.add(new WebGIS.MapAction.ZoomOut({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.PreviousExtent({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.NextExtent({map: eopCat.map}));
			toolbar.add(new WebGIS.MapAction.FullExtent({map: eopCat.map}));
                        
                        toolbar.add(new WebGIS.MapAction.Scale({map: eopCat.map, pathGisClient:"jsScripts/import/gis-client-library/"}));
                      
                      
		},
        
         SendCatalogueRequest: function(){
            south.expand(true);
            //sendXmlKeyValueRequest: function(renderObjectId, proxyRedirect, async, timeOut, returnRootPath, fixRequest, blockBar, loadingBarImg, ddGroup, layerName, styleLayer)
            var serviceRequest=formsCatInterfObject.sendXmlKeyValueRequest(
                                        "responsePanelID", 99999,"resources/images/loader.gif");
         },
          // Reset Alphanumeric Filter       
         Reset: function(){
             formsCatInterfObject.resetFormValues();
         }
	};
}();

var eopCat=ErgorrCatalogues.Application;
Ext.onReady(eopCat.init, ErgorrCatalogues.Application);
 




