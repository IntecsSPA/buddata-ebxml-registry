
package be.kzen.ergorr.model.ogc;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Not
    extends JAXBElement<UnaryLogicOpType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/ogc", "Not");

    public Not(UnaryLogicOpType value) {
        super(NAME, ((Class) UnaryLogicOpType.class), null, value);
    }

}
