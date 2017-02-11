package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.TransactionLogEntity;
import prv.mark.project.common.repository.TransactionLogRepository;
import prv.mark.project.common.service.TransactionLogEntityService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link TransactionLogEntityService} interface.
 *
 * @author mlglenn.
 */
@Service
public class TransactionLogEntityServiceImpl implements TransactionLogEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogEntityServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    public Optional<TransactionLogEntity> findById(final Long id) {
        LOGGER.debug("TransactionLogEntityServiceImpl.findById({})", id);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return Optional.ofNullable(transactionLogRepository.findById(id)).get();
        //return Optional.ofNullable(transactionLogRepository.findOne(id));
    }

    @Override
    public List<TransactionLogEntity> findByLogDateTime(final Date logDateTime) {
        LOGGER.debug("TransactionLogEntityServiceImpl.findByLogDateTime({})", logDateTime);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.findByLogDateTime(logDateTime);
    }


    @Override
    public List<TransactionLogEntity> findByTransactionType(final String transactionType) {
        LOGGER.debug("TransactionLogEntityServiceImpl.findByTransactionType({})", transactionType);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.findByTransactionType(transactionType);
    }

    @Override
    public List<TransactionLogEntity> findAll() {
        LOGGER.debug("TransactionLogEntityServiceImpl.findAll()");
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.findAll();
    }

    @Override
    @Transactional
    public TransactionLogEntity save(final TransactionLogEntity transactionLogEntity) {
        LOGGER.debug("TransactionLogEntityServiceImpl.save({})", transactionLogEntity.toString());
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.saveAndFlush(transactionLogEntity);
    }


    /*private List<TransactionLogEntity> nullList() {
        List<TransactionLogEntity> list = new ArrayList<>();
        list.add(new TransactionLogEntity());
        return list;
    }*/
}
