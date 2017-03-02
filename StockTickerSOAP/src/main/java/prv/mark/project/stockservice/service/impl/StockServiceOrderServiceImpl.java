package prv.mark.project.stockservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import prv.mark.project.common.domain.EnumAction;
import prv.mark.project.common.domain.EnumOrderStatus;
import prv.mark.project.common.domain.EnumOrderTypes;
import prv.mark.project.common.domain.EnumStatusCodes;
import prv.mark.project.common.domain.EnumTransactionTypes;
import prv.mark.project.common.domain.TransactionDto;
import prv.mark.project.common.entity.StockOrderEntity;
import prv.mark.project.common.entity.StockPriceEntity;
import prv.mark.project.common.entity.StockSymbolEntity;
import prv.mark.project.common.entity.TransactionLogEntity;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.service.StockOrderEntityService;
import prv.mark.project.common.service.StockPriceEntityService;
import prv.mark.project.common.service.StockSymbolEntityService;
import prv.mark.project.common.service.TransactionLogEntityService;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockservice.jms.TransactionLoggerJmsClient;
import prv.mark.project.stockservice.schemas.GetStockPriceRequest;
import prv.mark.project.stockservice.schemas.GetStockPriceResponse;
import prv.mark.project.stockservice.schemas.RequestHeader;
import prv.mark.project.stockservice.schemas.StockQuote;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;
import prv.mark.project.stockservice.service.StockServiceOrderService;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service implementation of the {@link StockServiceOrderService} interface.
 *
 * @author mlglenn
 */
