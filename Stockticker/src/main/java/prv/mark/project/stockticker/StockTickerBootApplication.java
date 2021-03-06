package prv.mark.project.stockticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the Spring Boot Application class.
 * <p>
 * This code sets up the application context from Spring Boot.
 * </p>
 * @author mlglenn on 12/13/2016.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"prv.mark.project"})
public class StockTickerBootApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockTickerBootApplication.class);

    /**
     * The starting point for the Spring Boot application.
     *
     * @param args {@link String[]}
     */
    public static void main(String[] args) {

        LOGGER.debug("*** StockTickerBootApplication.main() executing ***");

        ApplicationContext ctx = SpringApplication.run(StockTickerBootApplication.class, args);
    }
}
