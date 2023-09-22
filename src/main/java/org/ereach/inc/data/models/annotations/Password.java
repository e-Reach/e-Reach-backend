package org.ereach.inc.data.models.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ereach.inc.services.validators.PasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
//@Constraint(validatedBy = PasswordValidator.class)
@Target(value = {FIELD, PARAMETER, ANNOTATION_TYPE})
@Retention(value = RUNTIME)
public @interface Password {
	
	String regexp() default "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$&.!*?]).{8,12}$";
	String message() default "Invalid password";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
