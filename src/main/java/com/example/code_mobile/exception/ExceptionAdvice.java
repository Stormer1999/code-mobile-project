package com.example.code_mobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String handlerProductNotFound(ProductNotFoundException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  String handlerStorageException(StorageException ex) {
    return ex.getMessage();
  }
}
