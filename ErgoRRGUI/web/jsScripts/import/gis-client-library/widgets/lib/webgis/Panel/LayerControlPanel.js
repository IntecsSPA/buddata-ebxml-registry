/**

 * 
 * Author: Andrea Marongiu
 *
 * @fileoverview WebGIS.Panel.LayersControl class
 */
var startIamgeWidth='25';
var startIamgeHeight='25';
var imageMenuWidth='25';
var imageMenuHeight='25';
var imageMenuHeightSelect='32';
var imageMenuWidthSelect='32';
var startInfoW=startIamgeWidth;
var startInfoH=startIamgeHeight;


var htmlToolbarsArray;

Ext.namespace('WebGIS', 'WebGIS.Panel');


//WebGIS.Panel.LayersControl = function() {}; 


//WebGIS.Panel.LayersControl.prototype.update = function() {};

var proxyInfo="ProxyRedirect?url=";
WebGIS.Panel.LayersControl = Ext.extend(Ext.Panel, {

imageFolder: null,



    // constructor
    initComponent: function() {
        
        this.updateLayerControl();
        WebGIS.Panel.LayersControl.superclass.initComponent.call(this);
    },
    
    updateLayerControl: function(){
      this.html="<table width='100%'><tr><td>";
      var i,layer,htmlToolbar, rowColor,fontColor;
   
      var urlLegend;
      htmlToolbarsArray=new Array();
      for (i=0;i<this.map.getNumLayers();i++){
          layer=this.map.layers[i];
          if((layer.isBaseLayer == false) && (layer.params)){
            //http://192.168.24.113:8021/geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=topp:states
            if(!layer.options.legendUrl)
                urlLegend=layer.url+"?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&STYLES="+layer.params.STYLES+
                    "&LAYER="+layer.params.LAYERS+"&BBOX="+map.getExtent().toBBOX()+"&WIDTH="+map.size.w+
                      "&HEIGHT="+map.size.h;
            else      
                urlLegend=layer.options.legendUrl;
            //startInfoW=imageMenuWidthSelect;startInfoH=imageMenuWidthSelect;
            htmlToolbar="<img  id='"+layer.name+"Identify' title='\""+layer.name+"\" Identify' src='style/img/identify2.png' onmouseout=\"javascript:this.src='style/img/identify2.png';this.width=startInfoW;this.height=startInfoH;\""+ 
               " onmouseover=\"javascript:this.src='style/img/identify2.png';this.width=imageMenuWidthSelect;this.height=imageMenuWidthSelect;\" width='"+startIamgeWidth+"'  height='"+startIamgeHeight+"'"+
               " onclick=\"javascript:startInfoW=imageMenuWidthSelect;startInfoH=imageMenuWidthSelect;this.width=imageMenuWidthSelect;this.height=imageMenuWidthSelect;getFeatureInfo('"+layer.url+"','"+layer.params.LAYERS+"','"+layer.params.FORMAT+"','"+layer.params.STYLES+"','"+layer.params.SRS+"','"+layer.name+"_workspace','"+layer.name+"Identify');\"/>"+
               " <img  title='Show/Hide \""+layer.name+"\" Legend' src='style/img/legend2.png' onmouseout=\"javascript:this.src='style/img/legend2.png';this.width=imageMenuWidth;this.height=imageMenuHeight;\""+ 
               " onmouseover=\"javascript:this.src='style/img/legend2.png';this.width=imageMenuWidthSelect;this.height=imageMenuWidthSelect;\" width='"+startIamgeWidth+"'  height='"+startIamgeHeight+"'"+
               " onclick=\"javascript:showOrHideImage('"+layer.name+"_legend','"+urlLegend+"','"+layer.options.legendDim+"')\"/>"+
               " <img  title='Remove \""+layer.name+"\" from the map' src='style/img/remove.png' onmouseout=\"javascript:this.src='style/img/remove.png';this.width=imageMenuWidth;this.height=imageMenuHeight;\""+ 
               " onmouseover=\"javascript:this.src='style/img/remove.png';this.width=imageMenuWidthSelect;this.height=imageMenuWidthSelect;\" width='"+startIamgeWidth+"'  height='"+startIamgeHeight+"'"+
               " onclick=\"javascript:removeLayerfromControl("+i+");\"/>"+
               " <img  title='Download layer in KML Format' src='style/img/kml.png' onmouseout=\"javascript:this.src='style/img/kml.png';this.width=imageMenuWidth;this.height=imageMenuHeight;\""+ 
               " onmouseover=\"javascript:this.src='style/img/kml.png';this.width=imageMenuWidthSelect;this.height=imageMenuWidthSelect;\" width='"+startIamgeWidth+"'  height='"+startIamgeHeight+"'"+
               " onclick=\"javascript:exportLayer("+i+",'KML');\"/>"+
               " <img  title='Download layer in GEOTIFF Format' src='style/img/wcsExport.png' onmouseout=\"javascript:this.src='style/img/wcsExport.png';this.width=imageMenuWidth;this.height=imageMenuHeight;\""+ 
               " onmouseover=\"javascript:this.src='style/img/wcsExport.png';this.width=imageMenuWidthSelect;this.height=imageMenuWidthSelect;\" width='"+startIamgeWidth+"'  height='"+startIamgeHeight+"'"+
               " onclick=\"javascript:exportLayer("+i+",'GEOTIFF');\"/>"+
               "<img src='style/img/empty.png' width='1' height='"+imageMenuHeightSelect+"'/>";
            htmlToolbarsArray[i]=htmlToolbar;
            if(i %2 == 0){
              rowColor="#99bbe8";
              fontColor="#000000";
            }else{
              rowColor="#325e8f";
              fontColor="#ffffff";
            }  
            
            this.html+="<table id='"+layer.name+"_table' bgcolor=\""+rowColor+"\" width='100%'><tr>";
            this.html+="<td><img id='"+layer.name+"_imageOptions' onclick=\"javascript:toggleLayerFunctionalities("+i+");\" src='style/img/expand.png' title='Expand layer option'/>";
            this.html+="&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"checkbox\" name=\"vis_"+layer.name+"\" value=\"ON\"";
            if(layer.visibility)
              this.html+="checked='true'";
            this.html+="onclick=\"javascript:map.layers["+i+"].setVisibility(!map.layers["+i+"].visibility);\" /></td>";
            this.html+="<td align='left'><b id='"+layer.name+"_tagName' style='color:"+fontColor+"; font-size: 12px;'>"+layer.name+"</b></td></tr>";
           // this.html+="<tr><td></td><td align='left'>"+htmlToolbar+"</td></tr>";
            this.html+="<tr><td></td><td align='left'><div id='"+layer.name+"_toolbar'></div></td></tr>";
            this.html+="<tr><td colspan=\"2\" align=\"center\"><div id='"+layer.name+"_sliderBar'></div></td></tr>";
            this.html+="<tr><td colspan=\"2\" align=\"center\"><div id='"+layer.name+"_workspace'></div></td></tr>";
            this.html+="<tr><td colspan=\"2\" align=\"center\"><div id='"+layer.name+"_legend'></div></td></tr>";
            this.html+="</table>";
         } 
      }
      this.html+="</td></tr></table>";
    },
    
    showOpacitySliderBar: function(){
        var layer;
        for (var i=0;i<this.map.getNumLayers();i++){
          layer=map.layers[i];
          if((layer.isBaseLayer == false) && (layer.params)){
              new Ext.Slider({
                renderTo: layer.name+"_sliderBar",
                width: 200,
                layerIndex: i,
                id: layer.name+"slider",
                stateId: layer.name+"state",
                value: 1 * 100,
                listeners: {
                    "change": function(el, val) {
                    map.layers[this.layerIndex].setOpacity(val / 100);
                    }
                },
                plugins: new Ext.ux.SliderTip()
              });
         }
        } 
    }

});

