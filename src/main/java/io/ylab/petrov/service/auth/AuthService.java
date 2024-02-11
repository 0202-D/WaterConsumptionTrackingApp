package io.ylab.petrov.service.auth;

import io.ylab.petrov.dto.user.UserRsDto;
import io.ylab.petrov.dto.user.UserRqDto;
import io.ylab.petrov.model.user.User;

import java.util.Optional;

public interface AuthService {
    UserRsDto userRegistration(User user);

    UserRsDto authenticateUser(UserRqDto user);

    Optional<User> getUserByUserName(String userName);
}
