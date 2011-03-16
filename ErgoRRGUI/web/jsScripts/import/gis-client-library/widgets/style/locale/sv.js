/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview Swedish locale for all WebGIS public classes
 */

// Global resources
WebGIS.Locale = {};
WebGIS.Locale.errorText = "Felmeddelande";

// WebGIS.Map
Ext.apply(WebGIS.Control.Map.prototype, {
	configErrorText: 'Kunde ej ladda kartkonfiguration'
});

// WebGIS.Toc
Ext.apply(WebGIS.Control.Toc.prototype, {
	windowTitleMetadataText: 'Metadata för lagret',
	contextMenuMetadataText: 'Visa metadata'
});

// WebGIS.Control.ScaleList
Ext.apply(WebGIS.Control.ScaleList.prototype, {
	configErrorText: 'Requires defined resolutions array in map configuration'
});

// OpenLayers.Layer.Xepto
Ext.apply(OpenLayers.Layer.WebGISTileServer.prototype, {
	tokenFunctionErrorText: 'OpenLayers.Layer.Xepto.GetToken() är ej definierad'
});

// Basic tools
Ext.apply(WebGIS.MapAction.ZoomInBox.prototype, {
	titleText: 'Zooma in',
	tooltipText: 'Zooma in genom att rita en rektangel'
});
Ext.apply(WebGIS.MapAction.ZoomOutBox.prototype, {
	titleText: 'Zooma ut',
	tooltipText: 'Zooma ut genom att rita en rektangel'
});
Ext.apply(WebGIS.MapAction.ZoomIn.prototype, {
	titleText: 'Zooma in',
	tooltipText: 'Zooma in ett steg'
});
Ext.apply(WebGIS.MapAction.ZoomOut.prototype, {
	titleText: 'Zooma ut',
	tooltipText: 'Zooma ut ett steg'
});
Ext.apply(WebGIS.MapAction.FullExtent.prototype, {
	titleText: 'Visa hela kartan',
	tooltipText: 'Visa hela kartan'
});
Ext.apply(WebGIS.MapAction.DragPan.prototype, {
	titleText: 'Panorera',
	tooltipText: 'Panorera genom att dra kartan'
});
Ext.apply(WebGIS.MapAction.PreviousExtent.prototype, {
	titleText: 'Föregående vy',
	tooltipText: 'Gå till föregående vy'
});
Ext.apply(WebGIS.MapAction.NextExtent.prototype, {
	titleText: 'Nästa vy',
	tooltipText: 'Gå till nästa vy'
});
Ext.apply(WebGIS.MapAction.NextExtent.prototype, {
	titleText: 'Nästa vy',
	tooltipText: 'Gå till nästa vy'
});

// Editor tools
Ext.apply(WebGIS.MapAction.DrawFeature.prototype, {
	titlePointText:			'Rita punkt',
	tooltipPointText:		'Rita punkt',
	titleCurveText:			'Rita linje',
	tooltipCurveText:		'Rita linje',
	titlePolygonText:		'Rita yta',
	tooltipPolygonText:	'Rita yta'
});
Ext.apply(WebGIS.MapAction.SelectFeature.prototype, {
	titleText: 'Markera geometri',
	tooltipText: 'Markera geometri'
});
Ext.apply(WebGIS.MapAction.ModifyFeature.prototype, {
	titleText: 'Modifiera geometri',
	tooltipText: 'Modifiera geometri'
});
Ext.apply(WebGIS.MapAction.DragFeature.prototype, {
	titleText: 'Flytta geometri',
	tooltipText: 'Flytta geometri'
});

// Measure tools
Ext.apply(WebGIS.MapAction.MeasureLine.prototype, {
	titleText: 'Mät avstånd',
	tooltipText: 'Mät avstånd genom att rita en linje'
});
Ext.apply(WebGIS.MapAction.MeasureArea.prototype, {
	titleText: 'Mät area',
	tooltipText: 'Mät area genom att rita en polygon'
});

// Identify tools
Ext.apply(WebGIS.MapAction.Identify.prototype, {
	titleText: 'Identifiera',
	tooltipText: 'Visar attribut för geometri från en punkt i kartan (endast WMS-lager)'
});
