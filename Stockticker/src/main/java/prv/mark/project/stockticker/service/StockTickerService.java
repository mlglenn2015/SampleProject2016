package prv.mark.project.stockticker.service;

import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceResponse;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse;

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
