package com.sap.jamsession.repositories;

import com.sap.jamsession.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
  Optional<ImageData> getImageById(Long id);

  ImageData save(ImageData imageData);

  void deleteImageById(Integer id);
}
