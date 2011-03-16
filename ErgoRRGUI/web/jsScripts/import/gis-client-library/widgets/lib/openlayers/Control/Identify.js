/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview OpenLayers.Control.Identify extension
 */

/**
 * @class Handles indentify clicks on map, works with WMS service only and is affected by same origin policy
 * @extends OpenLayers.Control
 * @requires OpenLayers/Control.js
 * @requires OpenLayers/Handler/Click.js
 */
OpenLayers.Control.Identify = function() {}; // jsdoc parser workaround
OpenLayers.Control.Identify = OpenLayers.Class(OpenLayers.Control, {
    /**
     * Property: type
     * {OpenLayers.Control.TYPE}
     */
    type: OpenLayers.Control.TYPE_TOOL,

    /**
     * Overriden constructor. Takes care of handler initialization.
     */
    initialize: function(options) {
        OpenLayers.Control.prototype.initialize.apply(this, [options]);
		
        this.handler = new OpenLayers.Handler.Click(this, {click: this.identify});
    },

    /**
     * Method: identify
     *
     * Parameters:
     * event - map click event
     */
    identify: function (event) {
        var node = this.toc.getSelectionModel().getSelectedNode();
		
        if (node == null) {
            Ext.MessageBox.show({
                title: 'Information',
                msg: 'Requires selected layer in a WMS service',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });	
		
            return;
        }
	
	
        if (node.layer.CLASS_NAME != 'OpenLayers.Layer.WMS') {
            Ext.MessageBox.show({
                title: 'Information',
                msg: 'Can only identify on WMS layers',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });	
		
            return;
        }
		
        var url =  node.layer.getFullRequestString({
            REQUEST: "GetFeatureInfo",
            EXCEPTIONS: "application/vnd.ogc.se_xml",
            BBOX: node.layer.map.getExtent().toBBOX(),
            X: event.xy.x,
            Y: event.xy.y,
            //INFO_FORMAT: 'text/text',
            INFO_FORMAT: 'text/html',
            QUERY_LAYERS: node.name,
            WIDTH: node.layer.map.size.w,
            HEIGHT: node.layer.map.size.h});
		
        try {
            Ext.Ajax.request({
                url: url,
                scope: this,
                success: function(response, options) {
                    var html = response.responseText;
	
                    this.resultTo.show();
                    this.resultTo.body.update(html);
                },
                failure: function(response, options) {
                    Ext.MessageBox.show({
                        title: 'Error',
                        msg: 'Could not complete GetFeatureInfo request',
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.ERROR
                    });
                }
            });
        }
        catch (ex) {
            Ext.MessageBox.show({
                title: 'Error',
                msg: 'Exception occured in AJAX-request.',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
		
    },

    CLASS_NAME: "OpenLayers.Control.Identify"
});
