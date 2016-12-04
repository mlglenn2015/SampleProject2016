package prv.mark.project.stockticker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC configuration for the Stock Ticker application.
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"prv.mark.project.stockticker.web"})
public class StockTickerWebConfig extends WebMvcConfigurerAdapter {
}
