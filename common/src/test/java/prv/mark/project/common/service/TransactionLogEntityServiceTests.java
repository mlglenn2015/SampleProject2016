package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.TransactionDto;
import prv.mark.project.common.entity.TransactionLogEntity;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the {@link TransactionLogEntityService}.
 *
 * @author mlglenn
 */
public class TransactionLogEntityServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogEntityServiceTests.class);

    @Autowired
    private TransactionLogEntityService transactionLogEntityService;

    @Before
    public void setUp() {
        assertNotNull(transactionLogEntityService);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("TransactionLogEntityServiceTests.defaultTest()");
    }

    @Test
    public void testTransactionLog() {
        TransactionDto dto = buildDto();
        assertNotNull(dto);

        TransactionLogEntity entity = buildTransactionLog(dto);
        assertNotNull(entity);

        TransactionLogEntity retTransactionLogEntity = insertTransactionLog(entity);
        assertNotNull(retTransactionLogEntity);
        assertTrue(retTransactionLogEntity.getId() > 0);

        Optional<TransactionLogEntity> newTransactionLog
                = transactionLogEntityService.findById(retTransactionLogEntity.getId());
        assertNotNull(newTransactionLog);

        assertEquals(retTransactionLogEntity.getTransactionType(), newTransactionLog.get().getTransactionType());
    }

    @Test
    public void testFindAll() {
        List<TransactionLogEntity> entityList = new ArrayList<>();
        entityList = transactionLogEntityService.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindByTransactionType() {
        assertNotNull(transactionLogEntityService.findByTransactionType("STOCK PURCHASE"));
    }

    @Test
    public void testFindByLogDateTime() {
        List<TransactionLogEntity> transactionLogEntityList = new ArrayList<>();
        transactionLogEntityList = transactionLogEntityService.findByLogDateTime(DateUtils.getDate());
        assertNotNull(transactionLogEntityList);
    }


    private TransactionDto buildDto() {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionType("STOCK PURCHASE");
        dto.setTransactionDetail("STOCK PURCHASE TRANSACTION");
        dto.setLogDateTime(DateUtils.getLocalDateTime());
        return dto;
    }

    private TransactionLogEntity buildTransactionLog(final TransactionDto dto) {
        TransactionLogEntity entity = new TransactionLogEntity();
        entity.setId(null);
        entity.setLogDateTime(DateUtils.getDateFromLocalDateTime(dto.getLogDateTime()));
        entity.setTransactionType(dto.getTransactionType());
        entity.setTransactionData(dto.getTransactionDetail());
        return entity;
    }

    private TransactionLogEntity insertTransactionLog(
            final TransactionLogEntity entity) {

        LOGGER.debug("TransactionLogEntityServiceTests.insertTransactionLog()");
        TransactionLogEntity returnEntity = new TransactionLogEntity();
        try {
            returnEntity = transactionLogEntityService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionLogEntity entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionLogEntity entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
