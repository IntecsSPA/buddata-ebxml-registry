
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class PropertyIsNull
    extends JAXBElement<PropertyIsNullType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "PropertyIsNull");

    public PropertyIsNull(PropertyIsNullType value) {
        super(NAME, ((Class) PropertyIsNullType.class), null, value);
    }

}
