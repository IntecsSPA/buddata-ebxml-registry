package be.kzen.ergorr.model.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * JAXB utility to marshall and unmarshall
 * Java object instances to and from XML.
 * 
 * @author Yaman Ustuntas
 */
public class JAXBUtil {

    private static Logger logger = Logger.getLogger(JAXBUtil.class.getName());
    private static JAXBUtil instance;
    private JAXBContext jaxbContext;

    /**
     * Private constructor for the singleton instance.
     */
    private JAXBUtil() {
        try {
            String pkgs = "be.kzen.ergorr.model.csw:" +
                    "be.kzen.ergorr.model.eo.eop:" +
                    "be.kzen.ergorr.model.eo.atm:" +
                    "be.kzen.ergorr.model.eo.opt:" +
                    "be.kzen.ergorr.model.eo.sar:" +
                    "be.kzen.ergorr.model.gml:" +
                    "be.kzen.ergorr.model.ogc:" +
                    "be.kzen.ergorr.model.rim:" +
                    "be.kzen.ergorr.model.ows:" +
                    "be.kzen.ergorr.model.wrs";

            jaxbContext = JAXBContext.newInstance(pkgs);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to create JAXBContext", ex);
        }
    }

    public static synchronized JAXBUtil getInstance() {
        if (instance == null) {
            instance = new JAXBUtil();
        }

        return instance;
    }

    public void marshall(Object obj, OutputStream outStream) throws JAXBException {
        synchronized (jaxbContext) {
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(obj, outStream);
        }
    }

    public Node marshall(Object obj) throws JAXBException {
        synchronized (jaxbContext) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = null;
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.newDocument();
            } catch (ParserConfigurationException ex) {
                throw new JAXBException(ex);
            }

            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(obj, doc);
            return doc.getDocumentElement();
        }
    }

    public byte[] marshallToByteArr(Object obj) throws JAXBException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshall(obj, baos);
        return baos.toByteArray();
    }

    public String marshallToStr(Object obj) throws JAXBException {
        return new String(marshallToByteArr(obj));
    }

    public Unmarshaller createUnmarshaller() throws JAXBException {
        synchronized (jaxbContext) {
            return jaxbContext.createUnmarshaller();
        }
    }

    public Object unmarshall(File file) throws JAXBException {
        synchronized (jaxbContext) {
            Unmarshaller um = jaxbContext.createUnmarshaller();
            return um.unmarshal(file);
        }
    }

    public Object unmarshall(URL url) throws JAXBException {
        synchronized (jaxbContext) {
            Unmarshaller um = jaxbContext.createUnmarshaller();
            return um.unmarshal(url);
        }
    }

    public Object unmarshall(String xml) throws JAXBException {
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        synchronized (jaxbContext) {
            Unmarshaller um = jaxbContext.createUnmarshaller();
            return um.unmarshal(bais);
        }
    }
}