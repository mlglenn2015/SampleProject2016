package prv.mark.project.translogproducer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * TODO
 * Created by Owner on 12/10/2016.
 */
@Component
public class TbdLoggingInterceptor extends ChannelInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TbdLoggingInterceptor.class);

    /**
     * Interceptor that logs important data from incoming messages.
     *
     * @param message
     * @param messageChannel
     * @return {@link org.springframework.messaging.Message}
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        LOGGER.debug("*** Received Stock Ticker Transaction Log message {} ***", message);
        Object payload = message.getPayload();
        MessageHeaders messageHeaders = message.getHeaders();
        LOGGER.debug("*** Correlation id: {}, Timestamp: {}, Detail: {}, Payload: \n{}",
                messageHeaders.getId(),
                Date.from(Instant.ofEpochMilli(messageHeaders.getTimestamp())),
                messageHeaders.get("detailMessage"),
                payload);
        return message;
    }
}
