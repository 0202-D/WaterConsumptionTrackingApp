package io.ylab.petrov.service.monitoring;

import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface MonitoringService {
    boolean addReading(AddReadingRequestDto dto);

    Optional<ReadingResponseDto> getCurrentReading(long userId, long meterId);

    Optional<Reading> getReadingForMonth(long userId, long meterId, Month month);

    List<Reading> historyReadingsByUserId(long userId);
}