function showOrHideImage (elementId, urlImage, dimensions){
    var dim,html="";
    if(urlImage!="null"){
      if(dimensions){
         dim=dimensions.split(',');
         html="WIDTH='"+dim[1]+"' HEIGHT='"+dim[0]+"'";
      }
      var element=document.getElementById(elementId);
      if(element.getElementsByTagName('img').length>0) 
          element.innerHTML=''; 
      else 
         element.innerHTML="<img src=\""+urlImage+"\" "+html+">";
    }
}

function getFeatureInfo(url,layers,format,styles,srs,elementId,imageId){
   // alert(layers);
    map.enebaleMapEvent=false;
    var element=document.getElementById(elementId);
    map.events.register('click', map, function (e) {   
      // toolbarNorth.addFill(); 
      
      //element.innerHTML = "<table width='100%'><tr><td align='center'><img src='style/img/loader/loader.gif'></td></tr><table>";
      var params={
                  REQUEST: 'GetFeatureInfo',
                  EXCEPTIONS: 'application/vnd.ogc.se_xml',
                  BBOX: map.getExtent().toBBOX(),
                  X: e.xy.x,
                  Y: e.xy.y,
                  INFO_FORMAT:'text/html',
                  QUERY_LAYERS: layers,
                  FEATURE_COUNT: 50,
                  Srs: srs,
                  Layers:layers,
                  Styles:styles,
                  WIDTH: map.size.w,
                  HEIGHT: map.size.h,
                  format: format};
              
      var displayInfo=function(response){
      //  alert(elementId);  
        var imageElement=document.getElementById(imageId); 
        if(imageElement){
          imageElement.width=startIamgeWidth;
          imageElement.height=startIamgeHeight;
        }  
        startInfoW=startIamgeWidth;
        startInfoH=startIamgeHeight;
        if(elementId!= ""){
           var element=document.getElementById(elementId);
           if(element){
             elementId=""; 
             var statisticValue,statisticTitle;
            // element.innerHTML = response.responseText; 
             map.enebaleMapEvent=true;
             var xmlResponse = (new DOMParser()).parseFromString(response.responseText, "text/xml");  
             var td=xmlResponse.selectNodes("//td");
             switch(styles){
                 case "mean":
                     if(td.length >0)
                        statisticValue=td[1].firstChild.nodeValue; 
                     else 
                        statisticValue="Error"; 
                     statisticTitle="MEAN: &nbsp;&nbsp;";
                     break;
                 case "stddev":
                     if(td.length >0)
                        statisticValue=td[3].firstChild.nodeValue; 
                     else 
                        statisticValue="Error"; 
                     statisticTitle="STANDARD DEVIATION: &nbsp;&nbsp;";
                     break;   
                 case "bitmask":
                     if(td.length >0)
                        statisticValue=td[2].firstChild.nodeValue; 
                     else 
                        statisticValue="Error"; 
                     statisticTitle="BITMASK: &nbsp;&nbsp;";
                     break;    
             }
             element.innerHTML = "<table><tr bgcolor='#FFFFFF'><td colspan='2' align='center'><b>Query Informations </b><td></tr><tr><td bgcolor='#FFFFFF'}>"+statisticTitle+"</td><td bgcolor='#FFFFFF'>"+ statisticValue+"</td></tr></table>";
           }
        }
      }        
      OpenLayers.loadURL(proxyInfo+url, params, this, displayInfo, displayInfo);
      OpenLayers.Event.stop(e);
   });
   
}

