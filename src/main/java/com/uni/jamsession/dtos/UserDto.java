package com.uni.jamsession.dtos;

import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.validation.UserValidationGroups;
import jakarta.validation.constraints.*;
import java.util.Set;

public record UserDto(
    Integer id,
    @NotBlank(
            groups = {UserValidationGroups.OnRegister.class},
            message = "Name cannot be blank")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
        @Pattern(
            regexp = "^[A-Za-zÀ-ÿ '-]+$",
            message = "Name can only contain letters, spaces, hyphens, and apostrophes.")
        String name,
    @NotBlank(
            groups = {UserValidationGroups.OnRegister.class, UserValidationGroups.OnLogin.class},
            message = "Email cannot be blank")
        @Email(message = "Invalid email format. Please provide a valid email address.")
        String email,
    @NotBlank(
            groups = {UserValidationGroups.OnRegister.class, UserValidationGroups.OnLogin.class},
            message = "Password cannot be empty")
        @Size(
            groups = {UserValidationGroups.OnRegister.class},
            min = 6,
            max = 64,
            message = "Password must be between 8 and 64 characters.")
        String password,
    @Size(
            groups = {UserValidationGroups.OnEdit.class},
            max = 500,
            message = "Bio must be at most 500 characters")
        String bio,
    Integer profilePictureId,
    Set<MusicGenre> favoriteGenres,
    Set<
            @Min(
                groups = {UserValidationGroups.OnEdit.class},
                value = 1,
                message = "Genre ID must be a positive integer")
            Integer>
        favouriteGenreIds,
    Set<JamSessionDto> ownedJamSessions) {}
