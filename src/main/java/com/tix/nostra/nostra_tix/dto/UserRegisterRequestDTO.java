package com.tix.nostra.nostra_tix.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Phone number is required") String phoneNo,
        @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String password,
        @NotBlank(message = "Role is required") String role) {
}
