package prv.mark.project.common.exception;

import prv.mark.project.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception class for record save operations.
 *
 * @author mlglenn.
 */
public class ApplicationException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(prv.mark.project.common.exception.ApplicationException.class);

    /**
     * Default constructor.
     */
    public ApplicationException() {
        super(StringUtils.APPLICATION_EXCEPTION);
        LOGGER.error(StringUtils.APPLICATION_EXCEPTION);
    }

    /**
     * Constructor with message.
     * @param message String containing exception message
     */
    public ApplicationException(final String message) {
        super(new StringBuffer()
                .append(StringUtils.APPLICATION_EXCEPTION).append(StringUtils.BLANK)
                .append(message).toString());
        LOGGER.error(StringUtils.APPLICATION_EXCEPTION);
    }

    /**
     * Constructor with message.
     * @param message String containing exception message
     * @param id Long containing record ID
     */
    public ApplicationException(final String message, final Long id) {
        super(new StringBuffer()
                .append(StringUtils.APPLICATION_EXCEPTION).append(StringUtils.BLANK)
                .append(message.replace("{}", id.toString())).toString());
        LOGGER.error(StringUtils.APPLICATION_EXCEPTION);
    }
}
