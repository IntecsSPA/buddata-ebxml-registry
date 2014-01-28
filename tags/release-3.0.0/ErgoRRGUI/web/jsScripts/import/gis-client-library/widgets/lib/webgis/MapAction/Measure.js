/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Author: Albin Lundmark
 *
 * @fileoverview Measure map tools implemented as WebGIS.MapAction classes
 */

Ext.namespace('WebGIS', 'WebGIS.MapAction');

/**
 * @class Activates measuring line tool on map.<br>The tool presumes the map unit to be meters and presents the result in m or km depending on value. If the tool is used on a WGS84 (or other none-meter) map the resulting value will be invalid.
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options<br>
 */
WebGIS.MapAction.MeasureLine = function(config) {
    var handler, nbrOfTips = 0;
    var tip;
	
    config.iconCls = 'webgis-mapaction-measurelength';

    handler = OpenLayers.Handler.Path;

    config.olcontrol = new OpenLayers.Control.DrawFeature(config.layer, handler, 
    { 
        callbacks: { 
            done: function(line) 
            {
                setTip(null);
            },
            point: function(point) 
            {
                var len = this.handler.line.geometry.getLength();
                if(len!=0) {
                    if((len%1000)==len) // is not more than 1 km
                        len = Math.round(len).toString() + ' m';
                    else len = ((Math.round(len*10/1000)/10)).toString() + ' km';
					
                    var tip = new Ext.Tip({html: len, style: "width:150", autoHeight:true});
                    setTip(tip);
						
                    var p = this.map.getViewPortPxFromLonLat(new OpenLayers.LonLat(point.x,point.y))
                    tip.showAt([p.x+2,p.y+2]);
                } 
            },
            cancel: function(line) 
            {
                setTip(null);
            }
        }
    }	 
);
	
    var setTip =function(tip) {
        if(this.tip != undefined)
            this.tip.destroy();
        this.tip = tip;
		
    };

    WebGIS.MapAction.MeasureLine.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.MeasureLine, WebGIS.MapAction, {
});

/**
 * @class Activates measuring area tool on map.<br>The tool presumes the map unit to be meters and presents the result in m2 or km2 depending on value. If the tool is used on a WGS84 map the resulting value will be invalid.
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options<br>
 */
WebGIS.MapAction.MeasureArea = function(config) {
    var handler, nbrOfTips = 0;
    var tip;
	
    config.iconCls = 'webgis-mapaction-measurearea';
	
    handler = OpenLayers.Handler.Polygon;

    config.olcontrol = new OpenLayers.Control.DrawFeature(config.layer, handler, 
    { 
        callbacks: { 
            done: function(area) 
            {
                setTip(null);
            },
            point: function(point) 
            {
                var area= this.handler.polygon.geometry.getArea();
                if(area != 0) {
                    if((area%1000000)==area) // is not more than 1 km2
                        area = Math.round(area).toString() + ' m&#178;';
                    else area = (Math.round(area*10/1000000)/10).toString() + ' km&#178;';
					
                    //var p2 = new Ext.Panel({border: false, width: 140, html: area});		
                    var tip = new Ext.Tip({html: area, style: "width:150", autoHeight:true});
						
                    setTip(tip);
					
                    var p = this.map.getViewPortPxFromLonLat(new OpenLayers.LonLat(point.x,point.y))
                    tip.showAt([p.x+2,p.y+2]);
						
						
                } 
            },
            cancel: function(line) 
            {
                setTip(null);
            }
        }
    }	 
);
	
    var setTip =function(tip) {
        if(this.tip != undefined)
            this.tip.destroy();
        this.tip = tip;
		
    };

    WebGIS.MapAction.MeasureArea.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.MeasureArea, WebGIS.MapAction, {
});
