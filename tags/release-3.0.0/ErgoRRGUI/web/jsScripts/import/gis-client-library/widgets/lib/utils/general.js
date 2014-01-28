

function replaceAll(text, strA, strB)
{
    return text.replace( new RegExp(strA,"g"), strB );    
}

function assignXMLHttpRequest() {
   var XHR = null;
   /*var browserUtente = navigator.userAgent.toUpperCase();
   if(typeof(XMLHttpRequest) === "function" || typeof(XMLHttpRequest) === "object")
      XHR = new XMLHttpRequest();
   else 
      if(window.ActiveXObject && browserUtente.indexOf("MSIE 4") < 0) 
	{ 
	 if(browserUtente.indexOf("MSIE 5") < 0)
	    XHR = new ActiveXObject("Msxml2.XMLHTTP");
         else
	    XHR = new ActiveXObject("Microsoft.XMLHTTP");
        }*/
  try {
        XHR = new XMLHttpRequest();
  } catch (trymicrosoft) {
    try {
        XHR = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (othermicrosoft) {
        try {
                XHR = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                XHR = false;
            }
    }
  }

  return XHR;

}

 var request=null;
 var timeOutEvent=null;
// var xhrTimeout=null;
function sendXmlHttpRequestTimeOut(requestMethod, requestUrl, requestAsync, requestBody, timeOutRequest, eventResponse, eventTimeOut, headers, loading, eventError){
    request=assignXMLHttpRequest();
    //alert(requestAsync);
    if(request){
        timeOutEvent=eventTimeOut;
        if(timeOutRequest <1000)
           timeOutRequest=timeOutRequest*1000;
        var message;
        request.open(requestMethod, requestUrl, requestAsync);
        request.setRequestHeader("connection", "close");
        var headSplit;
        if(headers){
           for(var i=0;i<headers.length; i++){
               headSplit=headers[i].split(",");
               request.setRequestHeader(headSplit[0], headSplit[1]);
           }
        }
        var xhrTimeout=setTimeout("request.abort();timeOutEvent.call();",timeOutRequest);
        if(requestAsync){
              request.onreadystatechange= function(){
                     ajaxResponseManager(request,xhrTimeout,message,eventResponse,eventError);
              };
        }
        if(loading){
           message=Ext.MessageBox.wait(loading.message,loading.title);
           //String title, String msg, [String progressText] )
         }
         request.send(requestBody);
         if(!requestAsync){
             var result=ajaxResponseManager(request,xhrTimeout,message,eventResponse,eventError);
             return result;
         }
         return null;
    }else{

       return null;
    }
}

function ajaxResponseManager(request,xhrTimeout,message,eventResponse,eventError){
  try{
      if(request.readyState == 4){
         clearTimeout(xhrTimeout);
         xhrTimeout=null;
         if(request.status == 200){
                    if(message) 
                       message.hide();

                     eventResponse(request.responseText);
                     return true;
                }else{
                   if(eventError) 
                     eventError(request.responseText); 
                   if(request){
                      Ext.Msg.show({
                          title: 'Error: ' + request.statusText,
                          buttons: Ext.Msg.OK,
                          width: Math.floor((screen.width/100)*50),
                          msg: request.responseText,
                          animEl: 'elId',
                          icon: Ext.MessageBox.ERROR
                      });
                   }
                 return false;  
               }  
     } 
  }catch(e){
     Ext.Msg.show({
        title:'Request Exception',
        buttons: Ext.Msg.OK,
        msg: 'EXCEPTION: '+e.message,
        animEl: 'elId',
        icon: Ext.MessageBox.ERROR
     });    
     return false;
  }
  return true;
}

    
 
function pausecomp(millis){
var date = new Date();
var curDate = null;
do { curDate = new Date(); }
while(curDate-date < millis);
} 
    

   
function removeXmlDiterctive(xmlString){
 var check=xmlString.indexOf("<?");
 if(check>=0){
    newXmlString=xmlString.substr(xmlString.indexOf("?>")+2,xmlString.length);
    return(newXmlString);
 }
 else
   return(xmlString);
}

/**
*
*  Javascript trim, ltrim, rtrim
*  http://www.webtoolkit.info/
*  Javascript trim implementation removes all leading and trailing occurrences of a set of characters specified. If no characters are specified it will trim whitespace characters from the beginning or end or both of the string.
*  Without the second parameter, they will trim these characters:
*   /*  " " (ASCII 32 (0x20)), an ordinary space.
    * "\t" (ASCII 9 (0x09)), a tab.
    * "\n" (ASCII 10 (0x0A)), a new line (line feed).
    * "\r" (ASCII 13 (0x0D)), a carriage return.
    * "\0" (ASCII 0 (0x00)), the NUL-byte.
    * "\x0B" (ASCII 11 (0x0B)), a vertical tab. 
**/

function trim(str, chars) {
    return ltrim(rtrim(str, chars), chars);
}

function ltrim(str, chars) {
    chars = chars || "\\s";
    return str.replace(new RegExp("^[" + chars + "]+", "g"), "");
}

function rtrim(str, chars) {
    chars = chars || "\\s";
    return str.replace(new RegExp("[" + chars + "]+$", "g"), "");
}

// Modificare Utilizzando Reg
function normalize_html(text){
  text=replaceAll(text,"\"","\\\"");
  text=replaceAll(text,"\n","\"+\"");
  text=replaceAll(text,"\b","");
  text=replaceAll(text,"\f","");
  text=replaceAll(text,"\t","");
  text=replaceAll(text,"\r","");
  text=trim(text);
  text=normalize_space(text);  
  return(text);          
}

// Modificare Utilizzando Reg
function adapt_xml(text){

  text=replaceAll(text,"<","&lt;");
  text=replaceAll(text,">","&gt;");
  text=replaceAll(text,"&","&amp;");
  text=replaceAll(text,"\"","\\\"");

  /*/text=replaceAll(text,"\t","");
  text=replaceAll(text,"\r","");
  text=trim(text);
  text=normalize_space(text);  */
  return(text);          
}


function getArrayByStringSlipt(stringList, separator){
    var arrayString=new Array();
    if(stringList)
      if(stringList.indexOf(separator) != -1)
        arrayString=stringList.split(separator);
      else
        arrayString[0]=stringList;
    return(arrayString);
}

function getPixelDim(dimType,stringDim){
   var perc;
   if(stringDim.indexOf('%')!= -1){
      perc=stringDim.split('%');
      if(dimType == "width")
        return (screen.width/100)*parseInt(perc[0]);
      else
        return(screen.height/100)*parseInt(perc[0]);
   }else
     return stringDim;

}


function createObjectByString(stringObject) {
    var newObject=null;
    var newObjString= "newObject={ "+stringObject+" }";
    eval(newObjString);
    return(newObject);
}


function removeElement(groupDiv,divNum) {
  var d = document.getElementById(groupDiv);
  var olddiv = document.getElementById(divNum);
  d.removeChild(olddiv);
}


Array.prototype.unique =
  function() {
    var a = [];
    var l = this.length;
    for(var i=0; i<l; i++) {
      for(var j=i+1; j<l; j++) {
        // If this[i] is found later in the array
        if (this[i] === this[j])
          j = ++i;
      }
      a.push(this[i]);
    }
    return a;
  };

// Get the union of n arrays
Array.prototype.union =
  function() {
    var a = [].concat(this);
    var l = arguments.length;
    for(var i=0; i<l; i++) {
      a = a.concat(arguments[i]);
    }
    return a.unique();
  };

function skip(){



  }



Array.prototype.find =
  function(element) {
    var arr2str = this.toString();
    var index=arr2str.search(element);

    return index;
  };

  function skip(){



  }

function popup(url, windName)
{
 var params;
 if(!windName)
    windName='';
 params  = 'width='+screen.width;
 params += ', height='+screen.height;
 params += ', top=0, left=0';
 params += ', fullscreen=yes';
 params += ',directories=yes, ';
 params += ',  location=yes, ';
 params += ',  menubar=yes,';
 params += ',  resizable=yes';
 params += ',  toolbar=yes';
 var newwin=window.open(url, windName , params);
 if (window.focus) {newwin.focus()}
 return false;
}


function popupPlaseWait(url) {
   var winl = (screen.width-150)/2;
   var wint = (screen.height-150)/2;

   var newwindow=window.open(url,'name','height=150,width=150,top='+wint+',left='+winl);
   if (window.focus) {
       newwindow.focus()
    }
   return newwindow;
}

function randomColor(){
    //elabora un colore casuale e ne restituisce la stringa esadecimale
    var rc=new Array(3);
    for(var i=0;i<3;i++)
        rc[i]=Math.floor(Math.random() * 255);
    return HEX(rc);
}


function RGB(c){
//restituisce la conversione esadecimale sotto forma di array di interi
var r=new Array(3);
if(c.charAt(0)=="#") c=c.substr(1,6); //elimina il cancelletto
for(i=0;i<3;i++)
r[i]=HexToDec(c.substr(i*2,2));
return r;
}

function HEX(c){
//riceve un vettore rgb in decimale e lo converte in stringa esadecimale
return ("#" + DecToHex(c[0])+ DecToHex(c[1]) + DecToHex(c[2]));
}


function DecToHex(n){
//converte da decimale (0..255) a esadecimale (stringa a due caratteri)
hex=n.toString(16);
if(hex.length==1) hex="0"+hex; /*aggiunge lo zero davanti se è un numero con una cifra sola*/
return hex.toUpperCase();
}

function HexToDec(s){
//converte da stringa esadecimale a numero decimale
return parseInt(s,16);
} 


  
  
