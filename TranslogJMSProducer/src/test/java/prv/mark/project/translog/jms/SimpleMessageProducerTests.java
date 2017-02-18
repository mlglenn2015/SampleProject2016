package prv.mark.project.translog.jms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import javax.jms.JMSException;

/**
 * JUnit testst for the {@link SimpleMessageProducer}.
 *
 * Created by Owner on 2/18/2017.
 */
public class SimpleMessageProducerTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMessageProducerTests.class);


    @Before
    public void setUp() {
        LOGGER.debug("***** SimpleMessageProducerTests.setUp() *****");
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** SimpleMessageProducerTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** SimpleMessageProducerTests.defaultTest() *****");
    }


    //TODO
    //@Test
    public void testSendMessage() {
        SimpleMessageProducer producer = new SimpleMessageProducer();
        try {
            producer.sendMessage();
        } catch (JMSException jmse) {
            //TODO handle
        }
    }
}
