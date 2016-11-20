package prv.mark.project.stockticker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Import({TestDataConfig.class, TestWebServicesConfig.class})
@ComponentScan(basePackages = {"prv.mark.project"})
//@PropertySources(value = {
//        @PropertySource("classpath:StockTicker-TEST.properties")
//})
@Profile("test")
public class StockTickerTestConfig {

    /*@Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        //validatorFactory.setValidationMessageSource(messageSource());
        return validatorFactory;
    }*/

    /*@Bean
    public MessageSource messageSource() {
        return new MessageSource();
    }*/

}
