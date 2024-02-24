package com.example.auditaspectstarter.dao;

import com.example.auditaspectstarter.model.Action;
import com.example.auditaspectstarter.utils.HikariCPDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class JdbcActionRepository implements ActionRepository {

    @Override
    public void addAction(Action action) {
        String query = "INSERT INTO domain.action (activity, date_time) VALUES ( ?, ?)";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, action.getActivity().toString());
            statement.setTimestamp(2, Timestamp.valueOf(action.getDateTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
