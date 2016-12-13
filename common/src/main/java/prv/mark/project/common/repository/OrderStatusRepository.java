package prv.mark.project.common.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link prv.mark.project.common.entity.OrderStatus} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Optional<OrderStatus> findById(Long id);

    @Query("select o from OrderStatus o where o.orderStatus = ?1")
    Optional<prv.mark.project.common.entity.OrderStatus> findByOrderStatus(String orderStatus);

    List<OrderStatus> findAll();
}
