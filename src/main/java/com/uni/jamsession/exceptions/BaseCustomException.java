package com.sap.jamsession.exceptions;

import java.io.Serial;
import org.springframework.http.HttpStatus;

public class BaseCustomException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;
  private final HttpStatus status;

  public BaseCustomException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public BaseCustomException(String message, HttpStatus status, Throwable cause) {
    super(message, cause);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
