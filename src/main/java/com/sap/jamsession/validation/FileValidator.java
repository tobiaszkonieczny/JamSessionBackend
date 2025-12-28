package com.sap.jamsession.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<FileValid, MultipartFile> {

  private static final long MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB
  private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png");

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file == null || file.isEmpty()) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate("File cannot be empty.")
          .addConstraintViolation();
      return false;
    }

    if (!ALLOWED_TYPES.contains(file.getContentType())) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate("Only PNG and JPG images are allowed.")
          .addConstraintViolation();
      return false;
    }

    if (file.getSize() > MAX_FILE_SIZE) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate("File size must be less than 1MB.")
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
