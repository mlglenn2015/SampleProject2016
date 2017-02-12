package prv.mark.project.stockservice.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit tests for the MessageBuilder utility class.
 *
 * Created by Owner on 2/12/2017.
 */
public class MessageBuilderUtilsTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBuilderUtilsTests.class);

    @Before
    public void setUp() {
        LOGGER.debug("***** MessageBuilderUtilsTests.setUp() *****");
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** MessageBuilderUtilsTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** MessageBuilderUtilsTests.defaultTest() *****");
    }

    @Test
    public void testGetSoapMessageFromString() {
        LOGGER.debug("***** MessageBuilderUtilsTests.testGetSoapMessageFromString() *****");
        assertNotNull(MessageBuilderUtils.getSoapMessageFromString("200", "OK", "Request Successful"));
    }

}
