package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/reg")
    public UserResponseDto addUser(@RequestBody @Valid User user) {
        return authService.userRegistration(user);
    }

    @PostMapping("/auth")
    public UserResponseDto authenticateUser(@RequestBody @Valid UserRequestDto dto) {
        return authService.authenticateUser(dto);
    }
    public Optional<User> getUserByUserName(String userName) {
        return authService.getUserByUserName(userName);
    }
}
