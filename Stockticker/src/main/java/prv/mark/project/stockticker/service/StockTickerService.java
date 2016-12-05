package prv.mark.project.stockticker.service;

import prv.mark.project.xml.stocks.GetStockPriceRequest;
import prv.mark.project.xml.stocks.GetStockPriceResponse;
import prv.mark.project.xml.stocks.SubmitOrderRequest;
import prv.mark.project.xml.stocks.SubmitOrderResponse;

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
