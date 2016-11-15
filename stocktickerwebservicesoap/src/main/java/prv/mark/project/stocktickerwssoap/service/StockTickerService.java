package prv.mark.project.stocktickerwssoap.service;

import prv.mark.xml.stocks.GetStockPriceRequest;
import prv.mark.xml.stocks.GetStockPriceResponse;

/**
 * Web Service contract for the stock ticker application.
 *
 * WSDL http://localhost:12001/TODO/StockTicker.wsdl
 *
 * @author mlglenn
 */
public interface StockTickerService {

    //SubmitOrderResponse submitOrder(SubmitOrderRequest submitOrderRequest);

    GetStockPriceResponse getStockPrice(GetStockPriceRequest getStockPriceRequest);
}
