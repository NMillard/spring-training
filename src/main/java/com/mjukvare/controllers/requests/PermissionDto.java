package com.mjukvare.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissionDto(
        @NotNull @NotBlank
        String type,

        boolean value) {
}