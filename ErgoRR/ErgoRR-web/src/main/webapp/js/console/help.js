var aboutPage="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>kZen ErgoRR</title><style type=\"text/css\">body {margin:0px;padding:0px;font-size:small} div.header {padding:8px 8px 0px 8px;background-color:#666;color:#FFF;border-bottom:8px solid #990;} div.content {padding:8px;}</style></head><body><div class=\"header\"> <h3>An ebXML Registry Repository implemetation with Earth Observation extensions.</h3></div><div class=\"content\"><b>Developed by:</b><ul><li>Yaman Ustuntas (4C Technologies / kZen)</li><li>Massimiliano Fanciulli (Intecs Informatica e Tecnologia del Software)</li></ul><br/><br/><b>License:</b>General Public License version 3 (GPL3)</div></body></html>";

var miscHelp="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"+
    "</head><body>"+
    "<h2>Repository home folder</h2>"+
    "Repository root directory where tomcat user has read and write access."+
    " The application will automatically append the 'deployName' property (see ergorr.properties file customized before deploying the catalogue) to this path."+
    " So if you have multiple instance of the registry then it will place the repository items of different instances in seperate subfolders.<br><br>"+
    "<h2>Service provider name</h2>"+
    "Name of the provider hosting this catalogue<br><br>"+
    "<h2>Service provider website</h2>"+
    "URL of the website of the Service Provider"+
    "<h2>Contact name</h2>"+
    "Name of the contact person.<br><br>"+
    "<h2>Contact position</h2>"+
    "Position of the contact person.<br><br>"+
    "<h2>Contact phone number</h2>"+
    "Phone number of the contact person.<br><br>"+
    "<h2>Delivery point address</h2>"+
    "Address of the deivery point (Road only).<br><br>"+
    "<h2>Delivery point city</h2>"+
    "City of the deivery point.<br><br>"+
    "<h2>Delivery point administrative area</h2>"+
    "Administrative area of the deivery point.<br><br>"+
    "<h2>Delivery point postal code</h2>"+
    "Postal code of the deivery point.<br><br>"+
    "<h2>Delivery point country</h2>"+
    "Country of the deivery point.<br><br>"+
    "<h2>Contact person email address</h2>"+
    "Email address of the contact person.<br><br>"+
    "<h2>Contact person hours of service</h2>"+
    "Hours of service of the contact person.<br><br>"+
    "<h2>Contact instructions</h2>"+
    "Specific instructions for contacting catalogue responsible persons.<br><br>"+
     "<h2>Contact person role</h2>"+
    "Role of the contact person.<br><br>"+
    "<h2>Encoding</h2>"+
    "Encoding used for SOAP messages exchange.<br><br>"+
    "<h2>Language</h2>"+
    "Language for RIM names and descriptions.<br><br>"+
    "<h2>Return exceptions in SOAP messages</h2>"+
    "If checked ebRR will returns a detailed description of exceptions in SOAP messages.</body></html>";

var databaseHelp="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"+
    "</head><body>"+
    "<h2>Max records to return</h2>"+
    "This fields allows to set the maximum number of records to be returned "+
    "by the catalogue. This value is checked against the maxRecords field in "+
    "GetRecords operations and the lowest one is used effectively.<br><br>"+
    "<h2>Default SRS ID</h2>"+
    "Provide description.<br><br>"+
    "<h2>Default SRS ID</h2>"+
    "Provide description.<br><br>"+
    "<h2>Result depth</h2>"+
    "the depth of the relationships in a response.<br><br>"+
    "<h2>Simplify model</h2>"+
    "Great for performance improvement. usually the elements such as Slot, "+
    "ExternalIdentifier, Classification, Name, Description, VersionInfo of "+
    "objects such as Associations, Classifications and ExternalIdentifiers are "+
    "never used (although allowed in the specification). If checked, this setting "+
    "will cause the persistence layer to skip those queries for those elements for "+
    "the certain objects.<br><br>"+
    "<h2>Load package members</h2>"+
    "if checked also loads the member objects of a RegistryPackage.<br><br>"+
    "<h2>Load nested classification nodes</h2>"+
    "if set to 'true', loads child ClassificationNodes of ClassificationSchemes and ClassificationNodes.<br><br>"+
    "<h2>Return results count</h2>"+
    "If not checked, good for performance as the implementation doesn't have to make "+
    "a seperate query for counting. The downside is that the client will not be able to "+
    "get the total result count to do things like pagination. An alternative for the "+
    "client can be to fetch (requestedObjectSize + 1) to know if there is atleast one more page."+
    "NOTE: this has to be implemented yet and has to effect at the moment.<br><br></body></html>";