@Service
public class StockServiceOrderServiceImpl implements StockServiceOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceOrderServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private ApplicationParameterSource applicationParameterSource;
    @Autowired
    private StockSymbolEntityService stockSymbolEntityService;
    @Autowired
    private StockPriceEntityService stockPriceEntityService;
    @Autowired
    private StockOrderEntityService stockOrderEntityService;
    @Autowired
    private TransactionLogEntityService transactionLogEntityService;

    //Added with TlogProducerJmsConfig
    //@Autowired
    //private TranslogProducerJms translogProducerJms;
    //@Autowired
    //protected MessageChannel tlogJMSLogger;


    @Override
    public GetStockPriceResponse getStockPrice(
            final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockServiceOrderServiceImpl.getStockPrice() entry ...");
        logGetStockPriceRequest(getStockPriceRequest);

        TransactionLoggerJmsClient tlogClient =
                new TransactionLoggerJmsClient(setTransactionDto(getStockPriceRequest));
        tlogClient.sendMessage();












        //TODO place the transaction on the log queue
        /*TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setId(null);
        transactionLogEntity.setTransactionType(transactionDto.getTransactionType());
        transactionLogEntity.setLogDateTime(DateUtils.getDateFromLocalDateTime(transactionDto.getLogDateTime()));
        //transactionLogEntity.setLogDateTime(transactionDto.getLogDateTime());
        if (StringUtils.isNotEmpty(transactionDto.getTransactionDetail())) {
            transactionLogEntity.setTransactionData(transactionDto.getTransactionDetail());
        }
        saveTransactionLogEntity(transactionLogEntity);*/

        //translogProducerJms.sendMessage("TEST MESSAGE");




        Optional<StockPriceEntity> returnedEntity
                = stockPriceEntityService.findByStockSymbol(getStockPriceRequest.getTickerSymbol());

        GetStockPriceResponse getStockPriceResponse; // = new GetStockPriceResponse();

        if (returnedEntity != null && returnedEntity.isPresent()) {
            getStockPriceResponse = buildSuccessfulStockPriceResponse(returnedEntity.get());
        } else {
            getStockPriceResponse = buildFailureStockPriceResponse(getStockPriceRequest);
        }



        //TODO testing
        /*StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(1);
        stockQuote.setStatusText("success");
        stockQuote.setTickerSymbol("WMT");
        stockQuote.setStockPrice(NumberUtils.toFloat("68.00"));
        getStockPriceResponse.setQuote(stockQuote);*/



        logGetStockPriceResponse(getStockPriceResponse);
        return getStockPriceResponse;
    }

    @Override
    public List<GetStockPriceResponse> getAll() {
        LOGGER.debug("*** StockServiceOrderServiceImpl.getAll() entry ...");
        //logGetStockPriceRequest(getStockPriceRequest);
        String trxDetail = "GET ALL STOCK QUOTES";
        LOGGER.debug(trxDetail);
        TransactionDto transactionDto = setTransactionDto(trxDetail);




        //TODO place the tranaction on the log queue
        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setId(null);
        transactionLogEntity.setTransactionType(EnumTransactionTypes.STOCK_PRICE_INQUIRY.getTransactionTypeDesc());
        transactionLogEntity.setLogDateTime(DateUtils.getDateFromLocalDateTime(transactionDto.getLogDateTime()));
        if (StringUtils.isNotEmpty(transactionDto.getTransactionDetail())) {
            transactionLogEntity.setTransactionData(transactionDto.getTransactionDetail());
        }
        saveTransactionLogEntity(transactionLogEntity);





        //List<StockPriceEntity> returnedEntityList = stockPriceEntityService.findAll(); TODO
        List<GetStockPriceResponse> getStockPriceResponseList = new ArrayList<>();
        //if (returnedEntityList != null && returnedEntityList.size() > 0) {
        //    for (StockPriceEntity stockPrice : returnedEntityList) {
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
        LOGGER.debug("*** StockServiceOrderServiceImpl.placeOrder() entry ...");
        logSubmitOrderRequest(submitOrderRequest);

        TransactionDto transactionDto = setTransactionDto(submitOrderRequest);




        //TODO place the tranaction on the log queue (TransactionLogger)
        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setId(null);
        transactionLogEntity.setTransactionType(transactionDto.getTransactionType());
        transactionLogEntity.setLogDateTime(DateUtils.getDateFromLocalDateTime(transactionDto.getLogDateTime()));
        if (StringUtils.isNotEmpty(transactionDto.getTransactionDetail())) {
            transactionLogEntity.setTransactionData(transactionDto.getTransactionDetail());
        }
        saveTransactionLogEntity(transactionLogEntity);




        StockOrderEntity stockOrder = new StockOrderEntity();
        stockOrder.setOrderStatus(EnumOrderStatus.PENDING.getStatusDesc());
        stockOrder.setOrderDate(DateUtils.xmlGregorianCalendarToDate(submitOrderRequest.getOrder().getOrderDate()));
        stockOrder.setAction(submitOrderRequest.getOrder().getAction());
        stockOrder.setQuantity(submitOrderRequest.getOrder().getQuantity().longValue());
        stockOrder.setStockSymbol(submitOrderRequest.getOrder().getTickerSymbol());
        stockOrder.setOrderType(submitOrderRequest.getOrder().getOrderType());

        if (submitOrderRequest.getOrder().getOrderType().equals(EnumOrderTypes.MARKET_ORDER.getOrderType())) {
            Optional<StockPriceEntity> returnedEntity
                    = stockPriceEntityService.findByStockSymbol(submitOrderRequest.getOrder().getTickerSymbol());
            if (returnedEntity.isPresent()) {
                stockOrder.setPrice(returnedEntity.get().getCurrentPrice());
            } else {
                //TODO we have a problem
            }
        }

        StockOrderEntity returnedEntity = saveStockOrderEntity(stockOrder);

        SubmitOrderResponse submitOrderResponse = new SubmitOrderResponse();

        if (returnedEntity != null) {
            submitOrderResponse = buildSuccessfulStockOrderResponse(submitOrderRequest, returnedEntity);
        } else {
            submitOrderResponse = buildFailureStockOrderResponse(submitOrderRequest, returnedEntity);
        }

        logSubmitOrderResponse(submitOrderResponse);
        return submitOrderResponse;
    }

    public boolean isSymbolInExchange(final String symbol) {
        Optional<StockSymbolEntity> returnedEntity = stockSymbolEntityService.findByTickerSymbol(symbol);
        if (returnedEntity.isPresent() && returnedEntity.get().getTickerSymbol().equals(symbol)) {
           return true;
        } else {
           return false;
        }
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
        if (submitOrderRequest.getOrder().getAction().equalsIgnoreCase(EnumAction.BUY.getActionType())) {
            transactionDto.setTransactionType(EnumTransactionTypes.STOCK_PURCHASE.getTransactionTypeDesc());
        } else {
            transactionDto.setTransactionType(EnumTransactionTypes.STOCK_SALE.getTransactionTypeDesc());
        }
        transactionDto.setTransactionDetail(
                submitOrderRequest.getHead().getSource() + ","
                        + submitOrderRequest.getOrder().getAction() + ","
                        + submitOrderRequest.getOrder().getQuantity() + ","
                        + submitOrderRequest.getOrder().getTickerSymbol() + ","
                        + submitOrderRequest.getOrder().getStockPrice() + ","
                        + submitOrderRequest.getOrder().getOrderType() + ","
                        + submitOrderRequest.getOrder().getOrderDate());
        return transactionDto;
    }

    private GetStockPriceResponse buildSuccessfulStockPriceResponse(
            final StockPriceEntity returnedEntity) {

        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode()); //success
        stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_SUCCESSFUL));

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
        stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_FAILED));

        if (getStockPriceRequest != null) {
            stockQuote.setTickerSymbol(getStockPriceRequest.getTickerSymbol());
        }
        response.setQuote(stockQuote);
        return response;
    }

    private SubmitOrderResponse buildSuccessfulStockOrderResponse(
            final SubmitOrderRequest submitOrderRequest,
            final StockOrderEntity returnedEntity) {

        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(EnumStatusCodes.SUCCESS.getStatudCode());
        response.setStatusDesc("Order Filled: " + submitOrderRequest.getOrder().getAction()
                + " " + submitOrderRequest.getOrder().getQuantity() + " shares at "
                + returnedEntity.getPrice() + " - " + submitOrderRequest.getOrder().getOrderType());
        return response;
    }

    private SubmitOrderResponse buildFailureStockOrderResponse(
            final SubmitOrderRequest submitOrderRequest,
            final StockOrderEntity returnedEntity) {

        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(EnumStatusCodes.REQUEST_FAILED.getStatudCode());
        response.setStatusDesc("Order FAILED: " + submitOrderRequest.getOrder().getAction()
                + " " + submitOrderRequest.getOrder().getQuantity() + " shares at "
                + returnedEntity.getPrice() + " - " + submitOrderRequest.getOrder().getOrderType());
        return response;
    }

    private StockOrderEntity saveStockOrderEntity(final StockOrderEntity entity) {

        LOGGER.debug("StockServiceOrderServiceImpl.saveStockOrderEntity()");
        StockOrderEntity returnEntity = new StockOrderEntity();
        try {

            returnEntity = stockOrderEntityService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving StockOrderEntity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved StockOrderEntity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

    private TransactionLogEntity saveTransactionLogEntity(final TransactionLogEntity entity) {

        LOGGER.debug("StockServiceOrderServiceImpl.saveTransactionLogEntity()");
        TransactionLogEntity returnEntity = new TransactionLogEntity();
        try {
            returnEntity = transactionLogEntityService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionLogEntity entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionLogEntity entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

    private void logSubmitOrderRequest(final SubmitOrderRequest submitOrderRequest) {
        LOGGER.debug("*** StockServiceOrderServiceImpl.logSubmitOrderRequest() entry ...");
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
        LOGGER.debug("*** StockServiceOrderServiceImpl.logGetStockPriceRequest() entry ...");
        logRequestHeader(getStockPriceRequest.getHead());
        LOGGER.debug("********** REQUEST DATA **********");
        LOGGER.debug("Ticker symbol: {}", getStockPriceRequest.getTickerSymbol());
    }

    private void logRequestHeader(final RequestHeader requestHeader) {
        LOGGER.debug("********** REQUEST HEADER **********");
        LOGGER.debug("source: {}", requestHeader.getSource());
    }

    private void logGetStockPriceResponse(final GetStockPriceResponse getStockPriceResponse) {
        LOGGER.debug("*** StockServiceOrderServiceImpl.logGetStockPriceResponse() entry ...");
        LOGGER.debug("********** RETURNING STOCK PRICE RESPONSE **********");
        LOGGER.debug("Status Code:{}", getStockPriceResponse.getQuote().getStatusCode());
        LOGGER.debug("Status Text:{}", getStockPriceResponse.getQuote().getStatusText());
        LOGGER.debug("Stock Price:{}", NumberUtils.myToBigDecimal(getStockPriceResponse.getQuote().getStockPrice()));
        LOGGER.debug("Ticker Symbol:{}", getStockPriceResponse.getQuote().getTickerSymbol());
    }

    private void logSubmitOrderResponse(final SubmitOrderResponse submitOrderResponse) {
        LOGGER.debug("*** StockServiceOrderServiceImpl.logSubmitOrderResponse() entry ...");
        LOGGER.debug("********** RETURNING SUBMIT ORDER RESPONSE **********");
        LOGGER.debug("Status Code:{}", submitOrderResponse.getStatus());
        LOGGER.debug("Status Description:{}", submitOrderResponse.getStatusDesc());
    }
}
