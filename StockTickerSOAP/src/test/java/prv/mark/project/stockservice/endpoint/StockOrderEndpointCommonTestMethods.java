package prv.mark.project.stockservice.endpoint;

import prv.mark.project.common.domain.EnumAction;
import prv.mark.project.common.domain.EnumOrderTypes;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.RequestHeader;
import prv.mark.project.stockservice.schemas.StockOrder;
import prv.mark.project.stockservice.schemas.StockQuote;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;

/**
 * Common methods for the endpoint tests.
 *
 * Created by Owner on 2/14/2017.
 */
public class StockOrderEndpointCommonTestMethods {

    protected static SubmitOrderResponse buildSubmitOrderResponse(final String symbol) {
        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(EnumStatusCodes.SUCCESS.getStatudCode());
        response.setStatusDesc("REQUEST SUCCESSFUL");
        return response;
    }

    protected static SubmitOrderRequest buildSubmitOrderRequest(final String symbol) {
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setHead(buildRequestHeader());
        request.setOrder(buildStockOrder(symbol));
        return request;
    }

    protected static StockOrder buildStockOrder(final String symbol) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setOrderDate(DateUtils.getCurrentXMLGregorianCalendar());
        stockOrder.setAction(EnumAction.BUY.getActionType());
        stockOrder.setQuantity(1);
        stockOrder.setTickerSymbol(symbol);
        stockOrder.setOrderType(EnumOrderTypes.MARKET_ORDER.getOrderType());
        stockOrder.setStockPrice(NumberUtils.toFloat("65.00"));
        return stockOrder;
    }

    protected static StockOrder buildStockOrderNullPrice(final String symbol) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.setOrderDate(DateUtils.getCurrentXMLGregorianCalendar());
        stockOrder.setAction(EnumAction.BUY.getActionType());
        stockOrder.setQuantity(1);
        stockOrder.setTickerSymbol(symbol);
        stockOrder.setOrderType(EnumOrderTypes.MARKET_ORDER.getOrderType());

        return stockOrder;
    }

    protected static SubmitOrderRequest buildSubmitOrderRequestNullOrder(final String symbol) {
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setHead(buildRequestHeader());

        return request;
    }

    protected static GetStockPriceResponse buildGetStockPriceResponse(final String symbol) {
        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote quote = new StockQuote();
        quote.setTickerSymbol(symbol);
        quote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode());
        quote.setStatusText("REQUEST SUCCESSFUL");
        quote.setStockPrice(NumberUtils.toFloat("68.00"));
        response.setQuote(quote);
        return response;
    }

    protected static GetStockPriceRequest buildGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildRequestHeader());
        request.setTickerSymbol(symbol);
        return request;
    }

    protected static GetStockPriceRequest buildInvalidGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildInvalidRequestHeader());
        request.setTickerSymbol(symbol);
        return request;
    }

    protected static GetStockPriceRequest buildEmptyGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();
        request.setHead(buildEmptyRequestHeader());
        request.setTickerSymbol(symbol);
        return request;
    }

    protected static GetStockPriceRequest buildNullGetStockPriceRequest(final String symbol) {
        GetStockPriceRequest request = new GetStockPriceRequest();

        request.setTickerSymbol(symbol);
        return request;
    }

    protected static RequestHeader buildRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("STOCKTICKER_20170131");
        return header;
    }

    protected static RequestHeader buildInvalidRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("INVALID");
        return header;
    }

    protected static RequestHeader buildEmptyRequestHeader() {
        RequestHeader header = new RequestHeader();
        header.setSource("");
        return header;
    }
}
