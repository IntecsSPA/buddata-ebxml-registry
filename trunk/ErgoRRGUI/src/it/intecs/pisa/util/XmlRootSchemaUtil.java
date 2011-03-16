/**
 * 
 */
package it.intecs.pisa.util;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Massimiliano Fanciulli
 *
 */
public class XmlRootSchemaUtil {
	public static final String NAMESPACE_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
	
	/**
	 * 
	 * @param xml
	 * @param namespace
	 * @param uri
	 */
	public static void updateSchemaLocation(Document xml, String namespace,String uri) {
		Element rootEl = null;
		String schemaLocationFullStr = null;
		
		rootEl = xml.getDocumentElement();

		schemaLocationFullStr = namespace + " " + uri;

		rootEl.setAttribute("xmlns:xsi", NAMESPACE_SCHEMA_INSTANCE);
		rootEl.setAttribute("xsi:schemaLocation", schemaLocationFullStr);
	}
	
	/**
	 * 
	 * @param xml
	 * @param schema
	 */
	public static void updateSchemaLocation(Document xml, File schema) {
		DOMUtil util = null;
		Document schemaDoc = null;
		Element rootEl = null;
		String namespace = null;
	
		try {
			util = new DOMUtil();

			schemaDoc = util.fileToDocument(schema);
			rootEl = schemaDoc.getDocumentElement();
			namespace = rootEl.getAttribute("targetNamespace");
	
			updateSchemaLocation(xml,namespace,schema.toURI().toString());
	
		} catch (Exception e) {
	
		}
	}
	
	/**
	 * 
	 * @param xml
	 * @param schema
	 */
	public static void updateSchemaLocation(Document xml, URI schema) {
		updateSchemaLocation(xml,new File(schema));
	}
	
	/**
	 * 
	 * @param xmlToUpdate
	 * @param schemaLocation
	 * @return
	 * @throws Exception
	 */
	public static InputStream updateSchemaLocation(InputStream xmlToUpdate,
			URI schemaLocation) throws Exception {
		DOMUtil util = null;
		Document xml = null;
		InputStream resultStream = null;

		util = new DOMUtil();

		xml = util.inputStreamToDocument(xmlToUpdate);

		XmlRootSchemaUtil.updateSchemaLocation(xml, schemaLocation);

		resultStream = DOMUtil.getDocumentAsInputStream(xml);

		return resultStream;
	}
	
}
