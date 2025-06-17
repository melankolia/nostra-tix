package com.tix.nostra.nostra_tix.security;

import jakarta.validation.constraints.NotBlank;

public record OTPRequestDTO(
        @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "OTP is required") String otp) {
}
