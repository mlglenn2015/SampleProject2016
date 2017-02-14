package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.StockSymbolEntity;
import prv.mark.project.common.repository.StockSymbolRepository;
import prv.mark.project.common.service.StockSymbolEntityService;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link StockSymbolEntityService} interface.
 *
 * @author mlglenn.
 */
@Service
public class StockSymbolEntityServiceImpl implements StockSymbolEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSymbolEntityServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private StockSymbolRepository stockSymbolRepository;

    @Override
    public Optional<StockSymbolEntity> findById(final Long id) {
        LOGGER.debug("StockSymbolEntityServiceImpl.findById({})", id);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return Optional.ofNullable(stockSymbolRepository.findById(id)).get();
        //return Optional.ofNullable(stockPriceRepository.findOne(id));
    }

    @Override
    public Optional<StockSymbolEntity> findByTickerSymbol(final String tickerSymbol) {
        LOGGER.debug("StockSymbolEntityServiceImpl.findByTickerSymbol({})", tickerSymbol);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockSymbolRepository.findByTickerSymbol(tickerSymbol);
    }

    @Override
    public List<StockSymbolEntity> findAll() {
        LOGGER.debug("StockSymbolEntityServiceImpl.findAll()");
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockSymbolRepository.findAll();
    }

    @Override
    @Transactional
    public StockSymbolEntity save(StockSymbolEntity stockSymbolEntity) {
        LOGGER.debug("StockSymbolEntityServiceImpl.save({})", stockSymbolEntity.toString());
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockSymbolRepository.saveAndFlush(stockSymbolEntity);
    }
}
