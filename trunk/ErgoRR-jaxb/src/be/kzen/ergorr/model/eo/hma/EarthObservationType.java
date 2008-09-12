
package be.kzen.ergorr.model.eo.hma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import be.kzen.ergorr.model.gml.ObservationType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Earth Obervation Product description
 * 
 * <p>Java class for EarthObservationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarthObservationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}ObservationType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarthObservationType")
@XmlSeeAlso({
    be.kzen.ergorr.model.eo.atm.EarthObservationType.class,
    be.kzen.ergorr.model.eo.opt.EarthObservationType.class,
    be.kzen.ergorr.model.eo.sar.EarthObservationType.class
})
@XmlRootElement
public class EarthObservationType
    extends ObservationType
{


}
