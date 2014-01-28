/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview OpenLayers.Layer.Xepto class
 */

/**
 * @class WebGISTileServer
 * @extends OpenLayers.Layer.Grid
 * @requires OpenLayers/Layer/Grid.js
 */
OpenLayers.Layer.WebGISTileServer = function() {}; // jsdoc parser workaround

WebGISTileServer = new Object();
WebGISTileServer.Provider = OpenLayers.Class.create();
/*WebGISTileServer.Provider.prototype = {
    SCALES: [20,40,120,360,2500,4200,22000,46000,108000],
    MINX: [-1336,-1336,-7336,16664,-950488,-1160488,-3200488,-200488,-14710625],
    MINY: [-378714,-19257714,-19263714,-4299714,-175193,-520193,-2050193,-3250193,-9446908],
    MAXX: [5641664,11404664,11404664,7792664,5049512,5139512,6699512,6699512,17689375],
    MAXY: [5984286,4718286,4748286,4826286,5074807,5149807,7849807,10549807,6753092],
    SIZES: [500,1000,3000,9000,62500,105000,550000,1150000,2700000],
     
    map_options: {
        resolutions: [500/250,1000/250,3000/250,9000/250,62500/250,105000/250,550000/250,1150000/250,2700000/250],
        maxExtent: new OpenLayers.Bounds(-14710625, -9446908, 17689375,6753092),
        units: "meters"
    },

    defaultLatitude: 6404510, defaultLongitude: 1271140,
    providerId: 1,
    
    initialize: function()
    {
        
    },
    
    CLASS_NAME: "WebGISTileServer.Provider"
};*/

OpenLayers.Layer.WebGISTileServer = OpenLayers.Class(OpenLayers.Layer.Grid, {
		
    // overridden config options
    isBaseLayer: true,
    tileSize: new OpenLayers.Size(250, 250),
    
    MIN_ZOOM_LEVEL: 0,
    MAX_ZOOM_LEVEL: 8,

    	
    /**
     * @constructor
     *
     * @param {String} name Name of layer
     * @param {String} token authenticated WebGISTileServer service token
     * @param {String} url URL to WebGISTileServer service
     * @param {String} providerName WebGISTileServer service specific provider name
     */
    initialize: function(name, url, providerName) {
        var newArguments = new Array();
        newArguments.push(name, url, {}, providerName);
		
        OpenLayers.Layer.Grid.prototype.initialize.apply(this, newArguments);

        // TODO: add all supported providers
        if (providerName == "LMVSE")
        {
            if (typeof(WebGISTileServer.Provider.LMVSE) == 'undefined')
            {
                throw this.providerErrorText;
            }
			
            this.provider = new WebGISTileServer.Provider.LMVSE();
        }
        else {
            throw this.providerNameErrorText;
        }

        if (typeof(OpenLayers.Layer.WebGISTileServer.GetToken) != 'undefined') {
            this.token = OpenLayers.Layer.WebGISTileServer.GetToken();
        }
        else {
            throw this.tokenFunctionErrorText;
        }
		

    },

    // overridden to set maxextent and tileorigin for each zoomlevel
    initGriddedTiles: function(bounds) {
        var z = this.MAX_ZOOM_LEVEL-this.map.getZoom();
        this.maxExtent = new OpenLayers.Bounds(this.provider.MINX[z],this.provider.MINY[z],this.provider.MAXX[z],this.provider.MAXY[z]);    	
        this.tileOrigin = new OpenLayers.LonLat(this.provider.MINX[z], this.provider.MINY[z]);
		
        OpenLayers.Layer.Grid.prototype.initGriddedTiles.apply(this, arguments);
    },
		
    getURL: function (bounds) {
        var res = this.map.getResolution();
        var z = this.MAX_ZOOM_LEVEL-this.map.getZoom();
			
        var x = Math.floor((bounds.left - this.tileOrigin.lon) / (res * this.tileSize.w));
        var y = Math.floor((bounds.bottom - this.tileOrigin.lat) / (res * this.tileSize.h));
			
        // xepto seem to have reverse y tile ordering
        var tilemax = (this.provider.MAXY[z]-this.provider.MINY[z])/(this.provider.SIZES[z]);
        y = tilemax-1-y;

        return this.url + "?token="+ this.token + "&zoomlevel=" + z + "&x=" + x + "&y=" + y; 
    },

    addTile: function(bounds,position) {
        return new OpenLayers.Tile.Image(this, position, bounds, null, this.tileSize);
    },

    /** @final @type String */
    CLASS_NAME: "OpenLayers.Layer.WebGISTileServer"
});
