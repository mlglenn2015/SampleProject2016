package prv.mark.project.common.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * SOAP Fault returned when client fails to authenticate.
 *
 * @author mlglenn
 */
@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://prv.mark.project/stocks}100",
        faultStringOrReason = "General Error. This is returned when an error is encountered that does not match a specific code.")
public class SOAPGeneralFault extends SOAPException {

    public SOAPGeneralFault() {
        super();
    }

    public SOAPGeneralFault(final String message) {
        super(message);
    }

}
