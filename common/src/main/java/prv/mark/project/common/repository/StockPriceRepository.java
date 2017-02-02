package prv.mark.project.common.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.StockPrice;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link prv.mark.project.common.entity.StockPrice} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

    Optional<StockPrice> findById(Long id);

    Optional<StockPrice> findByStockSymbol(String stockSymbol);

    List<prv.mark.project.common.entity.StockPrice> findAll();
}
