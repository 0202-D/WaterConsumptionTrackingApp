package io.ylab.petrov.io.service;

import io.ylab.petrov.io.dao.UserRepository;
import io.ylab.petrov.io.dto.AuthReqDto;
import io.ylab.petrov.io.model.user.User;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {
    UserRepository userRepository = new UserRepository();

    @Override
    public boolean userRegistration(User user) {
        userRepository.addUser(user);
        return true;
    }

    @Override
    public boolean authenticateUser(AuthReqDto user) {
        Optional<User> searchUser = userRepository.getUsers().stream()
                .filter(el -> el.getUserName().equals(user.userName())).findFirst();
        if (searchUser.isEmpty()) {
            System.out.println("Пользователя с таким именем не существует");
            return false;
        }
        return searchUser.get().getLogin().equals(user.password());
    }
}
