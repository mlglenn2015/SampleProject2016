package prv.mark.project.common.service;

import prv.mark.project.common.entity.StockPriceEntity;
import prv.mark.project.common.entity.StockSymbolEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link StockSymbolEntity} entities.
 *
 * @author mlglenn.
 */
public interface StockSymbolEntityService {

    Optional<StockSymbolEntity> findById(Long id);

    Optional<StockSymbolEntity> findByTickerSymbol(String tickerSymbol);

    List<StockSymbolEntity> findAll();

    StockSymbolEntity save(StockSymbolEntity stockSymbolEntity);
}
