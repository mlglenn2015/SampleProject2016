package prv.mark.project.stockservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * Specialized exception that translates to a SOAP Fault when thrown.
 *
 * This type of exception is thrown if a SOAP fault needs to be sent back to a web client. </p>
 *
 * @author Mark Glenn
 */
@SoapFault(faultCode = FaultCode.CLIENT)
public class StockTickerSoapException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerSoapException.class);

    /**
     * {@inheritDoc}
     */
    public StockTickerSoapException(String message) {
        super(message);
        LOGGER.debug("StockTickerSoapException(): {}", message);
    }
}
