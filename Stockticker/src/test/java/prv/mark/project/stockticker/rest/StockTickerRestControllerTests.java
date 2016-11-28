package prv.mark.project.stockticker.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.domain.json.AbstractJsonResponse;
import prv.mark.project.common.domain.json.StockPriceResponse;
import prv.mark.project.common.exception.SOAPGeneralFault;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockticker.config.StockTickerTestConfig;
import prv.mark.project.stockticker.service.StockTickerService;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;
import prv.mark.xml.stocks.GetStockPriceRequest;
import prv.mark.xml.stocks.GetStockPriceResponse;
import prv.mark.xml.stocks.RequestHeader;
import prv.mark.xml.stocks.StockQuote;

import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {StockTickerTestConfig.class})
@ActiveProfiles({"test"})
public class StockTickerRestControllerTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerRestControllerTests.class);

    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    @Autowired
    @InjectMocks
    private StockTickerRestController stockTickerRestController;

    @Mock
    private StockTickerService stockTickerService;

    Predicate<StockPriceResponse> validResponse = response -> Optional.of(response)
            .filter(r -> Optional.of(r.getRespStatus()).isPresent())
            .filter(r -> r.getRespStatus().getStatus().equals(AbstractJsonResponse.RespStatus.OK.getStatus()))
            .filter(r -> Optional.of(r.getStockPrice()).isPresent())
            .filter(r -> Optional.of(r.getStockSymbol()).isPresent())
            .isPresent();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(stockTickerRestController);
        assertNotNull(stockTickerService);
        assertNotNull(applicationParameterSource);
    }

    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void dummyTest() {
        LOGGER.debug("StockTickerRestControllerTests.dummyTest()");
    }

    //@Test
    public void testGetStockPriceRequestValid() {
        LOGGER.debug("StockTickerRestControllerTests.testGetStockPriceRequestValid()");
        GetStockPriceRequest request = buildGetStockPriceRequest();
        assertNotNull(request);

        doReturn(buildStockPriceResponse())
                .when(stockTickerService)
                .getStockPrice(request);
        StockPriceResponse response = stockTickerRestController.getStockPriceBySymbol("A");
        verify(stockTickerService).getStockPrice(request);

        assertNotNull(response);
        assertTrue(Optional.of(response).filter(validResponse).isPresent());
    }

    @Test(expected = SOAPGeneralFault.class)
    public void testGetStockPriceRequestInvalidStockSymbol2_345() {
        LOGGER.debug("StockTickerRestControllerTests.testGetStockPriceRequestInvalidStockSymbol2_345()");
        StockPriceResponse response = stockTickerRestController.getStockPriceBySymbol("2_345");
    }

    @Test(expected = SOAPGeneralFault.class)
    public void testGetStockPriceRequestInvalidStockSymbolABCDEFGH012345() {
        LOGGER.debug("StockTickerRestControllerTests.testGetStockPriceRequestInvalidStockSymbolABCDEFGH012345()");
        StockPriceResponse response = stockTickerRestController.getStockPriceBySymbol("ABCDEFGH012345");
    }


    private GetStockPriceRequest buildGetStockPriceRequest() {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildRequestHeader());
        request.setTickerSymbol("A");
        return request;
    }

    private RequestHeader buildRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource(applicationParameterSource.getParm(StringUtils.PARM_VALID_HEADER_SOURCE));
        return header;
    }

    /*private StockPriceResponse buildStockPriceResponse() {
        StockPriceResponse response = new StockPriceResponse();
        response.setId(1L);
        response.setRespStatus(AbstractJsonResponse.RespStatus.OK);
        response.setStockSymbol("A");
        response.setStockPrice(NumberUtils.toBigDecimal("9.99"));
        return response;
    }*/

    private GetStockPriceResponse buildStockPriceResponse() {
        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setTickerSymbol("A");
        stockQuote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode());
        stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_SUCCESSFUL));
        stockQuote.setStockPrice(NumberUtils.toFloat("9.99"));
        response.setQuote(stockQuote);
        return response;
    }
}
