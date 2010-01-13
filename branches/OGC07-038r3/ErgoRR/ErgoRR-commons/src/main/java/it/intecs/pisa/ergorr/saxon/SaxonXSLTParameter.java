

package it.intecs.pisa.ergorr.saxon;

import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmAtomicValue;

/**
 *
 * @author Andrea Marongiu
 */
public class SaxonXSLTParameter {
    private QName paramName;
    private XdmAtomicValue paramValue;

    
   public SaxonXSLTParameter(String parameterName, String parmaterValue){
       this.paramName=new QName(parameterName);
       this.paramValue=new XdmAtomicValue(parmaterValue);
   }


    /**
     * @return the paramName
     */
    public QName getParamName() {
        return paramName;
    }

    /**
     * @return the paramValue
     */
    public XdmAtomicValue getParamValue() {
        return paramValue;
    }
}
