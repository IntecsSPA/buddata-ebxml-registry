
package be.kzen.ergorr.model.gml;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Point
    extends JAXBElement<PointType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/gml/3.2", "Point");

    public Point(PointType value) {
        super(NAME, ((Class) PointType.class), null, value);
    }

}
