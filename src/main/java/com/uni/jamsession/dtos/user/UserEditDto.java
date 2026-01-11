package com.uni.jamsession.dtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserEditDto(
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
        String name,

        @Size(max = 500, message = "Bio must be at most 500 characters")
        String bio,

        Integer profilePictureId,

        Set<@Min(value = 1, message = "Genre ID must be positive") Integer> favouriteGenreIds
) {
}
