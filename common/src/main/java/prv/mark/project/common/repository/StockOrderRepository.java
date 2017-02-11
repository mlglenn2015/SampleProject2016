package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.StockOrderEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link StockOrderEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface StockOrderRepository extends JpaRepository<StockOrderEntity, Long> {

    Optional<StockOrderEntity> findById(Long id);

    List<StockOrderEntity> findByOrderDate(Date orderDate);

    List<StockOrderEntity> findByOrderType(String orderType);

    List<StockOrderEntity> findByOrderStatus(String orderStatus);

    List<StockOrderEntity> findAll();
}
