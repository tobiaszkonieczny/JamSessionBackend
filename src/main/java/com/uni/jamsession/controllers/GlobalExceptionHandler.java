package com.uni.jamsession.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.uni.jamsession.exceptions.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.uni.jamsession.exceptions.ImageUploadException;
import com.uni.jamsession.exceptions.ResourceDuplicatedException;
import com.uni.jamsession.exceptions.ResourceNotFoundException;
import com.uni.jamsession.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(IllegalAccessException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalAccessException(IllegalAccessException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<Map<String, Object>> handleIOException(IOException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    LOGGER.error(ex.getMessage());
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return buildErrorResponse("Validation failed", errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    Map<String, String> errors = new HashMap<>();
    return buildErrorResponse(ex.getMessage(), errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
    LOGGER.error(ex.getMessage(), ex);
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<Map<String, Object>> handleHandlerMethodValidationException(
      HandlerMethodValidationException ex) {
    return buildErrorResponse(
        "Invalid file upload", extractHandlerMethodErrors(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, ex.getStatus());
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<Map<String, Object>> handleTokenExpiredException(TokenExpiredException ex) {
    LOGGER.error("GlobalExceptionHandler", ex);

    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(JWTVerificationException.class)
  public ResponseEntity<Map<String, Object>> handleJWTVerificationException(
      JWTVerificationException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse("Invalid Jwt token", null, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ResponseEntity<Map<String, Object>> handInternalAuthenticationServiceException(
      InternalAuthenticationServiceException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Map<String, Object>> handleBadCredentialsException(
      BadCredentialsException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ResourceDuplicatedException.class)
  public ResponseEntity<Map<String, Object>> handleResourceDuplicatedException(
      ResourceDuplicatedException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleAuthorizationDeniedException(
      AuthorizationDeniedException ex) {
    LOGGER.error(ex.getMessage());
    return buildErrorResponse(ex.getMessage(), null, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ImageUploadException.class)
  public ResponseEntity<Map<String, Object>> handleImageUploadException(
          ImageUploadException ex
  ){
    LOGGER.error(ex.getMessage());
    return buildErrorResponse("Failed to upload image", null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
          DataIntegrityViolationException ex
  ){
    LOGGER.error(ex.getMessage());
    return buildErrorResponse("Cannot delete object, it is used in other parts of the application", null, HttpStatus.CONFLICT);
  }
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    LOGGER.error("Malformed JSON request: {}", ex.getMessage());
    return buildErrorResponse("Malformed JSON request", null, HttpStatus.BAD_REQUEST);
  }


  private Map<String, String> extractHandlerMethodErrors(HandlerMethodValidationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getAllErrors()
        .forEach(
            error -> {
              String errorMessage = error.getDefaultMessage();
              errors.put("File", errorMessage);
            });
    return errors;
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(
      String message, Map<String, String> errors, HttpStatus status) {
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", LocalDateTime.now());
    response.put("message", message);
    response.put("errors", errors);
    response.put("status", status.value());
    return new ResponseEntity<>(response, status);
  }
}
