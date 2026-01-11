package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.CreateMusicGenreDto;
import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.model.MusicGenre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MusicGenreMapper {
    public MusicGenre toEntity(MusicGenreDto musicGenreDto);
    public MusicGenreDto toDto(MusicGenre musicGenre);
    public MusicGenre toEntityFromCreateDto(CreateMusicGenreDto createMusicGenreDto);
}
