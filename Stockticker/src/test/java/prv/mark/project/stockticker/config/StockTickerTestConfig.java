package prv.mark.project.stockticker.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import prv.mark.project.common.service.impl.ApplicationMessageSource;
import prv.mark.project.testutils.config.TestDataConfig;
import prv.mark.project.testutils.config.TestWebServicesConfig;

@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@Import({TestDataConfig.class, TestWebServicesConfig.class})
@PropertySources(value = {
        @PropertySource("classpath:StockTicker-TEST.properties")
})
@Profile("test")
public class StockTickerTestConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        validatorFactory.setValidationMessageSource(messageSource());
        return validatorFactory;
    }

    @Bean
    public MessageSource messageSource() {
        return new ApplicationMessageSource();
    }

}
