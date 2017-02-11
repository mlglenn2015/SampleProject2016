package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.ApplicationMessagesEntity;

/**
 * DAO Interface for {@link ApplicationMessagesEntity} entities.
 *
 * @author mlglenn
 */
@Repository
public interface ApplicationMessagesRepository extends JpaRepository<ApplicationMessagesEntity, Long> {

    @Cacheable("applicationmessages")
    ApplicationMessagesEntity findByMessageKey(String messageKey);
}
