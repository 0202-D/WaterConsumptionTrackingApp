package io.ylab.petrov.dao.audit;

import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcActionRepository implements ActionRepository {
    private final Connection connection;

    private UserRepository userRepository;

    public JdbcActionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAction(Action action) {
        String query = "INSERT INTO domain.action (user_id, activity, date_time) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, action.getUser().getId());
            statement.setString(2, action.getActivity().toString());
            statement.setTimestamp(3, Timestamp.valueOf(action.getDateTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Action> getAllByUserName(String userName) {
        userRepository = new JdbcUserRepository(connection);
        String query = "SELECT * FROM domain.action " +
                "INNER JOIN users ON actions.user_id = users.id " +
                "WHERE users.user_name = ?";
        List<Action> userActions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                var user = userRepository.getUserById(resultSet.getLong("user_id"))
                        .orElseThrow(()->new RuntimeException("Пльзователя с таким id не существует"));
                while (resultSet.next()) {
                    Action action = Action.builder()
                            .user(user)
                            .dateTime(resultSet.getTimestamp("date").toLocalDateTime())
                            .activity(Activity.valueOf(resultSet.getString("activity")))
                            .build();
                    userActions.add(action);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActions;
    }
}
