package prv.mark.project.translog.jms;

import net.javacrumbs.smock.common.MessageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import prv.mark.project.common.util.DateUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Class to send a JMS message.
 *
 * Sources:
 * https://bsnyderblog.blogspot.com/2010/02/using-spring-jmstemplate-to-send-jms.html
 * http://www.springbyexample.org/examples/simple-spring-jms.html
 * https://www.youtube.com/watch?v=GOsyO1LZA5k
 * https://youtu.be/9WGSVnhlOHE
 *
 * Created by Owner on 2/16/2017.
 */
public class SimpleMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleMessageProducer.class);

    @Autowired
    protected JmsTemplate jmsTemplate;

    protected int numberOfMessages = 100;


    /**
     * TODO
     * @throws JMSException
     */
    public void sendMessage() throws JMSException {

        StringBuilder payload = new StringBuilder();
        payload.append("Message sent at: ").append(DateUtils.getDate());

        //MessageCreator messageCreator = new MessageCreator();

        /*jmsTemplate.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage(payload.toString());
                    //message.setIntProperty("messageCount", i);
                    message.setIntProperty("messageCount", 1);
                    //logger.info("Sending message number [" + i + "]");
                    logger.info("Sending message number [" + 1 + "]");
                    return message;
                }
            });*/

        //}

    }

    /**
     * TODO
     * @throws JMSException
     */
    /*public void sendMessage() throws JMSException {

        StringBuilder payload = new StringBuilder();
        //for (int i = 0; i < numberOfMessages; ++i) {
        //payload = new StringBuilder();
        //payload.append("Message [").append(i).append("] sent at: ").append(DateUtils.getDate());
        payload.append("Message sent at: ").append(DateUtils.getDate());

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(payload.toString());
                //message.setIntProperty("messageCount", i);
                message.setIntProperty("messageCount", 1);
                //logger.info("Sending message number [" + i + "]");
                logger.info("Sending message number [" + 1 + "]");
                return message;
            }
        });

        //}

    }*/
}
