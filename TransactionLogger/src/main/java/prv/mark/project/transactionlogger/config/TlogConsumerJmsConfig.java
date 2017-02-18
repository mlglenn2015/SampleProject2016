package prv.mark.project.transactionlogger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import prv.mark.project.transactionlogger.jms.TransactionLogListenerMDB;

/**
 * Spring Bean base configuration for JMS components.
 *
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jms.html
 * https://spring.io/guides/gs/messaging-jms/
 *
 * @author mlglenn on 2/18/2017.
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
@ImportResource(value = {"classpath:/TransactionLoggerJmsConfig.xml"})
@Profile({"local", "dev", "qatest", "staging", "production"})
public class TlogConsumerJmsConfig {

    private static final Logger logger = LoggerFactory.getLogger(TlogConsumerJmsConfig.class);


    @Bean
    public TransactionLogListenerMDB transactionLogListener() {
        return new TransactionLogListenerMDB();
    }
}
