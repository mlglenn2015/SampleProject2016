package prv.mark.project.common.repository;

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

        StockPriceEntity stockPriceEntity = buildStockPrice(dto);
        assertNotNull(stockPriceEntity);

        StockPriceEntity retStockPriceEntity = insertStockPrice(stockPriceEntity);
        assertNotNull(retStockPriceEntity);
        assertTrue(retStockPriceEntity.getId() > 0);

        Optional<StockPriceEntity> newStockPrice
                                = stockPriceRepository.findById(retStockPriceEntity.getId());
        assertNotNull(newStockPrice);

        assertEquals(retStockPriceEntity.getStockSymbol(), newStockPrice.get().getStockSymbol());
    }

    @Test
    public void testFindAll() {
        List<StockPriceEntity> entityList = new ArrayList<>();
        entityList = stockPriceRepository.findAll();
        assertNotNull(entityList);
        assertTrue(entityList.size() > 0);
    }

    @Test
    public void testFindById() {
        Optional<StockPriceEntity> stockPrice = stockPriceRepository.findById(1L);
        assertNotNull(stockPrice);
        assertTrue(stockPrice.get().getCurrentPrice().intValue() > NumberUtils.myToBigDecimal(0).intValue());
        assertTrue(StringUtils.isNotEmpty(stockPrice.get().getStockSymbol()));
    }

    @Test
    public void testFindByStockSymbol() {
        Optional<StockPriceEntity> stockPrice = stockPriceRepository.findByStockSymbol("X");
        assertNotNull(stockPrice.get());
        assertTrue(stockPrice.get().getCurrentPrice().intValue() > NumberUtils.myToBigDecimal(0).intValue());
    }

    @Test
    public void testFindByInvalidStockSymbol() {
        Optional<StockPriceEntity> stockPrice = stockPriceRepository.findByStockSymbol("TEST");
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

    private StockPriceEntity buildStockPrice(final StockOrderDto dto) {
        StockPriceEntity stockPriceEntity = new StockPriceEntity();
        stockPriceEntity.setId(null);
        stockPriceEntity.setCurrentPrice(dto.getPrice());
        stockPriceEntity.setStockSymbol(dto.getStockSymbol());
        return stockPriceEntity;
    }

    private StockPriceEntity insertStockPrice(
            final StockPriceEntity entity) {

        LOGGER.debug("StockPriceRepositoryTests.insertStockPrice()");
        StockPriceEntity returnEntity = new StockPriceEntity();
        try {
            returnEntity = stockPriceRepository.saveAndFlush(entity);

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
