package prv.mark.project.common.service;

import org.springframework.context.annotation.Profile;
import prv.mark.project.common.entity.StockPrice;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link StockPrice} entities.
 *
 * @author mlglenn.
 */
public interface StockPriceService {

    Optional<StockPrice> findById(Long id);

    Optional<StockPrice> findByStockSymbol(String stockSymbol);

    List<StockPrice> findAll();

    StockPrice save(StockPrice stockPrice);
}
