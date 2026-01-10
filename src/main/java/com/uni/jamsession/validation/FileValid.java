package com.uni.jamsession.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileValid {
  String message() default "Invalid file. Only PNG and JPG images up to 5MB are allowed.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
