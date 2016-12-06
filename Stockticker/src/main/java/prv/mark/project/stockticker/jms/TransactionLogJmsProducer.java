package prv.mark.project.stockticker.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.stocks.transloggertypes.schemas.TransactionLoggerMsgType;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * JMS client that places messages on the Transaction Log queue.
 *
 * @author mlglenn on 12/6/2016.
 */
@Component
@Profile({"transactionlogger", "test"})
public class TransactionLogJmsProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogJmsProducer.class);

    @Autowired
    private ApplicationMessageSource applicationMessageSource;

    @Autowired
    @Qualifier("stocksChannel")
    private MessageChannel stocksChannel; //TODO : BeanCreationException: Could not autowire field

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    @Autowired
    @Qualifier("stocksJmsMarshaller")
    private Jaxb2Marshaller stocksJmsMarshaller;


    @Transactional
    public void sendTransactionLogMessage() { //TODO finish
        LOGGER.info("*** sendTransactionLogMessage() ***");
        Instant startTime = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        LOGGER.info("Start Time: {}..{}", startTime.toString(), startTime.toEpochMilli());
        TransactionLoggerMsgType loggerMsg = new TransactionLoggerMsgType();
        LOGGER.info("Creating TransactionLoggerMsgType message:{}", loggerMsg);

        //TODO populate the message or pass it in ???

        Instant endTime = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        LOGGER.info("End Time: {}..{}", endTime.toString(), endTime.toEpochMilli());

        //TODO If valid msg, then send, else put on error queue

        if (isValidMsg(loggerMsg)) {
            LOGGER.info("*** Successfully processed message {}: {} in {} ms. ***",
                    loggerMsg.getTransactionType(), loggerMsg.getTransactionDetail(),
                    Duration.between(startTime, endTime).toMillis());

            stocksChannel.send(MessageBuilder.withPayload(loggerMsg).build());
            //return getTransLogObjectFactory().createTransactionLoggerMsg(loggerMsg); TODO ?
        } else {
            LOGGER.info("*** Message failed validation {}: {} ***",
                    loggerMsg.getTransactionType(), loggerMsg.getTransactionDetail());
            errorChannel.send(MessageBuilder.withPayload(loggerMsg).build());
            //return null; TODO ?
        }
    }



    private Boolean isValidMsg(final TransactionLoggerMsgType loggerMsg) {
        Boolean isValid = false;

        if (1==1) { //TODO

            isValid = true;
        } else {
            LOGGER.info("*** isValidMsg(): Transaction Log Message {} is INVALID ***", loggerMsg.getTransactionDetail());
            if (LOGGER.isDebugEnabled()) {
                //traceMsg(loggerMsg); TODO
            }
        }
        return isValid;
    }

    private prv.mark.project.stocks.commontypes.schemas.ObjectFactory getCommonObjectFactory() {
        return new prv.mark.project.stocks.commontypes.schemas.ObjectFactory();
    }

    private prv.mark.project.stocks.stocktickertypes.schemas.ObjectFactory getStockTickerObjectFactory() {
        return new prv.mark.project.stocks.stocktickertypes.schemas.ObjectFactory();
    }

    private prv.mark.project.stocks.transloggertypes.schemas.ObjectFactory getTransLogObjectFactory() {
        return new prv.mark.project.stocks.transloggertypes.schemas.ObjectFactory();
    }
}
