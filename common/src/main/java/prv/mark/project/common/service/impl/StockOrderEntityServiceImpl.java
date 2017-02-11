package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.StockOrderEntity;
import prv.mark.project.common.repository.StockOrderRepository;
import prv.mark.project.common.service.StockOrderEntityService;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link StockOrderEntityService} interface.
 *
 * @author mlglenn.
 */
@Service
public class StockOrderEntityServiceImpl implements StockOrderEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderEntityServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private StockOrderRepository stockOrderRepository;

    @Override
    public Optional<StockOrderEntity> findById(final Long id) {
        LOGGER.debug("StockOrderEntityServiceImpl.findById({})", id);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return Optional.ofNullable(stockOrderRepository.findById(id)).get();
        //return Optional.ofNullable(stockOrderRepository.findOne(id));
    }

    @Override
    public List<StockOrderEntity> findByOrderStatus(final String orderStatus) {
        LOGGER.debug("StockOrderEntityServiceImpl.findByOrderStatus({})", orderStatus);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockOrderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<StockOrderEntity> findAll() {
        LOGGER.debug("StockOrderEntityServiceImpl.findAll()");
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockOrderRepository.findAll();
    }

    @Override
    @Transactional
    public StockOrderEntity save(StockOrderEntity order) {
        LOGGER.debug("StockOrderEntityServiceImpl.save({})", order.toString());
        LOGGER.debug("ENVIRONMENT:{}", env);
        return stockOrderRepository.saveAndFlush(order);
    }


    /*private List<StockOrderEntity> nullList() {
        List<StockOrderEntity> list = new ArrayList<>();
        list.add(new StockOrderEntity());
        return list;
    }*/
}
