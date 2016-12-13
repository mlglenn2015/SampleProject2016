package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.StockPrice;
import prv.mark.project.common.repository.StockPriceRepository;
import prv.mark.project.common.service.StockPriceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link StockPriceService} interface.
 *
 * @author mlglenn.
 */
@Service
public class StockPriceServiceImpl implements StockPriceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    //@Autowired
    //private StockPriceRepository stockPriceRepository; TODO

    @Override
    public Optional<StockPrice> findById(final Long id) {
        LOGGER.debug("StockPriceServiceImpl.findById({})", id);
        //return Optional.ofNullable(ordersRepository.findById(id)).get();

        //return Optional.ofNullable(stockPriceRepository.findOne(id)); TODO
        return Optional.of(new StockPrice());
    }

    @Override
    public Optional<StockPrice> findByStockSymbol(final String stockSymbol) {
        LOGGER.debug("StockPriceServiceImpl.findByStockSymbol({})", stockSymbol);

        //Optional<StockPrice> stockPrice = stockPriceRepository.findByStockSymbol(stockSymbol); TODO
        //if (stockPrice.get() == null) {
            return null;
        //}
        //return stockPrice;
    }

    @Override
    public List<StockPrice> findAll() {
        LOGGER.debug("StockPriceServiceImpl.findAll()");
        //return stockPriceRepository.findAll(); TODO
        return nullList();
    }

    @Override
    @Transactional
    public StockPrice save(StockPrice stockPrice) {
        LOGGER.debug("StockPriceServiceImpl.save({})", stockPrice.toString());
        //return stockPriceRepository.saveAndFlush(stockPrice); TODO
        return new StockPrice();
    }

    private List<StockPrice> nullList() {
        List<StockPrice> list = new ArrayList<>();
        list.add(new StockPrice());
        return list;
    }
}
