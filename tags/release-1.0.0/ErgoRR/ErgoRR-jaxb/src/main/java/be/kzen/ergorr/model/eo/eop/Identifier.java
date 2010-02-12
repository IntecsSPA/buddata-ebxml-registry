
package be.kzen.ergorr.model.eo.eop;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Identifier
    extends JAXBElement<String>
{

    protected final static QName NAME = new QName("http://earth.esa.int/eop", "identifier");

    public Identifier(String value) {
        super(NAME, ((Class) String.class), null, value);
    }

}
