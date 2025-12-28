package com.sap.jamsession.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceDuplicatedException extends BaseCustomException {
  public ResourceDuplicatedException(
      String entityName, String duplicatedValueName, String duplicatedValue) {
    super(
        entityName + " named " + duplicatedValueName + " " + duplicatedValue + " already exists",
        HttpStatus.BAD_REQUEST);
  }
}
