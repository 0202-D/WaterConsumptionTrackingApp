package io.ylab.petrov.dao.user;

import io.ylab.petrov.model.user.User;

import java.util.Optional;

public interface UserRepository {
    void addUser(User user);

    User getUserById(long userId);

    Optional<User> getUserByUserName(String userName);
}
