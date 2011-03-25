

function createHtmlTemplateOperation(templateOperationElement){
  var labelButton,imageButton,imageDimMin,imageDimMax;
  var type=templateOperationElement.getAttribute("type");


  var style,mapObjcetName,layerFillOpacity,layerGraphicOpacity,geometry,attributeGeometry,formatPoint,pointSeparator,mapLayerName;
  var posList,posListDimension,layerTitle,urlAttribute;
  switch(type){
         case "details":
                     var tempString="";
                     var attributes=templateOperationElement.getAttribute("attributes").split(",");
                     var container=templateOperationElement.getAttribute("container");
                     var idAttribute=templateOperationElement.getAttribute("idAttribute");
                     var htmlDetailsLine=templateOperationElement.getAttribute("htmlDetailsLine");
                     var layoutStart=templateOperationElement.getAttribute("htmlLayoutStart");
                     var layoutEnd=templateOperationElement.getAttribute("htmlLayoutEnd");
                     var groups=templateOperationElement.selectNodes("gis:group");
                     var tempHtml, tempHtmlLine, tempAttributes, z;
                     var groupsHtml="";
                       
                     if(groups.length>0){   
                        for(var k=0; k<groups.length;k++){
                            tempAttributes=null; 
                            tempAttributes=groups[k].getAttribute("attributes").split(",");
                            if(tempAttributes.length ==0)
                               tempAttributes[0]=groups[k].getAttribute("attributes");  
                            tempHtmlLine=groups[k].getAttribute("htmlLine");
                            tempHtml=groups[k].getAttribute("htmlStart");
                            for(z=0;z<tempAttributes.length;z++){
                                tempString=tempHtmlLine.replace("$attributeValue",tempAttributes[z]); 
                                tempHtml+=tempString.replace("{$attributeName}", tempAttributes[z]);
                            }
                            tempHtml+=groups[k].getAttribute("htmlEnd");
                            groupsHtml+=tempHtml;  
                        }
                        
                     }
                     var htmlDetails="";
                     var htmlOperation="";

                     htmlDetails+=layoutStart;  
                     
                     for(var i=0; i<attributes.length; i++){
                      tempString=htmlDetailsLine.replace("$attributeValue",attributes[i]); 
                      htmlDetails+=tempString.replace("{$attributeName}", attributes[i]);
                     } 
                     htmlDetails+=groupsHtml+layoutEnd;
                     if(container == "window"){
                        var winWidth=eval(templateOperationElement.getAttribute("winWidth"));
                        var winHeight=eval(templateOperationElement.getAttribute("winHeight"));
                        labelButton=templateOperationElement.getAttribute("labelButton");
                        imageButton=templateOperationElement.getAttribute("imageButton");
                        imageDimMin=templateOperationElement.getAttribute("imageDimMin");
                        imageDimMax=templateOperationElement.getAttribute("imageDimMax");
                        var renderIteratorsFunction="";
                        var showdetailsWindow=/*/"function Details_"+idAttribute+"(){ "+*/
                                  "var htmlDetails_"+idAttribute+"='"+htmlDetails+"';"+
                                  "var win = new Ext.Window({ "+
                                            "title: '({"+idAttribute +"}) Result Details', "+
                                            "border: false, "+
                                            "animCollapse : true, "+
                                            "autoScroll : true, "+
                                            "maximizable: true, "+
                                            "resizable : true, "+
                                            "collapsible: true, "+
                                            "layout: 'fit', "+
                                            "width: "+winWidth+", "+
                                            "height: "+winHeight+", "+
                                            "closeAction:'close', "+
                                            "html: htmlDetails_"+idAttribute+
                                  "}); "+
                                  renderIteratorsFunction+
                                  "win.show();";
                       htmlOperation="<img  title='"+labelButton+"' src='"+imageButton+"' onmouseout=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMin+"';this.height='"+imageDimMin+"';\""+ 
                       " onmouseover=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMax+"';this.height='"+imageDimMax+"';\" width='"+imageDimMin+"'  height='"+imageDimMin+"'"+
                       " onclick=\"javascript:"+showdetailsWindow+"\"/>"+
                       "<img src='style/img/empty.png' width='1'  height='"+imageDimMax+"'/>";
                       
                    }
                    else{
                       if(container == "template"){
                           
                       }  
                     }
                     break;
         case "renderAndZoom":
                      style=templateOperationElement.getAttribute("style");
                      mapObjcetName= templateOperationElement.getAttribute("mapObjcetName");
                      layerFillOpacity= templateOperationElement.getAttribute("layerOpacity");
                      layerGraphicOpacity= templateOperationElement.getAttribute("layerGraphicOpacity");
                      geometry= templateOperationElement.getAttribute("geometry");
                      attributeGeometry= templateOperationElement.getAttribute("attributeGeometry");
                      formatPoint= templateOperationElement.getAttribute("formatPoint");
                      pointSeparator= templateOperationElement.getAttribute("pointSeparator");
                      mapLayerName= templateOperationElement.getAttribute("mapLayerName");
                      posList= templateOperationElement.getAttribute("posList"),
                      posListDimension=templateOperationElement.getAttribute("posListDimension"),

                      labelButton=templateOperationElement.getAttribute("labelButton");
                      imageButton=templateOperationElement.getAttribute("imageButton");
                      imageDimMin=templateOperationElement.getAttribute("imageDimMin");
                      imageDimMax=templateOperationElement.getAttribute("imageDimMax");
                     
                      var pointZoom=templateOperationElement.getAttribute("zoomPoint");
                      if(!(pointZoom || pointZoom!=''))
                        pointZoom=attributeGeometry;
                    
                     var zoomFactor=templateOperationElement.getAttribute("zoomFactor");
                     var renderAndZoomFunction=""+ 
                               "geometryrendering ('{"+attributeGeometry+"}', '"+formatPoint+"', '"+pointSeparator+"', '"+geometry+"', '"+mapLayerName+"', eval("+style+"), '"+mapObjcetName+"', "+
                              "{layerFillOpacity: "+layerFillOpacity+","+
                               "layerGraphicOpacity: "+layerGraphicOpacity+" " + 
                              "},true);"+
                              "zoomTo('{"+pointZoom+"}', '"+formatPoint+"', '"+mapObjcetName+"', '"+zoomFactor+"');";
           
                       htmlOperation="<img  title='"+labelButton+"' src='"+imageButton+"' onmouseout=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMin+"';this.height='"+imageDimMin+"';\""+ 
                       " onmouseover=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMax+"';this.height='"+imageDimMax+"';\" width='"+imageDimMin+"'  height='"+imageDimMin+"'"+
                       " onclick=\"javascript:"+renderAndZoomFunction+"\"/>"+
                       "<img src='style/img/empty.png' width='1'  height='"+imageDimMax+"'/>";
             
                    break;
         case "select":
                      style=templateOperationElement.getAttribute("style");
                      mapObjcetName= templateOperationElement.getAttribute("mapObjcetName");
                      layerFillOpacity= templateOperationElement.getAttribute("layerOpacity");
                      layerGraphicOpacity= templateOperationElement.getAttribute("layerGraphicOpacity");
                      geometry= templateOperationElement.getAttribute("geometry");
                      attributeGeometry= templateOperationElement.getAttribute("attributeGeometry");
                      formatPoint= templateOperationElement.getAttribute("formatPoint");
                      pointSeparator= templateOperationElement.getAttribute("pointSeparator");
                      posList= templateOperationElement.getAttribute("posList"),
                      posListDimension=templateOperationElement.getAttribute("posListDimension"),
                      mapLayerName= templateOperationElement.getAttribute("mapLayerName");
                      layerTitle= templateOperationElement.getAttribute("layerTitle"),

                     labelButton=templateOperationElement.getAttribute("labelButton");
                     imageButton=templateOperationElement.getAttribute("imageButton");
                     imageDimMin=templateOperationElement.getAttribute("imageDimMin");
                     imageDimMax=templateOperationElement.getAttribute("imageDimMax");

                     /*var pointZoom=templateOperationElement.getAttribute("zoomPoint");
                     if(!(pointZoom || pointZoom!=''))
                        pointZoom=attributeGeometry;

                     var zoomFactor=templateOperationElement.getAttribute("zoomFactor");*/
                     var renderSelectFunction=""+
                               "geometryrendering ('{"+attributeGeometry+"}', '"+formatPoint+"', '"+pointSeparator+"', '"+geometry+"', '"+mapLayerName+"', eval("+style+"), '"+mapObjcetName+"', "+
                              "{layerFillOpacity: "+layerFillOpacity+","+
                               "layerGraphicOpacity: "+layerGraphicOpacity+"," +
                               "layerTitle: '"+layerTitle+"' " +
                              "},true,'"+posList+"','"+posListDimension+"');";/*+
                              "zoomTo('{"+pointZoom+"}', '"+formatPoint+"', '"+mapObjcetName+"', '"+zoomFactor+"');";*/

                       htmlOperation="<img  title='"+labelButton+"' src='"+imageButton+"' onmouseout=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMin+"';this.height='"+imageDimMin+"';\""+
                       " onmouseover=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMax+"';this.height='"+imageDimMax+"';\" width='"+imageDimMin+"'  height='"+imageDimMin+"'"+
                       " onclick=\"javascript:"+renderSelectFunction+"\"/>"+
                       "<img src='style/img/empty.png' width='1'  height='"+imageDimMax+"'/>";
                    break;
         case "viewImage":
                      var labelImage=templateOperationElement.getAttribute("labelImage");
                      var attribute=templateOperationElement.getAttribute("attribute");
                      var position=templateOperationElement.getAttribute("position");
                      var bgColor=templateOperationElement.getAttribute("bgColor");
                      imageDimMax=templateOperationElement.getAttribute("maxheight");
                      htmlOperation="<table align='"+position+"'><tr BGCOLOR='"+bgColor+"'><td valign='center'><b>"+labelImage+":</b></td><td><img  height='"+imageDimMax+"'align='"+position+"' title='"+labelImage+"' src='{"+attribute+"}' onclick=\"javascript:"+""+"\"/></p></td></tr></table>";
                    break;
         case "zoomAt":
             
                    break;
         case "getRequestPopup":
                     labelButton=templateOperationElement.getAttribute("labelButton");
                     imageButton=templateOperationElement.getAttribute("imageButton");
                     imageDimMin=templateOperationElement.getAttribute("imageDimMin");
                     imageDimMax=templateOperationElement.getAttribute("imageDimMax");
                     idAttribute=templateOperationElement.getAttribute("idAttribute");
                     urlAttribute=templateOperationElement.getAttribute("urlAttribute");
                     winWidth=eval(templateOperationElement.getAttribute("winWidth"));
                     var xslResponse=templateOperationElement.getAttribute("xslResponse");
                     winHeight=eval(templateOperationElement.getAttribute("winHeight"));
                     var serviceURL=templateOperationElement.getAttribute("serviceURL");
                     var serviceURLVariable=templateOperationElement.getAttribute("serviceURLVariable");


                     if(serviceURLVariable)
                       serviceURL=serviceURLVariable;
                     var getRequest;
                     if(urlAttribute){
                        getRequest="{"+urlAttribute+"}";
                        serviceURL="'ProxyRedirect?url='";
                     }
                     else
                      getRequest="httpservice?request=GetRepositoryItem&service=CSW-ebRIM&version=2.0.2&id={"+idAttribute +"}";


                     var showpopupWindow="var targetURL="+serviceURL+"+'"+getRequest+"&XSLResponse="+xslResponse+"'; "+
                                  "var win = new Ext.Window({ "+
                                            "title: '({"+idAttribute +"}) Result Details', "+
                                            "border: false, "+
                                            "animCollapse : true, "+
                                            "autoScroll : true, "+
                                            "maximizable: true, "+
                                            "resizable : true, "+
                                            "collapsible: true, "+
                                            "bodyStyle : {background: '#ffffff'}, "+
                                            "layout: 'fit', "+
                                            "width: "+winWidth+", "+
                                            "height: "+winHeight+", "+
                                            "closeAction:'close', "+
                                            "autoLoad: {url: targetURL , scripts: true}"+
                                  "}).show();";
                              
                        htmlOperation="<img  title='"+labelButton+"' src='"+imageButton+"' onmouseout=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMin+"';this.height='"+imageDimMin+"';\""+
                       " onmouseover=\"javascript:this.src='"+imageButton+"';this.width='"+imageDimMax+"';this.height='"+imageDimMax+"';\" width='"+imageDimMin+"'  height='"+imageDimMin+"'"+
                       " onclick=\"javascript:"+showpopupWindow+"\"/>"+
                       "<img src='style/img/empty.png' width='1'  height='"+imageDimMax+"'/>";

                    break;
  }       
   
    return(htmlOperation);              
    
}

