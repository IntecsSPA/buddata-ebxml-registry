
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class PropertyIsBetween
    extends JAXBElement<PropertyIsBetweenType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "PropertyIsBetween");

    public PropertyIsBetween(PropertyIsBetweenType value) {
        super(NAME, ((Class) PropertyIsBetweenType.class), null, value);
    }

}
