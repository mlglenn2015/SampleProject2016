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
 * Utility class for SOAP messages.
 *
 * Created by mlglenn on 11/14/2016.
 */
public class SoapMessageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapMessageUtils.class);
    private static final String SERVER_FAULT_STR = "server";
    public static final String CHARSET_UTF8 = "UTF-8";


    /**
     * Returns a {@link SoapFaultClientException} from a custom XML message.
     *
     * @param serverMsg to indicate which message type
     * @param xml XML to create the message from
     * @return {@link SoapFaultClientException}
     */
    public static SoapFaultClientException getSoapFaultClientException(final boolean serverMsg,
                                                                       final String xml) {
        try {
            SaajSoapMessageFactory factory = new SaajSoapMessageFactory(MessageFactory.newInstance());

            SoapFaultClientException exception;
            SaajSoapMessage msg;
            if (serverMsg) {
                msg = factory.createWebServiceMessage();
                LOGGER.debug(xml);
                if (msg.getSaajMessage().getSOAPBody().getFault() == null) {
                    msg.setSaajMessage(getSoapMessageFromString(xml));
                }
                exception = new SoapFaultClientException(msg);
            } else {
                msg = factory.createWebServiceMessage();
                exception = new SoapFaultClientException(msg);
            }
            return exception;

        } catch (IOException | SOAPException | SoapFaultClientException e) {
            LOGGER.debug("SoapMessageUtils: Exception caught while creating SoapFaultClientException");
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
        return factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName(CHARSET_UTF8))));
    }

}
