package prv.mark.project.stockticker.service;

import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;

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
