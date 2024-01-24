package io.ylab.petrov.io.dao;

import io.ylab.petrov.io.model.user.Role;
import io.ylab.petrov.io.model.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        User user = User.builder()
                .userName("user")
                .login("user")
                .role(Role.USER)
                .build();
        User admin = User.builder()
                .userName("admin")
                .login("admin")
                .role(Role.ADMIN)
                .build();
        users.add(user);
        users.add(admin);
    }

    public void addUser(User user){
        users.add(user);
    }
}
