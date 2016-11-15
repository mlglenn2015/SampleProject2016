package prv.mark.project.testutils.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * Generic unit test foundation for web service endpoints.
 *
 * @author
 */

public abstract class AbstractAppWebServiceEndpointTest extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAppWebServiceEndpointTest.class);

    @BeforeTransaction
    public void beforeTransaction() {
        LOGGER.debug("@BeforeTransaction:AbstractAppWebServiceEndpointTest.beforeTransaction()");
        LOGGER.trace("Starting web service test transaction");
    }

    @AfterTransaction
    public void afterTransaction() {
        LOGGER.debug("@AfterTransaction:AbstractAppWebServiceEndpointTest.afterTransaction()");
        LOGGER.trace("End of web service test transaction");
    }
}
