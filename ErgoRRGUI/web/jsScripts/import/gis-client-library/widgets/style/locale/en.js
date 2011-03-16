/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview English locale for all WebGIS public classes
 */

// Global resources
WebGIS.Locale = {};
WebGIS.Locale.errorText = "Error message";

// WebGIS.Map
/*Ext.apply(WebGIS.Control.Map.prototype, {
	configErrorText: 'Could not load map configuration'
});*/

// WebGIS.Toc
Ext.apply(WebGIS.Control.Toc.prototype, {
	windowTitleMetadataText: 'Metadata for this layer',
	contextMenuMetadataText: 'Show metadata'
});

// WebGIS.Control.ScaleList
Ext.apply(WebGIS.Control.ScaleList.prototype, {
	configErrorText: 'Requires defined resolutions array in map configuration'
});

// Basic tools
Ext.apply(WebGIS.MapAction.ZoomInBox.prototype, {
	titleText: 'Zoom in',
	tooltipText: 'Zoom in by drawing a rectangle'
});
Ext.apply(WebGIS.MapAction.ZoomOutBox.prototype, {
	titleText: 'Zoom out',
	tooltipText: 'Zooma out by drawing a rectangle'
});
Ext.apply(WebGIS.MapAction.ZoomIn.prototype, {
	titleText: 'Zoom in',
	tooltipText: 'Zooma in one step'
});
Ext.apply(WebGIS.MapAction.SelectionWMS.prototype, {
	titleText: 'Select',
	tooltipText: 'Select a WMS Selectable Layers '
});
Ext.apply(WebGIS.MapAction.SelectAOI.prototype, {
	titleText: 'Set AOI',
	tooltipText: 'Select Area of Interest'
});
Ext.apply(WebGIS.MapAction.Scale.prototype, {
        titleText: 'Scale',
        tooltipText: 'Open/Close Scale Panel'
}); 

Ext.apply(WebGIS.MapAction.SimpleLegend.prototype, {
        titleText: 'Legend',
        tooltipText: 'Open/Close Legend Panel'
}); 

Ext.apply(WebGIS.MapAction.RemoveSelectionWMS.prototype, {
	titleText: 'UnSelect',
	tooltipText: 'Remove a WMS layers selection'
});
Ext.apply(WebGIS.MapAction.ZoomOut.prototype, {
	titleText: 'Zoom out',
	tooltipText: 'Zooma out one step'
});
Ext.apply(WebGIS.MapAction.FullExtent.prototype, {
	titleText: 'Full extent',
	tooltipText: 'Zoom to the the full map extent'
});
Ext.apply(WebGIS.MapAction.DragPan.prototype, {
	titleText: 'Pan',
	tooltipText: 'Pan by dragging the map'
});
Ext.apply(WebGIS.MapAction.PreviousExtent.prototype, {
	titleText: 'Previous extent',
	tooltipText: 'Go to previous extent'
});
Ext.apply(WebGIS.MapAction.NextExtent.prototype, {
	titleText: 'Next extent',
	tooltipText: 'Go to next extent'
});

// Editor tools
Ext.apply(WebGIS.MapAction.DrawFeature.prototype, {
	titlePointText:			'Draw point',
	tooltipPointText:		'Draw point',
	titleCurveText:			'Draw line',
	tooltipCurveText:		'Draw line',
	titlePolygonText:		'Draw polygon',
	tooltipPolygonText:	'Draw polygon'
});
Ext.apply(WebGIS.MapAction.SelectFeature.prototype, {
	titleText: 'Select geometry',
	tooltipText: 'Select geometry'
});
Ext.apply(WebGIS.MapAction.ModifyFeature.prototype, {
	titleText: 'Modify geometry',
	tooltipText: 'Modify geometry'
});
Ext.apply(WebGIS.MapAction.DragFeature.prototype, {
	titleText: 'Move geometry',
	tooltipText: 'Move geometry'
});

// Measure tools
Ext.apply(WebGIS.MapAction.MeasureLine.prototype, {
	titleText: 'Measure distance',
	tooltipText: 'Measure distance by drawing a line'
});
Ext.apply(WebGIS.MapAction.MeasureArea.prototype, {
	titleText: 'Measure area',
	tooltipText: 'Measure distance by drawing a polygon'
});

// Identify tools
Ext.apply(WebGIS.MapAction.Identify.prototype, {
	titleText: 'Identify',
	tooltipText: 'Shows attributes for an object in the map (only WMS-layers)'
});



// InfoFeature tools
Ext.apply(WebGIS.MapAction.InfoFeature.prototype, {
	titleText: 'Info Feature',
	tooltipText: 'Shows attributes for an Vector Feature in the map'
});
