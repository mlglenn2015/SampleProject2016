package prv.mark.project.stockticker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.xml.transformer.MarshallingTransformer;
import org.springframework.integration.xml.transformer.ResultToStringTransformer;
import org.springframework.messaging.MessageChannel;
//import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import prv.mark.project.stockticker.interceptor.StocksLoggingInterceptor;
import prv.mark.project.stocks.transloggertypes.schemas.TransactionLoggerMsgType;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Bean base configuration for JMS components.
 * This configuration is always active, and contains common JMS components, as well as application-specific
 * components that are shared with the {@link StockTickerJmsConfig}.
 *
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jms.html
 * https://spring.io/guides/gs/messaging-jms/
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
@ImportResource(value = {"classpath:/StockTickerJms.xml"})
public abstract class JmsConfig {

    /**
     * Stock Ticker application connection factory.
     */
    @Autowired
    protected ConnectionFactory stocksConnectionFactory;

    /**
     * JMS Queue where messages are sent
     */
    @Autowired
    @Qualifier("stocksQueue")
    protected Queue stocksQueue;

    /**
     * JMS Queue where messages are sent
     */
    @Autowired
    @Qualifier("stocksErrorQueue")
    protected Queue stocksErrorQueue;

    /**
     * Generic {@link MessageChannel} used to log messages intercepted by {@link WireTap()}.
     */
    @Autowired
    protected MessageChannel jmsLogger;

    @Bean
    public WireTap wireTap() {
        return new WireTap(jmsLogger);
    }

    /**
     * Stock Ticker application message integration channel.
     *
     * {@link TransactionLoggerMsgType} messages are placed onto this channel to send to the TransactionLogger.
     *
     * @return {@link MessageChannel}
     */
    @Bean
    public MessageChannel stocksChannel() {
        return MessageChannels.queue("stocksChannel", 500)
                .interceptor(new StocksLoggingInterceptor())
                .get();
    }

    /**
     * JAXB marshaller/unmarshaller for Stock Ticker application JMS messages.
     *
     * @return {@link Jaxb2Marshaller}
     */
    @Bean
    public Jaxb2Marshaller stocksJmsMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("prv.mark.project.stocks.transloggertypes.schemas");
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
    public MarshallingTransformer stocksTransformer() {
        return Transformers.marshaller(stocksJmsMarshaller(), new ResultToStringTransformer());
    }

    /**
     * Poller to execute at specific intervals. Picks up messages from the incoming queue
     * and places them on the output queue.
     *
     * @return {@link PollerMetadata}
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(15000).maxMessagesPerPoll(-1).get(); //15 seconds
    }

    /**
     * Publishes JMS messages to the stocksQueue JMS queue.
     *
     * @return {@link IntegrationFlow}
     */
    @Bean
    IntegrationFlow stocksFlow() {
        //return f -> f.channel(stocksChannel()) TODO ?
        return f -> f.channel("stocksChannel")
                .handle(Jms.outboundAdapter(stocksConnectionFactory).destination(stocksQueue));
    }

    /**
     * Publishes JMS messages to the stocksErrorQueue JMS queue.
     *
     * @return {@link IntegrationFlow}
     */
    @Bean
    IntegrationFlow stocksErrorFlow() {
        return f -> f.channel("errorChannel")
                .handle(Jms.outboundAdapter(stocksConnectionFactory).destination(stocksErrorQueue));
    }
}
