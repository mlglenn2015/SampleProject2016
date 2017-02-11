package prv.mark.project.batchclient.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.StockSymbolEntity;
import prv.mark.project.common.exception.ApplicationException;

/**
 * Spring Batch Processor class to process the Stock ticker symbols.
 * <p>
 * Function: This class ....
 * </p>
 * @author mlglenn.
 */
@Component
public class StockSymbolProcessor implements ItemProcessor<StockSymbolEntity, StockSymbolEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSymbolProcessor.class);

    /*@Autowired
    private MyExampleJpaRepositoryService myExampleJpaRepositoryService;*/
    //@Autowired
    //private ApplicationParameterSource applicationParameterSource;

    /**
     * Main method to process ....
     * <p>
     * @param entity {@link StockSymbolEntity} data transfer object
     * @return {@link StockSymbolEntity} data transfer object
     * @throws ApplicationException
     * </p>
     */
    @Override
    public StockSymbolEntity process(final StockSymbolEntity entity) throws ApplicationException {

        LOGGER.debug("*** StockSymbolProcessor.process() entry ***");

        //TODO validate first and fail fast

        LOGGER.debug("+++++++           StockSymbolEntity.id: [{}] +++++++", entity.getId());
        LOGGER.debug("+++++++ StockSymbolEntity.tickerSymbol: [{}] +++++++", entity.getTickerSymbol());

        /* Create a working copy of the input object */
        //StockSymbolEntity newEntity = new StockSymbolEntity();
        //BeanUtils.copyProperties(entity, newEntity);

        //TODO set values

        //String something =
        //        applicationParameterSource.getApplicationParameterValue("sample.app.parameter.key");

        //TODO call services

        //TODO save data

        return entity;
    }
}
