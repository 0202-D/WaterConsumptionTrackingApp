package io.ylab.petrov.dao.user;

import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;

import java.sql.*;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository{
    private Connection connection;

    public JdbcUserRepository(Connection connection) {
        this.connection = connection;
    }
    // Метод для получения пользователя по идентификатору
    public User getUserById(long userId) {
        String sql = "SELECT * FROM domain.users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Создаем и возвращаем объект User с данными из базы данных
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(Role.valueOf(resultSet.getString("role")));
                    return user;
                } else {
                    System.out.println("Пользователя с таким id не существует");
                    return null;
                }
            }
        } catch (SQLException e) {
            // Обработка ошибок
            e.printStackTrace();
            return null;
        }
    }

    // Метод для получения пользователя по имени пользователя
    public Optional<User> getUserByUserName(String userName) {
        String sql = "SELECT * FROM domain.users WHERE user_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(Role.valueOf(resultSet.getString("role")));
                    return Optional.of(user);
                } else {
                    System.out.println("Пользователя с таким userName не существует");
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user)  {
        String sql = "INSERT INTO domain.users (user_name, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
