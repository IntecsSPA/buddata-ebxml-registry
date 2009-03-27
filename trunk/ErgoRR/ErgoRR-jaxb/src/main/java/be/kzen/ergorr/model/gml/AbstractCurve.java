
package be.kzen.ergorr.model.gml;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class AbstractCurve
    extends JAXBElement<AbstractCurveType>
{

    protected final static QName NAME = new QName("http://www.opengis.net/gml", "_Curve");

    public AbstractCurve(AbstractCurveType value) {
        super(NAME, ((Class) AbstractCurveType.class), null, value);
    }

}
