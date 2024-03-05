package io.ylab.petrov.in.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@Tag(name = "Регистрация и аутентификация")
@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/registration")
    public UserResponseDto addUser(@RequestBody @Valid UserRequestDto dto) {
        return authService.userRegistration(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя")
    public UserResponseDto authenticateUser(@RequestBody @Valid UserRequestDto dto) {
        return authService.authenticateUser(dto);
    }
    public Optional<User> getUserByUserName(String userName) {
        return authService.getUserByUserName(userName);
    }
}
