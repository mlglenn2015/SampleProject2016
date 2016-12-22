package prv.mark.project.stockticker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC configuration for the Stock Ticker application.
 *
 * http://stackoverflow.com/questions/21516683/java-lang-illegalargumentexception-a-servletcontext-is-required-to-configure-de
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration //TODO Error creating bean with name 'defaultServletHandlerMapping' defined in class path resource
/*
One of your @Configuration classes is obviously annotated with @EnableWebMvc. That's how
DelegatingWebMvcConfiguration ends up in your stack trace, since it is imported by @EnableWebMvc.

So although you think you don't need a WebApplicationContext (and hence a ServletContext), you
in fact do need it simply because you are loading an application context with @EnableWebMvc.

You have two options:

    Compose the configuration classes for your integration test so that you are not including the
    web-related configuration (i.e., the @Configuration class(es) annotated with @EnableWebMvc).
    Annotate your test class with @WebAppConfiguration as suggested in other comments above.

Regards,
Sam (author of the Spring TestContext Framework)
 */
@EnableWebMvc
@ComponentScan(basePackages = {"prv.mark.project.stockticker"})
@Profile({"local", "dev", "qatest", "staging", "production"})
public class StockTickerWebConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerWebConfig.class);
}
