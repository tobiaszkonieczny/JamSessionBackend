package com.uni.jamsession.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format.")
        String email,

        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
