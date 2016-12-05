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
import prv.mark.project.stocks.commontypes.schemas.RequestHeader;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceResponse;
import prv.mark.project.stocks.stocktickertypes.schemas.StockOrder;
import prv.mark.project.stocks.stocktickertypes.schemas.StockQuote;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse;
import prv.mark.project.stocks.transloggertypes.schemas.TransactionLoggerMsgType;
//import prv.mark.project.xml.stocks.*;

@Configuration
@ComponentScan({"prv.mark.project", "prv.mark.project.stocks.commontypes.schemas",
        "prv.mark.project.stocks.stocktickertypes.schemas", "prv.mark.project.stocks.transloggertypes.schemas"}) //For JAXB classes
@EnableWs
@Import(TestDataConfig.class)
@PropertySource("classpath:test-common.properties")
@Profile("test")
public class TestWebServicesConfig {

    //@Value("${path_1}")
    //private String keyStorePath; TODO
    //@Value("${path_2}")
    //private String trustStorePath;
    @Value("${key.store.password}")
    private String keyStorePassword;
    @Value("${trust.store.password}")
    private String trustStorePassword;

    private static final Class<?>[] CLASSES_TO_BE_BOUND = {
            GetStockPriceRequest.class,
            GetStockPriceResponse.class,
            StockOrder.class,
            StockQuote.class,
            SubmitOrderRequest.class,
            SubmitOrderResponse.class,
            TransactionLoggerMsgType.class,
            RequestHeader.class
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
