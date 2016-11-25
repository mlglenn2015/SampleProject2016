package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.entity.ApplicationMessages;
import prv.mark.project.common.entity.OrderStatus;
import prv.mark.project.common.entity.TransactionTypes;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import javax.persistence.PersistenceException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the {@link OrderStatusRepository}.
 *
 * @author mlglenn
 */
public class OrderStatusRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusRepositoryTests.class);

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Before
    public void setUp() {
        assertNotNull(orderStatusRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("OrderStatusRepositoryTests.defaultTest()");
    }

    @Test
    public void testOrderStatusRepository() {
        prv.mark.project.common.entity.OrderStatus entity = buildEntity();
        assertNotNull(entity);

        prv.mark.project.common.entity.OrderStatus savedEntity = insertEntity(entity);
        assertNotNull(savedEntity);
        assertTrue(savedEntity.getId() > 0);

        Optional<OrderStatus> newEntity
                = orderStatusRepository.findById(savedEntity.getId());
        assertNotNull(newEntity);

        assertEquals(newEntity.get().getDescription(), "ORDER IN TESTING STATUS");
        assertEquals(savedEntity.getOrderStatus(), newEntity.get().getOrderStatus());
    }

    @Test
    public void testFindById() {
        Optional<OrderStatus> orderStatus = orderStatusRepository.findById(1L);
        assertTrue(StringUtils.isNotEmpty(orderStatus.get().getOrderStatus()));
    }

    @Test
    public void testFindByOrderStatus() {
        Optional<OrderStatus> orderStatus = orderStatusRepository.findByOrderStatus("PENDING");
        assertTrue(StringUtils.isNotEmpty(orderStatus.get().getDescription()));
        assertEquals(orderStatus.get().getDescription(), "ORDER IN PENDING STATUS");
    }

    @Test
    public void testFindByInvalidOrderStatus() {
        Optional<OrderStatus> orderStatus = orderStatusRepository.findByOrderStatus("TEST");
        assertEquals(orderStatus, Optional.empty());
    }


    private prv.mark.project.common.entity.OrderStatus buildEntity() {
        prv.mark.project.common.entity.OrderStatus entity = new prv.mark.project.common.entity.OrderStatus();
        entity.setId(null);
        entity.setOrderStatus("TESTING");
        entity.setDescription("ORDER IN TESTING STATUS");
        return entity;
    }

    private prv.mark.project.common.entity.OrderStatus insertEntity(
            final prv.mark.project.common.entity.OrderStatus entity) {

        LOGGER.debug("OrderStatusRepositoryTests.insertEntity()");
        prv.mark.project.common.entity.OrderStatus returnEntity = new prv.mark.project.common.entity.OrderStatus();
        try {
            returnEntity = orderStatusRepository.saveAndFlush(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving OrderStatus entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved OrderStatus entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
