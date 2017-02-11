package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.entity.OrderStatusEntity;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.util.StringUtils;
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
        OrderStatusEntity entity = buildEntity();
        assertNotNull(entity);

        OrderStatusEntity savedEntity = insertEntity(entity);
        assertNotNull(savedEntity);
        assertTrue(savedEntity.getId() > 0);

        Optional<OrderStatusEntity> newEntity
                = orderStatusRepository.findById(savedEntity.getId());
        assertNotNull(newEntity);

        assertEquals(newEntity.get().getDescription(), "ORDER IN TESTING STATUS");
        assertEquals(savedEntity.getOrderStatus(), newEntity.get().getOrderStatus());
    }

    @Test
    public void testFindAll() {
        List<OrderStatusEntity> entityList = new ArrayList<>();
        entityList = orderStatusRepository.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindById() {
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findById(1L);
        assertTrue(StringUtils.isNotEmpty(orderStatus.get().getOrderStatus()));
    }

    @Test
    public void testFindByOrderStatus() {
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findByOrderStatus("PENDING");
        assertTrue(StringUtils.isNotEmpty(orderStatus.get().getDescription()));
        assertEquals(orderStatus.get().getDescription(), "ORDER IN PENDING STATUS");
    }

    @Test
    public void testFindByInvalidOrderStatus() {
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findByOrderStatus("TEST");
        assertEquals(orderStatus, Optional.empty());
    }


    private OrderStatusEntity buildEntity() {
        OrderStatusEntity entity = new OrderStatusEntity();
        entity.setId(null);
        entity.setOrderStatus("TESTING");
        entity.setDescription("ORDER IN TESTING STATUS");
        return entity;
    }

    private OrderStatusEntity insertEntity(
            final OrderStatusEntity entity) {

        LOGGER.debug("OrderStatusRepositoryTests.insertEntity()");
        OrderStatusEntity returnEntity = new OrderStatusEntity();
        try {
            returnEntity = orderStatusRepository.saveAndFlush(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving OrderStatusEntity entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved OrderStatusEntity entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
