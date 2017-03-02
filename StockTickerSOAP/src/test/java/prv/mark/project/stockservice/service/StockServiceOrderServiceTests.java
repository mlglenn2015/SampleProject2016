package prv.mark.project.stockservice.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import prv.mark.project.common.entity.StockPriceEntity;
import prv.mark.project.common.service.StockPriceEntityService;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.stockservice.config.StockTickerSOAPTestConfig;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.util.StockOrderCommonTestMethods;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.testutils.config.TestWebServicesConfig;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * JUnit tests for the {@link StockServiceOrderService}.
 *
 * Created by Owner on 2/14/2017.
 */
//@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {StockTickerSOAPTestConfig.class})
//@ActiveProfiles({"test"})
public class StockServiceOrderServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceOrderServiceTests.class);

    @Autowired
    @InjectMocks
    private StockServiceOrderService stockServiceOrderService;

    @Mock
    private StockPriceEntityService stockPriceEntityService;

    @Before
    public void setUp() {
        LOGGER.debug("***** StockServiceOrderServiceTests.setUp() *****");
        assertNotNull(stockServiceOrderService);
        //assertNotNull(stockPriceEntityService);
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** StockServiceOrderServiceTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** StockServiceOrderServiceTests.defaultTest() *****");
    }


    //@Test TODO
    public void testGetStockPrice() {
        LOGGER.debug("***** StockServiceOrderServiceTests.testGetStockPrice() *****");
        assertNotNull(StockOrderCommonTestMethods.buildGetStockPriceRequest("WMT"));
    }

    //TODO
    //@Test
    public void testGetStockPriceFailureResponse() {
        LOGGER.debug("Begin StockServiceOrderServiceTests.testGetStockPriceFailureResponse()");
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest("WMT");
        assertNotNull(request);

        doReturn(buildEmptyStockPriceEntity())
                .when(stockPriceEntityService)
                .findByStockSymbol(request.getTickerSymbol());

        /*when(stockPriceEntityService.findByStockSymbol(request.getTickerSymbol()))
                .thenReturn(buildEmptyStockPriceEntity());*/
        //.thenReturn(buildStockPriceEntity());

        GetStockPriceResponse response = stockServiceOrderService.getStockPrice(request);
    }


    private Optional<StockPriceEntity> buildEmptyStockPriceEntity() {
        StockPriceEntity entity = new StockPriceEntity();
        return Optional.of(entity);
    }

    private Optional<StockPriceEntity> buildStockPriceEntity() {
        StockPriceEntity entity = new StockPriceEntity();
        entity.setId(1L);
        entity.setStockSymbol("WMT");
        entity.setCurrentPrice(NumberUtils.myToBigDecimal(68.00));
        return Optional.of(entity);
    }
}
