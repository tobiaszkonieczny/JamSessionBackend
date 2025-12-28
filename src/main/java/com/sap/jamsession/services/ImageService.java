package com.sap.jamsession.services;

import com.sap.jamsession.exceptions.ImageUploadException;
import com.sap.jamsession.model.ImageData;
import com.sap.jamsession.repositories.ImageDataRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
  private final ImageDataRepository imageDataRepository;
  private final UserService userService;

  @Autowired
  public ImageService(ImageDataRepository imageDataRepository, UserService userService) {
    this.imageDataRepository = imageDataRepository;
    this.userService = userService;
  }
    public ImageData getById(Long id) {
        Optional<ImageData> imageData = imageDataRepository.findById(id);
        return imageData.orElseThrow(() -> new ImageUploadException("Image not found with id: " + id, null));
    }
    @Transactional
    public ImageData uploadProfilePicture(MultipartFile file) {
        try {
            ImageData imageData = new ImageData();
            imageData.setName(file.getOriginalFilename());
            imageData.setType(file.getContentType());
            imageData.setData(file.getBytes());

            var image = imageDataRepository.save(imageData);

            var user = userService.getUser(userService.getUserPrinciples().getUserId());

           

            user.setProfilePictureId(image.getId());
            userService.saveUser(user);

            return image;

        } catch (IOException ex) {
            throw new ImageUploadException("Failed to upload image", ex);
        }
    }

    public ImageData uploadImage(MultipartFile file) {
        try {
            ImageData imageData = new ImageData();
            imageData.setName(file.getOriginalFilename());
            imageData.setType(file.getContentType());
            imageData.setData(file.getBytes());

            return imageDataRepository.save(imageData);

        } catch (IOException ex) {
            throw new ImageUploadException("Failed to upload image", ex);
        }
    }
}
