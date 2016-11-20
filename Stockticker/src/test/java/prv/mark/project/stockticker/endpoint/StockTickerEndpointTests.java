package prv.mark.project.stockticker.endpoint;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import prv.mark.project.stockticker.config.StockTickerTestConfig;

import java.util.Arrays;
import java.util.function.Predicate;


@ContextConfiguration(classes = {StockTickerTestConfig.class})
@ActiveProfiles({"test"})
public class StockTickerEndpointTests { //extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerEndpointTests.class);
    private static final String HEADER_SOURCE = "TESTING";
    private static final String DIVISION = "RES";
    private static final String ITEM_ID = "U-2";
    private static final String TRACKING_ID = "1ZV50R920365556807";
    private static final int SUCCESSFUL = 1;
    private static final int FAILURE = 0;

    //@Autowired
    //@InjectMocks

    @Autowired
    private StockTickerEndpoint stockTickerEndpoint;

    //@Mock
    //private Service service;

    //@Mock
    //private LookupService ;ookupService;

    Predicate<String> validDivision = i -> {
        String[] validDivs = {"RES"};
        return Arrays.asList(validDivs).contains(i);
    };

    /*BiFunction<ServiceableAddress, USPostalAddress, Boolean> validServiceableAddress = (serviceableAddress, address) ->
            Optional.of(serviceableAddress)
                    .filter(s -> StringUtils.isNotEmpty(s.getAddressLine1()))
                    .filter(s -> Optional.of(s.getUSPSAddress()).isPresent())
                    .filter(s -> Optional.of(s.getUSPSAddress().getCity()).isPresent())
                    .filter(s -> Optional.of(s.getUSPSAddress().getState()).isPresent())
                    .filter(s -> Optional.of(s.getUSPSAddress().getZip()).isPresent())
                    .filter(s -> Optional.of(s.getUSPSAddress().getZip4()).isPresent())
                    .filter(s -> s.getUSPSAddress().getCity().equalsIgnoreCase(address.getCity()))
                    .filter(s -> s.getUSPSAddress().getState().equalsIgnoreCase(address.getState()))
                    .filter(s -> s.getUSPSAddress().getZip().equalsIgnoreCase(address.getZipCode()))
                    .filter(s -> s.getUSPSAddress().getZip4().equalsIgnoreCase(address.getZip4()))
                    .isPresent();*/


    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        //assertNotNull(Endpoint);
        //assertNotNull(serviceabilityService);
    }

    //@Override
    public void tearDown() {
        //super.tearDown();
    }

    @Test
    public void dummyTest() {
        LOGGER.debug("StockTickerSimulatorEndpointTests.dummyTest()");
    }

    /*@Test
    public void testSubmitOrder() {
        LOGGER.debug("EndpointTests.testSubmitOrder()");
        SubmitOrderRequest submitOrderRequest = buildSubmitOrderRequest();
        assertNotNull(submitOrderRequest);
        SubmitOrderResponse submitOrderResponse = Endpoint.submitOrder(submitOrderRequest);
        assertNotNull(submitOrderResponse);
        assertTrue(submitOrderResponse.getSuccess() == 1);
    }

    @Test(expected = SOAPAuthenticationFault.class)
    public void testSubmitOrderInvalidCustomerId() {
        LOGGER.debug("EndpointTests.testSubmitOrderInvalidCustomerId()");
        SubmitOrderRequest submitOrderRequest = buildSubmitOrderRequest();
        submitOrderRequest.getHead().setCustomerId(StringUtils.EMPTY);
        assertNotNull(submitOrderRequest);
        SubmitOrderResponse submitOrderResponse = Endpoint.submitOrder(submitOrderRequest);
    }*/

}
