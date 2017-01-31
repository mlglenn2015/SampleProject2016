package prv.mark.project.stockservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Spring WS Bean configuration.
 *
 * @author mlglenn on 1/29/2017.
 */
@Configuration
@ComponentScan({"prv.mark.project.stockservice"})
@Import(StocksDataConfig.class)
@PropertySources({
        @PropertySource("classpath:/application.properties")
})
// @PropertySource("classpath:/common.properties"), TODO
@EnableWs
@Profile({"local", "dev", "qatest", "staging", "production"})
public class WebServiceConfig extends WsConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceConfig.class);

    private static final Class<?>[] WS_CLASSES_TO_BE_BOUND = {
            prv.mark.project.stockservice.schemas.GetStockPriceRequest.class,
            prv.mark.project.stockservice.schemas.GetStockPriceResponse.class,
            prv.mark.project.stockservice.schemas.StockOrder.class,
            prv.mark.project.stockservice.schemas.StockQuote.class,
            prv.mark.project.stockservice.schemas.SubmitOrderRequest.class,
            prv.mark.project.stockservice.schemas.SubmitOrderResponse.class,
            prv.mark.project.stockservice.schemas.RequestHeader.class
    };

    @Autowired
    private Environment env;


    /* This replaces the old web.xml configuration */
    /*@Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws*//*");
    }*/

    /*@Bean(name = "stocks")
    public SimpleWsdl11Definition stocks() {
        LOGGER.info("WebServiceConfig: Returning new SimpleWsdl11Definition...");
        return new SimpleWsdl11Definition(new ClassPathResource("Stocks.wsdl"));
    }*/

    @Bean(name = "stockservice")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema stockServiceTypesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("StockServicePort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://project.mark.prv/stockservice/schemas");
        wsdl11Definition.setSchema(stockServiceTypesSchema);
        return wsdl11Definition;
    }

    /*@Bean(name = "commonTypes")
    public SimpleXsdSchema commonTypes() {
        LOGGER.info("WebServiceConfig: Returning new SimpleXsdSchema for CommonTypes.xsd...");
        return new SimpleXsdSchema(new ClassPathResource("xsd/CommonTypes.xsd"));
    }*/

    //@Bean(name = "stockServiceTypes")
    @Bean
    public SimpleXsdSchema stockServiceTypesSchema() {
        LOGGER.info("WebServiceConfig: Returning new SimpleXsdSchema for StockServiceTypes.xsd...");
        return new SimpleXsdSchema(new ClassPathResource("xsd/StockServiceTypes.xsd"));
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        LOGGER.info("WebServiceConfig: Returning new messageFactory...");
        return new SaajSoapMessageFactory();
    }
}
