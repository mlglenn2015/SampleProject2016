package prv.mark.project.stockticker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring database configuration.
 *
 * @author MLGlenn.
 */
//@Configuration
//@ComponentScan(basePackages = {"prv.mark.project.stockticker"})
//@EnableJpaRepositories(basePackages = {"prv.mark.project.common.repository"})
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:/common.properties")
})
@Profile({"local", "dev", "qa", "stage", "prod"})
public class DataConfig { //extends JpaBaseConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    //private static final String DS_JNDI = "jdbc/stockTickerDataSource";

    @Autowired
    private Environment env;

    /*@Autowired
    private PlatformTransactionManager transactionManager;*/


    /* Used with JpaBaseConfiguration
    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected java.util.Map<String, Object> getVendorProperties() {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("eclipselink.weaving", "false");
        jpaProperties.put("eclipselink.logging.level", "SEVERE");
        jpaProperties.put("eclipselink.persistence-context.flush-mode", "AUTO");
        return jpaProperties;

    }*/


    /*@Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName("STOCKTICKER")
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }*/

    /*@Bean(destroyMethod = "")
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(DS_JNDI);
    }*/

    /*@Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource());
        return template;
    }*/

    /*@Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }*/

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // EclipseLink logging.  Default to SEVERE if not set as a system property or in a property file.
        String jpaLogging = Optional.ofNullable(env.getProperty("app.jpa.logging")).orElse("SEVERE");
        LOGGER.info("*** JPA logging set to {} level. ***", jpaLogging);

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("prv.mark.project.common.entity");
        //emfb.setJpaDialect(new EclipseLinkJpaDialect());
        AbstractJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        jpaVendorAdapter.setGenerateDdl(false);
        //jpaVendorAdapter.setDatabase(Database.HSQL);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("eclipselink.weaving", "false");
        jpaProperties.setProperty("eclipselink.logging.level", jpaLogging);

        emfb.setJpaProperties(jpaProperties);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        //emfb.afterPropertiesSet();

        return emfb;
    }*/

    //@Bean
    //public PlatformTransactionManager transactionManager() {
        /*JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;*/

    //    return new JtaTransactionManager();
    //}
}
