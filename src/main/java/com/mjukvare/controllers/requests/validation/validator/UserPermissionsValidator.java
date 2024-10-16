package com.mjukvare.controllers.requests.validation.validator;

import com.mjukvare.controllers.requests.CreateUserRequest;
import com.mjukvare.controllers.requests.PermissionDto;
import com.mjukvare.controllers.requests.validation.UserPermissions;
import com.mjukvare.domain.UserPermissionStore;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

/**
 * This class illustrates how you can inspect multiple attributes at once, and in relation to one another.
 * Also, take note of how the validator context is used. In particular that the default behavior is disabled
 * and a custom constraint violation is added.
 *
 * However, you need to be careful with an approach like this, where business logic is placed at the interface
 * to your application. You'll quickly run into duplicated logic or business policies that get out of sync.
 *
 * This class purely serves as an illustration of validating multiple attributes.
 */
public class UserPermissionsValidator implements ConstraintValidator<UserPermissions, CreateUserRequest> {
    private final UserPermissionStore permissionStore;

    public UserPermissionsValidator(UserPermissionStore permissionStore) {
        this.permissionStore = permissionStore;
    }

    @Override
    public boolean isValid(CreateUserRequest value, ConstraintValidatorContext context) {
        List<String> allowedPermissions = this.permissionStore.getUserTypePermissions(value.userType());
        List<String> providedPermissions = value.permissions()
                .stream()
                .filter(PermissionDto::value)
                .map(PermissionDto::type)
                .toList();

        var invalidPermissions = new HashSet<>(providedPermissions);
        allowedPermissions.forEach(invalidPermissions::remove);

        if (invalidPermissions.isEmpty()) return true;

        context.disableDefaultConstraintViolation();

        invalidPermissions.forEach(invalidPermission -> {
            int permissionIndex = value.permissions().indexOf(new PermissionDto(invalidPermission, true));
            context.buildConstraintViolationWithTemplate(
                            "User type %s cannot have permissions %s".formatted(value.userType(), invalidPermission)
                    )
                    .addPropertyNode("permissions[%d].type".formatted(permissionIndex))
                    .addConstraintViolation();
        });

        return false;
    }
}

