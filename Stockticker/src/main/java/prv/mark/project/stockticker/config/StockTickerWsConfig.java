package prv.mark.project.stockticker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
//import prv.mark.project.common.config.CommonDataConfig;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stocks.commontypes.schemas.RequestHeader;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceResponse;
import prv.mark.project.stocks.stocktickertypes.schemas.StockOrder;
import prv.mark.project.stocks.stocktickertypes.schemas.StockQuote;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse;
import prv.mark.project.stocks.transloggertypes.schemas.TransactionLoggerMsgType;
import prv.mark.project.stockticker.endpoint.StockTickerEndpoint;

import java.util.List;

/**
 * Spring Bean configuration.
 *
 * @author mlglenn
 */
@Configuration
@ComponentScan({"prv.mark.project.stocks.commontypes.schemas",
        "prv.mark.project.stocks.stocktickertypes.schemas", "prv.mark.project.stocks.transloggertypes.schemas"})
@Import(StockTickerDataConfig.class)
@PropertySources({
        @PropertySource("classpath:/common.properties"),
        @PropertySource("classpath:/application.properties")
})
@EnableWs
@Profile({"local", "dev", "qatest", "staging", "production"})
public class StockTickerWsConfig extends WsConfigurerAdapter { //web.xml replacement

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerWsConfig.class);

    private static final Class<?>[] WS_CLASSES_TO_BE_BOUND = {
            prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceRequest.class,
            prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceResponse.class,
            prv.mark.project.stocks.stocktickertypes.schemas.StockOrder.class,
            prv.mark.project.stocks.stocktickertypes.schemas.StockQuote.class,
            prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest.class,
            prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse.class,
            prv.mark.project.stocks.transloggertypes.schemas.TransactionLoggerMsgType.class,
            prv.mark.project.stocks.commontypes.schemas.RequestHeader.class
    };

    @Autowired
    private Environment env;

    /*@Autowired
    private ApplicationParameterSource applicationParameterSource;*/

    /*
    @Value("${keyStorePath}") TODO add
    private String keyStorePath;
    @Value("${trustStorePath}")
    private String trustStorePath;
     */
    @Value("${StockTicker.service.validatePayloads:false}")
    private String validatePayloads;
    @Value("${StockTicker.service.trace:false}")
    private String traceSoapEnvelopes;


    @Bean
    public StockTickerEndpoint stockTickerEndpoint() {
        return new StockTickerEndpoint();
    }

    @Bean(name = "StockTicker")
    public SimpleWsdl11Definition stockTicker() {
        LOGGER.info("StockTickerWsConfig: Returning new SimpleWsdl11Definition...");
        return new SimpleWsdl11Definition(new ClassPathResource("StockTicker.wsdl"));
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        LOGGER.info("StockTickerWsConfig: Returning new MessageDispatcherServlet...");
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "CommonTypes")
    public SimpleXsdSchema commonTypes() {
        LOGGER.info("StockTickerWsConfig: Returning new SimpleXsdSchema for CommonTypes.xsd...");
        return new SimpleXsdSchema(new ClassPathResource("xsd/CommonTypes.xsd"));
    }

    @Bean(name = "StockTickerTypes")
    public SimpleXsdSchema stockTickerTypes() {
        LOGGER.info("StockTickerWsConfig: Returning new SimpleXsdSchema for StockTickerTypes.xsd...");
        return new SimpleXsdSchema(new ClassPathResource("xsd/StockTickerTypes.xsd"));
    }

    @Bean(name = "TransactionLoggerTypes")
    public SimpleXsdSchema transactionLoggerTypes() {
        LOGGER.info("StockTickerWsConfig: Returning new SimpleXsdSchema for TransactionLoggerTypes.xsd...");
        return new SimpleXsdSchema(new ClassPathResource("xsd/TransactionLoggerTypes.xsd"));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        LOGGER.info("StockTickerWsConfig: Returning new Interceptors...");
        interceptors.add(payloadLoggingInterceptor());
        interceptors.add(payloadValidatingInterceptor());
    }

    @Bean
    public PayloadLoggingInterceptor payloadLoggingInterceptor() {
        LOGGER.info("StockTickerWsConfig: Returning new PayloadLoggingInterceptor...");
        final PayloadLoggingInterceptor interceptor = new PayloadLoggingInterceptor();
        LOGGER.info("StockTickerWsConfig: traceSoapEnvelopes={}", traceSoapEnvelopes);
        final Boolean logEnvelopes = Boolean.valueOf(StringUtils.safeString(traceSoapEnvelopes));
        interceptor.setLogRequest(logEnvelopes);
        interceptor.setLogResponse(logEnvelopes);
        return interceptor;
    }

    @Bean
    public PayloadValidatingInterceptor payloadValidatingInterceptor() {
        final PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        final Boolean validatePayloads = Boolean.valueOf(StringUtils.safeString(this.validatePayloads));
        interceptor.setValidateRequest(validatePayloads);
        interceptor.setValidateResponse(validatePayloads);
        interceptor.setSchema(new ClassPathResource("xsd/StockTickerTypes.xsd"));
        return interceptor;
    }

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        LOGGER.info("StockTickerWsConfig: Returning new SoapFaultMappingExceptionResolver...");
        SoapFaultMappingExceptionResolver exceptionResolver = new SoapFaultMappingExceptionResolver();
        SoapFaultDefinition defaultFault = new SoapFaultDefinition();
        defaultFault.setFaultCode(SoapFaultDefinition.CLIENT);
        exceptionResolver.setDefaultFault(defaultFault);
        return exceptionResolver;
    }

    /*@Bean
    public XwsSecurityInterceptor wsSecurityInterceptor() {
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        securityInterceptor.setCallbackHandlers(securityCallbackHandlers());
        return securityInterceptor;
    }*/

    /*private CallbackHandler[] securityCallbackHandlers() {
        CallbackHandler[] handlers = new CallbackHandler[]{keyStoreCallbackHandler()};
        return handlers;
    }*/

    /*@Bean
    public KeyStoreCallbackHandler keyStoreCallbackHandler() {
        KeyStoreCallbackHandler handler = new KeyStoreCallbackHandler();
        handler.setKeyStore(keyStore());
        handler.setTrustStore(trustStore());
        handler.setPrivateKeyPassword(KEY_STORE_PASSWORD);
        return handler;
    }*/

    /*@Bean
    public KeyStore keyStore() {
        KeyStoreFactoryBean keyStoreFactory = new KeyStoreFactoryBean();
        keyStoreFactory.setLocation(new FileSystemResource(keyStorePath));
        keyStoreFactory.setPassword(KEY_STORE_PASSWORD);
        return keyStoreFactory.getObject();
    }*/

    /*@Bean
    public KeyStore trustStore() {
        if (StringUtils.isEmpty(trustStorePath)) {
            trustStorePath = env.getProperty("JAVA_HOME") + "/jre/lib/security/cacerts";

            if (StringUtils.isEmpty(trustStorePath)) {
                throw new IllegalArgumentException("Unable to load trusted certificates file.");
            }
        }
        KeyStoreFactoryBean trustStoreFactory = new KeyStoreFactoryBean();
        trustStoreFactory.setLocation(new FileSystemResource(trustStorePath));
        trustStoreFactory.setPassword(env.getProperty(TRUST_STORE_PASSWORD));
        return trustStoreFactory.getObject();
    }*/

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        LOGGER.info("StockTickerWsConfig: Returning new WebServiceTemplate...");
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        webServiceTemplate.setCheckConnectionForError(true);
        webServiceTemplate.setCheckConnectionForFault(true);
        return webServiceTemplate;
    }

    /*@Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com..xml.lment");
        return marshaller;
    }*/

    @Bean
    public Jaxb2Marshaller marshaller() {
        LOGGER.info("StockTickerWsConfig: Returning new Jaxb2Marshaller for WS_CLASSES_TO_BE_BOUND");
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(WS_CLASSES_TO_BE_BOUND);
        //marshaller.setPackagesToScan("prv.mark.project.stocks");
        return marshaller;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        LOGGER.info("StockTickerWsConfig: Returning new messageFactory...");
        return new SaajSoapMessageFactory();
    }

}
