package prv.mark.project.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by mlglenn on 10/24/2016.
 */
public final class ApplicationTestExceptionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTestExceptionUtils.class);
    private static final String SERVER_FAULT_STR = "server";
    public static final String CHARSET_UTF8 = "UTF-8";


    /**
     * Gets the desired SOAP Fault exception.
     * @param serverMsg {@link boolean}
     * @return {@link SoapFaultClientException}
     */
    public static SoapFaultClientException getSoapFaultClientException(final boolean serverMsg) {
        try {
            SaajSoapMessageFactory factory = new SaajSoapMessageFactory(MessageFactory.newInstance());

            SoapFaultClientException exception;
            SaajSoapMessage msg;
            if (serverMsg) {
                msg = factory.createWebServiceMessage();
                LOGGER.debug(getSoapFaultMessage(SERVER_FAULT_STR, "Test Fault Description"));
                if (msg.getSaajMessage().getSOAPBody().getFault() == null) {
                    msg.setSaajMessage(getSoapMessageFromString(
                            getSoapFaultMessage(SERVER_FAULT_STR, "Test Fault Description")));
                }
                exception = new SoapFaultClientException(msg);
            } else {
                msg = factory.createWebServiceMessage();
                exception = new SoapFaultClientException(msg);
            }
            return exception;

        } catch (IOException | SOAPException | SoapFaultClientException e) {
            LOGGER.debug("Exception caught while creating SoapFaultClientException");
        }
        return null;
    }

    /**
     * Generates a SOAP message from an xml string.
     *
     * @param xml {@link String}
     * @return {@link SOAPMessage}
     * @throws SOAPException
     * @throws IOException
     */
    public static SOAPMessage getSoapMessageFromString(final String xml) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(
                new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName(CHARSET_UTF8))));
        return message;
    }

    /**
     * Generates a test SOAP Fault message.
     * @param errCode {@link String}
     * @param errDesc {@link String}
     * @return {@link String}
     */
    public static String getSoapFaultMessage(final String errCode, final String errDesc) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\"?>")
                .append("<SOAP-ENV:Envelope")
                .append(" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">")
                .append("<SOAP-ENV:Body>")
                .append("<SOAP-ENV:Fault>")
                .append("<faultcode>").append(errCode).append("</faultcode>")
                .append("<faultstring>").append(errDesc).append("</faultstring>")
                .append("<detail>Unit Tests of ExceptionRouter class</detail>")
                .append("</SOAP-ENV:Fault>")
                .append("</SOAP-ENV:Body>")
                .append("</SOAP-ENV:Envelope>")
                .toString();
    }

}
