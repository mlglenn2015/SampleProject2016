package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import prv.mark.project.common.entity.ApplicationParameterEntity;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the ApplicationParameterRepository.
 *
 * @author mlglenn on 12/12/2016.
 */
public class ApplicationParametersRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParametersRepositoryTests.class);

    @Autowired
    private ApplicationParameterRepository applicationParameterRepository;

    @Before
    public void setUp() {
        assertNotNull(applicationParameterRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("ApplicationParametersRepositoryTests.defaultTest()");
    }

    @Test
    public void testFindAll() {
        List<ApplicationParameterEntity> entityList = new ArrayList<>();
        entityList = applicationParameterRepository.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindByByParameterKey() {
        ApplicationParameterEntity applicationMessage =
                applicationParameterRepository.findEnabledByParameterKey("parm.validation.requestheader.source", true);
        assertTrue(StringUtils.isNotEmpty(applicationMessage.getParameterValue()));
        assertEquals(applicationMessage.getParameterValue(), "STOCKTICKER_20170131");
    }

    @Test
    public void testFindByInvalidByParameterKey() {
        assertNull(applicationParameterRepository.findEnabledByParameterKey("TEST", true));
    }
}
