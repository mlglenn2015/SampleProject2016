package prv.mark.project.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
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

    @Autowired
    private ApplicationMessagesRepository applicationMessagesRepository;

    public String getMessage(String messageKey) throws NoSuchMessageException {
        ApplicationMessages applicationMessage = applicationMessagesRepository.findByMessageKey(messageKey);
        if (applicationMessage == null) {
            throw new NoSuchMessageException("Message with key " + messageKey + " does not exist.");
        }
        return applicationMessage.getMessage();
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, String s1, Locale locale) {
        return getMessage(messageKey);
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, Locale locale) throws NoSuchMessageException {
        return getMessage(messageKey);
    }

    @Override
    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        for (String messageKey : messageSourceResolvable.getCodes()) {
            String message = getMessage(messageKey);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
        }
        throw new NoSuchMessageException("No resolveable message was found.");
    }
}