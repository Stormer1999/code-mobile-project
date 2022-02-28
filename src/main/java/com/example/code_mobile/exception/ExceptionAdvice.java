package com.example.code_mobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String productNotFound(ProductNotFoundException ex) {
    return ex.getMessage();
  }
}
