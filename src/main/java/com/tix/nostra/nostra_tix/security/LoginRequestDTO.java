package com.tix.nostra.nostra_tix.security;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "OTP is required") String otp) {
}
