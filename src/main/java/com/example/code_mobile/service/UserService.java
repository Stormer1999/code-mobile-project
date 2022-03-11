package com.example.code_mobile.service;

import com.example.code_mobile.controller.request.UserRequest;
import com.example.code_mobile.model.User;

public interface UserService {

    User register(UserRequest user);
}
