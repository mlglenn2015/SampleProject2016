package prv.mark.project.common.service;

import prv.mark.project.common.entity.StockOrder;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link StockOrder} entities.
 *
 * @author mlglenn.
 */
public interface StockOrderService {

    Optional<prv.mark.project.common.entity.StockOrder> findById(Long id);

    //List<prv.mark.project.common.entity.StockOrder> findByOrderDate(Date orderDate);

    //List<prv.mark.project.common.entity.StockOrder> findByOrderType(String orderType);

    List<prv.mark.project.common.entity.StockOrder> findByOrderStatus(String orderStatus);

    List<prv.mark.project.common.entity.StockOrder> findAll();

    StockOrder save(StockOrder order);
}
