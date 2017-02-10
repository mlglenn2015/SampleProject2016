package prv.mark.project.batchclient.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Spring Batch Tasklet example.
 * <p>
 * See https://examples.javacodegeeks.com/enterprise-java/spring/spring-batch-tasklet-example/
 * </p>
 * @author mlglenn
 */
@Component
public class PreProcessingTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreProcessingTasklet.class);
    private String sql;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Sample method to execute an SQL statement as determined by instance variable sql.
     * @param contribution {@link org.springframework.batch.core.StepContribution} contribution
     * @param chunkContext {@link org.springframework.batch.core.scope.context.ChunkContext} chunkContext
     * @return {@link org.springframework.batch.repeat.RepeatStatus}
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
            throws Exception {

        LOGGER.debug("*** PreProcessingTasklet.execute() ***");
        LOGGER.debug("SQL: {}", getSql());
        new JdbcTemplate(dataSource).execute(getSql());
        return RepeatStatus.FINISHED;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(final String sql) {
        this.sql = sql;
    }
}
