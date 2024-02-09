package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.user.UserDtoRs;
import io.ylab.petrov.dto.user.UserRqDto;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    public UserDtoRs addUser(User user) {
        return authService.userRegistration(user);
    }

    public UserDtoRs authenticateUser(UserRqDto dto) {
        return authService.authenticateUser(dto);
    }

    public Optional<User> getUserByUserName(String userName) {
        return authService.getUserByUserName(userName);
    }
}
