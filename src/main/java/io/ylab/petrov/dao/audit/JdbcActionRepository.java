package io.ylab.petrov.dao.audit;

import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.utils.HikariCPDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class JdbcActionRepository implements ActionRepository {

    private final UserRepository userRepository;

    @Override
    public void addAction(Action action) {
        String query = "INSERT INTO domain.action (user_id, activity, date_time) VALUES (?, ?, ?)";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, action.getUserId());
            statement.setString(2, action.getActivity().toString());
            statement.setTimestamp(3, Timestamp.valueOf(action.getDateTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Action> getAllByUserName(String userName) {
        String query = "SELECT * FROM domain.action " +
                "INNER JOIN users ON actions.user_id = users.id " +
                "WHERE users.user_name = ?";
        List<Action> userActions = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                User user = userRepository.getUserById(resultSet.getLong("user_id"))
                        .orElseThrow(() -> new RuntimeException("Пльзователя с таким id не существует"));
                while (resultSet.next()) {
                    Action action = Action.builder()
                            .userId(user.getId())
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
