package io.ylab.petrov.service.auth;

import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.dto.user.UserDtoRs;
import io.ylab.petrov.dto.user.UserRqDto;
import io.ylab.petrov.model.user.User;

import java.util.Optional;

public interface AuthService {
    UserDtoRs userRegistration(User user);

    UserDtoRs authenticateUser(UserRqDto user);

    Optional<User> getUserByUserName(String userName);
}
