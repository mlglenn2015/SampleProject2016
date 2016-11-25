package prv.mark.project.testutils.junit;

import prv.mark.project.testutils.config.TestDataConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Generic transactional unit test foundation.
 * <p> Most NBI Unit tests will derive from this class, as it provides database and transactional facilities used by
 * the applciation. </p>
 * <p> NOTE: Classes deriving from this class <b><i>must provide</i></b> the
 * {@link ContextConfiguration} annotation specific to the particular test's Spring configuration. </p>
 *
 * @author mlglenn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class})
@ComponentScan(basePackages = "prv.mark.project")
@ActiveProfiles("test")
@DirtiesContext
@Transactional
public abstract class AbstractAppTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAppTransactionalTest.class);

    /**
     * Method called after instantiation of this object.
     */
    @PostConstruct
    public static void postConstruct() {
        LOGGER.debug("@PostConstruct:AbstractAppTransactionalTest.postConstruct()");
    }

    /**
     * Method used to bootstrap test class.
     */
    @BeforeClass
    public static void bootstrap() {
        LOGGER.debug("@BeforeClass:AbstractAppTransactionalTest.bootstrap()");
    }

    /**
     * Method called after all tests are completed.
     */
    @AfterClass
    public static void destroy() {
        LOGGER.debug("@AfterClass:AbstractAppTransactionalTest.destroy()");
    }

    /**
     * Method called before every test is run.
     * <p>
     * <p>Useful for initializing variables used in every test.</p>
     */
    @Before
    public void setUp() {
        LOGGER.debug("@Before:AbstractAppTransactionalTest.setUp()");
    }

    /**
     * Method called after each test is run.
     */
    @After
    public void tearDown() {
        LOGGER.debug("@After:AbstractAppTransactionalTest.tearDown()");
    }

    @BeforeTransaction
    public void beforeTransaction() {
        LOGGER.debug("@BeforeTransaction:AbstractAppTransactionalTest.beforeTransaction()");
        LOGGER.trace("Starting transaction");
    }

    @AfterTransaction
    public void afterTransaction() {
        LOGGER.debug("@AfterTransaction:AbstractAppTransactionalTest.afterTransaction()");
        LOGGER.trace("End of transaction");
    }

    /**
     * Method called before destruction of this object.
     */
    @PreDestroy
    public static void preDestroy() {
        LOGGER.debug("@PreDestroy:AbstractAppTransactionalTest.preDestroy()");
    }

}
