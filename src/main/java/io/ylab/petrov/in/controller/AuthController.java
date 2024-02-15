package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    public UserResponseDto addUser(User user) {
        return authService.userRegistration(user);
    }

    public UserResponseDto authenticateUser(UserRequestDto dto) {
        return authService.authenticateUser(dto);
    }

    public Optional<User> getUserByUserName(String userName) {
        return authService.getUserByUserName(userName);
    }
}
