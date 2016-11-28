package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.ApplicationMessages;
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
public final class ApplicationParameterSource implements MessageSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParameterSource.class);

    @Autowired
    private ApplicationParametersRepository applicationParametersRepository;


    /*public String getMessage(String messageKey) throws NoSuchMessageException {
        ApplicationParameters parameter = applicationParametersRepository.findActiveByKey(messageKey, true);
        if (parameter == null) {
            throw new NoSuchMessageException("Message with key " + messageKey + " does not exist.");
        }
        return parameter.getProperty();
    }*/

    /**
     * Get a parameter based on the input key value.
     * @param key {@link String}
     * @return {@link String}
     * @throws ApplicationException
     */
    public String getParm(final String key) throws ApplicationException {

        ApplicationParameters parameter = applicationParametersRepository.findActiveByKey(key, true);
        if (parameter == null) {
            LOGGER.error(StringUtils.APPLICATION_EXCEPTION);
            throw new ApplicationException("Application parameter with key " + key + " not found.");
        }
        LOGGER.debug(parameter.toString());
        return parameter.getProperty();
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
        return getParm(key);
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, String s1, Locale locale) {
        return getParm(messageKey);
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, Locale locale) throws NoSuchMessageException {
        return getParm(messageKey);
    }

    @Override
    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        for (String messageKey : messageSourceResolvable.getCodes()) {
            String message = getParm(messageKey);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
        }
        throw new NoSuchMessageException("No resolveable message was found.");
    }
}
