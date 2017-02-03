package prv.mark.project.common.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.entity.StockOrder;
import prv.mark.project.common.entity.StockPrice;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.repository.StockOrderRepository;
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
 * JUnit tests for the {@link StockPriceService}.
 *
 * @author mlglenn
 */
public class StockPriceServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceServiceTests.class);

    @Autowired
    private StockPriceService stockPriceService;

    @Before
    public void setUp() {
        assertNotNull(stockPriceService);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("StockPriceServiceTests.defaultTest()");
    }

    @Test
    public void testStockPrice() {
        StockOrderDto dto = buildDto();
        assertNotNull(dto);

        prv.mark.project.common.entity.StockPrice stockPrice = buildStockPrice(dto);
        assertNotNull(stockPrice);

        prv.mark.project.common.entity.StockPrice retStockPrice = insertStockPrice(stockPrice);
        assertNotNull(retStockPrice);
        assertTrue(retStockPrice.getId() > 0);

        Optional<prv.mark.project.common.entity.StockPrice> newStockPrice
                = stockPriceService.findById(retStockPrice.getId());
        assertNotNull(newStockPrice);

        assertEquals(retStockPrice.getStockSymbol(), newStockPrice.get().getStockSymbol());
    }

    @Test
    public void testFindAll() {
        List<StockPrice> entityList = new ArrayList<>();
        entityList = stockPriceService.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindByStockSymbol() {
        Optional<StockPrice> stockPrice =  stockPriceService.findByStockSymbol("WMT");
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

    private prv.mark.project.common.entity.StockPrice buildStockPrice(final StockOrderDto dto) {
        prv.mark.project.common.entity.StockPrice stockPrice = new prv.mark.project.common.entity.StockPrice();
        stockPrice.setId(null);
        stockPrice.setCurrentPrice(dto.getPrice());
        stockPrice.setStockSymbol(dto.getStockSymbol());
        return stockPrice;
    }

    private prv.mark.project.common.entity.StockPrice insertStockPrice(
            final prv.mark.project.common.entity.StockPrice entity) {

        LOGGER.debug("StockPriceServiceTests.insertStockPrice()");
        prv.mark.project.common.entity.StockPrice returnEntity = new prv.mark.project.common.entity.StockPrice();
        try {
            returnEntity = stockPriceService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockPrice entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved StockPrice entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }
}
