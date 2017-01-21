package prv.mark.project.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import prv.mark.project.domain.Greeting;
import prv.mark.project.domain.Person;

/**
 * https://github.com/code-not-found/spring-ws/blob/master/springws-helloworld-example/src/main/java/com/codenotfound/endpoint/HelloWorldEndpoint.java
 *
 * Created by Owner on 1/13/2017.
 */
@Endpoint
public class HelloEndpoint {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HelloEndpoint.class);

    private static final String NAMESPACE_URI = "http://project.mark.prv/types/hello";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "person")
    @ResponsePayload
    public Greeting sayHello(@RequestPayload Person request) {
        LOGGER.info(
                "Endpoint received person[firstName={},lastName={}]",
                request.getFirstName(), request.getLastName());

        String greeting = "Hello " + request.getFirstName() + " "
                + request.getLastName() + "!";

        Greeting response = new Greeting();
        response.setGreeting(greeting);

        LOGGER.info("Endpoint sending greeting='{}'",
                response.getGreeting());
        return response;
    }

}
