package prv.mark.project.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This example illustrates using a URL to access resources
 * on a secure site.
 *
 * If you are running inside a firewall, please also set the following
 * Java system properties to the appropriate value:
 *
 *   https.proxyHost = <secure proxy server hostname>
 *   https.proxyPort = <secure proxy server port>
 *
 * Created by mlglenn on 12/22/2016.
 */
public class URLReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLReader.class);

    public static void main(String[] args) throws Exception {

        URL verisign = new URL( "https://<host>:<port>/<URI>?wsdl" ) ;

        LOGGER.info( "Opening URL: " + verisign.toString() ) ;

        BufferedReader in = new BufferedReader(new InputStreamReader(verisign.openStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            LOGGER.info(inputLine);

        in.close();
    }

}
