package prv.mark.project.stockticker.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;
import prv.mark.xml.translogger.schema.TransactionLoggerMsgType;

/**
 * Interceptor to log outgoing {@link TransactionLoggerMsgType} messages.
 *
 * @author mlglenn on 12/3/2016.
 */
@Component
public class StocksLoggingInterceptor extends ChannelInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksLoggingInterceptor.class);

    /**
     * Interceptor that logs important data from outgoing messages.
     *
     * @param message
     * @param messageChannel
     * @return {@link org.springframework.messaging.Message}
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        LOGGER.debug("*** Sending Stock Ticker Transaction Log message {} ***", message);
        return message;
    }
}
