package prv.mark.project.stockticker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import prv.mark.project.common.config.CommonDataConfig;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.service.impl.ApplicationParameterSource;
import prv.mark.project.common.util.StringUtils;

import javax.management.MBeanServer;
import java.util.List;

/**
 * Spring Bean configuration.
 *
 * @author mlglenn
 */
@Configuration
@ComponentScan("prv.mark.project")  //prv.mark.project.stockticker
@Import(CommonDataConfig.class)
@PropertySources({
        @PropertySource("classpath:/common.properties"),
        @PropertySource("classpath:/application.properties"),
        @PropertySource("classpath:/StockTicker.properties")
})
@EnableWs
//@EnableMBeanExport(defaultDomain = "prv.mark.project", server="jmxServerRuntime",
//        registration = RegistrationPolicy.IGNORE_EXISTING)
@Profile({"local", "dev", "test", "staging", "production"})
public class StockTickerWsConfig extends WsConfigurerAdapter {

    private static final Class<?>[] CLASSES_TO_BE_BOUND = {
            prv.mark.xml.stocks.GetStockPriceRequest.class,
            prv.mark.xml.stocks.GetStockPriceResponse.class,
            prv.mark.xml.stocks.StockOrder.class,
            prv.mark.xml.stocks.StockQuote.class,
            prv.mark.xml.stocks.RequestHeader.class,
            prv.mark.xml.stocks.SubmitOrderRequest.class,
            prv.mark.xml.stocks.SubmitOrderResponse.class
    };

    @Autowired
    private Environment env;

    /*@Autowired
    private ApplicationParameterSource applicationParameterSource;*/

    /*
    @Value("${path_1}")
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

    /*@Bean(name = "CommonTypes")
    public SimpleXsdSchema commonTypes() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/CommonTypes.xsd"));
    }*/

    /*@Bean(name = "Types")
    public SimpleXsdSchema types() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/Types.xsd"));
    }*/

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

    /*@Bean TODO moved to ApplicationConfig
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }*/

    /*@Bean TODO moved to ApplicationConfig
    public MessageSource messageSource() {
        return new ApplicationMessageSource();
    }
*/
    /*@Bean TODO moved to ApplicationConfig
    public MessageSource applicationParameterSource() {
        return new ApplicationParameterSource();
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

    /*@Bean TODO moved to JmxConfig
    public JndiObjectFactoryBean jmxServerRuntime() {
        JndiObjectFactoryBean runtime = new JndiObjectFactoryBean();
        runtime.setJndiName("java:comp/env/jmx/runtime");
        runtime.setProxyInterface(MBeanServer.class);
        return runtime;
    }*/
}
