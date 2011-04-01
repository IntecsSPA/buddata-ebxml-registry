

Ext.namespace('ErgorrCatalogues');

var selectionLayer=null;
var proxyRedirect="ProxyRedirect";
var timeOutRequestCatalog=500;
var formsCatInterfObject;
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
           westPanel: null,
           formsCatInterfObject: null,
           cimXMLInterfaceUrl: "resources/xml/interfaces/CSW-ebRIM-CIM_Profile-noloc.xml",
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
                                  listeners: {
                                    expand: function(){

                                    },
                                    collapse: function(){
                                        cimCat.mapRefresh();
                                    },
                                    resize: function(){
                                        cimCat.mapRefresh();
                                    }
                                  },
                                  autoScroll : true,
                                  margins:'0 0 0 0', 
                                  html: "<div id='responsePanelID'></div>" 
                  });
                 
                  
                  
                  cimCat.westPanel =new Ext.Panel({region:'west',
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
                                        cimCat.mapRefresh();
                                    },
                                    resize: function(){
                                       if(cimCat.formsCatInterfObject)
                                            cimCat.formsCatInterfObject.render();
                                        cimCat.mapRefresh();
                                       
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
                            items:[cimCat.westPanel,south,mapTool]
                      });
                       cimCat.map = new OpenLayers.Map(panel.body.dom, mapOptions); 
                       
                      
                       cimCat.formsCatInterfObject=createPanelExjFormByXml(cimCat.cimXMLInterfaceUrl);
                       cimCat.formsCatInterfObject.formsPanel.render(document.getElementById("formcataloguePanelID"));
                       cimCat.formsCatInterfObject.render();     
                       
                       var formatWMC = new OpenLayers.Format.WMC(mapOptions);
                       var contextMap = new XmlDoc("resources/xml/wmc/WebMapContext.xml").xmlText;
            
                       cimCat.map = formatWMC.read(contextMap, {map: cimCat.map});       
                       cimCat.map.zoomToMaxExtent();
                     
                       
                       // standard Open Layers
                        cimCat.map.addControl(new OpenLayers.Control.MousePosition());
			cimCat.map.addControl(new OpenLayers.Control.MouseDefaults());
	
			cimCat.map.addControl(new OpenLayers.Control.LayerSwitcher());
                        var scalebar = new OpenLayers.Control.ScaleBar();
                        cimCat.map.addControl(scalebar);
                        
			toolbar.add(new WebGIS.MapAction.DragPan({map: cimCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomInBox({map: cimCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomOutBox({map: cimCat.map}));
			toolbar.add(new WebGIS.MapAction.ZoomIn({map: cimCat.map}));                    
			toolbar.add(new WebGIS.MapAction.ZoomOut({map: cimCat.map}));
			toolbar.add(new WebGIS.MapAction.PreviousExtent({map: cimCat.map}));
			toolbar.add(new WebGIS.MapAction.NextExtent({map: cimCat.map}));
			toolbar.add(new WebGIS.MapAction.FullExtent({map: cimCat.map}));
                        
                        toolbar.add(new WebGIS.MapAction.Scale({map: cimCat.map, pathGisClient:"jsScripts/import/gis-client-library/"}));
                      
                      cimCat.westPanel.collapse(false);
                      
                     
                      setErgoRRURL();
                         var hideMask = function () {
                            Ext.get('loading').remove();
                            Ext.fly('loading-mask').fadeOut({
                                remove:true/*,
                                callback : west.expand*/
                            });
                        }

                        hideMask.defer(250);
                        setTimeout("cimCat.westPanel.expand(false)",(255));
		},
        
         SendCatalogueRequest: function(){
            south.expand(true);
            var serviceRequest=cimCat.formsCatInterfObject.sendXmlKeyValueRequest(
                                        "responsePanelID", 99999,"resources/images/loader.gif");
         },
          // Reset Alphanumeric Filter       
         Reset: function(){
             cimCat.formsCatInterfObject.resetFormValues();
         },
         
         mapRefresh: function(){

            if(cimCat.map){
                setTimeout("var oldCenter=cimCat.map.getCenter();var oldZoom=cimCat.map.getZoom();cimCat.map.setCenter(oldCenter, 0);cimCat.map.zoomTo(oldZoom);",1000);
            }

        }
	};
}();

var cimCat=ErgorrCatalogues.Application;
Ext.onReady(cimCat.init, ErgorrCatalogues.Application);
 



function setErgoRRURL(){
   
    Ext.getCmp("ServiceUrl").setValue(top.interfacesManager.properties.ergoRRURL+"webservice");
    
    
}