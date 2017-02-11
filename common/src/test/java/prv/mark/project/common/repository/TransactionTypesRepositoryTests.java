package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.entity.TransactionTypesEntity;
import prv.mark.project.common.exception.ExceptionRouter;
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
 * JUnit tests for the {@link TransactionTypesRepository}.
 *
 * @author mlglenn
 */
public class TransactionTypesRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionTypesRepositoryTests.class);

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    @Before
    public void setUp() {
        assertNotNull(transactionTypesRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("TransactionTypesRepositoryTests.defaultTest()");
    }

    @Test
    public void testTransactionTypesRepository() {
        TransactionTypesEntity entity = buildEntity();
        assertNotNull(entity);

        TransactionTypesEntity savedEntity = insertEntity(entity);
        assertNotNull(savedEntity);
        assertTrue(savedEntity.getId() > 0);

        Optional<TransactionTypesEntity> newEntity
                = transactionTypesRepository.findById(savedEntity.getId());
        assertNotNull(newEntity);

        assertEquals(newEntity.get().getDescription(), "TEST INQUIRY DESCRIPTION");
        assertEquals(savedEntity.getTransactionType(), newEntity.get().getTransactionType());
    }

    @Test
    public void testFindAll() {
        List<TransactionTypesEntity> entityList = new ArrayList<>();
        entityList = transactionTypesRepository.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindById() {
        Optional<TransactionTypesEntity> t = transactionTypesRepository.findById(1L);
        assertNotNull(t);
        assertNotNull(t.get().getDescription());
    }

    @Test
    public void testFindByTransactionType() {
        Optional<TransactionTypesEntity> t = transactionTypesRepository.findByTransactionType("STOCK PURCHASE");
        assertNotNull(t);
        assertEquals(t.get().getDescription(), "STOCK PURCHASE TRANSACTION");
    }

    @Test
    public void testFindByInvalidTransactionType() {
        Optional<TransactionTypesEntity> t = transactionTypesRepository.findByTransactionType("TEST");
        assertEquals(t, Optional.empty());
    }



    private TransactionTypesEntity buildEntity() {
        TransactionTypesEntity entity = new TransactionTypesEntity();
        entity.setId(null);
        entity.setTransactionType("TEST INQUIRY");
        entity.setDescription("TEST INQUIRY DESCRIPTION");
        return entity;
    }

    private TransactionTypesEntity insertEntity(
            final TransactionTypesEntity entity) {

        LOGGER.debug("TransactionTypesRepositoryTests.insertEntity()");
        TransactionTypesEntity returnEntity = new TransactionTypesEntity();
        try {
            returnEntity = transactionTypesRepository.saveAndFlush(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionTypesEntity entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionTypesEntity entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

}
