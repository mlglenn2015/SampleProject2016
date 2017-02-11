package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.TransactionTypesEntity;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link TransactionTypesEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface TransactionTypesRepository extends JpaRepository<TransactionTypesEntity, Long> {

    Optional<TransactionTypesEntity> findById(Long id);

    Optional<TransactionTypesEntity> findByTransactionType(String transactionType);

    List<TransactionTypesEntity> findAll();
}
