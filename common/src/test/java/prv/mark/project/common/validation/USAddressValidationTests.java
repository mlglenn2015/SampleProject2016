package prv.mark.project.common.validation;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import prv.mark.project.common.config.UtilsTestConfig;
import prv.mark.project.common.domain.USPostalAddress;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for US Address validation.
 *
 * Created by mlglenn on 11/15/2016.
 */
@ContextConfiguration(classes = UtilsTestConfig.class)
public class USAddressValidationTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(USAddressValidationTests.class);

    /*BiFunction<ServiceableAddress, USPostalAddress, Boolean> validServiceableAddress = (serviceableAddress, address) -> TODO cleanup
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

    @Autowired
    private Validator validator;

    @Before
    public void setUp() {
        assertNotNull(validator);
    }

    @Test
    public void testValidUSPostalAddress() {
        USPostalAddress usPostalAddress = buildAddress();
        assertNotNull(usPostalAddress);
        Set<ConstraintViolation<USPostalAddress>> violations = validator.validate(usPostalAddress);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInValidUSState() {
        USPostalAddress usPostalAddress = buildAddress();
        assertNotNull(usPostalAddress);
        usPostalAddress.setState("XX");
        validate(usPostalAddress, "state");
    }

    @Test
    public void testInValidZipCode() {
        USPostalAddress usPostalAddress = buildAddress();
        assertNotNull(usPostalAddress);
        usPostalAddress.setZipCode("XXXXX");
        validate(usPostalAddress, "zipCode");
    }

    @Test
    public void testInValidZip4() {
        USPostalAddress usPostalAddress = buildAddress();
        assertNotNull(usPostalAddress);
        usPostalAddress.setZip4("XXXX");
        validate(usPostalAddress, "zip4");
    }

    @Test
    public void testInValidZipPlus4() {
        USPostalAddress usPostalAddress = buildAddress();
        assertNotNull(usPostalAddress);
        usPostalAddress.setZipPlus4("XXXXX-XXXX");
        validate(usPostalAddress, "zipPlus4");
    }

    private void validate(USPostalAddress usPostalAddress, String propertyUnderTest) {
        Set<ConstraintViolation<USPostalAddress>> violations = validator.validate(usPostalAddress);
        assertTrue(!violations.isEmpty());
        assertTrue(violations.size() == 1);
        LOGGER.debug("Testing property: {}", propertyUnderTest);
        ConstraintViolation<USPostalAddress> violation = violations.iterator().next();
        assertTrue(violation.getPropertyPath().toString().equals(propertyUnderTest));
    }

    private USPostalAddress buildAddress() {
        USPostalAddress usPostalAddress = new USPostalAddress();
        usPostalAddress.setPrimaryRange("123");
        usPostalAddress.setPreDirectional("W");
        usPostalAddress.setPrimaryName("Main");
        //usPostalAddress.setPostDirectional();
        usPostalAddress.setSuffix("St");
        usPostalAddress.setCity("ANYTOWN");
        usPostalAddress.setState("FL");
        usPostalAddress.setZipCode("12345");
        usPostalAddress.setZip4("1234");
        usPostalAddress.setZipPlus4(usPostalAddress.getZipCode()+"-"+usPostalAddress.getZip4());
        usPostalAddress.setAddressLine1("123 W Main St");
        //usPostalAddress.setAddressLine2();
        return usPostalAddress;
    }
}
