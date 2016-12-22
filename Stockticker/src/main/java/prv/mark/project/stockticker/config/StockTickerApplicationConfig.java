package prv.mark.project.stockticker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import prv.mark.project.common.config.CommonDataConfig;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.common.service.impl.ApplicationParameterSource;

/**
 * Basic Stock Ticker application configuration.
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@Import(CommonDataConfig.class)
//@EnableMBeanExport
//@EnableMBeanExport(defaultDomain = "prv.mark.project", server="jmxServerRuntime",
//        registration = RegistrationPolicy.IGNORE_EXISTING)
@Profile({"local", "dev", "qatest", "staging", "production"})
public class StockTickerApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerApplicationConfig.class);

    @Bean
    public MessageSource applicationMessageSource() {
        LOGGER.info("StockTickerApplicationConfig: Returning new ApplicationMessageSource...");
        return new ApplicationMessageSource();
    }

    @Bean
    public ApplicationParameterSource applicationParameterSource() {
        LOGGER.info("StockTickerApplicationConfig: Returning new ApplicationParameterSource...");
        return new ApplicationParameterSource();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LOGGER.info("StockTickerApplicationConfig: Returning new LocalValidatorFactoryBean...");
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(applicationMessageSource());
        return validator;
    }

    //TODO @Bean jmxServerRuntime
}
