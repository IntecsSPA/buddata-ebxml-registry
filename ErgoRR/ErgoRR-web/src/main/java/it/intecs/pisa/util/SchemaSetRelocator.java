/**
 * 
 */
package it.intecs.pisa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import java.net.URISyntaxException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class contains some static method that are used to perform schema set
 * relocation It uses Java API in order to access to files
 * 
 * @author Massimiliano Fanciulli
 * 
 */
public class SchemaSetRelocator {

    private static final String URI_SCHEME_FILE = "file";
    private static final String URI_SCHEME_HTTP = "http";
    public static final String TAG_REDEFINE = "redefine";
    private static final String TAG_INCLUDE = "include";
    private static final String TAG_IMPORT = "import";
    protected static final String ATTRIBUTE_SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String ATTRIBUTE_SCHEMA_LOCATION = "schemaLocation";

    public static boolean updateSchemaLocationToOnlySchemaName(File schemaDir) {
        File[] subDirs = null;
        InputStream input = null;
        OutputStream output = null;
        Document doc = null;
        DOMUtil domutil;

        try {
            if (schemaDir.isDirectory()) {
                subDirs = schemaDir.listFiles();
                for (File st : subDirs) {
                    updateSchemaLocationToOnlySchemaName(st);
                }

            } else if(schemaDir.getAbsolutePath().endsWith(".xsd")){
                // it is a file, must relocate
                try {
                    domutil = new DOMUtil();
                    input = new FileInputStream(schemaDir);
                    doc = domutil.inputStreamToDocument(input);

                    makeSchemaLocationToOnlySchemaName(doc);

                    output = new FileOutputStream(schemaDir);
                    DOMUtil.dumpXML(doc, output, true);

                    output.close();

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * This method is used to update schemaLocations into all schema files that are found into the directory described by schemaDir variable.
     * The schema location is updated in order to be relative to the URI relativeTo
     * @param schemaDir Directory that is parsed in order to process all child schema files
     * @param relativeTo Variable containing the relativization parameter
     * @return true or false wheter the procedure is successfull or not
     */
    public static boolean updateSchemaLocationToAbsolute(File schemaDir,
            URI relativeTo) {
        File[] subDirs = null;
        InputStream input = null;
        OutputStream output = null;
        Document doc = null;
        DOMUtil domutil;

        domutil = new DOMUtil();

        try {
            if (schemaDir.isDirectory()) {
                subDirs = schemaDir.listFiles();
                for (File st : subDirs) {
                    updateSchemaLocationToAbsolute(st, relativeTo);
                }

            } else if(schemaDir.getAbsolutePath().endsWith(".xsd")){
                // it is a file, must relocate
                try {
                    input = new FileInputStream(schemaDir);
                    doc = domutil.inputStreamToDocument(input);

                    makeSchemaLocationAbsolute(doc, relativeTo, schemaDir.getParentFile());

                    output = new FileOutputStream(schemaDir);
                    DOMUtil.dumpXML(doc, output, true);

                    output.close();

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean updateSchemaLocationToRelative(File schemaDir, URI relativeTo) {
        return updateSchemaLocationToRelative(schemaDir, relativeTo, null/*"service:"*/);
    }

    /**
     *
     * @param schemaDir
     * @param relativeTo
     * @return
     */
    public static boolean updateSchemaLocationToRelative(File schemaDir,
            URI relativeTo, String addUriScheme) {
        File[] subDirs = null;
        InputStream input = null;
        OutputStream output = null;
        Document doc = null;
        DOMUtil domutil;

        domutil = new DOMUtil();

        try {
            if (schemaDir.isDirectory()) {
                subDirs = schemaDir.listFiles();
                for (File st : subDirs) {
                    updateSchemaLocationToRelative(st, relativeTo, addUriScheme);
                }

            } else if(schemaDir.getAbsolutePath().endsWith(".xsd")){
                // it is a file, must relocate
                try {
                    input = new FileInputStream(schemaDir);
                    doc = domutil.inputStreamToDocument(input);

                    relocateSchema(doc, relativeTo, addUriScheme);

                    output = new FileOutputStream(schemaDir);
                    DOMUtil.dumpXML(doc, output, true);

                    output.close();

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static void makeSchemaLocationToOnlySchemaName(Document doc) throws URISyntaxException {
        Element root = null;
        NodeList importNodes = null;
        String schemaLocation = null;
        Element importNode = null;
        URI fileURI = null;
        String path = null;
        String tagName = null;

        root = doc.getDocumentElement();
           //check if tag are at first level, child of root tag
        importNodes = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_IMPORT);
        // importNodes=root.getElementsByTagName("import");
        makeSchemaLocationToOnlySchemaName(importNodes);

        importNodes = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_INCLUDE);
        makeSchemaLocationToOnlySchemaName(importNodes);

        importNodes = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_REDEFINE);
        makeSchemaLocationToOnlySchemaName(importNodes);
    }

    private static void makeSchemaLocationToOnlySchemaName(NodeList importNodes) throws URISyntaxException {
        String schemaLocation;
        Element importNode;
        URI fileURI;
        String path;
        String uriScheme;

        for (int i = 0; i < importNodes.getLength(); i++) {
            importNode = (Element) importNodes.item(i);

            schemaLocation = importNode.getAttribute(ATTRIBUTE_SCHEMA_LOCATION);
            schemaLocation=schemaLocation.replace('\\', '/');
            fileURI = new URI(schemaLocation);

            uriScheme = fileURI.getScheme();

            if (uriScheme == null || uriScheme.startsWith(URI_SCHEME_FILE)) {
                //update only uri that point to local file..
                path = fileURI.getPath();
                path=path.replace('\\', '/');
                importNode.setAttribute(ATTRIBUTE_SCHEMA_LOCATION, path.substring(path.lastIndexOf("/") + 1));
            }
        }
    }

    /**
     *
     * @param doc
     * @param relativeTo
     * @throws Exception
     */
    private static void relocateSchema(Document doc, final URI relativeTo, String addUriScheme)
            throws Exception {
        Element root = null;
        NodeList nodesList = null;
        String schemaLocation = null;
        Element importNode = null;
        URI fileURI;

        root = doc.getDocumentElement();

        nodesList = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_IMPORT);
        relocateSchema(relativeTo, nodesList, addUriScheme);

        nodesList = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_INCLUDE);
        relocateSchema(relativeTo, nodesList, addUriScheme);

        nodesList = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_REDEFINE);
        relocateSchema(relativeTo, nodesList, addUriScheme);

    }

    private static void relocateSchema(final URI relativeTo, NodeList importNodes, String addUriScheme) throws URISyntaxException {
        String schemaLocation;
        Element importNode;
        URI fileURI;
        String uriScheme;
        String relocatedUri;

        for (int i = 0; i < importNodes.getLength(); i++) {
            importNode = (Element) importNodes.item(i);

            schemaLocation = importNode.getAttribute(ATTRIBUTE_SCHEMA_LOCATION);
            schemaLocation=schemaLocation.replace('\\', '/');
            fileURI = new URI(schemaLocation);

            uriScheme = fileURI.getScheme();

            if (uriScheme == null || uriScheme.startsWith(URI_SCHEME_FILE)) {
                relocatedUri = relativeTo.relativize(fileURI).toString();

                if (addUriScheme != null && addUriScheme.endsWith(":")) {
                    relocatedUri = addUriScheme + relocatedUri;
                } else if (addUriScheme != null && addUriScheme.endsWith(":") == false) {
                    relocatedUri = addUriScheme + ":" + relocatedUri;
                }

                relocatedUri=relocatedUri.replace('\\', '/');
                importNode.setAttribute(ATTRIBUTE_SCHEMA_LOCATION, relocatedUri);
            }
        }
    }

    /**
     *
     * @param doc
     * @param relativeTo
     * @throws Exception
     */
    private static void makeSchemaLocationAbsolute(Document doc, URI relativeTo, File subDir)
            throws Exception {
        Element root = null;
        NodeList importNodes = null;
        String schemaLocation = null;
        Element importNode = null;
        File file;
        File completeFile;

        root = doc.getDocumentElement();

        importNodes = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_IMPORT);
        makeSchemaLocationAbsolute(relativeTo, importNodes, subDir);

        importNodes = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_INCLUDE);
        makeSchemaLocationAbsolute(relativeTo, importNodes, subDir);

        importNodes = root.getElementsByTagNameNS(ATTRIBUTE_SCHEMA_NAMESPACE, TAG_REDEFINE);
        makeSchemaLocationAbsolute(relativeTo, importNodes, subDir);

    }

    private static void makeSchemaLocationAbsolute(URI relativeTo, NodeList importNodes, File subDir) throws Exception {
        String schemaLocation;
        Element importNode;
        File file;
        File completeFile;
        URI fileURI;
        File uriFile;
        String uriScheme;
        String newURI=null;

        for (int i = 0; i < importNodes.getLength(); i++) {
            importNode = (Element) importNodes.item(i);

            schemaLocation = importNode.getAttribute(ATTRIBUTE_SCHEMA_LOCATION);
            schemaLocation=schemaLocation.replace('\\', '/');

            fileURI = new URI(schemaLocation);

            uriScheme = relativeTo.getScheme();

            if (uriScheme == null || uriScheme.startsWith(URI_SCHEME_FILE)) {
                uriFile = new File(fileURI.getPath());
                if (uriFile.isAbsolute()) {
                    completeFile = uriFile;
                } else {
                    completeFile = new File(new File(relativeTo), schemaLocation);
                }

                
                String filePath;
                filePath = completeFile.getCanonicalPath();
                newURI = filePath.startsWith("/") == true ? "file:" + filePath : "file:/" + filePath;
            }
            else if(uriScheme.startsWith(URI_SCHEME_HTTP))
            {
               /*URI uri;
               uri=relativeTo.resolve(schemaLocation);
               newURI=uri.toString();*/
                newURI=relativeTo.toASCIIString()+"/"+schemaLocation;
            }

            newURI=newURI.replace('\\', '/');
            importNode.setAttribute(ATTRIBUTE_SCHEMA_LOCATION, newURI);
        }
    }
}
