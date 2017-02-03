package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.domain.TransactionDto;
import prv.mark.project.common.entity.StockOrder;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.repository.StockOrderRepositoryTests;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
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
 * JUnit tests for the {@link TransactionLogService}.
 *
 * @author mlglenn
 */
public class TransactionLogServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogServiceTests.class);

    @Autowired
    private TransactionLogService transactionLogService;

    @Before
    public void setUp() {
        assertNotNull(transactionLogService);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("TransactionLogServiceTests.defaultTest()");
    }

    @Test
    public void testTransactionLog() {
        TransactionDto dto = buildDto();
        assertNotNull(dto);

        prv.mark.project.common.entity.TransactionLog entity = buildTransactionLog(dto);
        assertNotNull(entity);

        prv.mark.project.common.entity.TransactionLog retTransactionLog = insertTransactionLog(entity);
        assertNotNull(retTransactionLog);
        assertTrue(retTransactionLog.getId() > 0);

        Optional<TransactionLog> newTransactionLog
                = transactionLogService.findById(retTransactionLog.getId());
        assertNotNull(newTransactionLog);

        assertEquals(retTransactionLog.getTransactionType(), newTransactionLog.get().getTransactionType());
    }

    @Test
    public void testFindAll() {
        List<TransactionLog> entityList = new ArrayList<>();
        entityList = transactionLogService.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }


    private TransactionDto buildDto() {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionType("STOCK PURCHASE");
        dto.setTransactionDetail("STOCK PURCHASE TRANSACTION");
        dto.setLogDateTime(DateUtils.getLocalDateTime());
        return dto;
    }

    private prv.mark.project.common.entity.TransactionLog buildTransactionLog(final TransactionDto dto) {
        prv.mark.project.common.entity.TransactionLog entity = new prv.mark.project.common.entity.TransactionLog();
        entity.setId(null);
        entity.setLogDateTime(DateUtils.getDateFromLocalDateTime(dto.getLogDateTime()));
        entity.setTransactionType(dto.getTransactionType());
        entity.setTransactionData(dto.getTransactionDetail());
        return entity;
    }

    private prv.mark.project.common.entity.TransactionLog insertTransactionLog(
            final prv.mark.project.common.entity.TransactionLog entity) {

        LOGGER.debug("TransactionLogServiceTests.insertTransactionLog()");
        prv.mark.project.common.entity.TransactionLog returnEntity = new prv.mark.project.common.entity.TransactionLog();
        try {
            returnEntity = transactionLogService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionLog entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionLog entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
