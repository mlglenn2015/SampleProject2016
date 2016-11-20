package prv.mark.project.common.validation;


import prv.mark.project.common.validation.impl.Zip4Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSR303 annotation to validate US Zip+4 code.
 *
 * @author mlglenn
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Zip4Validator.class)
public @interface Zip4 {

    String message() default "prv.mark.project.common.validation.Zip4";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
