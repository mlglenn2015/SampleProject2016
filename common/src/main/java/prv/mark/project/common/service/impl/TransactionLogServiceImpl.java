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
        //return Optional.ofNullable(ordersRepository.findById(id)).get();
        return Optional.ofNullable(transactionLogRepository.findOne(id));
        //return Optional.of(new TransactionLog());
    }

    @Override
    public List<TransactionLog> findByLogDateTime(final Date logDateTime) {
        LOGGER.debug("TransactionLogServiceImpl.findByLogDateTime({})", logDateTime);
        return transactionLogRepository.findByLogDateTime(logDateTime);
        //return nullList();
    }


    @Override
    public List<TransactionLog> findByTransactionType(final String transactionType) {
        LOGGER.debug("TransactionLogServiceImpl.findByTransactionType({})", transactionType);
        return transactionLogRepository.findByTransactionType(transactionType);
        //return nullList();
    }

    @Override
    public List<TransactionLog> findAll() {
        LOGGER.debug("TransactionLogServiceImpl.findAll()");
        return transactionLogRepository.findAll();
        //return nullList();
    }

    @Override
    @Transactional
    public TransactionLog save(final TransactionLog transactionLog) {
        LOGGER.debug("TransactionLogServiceImpl.save({})", transactionLog.toString());
        return transactionLogRepository.saveAndFlush(transactionLog);
        //return new TransactionLog();
    }

    private List<TransactionLog> nullList() {
        List<TransactionLog> list = new ArrayList<>();
        list.add(new TransactionLog());
        return list;
    }
}
