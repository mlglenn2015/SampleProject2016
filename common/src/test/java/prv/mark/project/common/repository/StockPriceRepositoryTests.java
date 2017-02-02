package prv.mark.project.common.repository;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaSystemException;
import prv.mark.project.common.domain.StockOrderDto;
import prv.mark.project.common.entity.StockPrice;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
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
 * JUnit tests for the {@link StockPriceRepository}.
 *
 * @author mlglenn
 */
public class StockPriceRepositoryTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceRepositoryTests.class);

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Before
    public void setUp() {
        assertNotNull(stockPriceRepository);
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("StockPriceRepositoryTests.defaultTest()");
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
                                = stockPriceRepository.findById(retStockPrice.getId());
        assertNotNull(newStockPrice);

        assertEquals(retStockPrice.getStockSymbol(), newStockPrice.get().getStockSymbol());
    }

    @Test
    public void testFindAll() {
        List<StockPrice> entityList = new ArrayList<>();
        entityList = stockPriceRepository.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindById() {
        Optional<StockPrice> stockPrice = stockPriceRepository.findById(1L);
        assertNotNull(stockPrice);
        assertTrue(stockPrice.get().getCurrentPrice().intValue() > NumberUtils.myToBigDecimal(0).intValue());
        assertTrue(StringUtils.isNotEmpty(stockPrice.get().getStockSymbol()));
    }

    @Test
    public void testFindByStockSymbol() {
        Optional<StockPrice> stockPrice = stockPriceRepository.findByStockSymbol("X");
        assertNotNull(stockPrice.get());
        assertTrue(stockPrice.get().getCurrentPrice().intValue() > NumberUtils.myToBigDecimal(0).intValue());
    }

    @Test
    public void testFindByInvalidStockSymbol() {
        Optional<StockPrice> stockPrice = stockPriceRepository.findByStockSymbol("TEST");
        assertEquals(stockPrice, Optional.empty());
    }


    private StockOrderDto buildDto() {
        StockOrderDto dto = new StockOrderDto();
        dto.setAction("INQUIRY");
        dto.setOrderType("NONE");
        dto.setQuantity(NumberUtils.toLong("0"));
        dto.setPrice(NumberUtils.myToBigDecimal(9.99));
        dto.setStockSymbol("A");
        dto.setOrderStatus("NONE");
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

        LOGGER.debug("StockPriceRepositoryTests.insertStockPrice()");
        prv.mark.project.common.entity.StockPrice returnEntity = new prv.mark.project.common.entity.StockPrice();
        try {
            returnEntity = stockPriceRepository.saveAndFlush(entity);

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
