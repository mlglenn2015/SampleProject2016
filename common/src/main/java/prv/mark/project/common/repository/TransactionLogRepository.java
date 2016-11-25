package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.TransactionLog;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link prv.mark.project.common.entity.TransactionLog} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    Optional<TransactionLog> findById(Long id);

    List<prv.mark.project.common.entity.TransactionLog> findByLogDateTime(Date logDateTime);

    List<prv.mark.project.common.entity.TransactionLog> findByTransactionType(String transactionType);

    List<TransactionLog> findAll();
}
