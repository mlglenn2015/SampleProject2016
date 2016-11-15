package prv.mark.project.common.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * Specialized exception that translates to a SOAP Fault when thrown.
 *
 * <p>
 *  This exception MUST BE THROWN when catching an exception if a SOAP fault needs to
 *  be sent back to the caller of a given web service method.
 * </p>
 *
 * @author mlglenn.
 */
@SoapFault(faultCode = FaultCode.SERVER)
public class SOAPServerException extends SOAPException {

    public SOAPServerException(final String message) {
        super(message);
    }

}