function removeLayerfromControl(layerIndex){
  map.removeLayer(map.layers[layerIndex]);
  layerControl.destroy();
  layerControl= new WebGIS.Panel.LayersControl({map: map});
  layerControl.render(document.getElementById('layerControlWindow'));
}

function changeColorLayerfromControl(layerIndex, tabColor){
    var layer=map.layers[layerIndex];
    var elementTagTable=document.getElementById(layer.name+"_table");
    for(var i=0; i<elementTagTable.rows.length; i++)
        for(var u=0; u<elementTagTable.rows[i].cells.length; u++)
          elementTagTable.rows[i].cells[u].style.backgroundColor = tabColor;
}

function toggleLayerFunctionalities(layerIndex){
    var layer=map.layers[layerIndex]; 
    var layerElement=document.getElementById(layer.name+"_toolbar");
    var layerElementSlider=document.getElementById(layer.name+"_sliderBar");
    var layerElementInfo=document.getElementById(layer.name+"_workspace");
    var layerElementLegend=document.getElementById(layer.name+"_legend");
    var imageLayerOptions=document.getElementById(layer.name+"_imageOptions");
    if(layerElement.getElementsByTagName('img').length > 0){
      layerElement.innerHTML="";
      layerElementSlider.innerHTML="";
      layerElementInfo.innerHTML="";
      layerElementLegend.innerHTML="";
      imageLayerOptions.src='style/img/expand.png';
      imageLayerOptions.title='Expand layer options';
    }else{
      layerElement.innerHTML=htmlToolbarsArray[layerIndex];
      new Ext.Slider({
                renderTo: layer.name+"_sliderBar",
                width: 200,
                layerIndex: layerIndex,
                id: layer.name+"slider",
                stateId: layer.name+"state",
                value: 1 * 100,
                listeners: {
                    "change": function(el, val) {
                    map.layers[this.layerIndex].setOpacity(val / 100);
                    }
                },
                plugins: new Ext.ux.SliderTip()
              });
       imageLayerOptions.src='style/img/contract.png';  
       imageLayerOptions.title='Close layer options';
    }  
}

