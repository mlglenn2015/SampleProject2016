package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.domain.TransactionDto;
import prv.mark.project.common.entity.StockPrice;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.exception.ExceptionRouter;
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
 * JUnit tests for the {@link TransactionLogRepository}.
 *
 * @author mlglenn
 */
public class TransactionLogRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogRepositoryTests.class);

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Before
    public void setUp() {
        assertNotNull(transactionLogRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("TransactionLogRepositoryTests.defaultTest()");
    }

    @Test
    public void testTransactionLog() {
        TransactionDto dto = buildDto();
        assertNotNull(dto);

        prv.mark.project.common.entity.TransactionLog entity = buildEntity(dto);
        assertNotNull(entity);

        prv.mark.project.common.entity.TransactionLog savedEntity = insertEntity(entity);
        assertNotNull(savedEntity);
        assertTrue(savedEntity.getId() > 0);

        Optional<TransactionLog> newEntity
                = transactionLogRepository.findById(savedEntity.getId());
        assertNotNull(newEntity);

        assertEquals(savedEntity.getTransactionType(), newEntity.get().getTransactionType());
    }

    @Test
    public void testFindById() {
        Optional<TransactionLog> tLog = transactionLogRepository.findById(1L);
        assertNotNull(tLog);
        assertNotNull(tLog.get().getLogDateTime());
    }

    @Test
    public void testFindByTransactionType() {
        List<TransactionLog> tLog = new ArrayList<>();
        tLog = transactionLogRepository.findByTransactionType("STOCK PURCHASE");
        assertNotNull(tLog);
        assertTrue(tLog.size() > 0);
    }

    @Test
    public void testFindByInvalidTransactionType() {
        List<TransactionLog> tLog = new ArrayList<>();
        tLog = transactionLogRepository.findByTransactionType("TEST");
        assertTrue(tLog.size() == 0);
    }


    private TransactionDto buildDto() {
        TransactionDto dto = new TransactionDto();
        dto.setLogDateTime(DateUtils.getLocalDateTime());
        dto.setTransactionType("STOCK PRICE INQUIRY");
        dto.setTransactionDetail("ADDING STOCK PRICE INQUIRY TRANSACTION DURING JUNIT TESTS");
        return dto;
    }

    private prv.mark.project.common.entity.TransactionLog buildEntity(final TransactionDto dto) {
        prv.mark.project.common.entity.TransactionLog entity = new prv.mark.project.common.entity.TransactionLog();
        entity.setId(null);
        entity.setLogDateTime(DateUtils.getDateFromLocalDateTime(dto.getLogDateTime()));
        entity.setTransactionType(dto.getTransactionType());
        return entity;
    }

    private prv.mark.project.common.entity.TransactionLog insertEntity(
            final prv.mark.project.common.entity.TransactionLog entity) {

        LOGGER.debug("TransactionLogRepositoryTests.insertEntity()");
        prv.mark.project.common.entity.TransactionLog returnEntity = new prv.mark.project.common.entity.TransactionLog();
        try {
            returnEntity = transactionLogRepository.saveAndFlush(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockPrice entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionLog entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

}
