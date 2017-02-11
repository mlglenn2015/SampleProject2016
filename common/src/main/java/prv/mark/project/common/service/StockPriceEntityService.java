package prv.mark.project.common.service;

import prv.mark.project.common.entity.StockPriceEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link StockPriceEntity} entities.
 *
 * @author mlglenn.
 */
public interface StockPriceEntityService {

    Optional<StockPriceEntity> findById(Long id);

    Optional<StockPriceEntity> findByStockSymbol(String stockSymbol);

    List<StockPriceEntity> findAll();

    StockPriceEntity save(StockPriceEntity stockPriceEntity);
}
