package com.uni.jamsession.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ '-]+$", message = "Name can only contain letters, spaces, hyphens, and apostrophes.")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format.")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
        String password
) {
}
