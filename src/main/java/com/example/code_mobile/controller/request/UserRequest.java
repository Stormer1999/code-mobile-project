package com.example.code_mobile.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

  @NotEmpty
  @Size(min = 1, max = 100)
  private String username;

  @NotEmpty
  @Length(min = 8, message = "The field must be at least {min} characters")
  private String password;

  @NotEmpty
  private String role;
}
