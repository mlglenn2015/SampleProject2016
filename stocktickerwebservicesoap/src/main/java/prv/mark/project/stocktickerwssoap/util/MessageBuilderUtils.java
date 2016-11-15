package prv.mark.project.stocktickerwssoap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mlglenn on 11/14/2016.
 */
public class MessageBuilderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBuilderUtils.class);
    private static final String SERVER_FAULT_STR = "server";
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * Generates a SOAP Fault message.
     * @param errCode {@link String}
     * @param errDesc {@link String}
     * @return {@link String}
     */
    public static String getSoapMessageFromString(final String errCode, final String errDesc, final String detail) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\"?>")
                .append("<SOAP-ENV:Envelope")
                .append(" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">")
                .append("<SOAP-ENV:Body>")
                .append("<SOAP-ENV:Fault>")
                .append("<faultcode>").append(errCode).append("</faultcode>")
                .append("<faultstring>").append(errDesc).append("</faultstring>")
                .append("<detail>").append(detail).append("</detail>")
                .append("</SOAP-ENV:Fault>")
                .append("</SOAP-ENV:Body>")
                .append("</SOAP-ENV:Envelope>")
                .toString();
    }

}
