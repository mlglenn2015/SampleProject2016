package prv.mark.project.common.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Converts the date-time to and from the date column in Oracle tables.
 *
 * @author mlglenn.
 */
@Converter(autoApply = true)
public class LDTConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public java.sql.Timestamp convertToDatabaseColumn(final LocalDateTime entityValue) {
        if (entityValue != null) {
            return Timestamp.valueOf(entityValue);
        } else {
            return null;
        }
    }
    @Override
    public LocalDateTime convertToEntityAttribute(final java.sql.Timestamp databaseValue) {
        if (databaseValue != null) {
            return databaseValue.toLocalDateTime();
        } else {
            return null;
        }
    }

}
