package prv.mark.project.translogproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * Specialized exception that translates to a SOAP Fault when thrown.
 *
 * <p> This exception MUST BE THROWN when catching an exception if a SOAP fault needs to be sent back to the caller of a
 * given web service method. </p>
 *
 * @author
 */
@SoapFault(faultCode = FaultCode.CLIENT)
public class TranslogProducerException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslogProducerException.class);

    /**
     * {@inheritDoc}
     */
    public TranslogProducerException(String message) {
        super(message);
        LOGGER.debug("TranslogProducerException(): {}", message);
    }
}
