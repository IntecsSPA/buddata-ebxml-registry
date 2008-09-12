
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Or
    extends JAXBElement<BinaryLogicOpType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "Or");

    public Or(BinaryLogicOpType value) {
        super(NAME, ((Class) BinaryLogicOpType.class), null, value);
    }

}
