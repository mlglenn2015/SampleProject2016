package prv.mark.project.stockticker.endpoint;


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
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockservice.common.schemas.RequestHeader;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.StockQuote;
import prv.mark.project.stockticker.config.StockTickerTestConfig;
import prv.mark.project.stockticker.service.StockTickerService;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ContextConfiguration(classes = {StockTickerTestConfig.class})
@ActiveProfiles({"test"})
public class StockTickerEndpointTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerEndpointTests.class);

    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    @Autowired
    @InjectMocks
    private StockTickerEndpoint stockTickerEndpoint;

    @Mock
    private StockTickerService stockTickerService;


    Predicate<GetStockPriceResponse> validResponse = response -> Optional.of(response)
            .filter(r -> Optional.of(r.getQuote()).isPresent())
            .filter(r -> r.getQuote().getStatusCode() == 0)
            .filter(r -> Optional.of(r.getQuote().getStockPrice()).isPresent())
            .filter(r -> Optional.of(r.getQuote().getTickerSymbol()).isPresent())
            .isPresent();


    @Before
    public void setUp() {
        LOGGER.debug("StockTickerEndpointTests.setUp()");
        MockitoAnnotations.initMocks(this);  //TODO A ServletContext is required to configure default servlet handling
        assertNotNull(stockTickerEndpoint);
        assertNotNull(stockTickerService);
        assertNotNull(applicationParameterSource);
    }

    @Override
    public void tearDown() {
        LOGGER.debug("StockTickerEndpointTests.setUp()");
        super.tearDown();
    }

    @Test
    public void dummyTest() {
        LOGGER.debug("Begin StockTickerSimulatorEndpointTests.dummyTest()");
        LOGGER.debug("End StockTickerSimulatorEndpointTests.dummyTest()");
    }

    @Test
    public void testGetStockPriceRequestValid() {
        LOGGER.debug("Begin StockTickerEndpointTests.testGetStockPriceRequestValid()");
        GetStockPriceRequest request = buildGetStockPriceRequest();
        assertNotNull(request);

        GetStockPriceResponse dummyResponse = buildStockPriceResponse();
        assertNotNull(dummyResponse);

        doReturn(dummyResponse)
                .when(stockTickerService)
                .getStockPrice(request);

        GetStockPriceResponse response = stockTickerEndpoint.getStockPrice(request);
        verify(stockTickerService).getStockPrice(request);

        assertNotNull(response);
        assertTrue(Optional.of(response).filter(validResponse).isPresent());

        assertEquals(response.getQuote().getTickerSymbol(), dummyResponse.getQuote().getTickerSymbol());
        LOGGER.debug("End StockTickerEndpointTests.testGetStockPriceRequestValid()");
    }

    /*@Test(expected = SOAPGeneralFault.class)
    public void testGetStockPriceRequestInvalidStockSymbol2_345() {
        LOGGER.debug("StockTickerEndpointTests.testGetStockPriceRequestInvalidStockSymbol2_345()");
        GetStockPriceRequest request = buildGetStockPriceRequest();
        assertNotNull(request);
        request.setTickerSymbol("2_345");
        GetStockPriceResponse response = stockTickerEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPGeneralFault.class)
    public void testGetStockPriceRequestInvalidStockSymbolABCDEFGH012345() {
        LOGGER.debug("StockTickerEndpointTests.testGetStockPriceRequestInvalidStockSymbolABCDEFGH012345()");
        GetStockPriceRequest request = buildGetStockPriceRequest();
        assertNotNull(request);
        request.setTickerSymbol("ABCDEFGH012345");
        GetStockPriceResponse response = stockTickerEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPGeneralFault.class)
    public void testGetStockPriceRequestNullHeaderSource() {
        LOGGER.debug("StockTickerEndpointTests.testGetStockPriceRequestNullHeaderSource()");
        GetStockPriceRequest request = buildGetStockPriceRequest();
        assertNotNull(request);
        request.getHead().setSource(null);
        GetStockPriceResponse response = stockTickerEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPGeneralFault.class)
    public void testGetStockPriceRequestInvalidHeaderSource() {
        LOGGER.debug("StockTickerEndpointTests.testGetStockPriceRequestInvalidHeaderSource()");
        GetStockPriceRequest request = buildGetStockPriceRequest();
        assertNotNull(request);
        request.getHead().setSource("INVALID");
        GetStockPriceResponse response = stockTickerEndpoint.getStockPrice(request);
    }*/


    private GetStockPriceRequest buildGetStockPriceRequest() {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildRequestHeader());
        request.setTickerSymbol("A");
        return request;
    }

    private RequestHeader buildRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource(applicationParameterSource.getParm(StringUtils.PARM_VALID_HEADER_SOURCE));
        //header.setSource("STOCKTICKER");
        return header;
    }

    private GetStockPriceResponse buildStockPriceResponse() {
        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setTickerSymbol("A");
        stockQuote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode());
        stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_SUCCESSFUL));
        //stockQuote.setStatusText("Request Successful");
        stockQuote.setStockPrice(NumberUtils.toFloat("9.99"));
        response.setQuote(stockQuote);
        return response;
    }
}
