package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.utils.HikariCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcReadingRepository implements ReadingRepository {
    private UserRepository userRepository;

    private MeterRepository meterRepository;

    @Override
    public void addReading(Reading reading) {
        String query = "INSERT INTO domain.reading (user_id, meter_id, meter_reading, date, is_current) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, reading.getUser().getId());
            statement.setLong(2, reading.getMeter().getId());
            statement.setBigDecimal(3, reading.getMeterReading());
            statement.setDate(4, java.sql.Date.valueOf(reading.getDate()));
            statement.setBoolean(5, reading.isCurrent());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ReadingRs> getCurrentReading(ReadingRqDto dto) {
        String query = "SELECT * FROM domain.reading WHERE user_id = ? AND meter_id = ? AND is_current =true ";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, dto.userId());
            statement.setLong(2, dto.meterId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ReadingRs reading = ReadingRs.builder()
                            .reading(resultSet.getBigDecimal("meter_reading"))
                            .date(resultSet.getDate("date").toLocalDate())
                            .build();
                    return Optional.of(reading);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Reading> getReadingForMonth(ReadingInMonthRq rq) {
        userRepository = new JdbcUserRepository();
        meterRepository = new JdbcMeterRepository();
        String query = "SELECT * FROM domain.reading WHERE user_id = ? AND meter_id = ? AND EXTRACT(MONTH FROM date) = ?";
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, rq.userId());
            statement.setLong(2, rq.meterId());
            statement.setInt(3, rq.month().getValue());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Reading reading = new Reading();
                    reading.setId(resultSet.getLong("id"));
                    long userId = resultSet.getLong("user_id");
                    long meterId = resultSet.getLong("meter_id");
                    User user = userRepository.getUserById(userId)
                            .orElseThrow(() -> new RuntimeException("Пользователя с таким id е существует"));
                    Meter meter = meterRepository.getMeterById(meterId);
                    reading.setMeter(meter);
                    reading.setUser(user);
                    reading.setMeterReading(resultSet.getBigDecimal("meter_reading"));
                    reading.setDate(resultSet.getDate("date").toLocalDate());
                    return Optional.of(reading);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Reading> historyReadingsByUserId(long id) {
        String query = "SELECT * FROM domain.reading WHERE user_id = ?";
        List<Reading> historyReadings = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reading reading = new Reading();
                    User user = userRepository.getUserById(resultSet.getLong("user_id"))
                            .orElseThrow(() -> new RuntimeException("Пользователя с таким Id не существует"));
                    Meter meter = meterRepository.getMeterById(resultSet.getLong("meter_id"));
                    reading.setMeter(meter);
                    reading.setUser(user);
                    reading.setDate(resultSet.getDate("date").toLocalDate());
                    reading.setCurrent(Boolean.parseBoolean(resultSet.getString("is_current")));
                    reading.setMeterReading(resultSet.getBigDecimal("meter_reading"));
                    reading.setId(resultSet.getLong("id"));
                    historyReadings.add(reading);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyReadings;
    }

    @Override
    public void save(Reading reading) {
        String query = "UPDATE domain.reading set is_current=false where id=" + reading.getId();
        try {
            Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

