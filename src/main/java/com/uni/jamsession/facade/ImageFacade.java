package com.uni.jamsession.facade;

import com.uni.jamsession.dtos.ImageDto;
import com.uni.jamsession.model.ImageData;
import com.uni.jamsession.model.User;
import com.uni.jamsession.security.AuthService;
import com.uni.jamsession.services.ImageService;
import com.uni.jamsession.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImageFacade {
  private final ImageService imageService;
  private final UserService userService;
  private final AuthService authService;

  @Transactional
  public ImageDto uploadProfilePicture(MultipartFile file) {
    ImageData savedImage = imageService.saveImage(file);
    User user = userService.getUserById(
        authService.getUserPrincipal().getId()
    );
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
