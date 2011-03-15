package it.intecs.pisa.util;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XSD {

	private static final String ATTR_TARGET_NAMESPACE = "targetNamespace";

	public static String getTargetNamespace(File schema)
	{
		DOMUtil util;
		Document doc;
		Element rootEl;
		try
		{
			util=new DOMUtil();
			doc=util.fileToDocument(schema);
			
			rootEl=doc.getDocumentElement();
			return rootEl.getAttribute(ATTR_TARGET_NAMESPACE);
		}
		catch(Exception e)
		{
			return "";
		}
		
	}
}
