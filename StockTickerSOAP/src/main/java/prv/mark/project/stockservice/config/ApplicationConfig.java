package prv.mark.project.stockservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.service.impl.ApplicationParameterSource;

/**
 * Basic Stock Ticker application configuration.
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@Import({StocksDataConfig.class, WebServiceConfig.class})
/* TODO if you want to add JMX metrics
@EnableMBeanExport
@EnableMBeanExport(defaultDomain = "prv.mark.project", server="jmxServerRuntime",
        registration = RegistrationPolicy.IGNORE_EXISTING) */
@Profile({"local", "dev", "qatest", "staging", "production"})
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);


    @Bean
    public MessageSource messageSource() {
        LOGGER.info("ApplicationConfig: Returning new MessageSource...");
        return new ApplicationMessageSource();
    }

    @Bean
    public ApplicationParameterSource applicationParameterSource() {
        LOGGER.info("ApplicationConfig: Returning new ApplicationParameterSource...");
        return new ApplicationParameterSource();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LOGGER.info("ApplicationConfig: Returning new LocalValidatorFactoryBean...");
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    //TODO @Bean jmxServerRuntime

}
