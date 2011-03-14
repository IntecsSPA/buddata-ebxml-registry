

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
function sendXmlHttpRequestTimeOut(requestMethod, requestUrl, requestAsync, 
                                    requestBody, timeOutRequest, eventResponse,
                                    eventTimeOut, headers, loading, eventError){
    
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

function sendAuthenticationXmlHttpRequestTimeOut(requestMethod, requestUrl, requestAsync,
                                                requestBody, userName, password, timeOutRequest,
                                                eventResponse, eventTimeOut, headers, loading, eventError){
    request=assignXMLHttpRequest();
    //alert(requestAsync);
    if(request){
        timeOutEvent=eventTimeOut;
        if(timeOutRequest <1000)
           timeOutRequest=timeOutRequest*1000;
        var message;
        request.open(requestMethod, requestUrl, requestAsync/*, userName, password*/);
      //  request.setRequestHeader("ebrr-Authorization", "ebrr  "+userName+":"+password);
        request.setRequestHeader("Authorization", "Basic "+Base64.encode(userName+":"+password));
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


function SHA1 (msg) {

	function rotate_left(n,s) {
		var t4 = ( n<<s ) | (n>>>(32-s));
		return t4;
	};

	function lsb_hex(val) {
		var str="";
		var i;
		var vh;
		var vl;

		for( i=0; i<=6; i+=2 ) {
			vh = (val>>>(i*4+4))&0x0f;
			vl = (val>>>(i*4))&0x0f;
			str += vh.toString(16) + vl.toString(16);
		}
		return str;
	};

	function cvt_hex(val) {
		var str="";
		var i;
		var v;

		for( i=7; i>=0; i-- ) {
			v = (val>>>(i*4))&0x0f;
			str += v.toString(16);
		}
		return str;
	};


	function Utf8Encode(string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	};

	var blockstart;
	var i, j;
	var W = new Array(80);
	var H0 = 0x67452301;
	var H1 = 0xEFCDAB89;
	var H2 = 0x98BADCFE;
	var H3 = 0x10325476;
	var H4 = 0xC3D2E1F0;
	var A, B, C, D, E;
	var temp;

	msg = Utf8Encode(msg);

	var msg_len = msg.length;

	var word_array = new Array();
	for( i=0; i<msg_len-3; i+=4 ) {
		j = msg.charCodeAt(i)<<24 | msg.charCodeAt(i+1)<<16 |
		msg.charCodeAt(i+2)<<8 | msg.charCodeAt(i+3);
		word_array.push( j );
	}

	switch( msg_len % 4 ) {
		case 0:
			i = 0x080000000;
		break;
		case 1:
			i = msg.charCodeAt(msg_len-1)<<24 | 0x0800000;
		break;

		case 2:
			i = msg.charCodeAt(msg_len-2)<<24 | msg.charCodeAt(msg_len-1)<<16 | 0x08000;
		break;

		case 3:
			i = msg.charCodeAt(msg_len-3)<<24 | msg.charCodeAt(msg_len-2)<<16 | msg.charCodeAt(msg_len-1)<<8	| 0x80;
		break;
	}

	word_array.push( i );

	while( (word_array.length % 16) != 14 ) word_array.push( 0 );

	word_array.push( msg_len>>>29 );
	word_array.push( (msg_len<<3)&0x0ffffffff );


	for ( blockstart=0; blockstart<word_array.length; blockstart+=16 ) {

		for( i=0; i<16; i++ ) W[i] = word_array[blockstart+i];
		for( i=16; i<=79; i++ ) W[i] = rotate_left(W[i-3] ^ W[i-8] ^ W[i-14] ^ W[i-16], 1);

		A = H0;
		B = H1;
		C = H2;
		D = H3;
		E = H4;

		for( i= 0; i<=19; i++ ) {
			temp = (rotate_left(A,5) + ((B&C) | (~B&D)) + E + W[i] + 0x5A827999) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}

		for( i=20; i<=39; i++ ) {
			temp = (rotate_left(A,5) + (B ^ C ^ D) + E + W[i] + 0x6ED9EBA1) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}

		for( i=40; i<=59; i++ ) {
			temp = (rotate_left(A,5) + ((B&C) | (B&D) | (C&D)) + E + W[i] + 0x8F1BBCDC) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}

		for( i=60; i<=79; i++ ) {
			temp = (rotate_left(A,5) + (B ^ C ^ D) + E + W[i] + 0xCA62C1D6) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}

		H0 = (H0 + A) & 0x0ffffffff;
		H1 = (H1 + B) & 0x0ffffffff;
		H2 = (H2 + C) & 0x0ffffffff;
		H3 = (H3 + D) & 0x0ffffffff;
		H4 = (H4 + E) & 0x0ffffffff;

	}

	var temp = cvt_hex(H0) + cvt_hex(H1) + cvt_hex(H2) + cvt_hex(H3) + cvt_hex(H4);

	return temp.toLowerCase();

}
  
  