function createCodeOnLoadOperation(onLoadOperationElement){
    var onLoadOperation=null;
    var type=onLoadOperationElement.getAttribute("type");
  switch(type){
         case "render":
                       onLoadOperation={
                         type: type,
                         vectorLayer: null,
                         style: onLoadOperationElement.getAttribute("style"),
                         mapObjcetName: onLoadOperationElement.getAttribute("mapObjcetName"),
                         layerFillOpacity: onLoadOperationElement.getAttribute("layerOpacity"),
                         layerGraphicOpacity: onLoadOperationElement.getAttribute("layerGraphicOpacity"),
                         geometry: onLoadOperationElement.getAttribute("geometry"),
                         posList: onLoadOperationElement.getAttribute("posList"),
                         posListDimension: onLoadOperationElement.getAttribute("posListDimension"),
                         attributeGeometry: onLoadOperationElement.getAttribute("attributeGeometry"),
                         pointSeparator: onLoadOperationElement.getAttribute("pointSeparator"),
                         formatPoint: onLoadOperationElement.getAttribute("formatPoint"),
                         layerName: onLoadOperationElement.getAttribute("layerName"),
                         //layerName:onLoadOperationElement.getAttribute("layerName"),
                         actionOnLoad: function(pointsString/*, format, separator*/){
                           if(pointsString!=""){
                             var pointsArray;
                             var olPointsArray=new Array();
                             
                              if(this.posList && this.posList!=""){
                                
                                 pointsArray=pointsString.split(" ");
                                 if(this.posListDimension == "2"){
                                   var tempLat,tempLong;
                                   for(i=0; i<pointsArray.length;i=i+2){
                                       tempLat=pointsArray[i];
                                       if(pointsArray[i+1])
                                         tempLong=pointsArray[i+1];
                                       else
                                         tempLong=pointsArray[0];
                                  //    alert(tempLat);
                                    //  alert(tempLong);
                                       olPointsArray.push(new OpenLayers.Geometry.Point(tempLong,tempLat));
                                   }
                                 }
                              }else{
                                 pointsArray=pointsString.split(this.pointSeparator);
                                 if(pointsArray.lenght==0)
                                    pointsArray[0]=pointsString;
                                 var i,latIndex,lonIndex,formatSeparator,tempPointSplit;
                                 var latFormatPosition=this.formatPoint.indexOf('lat');
                                 formatSeparator=this.formatPoint[3];
                                 if(latFormatPosition == 0){
                                    latIndex=0;lonIndex=1;
                                 }else{
                                    latIndex=1;lonIndex=0;
                                 }
                                 for(i=0; i<pointsArray.length;i++){
                                        tempPointSplit=pointsArray[i].split(formatSeparator);
                                        olPointsArray.push(new OpenLayers.Geometry.Point(tempPointSplit[lonIndex], tempPointSplit[latIndex]));
                                 }
                              }

                              var olStyle;
                              if (this.style && this.style!="")
                                  olStyle=this.style;
                              else
                                  olStyle = {
                                    fillColor: "#ee9900",
                                    fillOpacity: 0.4, 
                                    hoverFillColor: "white",
                                    hoverFillOpacity: 0.8,
                                    strokeColor: "#ee9900",
                                    strokeOpacity: 1,
                                    strokeWidth: 1,
                                    strokeLinecap: "round",
                                    hoverStrokeColor: "red",
                                    hoverStrokeOpacity: 1,
                                    hoverStrokeWidth: 0.2,
                                    pointRadius: 6,
                                    hoverPointRadius: 1,
                                    hoverPointUnit: "%",
                                    pointerEvents: "visiblePainted",
                                    cursor: ""
                              }
                              
                              var mapObject=eval(this.mapObjcetName);
                              var layer_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style['default']);
                              if(this.layerFillOpacity)
                                 layer_style.fillOpacity = this.layerFillOpacity;
                              if(this.layerGraphicOpacity)
                                 layer_style.graphicOpacity = this.layerGraphicOpacity;
                              var feature;
                              switch(this.geometry){
                                    case "polygon":
                                            var linearRing = new OpenLayers.Geometry.LinearRing(olPointsArray);
                                            feature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([linearRing]),null,olStyle);
                                            break;
                                    case   "point":
                                            feature = new OpenLayers.Feature.Vector(olPointsArray[0],null,olStyle);
                                            break; 
                                    case    "line":
                                            feature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.LineString(olPointsArray),null,olStyle);
                                            break;          
                              }
                             if(!this.vectorLayer){ 
                                this.vectorLayer=new OpenLayers.Layer.Vector(this.layerName, {style: layer_style});
                                mapObject.addLayer(this.vectorLayer);
                                this.vectorLayer.addFeatures([feature]);
                                
                             }else{
                               this.vectorLayer.addFeatures([feature]);  
                             }
                     
                         }
                       }  
                     }; 
                    
                     
          break;
  }         
  
 return(onLoadOperation);
}


