package prv.mark.project.stockticker.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.domain.EnumTransactionTypes;
import prv.mark.project.common.domain.TransactionDto;
import prv.mark.project.common.entity.StockOrder;
import prv.mark.project.common.entity.StockPrice;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.service.StockOrderService;
import prv.mark.project.common.service.StockPriceService;
import prv.mark.project.common.service.TransactionLogService;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stocks.commontypes.schemas.RequestHeader;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceResponse;
import prv.mark.project.stocks.stocktickertypes.schemas.StockQuote;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse;
import prv.mark.project.stockticker.service.StockTickerService;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//import prv.mark.project.xml.stocks.*;
//import prv.mark.xml.stocks.*;

/**
 * Service implementation of the {@link StockTickerService} interface.
 *
 * @author mlglenn
 */
@Service
public class StockTickerServiceImpl implements StockTickerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    //@Autowired
    //private ApplicationParameterSource applicationParameterSource; TODO
    /*@Autowired
    private StockPriceService stockPriceService;
    @Autowired
    private StockOrderService stockOrderService; TODO
    @Autowired
    private TransactionLogService transactionLogService;*/


    @Override
    public GetStockPriceResponse getStockPrice(
            final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerServiceImpl.getStockPrice() entry ...");
        logGetStockPriceRequest(getStockPriceRequest);

        TransactionDto transactionDto = setTransactionDto(getStockPriceRequest);




        //TODO place the tranaction on the log queue
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setId(null);
        transactionLog.setTransactionType(transactionDto.getTransactionType());
        transactionLog.setLogDateTime(DateUtils.getDateFromLocalDateTime(transactionDto.getLogDateTime()));
        //transactionLog.setLogDateTime(transactionDto.getLogDateTime());
        if (StringUtils.isNotEmpty(transactionDto.getTransactionDetail())) {
            transactionLog.setTransactionData(transactionDto.getTransactionDetail());
        }
        saveTransactionLogEntity(transactionLog);





        //Optional<StockPrice> returnedEntity TODO
        //        = stockPriceService.findByStockSymbol(getStockPriceRequest.getTickerSymbol());

        GetStockPriceResponse getStockPriceResponse
                = new GetStockPriceResponse();

        //if (returnedEntity != null && returnedEntity.get() != null) { TODO
        //    getStockPriceResponse = buildSuccessfulStockPriceResponse(returnedEntity.get());
        //} else {
            getStockPriceResponse = buildFailureStockPriceResponse(getStockPriceRequest);
        //}

        //TODO testing
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(1);
        stockQuote.setStatusText("success");
        stockQuote.setTickerSymbol("WMT");
        stockQuote.setStockPrice(NumberUtils.toFloat("68.00"));
        getStockPriceResponse.setQuote(stockQuote);






        logGetStockPriceResponse(getStockPriceResponse);
        return getStockPriceResponse;
    }

    @Override
    public List<GetStockPriceResponse> getAll() {
        LOGGER.debug("*** StockTickerServiceImpl.getAll() entry ...");
        //logGetStockPriceRequest(getStockPriceRequest);
        String trxDetail = "GET ALL STOCK QUOTES";
        LOGGER.debug(trxDetail);
        TransactionDto transactionDto = setTransactionDto(trxDetail);




        //TODO place the tranaction on the log queue
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setId(null);
        transactionLog.setTransactionType(EnumTransactionTypes.STOCK_PRICE_INQUIRY.getTransactionTypeDesc());
        transactionLog.setLogDateTime(DateUtils.getDateFromLocalDateTime(transactionDto.getLogDateTime()));
        if (StringUtils.isNotEmpty(transactionDto.getTransactionDetail())) {
            transactionLog.setTransactionData(transactionDto.getTransactionDetail());
        }
        saveTransactionLogEntity(transactionLog);





        //List<StockPrice> returnedEntityList = stockPriceService.findAll(); TODO
        List<GetStockPriceResponse> getStockPriceResponseList = new ArrayList<>();
        //if (returnedEntityList != null && returnedEntityList.size() > 0) {
        //    for (StockPrice stockPrice : returnedEntityList) {
        //        GetStockPriceResponse getStockPriceResponse = buildSuccessfulStockPriceResponse(stockPrice);
        //        getStockPriceResponseList.add(getStockPriceResponse);
        //        logGetStockPriceResponse(getStockPriceResponse);
        //    }
        //} else {
            GetStockPriceResponse getStockPriceResponse = buildFailureStockPriceResponse(null);
            getStockPriceResponseList.add(getStockPriceResponse);
            logGetStockPriceResponse(getStockPriceResponse);
        //}

        return getStockPriceResponseList;
    }

    @Override
    public SubmitOrderResponse placeOrder(
            SubmitOrderRequest submitOrderRequest) {
        LOGGER.debug("*** StockTickerServiceImpl.placeOrder() entry ...");
        logSubmitOrderRequest(submitOrderRequest);

        TransactionDto transactionDto = setTransactionDto(submitOrderRequest);




        //TODO place the tranaction on the log queue (TransactionLogger)
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setId(null);
        transactionLog.setTransactionType(transactionDto.getTransactionType());
        transactionLog.setLogDateTime(DateUtils.getDateFromLocalDateTime(transactionDto.getLogDateTime()));
        //transactionLog.setLogDateTime(transactionDto.getLogDateTime());
        if (StringUtils.isNotEmpty(transactionDto.getTransactionDetail())) {
            transactionLog.setTransactionData(transactionDto.getTransactionDetail());
        }
        saveTransactionLogEntity(transactionLog);



        StockOrder stockOrder = new StockOrder();
        stockOrder.setId(null);
        stockOrder.setAction(submitOrderRequest.getOrder().getAction());
        stockOrder.setStockSymbol(submitOrderRequest.getOrder().getTickerSymbol());
        stockOrder.setQuantity(submitOrderRequest.getOrder().getQuantity().longValue());
        //Optional<StockPrice> stockPriceEntity TODO
        //        = stockPriceService.findByStockSymbol(submitOrderRequest.getOrder().getTickerSymbol());
        //stockOrder.setPrice(stockPriceEntity.get().getCurrentPrice());
        stockOrder.setOrderType(submitOrderRequest.getOrder().getOrderType());
        stockOrder.setOrderDate(DateUtils.getDateFromLocalDateTime());

        StockOrder returnedEntity = saveStockOrderEntity(stockOrder);

        SubmitOrderResponse submitOrderResponse = new SubmitOrderResponse();

        if (returnedEntity != null) {
            submitOrderResponse = buildSuccessfulStockOrderResponse(submitOrderRequest, returnedEntity);
        } else {
            submitOrderResponse = buildFailureStockOrderResponse(submitOrderRequest, returnedEntity);
        }

        logSubmitOrderResponse(submitOrderResponse);
        return submitOrderResponse;
    }



    /* Private methods */

    private TransactionDto setTransactionDto(final String detailString) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setLogDateTime(DateUtils.getLocalDateTime());
        transactionDto.setTransactionType(EnumTransactionTypes.STOCK_PRICE_INQUIRY.getTransactionTypeDesc());
        transactionDto.setTransactionDetail(detailString);
        return transactionDto;
    }

    private TransactionDto setTransactionDto(final GetStockPriceRequest getStockPriceRequest) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setLogDateTime(DateUtils.getLocalDateTime());
        transactionDto.setTransactionType(EnumTransactionTypes.STOCK_PRICE_INQUIRY.getTransactionTypeDesc());
        transactionDto.setTransactionDetail(
                getStockPriceRequest.getHead().getSource() + "," + getStockPriceRequest.getTickerSymbol());
        return transactionDto;
    }

    private TransactionDto setTransactionDto(final SubmitOrderRequest submitOrderRequest) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setLogDateTime(DateUtils.getLocalDateTime());
        if (submitOrderRequest.getOrder().getAction().equalsIgnoreCase("BUY")) {
            transactionDto.setTransactionType(EnumTransactionTypes.STOCK_PURCHASE.getTransactionTypeDesc());
        } else {
            transactionDto.setTransactionType(EnumTransactionTypes.STOCK_SALE.getTransactionTypeDesc());
        }
        transactionDto.setTransactionDetail(
                submitOrderRequest.getHead().getSource() + "," + submitOrderRequest.getOrder().getAction() + ","
                + submitOrderRequest.getOrder().getQuantity() + ","
                + submitOrderRequest.getOrder().getTickerSymbol() + ","
                + submitOrderRequest.getOrder().getStockPrice() + ","
                + submitOrderRequest.getOrder().getOrderType() + ","
                + submitOrderRequest.getOrder().getOrderDate());
        return transactionDto;
    }

    private GetStockPriceResponse buildSuccessfulStockPriceResponse(
            final StockPrice returnedEntity) {

        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode()); //success
        //stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_SUCCESSFUL)); TODO
        stockQuote.setStatusText("Request Successful");
        if (returnedEntity != null) {
            stockQuote.setTickerSymbol(returnedEntity.getStockSymbol());
            stockQuote.setStockPrice(returnedEntity.getCurrentPrice().floatValue());
        }
        response.setQuote(stockQuote);
        return response;
    }

    private GetStockPriceResponse buildFailureStockPriceResponse(
            final GetStockPriceRequest getStockPriceRequest) {

        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(EnumStatusCodes.REQUEST_FAILED.getStatudCode()); //failure
        //stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_FAILED)); TODO
        stockQuote.setStatusText("Request Failed");
        if (getStockPriceRequest != null) {
            stockQuote.setTickerSymbol(getStockPriceRequest.getTickerSymbol());
        }
        response.setQuote(stockQuote);
        return response;
    }

    private SubmitOrderResponse buildSuccessfulStockOrderResponse(
            final SubmitOrderRequest submitOrderRequest,
            final StockOrder returnedEntity) {

        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(EnumStatusCodes.SUCCESS.getStatudCode());
        response.setStatusDesc("Order Filled: " + submitOrderRequest.getOrder().getAction()
                + " " + submitOrderRequest.getOrder().getQuantity() + " shares at "
                + returnedEntity.getPrice() + " - " + submitOrderRequest.getOrder().getOrderType());
        return response;
    }

    private SubmitOrderResponse buildFailureStockOrderResponse(
            final SubmitOrderRequest submitOrderRequest,
            final StockOrder returnedEntity) {

        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(EnumStatusCodes.REQUEST_FAILED.getStatudCode());
        response.setStatusDesc("Order FAILED: " + submitOrderRequest.getOrder().getAction()
                + " " + submitOrderRequest.getOrder().getQuantity() + " shares at "
                + returnedEntity.getPrice() + " - " + submitOrderRequest.getOrder().getOrderType());
        return response;
    }

    private StockOrder saveStockOrderEntity(final StockOrder entity) {

        LOGGER.debug("StockTickerServiceImpl.saveStockOrderEntity()");
        StockOrder returnEntity = new StockOrder();
        try {

            //returnEntity = stockOrderService.save(entity); TODO
            returnEntity = entity;

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockOrder entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved StockOrder entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

    private TransactionLog saveTransactionLogEntity(final TransactionLog entity) {

        LOGGER.debug("StockTickerServiceImpl.saveTransactionLogEntity()");
        TransactionLog returnEntity = new TransactionLog();
        try {
            //returnEntity = transactionLogService.save(entity); TODO
            returnEntity = entity;

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionLog entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionLog entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

    private void logSubmitOrderRequest(final SubmitOrderRequest submitOrderRequest) {
        LOGGER.debug("*** StockTickerServiceImpl.logSubmitOrderRequest() entry ...");
        logRequestHeader(submitOrderRequest.getHead());
        LOGGER.debug("********** ORDER DATA **********");
        LOGGER.debug("action: {}", submitOrderRequest.getOrder().getAction());
        LOGGER.debug("quantity: {}", submitOrderRequest.getOrder().getQuantity());
        LOGGER.debug("ticker symbol: {}", submitOrderRequest.getOrder().getTickerSymbol());
        LOGGER.debug("price: {}", submitOrderRequest.getOrder().getStockPrice());
        LOGGER.debug("order type: {}", submitOrderRequest.getOrder().getOrderType());
        LOGGER.debug("order date: {}", submitOrderRequest.getOrder().getOrderDate());
    }

    private void logGetStockPriceRequest(final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerServiceImpl.logGetStockPriceRequest() entry ...");
        logRequestHeader(getStockPriceRequest.getHead());
        LOGGER.debug("********** REQUEST DATA **********");
        LOGGER.debug("Ticker symbol: {}", getStockPriceRequest.getTickerSymbol());
    }

    private void logRequestHeader(final RequestHeader requestHeader) {
        LOGGER.debug("********** REQUEST HEADER **********");
        LOGGER.debug("source: {}", requestHeader.getSource());
    }

    private void logGetStockPriceResponse(final GetStockPriceResponse getStockPriceResponse) {
        LOGGER.debug("*** StockTickerServiceImpl.logGetStockPriceResponse() entry ...");
        LOGGER.debug("********** RETURNING STOCK PRICE RESPONSE **********");
        LOGGER.debug("Status Code:{}", getStockPriceResponse.getQuote().getStatusCode());
        LOGGER.debug("Status Text:{}", getStockPriceResponse.getQuote().getStatusText());
        LOGGER.debug("Stock Price:{}", NumberUtils.myToBigDecimal(getStockPriceResponse.getQuote().getStockPrice()));
        LOGGER.debug("Ticker Symbol:{}", getStockPriceResponse.getQuote().getTickerSymbol());
    }

    private void logSubmitOrderResponse(final SubmitOrderResponse submitOrderResponse) {
        LOGGER.debug("*** StockTickerServiceImpl.logSubmitOrderResponse() entry ...");
        LOGGER.debug("********** RETURNING SUBMIT ORDER RESPONSE **********");
        LOGGER.debug("Status Code:{}", submitOrderResponse.getStatus());
        LOGGER.debug("Status Description:{}", submitOrderResponse.getStatusDesc());
    }

} 
