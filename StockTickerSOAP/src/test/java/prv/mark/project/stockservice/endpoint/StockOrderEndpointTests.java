package prv.mark.project.stockservice.endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.RequestHeader;
import prv.mark.project.stockservice.schemas.StockQuote;
import prv.mark.project.testutils.junit.AbstractAppWebServiceEndpointTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * TODO finish
 *
 * Created by Owner on 2/6/2017.
 */
public class StockOrderEndpointTests extends AbstractAppWebServiceEndpointTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderEndpointTests.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private SoapFaultAnnotationExceptionResolver soapFaultExceptionResolver;

    @Autowired
    private StockOrderEndpoint stockOrderEndpoint;


    @Before
    public void setUp() {
        LOGGER.debug("***** StockOrderEndpointTests.setUp() *****");
        assertNotNull(env);
        assertNotNull(webServiceTemplate);
        assertNotNull(soapFaultExceptionResolver);
        assertNotNull(stockOrderEndpoint);
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** StockOrderEndpointTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** StockOrderEndpointTests.defaultTest() *****");
    }

    @Test
    public void testGetStockPrice() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPrice() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = buildGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);
        GetStockPriceResponse response = buildGetStockPriceResponse(tickerSymbol);
        assertNotNull(response);
        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request); //0
        assertNotNull(actualResponse);
        assertTrue(actualResponse.getQuote().getStatusCode() == EnumStatusCodes.SUCCESS.getStatudCode());
        assertEquals(response.getQuote().getTickerSymbol(), actualResponse.getQuote().getTickerSymbol());

        LOGGER.debug("***** Cease StockOrderEndpointTests.testGetStockPrice() *****");
    }

    //TODO more tests

    private GetStockPriceResponse buildGetStockPriceResponse(final String symbol) {
        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote quote = new StockQuote();
        quote.setTickerSymbol(symbol);
        quote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode());
        quote.setStatusText("REQUEST SUCCESSFUL");
        quote.setStockPrice(NumberUtils.toFloat("68.00"));
        response.setQuote(quote);
        return response;
    }

    private GetStockPriceRequest buildGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildRequestHeader());
        request.setTickerSymbol(symbol);
        return request;
    }

    private RequestHeader buildRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("STOCKTICKER_20170131");
        return header;
    }
}
