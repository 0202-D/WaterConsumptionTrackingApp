package io.ylab.petrov.service.monitoring;


import io.ylab.petrov.aop.annotation.Loggable;
import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.monitoring.*;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.monitoring.AddReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRsDto;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MonitoringServiceImpl implements MonitoringService {
    private final UserRepository userRepository = new JdbcUserRepository();
    private final ReadingRepository readingRepository = new JdbcReadingRepository();
    private final MeterRepository meterRepository = new JdbcMeterRepository();
    private final ActionRepository actionRepository = new JdbcActionRepository();

    @Override
    @Loggable
    public Optional<ReadingRsDto> getCurrentReading(ReadingRqDto dto) {
        User user = userRepository.getUserById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        return readingRepository.getCurrentReading(dto);
    }

    @Override
    public Optional<Reading> getReadingForMonth(ReadingInMonthRqDto rq) {
        User user = userRepository.getUserById(rq.userId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        return readingRepository.getReadingForMonth(rq);
    }

    @Override
    public List<Reading> historyReadingsByUserId(long userId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        return readingRepository.historyReadingsByUserId(userId);
    }

    @Override
    public boolean addReading(AddReadingRqDto dto) {
            User user = getUserById(dto.userId());
            Meter meter = getMeterById(dto.meterId());
            checkIfAlreadySubmittedForMonth(dto.userId(), dto.meterId());
            updatePreviousReading(dto.userId(), dto.meterId());
            saveNewReading(user, meter, dto.readout());
            return true;
    }

    private User getUserById(long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
    }

    private Meter getMeterById(long meterId) {
        return meterRepository.getMeterById(meterId);
    }

    private void checkIfAlreadySubmittedForMonth(long userId, long meterId) {
        ReadingRqDto currentReadingDto = ReadingRqDto.builder()
                .userId(userId)
                .meterId(meterId)
                .build();
        Optional<ReadingRsDto> currentReading = readingRepository.getCurrentReading(currentReadingDto);
        if (currentReading.isPresent() && currentReading.get().getDate().getMonth() == LocalDate.now().getMonth()) {
            throw new RuntimeException("За этот месяц Вы уже сдавали показания");
        }
    }

    private void updatePreviousReading(long userId, long meterId) {
        ReadingRqDto currentReadingDto = ReadingRqDto.builder()
                .userId(userId)
                .meterId(meterId)
                .build();
        Optional<ReadingRsDto> currentReading = readingRepository.getCurrentReading(currentReadingDto);
        if (currentReading.isPresent()) {
            ReadingInMonthRqDto rq = ReadingInMonthRqDto.builder()
                    .userId(userId)
                    .meterId(meterId)
                    .month(currentReading.get().getDate().getMonth())
                    .build();
            Reading reading = readingRepository.getReadingForMonth(rq)
                    .orElseThrow(() -> new RuntimeException("Показаний с таким id не существует"));
            reading.setCurrent(false);
            readingRepository.save(reading);
        }
    }

    private void saveNewReading(User user, Meter meter, BigDecimal readout) {
        Reading reading = Reading.builder()
                .user(user)
                .meter(meter)
                .meterReading(readout)
                .date(LocalDate.now())
                .isCurrent(true)
                .build();
        readingRepository.addReading(reading);
    }
}

