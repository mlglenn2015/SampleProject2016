package prv.mark.project.translog.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.translog.schemas.TransactionLoggerMsgType;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * TODO
 *
 * Created by Owner on 2/19/2017.
 */
@Component("transactionLogMessageSender")
public class TranslogProducerJms implements TransactionLogMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslogProducerJms.class);

    /* Declared in TlogProducerJmsConfig and TlogJmsConfig.xml */
    @Autowired(required = false)
    private JmsTemplate jmsTemplate;


    public TranslogProducerJms() {}

    @Override
    public void sendMessage(final String message) {
        LOGGER.info("TranslogProducerJms.sendMessage(): Entry");
        try {
            if (jmsTemplate != null) {
                LOGGER.info("TranslogProducerJms.sendMessage(): Sending message: [{}]", message);
                jmsTemplate.send(new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(message);
                    }
                });
            } else {
                LOGGER.info("TranslogProducerJms.sendMessage(): jmsTemplate is null");
            }
        } catch (Exception jmse) {
            LOGGER.debug("Unexpected exception caught while sending JMS message: {}", jmse);
            throw new SOAPClientException(jmse.getMessage());
        }
        LOGGER.info("TranslogProducerJms.sendMessage(): Exit");
    }

    @Override
    public void sendMessage(final TransactionLoggerMsgType message) {
        LOGGER.info("TranslogProducerJms.sendMessage(): Entry");
        try {
            if (jmsTemplate != null) {
                LOGGER.info("TranslogProducerJms.sendMessage(): Sending message: [{}]", translogToString(message));
                jmsTemplate.convertAndSend(message);
                /*jmsTemplate.convertAndSend(message, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws JMSException { TODO
                        if (message instanceof TransactionLoggerMsgType) {
                            TransactionLoggerMsgType newMsg = (TransactionLoggerMsgType) message;
                            LOGGER.info("Sending TransactionLoggerMsgType newMsg: date=[{}] type=[{}] text=[{}]",
                                    newMsg.getLogDateTime(), newMsg.getTransactionType(), newMsg.getTransactionDetail());
                        }
                        return message;
                    }
                });*/
            } else {
                LOGGER.info("TranslogProducerJms.sendMessage(): jmsTemplate is null");
            }
        } catch (Exception jmse) {
            LOGGER.debug("Unexpected exception caught while sending JMS message: {}", jmse);
            throw new SOAPClientException(jmse.getMessage());
        }
        LOGGER.info("TranslogProducerJms.sendMessage(): Exit");
    }


    private String translogToString(final TransactionLoggerMsgType message) {
        StringBuffer buffer = new StringBuffer();
        if (message != null) {
            buffer.append("Log Date/Time:[")
                    .append(message.getLogDateTime())
                    .append("]  Transaction Type:[")
                    .append(message.getTransactionType())
                    .append("]  Transaction Detail:[")
                    .append(message.getTransactionDetail())
                    .append("]");
        }
        return buffer.toString();
    }
}
