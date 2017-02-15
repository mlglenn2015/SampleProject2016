package prv.mark.project.stockservice.endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.exception.SOAPServerException;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;
import prv.mark.project.stockservice.service.StockServiceOrderService;
import prv.mark.project.stockservice.util.StockOrderCommonTestMethods;
import prv.mark.project.testutils.junit.AbstractAppWebServiceEndpointTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * JUnit tests for the StockOrderEndpoint.
 *
 * Created by Owner on 2/6/2017.
 */
public class StockOrderMockEndpointTests extends AbstractAppWebServiceEndpointTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderMockEndpointTests.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private SoapFaultAnnotationExceptionResolver soapFaultExceptionResolver;

    @Autowired
    @InjectMocks
    private StockOrderEndpoint stockOrderEndpoint;

    @Mock
    private StockServiceOrderService stockServiceOrderService;


    @Before
    public void setUp() {
        LOGGER.debug("***** StockOrderMockEndpointTests.setUp() *****");
        initMocks(this);
        assertNotNull(env);
        assertNotNull(webServiceTemplate);
        assertNotNull(soapFaultExceptionResolver);
        assertNotNull(stockOrderEndpoint);
    }

    @After
    public void tearDown() {
        LOGGER.debug("***** StockOrderMockEndpointTests.tearDown() *****");
    }

    @Test
    public void defaultTest() {
        LOGGER.debug("***** StockOrderMockEndpointTests.defaultTest() *****");
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceSOAPClientException() {
        LOGGER.debug("Begin StockOrderMockEndpointTests.testGetStockPriceSOAPClientException()");
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest("WMT");
        assertNotNull(request);

        doThrow(new SOAPClientException("StockOrderMockEndpointTests.testGetStockPriceSOAPClientException()"))
                .when(stockServiceOrderService)
                .getStockPrice(request);

        GetStockPriceResponse response = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testGetStockPriceSOAPServerException() {
        LOGGER.debug("Begin StockOrderMockEndpointTests.testGetStockPriceSOAPServerException()");
        GetStockPriceRequest request = StockOrderCommonTestMethods.buildGetStockPriceRequest("WMT");
        assertNotNull(request);

        doThrow(new SOAPServerException("StockOrderMockEndpointTests.testGetStockPriceSOAPServerException()"))
                .when(stockServiceOrderService)
                .getStockPrice(request);

        GetStockPriceResponse response = stockOrderEndpoint.getStockPrice(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderSOAPClientException() {
        LOGGER.debug("Begin StockOrderMockEndpointTests.testSubmitOrderSOAPClientException()");
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest("WMT");
        assertNotNull(request);

        doThrow(new SOAPClientException("StockOrderMockEndpointTests.testSubmitOrderSOAPClientException()"))
                .when(stockServiceOrderService)
                .placeOrder(request);

        SubmitOrderResponse response = stockOrderEndpoint.submitOrder(request);
    }

    @Test(expected = SOAPClientException.class)
    public void testSubmitOrderSOAPServerException() {
        LOGGER.debug("Begin StockOrderMockEndpointTests.testSubmitOrderSOAPServerException()");
        SubmitOrderRequest request = StockOrderCommonTestMethods.buildSubmitOrderRequest("WMT");
        assertNotNull(request);

        doThrow(new SOAPServerException("StockOrderMockEndpointTests.testSubmitOrderSOAPServerException()"))
                .when(stockServiceOrderService)
                .placeOrder(request);

        SubmitOrderResponse response = stockOrderEndpoint.submitOrder(request);
    }
}
