
package be.kzen.ergorr.interfaces.soap.csw;

import javax.xml.ws.WebFault;
import be.kzen.ergorr.model.ows.ExceptionReport;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.4-hudson-208-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "ExceptionReport", targetNamespace = "http://www.opengis.net/ows/1.1")
public class ServiceExceptionReport
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ExceptionReport faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ServiceExceptionReport(String message, ExceptionReport faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ServiceExceptionReport(String message, ExceptionReport faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
    
    /**
     * 
     * @param message
     * @param cause
     */
    public ServiceExceptionReport(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 
     * @param message
     */
    public ServiceExceptionReport(String message) {
        super(message);
    }

    /**
     * 
     * @return
     *     returns fault bean: be.kzen.ergorr.model.ows.ExceptionReport
     */
    public ExceptionReport getFaultInfo() {
        return faultInfo;
    }

}