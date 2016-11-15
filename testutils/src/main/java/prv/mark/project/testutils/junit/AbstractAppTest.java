package prv.mark.project.testutils.junit;

import prv.mark.project.testutils.config.TestConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Generic unit test foundation.
 *
 * <p> NOTE: Classes deriving from this class <b><i>must provide</i></b> the {@link
 * ContextConfiguration} annotation specific to the particular test's Spring
 * configuration. </p>
 *
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@DirtiesContext
@ActiveProfiles("test")
public abstract class AbstractAppTest extends AbstractJUnit4SpringContextTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAppTest.class);

    /**
     * Method used to bootstrap test class.
     */
    @BeforeClass
    public static void bootstrap() {
        LOGGER.debug("@BeforeClass:AbstractAppTest.bootstrap()");
    }

    /**
     * Method called before every test is run.
     *
     * <p>Useful for initializing variables used in every test.</p>
     */
    @Before
    public void setUp() {
        LOGGER.debug("@Before:AbstractAppTest.setUp()");
    }

    /**
     * Method called after each test is run.
     */
    @After
    public void tearDown() {
        LOGGER.debug("@After:AbstractAppTest.tearDown()");
    }

    /**
     * Method called after all tests are completed.
     */
    @AfterClass
    public static void destroy() {
        LOGGER.debug("@AfterClass:AbstractAppTest.destroy()");
    }
}
