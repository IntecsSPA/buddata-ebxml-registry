/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview WebGIS.Control.Toc class
 */

Ext.namespace('WebGIS', 'WebGIS.Control');

/**
 * @class A TOC generated from layers in an OpenLayers.Map.
 * All nodes are extended with a property "layer" referencing OL.Layer
 * All nodes are also extended with a property "metadata" that is used to view dynamic metadata on context menu clicks
 * Childnodes on parsed WMS layers have added property "name" that is the short layer name in the WMS service
 * 
 * @extends Ext.tree.TreePanel
 * @param {Object} config Ext.tree.TreePanel config options:<br>
 * {WebGIS.Map} [map] Required config option<br>
 * {boolean} [parseWMS] Optional config option, default is false
 */
WebGIS.Control.Toc = function() {}; 

/**
 * updates the Toc from current associated map layer contents
 */
WebGIS.Control.Toc.prototype.update = function() {};

// actual code
WebGIS.Control.Toc = Ext.extend(Ext.tree.TreePanel, {
	
    // default config options
    rootVisible: false,
    parseWMS: false,
    root: new Ext.tree.TreeNode({draggable:false}),
	
    // constructor
    initComponent: function() {
        WebGIS.Control.Toc.superclass.initComponent.call(this);
    },
	
    // recursive function to fill an Ext.tree.TreeNode with WMS layer information in XML document array form
    // node is an Ext.tree.TreeNode
    // layerinfos is a XML document array with WMS <Layer> tags to parse
    // onContextmenu is a function that handles contextmenu events on the nodes
    fillTree: function(node, layerinfos, layer, root) {

        for (var i = 0; i<layerinfos.length; i++)
        {
            var layerinfo = jQuery(layerinfos[i]);
			
            // use jquery selector function to get the Name-tag text for each layer
            var name = layerinfo.children('Name').text();
            var title = layerinfo.children('Title').text();
			
            var checked = false;
			
            if (layer.params.LAYERS.indexOf(name) != -1) checked = true;
			
            if (root == null) root = node;
            var childNode = new Ext.tree.TreeNode({
                text: title,
                checked: checked
            })
            childNode.name = name;
            childNode.layer = layer;
            childNode.layerIndex = i;
			
            // parse metadata
            childNode.metadata = {};
            childNode.metadata.wms = {};
            childNode.metadata.wms.layer = {};
            childNode.metadata.wms.layer.name = layerinfo.children('Name').text();
            childNode.metadata.wms.layer.title = layerinfo.children('Title').text();
            childNode.metadata.wms.layer.srs = layerinfo.children('SRS').text();
			
            node.appendChild(childNode);
			
            root.subLayers.push({name: name, visibility: checked});
			
            childNode.on("contextmenu", this.onContextmenu, this);
            childNode.on("checkchange", this.onWmsSubLayerCheckChange, root);
			
            // recurse layers child layers
            this.fillTree(node, jQuery(layerinfos[i]).children('Layer'), layer, root);
        }
    },
	
    // generic contextmenu handler
    onContextmenu: function(node, event) {
		
        var showProperties = function(baseitem, event) {
            var layer = baseitem.node;
            var metadata = baseitem.node.metadata;

            var html = "";

            if (typeof(metadata)!='undefined') {
				
                if (typeof(metadata.openlayers)!='undefined') {
                    html += '<h3 class="webgis-metadatalist">' + 'OpenLayers' + '</h3>';
                    html += '<dl class="webgis-metadatalist">'
                    html += '<dt>' + 'Type' + '</dt>';
                    html += '<dd>' + metadata.openlayers.type + '</dd>';
                    html += '<dt>' + 'Source' + '</dt>';
                    html += '<dd>' + metadata.openlayers.source + '</dd>';
                    html += '</dl><br>'
                }
				
                if (typeof(metadata.wms)!='undefined') {
                    if (typeof(metadata.wms.service)!='undefined') {
                        html += '<h3>' + 'WMS Service' + '</h3>';
                        html += '<dl>'
                        html += '<dt>' + 'Name' + '</dt>';
                        html += '<dd>' + metadata.wms.service.name + '</dd>';
                        html += '<dt>' + 'Title' + '</dt>';
                        html += '<dd>' + metadata.wms.service.title + '</dd>';
                        html += '<dt>' + 'Abstract' + '</dt>';
                        html += '<dd>' + metadata.wms.service.abstact + '</dd>';
                        html += '</dl><br>'
                    }
					
                    if (typeof(metadata.wms.layer)!='undefined') {
                        html += '<h3>' + 'WMS Layer' + '</h3>';
                        html += '<dl>'
                        html += '<dt>' + 'Name' + '</dt>';
                        html += '<dd>' + metadata.wms.layer.name + '</dd>';
                        html += '<dt>' + 'Title' + '</dt>';
                        html += '<dd>' + metadata.wms.layer.title + '</dd>';
                        html += '<dt>' + 'SRS' + '</dt>';
                        html += '<dd>' + metadata.wms.layer.srs + '</dd>';
                        html += '</dl><br>'
                    }
                }
            }
		
            var window = new Ext.Window({
                title: this.windowTitleMetadataText,
                border: false,
                width: 400,
                autoHeight: true,
                resizable: false,
                layout: 'fit',
                items: [{
                        cls: 'webgis-metadatalist',
                        border: false,
                        bodyStyle: 'padding:5px',
                        autoWidth: true,
                        autoHeight: true,
                        html: html
                    }]
            });
			
            window.show();
        };

        var menu = new Ext.menu.Menu();
		
        menu.add({
            text: this.contextMenuMetadataText,
            handler: showProperties,
            node: node
        });
		
        menu.showAt(event.getXY());
    },
	
    // standard handler for OL.Layer visibility
    onLayerCheckChange: function(node, checked) {
        this.setVisibility(checked);
    },
	
    // special handler for WMS sublayers visibility
    // creates new params for WMS layer and refreshes it
    onWmsSubLayerCheckChange: function(node, checked) {
        var layers = "";
		
        this.subLayers[node.layerIndex].visibility = checked;
		
        for (var i = 0; i<this.subLayers.length; i++)
        {			
            if (this.subLayers[i].visibility) layers += this.subLayers[i].name + ",";
        }
		
        if (layers == "") {
            this.layer.setVisibility(false);
        }
        else {
            layers = layers.slice(0, -1);
            this.layer.params.LAYERS = layers;
            this.layer.setVisibility(true);
            this.layer.redraw();
        }
		
    },

    // updates the Toc from current associated map layer contents
    // attaches event handling for visibility checkboxes
    update: function() {
        // loop all layers in map and add them to tree as separate nodes on the root
        for (var i=0; i<this.map.layers.length; i++) {
            var layer = this.map.layers[this.map.layers.length-1-i];
			
            var node = new Ext.tree.TreeNode({
                text: layer.name,
                checked: layer.visible
            });
            node.layer = layer;
	
            // parse metadata
            node.metadata = {};
            node.metadata.openlayers = {};
            node.metadata.openlayers.type = layer.CLASS_NAME;
            node.metadata.openlayers.source = layer.url;
			
            node.on("contextmenu", this.onContextmenu, this);
            node.on("checkchange", this.onLayerCheckChange, layer);
            this.getRootNode().appendChild(node);
			
            if (this.parseWMS)
                if (layer.CLASS_NAME=="OpenLayers.Layer.WMS") {
                    node.subLayers = [];
                    this.parseWMSCapabilities(layer, node);
                }
        }
    },
	
    parseWMSCapabilities: function(layer, node) {
		
        // callback function on successful request for GetCapabilities XML
        var success = function(response, options) {	
			
            // run as task (multithreaded)
            var task = {
                run: function(response, tree, node, layers, layer){
                    // get first Layer tag in XML and jquerify it (this is the WMS service layer)
                    var wmslayer = jQuery('Layer:first', response.responseXML);
                    var wmsservice = jQuery('Service:first', response.responseXML);
					
                    // parse name and title for top layer (the WMS service) and set the title as the text on the node
                    options.node.name = wmslayer.children('Name').text();
                    options.node.setText(wmslayer.children('Title').text());
					
                    // parse metadata
                    options.node.metadata.wms = {};
                    options.node.metadata.wms.service = {};
                    options.node.metadata.wms.service.name = wmsservice.children('Name').text();
                    options.node.metadata.wms.service.title = wmsservice.children('Title').text();
                    options.node.metadata.wms.service.abstract = wmsservice.children('Abstract').text();
					
                    // get XML nodes for layers under the top layer
                    var wmssublayers = wmslayer.children('Layer');
					
                    this.fillTree(options.node, wmssublayers, options.layer, null);
                },
                args: [response, options],
                interval: 1,
                repeat: 0,
                scope: this
            }
            Ext.TaskMgr.start(task);
        };

        // callback function on failed request for GetCapabilities XML
        var error = function(response, options) {
            Ext.MessageBox.show({
                title: 'Error',
                msg: 'Could not get WMS GetCapabilities',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
        }

        // make ajax request, report exceptions
        // NOTE: most probable cause for client exception is same origin restriction
        try {
            Ext.Ajax.request({
                url: layer.capabilitiesUrl + '?REQUEST=GetCapabilities&SERVICE=WMS&VERSION=1.1.1',
                scope: this,
                layer: layer,
                node: node,
                disableCaching: false,
                success: success,
                failure: error
            });
        }
        catch (ex) {
            error();
        }
    }
});
