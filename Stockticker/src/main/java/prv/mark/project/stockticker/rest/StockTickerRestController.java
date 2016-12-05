package prv.mark.project.stockticker.rest;

//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.WebServiceClientException;
import prv.mark.project.common.domain.json.AbstractJsonResponse;
import prv.mark.project.common.domain.json.StockPriceResponse;
import prv.mark.project.common.exception.SOAPClientException;
import prv.mark.project.common.exception.SOAPGeneralFault;
import prv.mark.project.common.exception.SOAPServerException;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockticker.service.StockTickerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * REST Controller for Stock Ticker application.
 */
@RestController
@RequestMapping("/stockrest")
public class StockTickerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerRestController.class);

    @Autowired
    private StockTickerService stockTickerService;
    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    private Predicate<String> validStockSymbolPattern = i -> {
        return Pattern.matches("[A-Z0-9]{1,12}", i);
    };


    /**
     * Request handler for /stockrest/bysymbol requests (http://localhost:13001/Stockticker/stockrest/bysymbol)
     * @param stockSymbol The input ticker symbol
     * @return {@link StockPriceResponse} containing the stock price
     */
    @RequestMapping(value="/bysymbol", method=RequestMethod.GET, params="stockSymbol")
    public StockPriceResponse getStockPriceBySymbol(final @RequestParam("stockSymbol") String stockSymbol) {
        LOGGER.debug("StockTickerRestController.getStockPriceBySymbol({})", stockSymbol);

        validateStockPriceRequest(stockSymbol);
        LOGGER.debug("Request is valid: {}", stockSymbol);

        GetStockPriceRequest getStockPriceRequest =new GetStockPriceRequest();
        getStockPriceRequest.setTickerSymbol(stockSymbol);
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

        StockPriceResponse stockPriceResponse = new StockPriceResponse();
        if (getStockPriceResponse == null) {
            stockPriceResponse.setRespStatus(AbstractJsonResponse.RespStatus.FAIL);
        } else {
            stockPriceResponse.setStockSymbol(getStockPriceResponse.getQuote().getTickerSymbol());
            stockPriceResponse.setStockPrice(NumberUtils.toBigDecimal(getStockPriceResponse.getQuote().getStockPrice()));
            stockPriceResponse.setRespStatus(AbstractJsonResponse.RespStatus.OK);
            //stockPriceResponse.setResult();
        }

        LOGGER.debug("Returning response: {}", stockPriceResponse.toString());
        return stockPriceResponse;
    }

    /**
     * Request handler for /stockrest/getall requests (http://localhost:13001/Stockticker/stockrest/bysymbol)
     * @return {@link StockPriceResponse} containing the stock price
     */
    @RequestMapping(value="/getall", method=RequestMethod.GET)
    public StockPriceResponse getAllStockPrices() {
        LOGGER.debug("StockTickerRestController.getAllStockPrices()");

        List<GetStockPriceResponse> stockPriceResponseList = new ArrayList<>();
        try {
            stockPriceResponseList  = stockTickerService.getAll();

        } catch (SOAPClientException sce) {

            LOGGER.error("SoapClientException caught: {}", sce.getMessage());
            throw new SOAPClientException(sce.getMessage());

        } catch (SOAPServerException | WebServiceClientException sse) {

            LOGGER.error("SoapServerException caught: {}", sse.getMessage());
            throw new SOAPServerException(sse.getMessage());
        }

        StockPriceResponse stockPriceResponse = new StockPriceResponse();
        //if (getStockPriceResponse == null) { TODO
            stockPriceResponse.setRespStatus(AbstractJsonResponse.RespStatus.FAIL);
        //} else {
        //    stockPriceResponse.setStockSymbol(getStockPriceResponse.getOrder().getTickerSymbol());
        //    stockPriceResponse.setStockPrice(NumberUtils.toBigDecimal(getStockPriceResponse.getOrder().getStockPrice()));
        //    stockPriceResponse.setRespStatus(AbstractJsonResponse.RespStatus.OK);
            //stockPriceResponse.setResult();
        //}

        LOGGER.debug("Returning response: {}", stockPriceResponse.toString());
        return stockPriceResponse;
    }

    /* *
     * Request handler for /stockrest/save requests
     * @param stockPriceRequest The input {@link StockPriceRequest} object
     * @return {@link StockPriceResponse} containing the stock price
     */
    /*@RequestMapping(value="/save", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public StockPriceResponse saveStockPriceBySymbol(@RequestBody final StockPriceRequest stockPriceRequest) {
        LOGGER.debug("StockTickerRestController.saveStockPriceBySymbol({})", stockPriceRequest.getStockSymbol());
        StockPriceResponse stockPriceResponse = new StockPriceResponse();
        stockPriceResponse.setRespStatus(AbstractJsonResponse.RespStatus.FAIL);

        //TODO save for future

        return stockPriceResponse;
    }*/


    private void validateStockPriceRequest(final String stockSymbol) {
        LOGGER.debug("*** StockTickerRestController.validateStockPriceRequest()");
        if (StringUtils.isEmpty(stockSymbol)) {
            String message = "*** Stock Symbol IS Empty!!! ***";
            LOGGER.error(message);
            throw new SOAPClientException(message);
        }

        String message = "*** Stock Symbol " + stockSymbol + " is invalid ***";
        String tickerSymbol = Optional.of(stockSymbol)
                .filter(validStockSymbolPattern)
                .orElseThrow(() -> new SOAPGeneralFault(message));
        LOGGER.debug("tickerSymbol:{}", tickerSymbol);
    }
}
