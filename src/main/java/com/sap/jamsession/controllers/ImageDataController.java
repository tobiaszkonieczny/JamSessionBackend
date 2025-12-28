package com.sap.jamsession.controllers;

import com.sap.jamsession.model.ImageData;
import com.sap.jamsession.services.ImageService;
import com.sap.jamsession.validation.FileValid;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageDataController {
  private final ImageService imageService;

  public ImageDataController(ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping("/upload")
  public ResponseEntity<Map<String, String>> uploadProfilePicture(
      @Valid @FileValid @RequestParam("file") MultipartFile file) throws IOException {
    ImageData savedImage = imageService.uploadProfilePicture(file);
    Map<String, String> response = new HashMap<>();
    response.put("message", "Image uploaded successfully");
    response.put("id", String.valueOf(savedImage.getId()));
    return ResponseEntity.ok(response);
    //    return ResponseEntity.status(HttpStatus.OK)
    //        .body("Image uploaded successfully: ID = " + savedImage.getId());
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
    ImageData imageData = imageService.getById(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, imageData.getType())
        .body(imageData.getData());
  }
}