// http://192.168.24.169:8080/geoserver/ows?SERVICE=WCS&amp;VERSION=1.0.0&amp;REQUEST=GetCoverage&amp;COVERAGE=topp:salt_m08_nest0_20080925_0@salinity&amp;CRS=EPSG:4326&amp;FORMAT=GEOTIFF&amp;WIDTH=500&amp;HEIGHT=500&amp;BBOX=0,30,20,50,0,0&amp;TIME=2008-09-25T02:00:00.000+0200
// http://192.168.24.113:8021/geoserver/wms?bbox=-23.752160893611112,16.00836255805556,48.66971910638889,58.19586255805556&styles=&Format=application/openlayers&request=GetMap&version=1.1.1&layers=topp:WMSInterpolatedLayer_28112008180001137&width=800&height=437&srs=EPSG:4326
function exportLayer(layerIndex, format){
    
  var layer=map.layers[layerIndex];  
  var emInput=formsEnsembleDataObject.getFormValues();
 
  var request="";
  switch (format){
      case 'KML':
            /*/request+=layer.url+"?SERVICE=WMS&request=GetMap&version=1.1.1&srs=EPSG:4326&styles="+layer.params.STYLES;
            request+="&BBOX="+layer.options.modelAOI+"&Format=application/vnd.google-earth.kml xml&layers="+layer.params.LAYERS;
            request+="&HEIGHT="+layer.options.modelHeight+"&WIDTH="+layer.options.modelWidth;*/
            request+=layer.url+"/kml_reflect?layers="+layer.params.LAYERS+"&styles="+layer.params.STYLES;
          break;
      case 'GEOTIFF':
            request=layer.url.substr(0,layer.url.length-3);
            request+="ows?BBOX="+layer.options.modelAOI+"&SERVICE=WCS&VERSION=1.0.0&REQUEST=GetCoverage&CRS=EPSG:4326&FORMAT=GEOTIFF";
            request+="&COVERAGE="+layer.params.LAYERS+"&HEIGHT="+layer.options.modelHeight+"&WIDTH="+layer.options.modelWidth;
          break;    
  }  
//Ext.Msg.alert('Debug', "Request: " + request);
window.location=request;  
}

/**
 * @class Ext.ux.SliderTip
 * @extends Ext.Tip
 * Simple plugin for using an Ext.Tip with a slider to show the slider value
 */
Ext.ux.SliderTip = Ext.extend(Ext.Tip, {
    minWidth: 10,
    offsets : [0, -10],
    init : function(slider){
        slider.on('dragstart', this.onSlide, this);
        slider.on('drag', this.onSlide, this);
        slider.on('dragend', this.hide, this);
        slider.on('destroy', this.destroy, this);
    },

    onSlide : function(slider){
        this.show();
        this.body.update(this.getText(slider));
        this.doAutoWidth();
        this.el.alignTo(slider.thumb, 'b-t?', this.offsets);
    },

    getText : function(slider){
        return "Opacity: " +slider.getValue()/100;
    }
});

/*
**
*
**/
