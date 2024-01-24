package io.ylab.petrov.io.controller;

import io.ylab.petrov.io.dto.AuthReqDto;
import io.ylab.petrov.io.model.user.User;
import io.ylab.petrov.io.service.AuthService;
import io.ylab.petrov.io.service.AuthServiceImpl;

public class AuthController {
    AuthService authService = new AuthServiceImpl();

    public void addUser(User user) {
        authService.userRegistration(user);
    }

    public boolean authenticateUser(AuthReqDto dto) {
        return authService.authenticateUser(dto);
    }
}
