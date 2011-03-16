/**
 * WebGIS JS Library
 * Copyright(c) 2007, Sweco Position
 *
 * Licensed under GPLv3
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Author: Björn Harrtell
 *
 * @fileoverview WebGIS.MapAction base class implementation
 */
 
Ext.namespace('WebGIS');

Ext.QuickTips.init();

/**
 * @class Abstract baseclass for MapActions. MapActions are extended from
 * Ext.Action to handle interaction with OpenLayers and can be used as buttons,
 * menu items and more in an Ext Js GUI. This class also have global handling of
 * OpenLayers.Control activation and manages navigation history
 * @extends Ext.Action
 * @param {Object} config Ext.Action config options<br>
 {OpenLayers.Map} [map] Required config option
 */
WebGIS.MapAction = function(config) {
	// default config options
	config.cls = 'x-btn-text-icon';
	
	if (typeof(this.titleText) != 'undefined') {
		config.text = this.titleText;
	}
	if (typeof(this.tooltipText) != 'undefined') {
		config.tooltip = this.tooltipText;
	}

	// if a olcontrol is specified, handle it globally
	if (typeof(config.olcontrol) != 'undefined')
	{
		config.map.addControl(config.olcontrol);
		WebGIS.MapAction.openLayersControls.push(config.olcontrol);
		config.handler = WebGIS.MapAction.MapActionHandler;
		config.scope = config;
		
		config.enableToggle = true;
		config.toggleGroup = 'WebGIS.MapAction';
	}
        

	// initalize navigation history (only do this once)
	if (!WebGIS.MapAction.navigationHistoryInitialized) {
		
		var processNewBounds = function() {
			var previous = WebGIS.MapAction.navigationHistory[WebGIS.MapAction.currentHistoryPosition];
			
			// map is done with move/zoom so enable navigation tools
			for (i=0; i<WebGIS.MapAction.navigationActions.length; i++) WebGIS.MapAction.navigationActions[i].enable();
			
			// check where we are in navigation history and disable related actions
			if (WebGIS.MapAction.navigationHistory.length < 2){
				for (i=0; i<WebGIS.MapAction.nextExtentActions.length; i++) WebGIS.MapAction.nextExtentActions[i].disable();
				for (i=0; i<WebGIS.MapAction.previousExtentActions.length; i++) WebGIS.MapAction.previousExtentActions[i].disable();
			}
			else if (WebGIS.MapAction.currentHistoryPosition == WebGIS.MapAction.navigationHistory.length-1)	{
				for (i=0; i<WebGIS.MapAction.previousExtentActions.length; i++) WebGIS.MapAction.previousExtentActions[i].disable();
			}
			else if (WebGIS.MapAction.currentHistoryPosition == 0)	{
				for (i=0; i<WebGIS.MapAction.nextExtentActions.length; i++) WebGIS.MapAction.nextExtentActions[i].disable();
			}
			
			//  abort if new extent equals the next/previous one			
			if (this.getExtent().equals(previous)) return;
			
			// add historic bounds to top of history
			WebGIS.MapAction.navigationHistory.splice(0, 0, this.getExtent());
		};
		
		// call custom processing handler when map is done with move/zoom
		config.map.events.register('zoomend', config.map, processNewBounds);
		config.map.events.register('moveend', config.map, processNewBounds);
		
		// add first map extent to history
		WebGIS.MapAction.navigationHistory.push(config.map.getExtent());
		WebGIS.MapAction.navigationHistoryInitialized = true;
	}

	// call Ext.Action constructor
	WebGIS.MapAction.superclass.constructor.call(this, config);
};
Ext.extend(WebGIS.MapAction, Ext.Action, {
});

/**
 * Generic handler for all map actions that are "tools".
 * Scope is config object of caller
 * @private
 */
WebGIS.MapAction.MapActionHandler = function(object, event) {
	// deactivate other openlayers controls
	//for (i=0; i<this.map.controls.length; i++) this.map.controls[i].deactivate();
	for (i=0; i<WebGIS.MapAction.openLayersControls.length; i++) WebGIS.MapAction.openLayersControls[i].deactivate();
	
	// activate this openlayers control
	this.olcontrol.activate();
	
	// if this action is connected to a button, make sure it's toggled if pressed twice
	if (typeof(object.toggle) != 'undefined') object.toggle(true);
};

/**
 * Static array to manage activation/deactivation of added OpenLayers controls
 * @private
 */
WebGIS.MapAction.openLayersControls = [];

/**
 * Static array to manage instances of all instant navigation related MapActions
 * @private
 */
WebGIS.MapAction.navigationActions = [];

/**
 * Static array to manage instances of MapAction.PreviousEXtent
 * @private
 */
WebGIS.MapAction.previousExtentActions = [];

/**
 * Static array to manage instances of MapAction.NextEXtent
 * @private
 */
WebGIS.MapAction.nextExtentActions = [];

/**
 * Static array to manage navigation and history
 * @private
 */
WebGIS.MapAction.navigationHistory = [];

/**
 * Static bool to indicate if navigation history has been initalized
 * @private
 */
WebGIS.MapAction.navigationHistoryInitialized = false;

/**
 * Static int representing current navigation history position
 * @private
 */
WebGIS.MapAction.currentHistoryPosition = 0;
