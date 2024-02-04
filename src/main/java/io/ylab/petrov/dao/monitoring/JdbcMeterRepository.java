package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.model.readout.Meter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcMeterRepository implements MeterRepository {
    private Connection connection;

    public JdbcMeterRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Meter getMeterById(long id) {
        String query = "SELECT * FROM domain.meter WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, meter.getId());
            statement.setString(2, meter.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
