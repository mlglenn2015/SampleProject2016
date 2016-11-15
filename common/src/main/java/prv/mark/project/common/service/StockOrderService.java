package prv.mark.project.common.service;

import prv.mark.project.common.entity.StockOrder;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link StockOrder} entities.
 *
 * @author mlglenn.
 */
public interface StockOrderService {

    Optional<StockOrder> findById(Long id);

    Optional<StockOrder> findByStockSymbol(String symbol);

    List<StockOrder> findAll();

    StockOrder save(StockOrder orders);
}
