package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.facade.MusicGenreFacade;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.dtos.CreateMusicGenreDto;
import com.uni.jamsession.repositories.MusicGenreRepository;
import com.uni.jamsession.services.MusicGenreService;
import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class MusicGenresController {

  private final MusicGenreFacade  musicGenreFacade;

  @GetMapping("/all")
  public ResponseEntity<List<MusicGenreDto>> getAllGenres() {
    return ResponseEntity.ok(musicGenreFacade.getAllMusicGenres());
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<MusicGenreDto> addGenre(@Valid @RequestBody CreateMusicGenreDto musicGenreDto) {
    return ResponseEntity.ok(
        musicGenreFacade.createMusicGenre(musicGenreDto)
    );
  }
  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteGenre(@RequestParam int id) {
    musicGenreFacade.deleteById(id);
    return new ResponseEntity<>("Deleted genre", HttpStatus.OK);
  }

}
