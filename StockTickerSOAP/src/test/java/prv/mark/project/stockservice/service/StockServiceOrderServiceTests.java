package prv.mark.project.stockservice.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import prv.mark.project.stockservice.util.StockOrderCommonTestMethods;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit tests for the {@link StockServiceOrderService}.
 *
 * Created by Owner on 2/14/2017.
 */
public class StockServiceOrderServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceOrderServiceTests.class);

    @Autowired
    private StockServiceOrderService stockServiceOrderService;

    @Before
    public void setUp() {
        LOGGER.debug("***** StockServiceOrderServiceTests.setUp() *****");
        //assertNotNull(stockServiceOrderService);
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
}
