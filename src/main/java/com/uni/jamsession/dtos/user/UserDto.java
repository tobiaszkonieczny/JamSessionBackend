package com.uni.jamsession.dtos.user;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.dtos.jamsession.JamSessionDto;

import java.util.Set;

public record UserDto(
        Integer id,
        String name,
        String email,
        String bio,
        Integer profilePictureId,
        Set<MusicGenreDto> favoriteGenres,
        Set<JamSessionDto> ownedJamSessions
) {}
