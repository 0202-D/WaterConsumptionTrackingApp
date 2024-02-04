package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import io.ylab.petrov.service.auth.AuthServiceImpl;

import java.util.Optional;

public class AuthController {
    private final AuthService authService = new AuthServiceImpl();

    public boolean addUser(User user) {
        return authService.userRegistration(user);
    }

    public Optional<User> authenticateUser(AuthReqDto dto) {
        return authService.authenticateUser(dto);
    }

    public Optional<User> getUserByUserName(String userName) {
        return authService.getUserByUserName(userName);
    }
}
