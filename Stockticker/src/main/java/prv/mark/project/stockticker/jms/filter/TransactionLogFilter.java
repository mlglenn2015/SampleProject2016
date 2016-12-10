package prv.mark.project.stockticker.jms.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO
 * Created by Owner on 12/10/2016.
 */
@Component
public class TransactionLogFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLogFilter.class);

    public boolean accept(final Object payload) { //The payload would be the JAXB message type
        return true; //TODO we can add filter logic that returns false or true based on the conditions
    }
}
