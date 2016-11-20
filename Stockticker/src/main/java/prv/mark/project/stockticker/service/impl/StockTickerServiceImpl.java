package prv.mark.project.stockticker.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.stockticker.service.StockTickerService;
import prv.mark.xml.stocks.GetStockPriceRequest;
import prv.mark.xml.stocks.GetStockPriceResponse;
import prv.mark.xml.stocks.RequestHeader;
import prv.mark.xml.stocks.StockQuote;

/**
 * Service implementation of the {@link StockTickerService} interface.
 *
 * @author mlglenn
 */
@Service
public class StockTickerServiceImpl implements StockTickerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerServiceImpl.class);
    private static final int SUCCESSFUL = 1;
    private static final int FAILURE = 0;

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    //@Autowired
    //private OrdersService ordersService;

    //@Autowired
    //private PlatformTransactionManager transactionManager;


    @Override
    public GetStockPriceResponse getStockPrice(final GetStockPriceRequest getStockPriceRequest) {
        LOGGER.debug("*** StockTickerServiceImpl.getStockPrice() entry ...");
        GetStockPriceResponse getStockPriceResponse = new GetStockPriceResponse();

        logGetStockPriceRequest(getStockPriceRequest);

        //TODO testing
        StockQuote stockQuote = new StockQuote();
        stockQuote.setStatusCode(1);
        stockQuote.setStatusText("success");
        stockQuote.setTickerSymbol("WMT");
        stockQuote.setStockPrice(NumberUtils.toFloat("68.00"));
        getStockPriceResponse.setOrder(stockQuote);


        //Orders returnedOrdersEntity = new Orders();

        //TODO Save the order to the in-memory database
        //for (Order order : submitOrderRequest.getOrders().getOrder()) {
        //    Orders ordersEntity = setOrderEntityProperties(order, submitOrderRequest);
        //    LOGGER.debug("*** SAVING ordersEntity: {}", ordersEntity.toString());
        //    returnedOrdersEntity = insertOrder(ordersEntity);
        //} //for

        //Pretend to submit an order and return a successful response
        //if (returnedOrdersEntity != null) {
            //getStockPriceResponse = buildSuccessfulSubmitOrderResponse();
        //} else {
        //    submitOrderResponse = buildFailureSubmitOrderResponse();
        //}

        logGetStockPriceResponse(getStockPriceResponse);

        return getStockPriceResponse;
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
