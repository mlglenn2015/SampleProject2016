package prv.mark.project.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prv.mark.project.common.entity.ApplicationParameterEntity;

/**
 * DAO Interface for {@link ApplicationParameterEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface ApplicationParameterRepository extends JpaRepository<ApplicationParameterEntity, Long> {

    /**
     * Returns the enabled property by propKey.
     * @param parameterKey {@link String}
     * @return {@link ApplicationParameterEntity}
     *
     * @ Query("select a from ApplicationParameterEntity a where a.parameterKey = ?1")
     */
    @Cacheable("applicationparameters")
    ApplicationParameterEntity findEnabledByParameterKey(String parameterKey, Boolean enabled);

}
