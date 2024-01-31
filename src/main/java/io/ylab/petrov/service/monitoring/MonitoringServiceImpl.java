package io.ylab.petrov.service.monitoring;


import io.ylab.petrov.dao.monitoring.InMemoryMeterRepository;
import io.ylab.petrov.dao.monitoring.MeterRepository;
import io.ylab.petrov.dao.monitoring.ReadingRepository;
import io.ylab.petrov.dao.monitoring.InMemoryReadingRepositoryImpl;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dao.user.InMemoryUserRepositoryImpl;
import io.ylab.petrov.dto.AddReadingRqDto;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.audit.AuditService;
import io.ylab.petrov.service.audit.AuditServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MonitoringServiceImpl implements MonitoringService {
    private static AtomicLong id = new AtomicLong(0);
    private final UserRepository userRepository = new InMemoryUserRepositoryImpl();
    private final ReadingRepository readingRepository = new InMemoryReadingRepositoryImpl();
    private final MeterRepository meterRepository = new InMemoryMeterRepository();
    private final AuditService auditService = new AuditServiceImpl();

    @Override
    public void addReading(AddReadingRqDto dto) {
        User user = userRepository.getUserById(dto.userId());
        Meter meter = meterRepository.getMeterById(dto.meterId());
        ReadingRqDto currentReadingDto = ReadingRqDto.builder()
                .userId(dto.userId())
                .meterId(dto.meterId())
                .build();
        Optional<Reading> currentReading = readingRepository.getCurrentReading(currentReadingDto);
        if (currentReading.isPresent() && currentReading.get().getDate().getMonth() == LocalDate.now().getMonth()) {
            // пока кидаю рантайм для скорости при переходе
            // на web конечно буду выбрасывать понятную ошибку без прекращения работы приложения
            throw new RuntimeException("За этот месяц Вы уже сдавали показания");
        }
        if (currentReading.isPresent()) {
            Reading oldCurrentReading = currentReading.get();
            oldCurrentReading.setCurrent(false);
        }
        Reading reading = Reading.builder()
                .id(id.incrementAndGet())
                .user(user)
                .meter(meter)
                .meterReading(dto.readout())
                .date(LocalDate.now())
                .isCurrent(true)
                .build();
        readingRepository.addReading(reading);
        auditService.addAction(new Action(user, Activity.SUBMITTED, LocalDateTime.now()));
    }

    public Optional<Reading> getCurrentReading(ReadingRqDto dto) {
        User user = userRepository.getUserById(dto.userId());
        auditService.addAction(new Action(user, Activity.REQUESTED, LocalDateTime.now()));
        return readingRepository.getCurrentReading(dto);
    }

    @Override
    public Optional<Reading> getReadingForMonth(ReadingInMonthRq rq) {
        User user = userRepository.getUserById(rq.userId());
        auditService.addAction(new Action(user, Activity.REQUESTED, LocalDateTime.now()));
        return readingRepository.getReadingForMonth(rq);
    }

    @Override
    public List<Reading> historyReadingsByUserId(long userId) {
        return readingRepository.historyReadingsByUserId(userId);
    }
}

