package io.ylab.auditaspectstarter.dao;

import io.ylab.auditaspectstarter.model.Action;
import io.ylab.auditaspectstarter.utils.HikariCPDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class ActionRepositoryImpl implements ActionRepository {
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
}
