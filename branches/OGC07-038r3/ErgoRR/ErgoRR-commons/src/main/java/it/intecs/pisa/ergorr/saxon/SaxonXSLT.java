/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.intecs.pisa.ergorr.saxon;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import javax.xml.transform.sax.SAXSource;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;


/**
 *
 * @author Andrea Marongiu
 */


public class SaxonXSLT {
     Processor processor = null;
     XsltCompiler compiler = null;

    public SaxonXSLT(){
        this.processor=new Processor(false);
        this.compiler=this.processor.newXsltCompiler();
    }

    public SaxonXSLT(SaxonURIResolver uriResolver){
        this.processor=new Processor(false);
        this.compiler=this.processor.newXsltCompiler();
        this.compiler.setURIResolver(uriResolver);
    }


    public XdmNode getXdmNode(SAXSource xmlSAXSource) throws IOException, SaxonApiException{
       XdmNode source = this.processor.newDocumentBuilder().build(xmlSAXSource);
       return(source);
    }

    public  XsltExecutable getXsltExecutable(SAXSource xsltSAXSource) throws SaxonApiException{
       XsltExecutable exp=this.compiler.compile(xsltSAXSource);
       return exp;
    }
    /**
     * 	Perform an XSLT Saxon transformation and returns the
     * <code>PipedInputStream</code> of result.
     *
     * @param xmlURL XML Document Url.
     * @param xsltPath XSLT Url.
     * @throws javax.xml.xpath.IOException
     * @return Result as PipedInputStream.
     */
   /* public static PipedInputStream saxonXSLPipeTransform(URL xmlURL, URL xsltPath) throws IOException{
        PipedInputStream pipeIn = new PipedInputStream();
        final PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
         PrintStream ps = new PrintStream(pipeOut);
        new XSLTSaxonThread(xmlURL,xsltPath,ps);
        return pipeIn;
    }*/

   /**
     * 	Perform an XSLT Saxon transformation and returns the
     * <code>PipedInputStream</code> of result.
     *
     * @param xmlURL XML Document Url.
     * @param xsltPath XSLT Url.
     * @param xsltParameters XSLT Parameters Array.
     * @throws javax.xml.xpath.IOException
     * @return Result as PipedInputStream.
     */
    public PipedInputStream saxonXSLPipeTransform(SAXSource xml, SAXSource xslt,
           SaxonXSLTParameter xsltParameters []) throws IOException, SaxonApiException{
      PipedInputStream pipeIn = new PipedInputStream();
      PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
      XdmNode xmlSource=this.getXdmNode(xml);
      XsltExecutable xsltSource=this.getXsltExecutable(xslt);
      SaxonXSLTThread saxonThread=new SaxonXSLTThread(xmlSource, xsltSource,xsltParameters,pipeOut);
      saxonThread.start();
      return pipeIn;
    }


    






}
