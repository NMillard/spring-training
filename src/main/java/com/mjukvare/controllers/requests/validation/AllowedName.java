package com.mjukvare.controllers.requests.validation;

import com.mjukvare.controllers.requests.validation.validator.AllowedNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AllowedNameValidator.class})
public @interface AllowedName {
    String message() default "com.mjukvare.controllers.requests.validation.AllowedName.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
