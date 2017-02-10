package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.ApplicationParameter;

/**
 * DAO Interface for {@link ApplicationParameter} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface ApplicationParameterRepository extends JpaRepository<ApplicationParameter, Long> {

    /**
     * Returns the enabled property by propKey.
     * @param parameterKey {@link String}
     * @return {@link ApplicationParameter}
     *
     * @ Query("select a from ApplicationParameter a where a.parameterKey = ?1")
     */
    @Cacheable("applicationparameters")
    ApplicationParameter findEnabledByParameterKey(String parameterKey, Boolean enabled);

}
