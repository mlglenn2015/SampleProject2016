package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.ApplicationParameters;
import prv.mark.project.common.exception.ApplicationException;
import prv.mark.project.common.repository.ApplicationParametersRepository;
import prv.mark.project.common.util.StringUtils;

import java.util.Locale;

/**
 * Designed to retrieve properties from the database APPLICATION_PARAMETERS table.
 *
 * @author mlglenn.
 */
@Component
public final class ApplicationParameterSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParameterSource.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private ApplicationParametersRepository applicationParametersRepository;

    /**
     * Get a parameter based on the input key value.
     * @param propKey {@link String}
     * @return {@link String}
     * @throws ApplicationException
     */
    public String getParm(final String propKey) throws ApplicationException {
        LOGGER.debug("ApplicationParameterSource.getParm({})", propKey);
        LOGGER.debug("ENVIRONMENT:{}", env);
        ApplicationParameters parameter = applicationParametersRepository.findActiveByPropKey(propKey);
        if (parameter == null) {
            LOGGER.error(StringUtils.APPLICATION_EXCEPTION);
            throw new ApplicationException("Application parameter with propKey " + propKey + " not found.");
        }
        LOGGER.debug(parameter.toString());
        return parameter.getPropProperty();
        //return "STOCKTICKER";  STOCKTICKER_20170131  TODO cleanup
    }

    /**
     * Get a parameter based on the input key value.
     * @param key {@link String}
     * @param objects not used
     * @param s1 not used
     * @param locale not used
     * @return {@link String}
     * @throws ApplicationException
     */
    public String getParm(final String key, final Object[] objects, final String s1,
                          final Locale locale)
            throws ApplicationException {
        LOGGER.debug("ApplicationParameterSource.getParm({})", key);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return getParm(key);
    }

    /**
     * Get a parameter based on the input key value.
     * @param key {@link String}
     * @param objects not used
     * @param locale not used
     * @return {@link String}
     * @throws ApplicationException
     */
    public String getParm(final String key, final Object[] objects, final Locale locale)
            throws ApplicationException {
        LOGGER.debug("ApplicationParameterSource.getParm({})", key);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return getParm(key);
    }

}
