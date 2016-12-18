package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import static org.junit.Assert.assertNotNull;


/**
 * JUnit tests for the {@link ApplicationMessagesRepository}.
 *
 * @author mlglenn
 */
//@ContextConfiguration(classes = {TestUtilConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
public class ApplicationMessagesRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMessagesRepositoryTests.class);

    @Autowired
    private ApplicationMessagesRepository applicationMessagesRepository;

    @Before
    public void setUp() {
        assertNotNull(applicationMessagesRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("ApplicationMessagesRepositoryTests.defaultTest()");
    }

    /*@Test
    public void testFindByMessageKey() {
        ApplicationMessages applicationMessage = applicationMessagesRepository.findByMessageKey("error.invalid.usstate");
        assertTrue(StringUtils.isNotEmpty(applicationMessage.getMessage()));
        assertEquals(applicationMessage.getMessage(), "Input is not a valid US State.");
    }

    @Test
    public void testFindByInvalidMessageKey() {
        assertNull(applicationMessagesRepository.findByMessageKey("TEST"));
    }*/
}
