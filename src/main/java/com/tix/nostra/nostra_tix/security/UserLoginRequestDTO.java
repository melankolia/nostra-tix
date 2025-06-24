package com.tix.nostra.nostra_tix.security;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @NotBlank(message = "Email is required") String email) {
}
