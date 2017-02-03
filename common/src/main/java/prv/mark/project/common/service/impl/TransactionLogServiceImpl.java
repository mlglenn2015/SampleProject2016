package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.project.common.entity.TransactionLog;
import prv.mark.project.common.repository.TransactionLogRepository;
import prv.mark.project.common.service.TransactionLogService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link TransactionLogService} interface.
 *
 * @author mlglenn.
 */
@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogServiceImpl.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    public Optional<TransactionLog> findById(final Long id) {
        LOGGER.debug("TransactionLogServiceImpl.findById({})", id);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return Optional.ofNullable(transactionLogRepository.findById(id)).get();
        //return Optional.ofNullable(transactionLogRepository.findOne(id));
    }

    @Override
    public List<TransactionLog> findByLogDateTime(final Date logDateTime) {
        LOGGER.debug("TransactionLogServiceImpl.findByLogDateTime({})", logDateTime);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.findByLogDateTime(logDateTime);
    }


    @Override
    public List<TransactionLog> findByTransactionType(final String transactionType) {
        LOGGER.debug("TransactionLogServiceImpl.findByTransactionType({})", transactionType);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.findByTransactionType(transactionType);
    }

    @Override
    public List<TransactionLog> findAll() {
        LOGGER.debug("TransactionLogServiceImpl.findAll()");
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.findAll();
    }

    @Override
    @Transactional
    public TransactionLog save(final TransactionLog transactionLog) {
        LOGGER.debug("TransactionLogServiceImpl.save({})", transactionLog.toString());
        LOGGER.debug("ENVIRONMENT:{}", env);
        return transactionLogRepository.saveAndFlush(transactionLog);
    }


    /*private List<TransactionLog> nullList() {
        List<TransactionLog> list = new ArrayList<>();
        list.add(new TransactionLog());
        return list;
    }*/
}
