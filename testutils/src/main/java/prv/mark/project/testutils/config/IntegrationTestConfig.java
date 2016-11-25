package prv.mark.project.testutils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("integration")
public class IntegrationTestConfig extends TestWebServicesConfig {

}
