package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.StockPriceEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link StockPriceEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface StockPriceRepository extends JpaRepository<StockPriceEntity, Long> {

    Optional<StockPriceEntity> findById(Long id);

    Optional<StockPriceEntity> findByStockSymbol(String stockSymbol);

    List<StockPriceEntity> findAll();
}
