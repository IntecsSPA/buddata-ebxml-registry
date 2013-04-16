
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class And
    extends JAXBElement<BinaryLogicOpType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "And");

    public And() {
        this(new BinaryLogicOpType());
    }

    public And(BinaryLogicOpType value) {
        super(NAME, ((Class) BinaryLogicOpType.class), null, value);
    }

}
