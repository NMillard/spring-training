package com.mjukvare.controllers.requests.validation;

import com.mjukvare.controllers.requests.validation.validator.UserPermissionsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UserPermissionsValidator.class })
public @interface UserPermissions {
    String message() default "com.mjukvare.controllers.requests.validation.UserPermissions.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
