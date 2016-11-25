package prv.mark.project.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.TransactionTypes;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link prv.mark.project.common.entity.TransactionTypes} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface TransactionTypesRepository extends JpaRepository<TransactionTypes, Long> {

    Optional<TransactionTypes> findById(Long id);

    Optional<TransactionTypes> findByTransactionType(String transactionType);

    List<TransactionTypes> findAll();
}
