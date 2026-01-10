package com.sap.jamsession.facade;

import com.sap.jamsession.dtos.ImageDto;
import com.sap.jamsession.model.ImageData;
import com.sap.jamsession.services.ImageService;
import com.sap.jamsession.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImageFacade {
  private final ImageService imageService;
  private final UserService userService;

  @Transactional
  public ImageDto uploadProfilePicture(MultipartFile file) {
    ImageData savedImage = imageService.saveImage(file);
    var user = userService.getUser(userService.getUserPrinciples().getUserId());
    user.setProfilePictureId(savedImage.getId());
    userService.saveUser(user);

    return new ImageDto(
        savedImage.getId().longValue(),
        null,
        null,
        null,
        "Profile picture uploaded successfully"
    );
  }

  public ImageDto uploadImage(MultipartFile file) {
    ImageData savedImage = imageService.saveImage(file);

    return new ImageDto(
        savedImage.getId().longValue(),
        null,
        null,
        null,
        "Image uploaded successfully"
    );
  }

  public ImageDto getImageById(Long id) {
    ImageData imageData = imageService.getById(id);

    return new ImageDto(
        imageData.getId().longValue(),
        imageData.getName(),
        imageData.getType(),
        imageData.getData(),
        null
    );
  }

  @Transactional
  public void deleteImage(Long id) {
    imageService.deleteById(id);
  }
}
