package prv.mark.project.batchclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import prv.mark.project.batchclient.BatchApplicationScheduler;
import prv.mark.project.batchclient.jdbc.StockSymbolRowMapper;
import prv.mark.project.batchclient.listener.BatchClientChunkListener;
import prv.mark.project.batchclient.listener.BatchClientJobListener;
import prv.mark.project.batchclient.listener.BatchClientStepExecutionListener;
import prv.mark.project.batchclient.processor.StockSymbolProcessor;
import prv.mark.project.batchclient.tasklet.PreProcessingTasklet;
//import prv.mark.project.common.entity.ApplicationParameterEntity;
import prv.mark.project.common.entity.StockSymbolEntity;

import javax.sql.DataSource;

/**
 * Spring Batch job configuration class.
 * <p>
 *      Links:
 *      http://projects.spring.io/spring-batch/
 *      https://github.com/codecentric/spring-batch-javaconfig/tree/master/src/main/java/de/codecentric/batch/configuration
 * </p>
 * @author mlglenn
 */
@Configuration
@EnableBatchProcessing //(modular = true) TODO can be used for multiple jobs
//@ImportResource("classpath:META-INF/spring/integration/my-config.xml") //TODO You can import XML configurations too
@Profile({"local", "dev", "qatest", "staging", "production"})
public class BatchConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;    //Required
    @Autowired
    private StepBuilderFactory stepBuilderFactory;  //Required
    @Autowired
    private JobRepository jobRepository;            //Required
    @Autowired
    private JobLauncher jobLauncher;                //Required
    @Autowired
    private JobRegistry jobRegistry;                //Required

    //@Autowired
    //private ApplicationParameterSource applicationParameterSource;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Environment environment;


    /* JOBS */

    /**
     * Typical Spring Batch Job Configuration class. Use the Builder pattern to build a
     * job sequence.
     *
     * @return {@link org.springframework.batch.core}
     */
    @Bean
    public Job mySpringBatchJob() {

        return jobBuilderFactory.get("StocksClientSpringBatchJobName")
                .incrementer(getRunIdIncrementer())
                .listener(batchClientJobListener())
                .start(myHouseKeepingStep())
                .next(myProcessorStep())
                //.next(myCreateOutputFileFromDatabaseStep())
                //.next(myPostExecutionTasksStep())
                .build();
    }


    /* STEPS */

    /**
     * Run Id incrementer for the Spring Batch jobs.
     * @return @{link RunIdIncrementer}
     */
    @Bean
    public RunIdIncrementer getRunIdIncrementer() {
        return new RunIdIncrementer();
    }

    /**
     * Sample Housekeeping step using the Builder pattern.
     * @return {@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step myHouseKeepingStep() {
        return stepBuilderFactory.get("StockClientHouseKeepingStepName")
                .tasklet(getPreProcessingTasklet())
                .listener(batchClientChunkListener())
                .build();
    }

    /**
     * Sample Spring Batch Processor step.
     * @return {@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step myProcessorStep() {
        return stepBuilderFactory.get("MyProcessorStepName")
                .<StockSymbolEntity, StockSymbolEntity>chunk(1)          //TODO change the chunksize as needed
                .reader(myExampleJdbcItemStreamReader())
                .processor(stockSymbolProcessor())
                .listener(batchClientStepExecutionListener())
                .build();
    }

    /**
     * Example step reads the records in a database table and writes to a flat file.
     * @return {@link org.springframework.batch.core.Step}
     */
    /*@Bean
    public Step myCreateOutputFileFromDatabaseStep() {
        if (LOGGER != null) {
            LOGGER.debug("*** SpringBatchJobConfiguration.myCreateOutputFileFromDatabaseStep() ***");
        }
        return stepBuilderFactory.get("MyCreateOutputFileFromDatabaseStepName")
                .<Soldier, Soldier>chunk(1) //TODO change the chunksize as needed
                .reader(myExampleJdbcItemStreamReader())
                .writer(myExampleFlatFileItemWriter())
                .listener(batchClientStepExecutionListener())
                .build();
    }*/

    /**
     * This Spring Batch step configures a file transfer.
     * @return {@link org.springframework.batch.core.Step}
     */
    /*@Bean
    public Step myPostExecutionTasksStep() {
        return stepBuilderFactory.get("MyPostExecutionTasksStepName")
                .tasklet(exampleFileTransmitterTasklet())
                .listener(batchClientStepExecutionListener())
                .build();
    }*/


    /* READERS */

    /**
     * Example Spring Batch ItemStreamReader.
     * @return @{link ItemStreamReader}
     */
    @Bean
    @StepScope
    public ItemStreamReader<StockSymbolEntity> myExampleJdbcItemStreamReader() {
        JdbcCursorItemReader<StockSymbolEntity> jdbcCursorItemReader = new JdbcCursorItemReader<>();
        jdbcCursorItemReader.setRowMapper(stockSymbolRowMapper());
        jdbcCursorItemReader.setDataSource(dataSource);
        jdbcCursorItemReader.setSaveState(false); //necessary when using an indicator in the table to show the record was processed
        /*jdbcCursorItemReader.setSql(Optional.ofNullable(
                applicationParameterSource.getApplicationParameterValue("Example.sql.statement")) //TODO
                .orElse(AppStringUtils.EMPTY));*/
        //jdbcCursorItemReader.setSql("SELECT ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE FROM APPLICATION_PARAMETER WHERE ENABLED = 'Y'");
        jdbcCursorItemReader.setSql("SELECT ID, TICKER_SYMBOL FROM STOCKS.STOCK_SYMBOL");
        return jdbcCursorItemReader;
    }

    /**
     * Example Spring Row mapper class to map row data from the APPLICATION_PARAMETER table.
     * @return @{link RowMapper}
     */
    @Bean
    public RowMapper<StockSymbolEntity> stockSymbolRowMapper() {
        return new StockSymbolRowMapper();
    }


    /* PROCESSORS */

    /**
     * Example Spring Batch Processor bean.
     * @return {@link org.springframework.batch.item.ItemProcessor}
     */
    @Bean
    public ItemProcessor<StockSymbolEntity, StockSymbolEntity> stockSymbolProcessor() {
        return new StockSymbolProcessor();
    }


    /* WRITERS */

    /**
     * Example Spring Batch FlatFileItemWriter class.
     * @return @{link FlatFileItemWriter}
     */
    /*@Bean
    public FlatFileItemWriter<Soldier> myExampleFlatFileItemWriter() {
        FlatFileItemWriter<Soldier> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new FileSystemResource("filename.stored.in.application.properties.or.parameters.table")); //TODO
        flatFileItemWriter.setShouldDeleteIfExists(true);
        flatFileItemWriter.setLineAggregator(myDelimitedLineAggregator());
        flatFileItemWriter.setHeaderCallback(myExampleHeaderCallback()); //Write header
        return flatFileItemWriter;
    }*/

    /**
     * Bean to return an instance of the ExampleHeaderCallback.
     * @return {@link prv.mark.spring.batch.writer.ExampleHeaderCallback}
     */
    /*@Bean
    public ExampleHeaderCallback myExampleHeaderCallback() {
        return new ExampleHeaderCallback();
    }*/

    /**
     * Example Spring Batch Line aggregator.
     * @return @{link DelimitedLineAggregator}
     */
    /*@Bean
    public DelimitedLineAggregator<Soldier> myDelimitedLineAggregator() {
        DelimitedLineAggregator<Soldier> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(AppStringUtils.COMMA);
        aggregator.setFieldExtractor(myBeanWrapperFieldExtractor());
        return aggregator;
    }*/

    /**
     * Example Spring Batch Field extractor.
     * @return @{link BeanWrapperFieldExtractor}
     */
    /*@Bean
    public BeanWrapperFieldExtractor<Soldier> myBeanWrapperFieldExtractor() {
        BeanWrapperFieldExtractor<Soldier> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        beanWrapperFieldExtractor.setNames(AppStringUtils.EXAMPLE_FLAT_FILE_ITEM_WRITER_FIELDS);
        return beanWrapperFieldExtractor;
    }*/


    /* LISTENERS */

    /**
     * Example Spring Batch job execution listener class.
     * @return @{link JobListener}
     */
    @Bean
    public JobExecutionListener batchClientJobListener() {
        return new BatchClientJobListener();
    }

    /**
     * Example Spring Batch StepExecutionListener class.
     * @return @{link StepExecutionListener}
     */
    @Bean
    public StepExecutionListener batchClientStepExecutionListener() {
        return new BatchClientStepExecutionListener();
    }

    /**
     * Example Spring Batch ChunkListener class.
     * @return @{link ChunkListener}
     */
    @Bean
    public ChunkListener batchClientChunkListener() {
        return new BatchClientChunkListener();
    }


    /* MISC */


    /**
     * Example Spring Batch Tasklet.
     * @return {@link Tasklet}
     */
    @Bean
    public Tasklet getPreProcessingTasklet() {
        PreProcessingTasklet tasklet = new PreProcessingTasklet();
        /*exampleTasklet.setSql(Optional.ofNullable(
                applicationParameterSource.getApplicationParameterValue("sql.parameter.name.in.table")) //TODO
                .orElse(AppStringUtils.EMPTY));*/
        tasklet.setSql("SELECT 1 FROM DUAL"); //For Oracle
        return tasklet;
    }

    /**
     * Returns an instance of the Spring Batch ApplicationScheduler.
     * @return {@link prv.mark.project.batchclient.BatchApplicationScheduler}
     */
    @Bean
    public BatchApplicationScheduler applicationScheduler() {
        return new BatchApplicationScheduler();
    }
}
