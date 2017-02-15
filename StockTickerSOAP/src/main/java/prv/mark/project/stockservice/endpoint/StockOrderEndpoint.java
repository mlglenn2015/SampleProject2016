package prv.mark.project.stockservice.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import prv.mark.project.common.domain.EnumAction;
import prv.mark.project.common.domain.EnumOrderTypes;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.exception.SOAPGeneralFault;
import prv.mark.project.common.exception.SOAPServerException;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockservice.schemas.RequestHeader;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;
import prv.mark.project.stockservice.service.StockServiceOrderService;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;


/**
 * Spring WS Endpoint class.
 * https://spring.io/guides/gs/producing-web-service/
 *
 * WSDL location: http://<host>:<port>/StockTickerSOAP/ws/stockservice.wsdl
 *
 * @author mlglenn on 1/12/2017.
 */
@Endpoint
public class StockOrderEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderEndpoint.class);
    private static final String NAMESPACE_URI = "http://project.mark.prv/stockservice/schemas";

    @Value("${application.id}")
    private String applicationId;

    @Autowired
    private ApplicationParameterSource applicationParameterSource;
    @Autowired
    private ApplicationMessageSource applicationMessageSource;
    @Autowired
    private StockServiceOrderService stockServiceOrderService;


    /* Predicate to validate the Ticker Symbol */
    private Predicate<String> validStockSymbolPattern = i -> {
        return Pattern.matches("[A-Z|0-9]{1,12}", i);
        //return Pattern.matches("\w", i);
    };


    /**
     * Endpoint method to accept a {@link GetStockPriceRequest} and return a response.
     * @param getStockPriceRequest {@link GetStockPriceRequest} Request payload
     * @return {@link GetStockPriceResponse}
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStockPriceRequest")
    @ResponsePayload
    public GetStockPriceResponse getStockPrice(@RequestPayload GetStockPriceRequest getStockPriceRequest) {
        LOGGER.trace("Application ID: {}", applicationId);
        LOGGER.info("*** StockOrderEndpoint.getStockPrice() entry ...");

        validateGetStockPriceRequest(getStockPriceRequest);

        if (!stockServiceOrderService.isSymbolInExchange(getStockPriceRequest.getTickerSymbol())) {
            LOGGER.error("*** Invalid Ticker Symbol {} ***", getStockPriceRequest.getTickerSymbol());
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.stocksymbolinexchange"));
        }

        LOGGER.debug("Request is valid: {}", getStockPriceRequest.toString());

        GetStockPriceResponse getStockPriceResponse;

        /*GetStockPriceResponse getStockPriceResponse = new GetStockPriceResponse();  //TODO remove; testing only
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(1);
        stockQuote.setStatusText("SUCCESS 20170131");
        stockQuote.setTickerSymbol("WMT");
        stockQuote.setStockPrice(NumberUtils.toFloat("68.00"));
        getStockPriceResponse.setQuote(stockQuote);*/

        try {
            getStockPriceResponse  = stockServiceOrderService.getStockPrice(getStockPriceRequest);

        } catch (SOAPClientException sce) {

            LOGGER.error("SoapClientException caught: {}", sce.getMessage());
            throw new SOAPClientException(sce.getMessage());

        } catch (SOAPServerException | WebServiceClientException sse) {

            LOGGER.error("SoapServerException caught: {}", sse.getMessage());
            throw new SOAPServerException(sse.getMessage());
        }

        if (getStockPriceResponse == null) {
            LOGGER.debug("Returning null response");
        } else {
            LOGGER.debug("Returning response: {}", getStockPriceResponse.getQuote().getTickerSymbol());
        }

        return getStockPriceResponse;
    }

    /**
     * Endpoint method to accept a {@link SubmitOrderRequest} and return a response.
     * @param submitOrderRequest {@link SubmitOrderRequest} Request payload
     * @return {@link SubmitOrderResponse}
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "submitOrderRequest")
    @ResponsePayload
    public SubmitOrderResponse submitOrder(@RequestPayload SubmitOrderRequest submitOrderRequest) {
        LOGGER.trace("Application ID: {}", applicationId);
        LOGGER.info("*** StockOrderEndpoint.submitOrder() entry ...");

        validateSubmitOrderRequest(submitOrderRequest);

        submitOrderRequest.getOrder().setOrderDate(DateUtils.getCurrentXMLGregorianCalendar());

        if (!stockServiceOrderService.isSymbolInExchange(submitOrderRequest.getOrder().getTickerSymbol())) {
            LOGGER.error("*** Invalid Ticker Symbol {} ***", submitOrderRequest.getOrder().getTickerSymbol());
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.stocksymbolinexchange"));
        }

        LOGGER.debug("Request is valid: {}", submitOrderRequest.toString());




        SubmitOrderResponse submitOrderResponse;

        /*SubmitOrderResponse submitOrderResponse = new SubmitOrderResponse(); //TODO remove; testing only
        submitOrderResponse.setStatus(0);
        submitOrderResponse.setStatusDesc("SUCCESS 20170131");*/


        try {
            submitOrderResponse  = stockServiceOrderService.placeOrder(submitOrderRequest);

        } catch (SOAPClientException sce) {

            LOGGER.error("SoapClientException caught: {}", sce.getMessage());
            throw new SOAPClientException(sce.getMessage());

        } catch (SOAPServerException | WebServiceClientException sse) {

            LOGGER.error("SoapServerException caught: {}", sse.getMessage());
            throw new SOAPServerException(sse.getMessage());
        }

        if (submitOrderResponse == null) {
            LOGGER.debug("Returning null SubmitOrderResponse");
        } else {
            LOGGER.debug("Returning SubmitOrderResponse: {}", submitOrderResponse.toString());
        }

        return submitOrderResponse;
    }


    /* Private methods */

    private void validateHeader(final RequestHeader requestHeader) {
        LOGGER.info("*** StockOrderEndpoint.validateHeader()");

        if (requestHeader == null) {
            LOGGER.error("*** RequestHeader IS NULL!!! ***");
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.header.source"));
        }
        if (StringUtils.isEmpty(requestHeader.getSource())) {
            LOGGER.error("*** Invalid Header Source {} ***", requestHeader.getSource());
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.header.source"));
        }

        String parameter = applicationParameterSource.getParm(StringUtils.PARM_VALID_HEADER_SOURCE);

        if (!requestHeader.getSource().equals(parameter)) {
            LOGGER.error("*** Invalid Header Source {} ***", requestHeader.getSource());
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.header.source"));
        }
    }

    private void validateGetStockPriceRequest(final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.info("*** StockOrderEndpoint.validateGetStockPriceRequest()");

        if (getStockPriceRequest == null) {
            LOGGER.error("*** GetStockPriceRequest IS NULL!!! ***");
            throw new SOAPClientException(applicationMessageSource.getMessage("info.status.fail"));
        }

        validateHeader(getStockPriceRequest.getHead());

        if (getStockPriceRequest.getTickerSymbol().isEmpty()) {
            LOGGER.error("*** Invalid Ticker Symbol {} ***", getStockPriceRequest.getTickerSymbol());
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.stocksymbol"));
        }

        String tickerSymbol = Optional.of(getStockPriceRequest.getTickerSymbol())
                .filter(validStockSymbolPattern)
                .orElseThrow(() -> new SOAPClientException(
                        applicationMessageSource.getMessage("error.invalid.stocksymbol")));

        LOGGER.debug("tickerSymbol:{}", tickerSymbol);
    }

    private void validateSubmitOrderRequest(final SubmitOrderRequest submitOrderRequest) {
        LOGGER.info("*** StockOrderEndpoint.validateSubmitOrderRequest()");

        if (submitOrderRequest == null) {
            LOGGER.error("*** SubmitOrderRequest IS NULL!!! ***");
            throw new SOAPClientException(applicationMessageSource.getMessage("info.status.fail"));
        }

        validateHeader(submitOrderRequest.getHead());

        if (submitOrderRequest.getOrder() == null) {
            LOGGER.error("*** SubmitOrderRequest.Order IS NULL!!! ***");
            throw new SOAPClientException(applicationMessageSource.getMessage("info.status.fail"));
        }

        if ( StringUtils.isEmpty(submitOrderRequest.getOrder().getAction())
                || (! isValidAction(submitOrderRequest.getOrder().getAction())) ) {

            LOGGER.error("*** Invalid Order Action ***");
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.orderaction"));
        }

        if ( StringUtils.isEmpty(submitOrderRequest.getOrder().getOrderType())
                || (! isValidOrderType(submitOrderRequest.getOrder().getOrderType())) ) {

            LOGGER.error("*** Invalid Order Type ***");
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.ordertype"));
        }

        if (StringUtils.isEmpty(submitOrderRequest.getOrder().getTickerSymbol())) {
            LOGGER.error("*** Invalid Ticker Symbol {} ***", submitOrderRequest.getOrder().getTickerSymbol());
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.stocksymbol"));
        }

        if (submitOrderRequest.getOrder().getQuantity() == null
                || submitOrderRequest.getOrder().getQuantity() <= 0) {
            LOGGER.error("Invalid value for submitOrderRequest.getOrder().getQuantity()");
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.quantity"));
        }

        if (submitOrderRequest.getOrder().getStockPrice() <= 0.00) {
            LOGGER.error("Invalid value for submitOrderRequest.getOrder().getStockPrice()");
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.price"));
        }

        if (submitOrderRequest.getOrder().getOrderType().equalsIgnoreCase(EnumOrderTypes.LIMIT_ORDER.getOrderType())
                && (submitOrderRequest.getOrder().getStockPrice() <= 0.00)) {
            LOGGER.error("Invalid value for submitOrderRequest.getOrder().getStockPrice()");
            throw new SOAPClientException(applicationMessageSource.getMessage("error.invalid.price"));
        }

        String tickerSymbol = Optional.of(submitOrderRequest.getOrder().getTickerSymbol())
                .filter(validStockSymbolPattern)
                .orElseThrow(() -> new SOAPClientException(
                        applicationMessageSource.getMessage("error.invalid.stocksymbol")));

        LOGGER.debug("tickerSymbol:{}", tickerSymbol);
    }

    private boolean isValidAction(final String action) {
        for (EnumAction enumAction : EnumAction.values()) {
            if (action.equalsIgnoreCase(enumAction.getActionType())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidOrderType(final String orderType) {
        for (EnumOrderTypes enumOrderType : EnumOrderTypes.values()) {
            if (orderType.equalsIgnoreCase(enumOrderType.getOrderType())) {
                return true;
            }
        }
        return false;
    }
}
