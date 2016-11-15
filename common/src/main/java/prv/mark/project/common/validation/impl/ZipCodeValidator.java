package prv.mark.project.common.validation.impl;

import prv.mark.project.common.validation.ZipCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Implementation class for {@link ZipCode} validation.
 *
 * @author
 */
public class ZipCodeValidator implements ConstraintValidator<ZipCode, String> {

    public void initialize(ZipCode zip) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("[0-9]{5}");
        return value != null && pattern.matcher(value).matches();
    }
}
