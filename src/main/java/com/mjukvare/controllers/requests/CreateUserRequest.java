package com.mjukvare.controllers.requests;

import com.mjukvare.controllers.requests.validation.AllowedName;
import com.mjukvare.controllers.requests.validation.UserPermissions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@UserPermissions // Custom constraint on the entire class.
public record CreateUserRequest(

        @AllowedName(message = "The name is blacklisted") // Custom constraint on a String field.
        @NotNull @NotEmpty String name,
        String lastName,

        @Range(min = 1, max = 120)
        int age,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate registrationDate,

        @NotNull
        UserType userType,

        /*
         * Note that this requires an @Valid annotation.
         * Not having this would mean validator constraints in the PermissionDto are not enforced.
         */
        @Valid
        List<PermissionDto> permissions
) {
}

