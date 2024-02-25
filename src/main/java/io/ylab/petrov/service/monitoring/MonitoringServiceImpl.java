package io.ylab.petrov.service.monitoring;


import io.ylab.petrov.dao.monitoring.*;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {
    private final UserRepository userRepository;
    private final ReadingRepository readingRepository;
    private final MeterRepository meterRepository;

    @Override
    public Optional<ReadingResponseDto> getCurrentReading(long userId, long meterId) {
        ReadingRequestDto dto = ReadingRequestDto.builder()
                .userId(userId)
                .meterId(meterId)
                .build();
        User user = userRepository.getUserById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        return readingRepository.getCurrentReading(dto);
    }

    @Override
    public Optional<Reading> getReadingForMonth(long userId, long meterId, Month month) {
        ReadingInMonthRequestDto dto = ReadingInMonthRequestDto.builder()
                .userId(userId)
                .meterId(meterId)
                .month(month)
                .build();
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        return readingRepository.getReadingForMonth(dto);
    }

    @Override
    public List<Reading> historyReadingsByUserId(long userId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        return readingRepository.historyReadingsByUserId(userId);
    }

    @Override
    public boolean addReading(AddReadingRequestDto dto) {
        User user = getUserById(dto.getUserId());
        Meter meter = getMeterById(dto.getMeterId());
        checkIfAlreadySubmittedForMonth(dto.getUserId(), dto.getMeterId());
        updatePreviousReading(dto.getUserId(), dto.getMeterId());
        saveNewReading(user, meter, dto.getReadout());
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
        ReadingRequestDto currentReadingDto = ReadingRequestDto.builder()
                .userId(userId)
                .meterId(meterId)
                .build();
        Optional<ReadingResponseDto> currentReading = readingRepository.getCurrentReading(currentReadingDto);
        if (currentReading.isPresent() && currentReading.get().getDate().getMonth() == LocalDate.now().getMonth()) {
            throw new RuntimeException("За этот месяц Вы уже сдавали показания");
        }
    }

    private void updatePreviousReading(long userId, long meterId) {
        ReadingRequestDto currentReadingDto = ReadingRequestDto.builder()
                .userId(userId)
                .meterId(meterId)
                .build();
        Optional<ReadingResponseDto> currentReading = readingRepository.getCurrentReading(currentReadingDto);
        if (currentReading.isPresent()) {
            ReadingInMonthRequestDto rq = ReadingInMonthRequestDto.builder()
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

