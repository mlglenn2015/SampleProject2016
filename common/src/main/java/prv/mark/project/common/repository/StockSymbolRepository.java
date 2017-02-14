package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.StockPriceEntity;
import prv.mark.project.common.entity.StockSymbolEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link StockSymbolRepository} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface StockSymbolRepository extends JpaRepository<StockSymbolEntity, Long> {

    Optional<StockSymbolEntity> findById(Long id);

    Optional<StockSymbolEntity> findByTickerSymbol(String tickerSymbol);

    List<StockSymbolEntity> findAll();

}
