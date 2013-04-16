
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class DWithin
    extends JAXBElement<DistanceBufferType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "DWithin");

    public DWithin(DistanceBufferType value) {
        super(NAME, ((Class) DistanceBufferType.class), null, value);
    }

}
