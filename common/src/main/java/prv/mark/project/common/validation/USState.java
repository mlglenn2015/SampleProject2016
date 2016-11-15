package prv.mark.project.common.validation;

import prv.mark.project.common.validation.impl.USStateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSR-303 annotation to validate US state abbreviations.
 *
 * @author
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = USStateValidator.class)
public @interface USState {

    String message() default "prv.mark.project.common.validation.USState";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
