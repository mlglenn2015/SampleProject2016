package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnit tests for the {@link ApplicationParameterSource}.
 *
 * @author mlglenn
 */
public class ApplicationMessageSourceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMessageSourceTests.class);

    @Autowired
    private ApplicationMessageSource applicationMessageSource;

    @Before
    public void setUp() {
        assertNotNull(applicationMessageSource);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("ApplicationMessageSourceTests.defaultTest()");
    }

    @Test
    public void testGetParm() {
        String retParm = applicationMessageSource.getMessage("error.invalid.uszip");
        assertNotNull(retParm);
        assertEquals(retParm, "Input is not a valid US Zip Code.");
    }
}
