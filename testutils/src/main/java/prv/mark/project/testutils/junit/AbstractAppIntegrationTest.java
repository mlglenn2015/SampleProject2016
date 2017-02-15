package prv.mark.project.testutils.junit;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for web service integration tests.
 *
 * @author mlglenn
 */
@ActiveProfiles({"integration"})
public abstract class AbstractAppIntegrationTest extends AbstractAppWebServiceEndpointTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAppIntegrationTest.class);

    @Override
    @Before
    public void setUp() {
        LOGGER.debug("@Before: AbstractAppIntegrationTest.setUp()");
    }

}
