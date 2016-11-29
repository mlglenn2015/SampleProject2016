package prv.mark.project.stockticker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import prv.mark.project.common.config.CommonDataConfig;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

/**
 * Spring database configuration.
 *
 * @author MLGlenn.
 */
//@Configuration
@ComponentScan(basePackages = {"prv.mark.project"})
@EnableJpaRepositories(basePackages = {"prv.mark.project.common.repository"})
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:/common.properties")
})
//@Profile({"local", "dev", "qa", "stage", "prod"})
@Profile({"notused"})
public class DataConfig  {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);

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
        LOGGER.info("*** JPA logging set to {} level. ***", jpaLogging);

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("prv.mark.project.common.entity");
        AbstractJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        //jpaVendorAdapter.setShowSql(false);
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

    /*@Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource());
        return template;
    }*/
}
