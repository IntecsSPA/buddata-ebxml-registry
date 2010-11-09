
package it.intecs.pisa.ergorr.saxon;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.saxon.s9api.*;


 /**
 *
 * @author Andrea Marongiu
 */
public class SaxonXSLTThread extends Thread {

    private XdmNode xml;
    private XsltExecutable xslt;
    private SaxonXSLTParameter parameters[]=null;
    private PipedOutputStream pipeOut=null;
    private static Logger logger = Logger.getLogger(SaxonXSLTThread.class.getName());


    SaxonXSLTThread (XdmNode xml, XsltExecutable xslt, SaxonXSLTParameter xsltParameters [],
                                    PipedOutputStream pipeOut) throws IOException{
         this.xml = xml;
         this.xslt = xslt;
         this.parameters=xsltParameters;
         this.pipeOut = pipeOut;
      }


    @Override
    public synchronized void run()
      {
       final Serializer out = new Serializer();
       out.setOutputProperty(Serializer.Property.METHOD, "xml");
       out.setOutputProperty(Serializer.Property.INDENT, "no");
       out.setOutputStream(this.pipeOut);

       XsltTransformer trans = this.xslt.load();
       trans.setInitialContextNode(this.xml);
       trans.setDestination(out);
       if(this.parameters != null)
           for(int i=0; i<this.parameters.length;i++)
               trans.setParameter(this.parameters[i].getParamName(), this.parameters[i].getParamValue());
       try {
            trans.transform();
       } catch (SaxonApiException ex) {
          logger.log(Level.WARNING, "Saxon tansform error: ", ex);
       }
       try {
            this.pipeOut.close();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not close the Saxon PipedOutputStream: ", ex);
        }
      }
}







