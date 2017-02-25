package prv.mark.project.translog.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * TODO
 * Created by Owner on 2/19/2017.
 */
public class TranslogProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslogProducer.class);

    @Autowired
    private IntegrationFlow translogProducerIntegrationFlow;

    @Autowired
    private MessageChannel translogProducerChannel;


    public void sendMessage(final String name) {
        LOGGER.info("TranslogProducer.sendMessage()");

        //IntegrationFlow integrationFlow = translogProducerIntegrationFlow()


        translogProducerChannel.send(MessageBuilder.withPayload(name).build());

    }
}
