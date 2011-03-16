/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview Basic identify tools implemented as WebGIS.MapAction classes
 */

Ext.namespace('WebGIS', 'WebGIS.MapAction');

/**
 * @class Identifies on clicked position
 * @extends WebGIS.MapAction
 * @required OpenLayers.Control.Identify
 * @param {Object} config WebGIS.MapAction config options<br>
 * {WebGIS.Control.Toc} [toc] Required config option<br>
 * {String/DOM.Element/Ext.Element} [resultTo] Optional config option, if not specified identify will open its result in a floating window
 */
WebGIS.MapAction.Identify = function(config) {
    // default config for this action, also used by button to make it toggle correctly
    config.iconCls = 'webgis-mapaction-identify';
    config.enableToggle = true;
    config.toggleGroup = 'WebGIS.MapAction';
	
    if (typeof(config.resultTo) == 'undefined')
    {
        config.resultTo = new Ext.Window({
            title: config.text,
            closeAction: 'hide',
            width: 300,
            autoHeight: true
        });
    }
	
    // define an OpenLayers control for this MapAction (is handled by MapAction constructor)
  //  config.olcontrol = new OpenLayers.Control.Identify({toc: config.toc, resultTo: config.resultTo});
   config.olcontrol = new OpenLayers.Control.Identify({toc: config.toc, resultTo: config.resultTo});
		   
    // call Ext.Action constructor
    WebGIS.MapAction.Identify.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.Identify, WebGIS.MapAction, {
});
