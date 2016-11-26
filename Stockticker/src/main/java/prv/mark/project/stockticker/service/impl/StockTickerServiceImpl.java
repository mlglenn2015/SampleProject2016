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
import prv.mark.project.common.entity.StockPrice;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.service.StockPriceService;
import prv.mark.project.common.service.TransactionLogService;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockticker.service.StockTickerService;
import prv.mark.xml.stocks.GetStockPriceRequest;
import prv.mark.xml.stocks.GetStockPriceResponse;
import prv.mark.xml.stocks.RequestHeader;
import prv.mark.xml.stocks.StockQuote;

import javax.persistence.PersistenceException;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Autowired
    private ApplicationParameterSource applicationParameterSource;
    @Autowired
    private StockPriceService stockPriceService;
    @Autowired
    private TransactionLogService transactionLogService;


    @Override
    public GetStockPriceResponse getStockPrice(final GetStockPriceRequest getStockPriceRequest) {
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





        Optional<StockPrice> returnedEntity = stockPriceService.findByStockSymbol(getStockPriceRequest.getTickerSymbol());

        GetStockPriceResponse getStockPriceResponse = new GetStockPriceResponse();
        if (returnedEntity != null && returnedEntity.get() != null) {
            getStockPriceResponse = buildSuccessfulStockPriceResponse(getStockPriceRequest, returnedEntity.get());
        } else {
            getStockPriceResponse = buildFailureStockPriceResponse(getStockPriceRequest, returnedEntity.get());
        }

        //TODO testing
        /*StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(1);
        stockQuote.setStatusText("success");
        stockQuote.setTickerSymbol("WMT");
        stockQuote.setStockPrice(NumberUtils.toFloat("68.00"));
        getStockPriceResponse.setOrder(stockQuote);*/





        //TODO Save the order to the in-memory database
        //for (Order order : submitOrderRequest.getOrders().getOrder()) {
        //    Orders ordersEntity = setOrderEntityProperties(order, submitOrderRequest);
        //    LOGGER.debug("*** SAVING ordersEntity: {}", ordersEntity.toString());
        //    returnedOrdersEntity = insertOrder(ordersEntity);
        //} //for




        logGetStockPriceResponse(getStockPriceResponse);
        return getStockPriceResponse;
    }


    /* Private methods */

    private TransactionDto setTransactionDto(final GetStockPriceRequest getStockPriceRequest) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setLogDateTime(DateUtils.getLocalDateTime());
        transactionDto.setTransactionType(EnumTransactionTypes.STOCK_PRICE_INQUIRY.getTransactionTypeDesc());
        transactionDto.setTransactionDetail(
                getStockPriceRequest.getHead().getSource() + "," + getStockPriceRequest.getTickerSymbol());
        return transactionDto;
    }

    private GetStockPriceResponse buildSuccessfulStockPriceResponse(final GetStockPriceRequest getStockPriceRequest,
                                                                 final StockPrice returnedEntity) {
        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(EnumStatusCodes.SUCCESS.getStatudCode()); //success
        stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_SUCCESSFUL));
        stockQuote.setTickerSymbol(getStockPriceRequest.getTickerSymbol());
        stockQuote.setStockPrice(returnedEntity.getCurrentPrice().floatValue());
        response.setOrder(stockQuote);
        return response;
    }

    private GetStockPriceResponse buildFailureStockPriceResponse(final GetStockPriceRequest getStockPriceRequest,
                                                                 final StockPrice returnedEntity) {
        GetStockPriceResponse response = new GetStockPriceResponse();
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(EnumStatusCodes.REQUEST_FAILED.getStatudCode()); //failure
        stockQuote.setStatusText(applicationParameterSource.getParm(StringUtils.PARM_REQUEST_FAILED));
        stockQuote.setTickerSymbol(getStockPriceRequest.getTickerSymbol());
        response.setOrder(stockQuote);
        return response;
    }

    /*private Orders insertOrder(final Orders entity) {

        LOGGER.debug("torServiceImpl.insertOrder()");
        Orders returnEntity = new Orders();
        try {
            //transactionManager.getTransaction()
//            returnEntity = ordersService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving Orders entity "
                    + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved Orders entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }*/

    private TransactionLog saveTransactionLogEntity(final TransactionLog entity) {

        LOGGER.debug("StockTickerServiceImpl.saveTransactionLogEntity()");
        TransactionLog returnEntity = new TransactionLog();
        try {
            returnEntity = transactionLogService.save(entity);

        } catch (PersistenceException | JpaSystemException | NoSuchElementException e) {
            String msg = "Exception caught while saving TransactionLog entity " + entity.getId() + ".";

            ExceptionRouter.logAndThrowApplicationException(LOGGER, msg, e.toString());
        }
        LOGGER.debug("*** Saved TransactionLog entity ***");
        LOGGER.debug(returnEntity.toString());

        return returnEntity;
    }

    private void logGetStockPriceRequest(final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerServiceImpl.logGetStockPriceRequest() entry ...");
        logRequestHeader(getStockPriceRequest.getHead());
        LOGGER.debug("********** ORDER DATA **********");
        LOGGER.debug("orderid: {}", getStockPriceRequest.getTickerSymbol());
    }

    private void logRequestHeader(final RequestHeader requestHeader) {
        LOGGER.debug("********** REQUEST HEADER **********");
        LOGGER.debug("source: {}", requestHeader.getSource());
    }

    private void logGetStockPriceResponse(final GetStockPriceResponse getStockPriceResponse) {
        LOGGER.debug("*** StockTickerServiceImpl.logGetStockPriceResponse() entry ...");
        LOGGER.debug("********** RETURNING RESPONSE **********");
        LOGGER.debug("Status Code:{}", getStockPriceResponse.getOrder().getStatusCode());
        LOGGER.debug("Status Text:{}", getStockPriceResponse.getOrder().getStatusText());
        LOGGER.debug("Stock Price:{}", NumberUtils.toBigDecimal(getStockPriceResponse.getOrder().getStockPrice()));
        LOGGER.debug("Ticker Symbol:{}", getStockPriceResponse.getOrder().getTickerSymbol());
    }

} 
