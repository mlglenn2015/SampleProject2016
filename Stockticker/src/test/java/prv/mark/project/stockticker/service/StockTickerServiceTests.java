package prv.mark.project.stockticker.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import prv.mark.project.stockticker.config.StockTickerTestConfig;

@ContextConfiguration(classes = {StockTickerTestConfig.class})
@ActiveProfiles({"test"})
public class StockTickerServiceTests { //TODO extends AbstractAppTransactionalTest {

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
    public void testLookupUSPostalAddressNosa() {
        when(serviceabilityLookupService.searchByAddress(TPA_DIV, address, IN_ADDR_NOSA)).thenReturn(getValidResult());
        result = serviceabilityService.lookupUSPostalAddress(TPA_DIV, address, IN_ADDR_NOSA);
        verify(serviceabilityLookupService).searchByAddress(TPA_DIV, address, IN_ADDR_NOSA);

        assertTrue(Optional.of(result)
                .filter(r -> validNosaDivision.apply(r.getDivisionId()))
                .filter(ServiceabilityResult::getServiceable)
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
    public void testLookupUSPostalAddressUSPSNonServiceable() {
        when(serviceabilityLookupService.searchByAddress(TPA_DIV, address, IN_ADDR_USPS))
                .thenReturn(getNonServiceableResult());
        result = serviceabilityService.lookupUSPostalAddress(TPA_DIV, address, IN_ADDR_USPS);
        verify(serviceabilityLookupService).searchByAddress(TPA_DIV, address, IN_ADDR_USPS);

        assertFalse(Optional.ofNullable(result)
                .filter(r -> Optional.ofNullable(r.getDivisionId()).isPresent())
                .isPresent());
    }*/

    /*@Test
    public void testLookupUSPostalAddressUSPS() {
        when(serviceabilityLookupService.searchByAddress(TPA_DIV, address, IN_ADDR_USPS)).thenReturn(getValidResult());
        result = serviceabilityService.lookupUSPostalAddress(TPA_DIV, address, IN_ADDR_USPS);
        verify(serviceabilityLookupService).searchByAddress(TPA_DIV, address, IN_ADDR_USPS);

        assertTrue(Optional.of(result)
                .filter(r -> validNosaDivision.apply(r.getDivisionId()))
                .filter(ServiceabilityResult::getServiceable)
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
