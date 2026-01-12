package com.uni.jamsession.dtos.user;

public record UserShortDto(
        Integer id,
        String name,
        String email,
        Integer profilePictureId
) {}