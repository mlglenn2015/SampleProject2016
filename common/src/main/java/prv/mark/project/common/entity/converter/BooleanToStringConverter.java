package prv.mark.project.common.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convert Java Boolean to SQL String.
 *
 * @author mlglenn.
 */
@Converter(autoApply = true)
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    private static final String YES = "Y";
    private static final String NO = "N";

    @Override
    public String convertToDatabaseColumn(final Boolean b) {
        return b ? YES : NO;
    }

    @Override
    public Boolean convertToEntityAttribute(final String s) {
        return s.equals(YES) ? true : false;
    }

}
