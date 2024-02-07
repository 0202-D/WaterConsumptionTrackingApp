package io.ylab.petrov.service.auth;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.utils.DataBaseConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthServiceImpl implements AuthService {
    private ActionRepository actionRepository;
    private UserRepository userRepository;

    @Override
    public boolean userRegistration(User user) {
        Connection connection = DataBaseConnector.getConnection();
        try {
            connection.setAutoCommit(false);
            userRepository = new JdbcUserRepository();
            actionRepository = new JdbcActionRepository();
            Optional<User> searchUser = userRepository.getUserByUserName(user.getUserName());
            if (searchUser.isPresent()) {
                System.out.println("Пользователь с таким именем уже существует");
                return false;
            }
            userRepository.addUser(user);
            var actionUser = userRepository.getUserByUserName(user.getUserName())
                    .orElseThrow(() -> new RuntimeException("Такого пользователя не существует"));
            Action action = Action.builder()
                    .user(actionUser)
                    .activity(Activity.REGISTERED)
                    .dateTime(LocalDateTime.now())
                    .build();
            actionRepository.addAction(action);
            connection.commit();
            return true;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Optional<User> authenticateUser(AuthReqDto user) {
        Optional<User> searchUser = null;
        Connection connection = DataBaseConnector.getConnection();
        try {
            connection.setAutoCommit(false);
            userRepository = new JdbcUserRepository();
            actionRepository = new JdbcActionRepository();
            searchUser = userRepository.getUserByUserName(user.userName());
            if (searchUser.isEmpty()) {
                System.out.println("Пользователь с таким именем не существует");
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Action action = Action.builder()
                .user(searchUser.get())
                .activity(Activity.ENTERED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        return searchUser;
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }
}
