/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.intecs.pisa.erogorr.ui;

/**
 *
 * @author simone
 */
import com.google.gson.JsonObject;
import it.intecs.pisa.util.DOMUtil;
import it.intecs.pisa.util.DateUtil;
import it.intecs.pisa.util.IOUtil;
import it.intecs.pisa.util.json.JsonErrorObject;
import it.intecs.pisa.util.json.JsonUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.intecs.pisa.util.DOMUtil;
import it.intecs.pisa.util.IOUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Massimiliano Fanciulli
 */
public class Harvest extends HttpServlet {

    protected static final String REST_HARVEST_FROM_FILE = "/fromFile";
    protected static final String REST_HARVEST_FROM_URL = "/fromURL";
    protected static final String ID_SOURCE = "source";
    private static String HARVEST_DIR = "harvest";
    private static String SLASH = "/";
    private static String HTTP_SERVICE_BINDING = "httpservice";
    private static String ID_PARAMETER = "id";
    private static String SERVICE_NAME = "serviceName";
    private static final String RESOURCE_TYPE_NAME = "EarthObservation";
    private static final String HARVEST_SOAP_ACTION = "http://www.opengis.net/cat/csw/2.0.2/requests#Harvest";
    private static final String CSW_NAMESPACE = "http://www.opengis.net/cat/csw/2.0.2";
    private static final String XML_SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String XML_SCHEMA_INSTANCE_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String CSW_VERSION = "2.0.2";
    private static final String CSW_SERVICE_NAME = "CSW";
    private static final String HARVEST_ELEMENT_NAME = "Harvest";
    private static final String SOURCE_ELEMENT_NAME = "Source";
    private static final String RESOURCE_TYPE_ELEMENT_NAME = "ResourceType";
    private static final String SERVICE_EXCEPTION_ELEMENT_NAME = "ServiceExceptionReport";
    private static String FILES_STORED_FOLDER_PATH = "../GUIManagerPlugin/resources/storedData/";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        response.setContentType("text/html;charset=UTF-8");
        String uri;
        try {
            uri = request.getRequestURI();
            if (uri.endsWith(REST_HARVEST_FROM_FILE)) {
                hartvestFromFile(request, response);
            } else if (uri.endsWith(REST_HARVEST_FROM_URL)) {
                hartvestFromURL(request, response);

            }

        } catch (Exception ex) {
            request.getSession().invalidate();
            response.sendError(401);
        } finally {

            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private static void copy(InputStream in, OutputStream out) throws IOException {
        synchronized (in) {
            synchronized (out) {

                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    private void hartvestFromFile(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        DOMUtil domutil = new DOMUtil();
        String responseMessage = "";
        String javascriptResponse = "";
        Document harvestDoc = null;
        String url = getApplicationURL(req.getRequestURL());
        String metadataFileID = req.getParameter(ID_PARAMETER);
        ZipFile zipFile = null;
        boolean harvestResult = false;
        boolean list = true;
        int countMetadata = 0;
        int listSize = 0;

        String rootDir = getServletContext().getRealPath("/");
        File webInfDir = new File(rootDir, "WEB-INF");
        File storeDir = new File(webInfDir, "storedData");


        File harvestData = new File(storeDir, metadataFileID);
        try {
            zipFile = new ZipFile(harvestData);
        } catch (ZipException exZip) {
            /*Single Metadata*/
            list = false;
            listSize = 1;
            harvestDoc = domutil.inputStreamToDocument(new FileInputStream(harvestData));
            try {
                harvestResult = this.harvestDocument(url, harvestDoc);

            } catch (Exception ex) {
                harvestResult = false;
            }
            if (harvestResult) {
                countMetadata++;
            }
        }

        /*Metadata List*/

        if (list) {
            ZipEntry entry = null;
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                listSize++;
                entry = (ZipEntry) entries.nextElement();
                harvestDoc = domutil.inputStreamToDocument(zipFile.getInputStream(entry));
                try {
                    harvestResult = this.harvestDocument(url, harvestDoc);
                } catch (Exception ex) {
                    harvestResult = false;
                }
                if (harvestResult) {
                    countMetadata++;
//                     logger.debug(entry.getName()+" harvested");
                } else {
//                    logger.error("Unable to harvest the following file: " + entry.getName());
                }
            }
            zipFile.close();
        }
        OutputStream out = resp.getOutputStream();
        if (countMetadata == 0) {
            javascriptResponse = resp.encodeRedirectURL("An error occurred while harvesting data from disk.");
            responseMessage = "{error : ";
        } else {
            javascriptResponse = resp.encodeRedirectURL("<br> Number of Metadata to harvest: " + listSize
                    + " <br> Number of Metadata harvested: " + countMetadata);
            responseMessage = "{info : ";
        }
        if (countMetadata < listSize) {
            javascriptResponse += " <br> See the log.";
        }

//        responseMessage += "\"" + javascriptResponse + "\", serviceName :\"" + servicename + "\"}";
        out.write(responseMessage.getBytes());
        out.close();
    }

    private boolean harvestDocument(String url, Document harvestDocument) throws Exception {
        DOMUtil util = null;
        String harvestURL = url + SLASH + HTTP_SERVICE_BINDING;
        Document message;
        String filename = java.util.UUID.randomUUID().toString() + ".xml";
        String metadateNamespace = harvestDocument.getDocumentElement().getNamespaceURI();
        String resourceTypePrefix = metadateNamespace.substring(metadateNamespace.lastIndexOf("/") + 1);

        String rootDir = getServletContext().getRealPath("/");

        File harvestDir = new File(rootDir, HARVEST_DIR);
        File harvest = new File(harvestDir, filename);
        DOMUtil.dumpXML(harvestDocument, harvest);
        util = new DOMUtil();
        String source = url + SLASH + HARVEST_DIR + SLASH + filename;
        message = getInputHarvestMessage(source, resourceTypePrefix);

        File tmpFile;
        tmpFile = IOUtil.getTemporaryFile();
        DOMUtil.dumpXML(message, tmpFile);
        Document harvestResponse = invokeHarvest(harvestURL, util.fileToDocument(tmpFile));


        // clean the HARVEST directory
        if (harvest.exists()) {
            harvest.delete();
        }

        if (harvestResponse.getDocumentElement().getLocalName().equalsIgnoreCase(SERVICE_EXCEPTION_ELEMENT_NAME)) {
            return false;
        } else {
            return true;
        }
    }

    private void hartvestFromURL(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        OutputStream out = resp.getOutputStream();
        DOMUtil domutil = new DOMUtil();
        String responseMessage = "";
        String javascriptResponse = "";
        Document harvestDoc = null;
        String url = req.getRequestURI();
        String metadataFileID = req.getParameter(ID_SOURCE);
        ZipFile zipFile = null;
        boolean harvestResult = false;
        boolean list = true;
        int countMetadata = 0;
        int listSize = 0;
        try {
            harvestResult = this.harvestDocument(url, harvestDoc);
        } catch (Exception ex) {
            javascriptResponse = resp.encodeRedirectURL("An error occurred while harvesting data from disk.<br> See the log.");
            out.write(javascriptResponse.getBytes());
            out.close();

        }
        out.write(javascriptResponse.getBytes());
        out.close();
    }

    private Document invokeHarvest(String url, Document harvestDocument) throws Exception {
        HttpMethod method;
        int statusCode = 0;
        HttpClient client = new HttpClient();


        method = new PostMethod(url);
          // Execute the method.
          statusCode = client.executeMethod(method);

          if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + method.getStatusLine());
          }

        DOMUtil util = new DOMUtil();
        String content = util.getDocumentAsString(harvestDocument);
        ((PostMethod)method).setRequestBody(content);

       // Read the response body.
       Document responseBody = util.inputStreamToDocument(method.getResponseBodyAsStream());
       return responseBody;

    }

    private Document getInputHarvestMessage(String source, String resourceTypePrefix) {
        Document message;
        DOMUtil util = new DOMUtil();
        Element rootEl;
        Element sourceEl;
        Element resourceTypeEl;

        message = util.newDocument();

        rootEl = message.createElement(HARVEST_ELEMENT_NAME);
        rootEl.setAttribute("service", CSW_SERVICE_NAME);
        rootEl.setAttribute("version", CSW_VERSION);
        rootEl.setAttribute("xmlns:csw", CSW_NAMESPACE);
        rootEl.setAttribute("xmlns", CSW_NAMESPACE);
        rootEl.setAttribute("xmlns:xsi", XML_SCHEMA_INSTANCE_NAMESPACE);
        rootEl.setAttribute("xmlns:xsd", XML_SCHEMA_NAMESPACE);
        message.appendChild(rootEl);

        sourceEl = message.createElement(SOURCE_ELEMENT_NAME);
        sourceEl.setAttribute("xmlns", CSW_NAMESPACE);
        sourceEl.setTextContent(source);
        rootEl.appendChild(sourceEl);

        resourceTypeEl = message.createElement(RESOURCE_TYPE_ELEMENT_NAME);
        resourceTypeEl.setTextContent(resourceTypePrefix + ":" + RESOURCE_TYPE_NAME);
        resourceTypeEl.setAttribute("xmlns", CSW_NAMESPACE);
        rootEl.appendChild(resourceTypeEl);
        return message;
    }

    private String getApplicationURL(StringBuffer requestURL) {
        int index = requestURL.indexOf(SLASH + SLASH);
        String remainingString = requestURL.substring(index + 2);
        index = remainingString.indexOf(SLASH);
        String address = remainingString.substring(0, index);
        remainingString = remainingString.substring(index + 1);
        String applicationName = remainingString.substring(0, remainingString.indexOf(SLASH));
        return "http://" + address + SLASH + applicationName;
    }
}
