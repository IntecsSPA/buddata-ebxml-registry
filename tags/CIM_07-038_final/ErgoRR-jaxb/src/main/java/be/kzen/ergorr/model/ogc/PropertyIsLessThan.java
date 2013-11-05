
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class PropertyIsLessThan
    extends JAXBElement<BinaryComparisonOpType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "PropertyIsLessThan");

    public PropertyIsLessThan(BinaryComparisonOpType value) {
        super(NAME, ((Class) BinaryComparisonOpType.class), null, value);
    }

}
