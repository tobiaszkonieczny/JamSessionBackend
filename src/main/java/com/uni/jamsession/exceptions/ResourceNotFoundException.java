package com.uni.jamsession.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseCustomException {
  public ResourceNotFoundException(String entityName, Object id) {
    super(entityName + " with Id " + id + " not found", HttpStatus.NOT_FOUND);
  }
}
