package prv.mark.project.stockticker.endpoint;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.exception.SOAPGeneralFault;
import prv.mark.project.common.exception.SOAPServerException;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockticker.service.StockTickerService;
import prv.mark.xml.stocks.GetStockPriceRequest;
import prv.mark.xml.stocks.GetStockPriceResponse;
import prv.mark.xml.stocks.RequestHeader;


/**
 * SOAP Web Service endpoint for stock ticker service.
 *
 * WSDL http://localhost:13001/Stockticker/StockTicker.wsdl
 *
 * @author mlglenn
 */
@Endpoint
public class StockTickerEndpoint {

    private static final String NAMESPACE_URI = "http://prv.mark.project/stocks";
    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerEndpoint.class);
    //private static final String HEADER_SOURCE = "STOCKTICKER"; TODO remove
    private static final String SERVER_FAULT_STR = "server";
    private static final String CHARSET_UTF8 = "UTF-8";

    @Autowired
    private StockTickerService stockTickerService;
    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    /*Predicate<String> validDivision = i -> { TODO cleanup
        String[] validDivs = {"RES"};
        return Arrays.asList(validDivs).contains(i);
    };

    Predicate<String> validStatePattern = i -> {
        return Pattern.matches("[A-Z]{2}", i);
    };

    Predicate<String> fractionalHouseNumberPattern = i -> {
        if (Pattern.matches(".*\\d+/\\d+.*", i)) return true;
        return false;
    };*/


    /**
     * Endpoint method to accept a {@link GetStockPriceRequest} and return a response.
     * @param getStockPriceRequest {@link GetStockPriceRequest} Request payload
     * @return {@link GetStockPriceResponse}
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStockPriceRequest")
    @ResponsePayload
    public GetStockPriceResponse getStockPrice(@RequestPayload GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerEndpoint.submitOrder() entry ...");

        //Validate the request
        validateGetStockPriceRequest(getStockPriceRequest);
        LOGGER.debug("Request is valid: {}", getStockPriceRequest.toString());

        GetStockPriceResponse getStockPriceResponse;
        try {
            getStockPriceResponse  = stockTickerService.getStockPrice(getStockPriceRequest);

        } catch (SOAPClientException sce) {

            LOGGER.error("SoapClientException caught: {}", sce.getMessage());
            throw new SOAPClientException(sce.getMessage());

        } catch (SOAPServerException | WebServiceClientException sse) {

            LOGGER.error("SoapServerException caught: {}", sse.getMessage());
            throw new SOAPServerException(sse.getMessage());

        }

        LOGGER.debug("Returning response: {}", getStockPriceResponse.toString());

        return getStockPriceResponse;
    }


    private void validateHeader(final RequestHeader requestHeader) {
        LOGGER.debug("*** StockTickerEndpoint.validateHeader()");
        if (requestHeader == null) {
            LOGGER.error("*** RequestHeader IS NULL!!! ***");
            throw new SOAPGeneralFault();
        }
        if (StringUtils.isEmpty(requestHeader.getSource())) {
            LOGGER.error("*** Invalid Header Source {} ***", requestHeader.getSource());
            throw new SOAPGeneralFault();
        }
        if (!requestHeader.getSource().equals(applicationParameterSource.getParm(StringUtils.PARM_VALID_HEADER_SOURCE))) {
            LOGGER.error("*** Invalid Header Source {} ***", requestHeader.getSource());
            throw new SOAPGeneralFault();
        }
    }

    private void validateGetStockPriceRequest(final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerEndpoint.validateGetStockPriceRequest()");
        if (getStockPriceRequest == null) {
            String message = "*** GetStockPriceRequest IS NULL!!! ***";
            LOGGER.error(message);
            throw new SOAPClientException(message);
        }

        validateHeader(getStockPriceRequest.getHead());

        if (getStockPriceRequest.getTickerSymbol().isEmpty()) {
            String message = "*** Invalid Ticker Symbol ***";
            LOGGER.error(message);
            throw new SOAPGeneralFault(message);
        }
    }

} 
