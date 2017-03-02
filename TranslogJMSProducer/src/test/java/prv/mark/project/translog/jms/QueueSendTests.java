package prv.mark.project.translog.jms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prv.mark.project.common.util.StringUtils;

import javax.jms.JMSException;
import javax.naming.InitialContext;

/**
 * TODO
 *
 * Created by Owner on 2/19/2017.
 */
public class QueueSendTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueSendTests.class);

    // Defines the queue.
    public final static String QUEUE="jms/TlogJMSQueue";

    @Before
    public void setUp() {
        LOGGER.debug("***** QueueSendTests.setUp() *****");
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** QueueSendTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** QueueSendTests.defaultTest() *****");
    }


    //@Test TODO all tests are disabled until i can mock JMS
    public void testSendMessage() {
        LOGGER.debug("***** QueueSendTests.testSendMessage() *****");
        String testMessage = "Test Message";
        QueueSend queueSend = new QueueSend();

        try {
            InitialContext ic = queueSend.getInitialContext("t3://localhost:7001");
            queueSend.init(ic, QUEUE);
            if (StringUtils.isNotEmpty(testMessage)) {
                LOGGER.info("Sending message: {}", testMessage);
                queueSend.send(testMessage);
            }

        } catch (Exception e) {
            LOGGER.info("Exception caught: {}", e);
        } finally {
            try {
                LOGGER.info("Closing connections ...");
                queueSend.close();
            } catch (JMSException jmse) {
                LOGGER.info("Exception caught while closing connections: {}", jmse);
            }
        }
    }

}
