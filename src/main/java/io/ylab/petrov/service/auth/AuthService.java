package io.ylab.petrov.service.auth;

import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.model.user.User;

import java.util.Optional;

public interface AuthService {
    UserResponseDto userRegistration(User user);

    UserResponseDto authenticateUser(UserRequestDto user);

    Optional<User> getUserByUserName(String userName);
}
