
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Within
    extends JAXBElement<BinarySpatialOpType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "Within");

    public Within(BinarySpatialOpType value) {
        super(NAME, ((Class) BinarySpatialOpType.class), null, value);
    }

}
