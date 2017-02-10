package prv.mark.project.batchclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring configuration class for JPA entities.
 * <p>
 * http://projects.spring.io/spring-batch/
 * </p>
 * @author mlglenn
 */
@Configuration
@Profile({"local", "dev", "qatest", "staging", "production"})
public class BatchClientDataConfiguration {

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    @Autowired
    private DataSource dataSource;


    /**
     * Public method to return an instance of {@link LocalContainerEntityManagerFactoryBean}.
     * <p>
     * LocalContainerEntityManagerFactoryBean is a FactoryBean that creates a JPA EntityManagerFactory
     * according to JPA's standard container bootstrap contract.
     * </p>
     * <p>
     * See https://docs.spring.io/spring/docs/3.0.x/javadoc-api/org/springframework/orm/jpa/LocalContainerEntityManagerFactoryBean.html
     * </p>
     * @return {@link LocalContainerEntityManagerFactoryBean}
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("prv.mark.project.common.entity");
        localContainerEntityManagerFactoryBean.setJpaDialect(new EclipseLinkJpaDialect());
        AbstractJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        jpaVendorAdapter.setGenerateDdl(false);
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("eclipselink.weaving", "false");
        jpaProperties.setProperty("eclipselink.logging.level", "SEVERE");
        jpaProperties.setProperty("eclipselink.persistence-context.flush-mode", "AUTO");
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.afterPropertiesSet();
        return localContainerEntityManagerFactoryBean;
    }

}
