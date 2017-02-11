package prv.mark.project.common.service;

import prv.mark.project.common.entity.StockOrderEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link StockOrderEntity} entities.
 *
 * @author mlglenn.
 */
public interface StockOrderEntityService {

    Optional<StockOrderEntity> findById(Long id);

    //List<prv.mark.project.common.entity.StockOrderEntity> findByOrderDate(Date orderDate);

    //List<prv.mark.project.common.entity.StockOrderEntity> findByOrderType(String orderType);

    List<StockOrderEntity> findByOrderStatus(String orderStatus);

    List<StockOrderEntity> findAll();

    StockOrderEntity save(StockOrderEntity order);
}
