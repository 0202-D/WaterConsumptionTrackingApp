package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.utils.HikariCPDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Repository
public class JdbcMeterRepository implements MeterRepository {
    @Override
    public Meter getMeterById(long id) {
        String query = "SELECT * FROM domain.meter WHERE id = ?";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Meter meter = new Meter();
                    meter.setId(resultSet.getLong("id"));
                    meter.setName(resultSet.getString("name"));
                    return meter;
                } else {
                    System.out.println("Такого счетчика не существует");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Meter meter) {
        String query = "INSERT INTO domain.meter (id, name) VALUES (?, ?)";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, meter.getId());
            statement.setString(2, meter.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
