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
import prv.mark.project.common.domain.EnumAction;
import prv.mark.project.common.domain.EnumOrderTypes;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.RequestHeader;
import prv.mark.project.stockservice.schemas.StockOrder;
import prv.mark.project.stockservice.schemas.StockQuote;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;
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

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceInvalidSymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceInvalidSymbol() *****");

        String tickerSymbol = "@";
        GetStockPriceRequest request = buildGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request); //1
        assertNotNull(actualResponse);
        assertTrue(actualResponse.getQuote().getStatusCode() == EnumStatusCodes.REQUEST_FAILED.getStatudCode());

        LOGGER.debug("***** Cease StockOrderEndpointTests.testGetStockPriceInvalidSymbol() *****");
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceInvalidHeader() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceInvalidHeader() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = buildInvalidGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceEmptyHeader() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceEmptyHeader() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = buildEmptyGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceNullHeader() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceEmptyHeader() *****");

        String tickerSymbol = "WMT";
        GetStockPriceRequest request = buildNullGetStockPriceRequest(tickerSymbol);
        assertNotNull(request);

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceNullRequest() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testGetStockPriceNullRequest() *****");

        GetStockPriceResponse actualResponse = stockOrderEndpoint.getStockPrice(null);
    }


    @Test
    public void testSubmitOrder() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrder() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        SubmitOrderResponse response = buildSubmitOrderResponse(tickerSymbol);
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
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        SubmitOrderResponse response = buildSubmitOrderResponse(tickerSymbol);
        assertNotNull(response);
        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request); //0
        assertNotNull(actualResponse);
        assertTrue(actualResponse.getStatus() == EnumStatusCodes.SUCCESS.getStatudCode());

        LOGGER.debug("***** Cease StockOrderEndpointTests.testSubmitOrderInvalidSymbol() *****");
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderNullOrder() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderNullOrder() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequestNullOrder(tickerSymbol);
        assertNotNull(request);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptyAction() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptyAction() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setAction(StringUtils.EMPTY);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptyOrderType() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptyOrderType() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setOrderType(StringUtils.EMPTY);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    //TODO test invalid order type not in Enum

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderEmptyTickerSymbol() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderEmptyTickerSymbol() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setTickerSymbol(StringUtils.EMPTY);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderNullQuantity() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderNullQuantity() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.getOrder().setQuantity(null);

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderLimitOrderNullPrice() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderLimitOrderNullPrice() *****");

        String tickerSymbol = "WMT";
        SubmitOrderRequest request = buildSubmitOrderRequest(tickerSymbol);
        assertNotNull(request);
        request.setOrder(buildStockOrderNullPrice(tickerSymbol));
        request.getOrder().setOrderType(EnumOrderTypes.LIMIT_ORDER.getOrderType());

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderNullRequest() {
        LOGGER.debug("***** Commence StockOrderEndpointTests.testSubmitOrderNullRequest() *****");

        SubmitOrderResponse actualResponse = stockOrderEndpoint.submitOrder(null);
    }


    private SubmitOrderResponse buildSubmitOrderResponse(final String symbol) {
        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(EnumStatusCodes.SUCCESS.getStatudCode());
        response.setStatusDesc("REQUEST SUCCESSFUL");
        return response;
    }

    private SubmitOrderRequest buildSubmitOrderRequest(final String symbol) {
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setHead(buildRequestHeader());
        request.setOrder(buildStockOrder(symbol));
        return request;
    }

    private StockOrder buildStockOrder(final String symbol) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setOrderDate(DateUtils.getCurrentXMLGregorianCalendar());
        stockOrder.setAction(EnumAction.BUY.getActionType());
        stockOrder.setQuantity(1);
        stockOrder.setTickerSymbol(symbol);
        stockOrder.setOrderType(EnumOrderTypes.MARKET_ORDER.getOrderType());
        stockOrder.setStockPrice(NumberUtils.toFloat("65.00"));
        return stockOrder;
    }

    private StockOrder buildStockOrderNullPrice(final String symbol) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setOrderDate(DateUtils.getCurrentXMLGregorianCalendar());
        stockOrder.setAction(EnumAction.BUY.getActionType());
        stockOrder.setQuantity(1);
        stockOrder.setTickerSymbol(symbol);
        stockOrder.setOrderType(EnumOrderTypes.MARKET_ORDER.getOrderType());

        return stockOrder;
    }

    private SubmitOrderRequest buildSubmitOrderRequestNullOrder(final String symbol) {
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setHead(buildRequestHeader());

        return request;
    }

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

    private GetStockPriceRequest buildInvalidGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildInvalidRequestHeader());
        request.setTickerSymbol(symbol);
        return request;
    }

    private GetStockPriceRequest buildEmptyGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildEmptyRequestHeader());
        request.setTickerSymbol(symbol);
        return request;
    }

    private GetStockPriceRequest buildNullGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();

        request.setTickerSymbol(symbol);
        return request;
    }

    private RequestHeader buildRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("STOCKTICKER_20170131");
        return header;
    }

    private RequestHeader buildInvalidRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("INVALID");
        return header;
    }

    private RequestHeader buildEmptyRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("");
        return header;
    }
}
