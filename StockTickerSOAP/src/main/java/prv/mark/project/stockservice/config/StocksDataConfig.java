package prv.mark.project.stockservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
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
@EnableJpaRepositories(basePackages = {"prv.mark.project.common.repository"},
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement
//@EnableJpaRepositories("prv.mark.project.common.repository")
@EntityScan("prv.mark.project.common.entity")
@Profile({"local", "dev", "qatest", "staging", "production"})
public class StocksDataConfig {

    /*
    @EnableSpringDataWebSupport TODO cleanup
@EnableJpaAuditing
@PropertySource("classpath:common.properties")
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StocksDataConfig.class);

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    @Value("${application.id}")
    private String applicationId;

    @Value("${application.jndi.datasource}")
    private String applicationJndiDataSource; //private static final String DS_JNDI = "jdbc/stockTickerDataSource";  TODO replace dataSource

    @Value("${key.store.password}")
    private String keyStorePassword;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;


    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LOGGER.info("StocksDataConfig: Returning new LocalContainerEntityManagerFactoryBean...");
        // EclipseLink logging.  Default to SEVERE if not set as a system property or in a property file.
        String jpaLogging = Optional.ofNullable(env.getProperty("app.jpa.logging")).orElse("SEVERE");
        LOGGER.info("*** JPA logging set to {} level. ***", jpaLogging);

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource()); //TODO
        //emfb.setDataSource(dataSource); //TODO
        emfb.setPackagesToScan("prv.mark.project.common.entity");
        emfb.setJpaDialect(new EclipseLinkJpaDialect());
        AbstractJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        //jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("eclipselink.weaving", "false");
        jpaProperties.setProperty("eclipselink.logging.level", jpaLogging);
        //jpaProperties.setProperty("eclipselink.logging.level", "SEVERE");
        jpaProperties.setProperty("eclipselink.persistence-context.flush-mode", "AUTO");

        emfb.setJpaProperties(jpaProperties);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.afterPropertiesSet();
        return emfb;
    }

    @Bean(destroyMethod = "") //TODO
    public DataSource dataSource() {
        LOGGER.info("StocksDataConfig: Returning new DataSource...");
        LOGGER.info("Application Id:{}", applicationId);
        LOGGER.info("Configuring applicationJndiDataSource:{}", applicationJndiDataSource);
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(applicationJndiDataSource);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        LOGGER.info("StocksDataConfig: Returning new JtaTransactionManager...");
        return new JtaTransactionManager();
    }

}
