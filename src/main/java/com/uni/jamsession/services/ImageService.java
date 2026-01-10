package com.uni.jamsession.services;

import com.uni.jamsession.exceptions.ImageUploadException;
import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.model.ImageData;
import com.uni.jamsession.repositories.ImageDataRepository;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@Service
public class ImageService {
  private final ImageDataRepository imageDataRepository;



  public ImageData getById(Long id) {
    return imageDataRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Image", id));
  }

  public ImageData saveImage(MultipartFile file) {
    try {
      ImageData imageData = new ImageData();
      imageData.setName(file.getOriginalFilename());
      imageData.setType(file.getContentType());
      imageData.setData(file.getBytes());

      return imageDataRepository.save(imageData);
    } catch (IOException ex) {
      throw new ImageUploadException("Failed to process image file", ex);
    }
  }

  public void deleteById(Long id) {
    if (!imageDataRepository.existsById(id)) {
      throw new ResourceNotFoundException("Image", id);
    }
    imageDataRepository.deleteById(id);
  }

  public boolean existsById(Long id) {
    return imageDataRepository.existsById(id);
  }
}
