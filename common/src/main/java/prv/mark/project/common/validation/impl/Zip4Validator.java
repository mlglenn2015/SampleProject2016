package prv.mark.project.common.validation.impl;

import prv.mark.project.common.validation.Zip4;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Implementation of the {@link Zip4} validation annotation.
 *
 * @author
 */
public class Zip4Validator implements ConstraintValidator<Zip4, String> {

    public void initialize(Zip4 zip) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("[0-9]{4}");
        return value != null && pattern.matcher(value).matches();
    }
}
