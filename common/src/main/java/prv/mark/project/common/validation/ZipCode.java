package prv.mark.project.common.validation;


import prv.mark.project.common.validation.impl.ZipCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * JSR303 validation interface for US Postal Code.
 *
 * @author mlglenn
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ZipCodeValidator.class)
public @interface ZipCode {

    String message() default "prv.mark.project.common.validation.ZipCode";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
