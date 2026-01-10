package com.uni.jamsession.controllers;

import com.uni.jamsession.dtos.ImageDto;
import com.uni.jamsession.facade.ImageFacade;
import com.uni.jamsession.validation.FileValid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@AllArgsConstructor
public class ImageDataController {
  private final ImageFacade imageFacade;

  @PostMapping("/profile-picture")
  public ResponseEntity<ImageDto> uploadProfilePicture(
      @Valid @FileValid @RequestParam("file") MultipartFile file) {
    ImageDto response = imageFacade.uploadProfilePicture(file);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/upload")
  public ResponseEntity<ImageDto> uploadImage(
      @Valid @FileValid @RequestParam("file") MultipartFile file) {
    ImageDto response = imageFacade.uploadImage(file);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
    ImageDto imageData = imageFacade.getImageById(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, imageData.type())
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageData.name() + "\"")
        .body(imageData.data());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
    imageFacade.deleteImage(id);
    return ResponseEntity.noContent().build();
  }
}
