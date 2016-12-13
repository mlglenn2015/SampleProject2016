package prv.mark.project.translogproducer.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBElement;

/**
 * Transforms incoming Transaction Log messages into another type TODO.
 *
 * @author mlglenn 12/10/2016.
 */
@Component
@Profile("tbd") //TODO
public class TbdEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TbdEventHandler.class);

    // TODO declare validation Predicates

    //TODO declare Converters

    //TODO Function methods to map properties from incoming type to outgoing type

    @Transactional
    public JAXBElement<Object> handleEvent(Object event) { // TODO the incoming and outgoing JAXB types
        LOGGER.debug("*** RECEIVED EVENT ***");

        //TODO validation

        //TODO set properties

        //TODO send message

        return null; //TODO temporary
    }
}
