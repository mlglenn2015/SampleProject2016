package prv.mark.project.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * Utility class for common {@link java.util.Date} and {@link java.util.Calendar} conversions.
 */
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    /**
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     *
     * @return
     */
    public static XMLGregorianCalendar getCurrentXMLGregorianCalendar() {
        LocalDateTime localDateTime = getLocalDateTime();
        XMLGregorianCalendar xmlGregorianCalendar;

        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDateTime.toString());
        } catch (DatatypeConfigurationException dce) {
            LOGGER.error(dce.getMessage());
            return null;
        }

        return xmlGregorianCalendar;
    }

    /**
     *
     * @param xmlGregorianCalendar
     * @return
     */
    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar) {

        if (xmlGregorianCalendar == null) {
            return null;
        }
        return xmlGregorianCalendar.toGregorianCalendar().getTime();
    }
}
