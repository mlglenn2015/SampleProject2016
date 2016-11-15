package prv.mark.project.common.validation.impl;

import prv.mark.project.common.validation.ZipPlus4;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Implementation class for {@link ZipPlus4} validation.
 *
 * @author mlglenn
 */
public class ZipPlus4Validator implements ConstraintValidator<ZipPlus4, String> {

    public void initialize(ZipPlus4 zipPlus4) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("[0-9]{5}-[0-9]{4}");
        return value != null && pattern.matcher(value).matches();
    }
}
