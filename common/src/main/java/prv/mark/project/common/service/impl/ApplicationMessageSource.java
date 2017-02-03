package prv.mark.project.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prv.mark.project.common.entity.ApplicationMessages;
import prv.mark.project.common.repository.ApplicationMessagesRepository;
import prv.mark.project.common.util.StringUtils;

import java.util.Locale;

/**
 * Implementation of the Spring Framework {@link MessageSource} interface
 * designed to retrieve messages from the APPLICATION_MESSAGES table.
 *
 * @author mlglenn
 */
@Component
public class ApplicationMessageSource implements MessageSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMessageSource.class);

    @Value("#{systemProperties['ENVIRONMENT']}")
    private String env;

    @Autowired
    private ApplicationMessagesRepository applicationMessagesRepository;

    public String getMessage(String messageKey) throws NoSuchMessageException {
        LOGGER.debug("ApplicationMessageSource.getMessage({})", messageKey);
        LOGGER.debug("ENVIRONMENT:{}", env);
        ApplicationMessages applicationMessage = applicationMessagesRepository.findByMessageKey(messageKey);
        if (applicationMessage == null) {
            throw new NoSuchMessageException("Message with key " + messageKey + " does not exist.");
        }
        return applicationMessage.getMessage();
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, String s1, Locale locale) {
        LOGGER.debug("ApplicationMessageSource.getMessage({})", messageKey);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return getMessage(messageKey);
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, Locale locale) throws NoSuchMessageException {
        LOGGER.debug("ApplicationMessageSource.getMessage({})", messageKey);
        LOGGER.debug("ENVIRONMENT:{}", env);
        return getMessage(messageKey);
    }

    @Override
    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        LOGGER.debug("ApplicationMessageSource.getMessage({})", messageSourceResolvable);
        LOGGER.debug("ENVIRONMENT:{}", env);
        for (String messageKey : messageSourceResolvable.getCodes()) {
            String message = getMessage(messageKey);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
        }
        throw new NoSuchMessageException("No resolveable message was found.");
    }
}