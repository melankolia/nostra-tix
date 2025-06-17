package com.tix.nostra.nostra_tix.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "User login is required") String userLogin,
        @NotBlank(message = "Password is required") String password,
        Long userId) {
}
