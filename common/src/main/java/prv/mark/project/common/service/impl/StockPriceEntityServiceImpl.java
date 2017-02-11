package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.StockPriceEntity;
import prv.mark.project.common.repository.StockPriceRepository;
import prv.mark.project.common.service.StockPriceEntityService;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link StockPriceEntityService} interface.
 *
 * @author mlglenn.
 */
@Service
public class StockPriceEntityServiceImpl implements StockPriceEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceEntityServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Override
    public Optional<StockPriceEntity> findById(final Long id) {
        LOGGER.debug("StockPriceEntityServiceImpl.findById({})", id);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return Optional.ofNullable(stockPriceRepository.findById(id)).get();
        //return Optional.ofNullable(stockPriceRepository.findOne(id));
    }

    @Override
    public Optional<StockPriceEntity> findByStockSymbol(final String stockSymbol) {
        LOGGER.debug("StockPriceEntityServiceImpl.findByStockSymbol({})", stockSymbol);
        LOGGER.debug("ENVIRONMENT:{}", env);
        Optional<StockPriceEntity> stockPrice = stockPriceRepository.findByStockSymbol(stockSymbol);
        if (stockPrice.get() == null) {
            return null;
        }
        return stockPrice;
    }

    @Override
    public List<StockPriceEntity> findAll() {
        LOGGER.debug("StockPriceEntityServiceImpl.findAll()");
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockPriceRepository.findAll();
    }

    @Override
    @Transactional
    public StockPriceEntity save(StockPriceEntity stockPriceEntity) {
        LOGGER.debug("StockPriceEntityServiceImpl.save({})", stockPriceEntity.toString());
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockPriceRepository.saveAndFlush(stockPriceEntity);
    }


    /*private List<StockPriceEntity> nullList() {
        List<StockPriceEntity> list = new ArrayList<>();
        list.add(new StockPriceEntity());
        return list;
    }*/
}
