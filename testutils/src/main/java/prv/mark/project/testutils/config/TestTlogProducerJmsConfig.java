package prv.mark.project.testutils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.xml.transformer.MarshallingTransformer;
import org.springframework.integration.xml.transformer.ResultToStringTransformer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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
//@ImportResource(value = {"classpath:/TlogJmsConfig-TEST.xml"}) unable to load application context
@ComponentScan(basePackages = {"prv.mark.project"})
@Profile("test")
public class TestTlogProducerJmsConfig {

    private static final Logger logger = LoggerFactory.getLogger(TestTlogProducerJmsConfig.class);

    //Defined in TlogJmsConfig.xml and accessed via jms/TlogConnectionFactory
    /*@Autowired(required = false)
    protected ConnectionFactory tlogConnectionFactory;*/

    //Defined in TlogJmsConfig.xml
    /*@Autowired(required = false)
    @Qualifier("tlogJMSQueue")
    protected Queue tlogJMSQueue;*/

    //Defined in TlogJmsConfig.xml
    /*@Autowired(required = false)
    @Qualifier("tlogErrorJMSQueue")
    protected Queue tlogErrorJMSQueue;*/

    //Defined in TlogJmsConfig.xml
    /*@Autowired(required = false)
    protected MessageChannel tlogJMSLogger;*/

    /*@Bean TODO channel must not be null
    public WireTap wireTap() {
        logger.debug("TlogProducerJmsConfig.wireTap(): Returning a new WireTap instance ...");
        return new WireTap(tlogJMSLogger);
    }*/

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

    @Bean
    public MarshallingTransformer translogProducerTransformer() {
        logger.debug("TlogProducerJmsConfig.translogProducerTransformer(): Returning a new MarshallingTransformer instance ...");
        return Transformers.marshaller(translogProducerJmsMarshaller(), new ResultToStringTransformer());
    }

    /*@Bean
    public JmsTemplate jmsTemplate() {
        logger.debug("TlogProducerJmsConfig.jmsTemplate(): Returning a new JmsTemplate instance ...");
        JmsTemplate jmsTempl = new JmsTemplate(tlogConnectionFactory);
        //jmsTempl.setDefaultDestinationName();
        jmsTempl.setDefaultDestination(tlogJMSQueue);
        jmsTempl.setMessageIdEnabled(true);
        jmsTempl.setMessageTimestampEnabled(true);
        jmsTempl.setSessionTransacted(true);
        return jmsTempl;
    }*/
}
