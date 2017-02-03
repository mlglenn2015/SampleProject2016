package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApplicationParameterSourceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParameterSourceTests.class);

    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    @Before
    public void setUp() {
        assertNotNull(applicationParameterSource);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("ApplicationParameterSourceTests.defaultTest()");
    }

    @Test
    public void testGetParm() {
        String retParm = applicationParameterSource.getParm("parm.request.successful");
        assertNotNull(retParm);
        assertEquals(retParm, "Request Successful");
    }

    /*@Test
    public void testGetParm() {
        String retParm = applicationParameterSource.getParm("parm.request.successful", new Object[], Locale.ENGLISH);
        assertNotNull(retParm);
        assertEquals(retParm, "Request Successful");
    }*/

}
