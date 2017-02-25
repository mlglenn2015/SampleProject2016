package prv.mark.project.translog.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import prv.mark.project.translog.schemas.TransactionLoggerMsgType;


/**
 * Interceptor to log outgoing {@link TransactionLoggerMsgType} messages.
 *
 * @author mlglenn on 2/15/2017.
 */
@Component
public class TranslogProducerInterceptor extends ChannelInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslogProducerInterceptor.class);

    /**
     * Interceptor that logs important data from outgoing messages.
     *
     * @param message
     * @param messageChannel
     * @return {@link org.springframework.messaging.Message}
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        LOGGER.debug("*** Sending Stock Service Transaction Log message {} ***", message);
        return message;
    }

}
