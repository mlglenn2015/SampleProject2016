package prv.mark.project.common.exception;

import prv.mark.project.common.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.ws.soap.client.SoapFaultClientException;

/**
 * Routes exceptions to the proper handler depending on fault type.
 *
 * @author MLGlenn.
 */
public class ExceptionRouter {

    private static final String SERVER_FAULT_STR = "server";


    /**
     * Routes a {@link SoapFaultClientException} to the proper SOAP Fault type depending on fault
     * type returned.
     *
     * @param logger {@link Logger}
     * @param message {@link String}
     * @param e thrown {@link SoapFaultClientException}
     */
    public static void route(final Logger logger, final String message,
                              final SoapFaultClientException e) {
        logger.trace(message, e);
        route(e);
    }

    /**
     * Routes a {@link SoapFaultClientException} to the proper SOAP Fault type depending on fault
     * type returned.
     *
     * @param e thrown {@link SoapFaultClientException}
     */
    public static void route(final SoapFaultClientException e) {
        if ((e.getFaultCode() != null)
                && (StringUtils.containsIgnoreCase(e.getFaultCode().toString(), SERVER_FAULT_STR))) {
            throw new SOAPServerException(e.getMessage());
        } else {
            throw new SOAPClientException(e.getMessage());
        }
    }

    /**
     * Logs and rethrows the given {@link SOAPException}.
     *
     * @param logger {@link Logger} to log message to
     * @param e Exception being thrown.  This can be a {@link SOAPClientException} or
     * {@link SOAPServerException}.
     * @param message String containing exception message
     */
    public static void logAndThrowSoapException(final Logger logger,
                                                final SOAPException e,
                                                final String message) {
        logger.trace(message, e);
        throw e;
    }

    /**
     * Logs and throws a {@link ApplicationException}.
     *
     * @param logger {@link Logger} to log message to
     * @param message String containing exception message
     */
    public static void logAndThrowApplicationException(final Logger logger,
                                                       final String message) {
        logger.trace(message);
        logger.error(StringUtils.APPLICATION_EXCEPTION);
        throw new ApplicationException();
    }

    /**
     * Logs and throws a {@link ApplicationException}.
     *
     * @param logger {@link Logger} to log message to
     * @param message String containing exception message
     * @param e the exception that was caught
     */
    public static void logAndThrowApplicationException(final Logger logger,
                                                       final String message,
                                                       final Exception e) {
        logger.trace(message, e);
        logAndThrowApplicationException(logger, message, e.toString());
    }

    /**
     * Logs and throws a {@link ApplicationException}.
     * @param logger {@link Logger} to log message to
     * @param message String containing exception message
     * @param exceptionString the exception string description
     */
    public static void logAndThrowApplicationException(final Logger logger,
                                                       final String message,
                                                       final String exceptionString) {
        logger.trace(message);
        logger.error(StringUtils.APPLICATION_EXCEPTION);
        throw new ApplicationException(exceptionString);
    }

    /**
     * Logs and throws a {@link ApplicationException}.
     * @param logger of type {@link String}
     * @param message of type {@link String}
     * @param id of type {@link Long}
     */
    public static void logAndThrowApplicationException(final Logger logger,
                                                       final String message,
                                                       final Long id) {
        logger.trace(message);
        logger.error(StringUtils.APPLICATION_EXCEPTION);
        throw new ApplicationException(message, id);
    }
}
