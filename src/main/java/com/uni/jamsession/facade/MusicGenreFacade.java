package com.uni.jamsession.facade;

import com.uni.jamsession.dtos.CreateMusicGenreDto;
import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.mappers.MusicGenreMapper;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.services.MusicGenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MusicGenreFacade {

    private final MusicGenreService musicGenreService;
    private final MusicGenreMapper musicGenreMapper;

    public List<MusicGenreDto> getAllMusicGenres() {
       List<MusicGenre> musicGenres = musicGenreService.getAllMusicGenres();
       return musicGenres.stream().map(
               musicGenreMapper::toDto
       ).collect(Collectors.toList());
    }

    public MusicGenreDto createMusicGenre(CreateMusicGenreDto musicGenreDto) {
        MusicGenre musicGenre = musicGenreMapper.toEntityFromCreateDto(musicGenreDto);
        MusicGenre createdGenre = musicGenreService.createMusicGenre(musicGenre);
        return musicGenreMapper.toDto(createdGenre);
    }

    public void deleteById(int id) {
        musicGenreService.deleteById(id);
    }

}
