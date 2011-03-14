<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ErgoRR</title>

        <!-- ************ XMLInterfaces Import *************-->
        <!-- Load jquery -->
        <script type="text/javascript" src="jsScripts/import/xmlInterfaces/import/jquery.js"></script>
        
        <!-- Load Extjs for IE -->
      <!--  <script type="text/javascript" src="jsScripts/import/xmlInterfaces/import/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="jsScripts/import/xmlInterfaces/import/ext/ext-all.js"></script>-->
       <!-- <script type="text/javascript" src="jsScripts/import/xmlInterfaces/import/jquery.js"></script>-->
        
        <!-- ***********************************************-->    
    
        <!-- ************ Load XMLInterfaces **************-->
            <script type="text/javascript" src="jsScripts/import/xmlInterfaces/interfacesManager.js"></script>
        <!-- **********************************************-->

     
        <link rel="stylesheet" type="text/css" href="resources/css/ergoRRUIManager.css" />
        <style type="text/css">
            body {margin:0px;padding:0px;font-size:small}
            div.header {padding:8px 8px 0px 8px;background-color:#666;color:#FFF;border-bottom:8px solid #4280b3;}
            div.content {padding:8px;}
            #loading-mask{
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        background-color:white;
    }
            #loading{
                position:absolute;
                left:42%;
                top:40%;
                padding:2px;
                z-index:20001;
                height:auto;
            }
            #loading a {
                color:#225588;
            }
            #loading .loading-indicator{
                background:white;
                color:#444;
                font:bold 13px tahoma,arial,helvetica;
                padding:10px;
                margin:0;
                height:auto;
            }
            #loading-icon {
                font: normal 10px arial,tahoma,sans-serif;
                position:absolute;
                left:38%;
                top:75%;
            }
            #loading-msg {
                font: normal 10px arial,tahoma,sans-serif;
                position:absolute;
                left:23%;
                top:160%;
            }
        </style>
        <script language="Javascript" type="text/javascript">
             
        </script>
         <script type="text/javascript" src="jsScripts/ergoRRUIManager.js"></script>
         <!--script type="text/javascript" src="js/console/functions.js"></script>
         <script type="text/javascript" src="js/console/help.js"></script-->


    </head>
    <body>
        <div class="header" id="header_div">
            <h3>An ebXML Registry Repository implemetation with Earth Observation extensions.</h3>
        </div>
        <!--div class="content" id="content_div">
            <b>Developed by:</b>
            <ul>
                <li>Yaman Ustuntas (4C Technologies / kZen)</li>
                <li>Massimiliano Fanciulli (Intecs Informatica e Tecnologia del Software)</li>
            </ul>
            <br/><br/>
            <b>License:</b>
            General Public License version 3 (GPL3)
            <br--> <!--<a href="js/console/main.html">here</a> --><!-- ergoRRUIManager.showLogin()  --> <!-- javascript:ergoRRUIManager.workspacePanel.insertLoadingPanel(); -->
            <!--You can configure this ebRR instance through the Graphical interface. Click <a href="#" onclick="javascript:ergoRRUIManager.showLogin()">here</a> for accessing it.
        </div-->
        <div id="loading-mask" style=""></div>
        <div id="loading">
            <div class="loading-indicator">ebXML ErgoRR Registry Repository<br /><span id="loading-msg">Loading... Please Wait...</span><span id="loading-icon"><img src="resources/images/init_Load.gif"/></span></div>
        </div>
        <div id="workspace"></div>
    </body>
</html>
