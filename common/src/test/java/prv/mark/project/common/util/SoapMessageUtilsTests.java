package prv.mark.project.common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.soap.client.SoapFaultClientException;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Junit tests for the {@link NumberUtils} class.
 *
 * Created by mlglenn on 11/15/2016.
 */
public class SoapMessageUtilsTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapMessageUtilsTests.class);

    @Test
    public void defaultTest() {
        LOGGER.debug("NumberUtilsTests.defaultTest()");
    }

    @Test
    public void testGetSoapFaultClientException() {
        String xml = buildXml();
        assertNotNull(xml);
        assertNotNull(SoapMessageUtils.getSoapFaultClientException(true, xml));
        assertNotNull(SoapMessageUtils.getSoapFaultClientException(false, xml));
    }

    @Test
    public void testGetSoapMessageFromString() {
        LOGGER.debug("SoapMessageUtilsTests.testGetSoapMessageFromString()");
        SOAPMessage soapMessage;
        try {
            soapMessage = SoapMessageUtils.getSoapMessageFromString(buildXml());
            assertNotNull(soapMessage);
        } catch (SOAPException|IOException e) {
            LOGGER.debug("Exception caught {}", e);
        }
    }

    private String buildXml() {
        return new StringBuilder()
                .append("<?xml version=\"1.0\"?>")
                .append("<SOAP-ENV:Envelope")
                .append(" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">")
                .append("<SOAP-ENV:Body>")
                .append("<SOAP-ENV:Fault>")
                .append("<faultcode>100</faultcode>")
                .append("<faultstring>Test</faultstring>")
                .append("<detail>Test Detail</detail>")
                .append("</SOAP-ENV:Fault>")
                .append("</SOAP-ENV:Body>")
                .append("</SOAP-ENV:Envelope>")
                .toString();
    }
}
