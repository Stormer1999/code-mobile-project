package com.example.code_mobile.service;

import com.example.code_mobile.controller.request.UserRequest;
import com.example.code_mobile.exception.UserDuplicateException;
import com.example.code_mobile.model.User;
import com.example.code_mobile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserServiceImpl(
      UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public User register(UserRequest userRequest) {
    User user = userRepository.findByUsername(userRequest.getUsername());
    if (user == null) {
      user =
          new User()
              .setUsername(userRequest.getUsername())
              .setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
              .setRole(userRequest.getRole());
      return userRepository.save(user);
    }
    throw new UserDuplicateException(user.getUsername());
  }
}
