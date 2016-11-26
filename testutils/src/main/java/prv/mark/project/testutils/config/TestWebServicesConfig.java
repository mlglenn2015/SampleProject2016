package prv.mark.project.testutils.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;

@Configuration
@ComponentScan({"prv.mark.project", "prv.mark.xml.stocks"}) //For JAXB classes
@EnableWs
@Import(TestDataConfig.class)
@PropertySource("classpath:test-common.properties")
@Profile("test")
public class TestWebServicesConfig {

    //@Value("${path_1}")
    //private String keyStorePath;
    @Value("${key.store.password}")
    private String keyStorePassword;
    //@Value("${path_2}")
    //private String trustStorePath;
    @Value("${trust.store.password}")
    private String trustStorePassword;

    private static final Class<?>[] CLASSES_TO_BE_BOUND = {
            prv.mark.xml.stocks.GetStockPriceRequest.class,
            prv.mark.xml.stocks.GetStockPriceResponse.class,
            prv.mark.xml.stocks.StockOrder.class,
            prv.mark.xml.stocks.StockQuote.class,
            prv.mark.xml.stocks.RequestHeader.class
    };

    @Autowired
    private Environment environment;

    /*@Bean
    public KeyStore keyStore() {
        KeyStoreFactoryBean keyStoreFactory = new KeyStoreFactoryBean();
        keyStoreFactory.setLocation(new FileSystemResource(keyStorePath));
        keyStoreFactory.setPassword(keyStorePassword);
        return keyStoreFactory.getObject();
    }*/

    /*@Bean
    public KeyStore trustStore() {
        if (StringUtils.isEmpty(trustStorePath)) {
            trustStorePath = environment.getProperty("JAVA_HOME") + "/jre/lib/security/cacerts";

            if (StringUtils.isEmpty(trustStorePath)) {
                throw new IllegalArgumentException("Unable to load trusted certificates file.");
            }
        }
        KeyStoreFactoryBean trustStoreFactory = new KeyStoreFactoryBean();
        trustStoreFactory.setLocation(new FileSystemResource(trustStorePath));
        trustStoreFactory.setPassword(environment.getProperty(trustStorePassword));
        return trustStoreFactory.getObject();
    }*/

    /*@Bean
    public KeyStoreCallbackHandler keyStoreCallbackHandler() {
        KeyStoreCallbackHandler handler = new KeyStoreCallbackHandler();
        handler.setKeyStore(keyStore());
        handler.setTrustStore(trustStore());
        handler.setPrivateKeyPassword(keyStorePassword);
        return handler;
    }*/

    @Bean
    public Jaxb2Marshaller soapMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(CLASSES_TO_BE_BOUND);
        return marshaller;
    }

    @Bean
    public SoapFaultAnnotationExceptionResolver soapFaultExceptionResolver() {
        return new SoapFaultAnnotationExceptionResolver();
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        return new SaajSoapMessageFactory();
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate template = new WebServiceTemplate(messageFactory());
        template.setMarshaller(soapMarshaller());
        template.setUnmarshaller(soapMarshaller());
        return template;
    }
}
