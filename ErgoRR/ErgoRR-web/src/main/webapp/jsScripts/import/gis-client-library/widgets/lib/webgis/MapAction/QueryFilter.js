Ext.namespace('WebGIS', 'WebGIS.MapAction');

/**
 
 */
WebGIS.MapAction.FilterPanel = function(config) {

      var filterWindow = new Ext.Window({
				title: 'Query Filter',
				border: false,
                                collapsible: true,
				layout: 'fit',
				width: 400,
				height: 600,
				contentEl: "filterExample"
			})
			filterWindow.show();
			filterWindow.setPosition(400,400);

    // use config parameter to determine geometry type to handle
    if (config.geometryType=='OpenLayers.Geometry.Point') {
        handler = OpenLayers.Handler.Point;
        config.iconCls = 'webgis-mapaction-drawpoint';
        config.text = this.titlePointText;
        config.tooltip = this.tooltipPointText;
    }
    else if (config.geometryType=='OpenLayers.Geometry.Curve') {
        handler = OpenLayers.Handler.Path;
        config.iconCls = 'webgis-mapaction-drawline';
        config.text = this.titleCurveText;
        config.tooltip = this.titleCurveText;
    }
    else if (config.geometryType=='OpenLayers.Geometry.Polygon') {
        handler = OpenLayers.Handler.Polygon;
        config.iconCls = 'webgis-mapaction-drawpolygon';
        config.text = this.titlePolygonText;
        config.tooltip = this.tooltipPolygonText;
    }

    config.olcontrol = new OpenLayers.Control.DrawFeature(config.layer, handler);

    WebGIS.MapAction.DrawFeature.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.DrawFeature, WebGIS.MapAction, {
});


/**
 * @class Selects a feature on specified layer, and saves the feature in that layer.
 * @extends WebGIS.MapAction
 * @param {String} config WebGIS.MapAction config options<br>
 {OpenLayers.Layer.Vector} [layer] Required config option<br>
 */
WebGIS.MapAction.SelectFeature = function(config) {
    config.iconCls = 'webgis-mapaction-selectfeature';
    config.olcontrol = new OpenLayers.Control.SelectFeature(config.layer, {hover: false, 
        onSelect: function (feature) {
            // Saves the marked fature in the layer
            config.layer.markedFeature = feature;
			
            // If a function should be called after the a marked feature is set, do that. 
            if (config.serialize != null)
                config.serialize(feature);
        }, 
        onUnselect : function (feature) {
            // Removes the marked feature in the layer
            config.layer.markedFeature = null;
        }
    });

    WebGIS.MapAction.SelectFeature.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.SelectFeature, WebGIS.MapAction, {
});

/**
 * @class Activates feature modification on specified layer.
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options<br>
 {OpenLayers.Layer.Vector} [layer] Required config option<br>
 */
WebGIS.MapAction.ModifyFeature = function(config) {
    config.iconCls = 'webgis-mapaction-modifyfeature';
    config.olcontrol = new OpenLayers.Control.ModifyFeature(config.layer);

    WebGIS.MapAction.ModifyFeature.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.ModifyFeature, WebGIS.MapAction, {
});

/**
 * @class Activates feature draggin on specified layer.
 * @extends WebGIS.MapAction
 * @param {Object} config WebGIS.MapAction config options<br>
 {OpenLayers.Layer.Vector} [layer] Required config option<br>
 */
WebGIS.MapAction.DragFeature = function(config) {
    config.iconCls = 'webgis-mapaction-dragfeature';
    config.olcontrol = new OpenLayers.Control.DragFeature(config.layer);

    WebGIS.MapAction.DragFeature.superclass.constructor.call(this, config);
}
Ext.extend(WebGIS.MapAction.DragFeature, WebGIS.MapAction, {
});

