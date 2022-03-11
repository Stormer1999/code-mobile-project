package com.example.code_mobile.exception;

public class UserDuplicateException extends RuntimeException {

  public UserDuplicateException(String username) {
    super("Username: " + username + " already exist");
  }
}
