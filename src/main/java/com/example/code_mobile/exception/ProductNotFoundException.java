package com.example.code_mobile.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long id) {
    super("Could not  product" + id);
  }
}
