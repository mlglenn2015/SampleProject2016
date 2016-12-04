package prv.mark.project.stockticker.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import prv.mark.project.common.config.CommonDataConfig;
import prv.mark.project.common.service.impl.ApplicationMessageSource;

/**
 * Basic Stock Ticker application configuration.
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@Import(CommonDataConfig.class)
@EnableMBeanExport
public class ApplicationConfig {

    @Bean
    public MessageSource messageSource() {
        return new ApplicationMessageSource();
    }

}
