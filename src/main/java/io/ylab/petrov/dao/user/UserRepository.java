package io.ylab.petrov.dao.user;

import io.ylab.petrov.model.user.User;

public interface UserRepository {
    void addUser(User user);

    User getUserById(long userId);

    User getUserByUserName(String userName);
}