function zoomTo (pointString, formatPoint, mapObjcetName, zoomfactor){
   var mapObj=eval(mapObjcetName);   
   var latFormatPosition=formatPoint.indexOf('lat');
   var latIndex,lonIndex;
   if(latFormatPosition == 0){
          latIndex=0;lonIndex=1; 
       }else{
          latIndex=1;lonIndex=0; 
       }
   var pointSeparator=formatPoint[3];
   var tempPointSplit=pointString.split(pointSeparator);
   var lonLat = new OpenLayers.LonLat(tempPointSplit[lonIndex], tempPointSplit[latIndex]);
   mapObj.setCenter (lonLat, zoomfactor);
}


/*this.style --> style     mapObjcetName--> this.mapObjcetName     this.layerFillOpacity --> layerOptions  this.layerGraphicOpacity-->layerOptions
 *this.geometry --> geometry      this.vectorLayer --> vectorLayer   */
function geometryrendering (pointsString, format, separator, geometry, 
    vectorLayer, style, mapObjcetName, layerOptions, 
    replace, posList, posListDimension){  
       var i,pointsArray;
       var olPointsArray=new Array();
   
       if(separator== 'null'){
          pointsArray=pointsString.split(" ");
          if(posListDimension == "2"){
             var tempLat,tempLong;
             for(i=0; i<pointsArray.length;i=i+2){
                 tempLat=pointsArray[i];
                 if(pointsArray[i+1])
                    tempLong=pointsArray[i+1];
                 else
                    tempLong=pointsArray[0];
                 olPointsArray.push(new OpenLayers.Geometry.Point(tempLong,tempLat));
             }
          }
       }else{

        pointsArray=pointsString.split(separator);
        if(pointsArray.lenght==0)
          pointsArray[0]=pointsString; 
        var latIndex,lonIndex,formatSeparator,tempPointSplit;
        var latFormatPosition=format.indexOf('lat');
        formatSeparator=format[3];
        if(latFormatPosition == 0){
            latIndex=0;lonIndex=1;
        }else{
            latIndex=1;lonIndex=0;
        }
        for(i=0; i<pointsArray.length;i++){
            tempPointSplit=pointsArray[i].split(formatSeparator);
            olPointsArray.push(new OpenLayers.Geometry.Point(tempPointSplit[lonIndex], tempPointSplit[latIndex]));
         }
       }

        var olStyle;
        if (style && style!="")
           olStyle=style;
        else
           olStyle = {
                      fillColor: "#ee9900",
                      fillOpacity: 0.4, 
                      hoverFillColor: "white",
                      hoverFillOpacity: 0.8,
                      strokeColor: "#ee9900",
                      strokeOpacity: 1,
                      strokeWidth: 1,
                      strokeLinecap: "round",
                      hoverStrokeColor: "red",
                      hoverStrokeOpacity: 1,
                      hoverStrokeWidth: 0.2,
                      pointRadius: 6,
                      hoverPointRadius: 1,
                      hoverPointUnit: "%",
                      pointerEvents: "visiblePainted",
                      cursor: ""
                     }
         
         var mapObject=eval(mapObjcetName);
         var layer_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style['default']);
         if(layerOptions.layerFillOpacity)
            layer_style.fillOpacity = layerOptions.layerFillOpacity;
         if(layerOptions.layerGraphicOpacity)
            layer_style.graphicOpacity = layerOptions.layerGraphicOpacity;
         var feature;
         switch(geometry){
                case "polygon":
                      var linearRing = new OpenLayers.Geometry.LinearRing(olPointsArray);
                      feature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([linearRing]),null,olStyle);
                      break;
                case  "point":
                      feature = new OpenLayers.Feature.Vector(olPointsArray[0],null,olStyle);
                      break; 
                case    "line":
                      //alert("line");
                      feature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.LineString(olPointsArray),null,olStyle);
                      break;          
               }
             
      var vectorLayerObj=eval(vectorLayer);
       var setLayerFunction="set_"+vectorLayer+"(new OpenLayers.Layer.Vector('"+layerOptions.layerTitle+"', {style: layer_style}));";
       if(!vectorLayerObj){ 
          eval(setLayerFunction);
          vectorLayerObj=eval(vectorLayer);
          mapObject.addLayer(vectorLayerObj);
          vectorLayerObj.addFeatures([feature]);
       }else{
           if(replace){ 
             mapObject.removeLayer(vectorLayerObj);  
             eval(setLayerFunction);
             vectorLayerObj=eval(vectorLayer);
             mapObject.addLayer(vectorLayerObj);
             vectorLayerObj.addFeatures([feature]);
           }    
           else    
            vectorLayerObj.addFeatures([feature]);  
       }         
    /* var layerName=vectorLayer;

       var vectorLayerObj=mapObject.getLayersByName(layerName)[0];

       if(!vectorLayerObj){
          vectorLayerObj=new OpenLayers.Layer.Vector(layerName, {style: layer_style, displayInLayerSwitcher: layerOptions.displayInLayerSwitcher});
          mapObject.addLayer(vectorLayerObj);
          vectorLayerObj.addFeatures([feature]);
       }else{
           if(replace){
             mapObject.removeLayer(vectorLayerObj);
             vectorLayerObj=new OpenLayers.Layer.Vector(layerName, {style: layer_style, displayInLayerSwitcher: layerOptions.displayInLayerSwitcher});
             mapObject.addLayer(vectorLayerObj);
             vectorLayerObj.addFeatures([feature]);
           }
           else
            vectorLayerObj.addFeatures([feature]);
       }       */    

}













