package com.example.code_mobile.controller.api;

import com.example.code_mobile.controller.request.UserRequest;
import com.example.code_mobile.exception.ValidationException;
import com.example.code_mobile.model.User;
import com.example.code_mobile.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public User register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult
          .getFieldErrors()
          .forEach(
              fieldError -> {
                throw new ValidationException(
                    fieldError.getField() + ": " + fieldError.getDefaultMessage());
              });
    }
    return userService.register(userRequest);
  }
}
