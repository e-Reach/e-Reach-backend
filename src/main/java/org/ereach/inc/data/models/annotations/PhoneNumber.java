package org.ereach.inc.data.models.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ereach.inc.services.validators.PhoneNumberValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RUNTIME)
@Target(value = {FIELD, PARAMETER, ANNOTATION_TYPE, METHOD})
public @interface PhoneNumber {

	String region() default "ZZ";
	String message() default "Invalid Phone number";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
