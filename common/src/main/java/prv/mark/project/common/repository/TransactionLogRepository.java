package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.TransactionLogEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link TransactionLogEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLogEntity, Long> {

    Optional<TransactionLogEntity> findById(Long id);

    List<TransactionLogEntity> findByLogDateTime(Date logDateTime);

    List<TransactionLogEntity> findByTransactionType(String transactionType);

    List<TransactionLogEntity> findAll();
}
