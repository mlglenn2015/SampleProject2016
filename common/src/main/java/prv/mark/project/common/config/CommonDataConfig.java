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
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
 * @author MLGlenn.
 */
@Configuration
@ComponentScan(basePackages = {"prv.mark.project.stockticker"})
@EnableJpaRepositories(basePackages = {"prv.mark.project.common.repository"})
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:/common.properties")
})
@Profile({"local", "dev", "qa", "stage", "prod"})
public class CommonDataConfig {

    private static final Logger logger = LoggerFactory.getLogger(CommonDataConfig.class);

    @Value("${spring.jpa.show-sql}")
    private String showSql;
    @Value("${application.id}")
    private String applicationId;
    @Value("${application.jndi.datasource}")
    private String applicationJndiDataSource;
    //private static final String DS_JNDI = "jdbc/stockTickerDataSource";

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // EclipseLink logging.  Default to SEVERE if not set as a system property or in a property file.
        String jpaLogging = Optional.ofNullable(env.getProperty("app.jpa.logging")).orElse("SEVERE");
        logger.info("*** JPA logging set to {} level. ***", jpaLogging);

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("prv.mark.project.common.entity");
        AbstractJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        //jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        jpaVendorAdapter.setShowSql(false);
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
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(applicationJndiDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JtaTransactionManager();
    }

}
