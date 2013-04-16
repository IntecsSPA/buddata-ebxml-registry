
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class BBOX
    extends JAXBElement<BBOXType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "BBOX");

    public BBOX(BBOXType value) {
        super(NAME, ((Class) BBOXType.class), null, value);
    }

}
