
/**
 * @requires OpenLayers/Control.js
 * @requires OpenLayers/Handler/Box.js
 */

/**
 * Class: OpenLayers.Control.RemoveSelectWMS
 *
 * Inherits from:
 *  - <OpenLayers.Control.RemoveSelectWMS>
 */






OpenLayers.Control.RemoveSelectWMS = OpenLayers.Class(OpenLayers.Control, {

    selectionControl: null,

    /**
     * Method: activate
     */
    activate: function() {
       this.removeSelection();
    },

    /**
     * Method: removeSelection
     *
     */
    removeSelection: function () {
       if(this.selectionControl.selectionLayers.length > 0){
            this.selectionControl.removeSelectedLayer();
        }
    },

    setSelectWMSControl: function (selectWMSConctol){
      this.selectionControl=selectWMSConctol;
    },

    CLASS_NAME: "OpenLayers.Control.RemoveSelectWMS"
});



