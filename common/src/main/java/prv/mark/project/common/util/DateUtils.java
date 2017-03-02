package prv.mark.project.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * Utility class for common {@link java.util.Date} and {@link java.util.Calendar} conversions.
 *
 * @author mlglenn
 */
public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static Date getDate() {
        return new Date();
    }

    /**
     * Returns the {@link LocalDateTime}.
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * Returns the local date time in this format: '2011-12-03T10:15:30'
     * @return {@link String}
     */
    public static String getDateFormattedAsString() {
        return getLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Returns a {@link LocalDateTime} from an input {@link Date}.
     * @param dateIn the input {@link Date}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getLocalDateTimeFromDate(final Date dateIn) {
        if (dateIn == null) {
            return getLocalDateTime();
        }
        LocalDateTime ldt = LocalDateTime.ofInstant(dateIn.toInstant(), ZoneId.systemDefault());
        return ldt;
    }

    /**
     * Returns the {@link LocalDate}.
     * @return {@link LocalDate}
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * Returns a {@link Date} object from an input {@link LocalDateTime}.
     * @param localDateTime the input {@link LocalDateTime}
     * @return {@link Date}
     */
    public static Date getDateFromLocalDateTime(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return getDate();
        }
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());
        return output;
    }

    /**
     * Returns a {@link Date} object from {@link LocalDateTime}.
     * @return {@link Date}
     */
    public static Date getDateFromLocalDateTime() {
        ZonedDateTime zdt = getLocalDateTime().atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());
        return output;
    }

    /**
     * Returns an {@link XMLGregorianCalendar} object.
     * @return {@link XMLGregorianCalendar}
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
     * Returns an {@link XMLGregorianCalendar} object.
     * @return {@link XMLGregorianCalendar}
     */
    public static XMLGregorianCalendar getCurrentXMLGregorianCalendarFromLocalDateTime(
                                                            final LocalDateTime localDateTime) {
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
     * Returns a {@link Date} object.
     * @param xmlGregorianCalendar the object to convert.
     * @return {@link Date}
     */
    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar) {

        if (xmlGregorianCalendar == null) {
            return getDate();
        }
        return xmlGregorianCalendar.toGregorianCalendar().getTime();
    }
}
