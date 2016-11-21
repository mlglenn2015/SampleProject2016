package prv.mark.project.stockticker.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prv.mark.project.common.domain.json.AbstractJsonResponse;
import prv.mark.project.common.domain.json.StockPriceRequest;
import prv.mark.project.common.domain.json.StockPriceResponse;

/**
 * REST Controller for Stock Ticker application.
 */
@RestController
@RequestMapping("/stockrest")
public class StockTickerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerRestController.class);


    /**
     * Request handler for /stockrest/bysymbol requests
     * @param stockSymbol The input ticker symbol
     * @return {@link StockPriceResponse} containing the stock price
     */
    @RequestMapping(value="/bysymbol", method=RequestMethod.GET, params="stockSymbol")
    public StockPriceResponse getStockPriceBySymbol(final @RequestParam("stockSymbol") String stockSymbol) {
        LOGGER.debug("StockTickerRestController.getStockPriceBySymbol({})", stockSymbol);
        StockPriceResponse stockPriceResponse = new StockPriceResponse();

        //TODO query the database and return the stock price if found

        return stockPriceResponse;
    }

    /**
     * Request handler for /stockrest/save requests
     * @param stockPriceRequest The input {@link StockPriceRequest} object
     * @return {@link StockPriceResponse} containing the stock price
     */
    @RequestMapping(value="/save", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public StockPriceResponse saveStockPriceBySymbol(@RequestBody final StockPriceRequest stockPriceRequest) {
        LOGGER.debug("StockTickerRestController.saveStockPriceBySymbol({})", stockPriceRequest.getStockSymbol());
        StockPriceResponse stockPriceResponse = new StockPriceResponse();
        stockPriceResponse.setRespStatus(AbstractJsonResponse.RespStatus.FAIL);

        //TODO save

        return stockPriceResponse;
    }
}
