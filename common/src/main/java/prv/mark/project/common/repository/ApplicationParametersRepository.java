package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * @param propKey {@link String}
     * @return {@link ApplicationParameters}
     */
    @Cacheable("parameters")
    ApplicationParameters findByPropKey(String propKey);

    /**
     * Returns the active property by the parameter key (not to be confused by id).
     *
     * @param propKey {@link String}
     * //@param active {@link Boolean}
     * @return {@link ApplicationParameters}
     */
    @Cacheable("parameters")
    ApplicationParameters findActiveByPropKey(String propKey);

}
