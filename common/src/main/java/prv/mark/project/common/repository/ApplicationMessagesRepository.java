package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.ApplicationMessages;

/**
 * DAO Interface for {@link ApplicationMessages} entities.
 *
 * @author mlglenn
 */
@Repository
public interface ApplicationMessagesRepository extends JpaRepository<ApplicationMessages, Long> {

    @Cacheable("applicationmessages")
    ApplicationMessages findByMessageKey(String messageKey);
}
