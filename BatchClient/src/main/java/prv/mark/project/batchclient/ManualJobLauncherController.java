package prv.mark.project.batchclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import prv.mark.project.batchclient.config.BatchClientDataConfiguration;
import prv.mark.project.batchclient.config.BatchConfig;
import prv.mark.project.common.util.DateUtils;

/**
 * This class is designed to manually launch the application from a web page using spring
 * profile credentials.
 *
 * @author mlglenn
 */
@ContextConfiguration(classes = {BatchConfig.class, BatchClientDataConfiguration.class})
@Controller
public class ManualJobLauncherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManualJobLauncherController.class);

    @Autowired
    BatchApplicationScheduler applicationScheduler;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;


    /**
     * Public method to handle the manual job execution URL request.
     * <p>
     * Spring will execute this method when the URI matches the value in @RequestMapping.
     * After manual job execution, the controller will forward to the designated /success.html
     * page.
     * </p>
     * @return {@link RequestMapping}
     * @throws Exception
     */
    @RequestMapping("/LaunchBatchClientJob.html")
    public RedirectView handle() throws Exception {

        String dateParam = DateUtils.getDateFormattedAsString();
        LOGGER.info("*** EXECUTING MANUAL LAUNCH ***");
        LOGGER.info("Date: {}", dateParam);
        //applicationScheduler.setJobIdParameter("99");
        applicationScheduler.run();
        LOGGER.info("Date: {}", DateUtils.getDateFormattedAsString());
        LOGGER.info("*** FINISHED EXECUTING MANUAL LAUNCH ***");
        return new RedirectView("/success.html", true);
    }

}
