package prv.mark.project.batchclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prv.mark.project.common.exception.ExceptionRouter;
import prv.mark.project.common.util.NumberUtils;

import java.util.Date;

/**
 * This is the Spring Batch Scheduler class.
 *
 * @author mlglenn
 */
@Component
public class BatchApplicationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchApplicationScheduler.class);
    public static final String JOB_ALREADY_RUNNING = "JOB ALREADY RUNNING";
    public static final String JOB_RESTART_FAILURE = "JOB RESTART FAILURE";
    public static final String JOB_ALREADY_COMPLETE = "JOB ALREADY COMPLETE";
    public static final String JOB_PARAMETERS_ARE_INVALID = "JOB PARAMETERS ARE INVALID";
    public static final String DUPLICATE_JOB_INSTANCE = "DUPLICATE JOB INSTANCE";
    public static final String SQL_EXCEPTION_CAUGHT = "SQL EXCEPTION CAUGHT";
    public static final String HSQL_EXCEPTION_CAUGHT = "HSQL EXCEPTION CAUGHT";
    public static final String ILLEGAL_STATE_EXCEPTION_CAUGHT = "ILLEGAL STATE EXCEPTION CAUGHT";
    public static final String NULL_POINTER_EXCEPTION_CAUGHT = "NULL POINTER EXCEPTION CAUGHT";
    public static final String GENERIC_EXCEPTION_CAUGHT = "GENERIC EXCEPTION CAUGHT";

    @Value("${app.properties.resource}")
    private String propertiesFileName;
    @Value("${spring.profiles.active}")
    private String springProfile;
    @Value("${spring.batch.app.jobid.addlong}")
    private String jobIdParameter;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;


    /**
     * This run() method executes according to the @Scheduled annotation.
     * Example: Scheduled(cron=0 15,45 * * * MON-FRI")
     */
    @Scheduled(cron = "${app.scheduler.crontab}")
    public final void run() {

        LOGGER.debug("");
        LOGGER.debug("******************************************************");
        LOGGER.debug("*** BatchApplicationScheduler.run() entry               ***");

        try {
            String dateParam = new Date().toString(); //we want this date string as a job parm

            LOGGER.debug("dateParam         : " + dateParam);
            LOGGER.debug("jobIdParameter    : " + getJobIdParameter());
            LOGGER.debug("propertiesFileName: " + getPropertiesFileName());
            LOGGER.debug("springProfile     : " + getSpringProfile());
            LOGGER.debug("Scheduling Job    : {}", getJob().getName());
            LOGGER.debug("Job Launcher      : " + getJobLauncher().toString());

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            JobParameters jobParameters;
            jobParametersBuilder.addString("date", dateParam);
            jobParametersBuilder.addLong("id", NumberUtils.toLong(jobIdParameter));
            jobParameters = jobParametersBuilder.toJobParameters();

            JobExecution execution;
            execution = jobLauncher.run(job, jobParameters);
            LOGGER.info("Application Execution Status: {}", execution.getStatus());
            LOGGER.debug("******************************************************");

            /* Various types of exceptions need to be caught and handled
             This would be a good place for an exception handler class
             */
        } catch (JobExecutionAlreadyRunningException e) {
            LOGGER.error(JOB_ALREADY_RUNNING);
            e.printStackTrace();

        } catch (JobRestartException e) {
            routeException(JOB_RESTART_FAILURE, e);

        } catch (JobInstanceAlreadyCompleteException e) {
            LOGGER.error(JOB_ALREADY_COMPLETE);
            e.printStackTrace();

        } catch (JobParametersInvalidException e) {
            routeException(JOB_PARAMETERS_ARE_INVALID, e);

        } catch (DuplicateKeyException e) {
            routeException(DUPLICATE_JOB_INSTANCE, e);

        } catch (BadSqlGrammarException e) {
            routeException(SQL_EXCEPTION_CAUGHT, e);

        } catch (IllegalStateException e) {
            routeException(ILLEGAL_STATE_EXCEPTION_CAUGHT, e);

        } catch (NullPointerException e) {
            routeException(NULL_POINTER_EXCEPTION_CAUGHT, e);

        } catch (Exception e) {
            routeException(GENERIC_EXCEPTION_CAUGHT, e);
        }
    }

    public String getPropertiesFileName() {
        return propertiesFileName;
    }

    public String getSpringProfile() {
        return springProfile;
    }

    public String getJobIdParameter() {
        return jobIdParameter;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public Job getJob() {
        return job;
    }


    private void routeException(String logmsg, Exception e) {
        ExceptionRouter.logAndThrowApplicationException(LOGGER, logmsg, e);
    }


}
