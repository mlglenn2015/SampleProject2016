package prv.mark.project.stockticker.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import prv.mark.project.stockticker.config.StockTickerTestConfig;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

@ContextConfiguration(classes = {StockTickerTestConfig.class})
@ActiveProfiles({"test"})
public class StockTickerServiceTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerServiceTests.class);
    private static final String IN_ADDR_USPS = "USPS";
    //private USPostalAddress address;

    //@Autowired
    //@InjectMocks
    private StockTickerService stockTickerService;

    //@Mock
    //private LookupService lookupService;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        //assertNotNull(stockTickerService);
    }

    @Test
    public void testIt() {

    }

    /*@Test
    public void testLookupUSPostalAddress() {
        when(ookupService.searchByAddress(TPA, address, IN_ADDR)).thenReturn(getValidResult());
        result = service.lookupUSPostalAddress(TPA_, address, IN_ADDR);
        verify(ookupService).searchByAddress(TPA, address, IN_ADDR_);

        assertTrue(Optional.of(result)
                .filter(r -> validDivision.apply(r.getDivisionId()))
                .filter(esult::erviceable)
                .filter(r -> !(CollectionUtils.isEmpty(r.getAddresses())))
                .filter(res -> Optional.of(res.getAddresses().stream()
                        .filter(r -> r.getAddressLine1().equalsIgnoreCase(address.getAddressLine1()))
                        .filter(r -> r.getAddressLine2().equalsIgnoreCase(address.getAddressLine2()))
                        .filter(r -> r.getCity().equalsIgnoreCase(address.getCity()))
                        .filter(r -> r.getState().equalsIgnoreCase(address.getState()))
                        .filter(r -> r.getZipCode().equalsIgnoreCase(address.getZipCode()))
                        .filter(r -> r.getZip4().equalsIgnoreCase(address.getZip4()))
                        .findFirst())
                        .isPresent())
                .isPresent());
    }*/

    /*@Test
    public void testookupUSPostalAddressUSPSNonServiceable() {
        when(ookupService.searchByAddress(TPA_, address, IN_ADDR_))
                .thenReturn(getNonServiceableResult());
        result = seervice.lookupUSPostalAddress(TPA_, address, IN_ADDR_);
        verify(LookupService).searchByAddress(TPA_, address, IN_ADDR_);

        assertFalse(Optional.ofNullable(result)
                .filter(r -> Optional.ofNullable(r.getDivisionId()).isPresent())
                .isPresent());
    }*/

    /*@Test
    public void testookupUSPostalAddressUSPS() {
        when(serviceabilityLookupService.searchByAddress(TPA_, address, IN_ADDR_)).thenReturn(getValidResult());
        result = serviceabilityService.lookupUSPostalAddress(TPA_, address, IN_ADDR_);
        verify(serviceabilityLookupService).searchByAddress(TPA_, address, IN_ADDR_);

        assertTrue(Optional.of(result)
                .filter(r -> validNosaDivision.apply(r.getDivisionId()))
                .filter(Result::getServiceable)
                .filter(r -> !(CollectionUtils.isEmpty(r.getAddresses())))
                .filter(res -> Optional.of(res.getAddresses().stream()
                        .filter(r -> r.getAddressLine1().equalsIgnoreCase(address.getAddressLine1()))
                        .filter(r -> r.getAddressLine2().equalsIgnoreCase(address.getAddressLine2()))
                        .filter(r -> r.getCity().equalsIgnoreCase(address.getCity()))
                        .filter(r -> r.getState().equalsIgnoreCase(address.getState()))
                        .filter(r -> r.getZipCode().equalsIgnoreCase(address.getZipCode()))
                        .filter(r -> r.getZip4().equalsIgnoreCase(address.getZip4()))
                        .findFirst())
                        .isPresent())
                .isPresent());
    }*/
}
