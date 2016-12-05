package prv.mark.project.stockticker.endpoint;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import prv.mark.project.common.domain.EnumOrderTypes;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.exception.SOAPGeneralFault;
import prv.mark.project.common.exception.SOAPServerException;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockticker.service.StockTickerService;
import prv.mark.project.xml.stocks.*;
import prv.mark.xml.stocks.*;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;


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

    @Autowired
    private StockTickerService stockTickerService;
    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    private Predicate<String> validStockSymbolPattern = i -> {
        return Pattern.matches("[A-Z0-9]{1,12}", i);
    };


    /**
     * Endpoint method to accept a {@link GetStockPriceRequest} and return a response.
     * @param getStockPriceRequest {@link GetStockPriceRequest} Request payload
     * @return {@link GetStockPriceResponse}
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStockPriceRequest")
    @ResponsePayload
    public GetStockPriceResponse getStockPrice(@RequestPayload GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerEndpoint.submitOrder() entry ...");

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

    /**
     * Endpoint method to accept a {@link SubmitOrderRequest} and return a response.
     * @param submitOrderRequest {@link SubmitOrderRequest} Request payload
     * @return {@link SubmitOrderResponse}
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SubmitOrderRequest")
    @ResponsePayload
    public SubmitOrderResponse placeStockOrder(@RequestPayload SubmitOrderRequest submitOrderRequest) {
        LOGGER.debug("*** StockTickerEndpoint.submitOrder() entry ...");

        validateSubmitOrderRequest(submitOrderRequest);
        LOGGER.debug("Request is valid: {}", submitOrderRequest.toString());
        submitOrderRequest.getOrder().setOrderDate(DateUtils.getCurrentXMLGregorianCalendar());

        SubmitOrderResponse submitOrderResponse;
        try {
            submitOrderResponse  = stockTickerService.placeOrder(submitOrderRequest);

        } catch (SOAPClientException sce) {

            LOGGER.error("SoapClientException caught: {}", sce.getMessage());
            throw new SOAPClientException(sce.getMessage());

        } catch (SOAPServerException | WebServiceClientException sse) {

            LOGGER.error("SoapServerException caught: {}", sse.getMessage());
            throw new SOAPServerException(sse.getMessage());
        }

        LOGGER.debug("Returning response: {}", submitOrderResponse.toString());
        return submitOrderResponse;
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
        if (!requestHeader.getSource().equals(applicationParameterSource.getParm(StringUtils.PARM_VALID_HEADER_SOURCE))) { //TODO
        //if (!requestHeader.getSource().equals("STOCKTICKER")) {
            LOGGER.error("*** Invalid Header Source {} ***", requestHeader.getSource());
            throw new SOAPGeneralFault();
        }
    }

    private void validateGetStockPriceRequest(final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerEndpoint.validateGetStockPriceRequest()");
        String message = "*** GetStockPriceRequest IS NULL!!! ***";
        if (getStockPriceRequest == null) {
            LOGGER.error(message);
            throw new SOAPClientException(message);
        }

        validateHeader(getStockPriceRequest.getHead());

        if (getStockPriceRequest.getTickerSymbol().isEmpty()) {
            LOGGER.error(message);
            throw new SOAPGeneralFault(message);
        }

        String newMessage = "*** Invalid Ticker Symbol: " + getStockPriceRequest.getTickerSymbol() + " ***";
        String tickerSymbol = Optional.of(getStockPriceRequest.getTickerSymbol())
                                .filter(validStockSymbolPattern)
                                .orElseThrow(() -> new SOAPGeneralFault(newMessage));
        LOGGER.debug("tickerSymbol:{}", tickerSymbol);
    }

    private void validateSubmitOrderRequest(final SubmitOrderRequest submitOrderRequest) {
        LOGGER.debug("*** StockTickerEndpoint.validateSubmitOrderRequest()");
        if (submitOrderRequest == null) {
            String message = "*** SubmitOrderRequest IS NULL!!! ***";
            LOGGER.error(message);
            throw new SOAPClientException(message);
        }

        validateHeader(submitOrderRequest.getHead());

        if (submitOrderRequest.getOrder() == null) {
            String message = "*** SubmitOrderRequest.Order IS NULL!!! ***";
            LOGGER.error(message);
            throw new SOAPGeneralFault(message);
        }

        if (StringUtils.isEmpty(submitOrderRequest.getOrder().getAction())
            || StringUtils.isEmpty(submitOrderRequest.getOrder().getOrderType())
            || StringUtils.isEmpty(submitOrderRequest.getOrder().getTickerSymbol())) {

            String message = "Required field is empty:" + submitOrderRequest.getOrder().getAction() + " "
                    + submitOrderRequest.getOrder().getOrderType() + " "
                    + submitOrderRequest.getOrder().getTickerSymbol();
            LOGGER.error(message);
            throw new SOAPGeneralFault(message);
        }

        if (submitOrderRequest.getOrder().getQuantity() == null
                || submitOrderRequest.getOrder().getQuantity() <= 0) {
            String message = "Invalid value for submitOrderRequest.getOrder().getQuantity()";
            LOGGER.error(message);
            throw new SOAPGeneralFault(message);
        }

        if (submitOrderRequest.getOrder().getOrderType().equalsIgnoreCase(EnumOrderTypes.LIMIT_ORDER.getOrderType())
            && (submitOrderRequest.getOrder().getStockPrice() < 0.00)) {
            String message = "Invalid value for submitOrderRequest.getOrder().getQuantity()";
            LOGGER.error(message);
            throw new SOAPGeneralFault(message);
        }

        String newMessage = "*** Invalid Ticker Symbol: " + submitOrderRequest.getOrder().getTickerSymbol() + " ***";
        String tickerSymbol = Optional.of(submitOrderRequest.getOrder().getTickerSymbol())
                .filter(validStockSymbolPattern)
                .orElseThrow(() -> new SOAPGeneralFault(newMessage));
        LOGGER.debug("tickerSymbol:{}", tickerSymbol);
    }

} 
