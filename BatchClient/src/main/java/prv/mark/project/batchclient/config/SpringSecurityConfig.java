package prv.mark.project.batchclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration for manual invocations via URLs.
 *
 * @author mlglenn
 */
@Configuration
@EnableWebSecurity
@Profile({"local", "dev", "qatest", "staging", "production"})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Value("${spring.user.name}")
    private String springUser;
    @Value("${spring.user.password}")
    private String springPasswd;


    /**
     * Sets the user authorization to configured values in the application-<profile>.properties file.
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(springUser).password(springPasswd).roles("USER");
    }
}
