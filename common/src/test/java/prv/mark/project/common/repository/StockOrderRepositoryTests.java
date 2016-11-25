package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the {@link StockOrderRepository}.
 *
 * @author mlglenn
 */
public class StockOrderRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderRepositoryTests.class);

    @Autowired
    private StockOrderRepository stockOrderRepository;

    @Before
    public void setUp() {
        assertNotNull(stockOrderRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("StockOrderRepositoryTests.defaultTest()");
    }

    @Test
    public void testStockOrder() {
        StockOrderDto dto = buildDto();
        assertNotNull(dto);

        prv.mark.project.common.entity.StockOrder stockOrder = buildStockOrder(dto);
        assertNotNull(stockOrder);

        prv.mark.project.common.entity.StockOrder retStockOrder = insertStockOrder(stockOrder);
        assertNotNull(retStockOrder);
        assertTrue(retStockOrder.getId() > 0);

        Optional<prv.mark.project.common.entity.StockOrder> newStockOrder
                = stockOrderRepository.findById(retStockOrder.getId());
        assertNotNull(newStockOrder);

        assertEquals(retStockOrder.getOrderStatus(), newStockOrder.get().getOrderStatus());
    }


    private StockOrderDto buildDto() {
        StockOrderDto dto = new StockOrderDto();
        dto.setAction("BUY");
        dto.setOrderType("LIMIT ORDER");
        dto.setQuantity(NumberUtils.toLong(300));
        dto.setPrice(NumberUtils.toBigDecimal(9.98));
        dto.setStockSymbol("A");
        dto.setOrderStatus("PENDING");
        dto.setOrderDate(DateUtils.getLocalDateTime());
        return dto;
    }

    private prv.mark.project.common.entity.StockOrder buildStockOrder(final StockOrderDto dto) {
        prv.mark.project.common.entity.StockOrder stockOrder = new prv.mark.project.common.entity.StockOrder();
        stockOrder.setId(null);
        stockOrder.setAction(dto.getAction());
        stockOrder.setOrderDate(DateUtils.getDateFromLocalDateTime(dto.getOrderDate()));
        stockOrder.setOrderStatus(dto.getOrderStatus());
        stockOrder.setOrderType(dto.getOrderType());
        stockOrder.setPrice(dto.getPrice());
        stockOrder.setQuantity(dto.getQuantity());
        stockOrder.setStockSymbol(dto.getStockSymbol());
        return stockOrder;
    }

    private prv.mark.project.common.entity.StockOrder insertStockOrder(
            final prv.mark.project.common.entity.StockOrder entity) {

        LOGGER.debug("StockOrderRepositoryTests.insertStockOrder()");
        prv.mark.project.common.entity.StockOrder returnEntity = new prv.mark.project.common.entity.StockOrder();
        try {
            returnEntity = stockOrderRepository.saveAndFlush(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockOrder entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved StockOrder entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
