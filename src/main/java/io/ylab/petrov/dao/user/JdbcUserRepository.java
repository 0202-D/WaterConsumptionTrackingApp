package io.ylab.petrov.dao.user;

import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.utils.HikariCPDataSource;

import java.sql.*;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private static final String GET_BY_NAME_QUERY = "SELECT u.* from domain.users u where u.user_name = ?";
    // Метод для получения пользователя по идентификатору
    public Optional<User> getUserById(long userId) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM domain.users WHERE id = ?")) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(Role.valueOf(resultSet.getString("role")));
                    return Optional.of(user);
                } else {
                    System.out.println("Пользователя с таким id не существует");
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Метод для получения пользователя по имени пользователя
    public Optional<User> getUserByUserName(String userName) {
        User user = new User();
        try (Connection connection = HikariCPDataSource.getConnection(); PreparedStatement statement =
                connection.prepareStatement(GET_BY_NAME_QUERY)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        user.setId(resultSet.getLong("id"));
                        user.setUserName(resultSet.getString("user_name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setRole(Role.valueOf(resultSet.getString("role")));
                        return Optional.of(user);
                    } else {
                        System.out.println("Пользователя с таким userName не существует");
                        return Optional.empty();
                    }
                } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO domain.users (user_name, password, role) VALUES (?, ?, ?)";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
