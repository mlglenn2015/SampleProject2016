package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.entity.TransactionTypes;
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
        prv.mark.project.common.entity.TransactionTypes entity = buildEntity();
        assertNotNull(entity);

        prv.mark.project.common.entity.TransactionTypes savedEntity = insertEntity(entity);
        assertNotNull(savedEntity);
        assertTrue(savedEntity.getId() > 0);

        Optional<TransactionTypes> newEntity
                = transactionTypesRepository.findById(savedEntity.getId());
        assertNotNull(newEntity);

        assertEquals(newEntity.get().getDescription(), "TEST INQUIRY DESCRIPTION");
        assertEquals(savedEntity.getTransactionType(), newEntity.get().getTransactionType());
    }

    @Test
    public void testFindById() {
        Optional<TransactionTypes> t = transactionTypesRepository.findById(1L);
        assertNotNull(t);
        assertNotNull(t.get().getDescription());
    }

    @Test
    public void testFindByTransactionType() {
        Optional<TransactionTypes> t = transactionTypesRepository.findByTransactionType("STOCK PURCHASE");
        assertNotNull(t);
        assertEquals(t.get().getDescription(), "STOCK PURCHASE TRANSACTION");
    }

    @Test
    public void testFindByInvalidTransactionType() {
        Optional<TransactionTypes> t = transactionTypesRepository.findByTransactionType("TEST");
        assertEquals(t, Optional.empty());
    }



    private prv.mark.project.common.entity.TransactionTypes buildEntity() {
        prv.mark.project.common.entity.TransactionTypes entity = new prv.mark.project.common.entity.TransactionTypes();
        entity.setId(null);
        entity.setTransactionType("TEST INQUIRY");
        entity.setDescription("TEST INQUIRY DESCRIPTION");
        return entity;
    }

    private prv.mark.project.common.entity.TransactionTypes insertEntity(
            final prv.mark.project.common.entity.TransactionTypes entity) {

        LOGGER.debug("TransactionTypesRepositoryTests.insertEntity()");
        prv.mark.project.common.entity.TransactionTypes returnEntity = new prv.mark.project.common.entity.TransactionTypes();
        try {
            returnEntity = transactionTypesRepository.saveAndFlush(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionTypes entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionTypes entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

}
