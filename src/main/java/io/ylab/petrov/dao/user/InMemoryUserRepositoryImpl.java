package io.ylab.petrov.dao.user;

import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class InMemoryUserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    public InMemoryUserRepositoryImpl() {
        User user = User.builder()
                .id(1L)
                .userName("user")
                .password("user")
                .role(Role.USER)
                .build();
        User admin = User.builder()
                .id(2L)
                .userName("admin")
                .password("admin")
                .role(Role.ADMIN)
                .build();
        users.add(user);
        users.add(admin);
    }

    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public User getUserById(long userId) {
        Optional<User> user = users.stream().filter(e -> e.getId() == userId).findFirst();
        if (user.isEmpty()) {
            System.out.println("Пользователя с таким id не существует");
            return null;
        }
        return user.get();
    }

    @Override
    public User getUserByUserName(String userName) {
        Optional<User> user = users.stream().filter(el -> el.getUserName().equals(userName)).findFirst();
        if (user.isEmpty()) {
            System.out.println("Пользователя с таким userName не существует");
            return null;
        }
        return user.get();
    }
}
