package com.mjukvare.controllers.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotNull @NotEmpty String name,
        String lastName,
        int age) {
}
