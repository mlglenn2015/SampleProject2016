package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.OrderStatusEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link OrderStatusEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {

    Optional<OrderStatusEntity> findById(Long id);

    @Query("select o from OrderStatusEntity o where o.orderStatus = ?1")
    Optional<OrderStatusEntity> findByOrderStatus(String orderStatus);

    List<OrderStatusEntity> findAll();
}
