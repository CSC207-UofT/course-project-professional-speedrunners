package com.boba.bobabuddy.framework.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation for password format validation.
 */

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Minimum password length 6, must contain one letter, one number, and no whitespace.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
