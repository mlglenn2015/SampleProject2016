package prv.mark.project.stockservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.testutils.config.TestDataConfig;
import prv.mark.project.testutils.config.TestWebServicesConfig;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

/**
 * TODO
 *
 * Created by Owner on 2/26/2017.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@Import({TestDataConfig.class, TestWebServicesConfig.class})
@PropertySources(value = {
        @PropertySource("classpath:StockTickerSOAP-TEST.properties")
})
@Profile("test")
public class StockTickerSOAPTestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerSOAPTestConfig.class);

    @Autowired
    private Environment environment;

    @Value("${StockTickerSOAP.service.trace}")
    private String serviceTrace;
    @Value("${StockTickerSOAP.service.validatePayloads}")
    private String validatePayloads;


    @Bean
    public MessageSource messageSource() {
        LOGGER.info("StockTickerSOAPTestConfig: Returning new MessageSource...");
        return new ApplicationMessageSource();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LOGGER.info("StockTickerSOAPTestConfig: Returning new LocalValidatorFactoryBean...");
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }
}
