package prv.mark.project.stockticker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.jmx.config.EnableIntegrationMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.management.MBeanServer;

/**
 * JMX Spring Bean configuration.
 *
 * http://docs.spring.io/spring-framework/docs/3.2.3.RELEASE/spring-framework-reference/html/jmx.html
 *
 * @author mlglenn on 12/3/2016.
 */
@Configuration
@EnableIntegrationMBeanExport(defaultDomain="prv.mark.project", server="jmxServerRuntime",
        registration= RegistrationPolicy.IGNORE_EXISTING, managedComponents={"*"})
@Profile({"local","dev","qatest","staging","production"})
public class JmxConfig {

    /**
     * Creates a bean to expose Weblogic properties via JMX.
     *
     * @return {@link JndiObjectFactoryBean}
     */
    @Bean
    public JndiObjectFactoryBean jmxServerRuntime() {
        JndiObjectFactoryBean runtime = new JndiObjectFactoryBean();
        runtime.setJndiName("java:comp/env/jmx/runtime");
        runtime.setProxyInterface(MBeanServer.class);
        return runtime;
    }
}
