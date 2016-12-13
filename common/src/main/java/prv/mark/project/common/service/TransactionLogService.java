package prv.mark.project.common.service;

import org.springframework.context.annotation.Profile;
import prv.mark.project.common.entity.TransactionLog;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link TransactionLog} entities.
 *
 * @author mlglenn.
 */
public interface TransactionLogService {

    Optional<TransactionLog> findById(Long id);

    List<TransactionLog> findByLogDateTime(Date logDateTime);

    List<prv.mark.project.common.entity.TransactionLog> findByTransactionType(String transactionType);

    List<TransactionLog> findAll();

    TransactionLog save(TransactionLog transactionLog);
}
