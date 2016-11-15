package prv.mark.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
//@Import({TestDataConfig.class})
@ComponentScan(basePackages = {"prv.mark.project.common.util"})
@PropertySources(value = {
        @PropertySource("classpath:common.properties")
})
@Profile("test")
public class UtilsTestConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        //validatorFactory.setValidationMessageSource(new ResourceBundleMessageSource());
        return validatorFactory;
    }

    /*@Bean
    public MessageSource messageSource() {
        return new NBIMessageSource();
    }*/

    /*@Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("common.properties"));
        return configurer;
    }*/

}
