package prv.mark.project.stockticker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import prv.mark.project.common.config.CommonDataConfig;
import prv.mark.project.common.util.StringUtils;
import prv.mark.project.stocks.commontypes.schemas.RequestHeader;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.GetStockPriceResponse;
import prv.mark.project.stocks.stocktickertypes.schemas.StockOrder;
import prv.mark.project.stocks.stocktickertypes.schemas.StockQuote;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderRequest;
import prv.mark.project.stocks.stocktickertypes.schemas.SubmitOrderResponse;
import prv.mark.project.stocks.transloggertypes.schemas.TransactionLoggerMsgType;

import java.util.List;

/**
 * Spring Bean configuration.
 *
 * @author mlglenn
 */
@Configuration
@ComponentScan({"prv.mark.project.stocks.commontypes.schemas",
        "prv.mark.project.stocks.stocktickertypes.schemas", "prv.mark.project.stocks.transloggertypes.schemas"})
@Import(CommonDataConfig.class)
@PropertySources({
        @PropertySource("classpath:/common.properties"),
        @PropertySource("classpath:/application.properties")
})
@EnableWs
//@EnableMBeanExport(defaultDomain = "prv.mark.project", server="jmxServerRuntime",
//        registration = RegistrationPolicy.IGNORE_EXISTING)
@Profile({"local", "dev", "qatest", "staging", "production"})
public class StockTickerWsConfig extends WsConfigurerAdapter {

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
    private Environment env;

    /*@Autowired
    private ApplicationParameterSource applicationParameterSource;*/

    /*
    @Value("${path_1}") TODO add
    private String keyStorePath;
    @Value("${path_2}")
    private String trustStorePath;
     */
    @Value("${StockTicker.service.validatePayloads:false}")
    private String validatePayloads;
    @Value("${StockTicker.service.trace:false}")
    private String traceSoapEnvelopes;

    @Bean(name = "StockTicker")
    public SimpleWsdl11Definition stockTicker() {
        return new SimpleWsdl11Definition(new ClassPathResource("StockTicker.wsdl"));
    }

    /*@Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        //servlet.seetApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws*//*");
    }*/

    @Bean(name = "CommonTypes")
    public SimpleXsdSchema commonTypes() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/CommonTypes.xsd"));
    }

    @Bean(name = "StockTickerTypes")
    public SimpleXsdSchema stockTickerTypes() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/StockTickerTypes.xsd"));
    }

    @Bean(name = "TransactionLoggerTypes")
    public SimpleXsdSchema transactionLoggerTypes() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/TransactionLoggerTypes.xsd"));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(payloadLoggingInterceptor());
        //interceptors.add(payloadValidatingInterceptor());
    }

    @Bean
    public PayloadLoggingInterceptor payloadLoggingInterceptor() {
        final PayloadLoggingInterceptor interceptor = new PayloadLoggingInterceptor();
        final Boolean logEnvelopes = Boolean.valueOf(StringUtils.safeString(traceSoapEnvelopes));
        interceptor.setLogRequest(logEnvelopes);
        interceptor.setLogResponse(logEnvelopes);
        return interceptor;
    }

    /*@Bean
    public PayloadValidatingInterceptor payloadValidatingInterceptor() {
        final PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        final Boolean validatePayloads = Boolean.valueOf(StringUtils.safeString(this.validatePayloads));
        interceptor.setValidateRequest(validatePayloads);
        interceptor.setValidateResponse(validatePayloads);
        interceptor.setSchema(new ClassPathResource("xsd/Types.xsd"));
        return interceptor;
    }*/

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
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

    /*@Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com..xml.lment");
        return marshaller;
    }*/

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        webServiceTemplate.setCheckConnectionForError(true);
        webServiceTemplate.setCheckConnectionForFault(true);
        return webServiceTemplate;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(CLASSES_TO_BE_BOUND);
        //marshaller.setPackagesToScan("prv.mark.project");
        return marshaller;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        return new SaajSoapMessageFactory();
    }

}
