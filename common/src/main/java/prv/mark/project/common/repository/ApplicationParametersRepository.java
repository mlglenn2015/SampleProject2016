package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.ApplicationParameters;

/**
 * DAO Interface for {@link ApplicationParameters} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface ApplicationParametersRepository extends JpaRepository<ApplicationParameters, Long> {

    /**
     * Returns the property by the parameter key (not to be confused by id).
     *
     * @param key {@link String}
     * @return {@link ApplicationParameters}
     */
    @Cacheable("parameters")
    ApplicationParameters findByKey(String key);

    /**
     * Returns the active property by the parameter key (not to be confused by id).
     *
     * @param key {@link String}
     * @param active {@link Boolean}
     * @return {@link ApplicationParameters}
     */
    @Cacheable("parameters")
    ApplicationParameters findActiveByKey(String key, Boolean active);

}
