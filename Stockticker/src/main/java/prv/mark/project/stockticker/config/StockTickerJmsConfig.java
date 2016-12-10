package prv.mark.project.stockticker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.xml.transformer.UnmarshallingTransformer;
import org.springframework.integration.xml.transformer.XsltPayloadTransformer;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import prv.mark.project.common.util.NumberUtils;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stockticker.interceptor.TbdLoggingInterceptor;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 * Created by Owner on 12/3/2016.
 */
@Configuration
@PropertySource(value = {"classpath:/application.properties"})
@Profile("tbd") //TODO
public class StockTickerJmsConfig extends JmsConfig {

    private static final String TBD_CLIENT_ID = "TBDListener";

    @Autowired
    @Qualifier("tbdCF")
    private ConnectionFactory tbdCF;

    @Autowired
    @Qualifier("incomimgStocksQueue")
    private Queue incomimgStocksQueue;

    @Autowired
    @Qualifier("incomimgStocksQueue2") // TODO TBD
    private Queue incomimgStocksQueue2;

    @Value("${application.jms.concurrentConsumers}")
    private String concurrentConsumers;

    @Value("${application.jms.maxConsumers}")
    private String maxConsumers;

    @Value("${application.jms.strPollFrequency}")
    private String strPollFrequency;

    @Value("${application.jms.messagesPerPoll}")
    private String messagesPerPoll;


    /**
     * TODO
     * @return
     */
    @Bean
    public Jaxb2Marshaller stocksMsgMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("prv.mark.project.stocks.transloggertypes.schemas");
        Map<String, Object> properties = new HashMap<>();
        properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxb2Marshaller.setMarshallerProperties(properties);
        return jaxb2Marshaller;
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public MarshallingMessageConverter stocksConverter() {
        return new MarshallingMessageConverter(stocksMsgMarshaller(), stocksMsgMarshaller());
    }

    /**
     * TODO
     */
    @Bean
    public MessageChannel tbdChannel() {
        return MessageChannels.direct("tbdChannel")
                .interceptor(new TbdLoggingInterceptor())
                .get();
    }

    /**
     * TODO
     */
    @Bean
    public MessageChannel incomingStocksChannel() {
        return MessageChannels.queue("incomingStocksChannel")
                .interceptor(new WireTap(jmsLogger))
                .get();
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public UnmarshallingTransformer stocksMsgUnmarshaller() {
        return Transformers.unmarshaller(stocksMsgMarshaller());
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public XsltPayloadTransformer tbdNamespaceTransformer() {
        return new XsltPayloadTransformer(new ClassPathResource("tbd_strip_namespaces.xsl"));
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public AbstractMessageListenerContainer tbdListener() {
        int consumerCount = (StringUtils.isNotEmpty(concurrentConsumers) && NumberUtils.isNumber(concurrentConsumers))
                ? Integer.valueOf(concurrentConsumers) : 1;
        int maxConsumerCount = (StringUtils.isNotEmpty(maxConsumers) && NumberUtils.isNumber(maxConsumers))
                ? Integer.valueOf(maxConsumers) : 5;
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(tbdCF);
        defaultMessageListenerContainer.setDestination(incomimgStocksQueue2);
        defaultMessageListenerContainer.setClientId(TBD_CLIENT_ID);
        defaultMessageListenerContainer.setConcurrentConsumers(consumerCount);
        defaultMessageListenerContainer.setMaxConcurrentConsumers(maxConsumerCount);
        return defaultMessageListenerContainer;
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public IntegrationFlow jmsIntegrationFlow() {
        return IntegrationFlows.from(Jms.messageDriverChannelAdapter(tbdListener()))
                .handle(Jms.outboundAdapter(tbdCF).destination(incomimgStocksQueue))
                .get();
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public IntegrationFlow incomingFlow() {
        int pollFrequency = (StringUtils.isNotEmpty(strPollFrequency) && NumberUtils.isNumber(strPollFrequency))
                ? Integer.valueOf(strPollFrequency) : 15000; //seconds
        int msgsPerPoll = (StringUtils.isNotEmpty(messagesPerPoll) && NumberUtils.isNumber(messagesPerPoll))
                ? Integer.valueOf(messagesPerPoll) : 5000;
        return IntegrationFlows.from(Jms.inboundAdapter(tbdCF)
                    .destination(incomimgStocksQueue),
                c -> c.poller(Pollers.fixedDelay(pollFrequency).maxMessagesPerPoll(msgsPerPoll)))
                    .transform(tbdNamespaceTransformer())
                    .transform(stocksMsgUnmarshaller())
                    .filter("@transactionLogFilter.accept(payload)")
                    .enrichHeaders(s -> s.headerExpressions(
                            h -> h.put("transactionLogNumber", "payload.transactionLogs[0].transLogNumber")))
                    .channel(tbdChannel())
                    .get();
    }

    /**
     * TODO
     * @return
     */
    @Bean
    public IntegrationFlow tbdEventFlow() {
        return IntegrationFlows.from("tbdChannel")
                .handle("tbdEventHandler", "handleEvent")  //The handler bean name and main method
                .transform(stocksTransformer())
                .channel(stocksChannel())
                .get();
    }
}
