package prv.mark.project.translogproducer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.xml.transformer.UnmarshallingTransformer;
import org.springframework.integration.xml.transformer.XsltPayloadTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import prv.mark.project.translogproducer.interceptor.StocksLoggingInterceptor;
import prv.mark.project.testutils.config.TestDataConfig;
import prv.mark.project.testutils.config.TestWebServicesConfig;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Owner on 12/10/2016.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@EnableIntegration
@IntegrationComponentScan
@Import({TestDataConfig.class, TestWebServicesConfig.class})
@ImportResource("classpath:test-jms-context.xml")
@Profile("test")
public class TestJmsConfig {

    //@Autowired
    //protected ConnectionFactory stocksConnectionFactory; //TODO No qualifying bean of type javax.jms.ConnectionFactory

    //@Autowired
    //@Qualifier("stocksQueue")
    //protected Queue stocksQueue;

    //@Autowired
    //@Qualifier("stocksErrorQueue")
    //protected Queue stocksErrorQueue;


    /*@Bean
    public MessageChannel stocksChannel() {
        return MessageChannels.queue("stocksChannel", 500)
                .interceptor(new StocksLoggingInterceptor())
                .get();
    }*/

    /*@Bean
    public IntegrationFlow stocksFlow() {
        //return f -> f.channel(stocksChannel()) TODO ?
        return f -> f.channel("stocksChannel")
                .handle(Jms.outboundAdapter(stocksConnectionFactory).destination(stocksQueue));
    }

    @Bean
    public IntegrationFlow stocksErrorFlow() {
        return f -> f.channel("errorChannel")
                .handle(Jms.outboundAdapter(stocksConnectionFactory).destination(stocksErrorQueue));
    }*/

    @Bean
    public Jaxb2Marshaller tbdJmsMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("prv.mark.project");
        return jaxb2Marshaller;
    }

    @Bean
    public Jaxb2Marshaller stocksJmsMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("prv.mark.project.stocks.transloggertypes.schemas");
        return jaxb2Marshaller;
    }

    @Bean
    public Jaxb2Marshaller stocksMsgMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("prv.mark.project.stocks.transloggertypes.schemas");
        return jaxb2Marshaller;
    }

    @Bean
    public UnmarshallingTransformer tbdMsgUnmarshaller() {
        return Transformers.unmarshaller(tbdJmsMarshaller());
    }

    @Bean
    public UnmarshallingTransformer stocksMsgUnmarshaller() {
        return Transformers.unmarshaller(stocksMsgMarshaller());
    }

    @Bean
    public XsltPayloadTransformer tbdNamespaceTransformer() {
        return new XsltPayloadTransformer(new ClassPathResource("tbd_strip_namespaces.xsl"));
    }

}
