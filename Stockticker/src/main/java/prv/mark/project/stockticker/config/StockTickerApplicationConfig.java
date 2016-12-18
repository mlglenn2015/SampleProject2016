package prv.mark.project.stockticker.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
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
@EnableMBeanExport
@Profile({"local", "dev", "qatest", "staging", "production"})
public class StockTickerApplicationConfig {

    /*@Bean defined in CommonDataConfig
    public ApplicationMessageSource applicationMessageSource() {
        return new ApplicationMessageSource();
    }

    @Bean defined in CommonDataConfig
    public ApplicationParameterSource applicationParameterSource() {
        return new ApplicationParameterSource();
    }*/

    /*@Bean defined in CommonDataConfig
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        //validator.setValidationMessageSource(applicationMessageSource()); TODO
        return validator;
    }*/
}
