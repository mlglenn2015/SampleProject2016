package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link prv.mark.project.common.entity.StockOrder} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface StockOrderRepository extends JpaRepository<prv.mark.project.common.entity.StockOrder, Long> {

    Optional<prv.mark.project.common.entity.StockOrder> findById(Long id);

    Optional<prv.mark.project.common.entity.StockOrder> findByStockSymbol(String symbol);

    List<prv.mark.project.common.entity.StockOrder> findAll();
}
