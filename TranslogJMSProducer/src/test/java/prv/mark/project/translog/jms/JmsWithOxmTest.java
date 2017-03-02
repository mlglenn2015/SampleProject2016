package prv.mark.project.translog.jms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import prv.mark.project.testutils.junit.AbstractAppTest;

import static org.junit.Assert.assertNotNull;

/**
 * http://chariotsolutions.com/blog/post/sending-beans-as-xml-with-jmstemplate/
 *
 * Created by Owner on 2/26/2017.
 */
public class JmsWithOxmTest extends AbstractAppTest {

    private static final Logger logger = LoggerFactory.getLogger(JmsWithOxmTest.class);
    private static final String TEST_DEST = "oxmTestQueue";

    /*@Autowired TODO
    JmsTemplate jmsTemplate;*/


    @Before
    public void setUp() {
        logger.debug("***** JmsWithOxmTest.setUp() *****");
    }

    @After
    public void tearDown() {
        logger.debug("***** JmsWithOxmTest.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        logger.debug("***** JmsWithOxmTest.defaultTest() *****");
    }


    /*@Test
    public void testSendingMessage() {
        Account account = generateTestMessage();
        jmsTemplate.convertAndSend(TEST_DEST, account,
        new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message)
        throws JMSException {
                if (message instanceof BytesMessage) {
                    BytesMessage messageBody = (BytesMessage) message;
                    messageBody.reset();
                    Long length = messageBody.getBodyLength();
                    logger.debug("***** MESSAGE LENGTH is {} bytes",
                            length);
                    byte[] byteMyMessage = new byte[length.intValue()];
                    int red = messageBody.readBytes(byteMyMessage);
                    logger.debug(
                            "***** SENDING MESSAGE - n" +
                            "<!-- MSG START -->n{}n<!-- MSG END -->",
                    new String(byteMyMessage));
                }
                return message;
            }
        });
        Account account2 = (Account) jmsTemplate.receiveAndConvert(TEST_DEST);
        assertNotNull("Account MUST return from JMS", account2);
        assertEquals("Name MUST match", account.getName(), account2.getName());
        assertEquals("Description MUST match", account.getDescription(),
                account2.getDescription());
        assertEquals("Balance MUST match", account.getBalance(),
                account2.getBalance());
    }

    private Account generateTestMessage() {
        Account account = new Account();
        account.setBalance(new BigDecimal(12345.67));
        account.setDescription("A no account varmint");
        account.setName("Waskally Wabbit - Gordon Test June 2011");
        logger.debug("Generated Test Message: " + account.toString());
        return account;
    }*/

}
