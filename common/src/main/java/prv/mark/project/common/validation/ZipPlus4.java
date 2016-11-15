package prv.mark.project.common.validation;

import prv.mark.project.common.validation.impl.ZipPlus4Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSR303 validation interface for ZIP Plus 4.
 *
 * @author mlglenn
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ZipPlus4Validator.class)
public @interface ZipPlus4 {

    String message() default "prv.mark.project.common.validation.ZipPlus4";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
