package prv.mark.project.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import prv.mark.project.domain.Greeting;
import prv.mark.project.domain.Person;


/**
 * Created by Owner on 1/13/2017.
 */
@Component
public class HelloWorldClient {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HelloWorldClient.class);

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public String sayHello(String firstName, String lastName) {

        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastName);

        LOGGER.info("Client sending person[firstName={},lastName={}]",
                person.getFirstName(), person.getLastName());

        Greeting greeting = (Greeting) webServiceTemplate
                .marshalSendAndReceive(person);

        LOGGER.info("Client received greeting='{}'",
                greeting.getGreeting());
        return greeting.getGreeting();
    }

}
