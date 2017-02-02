package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.StockOrder;
import prv.mark.project.common.repository.StockOrderRepository;
import prv.mark.project.common.service.StockOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link StockOrderService} interface.
 *
 * @author mlglenn.
 */
@Service
public class StockOrderServiceImpl implements StockOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private StockOrderRepository stockOrderRepository;

    @Override
    public Optional<StockOrder> findById(final Long id) {
        LOGGER.debug("StockOrderServiceImpl.findById({})", id);
        return Optional.ofNullable(stockOrderRepository.findById(id)).get();
        //return Optional.ofNullable(stockOrderRepository.findOne(id));
    }

    @Override
    public List<StockOrder> findByOrderStatus(final String orderStatus) {
        LOGGER.debug("StockOrderServiceImpl.findByOrderStatus({})", orderStatus);
        return stockOrderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<StockOrder> findAll() {
        LOGGER.debug("StockOrderServiceImpl.findAll()");
        return stockOrderRepository.findAll();
    }

    @Override
    @Transactional
    public StockOrder save(StockOrder order) {
        LOGGER.debug("StockOrderServiceImpl.save({})", order.toString());
        return stockOrderRepository.saveAndFlush(order);
    }


    private List<StockOrder> nullList() {
        List<StockOrder> list = new ArrayList<>();
        list.add(new StockOrder());
        return list;
    }
}
