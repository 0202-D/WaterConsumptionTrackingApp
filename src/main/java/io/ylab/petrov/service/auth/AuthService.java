package io.ylab.petrov.service.auth;

import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.model.user.User;

import java.util.Optional;

public interface AuthService {
    boolean userRegistration(User user);

    Optional<User> authenticateUser(AuthReqDto user);

    Optional<User> getUserByUserName(String userName);
}
