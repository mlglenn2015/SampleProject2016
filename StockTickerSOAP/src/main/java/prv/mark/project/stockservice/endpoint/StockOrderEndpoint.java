package prv.mark.project.stockservice.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import prv.mark.project.stockservice.schemas.SubmitOrderRequest;
import prv.mark.project.stockservice.schemas.SubmitOrderResponse;

/**
 * Spring WS Endpoint class.
 * https://spring.io/guides/gs/producing-web-service/
 *
 * @author mlglenn on 1/12/2017.
 */
@Endpoint
public class StockOrderEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderEndpoint.class);
    private static final String NAMESPACE_URI = "http://project.mark.prv/stockservice/schemas";


    /*@Autowired
    public StockOrderEndpoint(MyRepository repository) {
        LOGGER.info("*** StockOrderEndpoint() entry ...");
    }*/

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "submitOrderRequest")
    @ResponsePayload
    public SubmitOrderResponse submitOrder(@RequestPayload SubmitOrderRequest submitOrderRequest) {

        LOGGER.info("*** StockOrderEndpoint.order() entry ...");
        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(0);
        response.setStatusDesc("SUCCESS");
        return response;
    }

}
