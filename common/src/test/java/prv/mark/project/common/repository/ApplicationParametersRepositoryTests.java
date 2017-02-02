package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import prv.mark.project.common.entity.ApplicationMessages;
import prv.mark.project.common.entity.ApplicationParameters;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the ApplicationParametersRepository.
 *
 * @author mlglenn on 12/12/2016.
 */
public class ApplicationParametersRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParametersRepositoryTests.class);

    @Autowired
    private ApplicationParametersRepository applicationParametersRepository;

    @Before
    public void setUp() {
        assertNotNull(applicationParametersRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("ApplicationParametersRepositoryTests.defaultTest()");
    }

    @Test
    public void testFindAll() {
        List<ApplicationParameters> entityList = new ArrayList<>();
        entityList = applicationParametersRepository.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindByMessageKey() {
        ApplicationParameters applicationMessage =
                applicationParametersRepository.findActiveByPropKey("parm.validation.requestheader.source");
        assertTrue(StringUtils.isNotEmpty(applicationMessage.getPropProperty()));
        assertEquals(applicationMessage.getPropProperty(), "STOCKTICKER_20170131");
    }

    @Test
    public void testFindByInvalidMessageKey() {
        assertNull(applicationParametersRepository.findActiveByPropKey("TEST"));
    }
}
