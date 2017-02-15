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
import prv.mark.project.common.domain.EnumOrderTypes;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;
import prv.mark.project.stockservice.util.StockOrderCommonTestMethods;
import prv.mark.project.testutils.junit.AbstractAppWebServiceEndpointTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the StockOrderEndpoint.
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
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);
        GetStockPriceResponse response = StockOrderCommonTestMethods.buildGetStockPriceResponse(tickerSymbol);
        assertNotNull(response);
        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request); //0
        assertNotNull(actualResponse);
        assertTrue(actualResponse.getQuote().getStatusCode() == EnumStatusCodes.SUCCESS.getStatudCode());
        assertEquals(response.getQuote().getTickerSymbol(), actualResponse.getQuote().getTickerSymbol());

        LOGGER.debug("***** Cease StockOrderEndpointTests.testGetStockPrice() *****");
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceInvalidSymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceInvalidSymbol() *****");

        String tickerSymbol = "@";
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceEmptySymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceEmptySymbol() *****");

        String tickerSymbol = "";
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceSymbolNotInExchange() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceSymbolNotInExchange() *****");

        String tickerSymbol = "XXX";
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceInvalidHeader() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceInvalidHeader() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildInvalidGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceEmptyHeader() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceEmptyHeader() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildEmptyGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceNullHeader() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceEmptyHeader() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildNullGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceNullRequest() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceNullRequest() *****");

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(null);
    }

    /****************************************/

    @Test
    public void testSubmitOrder() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrder() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        SubmitOrderResponse response = StockOrderCommonTestMethods.buildSubmitOrderResponse(tickerSymbol);
        assertNotNull(response);
        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request); //0
        assertNotNull(actualResponse);
        assertTrue(actualResponse.getStatus() == EnumStatusCodes.SUCCESS.getStatudCode());

        LOGGER.debug("***** Cease StockOrderEndpointTests.testSubmitOrder() *****");
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderInvalidSymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderInvalidSymbol() *****");

        String tickerSymbol = "@";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        SubmitOrderResponse response = StockOrderCommonTestMethods.buildSubmitOrderResponse(tickerSymbol);
        assertNotNull(response);
        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request); //0
        assertNotNull(actualResponse);
        assertTrue(actualResponse.getStatus() == EnumStatusCodes.SUCCESS.getStatudCode());

        LOGGER.debug("***** Cease StockOrderEndpointTests.testSubmitOrderInvalidSymbol() *****");
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderSymbolNotInExchange() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderSymbolNotInExchange() *****");

        String tickerSymbol = "XXXXXXXXX";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        SubmitOrderResponse response = StockOrderCommonTestMethods.buildSubmitOrderResponse(tickerSymbol);
        assertNotNull(response);
        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptySymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptySymbol() *****");

        String tickerSymbol = "";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        SubmitOrderResponse response = StockOrderCommonTestMethods.buildSubmitOrderResponse(tickerSymbol);
        assertNotNull(response);
        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderNullOrder() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderNullOrder() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequestNullOrder(tickerSymbol);
        assertNotNull(request);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptyAction() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptyAction() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setAction(StringUtils.EMPTY);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderInvalidAction() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderInvalidAction() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setAction("XXXXXXX");

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptyOrderType() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptyOrderType() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setOrderType(StringUtils.EMPTY);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderInvalidOrderType() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderInvalidOrderType() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setOrderType("MARKET"); //MARKET ORDER is the correct value

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptyTickerSymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptyTickerSymbol() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setTickerSymbol(StringUtils.EMPTY);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderNullQuantity() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderNullQuantity() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setQuantity(null);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderLimitOrderNullPrice() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderLimitOrderNullPrice() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.setOrder(StockOrderCommonTestMethods.buildStockOrderNullPrice(tickerSymbol));
        request.getOrder().setOrderType(EnumOrderTypes.LIMIT_ORDER.getOrderType());

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderLimitOrderInvalidPrice() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderLimitOrderInvalidPrice() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.setOrder(StockOrderCommonTestMethods.buildStockOrder(tickerSymbol));
        request.getOrder().setOrderType(EnumOrderTypes.LIMIT_ORDER.getOrderType());
        request.getOrder().setStockPrice(NumberUtils.toFloat("0.00"));

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderNullRequest() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderNullRequest() *****");

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(null);
    }
}
