
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class PropertyIsLike
    extends JAXBElement<PropertyIsLikeType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "PropertyIsLike");

    public PropertyIsLike(PropertyIsLikeType value) {
        super(NAME, ((Class) PropertyIsLikeType.class), null, value);
    }

}
