package prv.mark.project.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

/**
 * Spring database configuration.
 *
 * @author mlglenn.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@EnableJpaRepositories(basePackages = {"prv.mark.project.common.repository"})
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableJpaAuditing
@PropertySource("classpath:common.properties")
@Profile({"local", "dev", "qatest", "staging", "production"})
public class CommonDataConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDataConfig.class);

    @Value("${spring.jpa.show-sql}")
    private String showSql;
    @Value("${application.id}")
    private String applicationId;
    @Value("${application.jndi.datasource}")
    private String applicationJndiDataSource; //private static final String DS_JNDI = "jdbc/stockTickerDataSource";

    @Autowired
    private Environment env;



    /*@Bean
    public MessageSource applicationMessageSource() {
        return new ApplicationMessageSource();
    }

    @Bean
    public ApplicationParameterSource applicationParameterSource() {
        return new ApplicationParameterSource();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(applicationMessageSource());
        return validator;
    }*/

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // EclipseLink logging.  Default to SEVERE if not set as a system property or in a property file.
        String jpaLogging = Optional.ofNullable(env.getProperty("app.jpa.logging")).orElse("SEVERE");
        LOGGER.info("*** JPA logging set to {} level. ***", jpaLogging);

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("prv.mark.project.common.entity");
        AbstractJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        //jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("eclipselink.weaving", "false");
        jpaProperties.setProperty("eclipselink.logging.level", jpaLogging);

        emfb.setJpaProperties(jpaProperties);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);

        return emfb;
    }

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        LOGGER.info("Application Id:{}", applicationId);
        LOGGER.info("Configuring applicationJndiDataSource:{}", applicationJndiDataSource);
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(applicationJndiDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        LOGGER.info("Returning new JtaTransactionManager...");
        return new JtaTransactionManager();
    }

}
