package prv.mark.project.common.service.impl;

import prv.mark.project.common.service.StockOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Default implementation of the {@link StockOrderService} interface.
 *
 * @author mlglenn.
 */
@Service
public class StockOrderServiceImpl { //implements OrdersService { TODO finish

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    //@Autowired
    //private OrdersRepository ordersRepository;

    /*@Override
    public Optional<Orders> findById(final Long id) {
        LOGGER.debug("OrdersServiceImpl.findById({})", id);
        //return Optional.ofNullable(ordersRepository.findById(id)).get();
        return Optional.ofNullable(ordersRepository.findOne(id));
    }

    @Override
    public List<Orders> findByServiceOrderId(final String serviceOrderId) {
        LOGGER.debug("OrdersServiceImpl.findByServiceOrderId({})", serviceOrderId);
        return ordersRepository.findByServiceOrderId(serviceOrderId);
    }

    @Override
    @Transactional  //(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Orders save(Orders order) {
        LOGGER.debug("OrdersServiceImpl.save({})", order.toString());
        return ordersRepository.saveAndFlush(order);
    }*/

}
