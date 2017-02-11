package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.entity.StockOrderEntity;
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
 * JUnit tests for the {@link StockOrderEntityService}.
 *
 * @author mlglenn
 */
public class StockOrderEntityServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderRepositoryTests.class);

    @Autowired
    private StockOrderEntityService stockOrderEntityService;

    @Before
    public void setUp() {
        assertNotNull(stockOrderEntityService);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("StockOrderEntityServiceTests.defaultTest()");
    }

    @Test
    public void testStockOrder() {
        StockOrderDto dto = buildDto();
        assertNotNull(dto);

        StockOrderEntity stockOrderEntity = buildStockOrder(dto);
        assertNotNull(stockOrderEntity);

        StockOrderEntity retStockOrderEntity = insertStockOrder(stockOrderEntity);
        assertNotNull(retStockOrderEntity);
        assertTrue(retStockOrderEntity.getId() > 0);

        Optional<StockOrderEntity> newStockOrder
                = stockOrderEntityService.findById(retStockOrderEntity.getId());
        assertNotNull(newStockOrder);

        assertEquals(retStockOrderEntity.getOrderStatus(), newStockOrder.get().getOrderStatus());
    }

    @Test
    public void testFindAll() {
        List<StockOrderEntity> entityList = new ArrayList<>();
        entityList = stockOrderEntityService.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindByOrderStatus() {
        List<StockOrderEntity> stockOrderEntities = stockOrderEntityService.findByOrderStatus("PENDING");
        assertNotNull(stockOrderEntities);
        //assertTrue(stockOrderEntities.size() > 0);
    }

    private StockOrderDto buildDto() {
        StockOrderDto dto = new StockOrderDto();
        dto.setAction("BUY");
        dto.setOrderType("LIMIT ORDER");
        dto.setQuantity(NumberUtils.toLong("300"));
        dto.setPrice(NumberUtils.myToBigDecimal(9.98));
        dto.setStockSymbol("A");
        dto.setOrderStatus("PENDING");
        dto.setOrderDate(DateUtils.getLocalDateTime());
        return dto;
    }

    private StockOrderEntity buildStockOrder(final StockOrderDto dto) {
        StockOrderEntity stockOrderEntity = new StockOrderEntity();
        stockOrderEntity.setId(null);
        stockOrderEntity.setAction(dto.getAction());
        stockOrderEntity.setOrderDate(DateUtils.getDateFromLocalDateTime(dto.getOrderDate()));
        stockOrderEntity.setOrderStatus(dto.getOrderStatus());
        stockOrderEntity.setOrderType(dto.getOrderType());
        stockOrderEntity.setPrice(dto.getPrice());
        stockOrderEntity.setQuantity(dto.getQuantity());
        stockOrderEntity.setStockSymbol(dto.getStockSymbol());
        return stockOrderEntity;
    }

    private StockOrderEntity insertStockOrder(
            final StockOrderEntity entity) {

        LOGGER.debug("StockOrderEntityServiceTests.insertStockOrder()");
        StockOrderEntity returnEntity = new StockOrderEntity();
        try {
            returnEntity = stockOrderEntityService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockOrderEntity entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved StockOrderEntity entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
