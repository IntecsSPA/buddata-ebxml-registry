/**

 *
 * Author: Andrea Marongiu
 *
 * @fileoverview WebGIS.Panel.WindowInterfacePanel class
 */

Ext.namespace('WebGIS', 'WebGIS.Panel');


WebGIS.Panel.WindowInterfacePanel = Ext.extend(Ext.Window, {

  loadingBarImg: "images/loader1.gif",
  loadingMessage: "Loading... Please Wait...",
  loadingMessageColor: "black",
  loadingPanelColor: "#d9dce0",
  loadingMessagePadding: 0,
  //xtype: 'window',
  loadingBarImgPadding: 0,
  loadingPanelDuration: 1000,
  loadingPanelWorkspaceEl: 'workspace',

  getXType:function(){
    return('window');
  },

  insertLoadingPanel: function(){
                                        // Insert Style
                                  if(BrowserDetect.browser == "Firefox"){
                                        var workspace=document.getElementById(this.loadingPanelWorkspaceEl);
                                        var divPanelMask=document.createElement('div');
                                        divPanelMask.id="loadingPanel-mask";
                                        var divPanelInfo=document.createElement('div');
                                        divPanelInfo.id="loadingPanel-info";
                                        divPanelInfo.innerHTML=""+
                                            "<span id=\"loadingPanel-img\"><img src=\""+this.loadingBarImg+"\"/></span></br>"+
                                            "<span id=\"loadingPanel-msg\">"+this.loadingMessage+"</span>";
                                        var loadingPanelStyle=document.createElement('style');
                                        loadingPanelStyle.type="text/css";
                                        var left=this.x+((this.getWidth()-this.getInnerWidth())/2);
                                        var top=this.y+((this.getHeight()-this.getInnerHeight())/1.3);
                                        var leftInfo=left+(this.getInnerWidth()/2.8);
                                        var topInfo=top+(this.getInnerHeight()/2.8);
                                       // var styleNode = document.createTextNode("Hello. This is a new node.");
                                        var styleNode = document.createTextNode("#loadingPanel-mask{"+
                                            "position:absolute;"+
                                            "left:"+left+";"+
                                            "top:"+top+";"+
                                            "width:"+this.getInnerWidth()+"px;"+
                                            "height:"+this.getInnerHeight()+"px;"+
                                            "z-index:20000;"+
                                            "background-color:"+this.loadingPanelColor+";"+
                                        "}\n"+
                                        "#loadingPanel-info{"+
                                            "position:absolute;"+
                                            "left:"+leftInfo+";"+
                                            "top:"+topInfo+";"+
                                            "z-index:20000;"+
                                            "height:auto;"+
                                        "}\n"+
                                        "#loadingPanel-msg{"+
                                            "font: normal 10px arial,tahoma,sans-serif;"+
                                            "padding:"+this.loadingMessagePadding+"px;"+
                                            "color: "+this.loadingMessageColor+";"+
                                        "}\n"+
                                        "#loadingPanel-img{"+
                                            "padding:"+this.loadingBarImgPadding+"px;"+
                                        "}\n");
                                        
                                        workspace.appendChild(loadingPanelStyle);
                                        var test=workspace.getElementsByTagName("style");
                                        test[0].appendChild(styleNode);
                                        workspace.appendChild(divPanelMask);
                                        workspace.appendChild(divPanelInfo);

                                       var hideMask = function () {
                                         Ext.get('loadingPanel-info').remove();
                                         Ext.fly('loadingPanel-mask').fadeOut({
                                            remove:true
                                          
                                        });
                                  
                                    }

                                    hideMask.defer(this.loadingPanelDuration);
                                    }

                            }
});

