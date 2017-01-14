package prv.mark.project.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse;

import java.math.BigDecimal;

/**
 *
 * http://www.source4code.info/2016/10/spring-ws-soap-web-service-consumer-provider-wsdl-example.html
 *
 * https://github.com/code-not-found/spring-ws/tree/master/springws-helloworld-example
 *
 * Created by Owner on 1/12/2017.
 */
public class OrderEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEndpoint.class);

    @PayloadRoot(localPart="clientDataRequest", namespace="http://prv.mark.project/stocks")
    public @ResponsePayload
    SubmitOrderResponse order(@RequestPayload SubmitOrderRequest submitOrderRequest) {

        LOGGER.info("*** OrderEndpoint.order() entry ...");
        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setStatus(0);
        response.setStatusDesc("SUCCESS");
        return response;
    }

}
