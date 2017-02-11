package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.entity.StockPriceEntity;
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
 * JUnit tests for the {@link StockPriceEntityService}.
 *
 * @author mlglenn
 */
public class StockPriceEntityServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceEntityServiceTests.class);

    @Autowired
    private StockPriceEntityService stockPriceEntityService;

    @Before
    public void setUp() {
        assertNotNull(stockPriceEntityService);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("StockPriceEntityServiceTests.defaultTest()");
    }

    @Test
    public void testStockPrice() {
        StockOrderDto dto = buildDto();
        assertNotNull(dto);

        StockPriceEntity stockPriceEntity = buildStockPrice(dto);
        assertNotNull(stockPriceEntity);

        StockPriceEntity retStockPriceEntity = insertStockPrice(stockPriceEntity);
        assertNotNull(retStockPriceEntity);
        assertTrue(retStockPriceEntity.getId() > 0);

        Optional<StockPriceEntity> newStockPrice
                = stockPriceEntityService.findById(retStockPriceEntity.getId());
        assertNotNull(newStockPrice);

        assertEquals(retStockPriceEntity.getStockSymbol(), newStockPrice.get().getStockSymbol());
    }

    @Test
    public void testFindAll() {
        List<StockPriceEntity> entityList = new ArrayList<>();
        entityList = stockPriceEntityService.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindByStockSymbol() {
        Optional<StockPriceEntity> stockPrice =  stockPriceEntityService.findByStockSymbol("WMT");
        assertNotNull(stockPrice.get());
        assertEquals(stockPrice.get().getStockSymbol(), "WMT");
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

    private StockPriceEntity buildStockPrice(final StockOrderDto dto) {
        StockPriceEntity stockPriceEntity = new StockPriceEntity();
        stockPriceEntity.setId(null);
        stockPriceEntity.setCurrentPrice(dto.getPrice());
        stockPriceEntity.setStockSymbol(dto.getStockSymbol());
        return stockPriceEntity;
    }

    private StockPriceEntity insertStockPrice(
            final StockPriceEntity entity) {

        LOGGER.debug("StockPriceEntityServiceTests.insertStockPrice()");
        StockPriceEntity returnEntity = new StockPriceEntity();
        try {
            returnEntity = stockPriceEntityService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockPriceEntity entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved StockPriceEntity entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
