package prv.mark.project.transactionlogger.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JMS listener MDB to consume Transaction Log messages and persist to the database.
 *
 * Sources:
 * https://github.com/icarus-dave/weblogic-jms-spring/blob/master/src/main/java/com/test/weblogic/SimpleMDB.java
 * https://www.youtube.com/watch?v=GOsyO1LZA5k
 * https://youtu.be/9WGSVnhlOHE
 *
 * Created by Owner on 2/18/2017.
 */
public class TransactionLogListenerMDB implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(TransactionLogListenerMDB.class);
    public List<String> messages = Collections.synchronizedList(new ArrayList<String>());

    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            logger.info("Consumed message: " + msg.getText());  //TODO save to the database
        } catch (JMSException e) {

            // TODO Custom Exception handling
            e.printStackTrace();
        }
    }

}
