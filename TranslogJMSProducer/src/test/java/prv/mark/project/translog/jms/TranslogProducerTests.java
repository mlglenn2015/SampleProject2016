package prv.mark.project.translog.jms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

/**
 * TODO
 * Created by Owner on 2/19/2017.
 */
public class TranslogProducerTests { //extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslogProducerTests.class);


    @Before
    public void setUp() {
        LOGGER.debug("***** TranslogProducerTests.setUp() *****");
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** TranslogProducerTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** TranslogProducerTests.defaultTest() *****");
    }



}
