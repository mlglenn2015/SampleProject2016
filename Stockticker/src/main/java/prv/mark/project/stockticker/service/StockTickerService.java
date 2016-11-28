package prv.mark.project.stockticker.service;

import prv.mark.xml.stocks.GetStockPriceRequest;
import prv.mark.xml.stocks.GetStockPriceResponse;
import prv.mark.xml.stocks.SubmitOrderRequest;
import prv.mark.xml.stocks.SubmitOrderResponse;

import java.util.List;

/**
 * Web Service contract for the stock ticker application.
 *
 * @author mlglenn
 */
public interface StockTickerService {

    SubmitOrderResponse placeOrder(SubmitOrderRequest submitOrderRequest);

    GetStockPriceResponse getStockPrice(GetStockPriceRequest getStockPriceRequest);

    List<GetStockPriceResponse> getAll();
}
