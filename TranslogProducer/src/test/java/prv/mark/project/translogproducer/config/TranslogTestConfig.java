package prv.mark.project.translogproducer.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
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
public class TranslogTestConfig {

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
