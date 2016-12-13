package prv.mark.project.common.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link prv.mark.project.common.entity.StockOrder} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface StockOrderRepository extends JpaRepository<prv.mark.project.common.entity.StockOrder, Long> {

    Optional<prv.mark.project.common.entity.StockOrder> findById(Long id);

    List<prv.mark.project.common.entity.StockOrder> findByOrderDate(Date orderDate);

    List<prv.mark.project.common.entity.StockOrder> findByOrderType(String orderType);

    List<prv.mark.project.common.entity.StockOrder> findByOrderStatus(String orderStatus);

    List<prv.mark.project.common.entity.StockOrder> findAll();
}
