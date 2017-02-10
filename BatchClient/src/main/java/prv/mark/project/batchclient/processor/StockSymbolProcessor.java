package prv.mark.project.batchclient.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.StockSymbol;
import prv.mark.project.common.exception.ApplicationException;
import prv.mark.project.common.service.impl.ApplicationParameterSource;

/**
 * Spring Batch Processor class to process the Stock ticker symbols.
 * <p>
 * Function: This class ....
 * </p>
 * @author mlglenn.
 */
@Component
public class StockSymbolProcessor implements ItemProcessor<StockSymbol, StockSymbol> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSymbolProcessor.class);

    /*@Autowired
    private MyExampleJpaRepositoryService myExampleJpaRepositoryService;*/
    //@Autowired
    //private ApplicationParameterSource applicationParameterSource;

    /**
     * Main method to process ....
     * <p>
     * @param entity {@link StockSymbol} data transfer object
     * @return {@link StockSymbol} data transfer object
     * @throws ApplicationException
     * </p>
     */
    @Override
    public StockSymbol process(final StockSymbol entity) throws ApplicationException {

        LOGGER.debug("*** StockSymbolProcessor.process() entry ***");

        //TODO validate first and fail fast

        LOGGER.debug("+++++++           StockSymbol.id: [{}] +++++++", entity.getId());
        LOGGER.debug("+++++++ StockSymbol.tickerSymbol: [{}] +++++++", entity.getTickerSymbol());

        /* Create a working copy of the input object */
        //StockSymbol newEntity = new StockSymbol();
        //BeanUtils.copyProperties(entity, newEntity);

        //TODO set values

        //String something =
        //        applicationParameterSource.getApplicationParameterValue("sample.app.parameter.key");

        //TODO call services

        //TODO save data

        return entity;
    }
}
