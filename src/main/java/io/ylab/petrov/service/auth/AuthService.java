package io.ylab.petrov.service.auth;

import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.model.user.User;

public interface AuthService {
    boolean userRegistration(User user);

    boolean authenticateUser(AuthReqDto user);
}
