/* 
 *
 *  Developed By:      Intecs  S.P.A.
 *  File Name:         $RCSfile: XMLSerializer2.java,v $
 *  TOOLBOX Version:   $Name: HEAD $
 *  File Revision:     $Revision: 1.1.1.1 $
 *  Revision Date:     $Date: 2006/06/13 15:02:25 $
 *
 */
package it.intecs.pisa.util;

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class XMLSerializer2 {

  private static Transformer serializer = null;

  static {
    try {
      serializer = TransformerFactory.newInstance().newTransformer();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    }
  }

  private final StreamResult out;

  public XMLSerializer2(OutputStream out) {
    this.out = new StreamResult(out);
  }

  public XMLSerializer2(Writer out) {
    this.out = new StreamResult(out);
  }

  public void serialize(Node node) throws TransformerException {
   if (serializer != null)
      synchronized (serializer) {
        serializer.transform(new DOMSource(node), out);
      }
  }
  
   public void serialize(Node node,boolean indent) throws TransformerException {
     /*  Properties props;
       
       props=new Properties(); */
        if (indent==true)
        {
           /*props.setProperty("indent","yes");
           props.setProperty("method","xml");*/
            DOMUtil.indent(node.getOwnerDocument());
        }
       // else props.setProperty("indent","no");
       
     /*  this.serializer.setOutputProperties(props);*/
       
       this.serialize(node);
  }

}
