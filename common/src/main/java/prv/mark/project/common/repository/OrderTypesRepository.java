package prv.mark.project.common.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.OrderTypes;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link prv.mark.project.common.entity.OrderTypes} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface OrderTypesRepository extends JpaRepository<OrderTypes, Long> {

    Optional<OrderTypes> findById(Long id);

    @Query("select o from OrderTypes o where o.orderType = ?1")
    Optional<prv.mark.project.common.entity.OrderTypes> findByOrderType(String orderType);

    List<OrderTypes> findAll();
}
