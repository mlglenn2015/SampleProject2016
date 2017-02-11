package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.OrderTypesEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link OrderTypesEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface OrderTypesRepository extends JpaRepository<OrderTypesEntity, Long> {

    @Cacheable("ordertypes")
    Optional<OrderTypesEntity> findById(Long id);

    @Cacheable("ordertypes")
    @Query("select o from OrderTypesEntity o where o.orderType = ?1")
    Optional<OrderTypesEntity> findByOrderType(String orderType);

    @Cacheable("ordertypes")
    List<OrderTypesEntity> findAll();
}
