package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import java.util.Locale;

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

    @Test(expected = NoSuchMessageException.class)
    public void testNoSuchMessage() {
        String retParm = applicationMessageSource.getMessage("invalid");
    }

    @Test
    public void testGetParmFirstForm() {
        String retParm = applicationMessageSource.getMessage("error.invalid.uszip");
        assertNotNull(retParm);
        assertEquals(retParm, "Input is not a valid US Zip Code.");
    }

    @Test
    public void testGetParmSecondForm() {
        String[] obj = {"1", "2", "3"};
        String retParm = applicationMessageSource.getMessage("error.invalid.uszip", obj, "Test", Locale.ENGLISH);
        assertNotNull(retParm);
        assertEquals(retParm, "Input is not a valid US Zip Code.");
    }

    @Test
    public void testGetParmThirdForm() {
        String[] obj = {"1", "2", "3"};
        String retParm = applicationMessageSource.getMessage("error.invalid.uszip", obj, Locale.ENGLISH);
        assertNotNull(retParm);
        assertEquals(retParm, "Input is not a valid US Zip Code.");
    }
}
