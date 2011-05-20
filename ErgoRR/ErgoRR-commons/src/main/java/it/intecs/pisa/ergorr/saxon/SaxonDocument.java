
package it.intecs.pisa.ergorr.saxon;

import be.kzen.ergorr.commons.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import net.sf.saxon.om.NamespaceConstant;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.WhitespaceStrippingPolicy;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.DocumentImpl;
import net.sf.saxon.xpath.XPathEvaluator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author Andrea Marongiu
 */
public class SaxonDocument {

    private String stringDoc=null;
    private Processor processor=null;
    private XPathCompiler xpathComp=null;
    private DocumentBuilder builder=null;
    private XdmNode documentXmdNode=null;

    public SaxonDocument(URL xmlURL) throws IOException, SaxonApiException{
        this.processor= new Processor(false);
        InputSource is = new InputSource(xmlURL.openStream());
        this.stringDoc=IoUtil.toString(is.getByteStream());
        this.saxonDocumentInit();
    }

    public SaxonDocument(String xml) throws SaxonApiException{
        this.processor= new Processor(false);
        this.stringDoc=xml;
        this.saxonDocumentInit();
    }

    public SaxonDocument(InputStream xmlInputStream) throws SaxonApiException, IOException{
        this.processor= new Processor(false);
        InputSource is = new InputSource(xmlInputStream);
        this.stringDoc=IoUtil.toString(is.getByteStream());
        this.saxonDocumentInit();
    }

   private void saxonDocumentInit() throws SaxonApiException{
        this.xpathComp = this.processor.newXPathCompiler();
        this.builder = this.processor.newDocumentBuilder();
        builder.setLineNumbering(true);
        builder.setWhitespaceStrippingPolicy(WhitespaceStrippingPolicy.ALL);
        this.documentXmdNode= builder.build(this.getSAXSource());
   }

    /**
     * 
     */
    public String getRootNameSpace() throws XPathFactoryConfigurationException, XPathException, XPathExpressionException {
        System.setProperty("javax.xml.xpath.XPathFactory:"+
                     NamespaceConstant.OBJECT_MODEL_SAXON,
                    "net.sf.saxon.xpath.XPathFactoryImpl");
        XPathFactory xpf = XPathFactory.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
        XPath xpe = xpf.newXPath();
        NodeInfo doc = ((XPathEvaluator) xpe).setSource(this.getSAXSource());
        javax.xml.xpath.XPathExpression rootXpath = xpe.compile("*");
        NodeInfo root=(NodeInfo)rootXpath.evaluate(doc, XPathConstants.NODE);
        return root.getURI();
    }


    /**
     * 	
     */
    public String evaluateXPath(String xpath) throws XPathException, XPathExpressionException, SaxonApiException {
        XPathSelector selector=this.xpathComp.compile(xpath).load();
        selector.setContextItem(this.documentXmdNode);
        return selector.evaluateSingle().getStringValue();
    }


    public void declareXPathNamespace(String prefix, String uri){

        this.xpathComp.declareNamespace(prefix, uri);

    }
    
    
    public String evaluateXPathNode(String xpath) throws XPathFactoryConfigurationException, XPathException, XPathExpressionException {
        System.setProperty("javax.xml.xpath.XPathFactory:"+
                     NamespaceConstant.OBJECT_MODEL_SAXON,
                    "net.sf.saxon.xpath.XPathFactoryImpl");
        XPathFactory xpf = XPathFactory.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
        XPath xpe = xpf.newXPath();
        NodeInfo doc = ((XPathEvaluator) xpe).setSource(this.getSAXSource());
        javax.xml.xpath.XPathExpression rootXpath = xpe.compile(xpath);
        NodeInfo root=(NodeInfo)rootXpath.evaluate(doc, XPathConstants.NODE);
        return transformToString(root);
    }

    /**
     * @return the saxDoc
     */
    public SAXSource getSAXSource() {
        return(new SAXSource(new InputSource(new StringReader(this.stringDoc))));
    }

    /**
     * @return the stringDoc
     */
    public String getXMLDocumentString() {
        return stringDoc;
    }

    
    private static String transformToString(NodeInfo sourceXML) {

        StringWriter sw = new StringWriter();

        try {
            TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
            Transformer transformer = tFactory.newTransformer();
            transformer.transform(sourceXML, new StreamResult(sw));
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
        
        return sw.toString();
    }
}
