package io.ylab.petrov.io.service;

import io.ylab.petrov.io.dto.AuthReqDto;
import io.ylab.petrov.io.model.user.User;

public interface AuthService {
    boolean userRegistration(User user);

    boolean authenticateUser(AuthReqDto user);
}
