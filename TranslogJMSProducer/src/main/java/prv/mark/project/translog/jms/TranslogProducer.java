package prv.mark.project.translog.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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

    @Autowired
    private Jaxb2Marshaller translogProducerJmsMarshaller;




    public void sendMessage(final String name) {
        LOGGER.info("TranslogProducer.sendMessage()");

        //IntegrationFlow integrationFlow = translogProducerIntegrationFlow()

        //translogProducerJmsMarshaller.
        //MessageBuilder

        translogProducerChannel.send(MessageBuilder.withPayload(name).build());

    }
}
