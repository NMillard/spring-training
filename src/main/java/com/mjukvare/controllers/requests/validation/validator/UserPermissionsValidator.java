package com.mjukvare.controllers.requests.validation.validator;

import com.mjukvare.controllers.requests.CreateUserRequest;
import com.mjukvare.controllers.requests.PermissionDto;
import com.mjukvare.controllers.requests.UserType;
import com.mjukvare.controllers.requests.validation.UserPermissions;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This class illustrates how you can inspect multiple attributes at once, and in relation to one another.
 * Also, take note of how the validator context is used. In particular that the default behavior is disabled
 * and a custom constraint violation is added.
 */
public class UserPermissionsValidator implements ConstraintValidator<UserPermissions, CreateUserRequest> {

    private final Map<UserType, List<String>> permissions = Map.of(
            UserType.FREE, List.of("users.view"),
            UserType.PREMIUM, List.of("users.view", "users.edit", "users.delete", "user.create")
    );

    @Override
    public boolean isValid(CreateUserRequest value, ConstraintValidatorContext context) {
        List<String> allowedPermissions = this.permissions.get(value.userType());
        List<String> providedPermissions = value.permissions()
                .stream()
                .filter(PermissionDto::value)
                .map(PermissionDto::type)
                .toList();

        var invalidPermissions = new HashSet<>(providedPermissions);
        allowedPermissions.forEach(invalidPermissions::remove);

        if (invalidPermissions.isEmpty()) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        "User type %s cannot have permissions %s "
                                .formatted(value.userType(), invalidPermissions)
                )
                .addPropertyNode("permissions")
                .addConstraintViolation();

        return false;
    }
}
