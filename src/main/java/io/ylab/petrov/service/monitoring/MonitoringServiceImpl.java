package io.ylab.petrov.service.monitoring;


import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.monitoring.*;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.AddReadingRqDto;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.utils.DataBaseConnector;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MonitoringServiceImpl implements MonitoringService {
    // Connection нужно открывать в try with resources , но логика в методах достаточно сложная и во многих местах
    // приложении падало с ошибкой что соединение было уже закрыто. Пока не хватило времени во всех местах
    // разобраться с транзакционностью и закрытием открытием потоков, поэтому упростил с потенциальной утечкой памяти
    private UserRepository userRepository;
    private ReadingRepository readingRepository;
    private MeterRepository meterRepository;
    private ActionRepository actionRepository;

    @Override
    public void addReading(AddReadingRqDto dto) {
        Connection connection = DataBaseConnector.getConnection();
        userRepository = new JdbcUserRepository(connection);
        meterRepository = new JdbcMeterRepository(connection);
        readingRepository = new JdbcReadingRepository(connection);
        User user = userRepository.getUserById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        Meter meter = meterRepository.getMeterById(dto.meterId());
        ReadingRqDto currentReadingDto = ReadingRqDto.builder()
                .userId(dto.userId())
                .meterId(dto.meterId())
                .build();
        Optional<ReadingRs> currentReading = readingRepository.getCurrentReading(currentReadingDto);
        if (currentReading.isPresent() && currentReading.get().getDate().getMonth() == LocalDate.now().getMonth()) {
            throw new RuntimeException("За этот месяц Вы уже сдавали показания");
        }
        if (currentReading.isPresent()) {
            ReadingInMonthRq rq = ReadingInMonthRq.builder()
                    .userId(dto.userId())
                    .meterId(dto.meterId())
                    .month(currentReading.get().getDate().getMonth())
                    .build();
            Reading reading = readingRepository.getReadingForMonth(rq)
                    .orElseThrow(()->new RuntimeException("Показаний с таким id не существует"));
            reading.setCurrent(false);
            readingRepository.save(reading);
        }
        Reading reading = Reading.builder()
                .user(user)
                .meter(meter)
                .meterReading(dto.readout())
                .date(LocalDate.now())
                .isCurrent(true)
                .build();
        readingRepository.addReading(reading);
        Action action = Action.builder()
                .user(user)
                .activity(Activity.SUBMITTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }

    public Optional<ReadingRs> getCurrentReading(ReadingRqDto dto) {
        Connection connection = DataBaseConnector.getConnection();
        userRepository = new JdbcUserRepository(connection);
        readingRepository = new JdbcReadingRepository(connection);
        User user = userRepository.getUserById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .user(user)
                .activity(Activity.REQUESTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        return readingRepository.getCurrentReading(dto);
    }

    @Override
    public Optional<Reading> getReadingForMonth(ReadingInMonthRq rq) {
        Connection connection = DataBaseConnector.getConnection();
        userRepository = new JdbcUserRepository(connection);
        readingRepository = new JdbcReadingRepository(connection);
        User user = userRepository.getUserById(rq.userId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .user(user)
                .activity(Activity.REQUESTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        return readingRepository.getReadingForMonth(rq);
    }

    @Override
    public List<Reading> historyReadingsByUserId(long userId) {
        Connection connection = DataBaseConnector.getConnection();
        readingRepository = new JdbcReadingRepository(connection);
        actionRepository = new JdbcActionRepository(connection);
        userRepository = new JdbcUserRepository(connection);
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .user(user)
                .activity(Activity.HISTORY)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        return readingRepository.historyReadingsByUserId(userId);
    }
}

