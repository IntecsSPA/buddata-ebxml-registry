

package it.intecs.pisa.ergorr.saxon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;

/**
 *
 * @author Andrea Marongiu
 */
public class SaxonURIResolver implements URIResolver {
   
   private String contextURL;

  public SaxonURIResolver(URL contextURL) throws URISyntaxException{
     this.contextURL = contextURL.toString();
  }

     public Source resolve(String href, String base) throws TransformerException {
       
        Source sourceResult=null;
        URL sourceURL = null;
        try {
            sourceURL = new URL(this.contextURL + "/" + href);
        } catch (MalformedURLException ex) {
            throw new TransformerException("Malformed Internal Resource URL: " + this.contextURL + "/" + href);
        }
        try {
            sourceResult=new SAXSource(new InputSource(sourceURL.openStream()));
        } catch (IOException ex) {
            throw new TransformerException("Could not load internal Resource " + sourceURL.toString());
        }
         return sourceResult;
    }

}
/*String base_path;
>   String nav_style;
>   public MyResolver(ServletContext context, String
> style) {
>     this.base_path =
> context.getRealPath("/WEB-INF/styling/");
>     this.nav_style = style;
>   }
*/