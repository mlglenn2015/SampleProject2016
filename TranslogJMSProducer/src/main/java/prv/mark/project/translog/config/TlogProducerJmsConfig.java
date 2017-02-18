package prv.mark.project.translog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.xml.transformer.MarshallingTransformer;
import org.springframework.integration.xml.transformer.ResultToStringTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import prv.mark.project.translog.interceptor.TranslogProducerInterceptor;
import prv.mark.project.translog.schemas.TransactionLoggerMsgType;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Bean base configuration for JMS components.
 *
 * Sources:
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jms.html
 * https://spring.io/guides/gs/messaging-jms/
 *
 * @author mlglenn on 2/15/2017.
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
@ImportResource(value = {"classpath:/TlogJmsConfig.xml"})
@Profile({"local", "dev", "qatest", "staging", "production"})
public class TlogProducerJmsConfig {

    private static final Logger logger = LoggerFactory.getLogger(TlogProducerJmsConfig.class);

    @Autowired
    protected ConnectionFactory tlogConnectionFactory;  // TODO? JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

    @Autowired
    @Qualifier("tlogJMSQueue")
    protected Queue tlogJMSQueue;

    /*@Autowired
    @Qualifier("tlogErrorJMSQueue")
    protected Queue tlogErrorJMSQueue;*/

    /**
     * Generic {@link MessageChannel} used to log messages intercepted by {@link WireTap()}.
     */
    @Autowired
    protected MessageChannel tlogJMSLogger;

    @Bean
    public WireTap wireTap() {
        return new WireTap(tlogJMSLogger);
    }

    /**
     * JAXB marshaller/unmarshaller for Stock Ticker application JMS messages.
     *
     * @return {@link Jaxb2Marshaller}
     */
    @Bean
    public Jaxb2Marshaller translogProducerJmsMarshaller() {
        logger.debug("TlogProducerJmsConfig.translogProducerJmsMarshaller(): Returning a Jaxb2Marshaller instance ...");
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("prv.mark.project.translog.schemas");
        Map<String, Object> props = new HashMap<>();
        props.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxb2Marshaller.setMarshallerProperties(props);
        return jaxb2Marshaller;
    }

    /**
     * Marshals {@link TransactionLoggerMsgType} into JMS messages.
     *
     * @return {@link MarshallingTransformer}
     */
    @Bean
    public MarshallingTransformer translogProducerTransformer() {
        return Transformers.marshaller(translogProducerJmsMarshaller(), new ResultToStringTransformer());
    }

    /**
     * Transaction Log Producer message integration channel.
     *
     * {@link TransactionLoggerMsgType} messages are placed onto this channel to be consumed by the TransactionLogger.
     *
     * @return {@link MessageChannel}
     */
    @Bean
    public MessageChannel translogProducerChannel() {
        return MessageChannels.queue("translogProducerChannel", 500)
                .interceptor(new TranslogProducerInterceptor())
                .get();
    }

    /**
     * Publishes JMS messages to the Transaction Log JMS queue.
     *
     * @return {@link IntegrationFlow}
     */
    @Bean
    public IntegrationFlow translogProducerIntegrationFlow() {
        //return f -> f.channel(translogProducerChannel()) TODO ?
        return f -> f.channel("translogProducerChannel")
                .handle(Jms.outboundAdapter(tlogConnectionFactory).destination(tlogJMSQueue));
    }
}
