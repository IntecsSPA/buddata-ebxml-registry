package be.kzen.ergorr.xpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;

/**
 * Binds namespace URIs with their prefixes in a hash map.
 * This is used by the <code>XPathUtil</code>.
 *
 * @author Yaman Ustuntas
 */
public class XMLNamespaces implements NamespaceContext {
    private Map<String, String> namespaces;
    
    public XMLNamespaces() {
        namespaces = new HashMap<String, String>();
    }
    
    public XMLNamespaces(HashMap namespaces) {
        this.namespaces = namespaces;
    }
    
    public void addNamespaceURI(String prefix, String namespaceURI) {
        namespaces.put(prefix, namespaceURI);
    }
    
    public String getNamespaceURI(String prefix) {
        return namespaces.get(prefix);
    }
    
    public String getPrefix(String namespaceURI) {
        Set keys = namespaces.keySet();

        for(Iterator i = keys.iterator(); i.hasNext(); ) {
            String prefix = (String)i.next();
            String uri = (String)namespaces.get(prefix);
            if(uri.equals(namespaceURI)) {
                return prefix;
            }
        }

        return null;
    }
    
    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
}
